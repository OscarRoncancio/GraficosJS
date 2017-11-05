

window.onload = function () {
    var ctx = document.getElementById("popChart1");
    var myBarChart;
    var parametros = {
        "hidden": "1"
    };
    $.ajax({

        data: parametros,
        url: "ServletGraficoPE",
        type: "GET"


    }).done(function (response) {
        console.log(response);
        var inventario = response;
        var arreglo1 = [];
        arreglo1.push([inventario[0].valor, inventario[1].valor, inventario[2].valor]);
//        $("#btn1").click(function () {
//            $("p").append(" <b>" + arreglo1[0] + "</b>");
//        });
        myBarChart = new Chart(ctx, {
            type: 'bar',
            data: {
                borderSkipped: 'left',
                labels: ["español", "matematicas", "matematica"],
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