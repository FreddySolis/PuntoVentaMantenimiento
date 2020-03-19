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
public class TipoModel {
    private int id;
    private String tipo;

    public TipoModel(int id, String tipo) {
        this.id = id;
        this.tipo = tipo;
    }
    public TipoModel() {
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public static TipoModel all_type(ConnectorMySQL cn){
        TipoModel tipo = null;
        String SQL = "SELECT * FROM `tipos`";
        try {                          
            Statement stt = cn.getConnection().createStatement();
            ResultSet rs = stt.executeQuery(SQL);                
            while(rs.next()){
                tipo = new TipoModel();
                tipo.setId(rs.getInt("id"));
                tipo.setTipo(rs.getString("apellidos"));
                
                System.out.println("Usuario encontrado: " + tipo.getTipo());   

                //user = rs.getString("nombre_usuario");
                //user = user +";"+rs.getString("admin");
            }                
            //System.out.println("Usuario encontrado: " + usuari.get_nombre());   
            return tipo;
        }catch (SQLException e){
            e.printStackTrace();
            return tipo = null;
        }
    }
    
    
    
    
}
