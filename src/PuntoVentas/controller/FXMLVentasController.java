/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PuntoVentas.controller;

import PuntoVentas.Main;
import PuntoVentas.model.ConnectorMySQL;
import PuntoVentas.model.ConnectorMySQL;
import PuntoVentas.model.ProductosModel;
import PuntoVentas.model.ProductosModel;
import PuntoVentas.model.ProductosVentas;
import PuntoVentas.model.ProductosVentas;
import PuntoVentas.model.VentasModel;
import PuntoVentas.model.VentasModel;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;

/**
 * FXML Controller class
 *
 * @author JulioCaballero
 */
public class FXMLVentasController implements Initializable {

    @FXML
    private Color x5;
    @FXML
    private Button btnEliminar;
    @FXML
    private Font x2;
    @FXML
    private Button txtVenta;
    @FXML
    private Font x1;
    @FXML
    private Font x3;
    @FXML
    private Label lblProducto;
    @FXML
    private TextField txtCantidad;
    @FXML
    private Button btnAgregar;
    @FXML
    private TextField txtCambio;
    @FXML
    private Font x4;
    @FXML
    private TextField txtFolio;
    @FXML
    private TextField txtTotal;
    @FXML
    private TextField txtIva;
    @FXML
    private TextField txtBuscar;
    @FXML
    private Label lblEmpleado;

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

    //Colecciones
    private ObservableList<ProductosModel> listaProductos;
    private ObservableList<ProductosVentas> listaVentas;
    private ObservableList<ProductosModel> filters;

    private ConnectorMySQL conexion;
    @FXML

    private ProductosModel producto = null;
    private VentasModel venta;
    private ProductosVentas producto_venta;

    private float total;
    private float iva;

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
        clnVenta.setCellValueFactory(new PropertyValueFactory<>("productos"));
        clnPrecioVenta.setCellValueFactory(new PropertyValueFactory<>("total"));
        clnCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        gestionarEventosProductos();
        gestionarEventosVentas();
        txtCantidad.setText("0");
        venta = new VentasModel();
        total = 0;
        iva = 0;
        lblEmpleado.setText(Controller.user.get_nombre());
        String folio1 = VentasModel.buscarUltimaVenta(ConnectorMySQL.getConnection()).getFolio();
        int folio = Integer.parseInt(folio1) + 1;
        txtFolio.setText(folio + "");
        venta.setFolio(folio + "");
    }

    public void gestionarEventosProductos() {
        tblProductos.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends ProductosModel> observable, ProductosModel oldValue, ProductosModel valorSeleccionado) -> {
            if (valorSeleccionado != null) {
                lblProducto.setText(valorSeleccionado.getProducto());
                txtCantidad.setText("1");
                producto = valorSeleccionado;
            } else {
                producto = null;
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
    private void quitarProducto(ActionEvent event) {
        try {
            listaVentas.remove(producto_venta);
            producto_venta.getProductos().setCantidad(producto_venta.getProductos().getCantidad() + producto_venta.getCantidad());
            tblProductos.refresh();
            tlbVentas.refresh();
            calcularPrecios();
        } catch (Exception e) {
            System.out.print("Error: " + e);
        }
    }

    @FXML
    void buscar(ActionEvent event) {

        if (txtBuscar.getText().equals("")) {
            tblProductos.setItems(listaProductos);
            tblProductos.refresh();
        } else {
            filters = FXCollections.observableArrayList();
            char[] letras = txtBuscar.getText().toCharArray();
            for (ProductosModel aux : listaProductos) {
                char[] name_product = aux.getProducto().toCharArray();
                int coincidencias = 0;

                for (int i = 0; i < letras.length; i++) {
                    for (int j = 0; j < name_product.length; j++) {
                        if (letras[i] == name_product[j]) {
                            if (coincidencias == 0) {
                                coincidencias++;
                            } else {
                                if (i == j) {
                                    coincidencias++;
                                }
                            }
                            j = name_product.length;
                        }
                    }

                    if (coincidencias == letras.length) {
                        filters.add(aux);
                    }

                }

            }

            tblProductos.setItems(filters);
            tblProductos.refresh();

        }
    }

    @FXML
    private void realizarVenta(ActionEvent event) {
        try {
            boolean error = true;
            if (listaVentas.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setHeaderText("No tienes productos seleccionado");
                alert.setContentText("No puedes realizar una venta");
            } else {
                int result = 0;
                for (ProductosVentas aux : listaVentas) {
                    result = venta.guardarInformacion(ConnectorMySQL.getConnection());
                    if (result == 1) {
                        venta = venta.buscarVenta(ConnectorMySQL.getConnection());

                        if (venta != null) {
                            aux.setVenta(venta);
                            result = aux.guardarInformacion(ConnectorMySQL.getConnection());
                            if (result == 1) {
                                result = aux.getProductos().updateCantidad(ConnectorMySQL.getConnection());
                                if (result == 1) {
                                    error = false;
                                }
                            }
                        }
                    }

                    if (!error) {
                        String txtFinal = "";
                        ObservableList<ProductosVentas> data = tlbVentas.getItems();
                        for (ProductosVentas a : data) {
                            String name = a.getProductos().getProducto();
                            String price = String.valueOf(a.getProductos().getPrecio());
                            String amount = String.valueOf(a.getCantidad());
                            String total2 = String.valueOf(a.getTotal());
                            int nameValue = name.length();
                            int priceValue = price.length();
                            int amountValue = amount.length();
                            int totalValue = total2.length();
                            if (name.length() < 15) {
                                for (int i = 15; i > nameValue; i--) {
                                    name = name + " ";
                                }
                            }
                            if (price.length() < 15) {
                                for (int i = 15; i > priceValue; i--) {

                                    price = price + " ";
                                }
                            }
                            if (amount.length() < 15) {
                                for (int i = 15; i > amountValue; i--) {

                                    amount = amount + " ";
                                }
                            }
                            if (total2.length() < 15) {
                                for (int i = 15; i > totalValue; i--) {

                                    total2 = total2 + " ";
                                }
                            }
                            txtFinal = name + price + amount + total2 + "\n" + txtFinal;

                        }
                        txtFinal = "----------------------------------------------------------\n" + txtFinal;
                        Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
                        mensaje.setTitle("Venta Satisfactoria");
                        mensaje.setContentText("La venta se ha realizado exitosamente");
                        mensaje.show();
                        listaProductos.clear();
                        ProductosModel.llenarInformacion(ConnectorMySQL.getConnection(), listaProductos);
                        listaVentas.clear();
                        tblProductos.refresh();
                        tlbVentas.refresh();

                        File file = new File(Main.path + "\\ticket.txt");

                        if (file.createNewFile()) {
                            System.out.println("File is created!");
                        } else {
                            System.out.println("File already exists.");
                        }
                        FileWriter writer = new FileWriter(file);
                        writer.write("                      Ticket De Venta\n");
                        writer.write("Producto       Precio         Cantidad       Total\n");
                        writer.write(txtFinal);
                        writer.write("----------------------------------------------------------\n");
                        writer.close();

                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Ha ocurrido un error");
                        alert.setHeaderText("Venta incorrecta");
                        alert.setContentText("No se ha podido realizar la venta, por falvor vuelta a intentarlo");

                        alert.showAndWait();
                    }
                    String folio1 = VentasModel.buscarUltimaVenta(ConnectorMySQL.getConnection()).getFolio();
                    int folio = Integer.parseInt(folio1) + 1;
                    txtFolio.setText(folio + "");
                }

            }
        } catch (Exception e) {
            System.out.print("Error: " + e);
        }
    }

    @FXML
    private void agregarVentas(ActionEvent event) {
        try {
            if (Integer.parseInt(txtCantidad.getText()) > producto.getCantidad() || Integer.parseInt(txtCantidad.getText()) < 1) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setHeaderText("No hay suficientes producto");
                alert.setContentText("No puedes agregar mas productos");
                alert.showAndWait();
            } else if (producto == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setHeaderText("No seleccionaste un producto");
                alert.setContentText("No hay productos, por favor seleccionar uno");
            } else {
                java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
                //venta.setFolio(txtFolio.getText());
                float total = Integer.parseInt(txtCantidad.getText()) * producto.getPrecio();
                float iva = total * 0.16f;

                total = total + iva;

                listaVentas.add(new ProductosVentas(producto, venta, Controller.user.get_id_user(), Integer.parseInt(txtCantidad.getText()), date,
                        total, iva));
                producto.setCantidad(producto.getCantidad() - Integer.parseInt(txtCantidad.getText()));
                tblProductos.refresh();

                calcularPrecios();
            }
        } catch (Exception e) {
            System.out.print("Error: " + e);
        }
    }

    @FXML
    private void FijarRuta() {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Choose Directory");
        Main.path = chooser.showDialog(null).getPath();
    }

    public void calcularPrecios() {
        this.total = 0;
        this.iva = 0;
        for (ProductosVentas aux : listaVentas) {
            this.total += aux.getTotal();
            this.iva += aux.getIva();
        }

        txtTotal.setText("" + total);
        txtIva.setText("" + iva);
    }

}
