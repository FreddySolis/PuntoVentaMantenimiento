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
 * @author JulioCaballero
 */
public class ProductosVentas {

    private int id;
    private ProductosModel productos;
    private VentasModel venta;
    private int id_usuarios;
    private int cantidad;
    private Date fecha;
    private float total;
    private float iva;

    public ProductosVentas() {
        this.id = 0;
        this.productos = null;
        this.venta = null;
        this.id_usuarios = 0;
        this.cantidad = 0;
        this.fecha = null;
        this.total = 0;
        this.iva = 0;
    }

    public ProductosVentas(ProductosModel productos, VentasModel venta, int id_usuarios, int cantidad, Date fecha, float total, float iva) {
        this.productos = productos;
        this.venta = venta;
        this.id_usuarios = id_usuarios;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.total = total;
        this.iva = iva;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProductosModel getProductos() {
        return productos;
    }

    public void setProductos(ProductosModel productos) {
        this.productos = productos;
    }

    public VentasModel getVenta() {
        return venta;
    }

    public void setVenta(VentasModel ventas) {
        this.venta = ventas;
    }

    public int getId_usuarios() {
        return id_usuarios;
    }

    public void setId_usuarios(int id_usuarios) {
        this.id_usuarios = id_usuarios;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
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

    public float getIva() {
        return iva;
    }

    public void setIva(float iva) {
        this.iva = iva;
    }

    public int guardarInformacion(Connection cn) {
        String sSQL = "INSERT INTO productos_ventas (id_productos,id_ventas,id_usuarios,"
                + "cantidad,fecha,total,iva) VALUES (?,?,?,?,?,?,?);";

        try {
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setInt(1, productos.getId());
            pst.setInt(2, venta.getId());
            pst.setInt(3, id_usuarios);
            pst.setInt(4, cantidad);
            pst.setDate(5, fecha);
            pst.setFloat(6, total);
            pst.setFloat(7, iva);

            return pst.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Error: " + ex);
            return 0;

        }
    }
    
    @Override
    public String toString() {
        return String.valueOf(id);
    }
    
    public static float getIngresos(ConnectorMySQL cn, LocalDate dtFechaI, LocalDate dtFechaF){
        String sSQL = "SELECT SUM(total) totalV  FROM `productos_ventas` INNER JOIN `productos` ON productos_ventas.id_productos = productos.id INNER JOIN `ventas` ON productos_ventas.id_ventas = ventas.id INNER JOIN `proveedores` ON proveedores.id = productos.id_proveedor WHERE fecha BETWEEN '"+ dtFechaI +"' AND '"+ dtFechaF +"'"; 
        float suma = -1;
        //Statement st;
        //ResultSet rs;
        
        try {
            Statement stt = cn.getConnection().createStatement();
            ResultSet rs = stt.executeQuery(sSQL);        
            ProductosModel producto;
            while (rs.next()) {                    
               suma = rs.getFloat("totalV");
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
    
    public static float getIngresosUser(ConnectorMySQL cn, LocalDate dtFechaI, LocalDate dtFechaF, int user){
        String sSQL = "SELECT SUM(total) totalV  FROM `productos_ventas` INNER JOIN `productos` ON productos_ventas.id_productos = productos.id INNER JOIN `ventas` ON productos_ventas.id_ventas = ventas.id INNER JOIN `proveedores` ON proveedores.id = productos.id_proveedor WHERE fecha BETWEEN '"+ dtFechaI +"' AND '"+ dtFechaF +"' AND productos_ventas.id_usuarios =" + user ; 
        float suma = -1;
        //Statement st;
        //ResultSet rs;
        
        try {
            Statement stt = cn.getConnection().createStatement();
            ResultSet rs = stt.executeQuery(sSQL);        
            ProductosModel producto;
            while (rs.next()) {                    
               suma = rs.getFloat("totalV");
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
