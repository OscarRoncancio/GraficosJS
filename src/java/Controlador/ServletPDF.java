/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ServletPDF", urlPatterns = {"/ServletPDF"})
public class ServletPDF extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/pdf");
        OutputStream out = response.getOutputStream();

        try {

            try {

                Connection con = null;
                Statement st = null;
                ResultSet rs = null;

                Class.forName("com.mysql.jdbc.Driver");
                con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "root");
                st = con.createStatement();
                rs = st.executeQuery("SELECT * FROM Estudiante");
                if (con != null) {

                    try {
                        Document documento = new Document();
                        PdfWriter.getInstance(documento, out);
                        
                        documento.open();

                        Paragraph p1 = new Paragraph();
                        Font ft = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, BaseColor.BLUE);
                        p1.add(new Phrase("Boletin", ft));
                        p1.setAlignment(Element.ALIGN_CENTER);
                        documento.add(p1);
                        PdfPTable tabla = new PdfPTable(6);
                        PdfPCell celda1 = new PdfPCell(new Paragraph("ID",FontFactory.getFont("Arial", 12,Font.BOLD,BaseColor.BLACK)));
                        PdfPCell celda2 = new PdfPCell(new Paragraph("Nombre",FontFactory.getFont("Arial", 12,Font.BOLD,BaseColor.BLACK)));
                        PdfPCell celda3 = new PdfPCell(new Paragraph("Apellido",FontFactory.getFont("Arial", 12,Font.BOLD,BaseColor.BLACK)));
                        PdfPCell celda4 = new PdfPCell(new Paragraph("Correo acudiente",FontFactory.getFont("Arial", 12,Font.BOLD,BaseColor.BLACK)));
                        PdfPCell celda5 = new PdfPCell(new Paragraph("Nombre acudiente",FontFactory.getFont("Arial", 12,Font.BOLD,BaseColor.BLACK)));
                        PdfPCell celda6 = new PdfPCell(new Paragraph("Curso",FontFactory.getFont("Arial", 12,Font.BOLD,BaseColor.BLACK)));
                        
                        tabla.addCell(celda1);
                        tabla.addCell(celda2);
                        tabla.addCell(celda3);
                        tabla.addCell(celda4);
                        tabla.addCell(celda5);
                        tabla.addCell(celda6);
                        
                        while(rs.next()){
                            tabla.addCell(rs.getString(1));
                            tabla.addCell(rs.getString(2));
                            tabla.addCell(rs.getString(3));
                            tabla.addCell(rs.getString(4));
                            tabla.addCell(rs.getString(5));
                            tabla.addCell(rs.getString(6));
                        }
                        documento.add(tabla);
                        documento.close();

                    } catch (Exception ex) {
                        ex.getMessage();
                    }

                }

            } catch (Exception ex) {
                ex.getMessage();
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
