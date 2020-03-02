/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PuntoVentas.model;

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
    
    
    
}
