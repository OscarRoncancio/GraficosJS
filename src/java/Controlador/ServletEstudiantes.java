/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import DB.Estudiantes;
import Modelo.Estudiante;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author willy
 */
public class ServletEstudiantes extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ArrayList<Estudiante> estudiantes;
    private Estudiantes est;

    public ServletEstudiantes() throws URISyntaxException {
        this.est = new Estudiantes();
        this.estudiantes = new ArrayList<>();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=iso-8859-1");
        PrintWriter out = response.getWriter();

        int curso = 0;
        try {
            curso = Integer.parseInt(request.getParameter("curso").trim());
        } catch (Exception e) {
            curso = 0;
        }
        if (curso != 0) {
            estudiantes = this.est.GetEstudiantesCurso(curso);
            out.println("<table style= cellspacing=1 bgcolor=#0099cc>");
            out.println("<tr>");
            out.println("<td style= rowspan=7 align=center bgcolor=#f8f8f8> ID </td>");
            out.println("<td style= rowspan=7 align=center bgcolor=#f8f8f8> NOMBRE </td>");
            out.println("<td style= rowspan=7 align=center bgcolor=#f8f8f8>APELLIDO</td>");
            out.println("<td style= rowspan=7 align=center bgcolor=#f8f8f8>CURSO</td>");
            out.println("</tr>");
            for (int i = 0; i < estudiantes.size(); i++) {
                Estudiante est1 = estudiantes.get(i);
                out.println("<tr>");
                out.println("<td style= rowspan=7 align=center bgcolor=#f8f8f8>" + est1.getId() + "</td>");
                out.println("<td style= rowspan=7 align=center bgcolor=#f8f8f8>" + est1.getNombre() + "</td>");
                out.println("<td style= rowspan=7 align=center bgcolor=#f8f8f8>" + est1.getApellido() + "</td>");
                out.println("<td style= rowspan=7 align=center bgcolor=#f8f8f8>" + est1.getCurso() + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
        } else {
            out.println("casillas vacias ");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html; charset=iso-8859-1");
        PrintWriter out = response.getWriter();
        int id = 0, curso = 0;
        String nombre = "", apellido = "";
        // Obtengo los datos de la peticion
        try {
            id = Integer.parseInt(request.getParameter("id").trim());
            nombre = request.getParameter("nombre").trim();
            apellido = request.getParameter("apellido").trim();
            curso = Integer.parseInt(request.getParameter("curso").trim());
        } catch (Exception e) {
            id = 0;
            curso = 0;
        }
        // Compruebo que los campos del formulario tienen datos para añadir a la tabla
        if (!nombre.equals("") && !apellido.equals("") && id != 0 && curso != 0) {
            // Creo el objeto persona y lo añado al arrayList
            Estudiante est = new Estudiante(id, nombre, apellido, curso);
            boolean a = this.est.agregar(est);
            estudiantes = this.est.GetEstudiantes();
            if (a) {
                out.println("<table style= cellspacing=1 bgcolor=#0099cc>");
                out.println("<tr>");
                out.println("<td style= rowspan=7 align=center bgcolor=#f8f8f8> ID </td>");
                out.println("<td style= rowspan=7 align=center bgcolor=#f8f8f8> NOMBRE </td>");
                out.println("<td style= rowspan=7 align=center bgcolor=#f8f8f8>APELLIDO</td>");
                out.println("<td style= rowspan=7 align=center bgcolor=#f8f8f8>CURSO</td>");
                out.println("</tr>");
                for (int i = 0; i < estudiantes.size(); i++) {
                    Estudiante est1 = estudiantes.get(i);
                    out.println("<tr>");
                    out.println("<td style= rowspan=7 align=center bgcolor=#f8f8f8>" + est1.getId() + "</td>");
                    out.println("<td style= rowspan=7 align=center bgcolor=#f8f8f8>" + est1.getNombre() + "</td>");
                    out.println("<td style= rowspan=7 align=center bgcolor=#f8f8f8>" + est1.getApellido() + "</td>");
                    out.println("<td style= rowspan=7 align=center bgcolor=#f8f8f8>" + est1.getCurso() + "</td>");
                    out.println("</tr>");
                }
                out.println("</table>");
            } else {
                out.println("error al agregar");
            }
        } else {
            out.println("casillas vacias ");
        }

    }
}
