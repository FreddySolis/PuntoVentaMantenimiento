/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PuntoVentas.model;

import PuntoVentas.controller.ConnectorMySQL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hinge
 */
public class UsersModel {
    
    public static String find_user(ConnectorMySQL cn,String usuario){
        String user = null;
        String usuarioSQL = "select nombre_usuario,admin from usuarios where nombre_usuario = '"+usuario+"'";
        try {                          
            Statement stt = cn.getConnection().createStatement();
            ResultSet rs = stt.executeQuery(usuarioSQL);                
            while(rs.next()){
                user = rs.getString("nombre_usuario");
                user = user +";"+rs.getString("admin");
            }                
               
            return user;
        }catch (SQLException e){
            e.printStackTrace();
            return user = null;
        }
    }
    
    public static String get_password(ConnectorMySQL cn, String usuario){
        String password = null;
        String passwordSQL = "select password from usuarios where nombre_usuario = '"+usuario+"'";

        try {                          
            Statement stt = cn.getConnection().createStatement();
            ResultSet rs = stt.executeQuery(passwordSQL);                
            while(rs.next()){
                password = rs.getString("password");
            }                
               
            return password;
        }catch (SQLException e){
            e.printStackTrace();
            return password = null;
        }
        
    }
}
