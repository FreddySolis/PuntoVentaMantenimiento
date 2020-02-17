package PuntoVentas.controller;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;

import java.sql.Connection;
import java.sql.ResultSet;
import PuntoVentas.model.ProductosModel;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Node;
import javafx.scene.Parent;

public class ControllerProductos implements Initializable {

    @FXML
    private Button Regresar;
    @FXML
    private Button buttoneliminar;
    @FXML
    private Button buttoneditar;
    @FXML
    private Button btnAgregar;
    @FXML
    private TextField productoTF;
    @FXML
    private TextField cantidadTF;
    @FXML
    private TextField precioTF;
    @FXML
    private TextField proveedorTF;
    @FXML
    private TextField tamañoTF;
    @FXML
    private TextField idTF;
    @FXML
    private TextField tipoProductoTF;
    @FXML
    private TableColumn<ProductosModel, String> tipoProduct;
    @FXML
    private TableColumn<ProductosModel, String> CantidadProducto;
    @FXML
    private TableColumn<ProductosModel, String> folio;
    @FXML
    private TableColumn<ProductosModel, String> nombreProduct;
    @FXML
    private TableColumn<ProductosModel, String> precio;
    @FXML
    private TableColumn<ProductosModel, String> nombreProvee;
    @FXML
    private TableView<ProductosModel> tablaProduct;
    @FXML
    ObservableList<ProductosModel> productList = FXCollections.observableArrayList();

    public void initialize(URL url, ResourceBundle rb) {
        nombreProduct.setCellValueFactory(new PropertyValueFactory<>("producto"));
        tipoProduct.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        precio.setCellValueFactory(new PropertyValueFactory<>("Precio"));
        CantidadProducto.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        nombreProvee.setCellValueFactory(new PropertyValueFactory<>("proveedor"));
        folio.setCellValueFactory(new PropertyValueFactory<>("id"));
        ObservableList<ProductosModel> list = getPersonList();
        tablaProduct.getItems().setAll(list);

        final ObservableList<ProductosModel> tablaProducto = tablaProduct.getSelectionModel().getSelectedItems();
        tablaProducto.addListener(selectorTablaProductos);
    }

    public ObservableList<ProductosModel> getPersonList() {
        ObservableList<ProductosModel> productList = FXCollections.observableArrayList();
        Connection connection = ConnectorMySQL.getConnection();
        String query = "select productos.id,productos.id_proveedor,productos.id_tipo,productos.producto,productos.tamaño,productos.precio,productos.cantidad,proveedores.proveedor,tipos.tipo from productos INNER JOIN proveedores ON productos.id_proveedor = proveedores.id INNER JOIN tipos on productos.id_tipo = tipos.id";
        Statement st;
        ResultSet rs;

        try {
            st = connection.createStatement();
            rs = st.executeQuery(query);
            ProductosModel Productos;
            while (rs.next()) {
                Productos = new ProductosModel(rs.getInt("id"), rs.getInt("id_proveedor"), rs.getString("proveedor"), rs.getInt("id_tipo"), rs.getString("tipo"), rs.getString("producto"), rs.getString("tamaño"), rs.getFloat("precio"), rs.getInt("cantidad"));
                productList.add(Productos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productList;
    }

    public void executeQuery(String query) {
        Connection conn = ConnectorMySQL.getConnection();
        Statement st;
        try {
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final ListChangeListener<ProductosModel> selectorTablaProductos
            = new ListChangeListener<ProductosModel>() {

        @Override
        public void onChanged(Change<? extends ProductosModel> arg0) {
            ponerProductoSeleccionada();

        }
    };

    private void ponerProductoSeleccionada() {
        try {
            final ProductosModel producto = productoSeleccionado();
            if (productList != null) {
                proveedorTF.setText(producto.getProveedor());
                cantidadTF.setText(Integer.toString(producto.getCantidad()));
                tipoProductoTF.setText(producto.getTipo());
                productoTF.setText(producto.getProducto());
                precioTF.setText(Float.toString(producto.getPrecio()));
                tamañoTF.setText(producto.getTamaño());
                idTF.setText(String.valueOf(producto.getId()));
            }
        } catch (Exception e) {

        }
    }

    public ProductosModel productoSeleccionado() {
        if (tablaProduct != null) {
            List<ProductosModel> tabla = tablaProduct.getSelectionModel().getSelectedItems();
            if (tabla.size() == 1) {
                final ProductosModel seleccionada = tabla.get(0);
                return seleccionada;
            }
        }
        return null;
    }

    @FXML
    private void modificar(ActionEvent event) {
        String query = "UPDATE `productos` SET `id_proveedor` = (SELECT proveedores.id from proveedores WHERE proveedores.proveedor = '" + proveedorTF.getText() + "'), `id_tipo` = (SELECT tipos.id from tipos WHERE tipos.tipo = '" + tipoProductoTF.getText() + "'), `producto` = '" + productoTF.getText() + "', `tamaño` = '" + tamañoTF.getText() + "', `precio` = '" + precioTF.getText() + "', `cantidad` = '" + cantidadTF.getText() + "' WHERE `productos`.`id` =" + idTF.getText();
        executeQuery(query);
        ObservableList<ProductosModel> list = getPersonList();
        tablaProduct.getItems().setAll(list);

        final ObservableList<ProductosModel> tablaPersonas = tablaProduct.getSelectionModel().getSelectedItems();
        tablaPersonas.addListener(selectorTablaProductos);

    }

    @FXML
    private void eliminar(ActionEvent event) {
        String query = "DELETE FROM productos WHERE id=" + tablaProduct.getSelectionModel().getSelectedItem().getId();
        executeQuery(query);
        ObservableList<ProductosModel> list = getPersonList();
        tablaProduct.getItems().setAll(list);

        final ObservableList<ProductosModel> tablaPersonas = tablaProduct.getSelectionModel().getSelectedItems();
        tablaPersonas.addListener(selectorTablaProductos);
    }

    @FXML
    private Button Salir;

    @FXML
    public void regresarLogin(ActionEvent event) {
        try {
            Parent menu_parent;

            menu_parent = FXMLLoader.load(getClass().getResource("../view/FXMLPuntoVentasLOGIN.fxml"));
            Scene menu_scene = new Scene(menu_parent);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.hide();
            app_stage.setScene(menu_scene);
            app_stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ControllerProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void cargarListado(ActionEvent event) {
       try {
            Parent menu_parent;

            menu_parent = FXMLLoader.load(getClass().getResource("../view/FXMLPuntoVentasLISTADO.fxml"));
            Scene menu_scene = new Scene(menu_parent);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.hide();
            app_stage.setScene(menu_scene);
            app_stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ControllerProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void agregarProducto() {
        String query = "INSERT INTO `productos` (`id`, `id_proveedor`, `id_tipo`, `producto`, `tamaño`, `precio`, `cantidad`) VALUES (NULL, (SELECT proveedores.id FROM proveedores where proveedores.proveedor = '" + proveedorTF.getText() + "'), (SELECT tipos.id FROM tipos where tipos.tipo = '" + tipoProductoTF.getText() + "'),'" + productoTF.getText() + " ', '" + tamañoTF.getText() + "', '" + precioTF.getText() + "', '" + cantidadTF.getText() + "');";
        executeQuery(query);
        ObservableList<ProductosModel> list = getPersonList();
        tablaProduct.getItems().setAll(list);

        final ObservableList<ProductosModel> tablaPersonas = tablaProduct.getSelectionModel().getSelectedItems();
        tablaPersonas.addListener(selectorTablaProductos);

        //INSERT INTO `productos` (`id`, `id_proveedor`, `id_tipo`, `producto`, `tamaño`, `precio`, `cantidad`) VALUES (NULL, '1', '1', 'test', 'grande', '50', '100');
    }


}
