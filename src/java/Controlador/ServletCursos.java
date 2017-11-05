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
 * https://blog.openalfa.com/como-leer-y-escribir-ficheros-json-en-java
 *
 * @author willy
 */
@WebServlet(name = "ServletCursos", urlPatterns = {"/ServletCursos"})
public class ServletCursos extends HttpServlet {

    private Cursos cur;

    public ServletCursos() throws URISyntaxException {
        this.cur = new Cursos();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (cur == null) {
            try {
                this.cur = new Cursos();
            } catch (URISyntaxException ex) {
                Logger.getLogger(ServletCursos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        int hacer = Integer.parseInt(request.getParameter("hidden").trim());
        if (hacer == 1) { // todoslos cursos
            ArrayList p = (ArrayList) this.cur.findAll();
            String json = new Gson().toJson(p);
            response.setContentType("application/json");
            response.getWriter().write(json);
        }if(hacer == 2){ // buscar 
            int id = 0;
             try {
                id = Integer.parseInt(request.getParameter("id").trim());
            } catch (Exception e) {
                id=0;
            }
            Curso c=null;
            try {
                c = this.cur.buscar(id);
            } catch (URISyntaxException ex) {
                Logger.getLogger(ServletCursos.class.getName()).log(Level.SEVERE, null, ex);
            }
            String json = new Gson().toJson(c);
            response.setContentType("application/json");
            response.getWriter().write(json);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Profesores prof = null;
        try {
            prof = new Profesores();
        } catch (URISyntaxException ex) {
            Logger.getLogger(ServletCursos.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (cur == null) {
            try {
                this.cur = new Cursos();
            } catch (URISyntaxException ex) {
                Logger.getLogger(ServletEstudiantes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        int id = 0;
        String nombre = " ",profesor = "";
        int hacer = Integer.parseInt(request.getParameter("hidden").trim());
        if (hacer == 1) {//nuevo curso
            // Obtengo los datos de la peticion
            try {
                nombre = request.getParameter("nombre").trim();
                id = Integer.parseInt(request.getParameter("id").trim());
                profesor = request.getParameter("profesor").trim();
            } catch (Exception e) {
                id = 0;
                profesor = "";
            }
            // Compruebo que los campos del formulario tienen datos para anadir a la tabla
            if (!nombre.equals("") && profesor.length() != 0 && id != 0) {
                if (prof.buscar(profesor) != null) {
                    Curso c = new Curso(id, nombre, prof.buscar(profesor));
                    boolean a = this.cur.insert(c);
                    if (a) {
                        response.setContentType("application/json");
                        response.getWriter().write("Curso creado correctamente");
                    } else {
                        response.setContentType("application/json");
                        response.getWriter().write("no se pudo crear el curso ");
                    }
                } else {
                    response.setContentType("application/json");
                    response.getWriter().write("no existe el profesor ");
                }
            } else {
                response.setContentType("application/json");
                response.getWriter().write("error Casillas Vacias");
            }
        } else if (hacer == 3) {//eliminar
            boolean error = false;
            try {
                id = Integer.parseInt(request.getParameter("id").trim());
            } catch (Exception e) {
                error = true;
            }
            if (!error) {
                Curso e=null;
                try {
                    e = this.cur.buscar(id);
                } catch (URISyntaxException ex) {
                    Logger.getLogger(ServletCursos.class.getName()).log(Level.SEVERE, null, ex);
                }
                boolean hecho = this.cur.delete(e);
                if (hecho) {
                    response.getWriter().write("curso borrado correctamente");
                } else {
                    response.getWriter().write("error al borrar");
                }
            } else {
                response.getWriter().write("casillas vacias");
            }
        }
    }
}
