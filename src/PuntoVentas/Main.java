package PuntoVentas;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;



public class Main extends Application {
    public static String path = "";
        public static boolean isAdmin;
	private Stage primaryStage;
	private AnchorPane root;
	@Override
	public void start(Stage primaryStage) {
		try {
			this.primaryStage= primaryStage;
			
			FXMLLoader loader = new FXMLLoader();

			loader.setLocation(Main.class.getResource("../PuntoVentas/view/FXMLLogin2.fxml"));

			AnchorPane root = (AnchorPane) loader.load();
			
			//Show the scene containing the root layout
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
                        primaryStage.initStyle(StageStyle.TRANSPARENT);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
	/*private Stage primaryStage;
	private AnchorPane root;
	@Override
	public void start(Stage primaryStage) {
		try {
			this.primaryStage= primaryStage;
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/FXMLPuntoVentasLOGIN.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			
			//Show the scene containing the root layout
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}*/
}
