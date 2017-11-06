/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import Modelo.*;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author willy
 */
public class Estudiantes implements IBaseDatos<Estudiante> {

    private PreparedStatement preparedStmt;
    private Connection connection;
    private String query;

    public Estudiantes() throws URISyntaxException {
        DbConnection c = new DbConnection();
        this.connection = c.getConnection();
    }

    @Override
    public List<Estudiante> findAll() {
        ArrayList<Estudiante> est = new ArrayList();
        Cursos cursos=null;
        try {
            cursos= new Cursos();
        } catch (URISyntaxException ex) {
            Logger.getLogger(Estudiantes.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.query = "select * from Estudiante ";
        try {
            // create the java statement
            Statement st = this.connection.createStatement();
            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(this.query);
            // iterate through the java resultset
            while (rs.next()) {
                String id2 = rs.getString("id");
                String nom = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String correoacu = rs.getString("correoAcudiente");
                String nombreacu = rs.getString("nombreAcudiente");
                Curso curso =cursos.buscar(rs.getInt("curso"));
                Estudiante e = new Estudiante(id2, nom, apellido, correoacu, nombreacu, curso);
                est.add(e);
            }
            st.close();
        } catch (SQLException ex) {
            // TODO Auto-generated catch block
            System.out.println("Failed to make update!");
            ex.printStackTrace();
        } catch (URISyntaxException ex) {
            Logger.getLogger(Estudiantes.class.getName()).log(Level.SEVERE, null, ex);
        }
        return est;
    }

    @Override
    public boolean insert(Estudiante a) {
        boolean r = false;
        try {
            // the mysql insert statement
            query = " insert into Estudiante (id,nombre,apellido,correoAcudiente,nombreAcudiente,curso)"
                    + " values (?, ?, ?, ?, ?, ?);";

            // create the mysql insert preparedstatement
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, a.getId());
            preparedStmt.setString(2, a.getNombre().trim());
            preparedStmt.setString(3, a.getApellido().trim());
            preparedStmt.setString(4, a.getCorreoAcudiente().trim());
            preparedStmt.setString(5, a.getNombreAcudiente().trim());
            preparedStmt.setInt(6, a.getCurso().getId());
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
    public boolean update(Estudiante a) {
        boolean r = false;
        if (buscar(a.getId()) != null) {
            try {
                //Update
                // create the java mysql update preparedstatement
                query = "update Estudiante set nombre = ?, apellido=?,correoAcudiente=?, nombreAcudiente=?,curso=? where id = \"?\"";
                preparedStmt = connection.prepareStatement(query);
                preparedStmt.setString(1, a.getNombre().trim());
                preparedStmt.setString(2, a.getApellido().trim());
                preparedStmt.setString(3, a.getCorreoAcudiente().trim());
                preparedStmt.setString(4, a.getNombreAcudiente().trim());
                preparedStmt.setInt(5, a.getCurso().getId());
                preparedStmt.setString(6, a.getId().trim());
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
    public boolean delete(Estudiante t) {
        boolean hecho = false;
        try {
            //borramos el curso
            this.query = "delete from Estudiante where id = \"" + t.getId()+"\"";
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

    public Estudiante buscar(String id) {
        Estudiante e = null;
         Cursos cursos=null;
        try {
            cursos= new Cursos();
        } catch (URISyntaxException ex) {
            Logger.getLogger(Estudiantes.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.query = "select * from Estudiante where id = \"" + id+"\"";
        try {
            // create the java statement
            Statement st = this.connection.createStatement();
            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(this.query);
            // iterate through the java resultset
            while (rs.next()) {
                String id2 = rs.getString("id");
                String nom = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String correoacu = rs.getString("correoAcudiente");
                String nombreacu = rs.getString("nombreAcudiente");
                Curso curso =cursos.buscar(rs.getInt("curso"));
                e = new Estudiante(id2, nom, apellido, correoacu, nombreacu, curso);
            }
            st.close();
        } catch (SQLException ex) {
            // TODO Auto-generated catch block
            System.out.println("Failed to make update!");
            ex.printStackTrace();
        } catch (URISyntaxException ex) {
            Logger.getLogger(Estudiantes.class.getName()).log(Level.SEVERE, null, ex);
        }
        return e;
    }
    public ArrayList<Estudiante> buscarIdEstudiante(String id)  {
        ArrayList<Estudiante> est = new ArrayList();

        this.query = "select correoAcudiente from Estudiante where id = '" + id+"'";
        try {
            // create the java statement
            Statement st = this.connection.createStatement();
            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(this.query);
            // iterate through the java resultset
            while (rs.next()) {
                String correoacu = rs.getString("correoAcudiente");
                Estudiante e = new Estudiante(correoacu);
                est.add(e);
            }
            st.close();
        } catch (SQLException ex) {
            // TODO Auto-generated catch block
            System.out.println("Failed to make update!");
            ex.printStackTrace();
        }
        return est;
    }

    public ArrayList<Estudiante> GetEstudiantesCurso(int c) {
        ArrayList<Estudiante> est = new ArrayList();
         Cursos cursos=null;
        try {
            cursos= new Cursos();
        } catch (URISyntaxException ex) {
            Logger.getLogger(Estudiantes.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.query = "select * from Estudiante where curso = " + c;
        try {
            // create the java statement
            Statement st = this.connection.createStatement();
            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(this.query);
            // iterate through the java resultset
            while (rs.next()) {
                String id2 = rs.getString("id");
                String nom = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String correoacu = rs.getString("correoAcudiente");
                String nombreacu = rs.getString("nombreAcudiente");
                Curso curso = cursos.buscar(rs.getInt("curso"));
                Estudiante e = new Estudiante(id2, nom, apellido, correoacu, nombreacu, curso);
                est.add(e);
            }
            st.close();
        } catch (SQLException ex) {
            // TODO Auto-generated catch block
            System.out.println("Failed to make update!");
            ex.printStackTrace();
        } catch (URISyntaxException ex) {
            Logger.getLogger(Estudiantes.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (int i = 0; i < est.size(); i++) {
            Estudiante min = est.get(i);
            for (int j = 0; j < est.size(); j++) {
                if (est.get(j).getId().compareTo(min.getId()) < 0 ) {
                    est.set(i, est.get(j));
                    est.set(j, min);
                    min = est.get(i);
                }
            }
        }
        return est;
    }

    public ArrayList<Estudiante> GetEstudiantesSinCurso() {
        ArrayList<Estudiante> est = new ArrayList();
         Cursos cursos=null;
        try {
            cursos= new Cursos();
        } catch (URISyntaxException ex) {
            Logger.getLogger(Estudiantes.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.query = "select * from Estudiante where curso = null";
        try {
            // create the java statement
            Statement st = this.connection.createStatement();
            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(this.query);
            // iterate through the java resultset
            while (rs.next()) {
                String id2 = rs.getString("id");
                String nom = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String correoacu = rs.getString("correoAcudiente");
                String nombreacu = rs.getString("nombreAcudiente");
                Curso curso = cursos.buscar(rs.getInt("curso"));
                Estudiante e = new Estudiante(id2, nom, apellido, correoacu, nombreacu, curso);
                est.add(e);
            }
            st.close();
        } catch (SQLException ex) {
            // TODO Auto-generated catch block
            System.out.println("Failed to make update!");
            ex.printStackTrace();
        } catch (URISyntaxException ex) {
            Logger.getLogger(Estudiantes.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (int i = 0; i < est.size(); i++) {
            Estudiante min = est.get(i);
            for (int j = 0; j < est.size(); j++) {
                if (est.get(j).getId().compareTo(min.getId()) < 0) {
                    est.set(i, est.get(j));
                    est.set(j, min);
                    min = est.get(i);
                }
            }
        }
        return est;
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
