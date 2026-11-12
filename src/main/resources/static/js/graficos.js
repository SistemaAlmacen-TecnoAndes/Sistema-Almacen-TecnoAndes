document.addEventListener('DOMContentLoaded', function() {
    
    // 1. Configuración del Gráfico de Líneas
    const ctxLineas = document.getElementById('graficoLineas').getContext('2d');
    new Chart(ctxLineas, {
        type: 'line',
        data: {
            labels: ['Semana 1', 'Semana 2', 'Semana 3', 'Semana 4'],
            datasets: [
                {
                    label: 'Entradas',
                    data: [65, 59, 80, 81],
                    borderColor: '#11998e',
                    backgroundColor: 'rgba(17, 153, 142, 0.1)',
                    tension: 0.4,
                    fill: true
                },
                {
                    label: 'Salidas',
                    data: [28, 48, 40, 19],
                    borderColor: '#cb2d3e',
                    backgroundColor: 'rgba(203, 45, 62, 0.1)',
                    tension: 0.4,
                    fill: true
                }
            ]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: { position: 'top' }
            }
        }
    });

    // 2. Configuración del Gráfico Circular
    const ctxCircular = document.getElementById('graficoCircular').getContext('2d');
    new Chart(ctxCircular, {
        type: 'doughnut', // Tipo dona (como en la imagen)
        data: {
            labels: ['Laptops', 'Monitores', 'Periféricos', 'Accesorios'],
            datasets: [{
                data: [30, 25, 35, 10],
                backgroundColor: [
                    '#2a5298', // Azul
                    '#38ef7d', // Verde
                    '#f5af19', // Naranja
                    '#8E2DE2'  // Morado
                ],
                borderWidth: 0
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: { position: 'bottom' }
            },
            cutout: '70%' // Hace que sea tipo dona
        }
    });

    // 3. Configuración del Gráfico de Barras Horizontal
    const ctxBarras = document.getElementById('graficoBarras').getContext('2d');
    new Chart(ctxBarras, {
        type: 'bar', 
        data: {
            labels: ['Laptops', 'Mouse', 'Monitores', 'Teclados'],
            datasets: [{
                label: 'Unidades vendidas',
                data: [45, 80, 30, 65],
                backgroundColor: '#4A00E0', // Color morado
                borderRadius: 5,            
                barThickness: 20            // Grosor de las barras (más delgado para que se vea bien horizontal)
            }]
        },
        options: {
            indexAxis: 'y', // <--- ESTO HACE QUE LAS BARRAS SEAN HORIZONTALES
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: { display: false } 
            },
            scales: {
                x: {
                    beginAtZero: true,
                    grid: {
                        display: false // Quitamos las líneas verticales de fondo
                    }
                },
                y: {
                    grid: {
                        display: false // Quitamos las líneas horizontales de fondo
                    }
                }
            }
        }
    });

});