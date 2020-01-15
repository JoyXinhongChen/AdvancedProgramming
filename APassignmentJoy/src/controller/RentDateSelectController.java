package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.RentalProperty;
import view.NewWindowForAlert;
import view.ShowDetailWindow;

public class RentDateSelectController {
	@FXML
	private Button okButton;

	@FXML
	private TextField customerID;

	@FXML
	private DatePicker date;

	@FXML
	private TextField numOfDays;

	private RentalProperty p;

	@FXML
	public void initialize() {
	}

	
	
	@FXML
	public void OK(ActionEvent event) {
		try {
			String s=date.getEditor().getText();
			if (s!=null) {
			String[] ss = s.split("/");
			 s= ss[1] + ss[0] + ss[2];}
			
			// user needs to enter customerID, date and numOfDays
			if (customerID.equals("") || s.equals("") || numOfDays.equals("")) {
				throw new Exception();
			}
			p.rent(customerID.getText(), new model.DateTime(s),
					Integer.parseInt(numOfDays.getText()));
			
			Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
			stage.close();
			ShowDetailWindow sd = new ShowDetailWindow();
			sd.show(p);
			
			new NewWindowForAlert("Book successfully");
		} catch (Exception e1) {
			new view.NewWindowForAlert("Input invalid");
		}
	}



	public void initalVariable(RentalProperty p) {
		this.p = p;
	}





}
