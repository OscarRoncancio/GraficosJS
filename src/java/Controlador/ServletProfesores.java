/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import DB.*;
import Modelo.*;
import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author willy
 */
public class ServletProfesores extends HttpServlet {
    private Profesores profesores;

    public ServletProfesores() throws FileNotFoundException, URISyntaxException {
        this.profesores = new Profesores();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int hacer = Integer.parseInt(request.getParameter("hidden").trim());
        if (hacer == 1) {
            ArrayList p = (ArrayList) this.profesores.findAll();
            String json = new Gson().toJson(p);
            response.setContentType("application/json");
            response.getWriter().write(json);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(" post");
        if (profesores == null) {
            try {
                this.profesores = new Profesores();
            } catch (URISyntaxException ex) {
                Logger.getLogger(ServletEstudiantes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        int cedula = 0;
        String nombre = "", apellido = "", usuario="";
        int hacer = Integer.parseInt(request.getParameter("hidden").trim());
        if (hacer == 1) {//nuevo profesor
            // Obtengo los datos de la peticion
            try {
                cedula = Integer.parseInt(request.getParameter("cedula").trim());
                nombre = request.getParameter("nombre").trim();
                apellido = request.getParameter("apellido").trim();
                usuario = request.getParameter("usuario").trim();
            } catch (Exception e) {
                cedula = 0;
            }
            // Compruebo que los campos del formulario tienen datos para a�adir a la tabla
            if (!nombre.equals("") && !apellido.equals("") && cedula != 0) {
                // Creo el objeto persona y lo a�ado al arrayList
               Profesor p = new Profesor(cedula, nombre, apellido, usuario);
                boolean a = this.profesores.insert(p);
                if (a) {
                    response.setContentType("application/json");
                    response.getWriter().write("true");
                } else {
                    response.setContentType("application/json");
                    response.getWriter().write("false");
                }
            } else {
                response.setContentType("application/json");
                response.getWriter().write("false");
            }
        }else if (hacer == 3) {
            boolean error = false;
            try {
                cedula = Integer.parseInt(request.getParameter("id").trim());
            } catch (Exception e) {
                error = true;
            }
            if (!error) { 
                Profesor e = this.profesores.buscar(cedula);
                boolean hecho = this.profesores.delete(e);
                if (hecho) {
                    response.getWriter().write("true");
                } else {
                    response.getWriter().write("false");
                }
            } else {
                response.getWriter().write("casillas vacias");
            }
        }
    }
}
