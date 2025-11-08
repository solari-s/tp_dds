document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("formHuesped");
  const mensajeDiv = document.getElementById("mensaje");
  const btnCancelar = document.getElementById("btnCancelar");

  btnCancelar.addEventListener("click", () => {
    clearFieldErrors();
    form.reset();
  });

  form.addEventListener("submit", e => {
    e.preventDefault();
    clearFieldErrors();
    mensajeDiv.textContent = "";

    let hayErrores = false;
    let mensajesErrores = [];

    // --- VALIDACIONES ---

    const apellidoEl = document.getElementById("apellido");
    const apellido = apellidoEl.value.trim();
    if (!apellido) {
      showFieldError(apellidoEl, "Debe ingresar el apellido");
      mensajesErrores.push("Debe ingresar el apellido");
      hayErrores = true;
    }

    const nombresEl = document.getElementById("nombres");
    const nombres = nombresEl.value.trim();
    if (!nombres) {
      showFieldError(nombresEl, "Debe ingresar los nombres");
      mensajesErrores.push("Debe ingresar los nombres");
      hayErrores = true;
    }

    const tipoDocEl = document.getElementById("tipoDoc");
    const tipoDoc = tipoDocEl.value;
    if (!tipoDoc) {
      showFieldError(tipoDocEl, "Debe seleccionar el tipo de documento");
      mensajesErrores.push("Debe seleccionar el tipo de documento");
      hayErrores = true;
    }

    const numDocEl = document.getElementById("numDoc");
    const numDoc = numDocEl.value.trim();
    if (!numDoc) {
      showFieldError(numDocEl, "Debe ingresar el número de documento");
      mensajesErrores.push("Debe ingresar el número de documento");
      hayErrores = true;
    } else if (!/^\d+$/.test(numDoc) || Number(numDoc) <= 0) {
      showFieldError(numDocEl, "El número de documento debe ser numérico y mayor a 0");
      mensajesErrores.push("El número de documento debe ser numérico y mayor a 0");
      hayErrores = true;
    }

    const cuitEl = document.getElementById("cuit");
    const cuit = cuitEl.value.trim();
    if (cuit && !isValidCUIT(cuit)) {
      showFieldError(cuitEl, "CUIT inválido (usar 11 dígitos o formato 00-00000000-0)");
      mensajesErrores.push("CUIT inválido (usar 11 dígitos o formato 00-00000000-0)");
      hayErrores = true;
    }

    const nacimientoEl = document.getElementById("nacimiento");
    const nacimiento = nacimientoEl.value;
    if (!nacimiento) {
      showFieldError(nacimientoEl, "Debe ingresar la fecha de nacimiento");
      mensajesErrores.push("Debe ingresar la fecha de nacimiento");
      hayErrores = true;
    } else if (new Date(nacimiento) > new Date()) {
      showFieldError(nacimientoEl, "La fecha de nacimiento no puede ser futura");
      mensajesErrores.push("La fecha de nacimiento no puede ser futura");
      hayErrores = true;
    }

    const direccionEl = document.getElementById("direccion");
    const direccion = direccionEl.value.trim();
    if (!direccion) {
      showFieldError(direccionEl, "Debe ingresar la dirección");
      mensajesErrores.push("Debe ingresar la dirección");
      hayErrores = true;
    }

    const telefonoEl = document.getElementById("telefono");
    const telefono = telefonoEl.value.trim();
    if (!telefono) {
      showFieldError(telefonoEl, "Debe ingresar el teléfono");
      mensajesErrores.push("Debe ingresar el teléfono");
      hayErrores = true;
    } else if (!/^\+?\d{6,15}$/.test(telefono)) {
      showFieldError(telefonoEl, "Teléfono inválido (solo dígitos, opcional +, 6-15 dígitos)");
      mensajesErrores.push("Teléfono inválido (solo dígitos, opcional +, 6-15 dígitos)");
      hayErrores = true;
    }

    const emailEl = document.getElementById("email");
    const email = emailEl.value.trim();
    if (email && !isValidEmail(email)) {
      showFieldError(emailEl, "Email inválido");
      mensajesErrores.push("Email inválido");
      hayErrores = true;
    }

    const ocupacionEl = document.getElementById("ocupacion");
    const ocupacion = ocupacionEl.value.trim();
    if (!ocupacion) {
      showFieldError(ocupacionEl, "Debe ingresar la ocupación");
      mensajesErrores.push("Debe ingresar la ocupación");
      hayErrores = true;
    }

    const nacionalidadEl = document.getElementById("nacionalidad");
    const nacionalidad = nacionalidadEl.value.trim();
    if (!nacionalidad) {
      showFieldError(nacionalidadEl, "Debe ingresar la nacionalidad");
      mensajesErrores.push("Debe ingresar la nacionalidad");
      hayErrores = true;
    }

    // --- RESULTADO ---
    if (hayErrores) {
      mostrarErroresGlobales(mensajesErrores);
      return;
    }

    // Si todo está bien
    mostrarExito(`✅ El huésped se cargó correctamente.`);
    form.reset();
  });
});

function clearFieldErrors() {
  document.querySelectorAll(".field-error").forEach(el => el.remove());
  const mensajeDiv = document.getElementById("mensaje");
  if (mensajeDiv) mensajeDiv.textContent = "";
}

function showFieldError(fieldEl, mensaje) {
  const span = document.createElement("div");
  span.className = "field-error error";
  span.textContent = mensaje;
  fieldEl.parentNode.insertBefore(span, fieldEl.nextSibling);
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

function mostrarError(mensaje) {
  const alert = document.createElement("div");
  alert.className = "alert error";
  alert.textContent = mensaje;
  document.body.appendChild(alert);
  setTimeout(() => alert.remove(), 3000);
}
