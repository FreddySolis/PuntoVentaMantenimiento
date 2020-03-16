/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PuntoVentas.controller;

import PuntoVentas.model.ConnectorMySQL;
import PuntoVentas.model.ProductosModel;
import PuntoVentas.model.ProductosVentas;
import PuntoVentas.model.VentasModel;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

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
    private TableView<ProductosVentas> tlbVentas;
    @FXML
    private TableColumn<ProductosVentas, String> clnVenta;
    @FXML
    private TableColumn<ProductosVentas, Integer> clnPrecioVenta;
    @FXML
    private TableColumn<ProductosVentas, Integer> clnCantidad;
    @FXML
    private TextField txtBuscar;

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
    private ObservableList<ProductosVentas> listaVentas;
    private ObservableList<ProductosModel> filters;

    private ConnectorMySQL conexion;
    @FXML
    private Label lblProducto;

    private ProductosModel producto;
    private VentasModel venta;
    private ProductosVentas producto_venta;

    @FXML
    private TextField txtCambio;

    @FXML
    private javafx.scene.text.Font x4;

    @FXML
    private TextField txtTotal;

    @FXML
    private TextField txtSubtotal;

    @FXML
    private TextField txtEfectivo;

    @FXML
    private TextField txtFolio;
    @FXML
    private Color x5;
    @FXML
    private javafx.scene.text.Font x2;
    @FXML
    private javafx.scene.text.Font x1;
    @FXML
    private javafx.scene.text.Font x3;

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
        venta = new VentasModel();
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
        tlbVentas.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends ProductosVentas> observable, ProductosVentas oldValue, ProductosVentas valorSeleccionado) -> {
            if (valorSeleccionado != null) {
                lblProducto.setText("Por vender: " + valorSeleccionado.getProductos().getProducto());
                producto_venta = valorSeleccionado;
            }
        });
    }

    @FXML
    void agregarVentas(ActionEvent event) {
        try {
            if (Integer.parseInt(txtCantidad.getText()) > producto.getCantidad() || Integer.parseInt(txtCantidad.getText()) < 1) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setHeaderText("No hay suficientes producto");
                alert.setContentText("No puedes agregar mas productos");
                alert.showAndWait();
            } else {
                java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
                venta.setFolio(txtFolio.getText());
                float total = Integer.parseInt(txtCantidad.getText()) * producto.getPrecio();
                float iva = total * 0.16f;
                total = total + iva;
                listaVentas.add(new ProductosVentas(producto, venta, 1, Integer.parseInt(txtCantidad.getText()), date,
                        total, iva));
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
            producto_venta.getProductos().setCantidad(producto_venta.getProductos().getCantidad() + producto_venta.getCantidad());
            tblProductos.refresh();
            tlbVentas.refresh();
        } catch (Exception e) {
            System.out.print("Error: " + e);
        }
    }

    @FXML
    void realizarVenta(ActionEvent event) {
        try {
            boolean error = true;
            if (listaVentas.isEmpty()) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setHeaderText("No tienes productos seleccionado");
                alert.setContentText("No puedes realizar una venta");
            } else {
                int result = 0;
                for (ProductosVentas aux : listaVentas) {
                    venta.setFolio(txtFolio.getText());

                    result = venta.guardarInformacion(ConnectorMySQL.getConnection());

                    if (result == 1) {
                        venta = venta.buscarVenta(ConnectorMySQL.getConnection());
                        if (venta != null) {
                            result = aux.guardarInformacion(ConnectorMySQL.getConnection());
                            if (result == 1) {
                                result = aux.getProductos().updateCantidad(ConnectorMySQL.getConnection());
                                if (result == 1) {
                                    error = false;
                                }
                            }
                        }
                    }

                }
                if (!error) {
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

    @FXML
    void buscar(ActionEvent event) {
        System.out.println("Entra al metodo");
        if (txtBuscar.getText().equals("")) {
            //System.out.println("Entra al listado");
            tblProductos.getItems().clear();
            tblProductos.setItems(listaProductos);
            tblProductos.refresh();
        } else {
            //System.out.println("Entra al filtro");
            filters = FXCollections.observableArrayList();
            char[] letras = txtBuscar.getText().toCharArray();
            for (ProductosModel aux : listaProductos) {
                char[] name_product = aux.getProducto().toCharArray();
                int coincidencias = 0;

                for (int i = 0; i < letras.length; i++) {
                    for (int j = 0; j < name_product.length; j++) {
                        if (letras[i] == name_product[j]) {
                            coincidencias++;
                        }
                    }
                }

                if (coincidencias == letras.length) {
                    filters.add(aux);
                }
            }

            /*for (ProductosModel aux : filters) {
                System.out.println(aux.getProducto());
                System.out.println("---------------------");
            
            
            }*/
            
            tblProductos.getItems().clear();
            tblProductos.setItems(filters);
            tblProductos.refresh();

        }
    }

}
