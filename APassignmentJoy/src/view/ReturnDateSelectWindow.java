package view;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.RentalProperty;

public class ReturnDateSelectWindow {
	
	
	public void show(RentalProperty p) {
		try {
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/RentDateSelect.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			stage.setResizable(false);
			stage.setScene(scene);
			stage.show();
			//pass p to controller
			controller.ReturnDateSelectController controller = loader.<controller.ReturnDateSelectController>getController();
			controller.initalVariable(p);
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
