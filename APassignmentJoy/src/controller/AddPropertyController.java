package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import view.AddApartmentWindow;
import view.AddPremiumSuiteWindow;

public class AddPropertyController {
	@FXML
	private Button addApartmentBtn;

	@FXML
	private Button addPremiumSuiteBtn;

	@FXML
	private Button homepageBtn;

	@FXML
	public void initialize() {

	}

	public void initVariable(HomeController homeController, Stage stage2) {
		//add apartment button-> add apartment window
		AddApartmentWindow addApartmentWindow = new AddApartmentWindow();
		addApartmentBtn.setOnAction(e -> addApartmentWindow.show());
		//add premium suite button-> add premium suite window
		AddPremiumSuiteWindow addPremiumSuiteWindow = new AddPremiumSuiteWindow();
		addPremiumSuiteBtn.setOnAction(e -> addPremiumSuiteWindow.show());

		homepageBtn.setOnAction(e -> {
			Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
			stage.close();
			homeController.change();
		});

		stage2.setOnCloseRequest(e -> {
			homeController.change();
			stage2.close();
		});

	}
}
