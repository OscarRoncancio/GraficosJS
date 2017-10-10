/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import DB.*;
import Modelo.*;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author willy
 */
@WebServlet(name = "ServletNotas", urlPatterns = {"/ServletNotas"})
public class ServletNotas extends HttpServlet {

    private Notas notas;

    public ServletNotas() throws URISyntaxException {
        this.notas = new Notas();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (this.notas == null) {
            try {
                this.notas = new Notas();
            } catch (URISyntaxException ex) {
                Logger.getLogger(ServletCursos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        int hacer = Integer.parseInt(request.getParameter("hidden").trim());
        if (hacer == 1) { // notas de un estudiante

        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (this.notas == null) {
            try {
                this.notas = new Notas();
            } catch (URISyntaxException ex) {
                Logger.getLogger(ServletCursos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        Materias mat = null;
        Estudiantes est = null;
        try {
            mat = new Materias();
            est = new Estudiantes();
        } catch (URISyntaxException ex) {
            Logger.getLogger(ServletNotas.class.getName()).log(Level.SEVERE, null, ex);
        }

        int id = 0, estudiante = 0, valor = 0, periodo = 0;
        String materia = "", observacion = "";
        int hacer = Integer.parseInt(request.getParameter("hidden").trim());
        if (hacer == 1) { // ingresar nota
            // Obtengo los datos de la peticion
            try {
                materia = request.getParameter("materia").trim();
                id = Integer.parseInt(request.getParameter("id").trim());
                observacion = request.getParameter("observacion").trim();
                estudiante = Integer.parseInt(request.getParameter("estudiante").trim());
                valor = Integer.parseInt(request.getParameter("valor").trim());
                periodo = Integer.parseInt(request.getParameter("periodo").trim());
            } catch (Exception e) {
                id = 0;
                estudiante = 0;
                valor = 0;
                periodo = 0;
                materia = "";
                observacion = "";
            }

            if (id != 0 && estudiante != 0 && valor != 0 && periodo != 0 && materia.length() != 0 && observacion.length() != 0) {
                Estudiante e = est.buscar(estudiante);
                Materia m = null; //mat.buscar(materia);
                if (e != null && m != null) {
                    Nota a = new Nota(id, estudiante, materia, valor, periodo, observacion);
                    boolean r = this.notas.insert(a);
                    if (r) {
                        response.setContentType("application/json");
                        response.getWriter().write("se ingreso la nota correctamente");
                    } else {
                        response.setContentType("application/json");
                        response.getWriter().write("error no se pudo guardar la nota");
                    }
                } else {
                    response.setContentType("application/json");
                    response.getWriter().write("error estudiante o materia no existe");
                }
            } else {
                response.setContentType("application/json");
                response.getWriter().write("error Casillas Vacias");
            }

        }

    }

}
