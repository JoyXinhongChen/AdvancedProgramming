package view;

import java.io.IOException;

import controller.HomeController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AddPropertyWindow {

	public void show(HomeController homeController) {
		try {
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddPropertyWindow.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			stage.setTitle("Choose Property Type");
			stage.setResizable(false);
			stage.setScene(scene);
			stage.show();
			controller.AddPropertyController controller = loader.<controller.AddPropertyController>getController();
			controller.initVariable(homeController,stage);
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