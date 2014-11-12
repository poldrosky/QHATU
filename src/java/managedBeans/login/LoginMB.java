/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans.login;

import beans.connection.SQLiteJDBC;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Omar Ernesto Cabrera Rosero
 */
@ManagedBean(name = "loginMB")
@SessionScoped
public class LoginMB {

    private String idSession = "";//identificador de la session
    private String loginname = "";
    private String password = "";
    private int userId;
    private String userLogin = "";
    private String userName = "";
    private String closeSessionDialog = "";
    FacesContext context;
    SQLiteJDBC sqliteJdbc;
    ApplicationControlMB applicationControlMB;
    StringEncryption stringEncryption = new StringEncryption();
    private boolean autenticado = false;
    private String activeIndexAcoordion1 = "-1";
    private String activeIndexAcoordion2 = "-1";
    private boolean permissionRegistryDataSection = true;
    private boolean permissionAdministrator = false;

    public LoginMB() {
    }

    public String CheckValidUser() {
        /*
         * determinar si el usuario puede acceder al sistema determinando si exite
         * el login, clave y la cuenta esta activa
         */
        closeSessionDialog = "-";
        password = stringEncryption.getStringMessageDigest(password, "SHA-1");
        context = FacesContext.getCurrentInstance();
        sqliteJdbc = (SQLiteJDBC) context.getApplication().evaluateExpressionGet(context, "#{sqliteJdbc}", SQLiteJDBC.class);
        sqliteJdbc.connect();
        ResultSet rs = sqliteJdbc.consult("Select * from users where user_password like '" + password + "' and user_login like '" + loginname + "'");
        try {
            if (rs.next()) {
                ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
                applicationControlMB = (ApplicationControlMB) contexto.getApplicationMap().get("applicationControlMB");
                //determino si el usuario tiene una session alctiva
                if (applicationControlMB.hasLogged(rs.getInt("user_id"))) {
                    //System.out.println("Ingreso rechazado, ya tiene otra session activa");
                    RequestContext.getCurrentInstance().execute("closeSessionDialog.show();");
                    //closeSessionDialog = "closeSessionDialog.show()";//dialog que permite terminar sesion desde otra terminal
                    return "";//no dirigir a ninguna pagina
                } else {
                    userLogin = rs.getString("user_login");
                    userName = rs.getString("user_name");
                    userId = rs.getInt("user_id");
                    return continueLogin();
                }
            } else {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Incorrecto Usuario o Clave");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                password = "";
                return "";
            }

        } catch (SQLException ex) {
            Logger.getLogger(LoginMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    private String continueLogin() {
        /*
         * instanciar todas las variables necesarias para que un usuario inicie una session
         */
        context = FacesContext.getCurrentInstance();
        sqliteJdbc = (SQLiteJDBC) context.getApplication().evaluateExpressionGet(context, "#{connectionJdbcMB}", SQLiteJDBC.class);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("username", loginname);
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

        idSession = session.getId();

        applicationControlMB.addSession(userId, idSession);
        permissionAdministrator = true;

        return inicializeVariables();
    }

    private String inicializeVariables() {        
            autenticado = true;
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto!!", "Se ha ingresado al sistema");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return "homePage";       
    }

    public String closeSessionAndLogin() {
        /*
         * terminar una session iniciada en otra terminal y continuar abriendo una nueva;
         * se usa cuando un mismo usuario intenta loguearse desde dos
         * terminales distintas, 
         */

        applicationControlMB.removeSession(userId);
        return continueLogin();
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

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdSession() {
        return idSession;
    }

    public void setIdSession(String idSession) {
        this.idSession = idSession;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isAutenticado() {
        return autenticado;
    }

    public void setAutenticado(boolean autenticado) {
        this.autenticado = autenticado;
    }

    public String getActiveIndexAcoordion1() {
        return activeIndexAcoordion1;
    }

    public void setActiveIndexAcoordion1(String activeIndexAcoordion1) {
        this.activeIndexAcoordion1 = activeIndexAcoordion1;
    }

    public String getActiveIndexAcoordion2() {
        return activeIndexAcoordion2;
    }

    public void setActiveIndexAcoordion2(String activeIndexAcoordion2) {
        this.activeIndexAcoordion2 = activeIndexAcoordion2;
    }

    public boolean isPermissionRegistryDataSection() {
        return permissionRegistryDataSection;
    }

    public void setPermissionRegistryDataSection(boolean permissionRegistryDataSection) {
        this.permissionRegistryDataSection = permissionRegistryDataSection;
    }

    public boolean isPermissionAdministrator() {
        return permissionAdministrator;
    }

    public void setPermissionAdministrator(boolean permissionAdministrator) {
        this.permissionAdministrator = permissionAdministrator;
    }
    
    
    
    
    
    
    
    
    
}
