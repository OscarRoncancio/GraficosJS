window.onload = function () {
    var ctx = document.getElementById("graficoTE");
    var myBarChart;

    var parametros = {
        "hidden": "1",
        "id": "123456"
    };
    $.ajax({

        data: parametros,
        url: "ServletGraficoTE",
        type: "GET"
    }).done(function (response) {

        console.log(response);
        src="GraficoJS2.html";
        var inventario = response;
        var arreglo = [];
        arreglo.push([inventario[0], inventario[1], inventario[2], inventario[3], inventario[4]]);

//        $("#btn1").click(function () {
//            $("p").append(" <b>" + arreglo[0] + "</b>");
//        });
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

