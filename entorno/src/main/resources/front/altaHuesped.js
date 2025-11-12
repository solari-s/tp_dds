document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("formHuesped");
  const mensajeDiv = document.getElementById("mensaje"); // Lo mantenemos por si lo usas para 'mostrarExito'
  const btnCancelar = document.getElementById("btnCancelar");

  btnCancelar.addEventListener("click", () => {
    clearFieldErrors();
    form.reset();
  });

  form.addEventListener("submit", e => {
    e.preventDefault();
    clearFieldErrors(); // Llama a la nueva función (ver abajo)
    
    let hayErrores = false;

    // --- VALIDACIONES ---
    // (Ahora solo añadimos .campo-error y ponemos hayErrores = true)

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
    } else if (new Date(nacimiento) > new Date()) { // Asumiendo que flatpickr no lo previene
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
      return; // Detiene el envío, pero no muestra ningún mensaje de texto
    }

    // Si todo está bien
    mostrarExito(`✅ El huésped se cargó correctamente.`);
    form.reset();
  });

  // --- [NUEVO] LIMPIEZA AUTOMÁTICA DE ERRORES ---
  // Esto quita el borde rojo en cuanto el usuario empieza a escribir o cambia una opción.
  form.querySelectorAll("input, select").forEach(campo => {
    const limpiarError = () => {
      campo.classList.remove("campo-error");
      campo.closest('.seleccionar')?.querySelector('.flecha')?.classList.remove('cajon-error');
    }
    campo.addEventListener("input", limpiarError);
    campo.addEventListener("change", limpiarError);
  });

}); 

// --- FUNCIONES AUXILIARES MODIFICADAS ---

function clearFieldErrors() {
  // Busca TODOS los campos y les quita la clase de error
  document.querySelectorAll("input, select").forEach(el => {
    el.classList.remove("campo-error");
  });
  
  // Sigue limpiando el div de mensajes por si 'mostrarExito' lo usó
  const mensajeDiv = document.getElementById("mensaje");
  if (mensajeDiv) mensajeDiv.textContent = "";

  document.querySelectorAll('.flecha').forEach(el => {
    el.classList.remove('cajon-error');
  });
}


function isValidEmail(email) {
  return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
}

function isValidCUIT(cuit) {
  return /^(\d{11}|\d{2}-\d{8}-\d{1})$/.test(cuit);
}


function mostrarExito(mensaje) {
  const alert = document.createElement("div");
  alert.className = "alert success"; 
  alert.textContent = mensaje;
  document.body.appendChild(alert);
  setTimeout(() => alert.remove(), 3000);
}