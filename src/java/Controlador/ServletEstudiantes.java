/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import DB.Estudiantes;
import Modelo.Estudiante;
import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        this.estudiantes = new ArrayList();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (estudiantes == null) {
            this.estudiantes = new ArrayList();
        }
        if (est == null) {
            try {
                this.est = new Estudiantes();
            } catch (URISyntaxException ex) {
                Logger.getLogger(ServletEstudiantes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        int hacer = Integer.parseInt(request.getParameter("hidden").trim());
        if (hacer == 1) { //estudiantes de un curso 
            int curso = 0;
            try {
                curso = Integer.parseInt(request.getParameter("curso").trim());
            } catch (Exception e) {
                curso = 0;
            }
            if (curso != 0) {
                estudiantes = this.est.GetEstudiantesCurso(curso);
                String json = new Gson().toJson(estudiantes);
                System.out.println(json);
                response.setContentType("application/json");
                response.getWriter().write(json);
            } else {
                response.setContentType("application/json");
                response.getWriter().write("false");
            }
        } else if (hacer == 2) {//estudiantes sin curso 
            estudiantes = this.est.GetEstudiantesSinCurso();
            String json = new Gson().toJson(estudiantes);
            System.out.println(json);
            response.setContentType("application/json");
            response.getWriter().write(json);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (estudiantes == null) {
            this.estudiantes = new ArrayList();
        }
        if (est == null) {
            try {
                this.est = new Estudiantes();
            } catch (URISyntaxException ex) {
                Logger.getLogger(ServletEstudiantes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        int id = 0, curso = 0;
        String nombre = "", apellido = "", correoAcudiente = "", nombreAcudiente = "";
        int hacer = Integer.parseInt(request.getParameter("hidden").trim());
        if (hacer == 1) {//nuevo estudiante
            // Obtengo los datos de la peticion
            try {
                id = Integer.parseInt(request.getParameter("id").trim());
                nombre = request.getParameter("nombre").trim();
                apellido = request.getParameter("apellido").trim();
                correoAcudiente = request.getParameter("correoAcudiente").trim();
                nombreAcudiente = request.getParameter("correoAcudiente").trim();
                curso = Integer.parseInt(request.getParameter("curso").trim());
            } catch (Exception e) {
                id = 0;
                curso = 0;
            }
            // Compruebo que los campos del formulario tienen datos para a�adir a la tabla
            if (!nombre.equals("") && !apellido.equals("") && id != 0 && curso != 0) {
                // Creo el objeto persona y lo a�ado al arrayList
                Estudiante est = new Estudiante(id, nombre, apellido, correoAcudiente, nombreAcudiente, curso);
                boolean a = this.est.insert(est);
                estudiantes = (ArrayList) this.est.findAll();
                if (a) {
                    response.setContentType("application/json");
                    response.getWriter().write("true");
                } else {
                    response.setContentType("application/json");
                    response.getWriter().write("false");
                }
            } else {
                response.getWriter().write("casillas vacias");
            }
        } else if (hacer == 2) {//asignar curso
            boolean error = false;
            try {
                id = Integer.parseInt(request.getParameter("id").trim());
                curso = Integer.parseInt(request.getParameter("curso").trim());
            } catch (Exception e) {
                error = true;
            }
            if (!error) { ////error se asigna curso a un estudiante que yaa tiene curso 
                Estudiante e = this.est.buscar(id);
                e.setCurso(curso);
                boolean hecho = this.est.update(e);
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
