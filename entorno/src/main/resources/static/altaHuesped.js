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

    // ✅ Si todo está bien: popup de éxito
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
      height: '184px',
      reverseButtons: true
    });

    if (resultado.isConfirmed) {
      clearFieldErrors();
      form.reset();
    } else {
      console.log("No desea cargar otro huésped.");
      // Podés redirigir o cerrar modal si querés
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
  return /^(\d{11}|\d{2}-\d{8}-\d{1})$/.test(cuit);
}
