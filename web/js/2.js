

window.onload = function () {
    var ctx = document.getElementById("popChart1");
    var myBarChart;
    var parametros = {
        "hidden": "1",
        "id": "2"
    };
    $.ajax({

        data: parametros,
        url: "ServletGraficoPE",
        type: "GET"

    }).done(function (response) {
        console.log(response);
        var inventario = response;
        var arreglo=[];
        var arreglo1 = [];
        var arreglo2 = [];
        var arreglo3 = [];
        for (var i = 0; i < inventario.length; i++) {
            arreglo.push(inventario[i].valor);
            arreglo2.push(inventario[i].materia);
        }
        arreglo3.push(arreglo2);
        arreglo1.push(arreglo);
//        $("#btn1").click(function () {
//            $("p").append(" <b>" + arreglo1[0] + "</b>");
//        });
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