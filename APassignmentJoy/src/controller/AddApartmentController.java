package controller;

import exception.AddPropertyException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AddApartmentController {

	@FXML
	private AnchorPane streetNoTF;

	@FXML
	private TextArea DescritpionTA;

	@FXML
	private Button ComfirmBtn;

	@FXML
	private Button BackBtn;

	@FXML
	private TextField PropertyID;

	@FXML
	private TextField StreetNoTF;

	@FXML
	private TextField StreetNameTF;

	@FXML
	private TextField SuburbTF;

	@FXML
	private ComboBox<String> BedNumCB;

	@FXML
	private Button UploadImageBtn;

	@FXML
	private static Text path;

	@FXML
	public void initialize() {
		//bedroom number for apartment from 1 to 3
		BedNumCB.getItems().setAll("1", "2", "3");
		ComfirmBtn.setOnAction(e -> {
			handle();
			Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
			stage.close();
		});
		UploadImageBtn.setOnAction(new controller.UploadImageHandler());
		BackBtn.setOnAction(e -> {
			Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
			stage.close();
		});

	}

	public void handle() {
		try {//get input and check
			AddPropertyHandler.addProperty(PropertyID.getText(), "Apartment", StreetNoTF.getText(),
					StreetNameTF.getText(), SuburbTF.getText(), BedNumCB.getSelectionModel().getSelectedItem(), "",
					DescritpionTA.getText(), model.DataStorage.getSelectImage());
			new view.NewWindowForAlert("Property Successfully Added");

		} catch (AddPropertyException e) {
			System.out.println("Wrong Input");

		}
	}
}
