/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PuntoVentas.controller;

import PuntoVentas.model.ProductosModel;
import PuntoVentas.model.TipoModel;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author PC1
 */
public class ControllerTipo implements Initializable{
    @FXML
	private Button btnNuevo;
    @FXML
	private Button btnInv;
    @FXML
	private Button btnEditar;
    @FXML
	private Button btnEliminar;
    @FXML
	private TextField tfNombre;
    @FXML
        private Label lblId;
    @FXML
        ObservableList<TipoModel> tipoList = FXCollections.observableArrayList();
    @FXML
    private TableView<TipoModel> tipoTable;
    @FXML
    private TableColumn<TipoModel, String> colTipo;
    @FXML
    private TableColumn<TipoModel, String> colId;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        ObservableList<TipoModel> list = getTiposList();
        tipoTable.getItems().setAll(list);
        
        final ObservableList<TipoModel> tablaTipo = tipoTable.getSelectionModel().getSelectedItems();
        tablaTipo.addListener(selectorTablaTipo);
        //userType();
        }
    
        @FXML
    private void eliminar(ActionEvent event) {
        String query = "DELETE FROM tipos WHERE id=" + tipoTable.getSelectionModel().getSelectedItem().getId();
        executeQuery(query);
        ObservableList<TipoModel> list = getTiposList();
        tipoTable.getItems().setAll(list);

        final ObservableList<TipoModel> tablaPersonas = tipoTable.getSelectionModel().getSelectedItems();
        tablaPersonas.addListener(selectorTablaTipo);
    }
        @FXML
    private void modificar(ActionEvent event) {
        String query = "UPDATE `tipos` SET `tipo` = '"+tfNombre.getText()+"' WHERE `tipos`.`id` = "+lblId.getText() + ";";
        executeQuery(query);
        ObservableList<TipoModel> list = getTiposList();
        tipoTable.getItems().setAll(list);

        final ObservableList<TipoModel> tablaPersonas = tipoTable.getSelectionModel().getSelectedItems();
        tablaPersonas.addListener(selectorTablaTipo);

    }

    public ObservableList<TipoModel> getTiposList() {
        ObservableList<TipoModel> tipoList = FXCollections.observableArrayList();
        Connection connection = ConnectorMySQL.getConnection();
        String query = "SELECT * FROM tipos";   
        Statement st;
        ResultSet rs;

        try {
            st = connection.createStatement();
            rs = st.executeQuery(query);
            TipoModel Tipos;
            while (rs.next()) {
                Tipos = new TipoModel(rs.getInt("id"), rs.getString("tipo"));
                Tipos.setId(rs.getInt("id"));
                Tipos.setTipo(rs.getString("tipo"));
                tipoList.add(Tipos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tipoList;
    }
        
    public void userType() {
        if (!PuntoVentas.Main.isAdmin) {
            btnNuevo.setDisable(true);
            btnEliminar.setDisable(true);
            btnEditar.setDisable(true);
            tfNombre.setDisable(true);
        }
    }
    
    public void CrearTipo()throws SQLException{
		Connection cn = ConnectorMySQL.getConnection();
        try {
        	System.out.println("HOLI agregar Tipo");
            String sSQLL = "INSERT INTO tipos (id,tipo) VALUES(?,?)";
            PreparedStatement stt = cn.prepareStatement(sSQLL);
            stt.setString(1,(null));
            stt.setString(2,(tfNombre.getText()));
            stt.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
        tfNombre.clear();
//        cajaPrecio.clear();
//        cajanombreProveedor.clear();
    }

    private final ListChangeListener<TipoModel> selectorTablaTipo
            = new ListChangeListener<TipoModel>() {
        @Override
        public void onChanged(ListChangeListener.Change<? extends TipoModel> arg0) {
            ponerTipoSeleccionada();
        }
    };
    
        private void ponerTipoSeleccionada() {
        try {
            final TipoModel tipo = tipoSeleccionado();
            if (tipoList != null) {
                lblId.setText(String.valueOf(tipo.getId()));
                tfNombre.setText(tipo.getTipo());
            }
        } catch (Exception e) {

        }
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
    public TipoModel tipoSeleccionado() {
        if (tipoTable != null) {
            List<TipoModel> tabla = tipoTable.getSelectionModel().getSelectedItems();
            if (tabla.size() == 1) {
                final TipoModel seleccionada = tabla.get(0);
                return seleccionada;
            }
        }
        return null;
    }
        
    @FXML
    public void regresarInventario() {
        try {
            AnchorPane root2 = (AnchorPane) FXMLLoader.load(getClass().getResource("FXMLInventario.fxml"));
            Scene scene = new Scene(root2);
            Stage primaryLayout = new Stage();
            primaryLayout.setScene(scene);
            primaryLayout.setTitle("FXMLInventario");
            primaryLayout.show();
            Stage nuevaEscena = (Stage) this.btnInv.getScene().getWindow();
            nuevaEscena.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
