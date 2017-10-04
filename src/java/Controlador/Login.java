/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import DB.DaoUser;
import Modelo.Validador;
import java.io.IOException;
import java.util.regex.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author 
 */
public class Login extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /* En este proyecto; este servlet no recibe ni debe recibir nada por GET, 
         * asi que si se lleva a entrar al servelt
         * usando el metodo GET solamente redireccion al index.jsp
         */
        response.sendRedirect("index.jsp");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession respuesta = request.getSession(true);
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        Pattern p = Pattern.compile("^([0-9a-zA-Z]([_.w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-w]*[0-9a-zA-Z].)+([a-zA-Z]{2,9}.)+[a-zA-Z]{2,3})$");
        Matcher m = p.matcher(email);
        Validador v = new Validador();
        DaoUser d = new DaoUser();

        //campos vacios
        if (email.isEmpty() || password.isEmpty()) {
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
                        } else {
                            respuesta.setAttribute("error", "Esta direccion de correo ya fue registrada");
                        }
                        d.desconectar();
                    } catch (Exception e) {
                    }
                } else {
                    respuesta.setAttribute("error", "Contrase�a no es valida");
                }
            } else {
                respuesta.setAttribute("error", "La direccion de email no es correcta");
            }
        response.sendRedirect("login.jsp");
    }
}
