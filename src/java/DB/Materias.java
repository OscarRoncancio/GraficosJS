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
public class Materias implements IBaseDatos<Materia> {

    private PreparedStatement preparedStmt;
    private Connection connection;
    private String query;

    public Materias() throws URISyntaxException {
        DbConnection c = new DbConnection();
        this.connection = c.getConnection();
    }

    @Override
    public List<Materia> findAll() {
        ArrayList<Materia> mat = new ArrayList();
        Profesores prof = null;
        Cursos cur=null;
        try {
            prof=new Profesores();
            cur=new Cursos();
        } catch (URISyntaxException ex) {
            Logger.getLogger(Materias.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.query = "select * from Materias ";
        try {
            // create the java statement
            Statement st = this.connection.createStatement();
            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(this.query);
            // iterate through the java resultset
            while (rs.next()) {
                int id2 = rs.getInt("id");
                String nom = rs.getString("nombre");
                Profesor profesor = prof.buscar(rs.getString("profesor"));
                Curso curso = cur.buscar(rs.getInt("curso"));
                int inth = rs.getInt("intHoraria");
                Materia e = new Materia(id2, nom, profesor, curso, inth);
                mat.add(e);
            }
            st.close();
        } catch (SQLException ex) {
            // TODO Auto-generated catch block
            System.out.println("Failed to make update!");
            ex.printStackTrace();
        } catch (URISyntaxException ex) {
            Logger.getLogger(Materias.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mat;
    }

    @Override
    public boolean insert(Materia a) {
        boolean r = false;
        try {
            // the mysql insert statement
            query = " insert into Materia (id,nombre,profesor,curso,intHoraria)"
                    + " values (?, ?, ?, ?, ?);";
            // create the mysql insert preparedstatement
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, a.getId());
            preparedStmt.setString(2, a.getNombre().trim());
            preparedStmt.setInt(3, a.getCurso().getId());
            preparedStmt.setInt(4, a.getIntHoraria());
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
    public boolean update(Materia a) {
        boolean r = false;
        if (buscar(a.getId()) != null) {
            try {
                //Update
                // create the java mysql update preparedstatement
                query = "update Profesor set nombre=?,profesor=?,curso=?,intHoraria=? where id= ?";
                preparedStmt = connection.prepareStatement(query);
                preparedStmt.setString(1, a.getNombre().trim());
                preparedStmt.setString(2, a.getProfesor().getCedula());
                preparedStmt.setInt(3, a.getCurso().getId());
                preparedStmt.setInt(4, a.getIntHoraria());
                preparedStmt.setInt(4, a.getId());
                // execute the java preparedstatement
                preparedStmt.executeUpdate();
                r = true;
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                System.out.println("Failed to make update!");
                e.printStackTrace();
            }
        }
        return r;
    }

    @Override
    public boolean delete(Materia t) {
        boolean hecho = false;
        try {
            //borramos el curso
            this.query = "delete from Materia where id = " + t.getId();
            this.preparedStmt = this.connection.prepareStatement(this.query);
            this.preparedStmt.execute();
            hecho = true;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("Failed to make update!");
            e.printStackTrace();
        }
        return hecho;
    }

    public Materia buscar(int id) {
        
         Profesores prof = null;
        Cursos cur=null;
        try {
            prof=new Profesores();
            cur=new Cursos();
        } catch (URISyntaxException ex) {
            Logger.getLogger(Materias.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Materia e = null;
        boolean r = false;
        this.query = "select * from Materia where id = " + id;
        try {
            // create the java statement
            Statement st = this.connection.createStatement();
            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(this.query);
            // iterate through the java resultset
            while (rs.next()) {
                int id2 = rs.getInt("id");
                String nom = rs.getString("nombre");
                Profesor profesor = prof.buscar(rs.getString("profesor"));
                Curso cursos = cur.buscar(rs.getInt("cursos"));
                int inthor = rs.getInt("intHoraria");
                e = new Materia(id2, nom, profesor, cursos, inthor);
                break;
            }
            st.close();
        } catch (SQLException ex) {
            // TODO Auto-generated catch block
            System.out.println("Failed to make update!");
            ex.printStackTrace();
        } catch (URISyntaxException ex) {
            Logger.getLogger(Materias.class.getName()).log(Level.SEVERE, null, ex);
        }
        return e;
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
