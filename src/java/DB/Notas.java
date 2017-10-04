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

/**
 * 
 * @author willy
 */
public class Notas implements IBaseDatos<Nota> {

    private PreparedStatement preparedStmt;
    private Connection connection;
    private String query;

    public Notas() throws URISyntaxException {
        DbConnection c = new DbConnection();
        this.connection = c.getConnection();
    }

    @Override
    public List<Nota> findAll() {
        ArrayList<Nota> mat = new ArrayList();
        this.query = "select * from Nota ";
        try {
            // create the java statement
            Statement st = this.connection.createStatement();
            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(this.query);
            // iterate through the java resultset
            while (rs.next()) {
                int id2 = rs.getInt("id");
                int est= rs.getInt("estudiante");
                String materia = rs.getString("materia");
                int valor = rs.getInt("valor");
                int periodo = rs.getInt("periodo");
                String observacion = rs.getString("observacion");
                Nota e = new Nota(id2, est, materia, valor, periodo, observacion);
                mat.add(e);
            }
            st.close();
        } catch (SQLException ex) {
            // TODO Auto-generated catch block
            System.out.println("Failed to make update!");
            ex.printStackTrace();
        }
        return mat;
    }

    @Override
    public boolean insert(Nota a) {
        boolean r = false;
        try {
            // the mysql insert statement
            query = " insert into Nota (id,estudiante,materia,valor,periodo,observacion)"
                    + " values (?, ?, ?, ?, ?, ?);";
            // create the mysql insert preparedstatement
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, a.getId());
            preparedStmt.setInt(2, a.getEstudiante());
            preparedStmt.setString(3, a.getMateria().trim());
            preparedStmt.setInt(4, a.getValor());
            preparedStmt.setInt(5, a.getPeriodo());
            preparedStmt.setString(6, a.getObservacion().trim());
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
    public boolean update(Nota a) {
        boolean r = false;
        if (buscar(a.getId()) != null) {
            try {
                //Update
                // create the java mysql update preparedstatement
                query = "update Nota set estudiante = ?, materia=?, valor=?, periodo=?, observacion=? where id = ?";
                preparedStmt = connection.prepareStatement(query);
                preparedStmt.setInt(1, a.getEstudiante());
                preparedStmt.setString(2, a.getMateria().trim());
                preparedStmt.setInt(3, a.getValor());
                preparedStmt.setInt(4, a.getPeriodo());
                preparedStmt.setString(5, a.getObservacion());
                preparedStmt.setInt(6, a.getId());
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
    public boolean delete(Nota t) {
        boolean hecho = false;
        try {
            //borramos el curso
            this.query = "delete from Nota where id = " + t.getId();
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

    public Nota buscar(int id) {
        Nota e = null;
        boolean r = false;
        this.query = "select * from Nota where id = " + id;
        try {
            // create the java statement
            Statement st = this.connection.createStatement();
            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(this.query);
            // iterate through the java resultset
            while (rs.next()) {
                int id2 = rs.getInt("id");
                int estudiante = rs.getInt("estudiante");
                String materia = rs.getString("materia");
                int valor = rs.getInt("valor");
                int periodo = rs.getInt("periodo");
                String observacion = rs.getString("observaciones");
                e = new Nota(id, estudiante, materia, valor, periodo, observacion);
            }
            st.close();
        } catch (SQLException ex) {
            // TODO Auto-generated catch block
            System.out.println("Failed to make update!");
            ex.printStackTrace();
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
