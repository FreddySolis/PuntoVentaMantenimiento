/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PuntoVentas.controller;

import PuntoVentas.model.ProductosModel;
import PuntoVentas.model.ProductosVentas;
import PuntoVentas.model.UsersModel;
import PuntoVentas.model.VentasModel;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author ayax9
 */
public class ControllerCorteCaja implements Initializable{
    
    @FXML
    private TableView<ProductosVentas> tbCaja;

    @FXML
    private TableColumn <ProductosVentas, String> colFolio;

    @FXML
    private TableColumn <ProductosVentas, String> colProducto;

    @FXML
    private TableColumn <ProductosVentas, String> colCantidad;

    @FXML
    private TableColumn <ProductosVentas, String> colIva;

    @FXML
    private TableColumn <ProductosVentas, String> ColTotal;

    @FXML
    private Button Seleccionar;

    @FXML
    private DatePicker dtFechaI;

    @FXML
    private DatePicker dtFechaF;

    @FXML
    private ComboBox<UsersModel> cbListaUsuarios;
    
    @FXML
    ObservableList<ProductosVentas> productList = FXCollections.observableArrayList();
    
    @FXML
    ObservableList<UsersModel> usersList = FXCollections.observableArrayList();
    
    private ConnectorMySQL conexion;
    
    @FXML
    private Label txtVentas;

    @FXML
    private Label txtArtVen;

    @FXML
    private Label txtTotal;

    @FXML
    private RadioButton tbTodos;
    
    @FXML
    private Label lbError;
    
    public void initialize(URL url, ResourceBundle rb) {
        cargarUsuarios();
        colFolio.setCellValueFactory(new PropertyValueFactory<>("venta"));
        colProducto.setCellValueFactory(new PropertyValueFactory<>("productos"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colIva.setCellValueFactory(new PropertyValueFactory<>("iva"));
        ColTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        
    }

    
    @FXML
    void seleccion_caja(ActionEvent event) {
        //System.out.println("Fecha inicio: "+dtFechaI.getValue()+" Fecha Final: "+ dtFechaF.getValue() + " Usuario Seleccionado: "+cbListaUsuarios.getSelectionModel().getSelectedItem());
        if(dtFechaI.getValue() != null && dtFechaF.getValue() != null){
            
            if(cbListaUsuarios.getSelectionModel().getSelectedItem()!=null && cbListaUsuarios.isDisable() == false){
                lbError.setVisible(false);
                if(tbCaja != null){
                    tbCaja.getItems().clear();
                    txtTotal.setText("n/o");
                    txtVentas.setText("n/o");
                    txtArtVen.setText("n/o");
                }
                System.out.println("Fecha inicio: "+dtFechaI.getValue()+" Fecha Final: "+ dtFechaF.getValue() + " Usuario Seleccionado: "+cbListaUsuarios.getSelectionModel().getSelectedItem());
                sSQL = "SELECT * FROM `productos_ventas` INNER JOIN `productos` ON productos_ventas.id_productos = productos.id INNER JOIN `ventas` ON productos_ventas.id_ventas = ventas.id INNER JOIN `proveedores` ON proveedores.id = productos.id_proveedor WHERE fecha BETWEEN '"+ dtFechaI.getValue() +"' AND '"+ dtFechaF.getValue() 
                +"' AND productos_ventas.id_usuarios =" + cbListaUsuarios.getSelectionModel().getSelectedItem().get_id_user() ;
                productList = getProductoVentaList(); 
                for(ProductosVentas aux:productList){
                    tbCaja.getItems().add(aux);
                }
                txtTotal.setText(Float.toString(ProductosVentas.getIngresosUser(conexion, dtFechaI.getValue(), dtFechaF.getValue(), cbListaUsuarios.getSelectionModel().getSelectedItem().get_id_user())));
                txtVentas.setText(Integer.toString(ProductosVentas.getVentasUser(conexion, dtFechaI.getValue(), dtFechaF.getValue(), cbListaUsuarios.getSelectionModel().getSelectedItem().get_id_user())));
                txtArtVen.setText(Integer.toString(ProductosVentas.getArticulosUser(conexion, dtFechaI.getValue(), dtFechaF.getValue(), cbListaUsuarios.getSelectionModel().getSelectedItem().get_id_user())));
                
                //tbCaja.getItems().addAll(productList);
            }else if(tbTodos.isSelected()){
                lbError.setVisible(false);
                if(tbCaja != null){
                    tbCaja.getItems().clear();
                    txtTotal.setText("n/o");
                    txtVentas.setText("n/o");
                    txtArtVen.setText("n/o");
                }
                System.out.println("Fecha inicio: "+dtFechaI.getValue() +" Fecha Final: "+ dtFechaF.getValue() + " Usuario Seleccionado: "+tbTodos.isSelected());
                sSQL = "SELECT * FROM `productos_ventas` INNER JOIN `productos` ON productos_ventas.id_productos = productos.id INNER JOIN `ventas` ON productos_ventas.id_ventas = ventas.id INNER JOIN `proveedores` ON proveedores.id = productos.id_proveedor WHERE fecha BETWEEN '"+ dtFechaI.getValue() +"' AND '"+ dtFechaF.getValue() +"'"; 
                productList = getProductoVentaList(); 
                for(ProductosVentas aux:productList){
                    tbCaja.getItems().add(aux);
                }
                txtTotal.setText(Float.toString(ProductosVentas.getIngresos(conexion, dtFechaI.getValue(), dtFechaF.getValue())));
                txtVentas.setText(Integer.toString(ProductosVentas.getVentas(conexion, dtFechaI.getValue(), dtFechaF.getValue())));
                txtArtVen.setText(Integer.toString(ProductosVentas.getArticulos(conexion, dtFechaI.getValue(), dtFechaF.getValue())));
                //tbCaja.getItems().addAll(productList);

            }else{
                lbError.setText("Usuario o Usuarios no establecios");
                lbError.setVisible(true);
            }
                      
        }else{
            lbError.setText("Fechas no establecidas");
            lbError.setVisible(true);
        }
       
        
    }
    private String sSQL;
    public ObservableList<ProductosVentas> getProductoVentaList() {
        ObservableList<ProductosVentas> productList = FXCollections.observableArrayList();
        Connection connection = ConnectorMySQL.getConnection();
        //System.out.println("Fecha inicio: "+dtFechaI.getValue() +" Fecha Final: "+ dtFechaF.getValue());
        //sSQL = "SELECT * FROM `productos_ventas` INNER JOIN `productos` ON productos_ventas.id_productos = productos.id WHERE fecha BETWEEN '"+ dtFechaI.getValue() +"' AND '"+ dtFechaF.getValue() +"'"; 
        //String sSQL2 = "SELECT * FROM `productos_ventas` INNER JOIN `productos` ON productos_ventas.id_productos = productos.id WHERE fecha BETWEEN '"+ dtFechaI.getValue() +"' AND '"+ dtFechaF.getValue() 
          //      +"' AND productos_ventas.id_usuarios = 2"; 
        Statement st;
        ResultSet rs;

        try {
            st = connection.createStatement();
            rs = st.executeQuery(sSQL);
            ProductosModel producto;
            while (rs.next()) {
                producto = new ProductosModel();
                producto.setProducto(rs.getString("productos.producto"));   
                producto.setId(rs.getInt("productos.id"));
                producto.setId_tipo(rs.getInt("productos.id_tipo"));
                producto.setId_proveedor(rs.getInt("productos.id_proveedor"));
                producto.setProveedor(rs.getString("proveedores.proveedor"));
                producto.setPrecio(rs.getFloat("productos.precio"));
                producto.setTamaño(rs.getString("productos.tamaño"));
                System.out.println(producto);
                         
                VentasModel venta = new VentasModel(rs.getInt("ventas.id"), rs.getString("ventas.folio"));
                
                ProductosVentas product = new ProductosVentas();
                product.setId(rs.getInt("productos_ventas.id"));
                product.setProductos(producto);
                product.setVenta(venta);
                product.setId_usuarios(rs.getInt("productos_ventas.id_usuarios"));
                product.setCantidad(rs.getInt("productos_ventas.cantidad"));
                product.setFecha(rs.getDate("productos_ventas.fecha"));
                product.setTotal(rs.getFloat("productos_ventas.total"));
                product.setIva(rs.getFloat("productos_ventas.iva"));
                
                System.out.println(product);
                productList.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productList;
    }
    
    @FXML
    void selecionTodo(MouseEvent event) {
        if(cbListaUsuarios.isDisable()){
            cbListaUsuarios.setDisable(false);
            /*sSQL = "SELECT * FROM `productos_ventas` INNER JOIN `productos` ON productos_ventas.id_productos = productos.id INNER JOIN `ventas` ON productos_ventas.id_ventas = ventas.id INNER JOIN `proveedores` ON proveedores.id = productos.id_proveedor WHERE fecha BETWEEN '"+ dtFechaI.getValue() +"' AND '"+ dtFechaF.getValue() 
                +"' AND productos_ventas.id_usuarios =" + cbListaUsuarios.getSelectionModel().getSelectedItem().get_id_user() ;*/
        }else{
            cbListaUsuarios.setDisable(true);
            sSQL = "SELECT * FROM `productos_ventas` INNER JOIN `productos` ON productos_ventas.id_productos = productos.id INNER JOIN `ventas` ON productos_ventas.id_ventas = ventas.id INNER JOIN `proveedores` ON proveedores.id = productos.id_proveedor WHERE fecha BETWEEN '"+ dtFechaI.getValue() +"' AND '"+ dtFechaF.getValue() +"'"; 
        }

    }
    
    private void cargarUsuarios(){
        UsersModel.all_user(conexion, usersList);        
        cbListaUsuarios.getItems().addAll(usersList);
    }
    
    
}
