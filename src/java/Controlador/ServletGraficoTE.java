/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import DB.ConeccionGrafico;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author Oscar
 */
@WebServlet(name = "ServletGrafico", urlPatterns = {"/ServletGrafico"})
public class ServletGraficoTE extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("image/PNG");
        OutputStream out = response.getOutputStream();

        try {
            ConeccionGrafico conec = new ConeccionGrafico();
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
                DefaultPieDataset data = new DefaultPieDataset();
                while (rs.next()) {
                    data.setValue(rs.getString(2) + ": " + String.valueOf(rs.getInt(1)), rs.getInt(1));
                }

                JFreeChart cha = ChartFactory.createPieChart3D("Notas de los estudiantes en el jardin", data, true, true, true);
                int ancho = 450, alto = 300;
                
                ChartUtilities.writeChartAsPNG(out, cha, ancho, alto);
                ps.close();
                rs.close();
                conec.desconectar();

            } catch (Exception ex) {

            }

        } finally {
            out.close();
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
