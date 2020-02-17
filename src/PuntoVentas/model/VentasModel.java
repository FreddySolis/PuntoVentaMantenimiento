/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PuntoVentas.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Hinge
 */
public class VentasModel {
    private int id;
    private int folio;
    private ProductosModel producto;
    private Date fecha;
    private int precioUnitario;
    private int cantidad;
    private int total;

    public VentasModel(int folio, ProductosModel producto, Date fecha, int total) {
        this.folio = folio;
        this.producto = producto;
        this.fecha = fecha;
        this.total = total;
    }
    
    public VentasModel(int folio, ProductosModel producto, Date fecha, int precioUnitario, int total) {
        this.folio = folio;
        this.producto = producto;
        this.fecha = fecha;
        this.precioUnitario = precioUnitario;
        this.total = total;
    }
    
    public VentasModel(int id, int folio, ProductosModel producto, Date fecha, int precioUnitario, int total) {
        this.id = id;
        this.folio = folio;
        this.producto = producto;
        this.fecha = fecha;
        this.precioUnitario = precioUnitario;
        this.total = total;
    }

    public VentasModel(int folio, ProductosModel producto, Date fecha, int precioUnitario, int cantidad, int total) {
        this.folio = folio;
        this.producto = producto;
        this.fecha = fecha;
        this.precioUnitario = precioUnitario;
        this.cantidad = cantidad;
        this.total = total;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    public int getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(int precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFolio() {
        return folio;
    }

    public void setFolio(int folio) {
        this.folio = folio;
    }

    public ProductosModel getProducto() {
        return producto;
    }

    public void setProducto(ProductosModel producto) {
        this.producto = producto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
    
    
     public int guardarInformacion(Connection cn){
         String sSQL = "INSERT INTO ventas (folio,id_producto,cantidad,fecha,total) "
                 + "VALUES (?,?,?,?,?);";
            
        try {  
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setInt(1,folio);
            pst.setInt(2,producto.getId());
            pst.setInt(3,cantidad);
            pst.setDate(4,fecha);
            pst.setInt(5,total);
            
            return pst.executeUpdate();
            
        } catch (SQLException ex) {
            System.err.println("Error: "+ex);
            return 0;
            
        }
    }
    
    
    
    
}
