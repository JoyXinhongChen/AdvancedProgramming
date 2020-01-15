package startUp;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class HomePageStartUp extends Application {
	@Override
	public void start(Stage primaryStage) {
		model.DataStorage.initialization();
		controller.DatabaseController.selectAll();

		try {
			Parent root = FXMLLoader.load(getClass().getResource("/view/HomeWindow.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setTitle("Welcome to the FexiRentSystem");
			primaryStage.setScene(scene);
			primaryStage.setResizable(true);
			primaryStage.show();
		} catch (Exception e) {

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle(e.getClass().getSimpleName());
			alert.setHeaderText("Error!");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
