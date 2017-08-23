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

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
     private Estudiantes estudiantes;

    public ServletEstudiantes() throws FileNotFoundException, URISyntaxException {
        this.estudiantes = new Estudiantes();
    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         int id=Integer.parseInt(request.getParameter("id"));
         Estudiante e = this.estudiantes.buscar(id);
        request.setAttribute("estudiante",e);
        RequestDispatcher dispacher = request.getRequestDispatcher("index.html");
        dispacher.forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        if (request.getParameter("nombre").length() != 0) {
            int id=Integer.parseInt(request.getParameter("id"));
            String nombre = request.getParameter("nombre").trim();
            String apellido = request.getParameter("apellido").trim();
            Estudiante e = new Estudiante(id,nombre, apellido);
            boolean n = this.estudiantes.agregar(e);
            if (n) {
                request.setAttribute("Mensaje", "Se agrego correctamente ");
            } else {
                request.setAttribute("Mensaje", "id repetido intente nuevamente ");
            }
        } else {
            request.setAttribute("Mensaje", "casillas incompletas intente nuevamente ");
        }

        RequestDispatcher dispacher = request.getRequestDispatcher("NuevaPersona.jsp");
        dispacher.forward(request, response);
    }

}
