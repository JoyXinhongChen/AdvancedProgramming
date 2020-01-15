package controller;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import model.DataStorage;

public class HomeController {
	@FXML
	private MenuBar menubar;

	@FXML
	private MenuItem saveButton;

	@FXML
	private MenuItem deleteButton;

	@FXML
	private MenuItem ImportButton;

	@FXML
	private MenuItem ExportButton;

	@FXML
	private MenuItem AddButton;

	@FXML
	private ComboBox<String> propertyType;

	@FXML
	private ComboBox<String> suburb;

	@FXML
	private ComboBox<String> status;

	@FXML
	private ComboBox<String> bedroomNo;

	@FXML
	private FlowPane flowpane;

	@FXML
	private TextField searchText;

	@FXML
	private ScrollPane sp;

	private ObservableList<String> list4 = FXCollections.observableArrayList();

	@FXML
	public void initialize() {

		flowpane.prefWidthProperty().bind(sp.widthProperty());
		setMenu();
		setFilter();
		change();
	}

	private void setMenu() {
		ImportButton.setOnAction(e -> {
			try {
				controller.FileController.importFile();
				change();

			} catch (Exception e1) {
			
				e1.printStackTrace();
			}
		});

		ExportButton.setOnAction(e -> {
			try {
				controller.FileController.exportFile();

			} catch (Exception e1) {

				e1.printStackTrace();
			}

		});

		AddButton.setOnAction(e -> {
			view.AddPropertyWindow n = new view.AddPropertyWindow();
			n.show(this);
		});
		
	}

	private void setFilter() {
		
		ObservableList<String> list1 = FXCollections.observableArrayList("All", "Apartment", "Premium Suite");
		ObservableList<String> list2 = FXCollections.observableArrayList("All", "Available", "Rented", "Maintenance");
		ObservableList<String> list3 = FXCollections.observableArrayList("All", "1", "2", "3");
		list4.add("All");
		for (int i = 0; i < DataStorage.getRP().size(); i++) {
			if (!list4.contains(DataStorage.getRP().get(i).getSuburb()))
				list4.add(DataStorage.getRP().get(i).getSuburb());
		}
		suburb.setItems(list4);
		propertyType.setItems(list1);
		status.setItems(list2);
		bedroomNo.setItems(list3);
		propertyType.setValue("All");
		bedroomNo.setValue("All");
		status.setValue("All");
		suburb.setValue("All");

		// Add listener to all the comboBox which will change the showing list if one of
		// the filter being changed
	}

	public void addOnePropertytoContent(model.RentalProperty p) {
//		one ProperyViewHbox shows one Property's information,
//		ProperyViewHbox = ImageView + ProperyDetailsVbox.
		HBox ProperyViewHbox = new HBox();

		// ProperyViewHbox
		ProperyViewHbox.setMaxWidth(700);
		ProperyViewHbox.setMinWidth(700);
		ProperyViewHbox.setSpacing(10);
		ProperyViewHbox.setPadding(new Insets(20, 5, 20, 5));

		// ImageView
		ImageView iv = new ImageView(p.getImage());
		iv.setFitHeight(300);
		iv.setFitWidth(300);

		// ProperyDetailsVbox
		VBox ProperyDetailsVbox = new VBox();
		ProperyDetailsVbox.setSpacing(10);
		ProperyDetailsVbox.setPadding(new Insets(0, 5, 5, 5));
		Text PropertyIDText = new Text("Property ID: " + p.getPropertyId());
		PropertyIDText.setFont(Font.font("Verdana", 20));
		PropertyIDText.setFill(Color.CORNFLOWERBLUE);
		Text PropertyAddressText = new Text(
				"Address: " + p.getStreetNum() + " " + p.getStreetName() + " " + p.getSuburb());
		TextFlow descriptionTA = new TextFlow();
		descriptionTA.getChildren().add(new Text("Description: \n" + p.getDescription()));
		descriptionTA.setPrefSize(600, 100);
		
		Button b = new Button("More Details");
		b.setOnAction(e -> {
			view.ShowDetailWindow n = new view.ShowDetailWindow();
			n.show(p);
			System.out.println("Showing property details");
		});

		ProperyDetailsVbox.getChildren().addAll(PropertyIDText, PropertyAddressText, descriptionTA, b);
		ProperyViewHbox.getChildren().addAll(iv, ProperyDetailsVbox);
		flowpane.getChildren().add(ProperyViewHbox);
	}

	private void clear() {
		flowpane.getChildren().clear();
	}

	public ComboBox<String> getPropertyType() {
		return propertyType;
	}

	public ComboBox<String> getSuburb() {
		return suburb;
	}

	public ComboBox<String> getStatus() {
		return status;
	}

	public ComboBox<String> getBedroomNo() {
		return bedroomNo;
	}

	@FXML
	public void change() {
		// change view
		ArrayList<model.RentalProperty> PL = model.DataStorage.getRP();
		int[] mark = new int[PL.size()];
		int bednum;
		if (getBedroomNo().getValue() != "All")
			bednum = Integer.parseInt((String) getBedroomNo().getValue());
		else
			bednum = -1;
		String Type = (String) getPropertyType().getValue();
		String Status = (String) getStatus().getValue();
		String Suburb = (String) getSuburb().getValue();
		for (int i = 0; i < mark.length; i++) {
			mark[i] = 0;// not visible
		}

		for (int i = 0; i < PL.size(); i++) {
			if ((PL.get(i).getType().equals(Type) || Type.equals("All"))
					&& (PL.get(i).getPropertyStatue().equals(Status) || Status.equals("All"))
					&& (PL.get(i).getSuburb().equals(Suburb) || Suburb.equals("All"))
					&& ((PL.get(i).getNumBedroom() == bednum) || bednum == -1)
					&& (PL.get(i).getPropertyId().toLowerCase().contains(searchText.getText())
							|| searchText.getText().replaceAll(" ", "").isEmpty()))
				mark[i] = 1;

		}

		clear();

		int i = 0;
		while (i < PL.size()) {
			if (mark[i] == 1) {
				//display all properties that meet user's choice
				model.RentalProperty p = PL.get(i);
				addOnePropertytoContent(p);
			}

			i++;
		}
       //for new property, just added
		for (int j = 0; j < DataStorage.getRP().size(); j++) {
			if (!list4.contains(DataStorage.getRP().get(j).getSuburb()))
				list4.add(DataStorage.getRP().get(j).getSuburb());
		}

	}

	@FXML
	void Save() {
		//save to database
		controller.DatabaseController.deleteTable();
		controller.DatabaseController.create1();
		controller.DatabaseController.create2();

		for (int i = 0; i < DataStorage.getRP().size(); i++)
			controller.DatabaseController.insert(DataStorage.getRP().get(i));
		System.out.println(1);
	}

	@FXML
	void Delete() {
		controller.DatabaseController.deleteTable();
		controller.DatabaseController.create1();
		controller.DatabaseController.create2();
		DataStorage.initialization();
		change();
	}
	

}
