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
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author Oscar
 */
@WebServlet(name = "ServletGrafico", urlPatterns = {"/ServletGrafico"})
public class ServletGraficoPE extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("image/PNG");
        OutputStream out = response.getOutputStream();
        
        
        try {
            ConeccionGrafico conec = new ConeccionGrafico();
            Connection con = conec.getConexion();
            
            ResultSet rs = null;
            PreparedStatement ps = null;
            
            
            try{    
                
                ps = con.prepareStatement("select Estudiante.id,Estudiante.nombre,Nota.materia,Nota.valor from Nota inner join Estudiante where Estudiante.id = "+ 2 +" &&  Estudiante.id = Nota.id_est");
                rs = ps.executeQuery();
                DefaultCategoryDataset data = new DefaultCategoryDataset();
                while(rs.next()){
                    
                    data.setValue(rs.getInt(4), rs.getString(3), rs.getString(3));
                
                
                }
                    JFreeChart cha = ChartFactory.createBarChart3D("Notas del estudiante segun materia", "Materia", "Nota en la asignatura", data, PlotOrientation.VERTICAL, true, true, true);
                    int ancho =450,alto=300;
                    
                    ChartUtilities.writeChartAsPNG(out, cha,ancho,alto);
                    ps.close();
                    rs.close();
                    conec.desconectar();
                
                
                
            }catch(Exception ex){
                
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
