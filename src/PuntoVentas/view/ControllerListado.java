package PuntoVentas.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
	public void cargarProveedores() {
		try {
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
	public void cargarInventario() {
		try {
			AnchorPane root2 = (AnchorPane)FXMLLoader.load(getClass().getResource("FXMLPuntoVentasPRODUCTOS.fxml"));
			Scene scene = new Scene (root2);
			Stage primaryLayout = new Stage();
			primaryLayout.setScene(scene);
			primaryLayout.setTitle("FXMLPuntoVentasPRODUCTOS");
			primaryLayout.show();
			Stage nuevaEscena =(Stage) this.Inventario.getScene().getWindow();
			nuevaEscena.close();
			
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
//	@FXML
//	private Button Ticket;
//	@FXML
//	public void cargarTicket() {
//		try {
//			AnchorPane root2 = (AnchorPane)FXMLLoader.load(getClass().getResource("FXMLPuntoVentasTICKET.fxml"));
//			Scene scene = new Scene (root2);
//			Stage primaryLayout = new Stage();
//			primaryLayout.setScene(scene);
//			primaryLayout.setTitle("FXMLPuntoVentasTICKET");
//			primaryLayout.show();
//			Stage nuevaEscena =(Stage) this.Ticket.getScene().getWindow();
//			nuevaEscena.close();
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
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
	
//	@FXML
//	private Button FlujoCaja;
//	@FXML
//	public void cargarFlujoCaja() {
//		try {
//			AnchorPane root2 = (AnchorPane)FXMLLoader.load(getClass().getResource("FXMLPuntoVentasFlujoCaja.fxml"));
//			Scene scene = new Scene (root2);
//			Stage primaryLayout = new Stage();
//			primaryLayout.setScene(scene);
//			primaryLayout.setTitle("FXMLPuntoVentasFlujoCaja");
//			primaryLayout.show();
//			Stage nuevaEscena =(Stage) this.FlujoCaja.getScene().getWindow();
//			nuevaEscena.close();
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
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
	public void cargarVentaYFlujo() {
		try {
			AnchorPane root2 = (AnchorPane)FXMLLoader.load(getClass().getResource("FXMLPuntoVentasVENTAS.fxml"));
			Scene scene = new Scene (root2);
			Stage primaryLayout = new Stage();
			primaryLayout.setScene(scene);
			primaryLayout.setTitle("FXMLPuntoVentasVENTAS");
			primaryLayout.show();
			Stage nuevaEscena =(Stage) this.ventaYflujo.getScene().getWindow();
			nuevaEscena.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
