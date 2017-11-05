/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import Modelo.*;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author willy
 */
public class Cursos implements IBaseDatos<Curso> {

    private PreparedStatement preparedStmt;
    private Connection connection;
    private String query;

    public Cursos() throws URISyntaxException {
        DbConnection c = new DbConnection();
        this.connection = c.getConnection();
    }

    @Override
    public boolean insert(Curso a) {
        boolean r = false;
        try {
            // the mysql insert statement
            query = " insert into Cursos (id,nombre,profesor)"
                    + " values (?, ?, ?);";
            // create the mysql insert preparedstatement
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, a.getId());
            preparedStmt.setString(2, a.getNombre().trim());
            preparedStmt.setString(3, a.getProfesor().getCedula());
            // execute the preparedstatement
            preparedStmt.execute();
            System.out.println("You made it, the insertion is ok!");
            r = true;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("Failed to make insertion!");
            e.printStackTrace();
        }
        return r;
    }

    @Override
    public boolean update(Curso a) {
        boolean r = false;
        try {
            if (buscar(a.getId()) != null) {
                try {
                    //Update
                    // create the java mysql update preparedstatement
                    query = "update Cursos set nombre = ?, profesor=? where id = ?";
                    preparedStmt = connection.prepareStatement(query);
                    preparedStmt.setString(1, a.getNombre());
                    preparedStmt.setString(2, a.getProfesor().getCedula());
                    preparedStmt.setInt(3, a.getId());
                    // execute the java preparedstatement
                    preparedStmt.executeUpdate();
                    r = true;
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    System.out.println("Failed to make update!");
                    e.printStackTrace();
                }
            }
        } catch (URISyntaxException ex) {
            Logger.getLogger(Cursos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }

    @Override
    public boolean delete(Curso t) {
        boolean hecho = false;
        try {
            //borramos el curso
            this.query = "delete from Cursos where id = " + t.getId();
            this.preparedStmt = this.connection.prepareStatement(this.query);
            this.preparedStmt.execute();
            System.out.println(" se borro corectamente \n\n ");
            //actualizamos la info de los estudiantes que pertenecian al curso 
            this.query = "update Estudiante set curso = null where curso = " + t.getId();
            this.preparedStmt = connection.prepareStatement(query);
            this.preparedStmt.executeUpdate();
            hecho = true;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("Failed to make update!");
            e.printStackTrace();
        }
        return hecho;
    }

    public Curso buscar(int id) throws URISyntaxException {
        Curso e = null;
        Profesores p = new Profesores();
        this.query = "select * from Curso where id = " + id;
        try {
            // create the java statement
            Statement st = this.connection.createStatement();
            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(this.query);
            // iterate through the java resultset
            while (rs.next()) {
                int id2 = rs.getInt("id");
                String nom = rs.getString("nombre");
                Profesor prof = p.buscar(rs.getString("profesor"));
                e = new Curso(id, nom, prof);
            }
            st.close();
        } catch (SQLException ex) {
            // TODO Auto-generated catch block
            System.out.println("Failed to make update!");
            ex.printStackTrace();
        }
        return e;
    }

    @Override
    public List<Curso> findAll() {
        Profesores p =null;
        try {
            p= new Profesores();
        } catch (URISyntaxException ex) {
            Logger.getLogger(Cursos.class.getName()).log(Level.SEVERE, null, ex);
        }
        ArrayList<Curso> cur2 = new ArrayList();
        this.query = "select * from Cursos ";
        try {
            // create the java statement
            Statement st = this.connection.createStatement();
            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(this.query);
            // iterate through the java resultset
            while (rs.next()) {
                int id2 = rs.getInt("id");
                String nom = rs.getString("nombre");
                Profesor prof = p.buscar(rs.getString("profesor"));
                Curso e = new Curso(id2, nom, prof);
                cur2.add(e);
            }
            st.close();
            for (int i = 0; i < cur2.size(); i++) {
                Curso min = cur2.get(i);
                for (int j = 0; j < cur2.size(); j++) {
                    if (cur2.get(j).getId() < min.getId()) {
                        cur2.set(i, cur2.get(j));
                        cur2.set(j, min);
                        min = cur2.get(i);
                    }
                }
            }
        } catch (SQLException ex) {
            // TODO Auto-generated catch block
            System.out.println("Failed to make update!");
            ex.printStackTrace();
        }
        return cur2;
    }

    public void disconect() throws SQLException {
        this.connection.close();
    }

    public PreparedStatement getPreparedStmt() {
        return preparedStmt;
    }

    public Connection getConnection() {
        return connection;
    }

}
