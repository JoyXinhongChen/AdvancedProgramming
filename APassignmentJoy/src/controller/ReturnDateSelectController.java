package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import model.DateTime;
import model.RentalProperty;
import view.NewWindowForAlert;
import view.ShowDetailWindow;

public class ReturnDateSelectController {
	@FXML
	private Button okButton;

	@FXML
	private DatePicker date;

	private RentalProperty p;
	
	@FXML
	public void initialize() {
	}


	@FXML
	public void OK(ActionEvent event) {
		try {
			String s=date.getEditor().getText();
			//exception: if user did not pick the date
			if (s.equals("")) {
				throw new Exception();
			}
			if (s!=null) {
				String[] ss = s.split("/");
				 s= ss[1] + ss[0] + ss[2];}
			
			
			p.returnProperty(new DateTime(s));

			Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
			stage.close();
			ShowDetailWindow sd = new ShowDetailWindow();
			sd.show(p);

			new NewWindowForAlert("Return successfully");
		} catch (exception.ReturnException e1) {
			new view.NewWindowForAlert("Input invalid");

		} catch (Exception e1) {
			
		}
	}
	
	public void initalVariable(RentalProperty p) {
		this.p = p;
	}

}
