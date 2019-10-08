package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

public class Controller{
	@FXML
	private TextField tf;
	@FXML
	private PasswordField pf;
	@FXML
	private Button btn;

	public void submit(ActionEvent event) throws IOException {
		/*String s1, s2;
		s1 = tf.getText();
		s2 = pf.getText();
		System.out.println(s1 + "" + s2);
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Login Confirmation");
		if(s1.equals("admin") && s2.equals("admin")) {
			//alert.setContentText("Successful");
			//alert.showAndWait();
			
		}
		else {
			System.out.println("Failure");
			alert.setContentText("Login failure due to incorrect User or Pass");
			alert.showAndWait();
		}*/
		
		Parent root = FXMLLoader.load(getClass().getResource("/application/MainWindow.fxml"));
		Scene scene = new Scene(root,800,800);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Stage stg = new Stage();
	    stg.setScene(scene);
		stg.show();
	}
}
