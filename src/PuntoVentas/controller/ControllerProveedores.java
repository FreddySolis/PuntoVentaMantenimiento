package PuntoVentas.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;

import PuntoVentas.model.ConnectorMySQL;
import PuntoVentas.model.ProveedorModel;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.transformation.FilteredList;
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

public class ControllerProveedores {
    
    @FXML
    private Button Regresar;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfPhoneNumber;
    @FXML
    private TextField tfSearch;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnAdd;
    @FXML
    private TableView<ProveedorModel> providerTable;
    @FXML
    private TextField tFId;
    @FXML
    private TableColumn<ProveedorModel, String> colName;
    @FXML
    private TableColumn<ProveedorModel, String> colEmail;
    @FXML
    private TableColumn<ProveedorModel, String> colPhoneNumber;
    @FXML
    ObservableList<ProveedorModel> proveedoresList = FXCollections.observableArrayList();
    
    @FXML
    private void initialize() {
        colName.setCellValueFactory(new PropertyValueFactory<>("proveedor"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        ObservableList<ProveedorModel> list = getPersonList();
        providerTable.getItems().setAll(list);
        
        final ObservableList<ProveedorModel> tablaProvees = providerTable.getSelectionModel().getSelectedItems();
        tablaProvees.addListener(selectortablaProveedor);
        userType();
    }
    
    public ObservableList<ProveedorModel> getPersonList() {
        ObservableList<ProveedorModel> proveedoresList = FXCollections.observableArrayList();
        Connection connection = ConnectorMySQL.getConnection();
        String query = "SELECT * FROM proveedores";
        Statement st;
        ResultSet rs;
        
        try {
            st = connection.createStatement();
            rs = st.executeQuery(query);
            ProveedorModel Proveedores;
            while (rs.next()) {
                Proveedores = new ProveedorModel(rs.getInt("id"), rs.getString("proveedor"), rs.getString("telefono"), rs.getString("correo"));
                proveedoresList.add(Proveedores);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return proveedoresList;
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
    
    private final ListChangeListener<ProveedorModel> selectortablaProveedor
            = new ListChangeListener<ProveedorModel>() {
        
        @Override
        public void onChanged(Change<? extends ProveedorModel> arg0) {
            ponerProveedorSeleccionada();
            
        }
    };
    
    private void ponerProveedorSeleccionada() {
        try {
            final ProveedorModel proveedor = proveedorSeleccionado();
            if (proveedoresList != null) {
                tfName.setText(proveedor.getProveedor());
                tfEmail.setText(proveedor.getCorreo());
                tfPhoneNumber.setText(proveedor.getTelefono());
                tFId.setText(String.valueOf(proveedor.getId()));
            }
        } catch (Exception e) {
            
        }
        
    }
    
    public ProveedorModel proveedorSeleccionado() {
        if (providerTable != null) {
            List<ProveedorModel> tabla = providerTable.getSelectionModel().getSelectedItems();
            if (tabla.size() == 1) {
                final ProveedorModel seleccionada = tabla.get(0);
                return seleccionada;
            }
        }
        return null;
    }
    
    @FXML
    private void modificar(ActionEvent event) {
        String query = "UPDATE proveedores SET proveedor='" + tfName.getText() + "',correo='" + tfEmail.getText() + "',telefono='" + tfPhoneNumber.getText() + "' WHERE id = " + tFId.getText();
        executeQuery(query);
        ObservableList<ProveedorModel> list = getPersonList();
        providerTable.getItems().setAll(list);
//		
//		 final ObservableList<ProveedorModel> tablaPersonas = tablaProvee.getSelectionModel().getSelectedItems();
//	     tablaPersonas.addListener(selectortablaProveedor);

    }
    
    @FXML
    private void agregar(ActionEvent event) {
        String query = "insert into proveedores (id, proveedor, correo, telefono) VALUES (NULL,'" + tfName.getText() + "','" + tfEmail.getText() + "','" + tfPhoneNumber.getText() + "')";
        executeQuery(query);
        ObservableList<ProveedorModel> list = getPersonList();
        providerTable.getItems().setAll(list);
//		
//		 final ObservableList<ProveedorModel> tablaPersonas = tablaProvee.getSelectionModel().getSelectedItems();
//	     tablaPersonas.addListener(selectortablaProveedor);

    }
    
    @FXML
    private void eliminar(ActionEvent event) {
        String query = "DELETE FROM proveedores WHERE id=" + tFId.getText();
        executeQuery(query);
        ObservableList<ProveedorModel> list = getPersonList();
        providerTable.getItems().setAll(list);
        
        final ObservableList<ProveedorModel> tablaPersonas = providerTable.getSelectionModel().getSelectedItems();
        tablaPersonas.addListener(selectortablaProveedor);
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
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private Button Salir;
    
    @FXML
    public void regresarLogin() {
        try {
            AnchorPane root2 = (AnchorPane) FXMLLoader.load(getClass().getResource("FXMLPuntoVentasLOGIN.fxml"));
            Scene scene = new Scene(root2);
            Stage primaryLayout = new Stage();
            primaryLayout.setScene(scene);
            primaryLayout.setTitle("FXMLPuntoVentasLOGIN");
            primaryLayout.show();
            Stage nuevaEscena = (Stage) this.Salir.getScene().getWindow();
            nuevaEscena.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void userType() {
        if (!PuntoVentas.Main.isAdmin) {
            tfEmail.setDisable(true);
            tfName.setDisable(true);
            tfPhoneNumber.setDisable(true);
            btnAdd.setDisable(true);
            btnEdit.setDisable(true);
            btnDelete.setDisable(true);
        }
    }
    
}
