package view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NewWindowForAlert {
	public NewWindowForAlert(String string) {
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		Button b = new Button("Ok");
		BorderPane bp = new BorderPane();
		Label label = new Label(string);
		HBox h = new HBox();
		h.setMinWidth(500);
		h.getChildren().add(b);
		h.setAlignment(Pos.CENTER);
		bp.setCenter(label);
		bp.setBottom(h);
		stage.setScene(new Scene(bp, 500, 100));
		stage.show();

		b.setOnAction(e -> {
			stage.close();
		});
	}
}
