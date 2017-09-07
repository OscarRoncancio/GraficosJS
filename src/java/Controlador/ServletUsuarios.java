/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import DB.Estudiantes;
import DB.Usuarios;
import Modelo.Estudiante;
import Modelo.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author willy
 */
public class ServletUsuarios {

    private static final long serialVersionUID = 1L;
    private Usuarios usu;

    public ServletUsuarios() throws URISyntaxException {
        this.usu = new Usuarios();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=iso-8859-1");
        PrintWriter out = response.getWriter();
        String usuario = "", contraseña = "";
        // Obtengo los datos de la peticion
        usuario = request.getParameter("usuario").trim();
        contraseña = request.getParameter("contraseña").trim();
        if (!usuario.equals("") && !contraseña.equals("")) {
            Usuario u = this.usu.buscar(usuario, contraseña);
            if (usuario == null) {
                out.println("<p>error al ingresar</p>");
            } else {
                out.println("<p><a href=\"inicioadmin.jsp\">continuar</a></p>");
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=iso-8859-1");
        PrintWriter out = response.getWriter();
        String usuario = "", contraseña = "";
        // Obtengo los datos de la peticion
        usuario = request.getParameter("usuario").trim();
        contraseña = request.getParameter("contraseña").trim();
        if (!usuario.equals("") && !contraseña.equals("")) {
            Usuario u = this.usu.buscar(usuario, contraseña);
            if (usuario == null) {
                out.println("<p>error al ingresar</p>");
            } else {
                out.println("<p><a href=\"index.html\">continuar</a></p>");
            }
        }
    }
}
