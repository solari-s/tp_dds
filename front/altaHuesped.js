document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("formHuesped");

  form.addEventListener("submit", e => {
    e.preventDefault();

    // Validación simple
    const nombre = document.getElementById("nombres").value.trim();
    if (nombre === "") {
      mostrarError("Debe ingresar el nombre del huésped");
      return;
    }

    mostrarExito(`✅ El huésped se cargado correctamente.`);
    form.reset();
  });
});

function mostrarError(mensaje) {
  const alert = document.createElement("div");
  alert.className = "alert error";
  alert.textContent = mensaje;
  document.body.appendChild(alert);

  setTimeout(() => alert.remove(), 3000);
}

function mostrarExito(mensaje) {
  const alert = document.createElement("div");
  alert.className = "alert success";
  alert.textContent = mensaje;
  document.body.appendChild(alert);

  setTimeout(() => alert.remove(), 3000);
}
