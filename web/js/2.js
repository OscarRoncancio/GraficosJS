
function GPE(id) {
    limpiar();
    var ctx = document.getElementById("popChart1");
    var myBarChart;
    var parametros = {
        "hidden": "1",
        "id": id
    };
    $.ajax({
        data: parametros,
        url: "ServletGraficoPE",
        type: "GET"

    }).done(function (response) {
        console.log(response);
        var inventario = response;
        var arreglo = [];
        var arreglo1 = [];
        var arreglo2 = [];
        var arreglo3 = [];
        for (var i in inventario) {
            arreglo.push(inventario[i].valor);
            arreglo2.push(inventario[i].materia);
        }
        arreglo3.push(arreglo2);
        arreglo1.push(arreglo);
        myBarChart = new Chart(ctx, {
            type: 'bar',
            data: {
                borderSkipped: 'left',
                labels: arreglo3[0],
                datasets: [{
                        label: 'notas',
                        data: arreglo1[0],
                        backgroundColor: [
                            'rgba(0, 99, 132, 0.6)',
                            'rgba(30, 99, 132, 0.6)',
                            'rgba(60, 99, 132, 0.6)',
                            'rgba(90, 99, 132, 0.6)',
                            'rgba(120, 99, 132, 0.6)'
                        ]

                    }]
            },
            options: {
                scales: {
                    yAxes: [{
                            ticks: {
                                beginAtZero: true
                            }
                        }]
                }
            }
        });
    });
}
;
function GTE() {
    limpiar();
    var ctx = document.getElementById("popChart1");
    var myBarChart;
    var parametros = {
        "hidden": "1"
    };
    $.ajax({

        data: parametros,
        url: "ServletGraficoTE",
        type: "GET"
    }).done(function (response) {

        console.log(response);
        src = "GraficoJS2.html";
        var inventario = response;
        var arreglo = [];
        arreglo.push([inventario[0], inventario[1], inventario[2], inventario[3], inventario[4]]);
        myBarChart = new Chart(ctx, {
            type: 'doughnut',
            data: {

                labels: ["1.0", "2.0", "3.0", "4.0", "5.0"],
                datasets: [{
                        label: 'notas',
                        data: arreglo[0],
                        backgroundColor: [
                            'rgba(0, 99, 132, 0.6)',
                            'rgba(30, 99, 132, 0.6)',
                            'rgba(60, 99, 132, 0.6)',
                            'rgba(90, 99, 132, 0.6)',
                            'rgba(120, 99, 132, 0.6)'
                        ]

                    }]
            },
            options: {

            }
        });
    }
    );
}
;
function limpiar() {
    $("#grafica").empty();
    $("#grafica").append(
            '<div  >' +
            '<h3>Versión en Zoom in</h3>' +
            '<canvas id="popChart1" width="700" height="300"></canvas><br/>' +
            '<br/>' +
            
            '</div>');
}
