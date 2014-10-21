/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.login;

import beans.connection.ConnectionJdbcMB;
import java.sql.ResultSet;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Omar Ernesto Cabrera Rosero
 */
@ManagedBean(name = "loginMB")
@SessionScoped
public class LoginMB extends ConnectionJdbcMB{
    private String loginname = "";
    private String password = "";
    private String closeSessionDialog = "";
    FacesContext context;
    ConnectionJdbcMB connectionJdbcMB;
    
    
    public LoginMB() {
    }
    
    public String CheckValidUser() {
        System.out.println("hola");
        closeSessionDialog = "-";
        context = FacesContext.getCurrentInstance();
        connectionJdbcMB = (ConnectionJdbcMB) context.getApplication().evaluateExpressionGet(context, "#{connection}", ConnectionJdbcMB.class);
        connect();
        
        ResultSet result = null;
        result = consult("select * from users");
        System.out.println(result);
        
        
        return null;
    
    }
    
    public String closeSessionAndLogin() {
        /*
         * terminar una session iniciada en otra terminal y continuar abriendo una nueva;
         * se usa cuando un mismo usuario intenta loguearse desde dos
         * terminales distintas, 
         */
        
        return null;
    }

    public String getCloseSessionDialog() {
        return closeSessionDialog;
    }

    public void setCloseSessionDialog(String closeSessionDialog) {
        this.closeSessionDialog = closeSessionDialog;
    }

    public String getLoginname() {
        return loginname;
    }

    public String getPassword() {
        return password;
    }
    
    
    
    
    
    

    

}
