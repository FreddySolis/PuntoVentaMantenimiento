/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PuntoVentas.controller;


import PuntoVentas.model.UsersModel;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 *
 * @author ayax9
 */
public class ControllerUsuarios  implements Initializable{
    
    private ConnectorMySQL conexion;
    
    public static UsersModel usuarioActivo;
    public static UsersModel usuarioseleccionado;
    
    public static FXMLMenuAdminController controllerMenu;
            
    @FXML
    private TableView<UsersModel> tbUsuarios;

    @FXML
    private TableColumn <UsersModel, String> colid;

    @FXML
    private TableColumn <UsersModel, String> colNombre;

    @FXML
    private TableColumn <UsersModel, String> colApellidos;

    @FXML
    private TableColumn <UsersModel, String> colCorreo;

    @FXML
    private TableColumn <UsersModel, String> colNumero;

    @FXML
    private TableColumn <UsersModel, String> colUsername;

    @FXML
    private TableColumn <UsersModel, String> colPassword;
    
    @FXML
    private TableColumn <UsersModel, String> colAdmin;

    
    @FXML
    private Button btn_Eliminar;

    @FXML
    private Label lbError;

    @FXML
    private Button btn_actualizar;

    @FXML
    private Button btn_Crear;
    
    @FXML
    ObservableList<UsersModel> usersList = FXCollections.observableArrayList();
    
    
    
    public void initialize(URL location, ResourceBundle resources) {
        colid.setCellValueFactory(new PropertyValueFactory<>("id_user"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colAdmin.setCellValueFactory(new PropertyValueFactory<>("admin"));
        cargarUsuarios();        
        System.out.println(usuarioActivo.get_id_info());
        System.out.println(usuarioActivo.get_id_user());
        
        
         
    }

    @FXML
    void Eliminar(ActionEvent event) {
        if(usuarioseleccionado.get_id_user() != usuarioActivo.get_id_user()){
            if(UsersModel.eliminar_usuario(conexion, usuarioseleccionado.get_id_user() ) == 1){
                lbError.setText("Eliminacion exitosa");
                cargarUsuarios();
            }            
            
        }else{
            lbError.setText("Eliminacion denegada");
        }
        
    }

    @FXML
    void cargarActualizar(ActionEvent event) {
        try {
            if(usuarioseleccionado != null){
                ControllerActualizarUsuario.user = usuarioseleccionado;
                createPage("FXMLPuntoVentasActualizarUsuario");
                
            }else{
                lbError.setText("Usuario no seleccionado");
            }
           

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void cargarCrear(ActionEvent event) {
        try {
            createPage("FXMLPuntoVentasREGISTRO");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

   
    
    
    private AnchorPane home;
    public void createPage(String inter) {
        try {
            home = FXMLLoader.load(getClass().getResource("../view/" + inter + ".fxml"));
            setNode(home);
        } catch (IOException ex) {
            Logger.getLogger(FXMLMenuAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void setNode(Node node) {
        controllerMenu.apContenedor.getChildren().clear();
        controllerMenu.apContenedor.getChildren().add((Node) node);
        FadeTransition ft = new FadeTransition(Duration.millis(500));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();

    }
    
    private void cargarUsuarios(){
        usersList = UsersModel.all_user_delete(conexion);       
        if(tbUsuarios != null){
            tbUsuarios.getItems().clear();
        }
        System.out.println(usersList);
        for(UsersModel aux:usersList){
           
            tbUsuarios.setItems(usersList);
        }
        
        System.out.println(tbUsuarios.getItems());
        
    }   
    @FXML
    void filaSeleccionada(MouseEvent event) {
        ObservableList<UsersModel> selectedIndices = tbUsuarios.getSelectionModel().getSelectedItems();
        usuarioseleccionado =  selectedIndices.get(0);
        System.out.println(usuarioseleccionado);
    }
    
     
}
