package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import model.RentalProperty;
import view.RentDateSelectWindow;
import view.ReturnDateSelectWindow;
import view.ShowDetailWindow;

public class ShowDetailController {

	@FXML
	private ImageView imageView;

	@FXML
	private Text P_ID;

	@FXML
	private Text Type;

	@FXML
	private Text Address;

	@FXML
	private Text suburb;

	@FXML
	private Text numOfBed;

	@FXML
	private Text status;

	@FXML
	private TextFlow descriptionTA;

	@FXML
	private ListView<String> recordList;

	@FXML
	private Text R_ID;

	@FXML
	private Text Rdate;

	@FXML
	private Text ERdate;

	@FXML
	private Text ARdate;

	@FXML
	private Text Rfee;

	@FXML
	private Text Lfee;

	@FXML
	private Button bButton;//BOOK

	@FXML
	private Button Rbutton;//RETURN

	@FXML
	private Button Mbutton;//MAINTENANCE

	@FXML
	private Button FMbutton;//FINISH MAINTENANCE
	
	@FXML
	private Button deleteButton;

	private RentalProperty p;


	@FXML
	void initialize() {
		
	}

	@FXML
	void showRecord() {
		String s = recordList.getSelectionModel().getSelectedItem();
		if (s.equals("Current Record")) {
			R_ID.setText(p.getRecord()[0].getRecordId());
			Rdate.setText(p.getRecord()[0].getRentDate().getFormattedDate());
			ERdate.setText(p.getRecord()[0].getErDate().getFormattedDate());
			ARdate.setText("");
			Rfee.setText("none");
			Lfee.setText("none");
		} else {
			s = s.replace("Record ", "");
			int i = Integer.parseInt(s);

			R_ID.setText(p.getRecord()[i].getRecordId());
			Rdate.setText(p.getRecord()[i].getRentDate().getFormattedDate());
			ERdate.setText(p.getRecord()[i].getErDate().getFormattedDate());
			ARdate.setText(p.getRecord()[i].getArDate().getFormattedDate());
			Rfee.setText(String.valueOf(p.getRecord()[i].getRentalFee()));
			Lfee.setText(String.valueOf(p.getRecord()[i].getLateFee()));
		}
	}

	//all buttons can not be pressed.
	void setAllButtonDisable() {
		bButton.setDisable(true);
		Rbutton.setDisable(true);
		Mbutton.setDisable(true);
		FMbutton.setDisable(true);
	}
	

	public void initVariable(RentalProperty p2) {

		p = p2;
		imageView.setImage(p.getImage());
		P_ID.setText(p.getPropertyId());
		Type.setText(p.getType());
		Address.setText(p.getStreetNum() + " " + p.getStreetName());
		suburb.setText(p.getSuburb());
		numOfBed.setText(String.valueOf(p.getNumBedroom()));
		status.setText(p.getPropertyStatue());
		//if p.getType().equals("Premium Suite") 
		descriptionTA.getChildren().add(new Text(p.getDescription()));

		if (p.getRecord()[0] != null) {
			recordList.getItems().addAll("Current Record");
		}

		for (int i = 1; i < p.getRecord().length; i++) {
			if (p.getRecord()[i] != null)
				recordList.getItems().add("Record " + i);
		}

		setAllButtonDisable();
		switch (p.getPropertyStatue()) {
		case "Available":
			//can be booked or put into "maintenance"
			bButton.setDisable(false);
			Mbutton.setDisable(false);
			break;
		case "Rented":
			//can be returned 
			Rbutton.setDisable(false);
			break;
		case "Maintenance":
			//can put into "finish maintenance"
			FMbutton.setDisable(false);
			break;
		default:
			break;
		}

		bButton.setOnAction(e -> {
			try {
				RentDateSelectWindow r = new RentDateSelectWindow();
				r.show(p);
				Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
				stage.close();
			} catch (Exception e1) {

			}
		});

		Rbutton.setOnAction(e -> {
			try {
				ReturnDateSelectWindow r = new ReturnDateSelectWindow();
				r.show(p);
				Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
				stage.close();
			} catch (Exception e1) {

			}
		});

		Mbutton.setOnAction(e -> {
			p.performMaintenance();
			Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
			stage.close();
			ShowDetailWindow a = new ShowDetailWindow();
			a.show(p);
		});

		FMbutton.setOnAction(e -> {
			p.completeMaintenance();
			Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
			stage.close();
			ShowDetailWindow a = new ShowDetailWindow();
			a.show(p);
		});
	}
}