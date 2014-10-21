/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.connection;

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

@ManagedBean(name = "connection")
@SessionScoped
public class ConnectionJdbcMB {

    public String pathDB;
    ConnectionJdbcMB connection;
    Statement query;

    public ConnectionJdbcMB() {
        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance()
                .getExternalContext().getContext();
        pathDB = ctx.getRealPath("/") + "Resources/db/qhatu";
    }
        
    public void connect(){
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        try {
            connection = (ConnectionJdbcMB) DriverManager.getConnection("jdbc:sqlite:" + pathDB);            
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public ResultSet consult(String sql){
        connect();
        ResultSet result = null;
        try {
            result = query.executeQuery(sql);
        } catch (SQLException e) {
                System.out.println("Mensaje:"+e.getMessage());
                System.out.println("Estado:"+e.getSQLState());
                System.out.println("Codigo del error:"+e.getErrorCode());
                JOptionPane.showMessageDialog(null, ""+e.getMessage());
            }
        return result;
    }
}

