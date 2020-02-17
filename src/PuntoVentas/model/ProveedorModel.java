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

    public ProveedorModel(int id, String proveedor) {
        this.id = id;
        this.proveedor = proveedor;
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
    
    
    
}
