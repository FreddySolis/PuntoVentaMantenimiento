/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PuntoVentas.model;

import PuntoVentas.controller.ConnectorMySQL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;

/**
 *
 * @author Hinge
 */
public class UsersModel {
    private int id_info;
    private int id_user;
    private String nombre;
    private String apellido;
    private String correo;
    private String numero;
    private String usuario;
    private String password;
    private int admin;
    
    public UsersModel(){
    
    }
    
    public UsersModel(int id_info, int id_user, String nombre, String apellido, String correo, String numero, String usuario, String password, int admin){
        this.id_info=id_info;
        this.id_user=id_info;
        this.nombre=nombre;
        this.apellido=apellido;
        this.correo=correo;
        this.numero=numero;
        this.usuario=usuario;
        this.password=password;
        this.admin=admin;
    }
    
    //////////////////////////////////////SETS
    public void set_id_info(int id_info){
        this.id_info=id_info;
    }
    public void set_admin(int admin){
        this.admin=admin;
    }
    public void set_id_user(int id_user){
        this.id_user=id_user;
    }
    public void set_nombre(String nombre){
        this.nombre=nombre;
    }
    public void set_apellido(String apellido){
        this.apellido=apellido;
    }
    public void set_correo(String correo){
        this.correo=correo;
    }
    public void set_numero(String numero){
        this.numero=numero;
    }
    public void set_usuario(String usuario){
        this.usuario=usuario;
    }
    public void set_password(String password){
        this.password=password;
    }
    
    ////////////////////////////GETS
    public int get_id_info(){
        return id_info;
    }
    public int get_id_user(){
        return id_user;
    }
    public int get_admin(){
        return admin;
    }
    public String get_nombre(){
        return nombre;
    }
    public String get_apellido(){
        return apellido;
    }
    public String get_correo(){
        return correo;
    }
    public String get_numero(){
        return numero;
    }
    public String get_usuario(){
        return usuario;
    }
    public String get_password(){
        return password;
    }
    
    
    public int guardar_informacion_usuario(ConnectorMySQL cn){
        String sSQL = "INSERT INTO informacion_usuario (nombre, apellidos, correo, " +
"                    telefono, id_usuarios) " +
"                    VALUES (?,?,?,?,?)";  
       
        try {  
            PreparedStatement pst = cn.getConnection().prepareStatement(sSQL);           
            pst.setString(1,nombre);
            pst.setString(2,apellido);
            pst.setString(3,correo);
            pst.setString(4,numero);         
            pst.setInt(5,id_user);   
            
            return pst.executeUpdate();
            
        } catch (SQLException ex) {
            System.out.println(ex);
            return 0;
        }
    }
    
    public int guardar_usuario(ConnectorMySQL cn){
        String sSQL = "INSERT INTO usuarios (nombre_usuario, password, " +
"                    admin) " +
"                    VALUES (?,?,?)";      
        
            
        try {  
            PreparedStatement pst = cn.getConnection().prepareStatement(sSQL, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1,usuario);
            pst.setString(2,password);
            pst.setInt(3,admin);
            
            int affectedRows = pst.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    id_user = (int) generatedKeys.getLong(1);
                    System.out.println("ID usuario ingresado: " + id_user);
                }
                else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
            
            return pst.executeUpdate();
            
        } catch (SQLException ex) {
            System.out.println(ex);
            return 0;
        }
    }
    public static void all_user(ConnectorMySQL cn, ObservableList<UsersModel> lista){
        UsersModel usuari = null;       
        String usuarioSQL = "SELECT * from usuarios INNER JOIN informacion_usuario ON usuarios.id = informacion_usuario.id_usuarios";
        try {                          
            Statement stt = cn.getConnection().createStatement();
            ResultSet rs = stt.executeQuery(usuarioSQL);                
            while(rs.next()){
                usuari = new UsersModel();
                usuari.set_nombre(rs.getString("nombre"));
                usuari.set_apellido(rs.getString("apellidos"));
                usuari.set_correo(rs.getString("correo"));
                usuari.set_numero(rs.getString("telefono"));
                usuari.set_usuario(rs.getString("nombre_usuario"));
                usuari.set_password(rs.getString("password"));
                usuari.set_admin(rs.getInt("admin"));
                usuari.set_id_user(rs.getInt("usuarios.id"));
                usuari.set_id_info(rs.getInt("informacion_usuario.id_usuarios"));
                lista.add(usuari);
                System.out.println("Usuario encontrado: " + usuari.get_usuario());   
                //user = rs.getString("nombre_usuario");
                //user = user +";"+rs.getString("admin");
            }                
            //System.out.println("Usuario encontrado: " + usuari.get_nombre());   
            
        }catch (SQLException e){
            e.printStackTrace();
           
        }
    }
 
    public static UsersModel find_user(ConnectorMySQL cn,String usuario){
        UsersModel usuari = null;
        String user = null;
        String usuarioSQL = "SELECT * from usuarios INNER JOIN informacion_usuario ON usuarios.id = informacion_usuario.id where nombre_usuario ='"+usuario+"'";
        try {                          
            Statement stt = cn.getConnection().createStatement();
            ResultSet rs = stt.executeQuery(usuarioSQL);                
            while(rs.next()){
                usuari = new UsersModel();
                usuari.set_nombre(rs.getString("nombre"));
                usuari.set_apellido(rs.getString("apellidos"));
                usuari.set_correo(rs.getString("correo"));
                usuari.set_numero(rs.getString("telefono"));
                usuari.set_usuario(rs.getString("nombre_usuario"));
                usuari.set_password(rs.getString("password"));
                usuari.set_admin(rs.getInt("admin"));
                usuari.set_id_user(rs.getInt("usuarios.id"));
                usuari.set_id_info(rs.getInt("informacion_usuario.id"));
                System.out.println("Usuario encontrado: " + usuari.get_nombre());   
                usuari.set_id_info(rs.getInt("informacion_usuario.id_usuarios"));
                System.out.println("Usuario encontrado: " + usuari.get_usuario());   

                //user = rs.getString("nombre_usuario");
                //user = user +";"+rs.getString("admin");
            }                
            //System.out.println("Usuario encontrado: " + usuari.get_nombre());   
            return usuari;
        }catch (SQLException e){
            e.printStackTrace();
            return usuari = null;
        }
    }
    
    public static UsersModel find_email(ConnectorMySQL cn,String correo){
        UsersModel usuari = null;
        String email = null;
        String usuarioSQL = "SELECT * from usuarios INNER JOIN informacion_usuario ON usuarios.id = informacion_usuario.id_usuarios where correo = '"+correo+"'";
        try {                          
            Statement stt = cn.getConnection().createStatement();
            ResultSet rs = stt.executeQuery(usuarioSQL);                
            while(rs.next()){
                usuari = new UsersModel();
                email = rs.getString("correo");
                usuari.set_nombre(rs.getString("nombre"));
                usuari.set_apellido(rs.getString("apellidos"));
                usuari.set_correo(rs.getString("correo"));
                usuari.set_numero(rs.getString("telefono"));
                usuari.set_usuario(rs.getString("nombre_usuario"));
                usuari.set_password(rs.getString("password"));
                usuari.set_admin(rs.getInt("admin"));
                usuari.set_id_user(rs.getInt("usuarios.id"));
                usuari.set_id_info(rs.getInt("informacion_usuario.id_usuarios"));
                System.out.println("Correo encontrado: " + usuari.get_correo()); 
            }                
            //System.out.println("Correo encontrado: " + usuari.get_correo());     
            return usuari;
        }catch (SQLException e){
            e.printStackTrace();
            return usuari = null;
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
    
    @Override
    public String toString(){
        return nombre;
    }
}
