            window.onload = function () {
                var parametros = {
                    "hidden": "3"
                };
                $.ajax({
                    data: parametros,
                    url: "ServletEstudiantes",
                    type: "GET"

                }).done(function (response) {
                    console.log(response);
                    var inventario = response;
                    $("#informaciontablas").show();
                    $("#tabla1").show();
                    $("#tabla1").empty();
                    $("#tabla1").append(
                            '<tr>' +
                            '<th style="width:100px">ID</th>' +
                            '<th style="width:100px">Nombre</th>' +
                            '<th style="width:100px">Apellido</th>' +
                            '<th style="width:100px">Correo Acudiente</th>' +
                            '<th style="width:100px">Nombre Acudiente</th>' +
                            '<th style="width:100px">Curso</th>' +
                            '<th style="width:100px">Nota</th>' +
                            '<th style="width:100px">Gráfico</th>' +
                            '<th style="width:100px">PDF</th>' +
                            '<th style="width:100px">Editar</th>' +
                            '<th style="width:100px">Borrar</th>' +
                            '</tr>'
                            );

                    for (var i in inventario) {
                        $("#tabla1").append(
                                '<tr>' +
                                '<td style="width:100px">' + inventario[i].id + '</td>' +
                                '<td style="width:100px">' + inventario[i].nombre + '</td>' +
                                '<td style="width:100px">' + inventario[i].apellido + '</td>' +
                                '<td style="width:100px">' + inventario[i].correoAcudiente + '</td>' +
                                '<td style="width:100px">' + inventario[i].nombreAcudiente + '</td>' +
                                '<td style="width:100px">' + inventario[i].curso + '</td>' +
                                '<td style="width:100px"> <input type = "number" id = "nota-' + inventario[i].id + '" /></td>' +
                                '<td style="width:100px">' +
                                '<button class="button" onclick="GPE(' + inventario[i].id + ')" data-type="zoomin">graf</button>' +
                                '</td>' +
                                '<td style="width:100px"> <button id = "bt-' + inventario[i].id + '">PDF</button></td>' +
                                '<td style="width:100px"> <button id = "bt-' + inventario[i].id + '">Editar</button></td>' +
                                '<td style="width:100px"> <button id = "bt-' + inventario[i].id + '">Borrar</button></td>' +
                                '</tr>'
                                );
                    }

                });
            };


