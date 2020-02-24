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
import javafx.scene.control.ComboBox;

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
    private TextField tFName;
    @FXML
    private TextField tFAmount;
    @FXML
    private ComboBox cBProveedor;
    @FXML
    private ComboBox cBType;
    @FXML
    private TextField tFPrice;
    @FXML
    private TextField tFTamano;
    @FXML
    private TextField idTF;
    @FXML
    private TableColumn<ProductosModel, String> colType;
    @FXML
    private TableColumn<ProductosModel, String> colAmount;
    @FXML
    private TableColumn<ProductosModel, String> colSize;
    @FXML
    private TableColumn<ProductosModel, String> colName;
    @FXML
    private TableColumn<ProductosModel, String> colPrice;
    @FXML
    private TableColumn<ProductosModel, String> colProvider;
    @FXML
    private TableView<ProductosModel> productTable;
    @FXML
    ObservableList<ProductosModel> productList = FXCollections.observableArrayList();

    public void initialize(URL url, ResourceBundle rb) {
        colName.setCellValueFactory(new PropertyValueFactory<>("producto"));
        colType.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("Precio"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colProvider.setCellValueFactory(new PropertyValueFactory<>("proveedor"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("tamaño"));
        ObservableList<ProductosModel> list = getPersonList();
        productTable.getItems().setAll(list);

        final ObservableList<ProductosModel> tablaProducto = productTable.getSelectionModel().getSelectedItems();
        tablaProducto.addListener(selectorTablaProductos);
        cBProvidersInit();
        cBTypeInit();

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
                Productos = new ProductosModel(rs.getInt("id"), rs.getInt("id_proveedor"), rs.getString("proveedor"), rs.getInt("id_tipo"), rs.getString("tipo"), rs.getFloat("precio"), rs.getInt("cantidad"));
                Productos.setProducto(rs.getString("producto"));
                Productos.setTamaño(rs.getString("tamaño"));
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
                tFAmount.setText(Integer.toString(producto.getCantidad()));
                tFName.setText(producto.getProducto());
                tFPrice.setText(Float.toString(producto.getPrecio()));
                tFTamano.setText(producto.getTamaño());
                idTF.setText(String.valueOf(producto.getId()));
                cBProveedor.getSelectionModel().select(producto.getProveedor());
                cBType.getSelectionModel().select(producto.getTipo());
            }
        } catch (Exception e) {

        }
    }

    public ProductosModel productoSeleccionado() {
        if (productTable != null) {
            List<ProductosModel> tabla = productTable.getSelectionModel().getSelectedItems();
            if (tabla.size() == 1) {
                final ProductosModel seleccionada = tabla.get(0);
                return seleccionada;
            }
        }
        return null;
    }

    @FXML
    private void modificar(ActionEvent event) {
        String query = "UPDATE `productos` SET `id_proveedor` = (SELECT proveedores.id from proveedores WHERE proveedores.proveedor = '" + cBProveedor.getSelectionModel().getSelectedItem() + "'), `id_tipo` = (SELECT tipos.id from tipos WHERE tipos.tipo = '" + cBType.getSelectionModel().getSelectedItem() + "'), `producto` = '" + tFName.getText() + "', `tamaño` = '" + tFTamano.getText() + "', `precio` = '" + tFPrice.getText() + "', `cantidad` = '" + tFAmount.getText() + "' WHERE `productos`.`id` =" + idTF.getText();
        executeQuery(query);
        ObservableList<ProductosModel> list = getPersonList();
        productTable.getItems().setAll(list);

        final ObservableList<ProductosModel> tablaPersonas = productTable.getSelectionModel().getSelectedItems();
        tablaPersonas.addListener(selectorTablaProductos);

    }

    private void cBProvidersInit() {
        String query = "Select proveedor from proveedores";

        Connection connection = ConnectorMySQL.getConnection();
        Statement st;
        ResultSet rs;
        try {
            st = connection.createStatement();
            rs = st.executeQuery(query);
            ProductosModel Productos;
            ObservableList<String> cbproveedores = FXCollections.observableArrayList();
            while (rs.next()) {
                cbproveedores.add(rs.getString("proveedor"));
            }
            cBProveedor.setItems(cbproveedores);
            cBProveedor.getSelectionModel().selectFirst();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    private void cBTypeInit() {
        String query = "Select tipo from tipos";

        Connection connection = ConnectorMySQL.getConnection();
        Statement st;
        ResultSet rs;
        try {
            st = connection.createStatement();
            rs = st.executeQuery(query);
            ProductosModel Productos;
            ObservableList<String> cbproveedores = FXCollections.observableArrayList();
            while (rs.next()) {
                cbproveedores.add(rs.getString("tipo"));
            }
            cBType.setItems(cbproveedores);
            cBType.getSelectionModel().selectFirst();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void eliminar(ActionEvent event) {
        String query = "DELETE FROM productos WHERE id=" + productTable.getSelectionModel().getSelectedItem().getId();
        executeQuery(query);
        ObservableList<ProductosModel> list = getPersonList();
        productTable.getItems().setAll(list);

        final ObservableList<ProductosModel> tablaPersonas = productTable.getSelectionModel().getSelectedItems();
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
        String query = "INSERT INTO `productos` (`id`, `id_proveedor`, `id_tipo`, `producto`, `tamaño`, `precio`, `cantidad`) VALUES (NULL, (SELECT proveedores.id FROM proveedores where proveedores.proveedor = '" + cBProveedor.getSelectionModel().getSelectedItem() + "'), (SELECT tipos.id FROM tipos where tipos.tipo = '" + cBType.getSelectionModel().getSelectedItem() + "'),'" + tFName.getText() + " ', '" + tFTamano.getText() + "', '" + tFPrice.getText() + "', '" + tFAmount.getText() + "');";
        executeQuery(query);
        ObservableList<ProductosModel> list = getPersonList();
        productTable.getItems().setAll(list);

        final ObservableList<ProductosModel> tablaPersonas = productTable.getSelectionModel().getSelectedItems();
        tablaPersonas.addListener(selectorTablaProductos);

        //INSERT INTO `productos` (`id`, `id_proveedor`, `id_tipo`, `producto`, `tamaño`, `precio`, `cantidad`) VALUES (NULL, '1', '1', 'test', 'grande', '50', '100');
    }

}
