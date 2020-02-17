/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PuntoVentas.model;

/**
 *
 * @author Hinge
 */
public class ProductosModel {
    
    private int id;
    private int id_proveedor;
    private String proveedor;
    private int id_tipo;
    private String tipo;
    private String producto;
    private String tamaño;
    private float precio;
    private int cantidad;

    public ProductosModel(int id, int id_proveedor, String proveedor, int id_tipo, String tipo, String producto, String tamaño, float precio, int cantidad) {
        this.id = id;
        this.id_proveedor = id_proveedor;
        this.proveedor = proveedor;
        this.id_tipo = id_tipo;
        this.tipo = tipo;
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

    public int getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(int id_proveedor) {
        this.id_proveedor = id_proveedor;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public int getId_tipo() {
        return id_tipo;
    }

    public void setId_tipo(int id_tipo) {
        this.id_tipo = id_tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    

    
}
