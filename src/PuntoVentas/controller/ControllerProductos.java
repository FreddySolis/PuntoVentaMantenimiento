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
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;

public class ControllerProductos implements Initializable {

    @FXML
    private Button Regresar;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnTipo;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnAdd;
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
    private ComboBox<String> cbprovee;
    @FXML
    private ComboBox<String> cbtipo;
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
        userType();

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
            cbproveedores.add("N/o");
            cbprovee.getItems().addAll(cbproveedores);
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
            cbproveedores.add("N/o");
            cbtipo.getItems().addAll(cbproveedores);
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
    public void cargarListado() {
        try {
            AnchorPane root2 = (AnchorPane) FXMLLoader.load(getClass().getResource("FXMLPuntoVentasLISTADO.fxml"));
            Scene scene = new Scene(root2);
            Stage primaryLayout = new Stage();
            primaryLayout.setScene(scene);
            primaryLayout.setTitle("FXMLPuntoVentasLISTADO");
            primaryLayout.show();
            Stage nuevaEscena = (Stage) this.Regresar.getScene().getWindow();
            nuevaEscena.close();

      
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
    
    @FXML
    void buscar(ActionEvent event) {
        String sql;
        
        if(cbtipo.getSelectionModel().getSelectedItem()!=null && cbprovee.getSelectionModel().getSelectedItem()!=null){
            
            if(!cbtipo.getSelectionModel().getSelectedItem().equals("N/o") && !cbprovee.getSelectionModel().getSelectedItem().equals("N/o")){
                System.out.println(cbtipo.getSelectionModel().getSelectedItem());
                System.out.println(cbprovee.getSelectionModel().getSelectedItem());
                sql ="SELECT * " +
                    "FROM productos " +
                    "INNER JOIN proveedores ON productos.id_proveedor = proveedores.id " +
                    "INNER JOIN tipos ON productos.id_tipo = tipos.id "+
                    "WHERE proveedores.proveedor = '"+cbprovee.getSelectionModel().getSelectedItem()+"' "+
                    "AND tipos.tipo = '"+cbtipo.getSelectionModel().getSelectedItem()+"' ";
                ObservableList<ProductosModel> list = getPersonList2(sql);
                productTable.getItems().setAll(list);
            }else if(cbtipo.getSelectionModel().getSelectedItem().equals("N/o") && !cbprovee.getSelectionModel().getSelectedItem().equals("N/o")){
                System.out.println(cbprovee.getSelectionModel().getSelectedItem());
                sql ="SELECT * " +
                    "FROM productos " +
                    "INNER JOIN proveedores ON productos.id_proveedor = proveedores.id " +
                    "INNER JOIN tipos ON productos.id_tipo = tipos.id "+
                    "WHERE proveedores.proveedor = '"+cbprovee.getSelectionModel().getSelectedItem()+"' ";
                ObservableList<ProductosModel> list = getPersonList2(sql);
                productTable.getItems().setAll(list);
            }else if(!cbtipo.getSelectionModel().getSelectedItem().equals("N/o") && cbprovee.getSelectionModel().getSelectedItem().equals("N/o")){
                System.out.println(cbtipo.getSelectionModel().getSelectedItem());
                sql ="SELECT * " +
                    "FROM productos " +
                    "INNER JOIN proveedores ON productos.id_proveedor = proveedores.id " +
                    "INNER JOIN tipos ON productos.id_tipo = tipos.id "+                    
                    "WHERE tipos.tipo = '"+cbtipo.getSelectionModel().getSelectedItem()+"' ";
                ObservableList<ProductosModel> list = getPersonList2(sql);
                productTable.getItems().setAll(list);
                
            }
            
        }else{
            System.out.println("Asignar ambos campos");
        }

    }
    
    @FXML
    void regresarTabla(MouseEvent event) {
        ObservableList<ProductosModel> list = getPersonList();
        productTable.getItems().setAll(list);
    }
    
    public ObservableList<ProductosModel> getPersonList2(String sql) {
        ObservableList<ProductosModel> productList = FXCollections.observableArrayList();
        Connection connection = ConnectorMySQL.getConnection();
        String query = sql;
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
    
    @FXML
    void irTipo(ActionEvent event){
        try {
            AnchorPane root2 = (AnchorPane) FXMLLoader.load(getClass().getResource("FXMLVentasTipo.fxml"));
            Scene scene = new Scene(root2);
            Stage primaryLayout = new Stage();
            primaryLayout.setScene(scene);
            primaryLayout.setTitle("FXMLVentasTipo");
            primaryLayout.show();
            Stage nuevaEscena = (Stage) this.btnTipo.getScene().getWindow();
            nuevaEscena.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void userType() {
        if (!PuntoVentas.Main.isAdmin) {

            btnAdd.setDisable(true);
            btnDelete.setDisable(true);
            tFName.setDisable(true);
            tFPrice.setDisable(true);
            tFTamano.setDisable(true);
            cBProveedor.setDisable(true);
            cBType.setDisable(true);
        }
    }

}
