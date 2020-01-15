package view;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import javafx.scene.control.Alert.AlertType;

import javafx.stage.Stage;

public class AddApartmentWindow {

	public void show() {

		try {
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddApartmentWindow.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			stage.setTitle("Add Apartment");
			stage.setResizable(false);
			stage.setScene(scene);
			stage.show();
		}

		catch (IOException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle(e.getClass().getSimpleName());
			alert.setHeaderText("Error!");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}

	}

}
