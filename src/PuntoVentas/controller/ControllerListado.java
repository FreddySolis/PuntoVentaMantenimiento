package PuntoVentas.controller;
import java.awt.event.MouseEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ControllerListado {
	@FXML
	private Button Proveedores;
	@FXML
	private Button ventaYflujo;
	@FXML
	public void cargarProveedores(ActionEvent event) {
		try {
                    Parent menu_parent = FXMLLoader.load(getClass().getResource
                    ("../view/FXMLProveedores.fxml"));
                    Scene menu_scene = new Scene(menu_parent);
                    Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    app_stage.hide();
                    app_stage.setScene(menu_scene);
                    app_stage.show();
                    AnchorPane root2 = (AnchorPane)FXMLLoader.load(getClass().getResource("FXMLPuntoVentasPROVEEDORES.fxml")); 
                    Scene scene = new Scene (root2);
                    Stage primaryLayout = new Stage();
                    primaryLayout.setScene(scene);
                    primaryLayout.setTitle("FXMLPuntoVentasPROVEEDORES");
                    primaryLayout.show();
                    Stage nuevaEscena =(Stage) this.Proveedores.getScene().getWindow();
                    nuevaEscena.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@FXML
	private Button Inventario;
	@FXML
	public void cargarInventario(ActionEvent event) {
		try {
                    Parent menu_parent = FXMLLoader.load(getClass().getResource("../view/FXMLPuntoVentasPRODUCTOS.fxml"));
                    Scene menu_scene = new Scene(menu_parent);
                    Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    app_stage.hide();
                    app_stage.setScene(menu_scene);
                    app_stage.show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@FXML
	private Button Usuarios;
	@FXML
	public void cargarUsuarios() {
		try {
			AnchorPane root2 = (AnchorPane)FXMLLoader.load(getClass().getResource("FXMLPuntoVentasUSUARIOS.fxml"));
			Scene scene = new Scene (root2);
			Stage primaryLayout = new Stage();
			primaryLayout.setScene(scene);
			primaryLayout.setTitle("FXMLPuntoVentasUSUARIOS");
			primaryLayout.show();
			Stage nuevaEscena =(Stage) this.Usuarios.getScene().getWindow();
			nuevaEscena.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@FXML
	private Button salirYregresar;
	@FXML
	public void regresarLogin() {
		try {
			AnchorPane root2 = (AnchorPane)FXMLLoader.load(getClass().getResource("FXMLPuntoVentasLOGIN.fxml"));
			Scene scene = new Scene (root2);
			Stage primaryLayout = new Stage();
			primaryLayout.setScene(scene);
			primaryLayout.setTitle("FXMLPuntoVentasLOGIN");
			primaryLayout.show();
			Stage nuevaEscena =(Stage) this.salirYregresar.getScene().getWindow();
			nuevaEscena.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	private Button Reporte;
	@FXML
	public void cargarReporte() {
		try {
			AnchorPane root2 = (AnchorPane)FXMLLoader.load(getClass().getResource("FXMLPuntoVentasREPORTE.fxml"));
			Scene scene = new Scene (root2);
			Stage primaryLayout = new Stage();
			primaryLayout.setScene(scene);
			primaryLayout.setTitle("FXMLPuntoVentasREPORTE");
			primaryLayout.show();
			Stage nuevaEscena =(Stage) this.Reporte.getScene().getWindow();
			nuevaEscena.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private Button RegistrarProducto;
	@FXML
	public void cargarRegistroProducto() {
		try {
			AnchorPane root2 = (AnchorPane)FXMLLoader.load(getClass().getResource("FXMLPuntoVentasRegistroProductos.fxml"));
			Scene scene = new Scene (root2);
			Stage primaryLayout = new Stage();
			primaryLayout.setScene(scene);
			primaryLayout.setTitle("FXMLPuntoVentasRegistroProductos");
			primaryLayout.show();
			Stage nuevaEscena =(Stage) this.RegistrarProducto.getScene().getWindow();
			nuevaEscena.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	
	@FXML
	private Button RegistroProvee;
	@FXML
	public void cargarRegistroProvee() {
		try {
			AnchorPane root2 = (AnchorPane)FXMLLoader.load(getClass().getResource("FXMLPuntoVentasREGISTROProveedor.fxml"));
			Scene scene = new Scene (root2);
			Stage primaryLayout = new Stage();
			primaryLayout.setScene(scene);
			primaryLayout.setTitle("FXMLPuntoVentasREGISTROProveedor");
			primaryLayout.show();
			Stage nuevaEscena =(Stage) this.RegistroProvee.getScene().getWindow();
			nuevaEscena.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void cargarVentaYFlujo(ActionEvent event) {
                    
                    try {
                    Parent menu_parent = FXMLLoader.load(getClass().getResource
                    ("../view/VentasPrueba.fxml"));
                    Scene menu_scene = new Scene(menu_parent);
                    Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    app_stage.hide();
                    app_stage.setScene(menu_scene);
                    app_stage.show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
                    
		
	}
        
        
        
}
