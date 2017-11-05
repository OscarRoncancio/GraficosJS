function Popup(id) {
    var parametros = {
        "id": id
    };
    $.ajax({
        data: parametros,
        url: "ServletGraficoPE",
        type: "GET"

    }).done(function (response) {
        console.log(response);
        alert("asdasdasdasdasdasd");
    });
}
;