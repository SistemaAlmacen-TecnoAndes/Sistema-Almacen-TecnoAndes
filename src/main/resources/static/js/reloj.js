function actualizarReloj() {
    const ahora = new Date();
    
    // Formatear la fecha (ej: 09 nov 2026)
    const opcionesFecha = { day: '2-digit', month: 'short', year: 'numeric' };
    const fecha = ahora.toLocaleDateString('es-ES', opcionesFecha);
    
    // Formatear la hora (ej: 22:04:05)
    const opcionesHora = { hour: '2-digit', minute: '2-digit', second: '2-digit', hour12: false };
    const hora = ahora.toLocaleTimeString('es-ES', opcionesHora);
    
    // Buscar los elementos en el HTML y actualizarlos
    const elementFecha = document.getElementById('fechaActual');
    const elementReloj = document.getElementById('relojActual');
    
    if (elementFecha && elementReloj) {
        elementFecha.textContent = fecha;
        elementReloj.textContent = hora;
    }
}

// Ejecutar la función cada segundo
setInterval(actualizarReloj, 1000);
// Ejecutarla una vez al cargar la página
document.addEventListener('DOMContentLoaded', actualizarReloj);