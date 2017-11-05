/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import DB.ConeccionGrafico;
import Modelo.Nota;
import com.google.gson.Gson;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author Oscar
 */
@WebServlet(name = "ServletGraficoPE", urlPatterns = {"/ServletGraficoPE"})
public class ServletGraficoPE extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ArrayList<Nota> notas = new ArrayList();
        ConeccionGrafico conec = new ConeccionGrafico();
        Connection con = conec.getConexion();
        Nota n = null;
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {

            ps = con.prepareStatement("select Estudiante.id,Estudiante.nombre,Nota.materia,Nota.valor from Nota inner join Estudiante where Estudiante.id = 2  &&  Estudiante.id = Nota.id_est");
            rs = ps.executeQuery();

            while (rs.next()) {

                n = new Nota(rs.getString(3), rs.getInt(4));
                notas.add(n);

            }

            int hacer = Integer.parseInt(request.getParameter("hidden").trim());
            if (hacer == 1) {
                notas = (ArrayList) notas;
                String json = new Gson().toJson(notas);
                response.setContentType("application/json");
                response.getWriter().write(json);
            }
            ps.close();
            rs.close();
            conec.desconectar();

        } catch (Exception ex) {
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
