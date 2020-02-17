/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PuntoVentas.model;

import java.sql.Connection;
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
public class ProductosModel {

    private int id;
    private int id_provedor;
    private int id_tipo;
    private String producto;
    private String tamaño;
    private int precio;
    private int cantidad;

    public ProductosModel(int id, int id_provedor, int id_tipo, String producto, String tamaño, int precio, int cantidad) {
        this.id = id;
        this.id_provedor = id_provedor;
        this.id_tipo = id_tipo;
        this.producto = producto;
        this.tamaño = tamaño;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_provedor() {
        return id_provedor;
    }

    public void setId_provedor(int id_provedor) {
        this.id_provedor = id_provedor;
    }

    public int getId_tipo() {
        return id_tipo;
    }

    public void setId_tipo(int id_tipo) {
        this.id_tipo = id_tipo;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getTamaño() {
        return tamaño;
    }

    public void setTamaño(String tamaño) {
        this.tamaño = tamaño;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public static void llenarInformacion(Connection cn, ObservableList<ProductosModel> lista) {
        String sSQL = "SELECT  id,producto,id_proveedor,id_tipo, tamaño, precio,cantidad  FROM productos WHERE cantidad > 0;";

        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);
            while (rs.next()) {
                lista.add(new ProductosModel(rs.getInt("id"), rs.getInt("id_proveedor"),
                        rs.getInt("id_tipo"), rs.getString("producto"), rs.getString("tamaño"), rs.getInt("precio"),
                        rs.getInt("cantidad")));

            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductosModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void busqueda(Connection cn, ObservableList<ProductosModel> lista, int id_Proyecto, String palabra) {
        String sSQL = "SELECT  id,producto,id_proveedor,id_tipo, tamaño, precio,cantidad  FROM productos "
                + "WHERE producto LIKE '%" + palabra + "%'";

        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);
            while (rs.next()) {
                lista.add(new ProductosModel(rs.getInt("id"), rs.getInt("id_proveedor"),
                        rs.getInt("id_tipo"), rs.getString("producto"), rs.getString("tamaño"), rs.getInt("precio"),
                        rs.getInt("cantidad")));

            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductosModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void getProducto(Connection cn, ObservableList<ProductosModel> lista, int id_Proyecto, int id) {
        String sSQL = "SELECT  id,producto,id_proveedor,id_tipo, tamaño, precio,cantidad  FROM productos "
                + "WHERE id = " + id;

        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);
            while (rs.next()) {
                lista.add(new ProductosModel(rs.getInt("id"), rs.getInt("id_proveedor"),
                        rs.getInt("id_tipo"), rs.getString("producto"), rs.getString("tamaño"), rs.getInt("precio"),
                        rs.getInt("cantidad")));

            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductosModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int updateCantidad(Connection connection) {
        try {
            String sSQL = "UPDATE productos SET cantidad = ? WHERE id = ?;";
            
            PreparedStatement instruccion = connection.prepareStatement(sSQL);
            instruccion.setInt(1, cantidad);
            instruccion.setInt(2, id);
            
            return instruccion.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public String toString() {
        return producto;
    }

}
