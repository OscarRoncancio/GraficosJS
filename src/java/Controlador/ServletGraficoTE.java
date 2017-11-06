/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import DB.Coneccion;
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
@WebServlet(name = "ServletGraficoTE", urlPatterns = {"/ServletGraficoTE"})
public class ServletGraficoTE extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ArrayList<Integer> n = new ArrayList();
        Coneccion conec = new Coneccion();
        Connection con = conec.getConexion();

        ResultSet rs = null;
        PreparedStatement ps = null;

        try {

            ps = con.prepareStatement("select Count(valor),\n"
                    + "case \n"
                    + "when valor = 50 then 'Estudiantes con 50'\n"
                    + "when valor = 40 then 'Estudiantes con 40'\n"
                    + "when valor = 30 then 'Estudiantes con 30'\n"
                    + "when valor = 20 then 'Estudiantes con 20'\n"
                    + "when valor = 10 then 'Estudiantes con 10'\n"
                    + "else 'ninguno'\n"
                    + "END as notas\n"
                    + "from Nota\n"
                    + "group by notas");
            rs = ps.executeQuery();
            
            while (rs.next()) {
                
                n.add(rs.getInt(1));

            }
            
            int hacer = Integer.parseInt(request.getParameter("hidden").trim());
            if (hacer == 1) {
                String json = new Gson().toJson(n);
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
