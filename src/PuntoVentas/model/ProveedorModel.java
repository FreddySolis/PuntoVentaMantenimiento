/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PuntoVentas.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author JulioCaballero
 */
public class ProveedorModel {
    private int id;
    private String proveedor;
    private String telefono;
    private String correo;

    public ProveedorModel(int id, String proveedor, String telefono, String correo) {
        this.id = id;
        this.proveedor = proveedor;
        this.telefono = telefono;
        this.correo = correo;
    }
    public ProveedorModel() {
        
    }    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    public static ProveedorModel all_proovedor(ConnectorMySQL cn){
        ProveedorModel proovedor = null;
        String SQL = "SELECT * FROM `proveedores`";
        try {                          
            Statement stt = cn.getConnection().createStatement();
            ResultSet rs = stt.executeQuery(SQL);                
            while(rs.next()){
                proovedor = new ProveedorModel();
                proovedor.setId(rs.getInt("id"));
                proovedor.setProveedor(rs.getString("proovedor"));
                proovedor.setTelefono(rs.getString("telefono"));
                proovedor.setCorreo(rs.getString("correo"));
                
                System.out.println("Usuario encontrado: " + proovedor.getProveedor());   

                //user = rs.getString("nombre_usuario");
                //user = user +";"+rs.getString("admin");
            }                
            //System.out.println("Usuario encontrado: " + usuari.get_nombre());   
            return proovedor;
        }catch (SQLException e){
            e.printStackTrace();
            return proovedor = null;
        }
    }
    
    
}
