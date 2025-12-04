document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("formHuesped");
  const btnCancelar = document.getElementById("btnCancelar");

  form.addEventListener("submit", async (e) => {
    e.preventDefault();
    clearFieldErrors();
    let hayErrores = false;

    // --- VALIDACIONES ---
    const apellidoEl = document.getElementById("apellido");
    if (!apellidoEl.value.trim()) {
      apellidoEl.classList.add("campo-error");
      hayErrores = true;
    }

    const nombresEl = document.getElementById("nombres");
    if (!nombresEl.value.trim()) {
      nombresEl.classList.add("campo-error");
      hayErrores = true;
    }

    const tipoDocEl = document.getElementById("tipoDoc");
    if (!tipoDocEl.value) {
      tipoDocEl.classList.add("campo-error");
      hayErrores = true;
    }

    const numDocEl = document.getElementById("numDoc");
    const numDoc = numDocEl.value.trim();
    if (!numDoc) {
      numDocEl.classList.add("campo-error");
      hayErrores = true;
    } else if (!/^\d+$/.test(numDoc) || Number(numDoc) <= 0) {
      numDocEl.classList.add("campo-error");
      hayErrores = true;
    }

    const cuitEl = document.getElementById("cuit");
    const cuit = cuitEl.value.trim();
    if (cuit && !isValidCUIT(cuit)) {
      cuitEl.classList.add("campo-error");
      hayErrores = true;
    }

    const nacimientoEl = document.getElementById("nacimiento");
    const nacimiento = nacimientoEl.value;
    if (!nacimiento) {
      nacimientoEl.classList.add("campo-error");
      nacimientoEl.closest('.seleccionar')?.querySelector('.flecha')?.classList.add('cajon-error');
      hayErrores = true;
    } else if (new Date(nacimiento) > new Date()) {
      nacimientoEl.classList.add("campo-error");
      hayErrores = true;
    }

    const direccionEl = document.getElementById("direccion");
    if (!direccionEl.value.trim()) {
      direccionEl.classList.add("campo-error");
      hayErrores = true;
    }

    const telefonoEl = document.getElementById("telefono");
    const telefono = telefonoEl.value.trim();
    if (!telefono) {
      telefonoEl.classList.add("campo-error");
      hayErrores = true;
    } else if (!/^\+?\d{6,15}$/.test(telefono)) {
      telefonoEl.classList.add("campo-error");
      hayErrores = true;
    }

    const emailEl = document.getElementById("email");
    const email = emailEl.value.trim();
    if (email && !isValidEmail(email)) {
      emailEl.classList.add("campo-error");
      hayErrores = true;
    }

    const ocupacionEl = document.getElementById("ocupacion");
    if (!ocupacionEl.value.trim()) {
      ocupacionEl.classList.add("campo-error");
      hayErrores = true;
    }

    const nacionalidadEl = document.getElementById("nacionalidad");
    if (!nacionalidadEl.value.trim()) {
      nacionalidadEl.classList.add("campo-error");
      hayErrores = true;
    }

    // --- RESULTADO ---
if (hayErrores) {
      console.log("Formulario inválido, campos marcados en rojo.");
      return;
    }

    // 1. Preparar los datos (Mapeo manual de IDs HTML a atributos del DTO)
    const datosHuesped = {
        nombre: nombresEl.value,      // HTML id="nombres" -> DTO "nombre"
        apellido: apellidoEl.value,
        // Convertimos a mayúsculas para asegurar que coincida con el Enum en Java (DNI, PASAPORTE)
        tipo_documento: tipoDocEl.value.toUpperCase(), 
        nroDocumento: numDocEl.value, // HTML id="numDoc" -> DTO "nroDocumento"
        // Convertimos la fecha de dd/mm/yyyy a yyyy-mm-dd
        fechaDeNacimiento: convertirFecha(nacimientoEl.value),
        nacionalidad: nacionalidadEl.value,
        email: emailEl.value,
        telefono: telefonoEl.value,
        ocupacion: ocupacionEl.value,
        direccion: direccionEl.value 
    };

    try {
        // 2. Enviar al servidor
        const response = await fetch('/api/huespedes/crear', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(datosHuesped)
        });

        if (!response.ok) throw new Error('Error en la respuesta del servidor');

        // 3. Mostrar Popup de Éxito SOLO si se guardó correctamente
        const resultado = await Swal.fire({
            title: `El huésped ${nombresEl.value} ${apellidoEl.value} ha sido cargado correctamente.`,
            text: "¿Desea cargar otro?",
            icon: "success",
            showCancelButton: true,
            confirmButtonText: "SÍ",
            cancelButtonText: "NO",
            confirmButtonColor: "#A8C6FA",
            cancelButtonColor: "#A8C6FA",
            background: "#D4E3FC",
            color: "#000000",
            width: '400px',
            reverseButtons: true
        });

        if (resultado.isConfirmed) {
            clearFieldErrors();
            form.reset();
        } else {
            window.location.href = "/"; // O redirigir a donde quieras
        }

    } catch (error) {
        console.error(error);
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'No se pudo guardar el huésped. Intente nuevamente.'
        });
    }
  });

  // --- LIMPIEZA AUTOMÁTICA DE ERRORES ---
  form.querySelectorAll("input, select").forEach(campo => {
    const limpiarError = () => {
      campo.classList.remove("campo-error");
      campo.closest('.seleccionar')?.querySelector('.flecha')?.classList.remove('cajon-error');
    };
    campo.addEventListener("input", limpiarError);
    campo.addEventListener("change", limpiarError);
  });

  // --- POPUP CANCELAR ---
  btnCancelar.addEventListener("click", async () => {
    const resultado = await Swal.fire({
      text: "¿Desea cancelar el alta del huésped?",
      showCancelButton: true,
      confirmButtonText: "SÍ",
      cancelButtonText: "NO",
      confirmButtonColor: "#A8C6FA",
      cancelButtonColor: "#A8C6FA",
      background: "#D4E3FC",
      color: "#000000",
      width: '400px',
      height: '184px',
      reverseButtons: true
    });

    if (resultado.isConfirmed) {
      clearFieldErrors();
      form.reset();
    }
  });
});

// --- FUNCIONES AUXILIARES ---
function clearFieldErrors() {
  document.querySelectorAll("input, select").forEach(el => {
    el.classList.remove("campo-error");
  });
  document.querySelectorAll('.flecha').forEach(el => {
    el.classList.remove('cajon-error');
  });
}

function isValidEmail(email) {
  return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
}


function isValidCUIT(cuit) {
  if (!cuit) return true; // Si es opcional y está vacío, es válido
  
  // 1. Eliminamos guiones, espacios y cualquier cosa que no sea número
  const limpio = cuit.toString().replace(/[^0-9]/g, "");
  
  // 2. Verificamos que tenga exactamente 11 números
  if (limpio.length !== 11) return false;

  // (Opcional) Si quieres validar el dígito verificador real de AFIP, 
  // podés agregar esa lógica aquí, pero por ahora con validar longitud basta 
  // para que no te de el error "en rojo" con un número correcto.
  return true;
}


function convertirFecha(fechaDDMMYYYY) {
    if (!fechaDDMMYYYY) return null;
    const partes = fechaDDMMYYYY.split('/');
    if (partes.length !== 3) return fechaDDMMYYYY;
    return `${partes[2]}-${partes[1]}-${partes[0]}`;
}