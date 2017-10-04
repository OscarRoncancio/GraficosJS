/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import DB.DaoUser;
import DB.Estudiantes;
import DB.Usuarios;
import Modelo.Estudiante;
import Modelo.Usuario;
import Modelo.Validador;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author willy
 */
@WebServlet(name = "ServletUsuarios", urlPatterns = {"/ServletUsuarios"})
public class ServletUsuarios extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private Usuarios usu;

    public ServletUsuarios() throws URISyntaxException {
        this.usu = new Usuarios();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 response.setContentType("text/html; charset=iso-8859-1");
        PrintWriter out = response.getWriter();
        HttpSession respuesta = request.getSession(true);
        String email = request.getParameter("usuario");
        String password = request.getParameter("contrasena");
        Pattern p = Pattern.compile("^([0-9a-zA-Z]([_.w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-w]*[0-9a-zA-Z].)+([a-zA-Z]{2,9}.)+[a-zA-Z]{2,3})$");
        Matcher m = p.matcher(email);
        Validador v = new Validador();
        DaoUser d = new DaoUser();
        System.out.println("---------------------------------entro "+email+"    | "+password);
        //campos vacios
        if (email.equals("") || password.equals("")) {
            respuesta.setAttribute("error", "Hay campos vacios");
        } else //No hay campos vacios, veo que la direccion de email sea válida
        if (m.find()) {
            //La direccion de email si es correcta, verifico que la contraseña tambien lo sea
            if (v.isUsernameOrPasswordValid(password)) {
                try {
                    d.conectar();
                    if (d.isAcountExists(email, password)) {
                        //Significa que la cuenta si existe
                        //OBTENGO EL NOMBRE DEL USUARIO Y LO GUARDO EN UNA SESION
                        String NombreUsuario = d.getNameByEmail(email);
                        respuesta.setAttribute("sessionNombre", NombreUsuario);
                        respuesta.setAttribute("sessionEmail", email);
                        Usuario u = this.usu.buscar(email, password);
                        if (u == null) {
                            out.println("<p>error al ingresar</p>");
                        } else {
                            if (u.getTipoUsuario().equals("admin")) {
                                response.sendRedirect("IndexAdmin.html");
                            } else {
                                response.sendRedirect("IndexAdmin.html");
                            }
                        }
                    } else {
                        respuesta.setAttribute("error", "Esta direccion de correo ya fue registrada");
                    }
                    d.desconectar();
                } catch (Exception e) {
                }
            } else {
                out.println("<p>Contrase�a no es valida</p>");
            }
        } else {
            out.println("<p>La direccion de email no es correcta</p>");
        }
        response.sendRedirect("IndexAdmin.html");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
    }
}
