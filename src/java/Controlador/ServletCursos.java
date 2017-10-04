/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import DB.Cursos;
import DB.Estudiantes;
import Modelo.Curso;
import Modelo.Estudiante;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *      https://blog.openalfa.com/como-leer-y-escribir-ficheros-json-en-java
 * @author willy
 */
@WebServlet(name = "ServletCursos", urlPatterns = {"/ServletCursos"})
public class ServletCursos extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private Cursos cur;

    public ServletCursos() throws URISyntaxException {
        this.cur = new Cursos();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=iso-8859-1");
        PrintWriter out = response.getWriter();

        ArrayList<Curso> cursos =(ArrayList) this.cur.findAll();
        if (cursos.size() != 0) {
            out.println("<table style= cellspacing=1 bgcolor=#0099cc>");
            out.println("<tr>");
            out.println("<td style= rowspan=7 align=center bgcolor=#f8f8f8> ID </td>");
            out.println("<td style= rowspan=7 align=center bgcolor=#f8f8f8> NOMBRE </td>");
            out.println("<td style= rowspan=7 align=center bgcolor=#f8f8f8>PROFESOR</td>");
            out.println("</tr>");
            for (int i = 0; i < cursos.size(); i++) {
                Curso est1 = cursos.get(i);
                out.println("<tr>");
                out.println("<td style= rowspan=7 align=center bgcolor=#f8f8f8>" + est1.getId() + "</td>");
                out.println("<td style= rowspan=7 align=center bgcolor=#f8f8f8>" + est1.getNombre() + "</td>");
                out.println("<td style= rowspan=7 align=center bgcolor=#f8f8f8>" + est1.getProfesor() + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
        } else {
            out.println("  no hay Cursos ");
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html; charset=iso-8859-1");
        PrintWriter out = response.getWriter();
        int hacer = Integer.parseInt(request.getParameter("hidden").trim());
        int id = 0, profesor = 0;
        String nombre = "";

        if (hacer == 1) {// Crear Curso
            // Obtengo los datos de la peticion
            try {
                id = Integer.parseInt(request.getParameter("id").trim());
                nombre = request.getParameter("nombre").trim();
                profesor = Integer.parseInt(request.getParameter("profesor").trim());
            } catch (Exception e) {
                id = 0;
                profesor = 0;
            }
            // Compruebo que los campos del formulario tienen datos para añadir a la tabla
            if (!nombre.equals("") && id != 0 && profesor != 0) {
                // Creo el objeto persona y lo añado al arrayList
                Curso c = new Curso(id, nombre, profesor);
                boolean a = this.cur.insert(c);
                ArrayList<Curso> cursos =(ArrayList) this.cur.findAll();
                if (a) {
                    out.println("<table style= cellspacing=1 bgcolor=#0099cc>");
                    out.println("<tr>");
                    out.println("<td style= rowspan=7 align=center bgcolor=#f8f8f8> ID </td>");
                    out.println("<td style= rowspan=7 align=center bgcolor=#f8f8f8> NOMBRE </td>");
                    out.println("<td style= rowspan=7 align=center bgcolor=#f8f8f8> PROFESOR</td>");
                    out.println("</tr>");
                    for (int i = 0; i < cursos.size(); i++) {
                        Curso est1 = cursos.get(i);
                        out.println("<tr>");
                        out.println("<td style= rowspan=7 align=center bgcolor=#f8f8f8>" + est1.getId() + "</td>");
                        out.println("<td style= rowspan=7 align=center bgcolor=#f8f8f8>" + est1.getNombre() + "</td>");
                        out.println("<td style= rowspan=7 align=center bgcolor=#f8f8f8>" + est1.getProfesor() + "</td>");
                        out.println("</tr>");
                    }
                    out.println("</table>");
                } else {
                    out.println("error al agregar");
                }
            } else {
                out.println("casillas vacias ");
            }
        } else if (hacer == 2) {
            //Eliminar Curso 
            // Obtengo los datos de la peticion
            try {
                id = Integer.parseInt(request.getParameter("id").trim());
            } catch (Exception e) {
                id = 0;
            }
            if (id != 0) {
                Curso c= this.cur.buscar(id);
                boolean hecho = this.cur.delete(c);
                if (hecho) {
                    out.println("el curso se borro satisfactoriamente ");
                } else {
                    out.println(" error al borrar ");
                }
            } else {
                out.println("casillas vacias ");
            }
        }
    }
}
