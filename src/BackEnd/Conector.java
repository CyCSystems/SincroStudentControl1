/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BackEnd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Luis Emmanuel Rodas Camposeco
 */
public class Conector {
    public static final String dv = "com.mysql.jdbc.Driver";
    public static final String db = "jdbc:mysql://localhost/cycsyste_studentcontrol";
    private static final String dbNube = "jdbc:mysql://cycsystem.com/cycsyste_LaColina";
    public Connection cn;
    public Statement st;
    
    public Conector(String Tipo){
        if("Local".equals(Tipo)){
            try{
                Class.forName(dv);
                DriverManager.setLoginTimeout(600);
                cn = DriverManager.getConnection(db, "root", "Admin2015");
                st = cn.createStatement();
            }catch(ClassNotFoundException | SQLException e){
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }else if("Nube".equals(Tipo)){
            try{
                Class.forName(dv);
                cn = DriverManager.getConnection(dbNube, "cycsyste_Admin", "Rq!ye@[yb)x_");
                st = cn.createStatement();
            }catch(ClassNotFoundException | SQLException e){
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
    }
    
    public Statement getSt(){
        return st;
    }
    
    public Connection getCn(){
        return cn;
    }
    
    public void BeginTransac(){
        try{
            cn.setAutoCommit(false);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void Close(){
        try{
            cn.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void Commit(){
        try{
            cn.commit();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void RollBack(){
        try{
            cn.rollback();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
