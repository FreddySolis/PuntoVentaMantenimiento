/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PuntoVentas.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hinge
 */
public class VentasModel {
    private int id;
    private String folio;

    public VentasModel(String folio) {
        this.folio = folio;
    }
    
    public VentasModel(int id,String folio) {
        this.id = id;
        this.folio = folio;
    }
    
    public VentasModel() {
        this.id = 0;
        this.folio = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    
    public int guardarInformacion(Connection cn){
         String sSQL = "INSERT INTO ventas (folio) VALUES (?);";
            
        try {  
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setString(1,folio);
           
            return pst.executeUpdate();
            
        } catch (SQLException ex) {
            System.err.println("Error: "+ex);
            return 0;
            
        }
    }
     
    @Override
    public String toString() {
        return String.valueOf(folio);
    }
    
    
     public VentasModel buscarVenta(Connection cn){
        String sSQL = "SELECT id,folio FROM ventas WHERE folio = "+folio;
        VentasModel ventamodel = null;    
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);
            while (rs.next()) {
               ventamodel = new VentasModel(rs.getInt("id"),rs.getString("folio"));

            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductosModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ventamodel;
    }
    
    
    
    
}
