/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PuntoVentas.controller;

import PuntoVentas.model.ConnectorMySQL;
import PuntoVentas.model.ProductosModel;
import PuntoVentas.model.VentasModel;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author JulioCaballero
 */
public class VentasPruebaController implements Initializable {

    @FXML
    private TableView<ProductosModel> tblProductos;

    //Columnas
    @FXML
    private TableColumn<ProductosModel, String> clnProductos;
    @FXML
    private TableColumn<ProductosModel, Integer> clnPrecio;
    @FXML
    private TableColumn<ProductosModel, Integer> clnExistencias;
    @FXML
    private TableView<VentasModel> tlbVentas;
    @FXML
    private TableColumn<VentasModel, String> clnVenta;
    @FXML
    private TableColumn<VentasModel, Integer> clnPrecioVenta;
    @FXML
    private TableColumn<VentasModel, Integer> clnCantidad;

    @FXML
    private TextField txtCantidad;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button txtVenta;

    //Colecciones
    private ObservableList<ProductosModel> listaProductos;
    private ObservableList<VentasModel> listaVentas;

    private ConnectorMySQL conexion;
    @FXML
    private Label lblProducto;

    private ProductosModel producto;
    private VentasModel venta;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conexion = new ConnectorMySQL();
        listaProductos = FXCollections.observableArrayList();
        listaVentas = FXCollections.observableArrayList();
        ProductosModel.llenarInformacion(ConnectorMySQL.getConnection(), listaProductos);
        tblProductos.setItems(listaProductos);
        tlbVentas.setItems(listaVentas);
        clnProductos.setCellValueFactory(new PropertyValueFactory<>("producto"));
        clnPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        clnExistencias.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        clnVenta.setCellValueFactory(new PropertyValueFactory<>("producto"));
        clnPrecioVenta.setCellValueFactory(new PropertyValueFactory<>("total"));
        clnCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        gestionarEventosProductos();
        gestionarEventosVentas();
        txtCantidad.setText("0");
    }

    public void gestionarEventosProductos() {
        tblProductos.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends ProductosModel> observable, ProductosModel oldValue, ProductosModel valorSeleccionado) -> {
            if (valorSeleccionado != null) {
                lblProducto.setText(valorSeleccionado.getProducto());
                txtCantidad.setText("1");
                producto = valorSeleccionado;
            }
        });
    }

    public void gestionarEventosVentas() {
        tlbVentas.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends VentasModel> observable, VentasModel oldValue, VentasModel valorSeleccionado) -> {
            if (valorSeleccionado != null) {
                lblProducto.setText("Por vender: "+valorSeleccionado.getProducto().getProducto());
                venta = valorSeleccionado;
            }
        });
    }

    @FXML
    void agregarVentas(ActionEvent event) {
        try {
            if (Integer.parseInt(txtCantidad.getText()) > producto.getCantidad() || Integer.parseInt(txtCantidad.getText()) < 1 ) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setHeaderText("No hay suficientes producto");
                alert.setContentText("No puedes agregar mas productos");
                alert.showAndWait();
            } else {
                java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
                listaVentas.add(new VentasModel(1, producto, date, producto.getPrecio(), Integer.parseInt(txtCantidad.getText()), Integer.parseInt(txtCantidad.getText()) * producto.getPrecio()));
                producto.setCantidad(producto.getCantidad() - Integer.parseInt(txtCantidad.getText()));
                //listaProductos.remove(producto);
                tblProductos.refresh();
            }
        } catch (Exception e) {
            System.out.print("Error: " + e);
        }

    }

    @FXML
    void quitarProducto(ActionEvent event) {
        try {
            listaVentas.remove(venta);
            venta.getProducto().setCantidad(venta.getProducto().getCantidad() + venta.getCantidad());
            tblProductos.refresh();
            tlbVentas.refresh();
        } catch (Exception e) {
            System.out.print("Error: " + e);
        }
    }

    @FXML
    void realizarVenta(ActionEvent event) {
        try {
            if (listaVentas.isEmpty()) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setHeaderText("No tienes productos seleccionado");
                alert.setContentText("No puedes realizar una venta");
            } else {
                int r = 0;
                int i = 0;
                for (VentasModel aux : listaVentas) {
                    r = aux.guardarInformacion(ConnectorMySQL.getConnection());
                    //System.out.println("Resultado venta: "+r);
                    if (r == 1) {
                        i = aux.getProducto().updateCantidad(ConnectorMySQL.getConnection());
                        //System.out.println("Resultado producto: "+i);
                    }
                    if (r != 1 || i != 1) {
                        break;
                    }
                }
                if (r == 1) {
                    Alert mensaje = new Alert(AlertType.INFORMATION);
                    mensaje.setTitle("Venta Satisfactoria");
                    mensaje.setContentText("La venta se ha realizado exitosamente");
                    mensaje.show();
                    listaProductos.clear();
                    ProductosModel.llenarInformacion(ConnectorMySQL.getConnection(), listaProductos);
                    listaVentas.clear();
                    tblProductos.refresh();
                    tlbVentas.refresh();
                    
                } else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Ha ocurrido un error");
                    alert.setHeaderText("Venta incorrecta");
                    alert.setContentText("No se ha podido realizar la venta, por falvor vuelta a intentarlo");

                    alert.showAndWait();
                }

            }
        } catch (Exception e) {
            System.out.print("Error: " + e);
        }
    }
    
    

}
