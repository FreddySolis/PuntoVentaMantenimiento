/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PuntoVentas.model;

import PuntoVentas.controller.ConnectorMySQL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

/**
 *
 * @author Hinge
 */
public class VentasModel {
    private int id;
    private int folio;
    private ProductosModel producto;    
    private Date fecha;
    private float precioUnitario;
    private int cantidad;
    private float total;
    
    private int id_user;
    private int id_venta;
    private int iva;
    private String folio_content;

    
    public VentasModel(){}
    
    public VentasModel(int folio, ProductosModel producto, Date fecha, float total) {
        this.folio = folio;
        this.producto = producto;
        this.fecha = fecha;
        this.total = total;
    }
    
    public VentasModel(int folio, ProductosModel producto, Date fecha, float precioUnitario, float total) {
        this.folio = folio;
        this.producto = producto;
        this.fecha = fecha;
        this.precioUnitario = precioUnitario;
        this.total = total;
    }
    
    public VentasModel(int id, int folio, ProductosModel producto, java.sql.Date fecha, float precioUnitario, float total) {
        this.id = id;
        this.folio = folio;
        this.producto = producto;
        this.fecha = fecha;
        this.precioUnitario = precioUnitario;
        this.total = total;
    }

    public VentasModel(int folio, ProductosModel producto, Date fecha, float precioUnitario, int cantidad, float total) {
        this.folio = folio;
        this.producto = producto;
        this.fecha = fecha;
        this.precioUnitario = precioUnitario;
        this.cantidad = cantidad;
        this.total = total;
    }
    
    public void setIdUser(int user) {
        this.id_user = user;
    }
    
    public int getIdUser() {
        return id_user;
    }
    
    public void setFolioC(String folio_content) {
        this.folio_content = folio_content;
    }
    
    public String getFolioC() {
        return folio_content;
    }
    
    public void setIva(int iva) {
        this.iva = iva;
    }
    
    public int getIva() {
        return iva;
    }
    
    public void setIdVenta(int venta) {
        this.id_venta = venta;
    }
    
    public int getIdVenta() {
        return id_venta;
    }
    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    public float getPrecioUnitario() {
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

    public void setTotal(float total) {
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
            pst.setFloat(5,total);
            
            return pst.executeUpdate();
            
        } catch (SQLException ex) {
            System.err.println("Error: "+ex);
            return 0;
            
        }
    }
     
    @Override
    public String toString() {
        return String.valueOf(id);
    }
    
    public static int getIngresos(ConnectorMySQL cn, LocalDate dtFechaI, LocalDate dtFechaF){
        String sSQL = "SELECT SUM(total) totalV  FROM `productos_ventas` INNER JOIN `productos` ON productos_ventas.id_productos = productos.id INNER JOIN `ventas` ON productos_ventas.id_ventas = ventas.id INNER JOIN `proveedores` ON proveedores.id = productos.id_proveedor WHERE fecha BETWEEN '"+ dtFechaI +"' AND '"+ dtFechaF +"'"; 
        int suma = -1;
        //Statement st;
        //ResultSet rs;
        
        try {
            Statement stt = cn.getConnection().createStatement();
            ResultSet rs = stt.executeQuery(sSQL);        
            ProductosModel producto;
            while (rs.next()) {                    
               suma = rs.getInt("totalV");
            }
            return suma;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return suma;        
    }
    
    public static int getVentas(ConnectorMySQL cn, LocalDate dtFechaI, LocalDate dtFechaF){
        String sSQL = "SELECT COUNT(total) cont_ventas FROM `productos_ventas` INNER JOIN `productos` ON productos_ventas.id_productos = productos.id INNER JOIN `ventas` ON productos_ventas.id_ventas = ventas.id INNER JOIN `proveedores` ON proveedores.id = productos.id_proveedor WHERE fecha BETWEEN '"+ dtFechaI +"' AND '"+ dtFechaF +"'"; 
        int suma = -1;
        //Statement st;
        //ResultSet rs;
        
        try {
            Statement stt = cn.getConnection().createStatement();
            ResultSet rs = stt.executeQuery(sSQL);        
            ProductosModel producto;
            while (rs.next()) {                    
               suma = rs.getInt("cont_ventas");
            }
            return suma;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return suma; 
    }
            
    public static int getArticulos(ConnectorMySQL cn, LocalDate dtFechaI, LocalDate dtFechaF){
        String sSQL = "SELECT SUM(cantidad) can_productos FROM `productos_ventas` INNER JOIN `productos` ON productos_ventas.id_productos = productos.id INNER JOIN `ventas` ON productos_ventas.id_ventas = ventas.id INNER JOIN `proveedores` ON proveedores.id = productos.id_proveedor WHERE fecha BETWEEN '"+ dtFechaI +"' AND '"+ dtFechaF +"'"; 
        int suma = -1;
        //Statement st;
        //ResultSet rs;
        
        try {
            Statement stt = cn.getConnection().createStatement();
            ResultSet rs = stt.executeQuery(sSQL);        
            ProductosModel producto;
            while (rs.next()) {                    
               suma = rs.getInt("can_productos");
            }
            return suma;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return suma; 
    }
    
    public static int getIngresosUser(ConnectorMySQL cn, LocalDate dtFechaI, LocalDate dtFechaF, int user){
        String sSQL = "SELECT SUM(total) totalV  FROM `productos_ventas` INNER JOIN `productos` ON productos_ventas.id_productos = productos.id INNER JOIN `ventas` ON productos_ventas.id_ventas = ventas.id INNER JOIN `proveedores` ON proveedores.id = productos.id_proveedor WHERE fecha BETWEEN '"+ dtFechaI +"' AND '"+ dtFechaF +"' AND productos_ventas.id_usuarios =" + user ; 
        int suma = -1;
        //Statement st;
        //ResultSet rs;
        
        try {
            Statement stt = cn.getConnection().createStatement();
            ResultSet rs = stt.executeQuery(sSQL);        
            ProductosModel producto;
            while (rs.next()) {                    
               suma = rs.getInt("totalV");
            }
            return suma;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return suma;        
    }
    
    public static int getVentasUser(ConnectorMySQL cn, LocalDate dtFechaI, LocalDate dtFechaF, int user){
        String sSQL = "SELECT COUNT(total) cont_ventas FROM `productos_ventas` INNER JOIN `productos` ON productos_ventas.id_productos = productos.id INNER JOIN `ventas` ON productos_ventas.id_ventas = ventas.id INNER JOIN `proveedores` ON proveedores.id = productos.id_proveedor WHERE fecha BETWEEN '"+ dtFechaI +"' AND '"+ dtFechaF +"' AND productos_ventas.id_usuarios =" + user; 
        int suma = -1;
        //Statement st;
        //ResultSet rs;
        
        try {
            Statement stt = cn.getConnection().createStatement();
            ResultSet rs = stt.executeQuery(sSQL);        
            ProductosModel producto;
            while (rs.next()) {                    
               suma = rs.getInt("cont_ventas");
            }
            return suma;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return suma; 
    }
            
    public static int getArticulosUser(ConnectorMySQL cn, LocalDate dtFechaI, LocalDate dtFechaF, int user){
        String sSQL = "SELECT SUM(cantidad) can_productos FROM `productos_ventas` INNER JOIN `productos` ON productos_ventas.id_productos = productos.id INNER JOIN `ventas` ON productos_ventas.id_ventas = ventas.id INNER JOIN `proveedores` ON proveedores.id = productos.id_proveedor WHERE fecha BETWEEN '"+ dtFechaI +"' AND '"+ dtFechaF +"' AND productos_ventas.id_usuarios =" + user; 
        int suma = -1;
        //Statement st;
        //ResultSet rs;
        
        try {
            Statement stt = cn.getConnection().createStatement();
            ResultSet rs = stt.executeQuery(sSQL);        
            ProductosModel producto;
            while (rs.next()) {                    
               suma = rs.getInt("can_productos");
            }
            return suma;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return suma; 
    }
    
    
    
    
}
