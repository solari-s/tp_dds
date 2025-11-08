document.getElementById("formHuesped").addEventListener("submit", e => {
  e.preventDefault();

  const apellido = document.getElementById("apellido").value.trim();
  const nombres = document.getElementById("nombres").value.trim();
  const tipoDoc = document.getElementById("tipoDoc").value;
  const nroDoc = document.getElementById("nroDoc").value.trim();

  let errores = [];

  if (!apellido) errores.push("Apellido es obligatorio");
  if (!nombres) errores.push("Nombres es obligatorio");
  if (!tipoDoc) errores.push("Debe seleccionar tipo de documento");
  if (!nroDoc) errores.push("Número de documento es obligatorio");

  if (errores.length > 0) {
    alert("⚠️ Errores:\n- " + errores.join("\n- "));
    return;
  }

  alert(`✅ Huésped ${nombres} ${apellido} cargado correctamente.`);
  e.target.reset();
});
