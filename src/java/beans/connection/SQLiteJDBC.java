/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.swing.JOptionPane;

/**
 *
 * @author Omar Ernesto Cabrera Rosero
 */
@ManagedBean(name = "sqliteJdbc")
@SessionScoped
public class SQLiteJDBC {

    Connection connection = null;
    Statement query;

    public SQLiteJDBC() {
    }

    public void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        try {
            ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance()
                    .getExternalContext().getContext();
            String pathDB = ctx.getRealPath("/") + "Resources/db/qhatu.db";
            connection = DriverManager.getConnection("jdbc:sqlite:" + pathDB);
            query = connection.createStatement();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }
    
    public boolean connectToDB() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        try {
            ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance()
                    .getExternalContext().getContext();
            String pathDB = ctx.getRealPath("/") + "Resources/db/qhatu.db";
            connection = DriverManager.getConnection("jdbc:sqlite:" + pathDB);
            query = connection.createStatement();
            return true;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
        return false;
    }
    
    
    public ResultSet consult(String sql) {
        connect();
        ResultSet result = null;
        try {
            result = query.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("Mensaje:" + e.getMessage());
            System.out.println("Estado:" + e.getSQLState());
            System.out.println("Codigo del error:" + e.getErrorCode());
            JOptionPane.showMessageDialog(null, "" + e.getMessage());
        }
        return result;
    }
}
