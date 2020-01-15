package model;

import java.io.File;
import java.net.MalformedURLException;

import exception.RentException;
import exception.ReturnException;
import javafx.scene.image.Image;

public abstract class RentalProperty {
	private String propertyId;
	private String streetNum;
	private String streetName, description;
	private String suburb;
	private int numBedroom;
	private String propertyStatue;
	private File imageFile;
	private String type;
	private Image image;

	private RentalRecord[] record = new RentalRecord[11];
	private DateTime lmDate;

	public RentalProperty() {
	}

	public RentalProperty(String pid, String type, String sn, String sna, String sb, int nb, String sts,
			String description, File imageFile) throws MalformedURLException {
		this.propertyId = pid;
		this.type = type;
		this.streetNum = sn;
		this.streetName = sna;
		this.suburb = sb;
		this.numBedroom = nb;
		this.propertyStatue = sts;
		this.description = description;

		if (imageFile == null) {
			this.imageFile = new File("Files/Image-Unavailable.jpg");

		} else
			this.imageFile = imageFile;
		
		this.image = new Image(this.imageFile.toURI().toURL().toString());

	}

	public Image getImage() {
		return image;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPropertyId() {
		return propertyId;
	}

	public String getStreetNum() {
		return streetNum;
	}

	public String getStreetName() {
		return streetName;
	}

	public String getSuburb() {
		return suburb;
	}

	public int getNumBedroom() {
		return numBedroom;
	}

	public String getDescription() {
		return description;
	}

	public File getImageFile() {
		return imageFile;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setImageFile(File imageFile) {
		this.imageFile = imageFile;
	}

	public void setRecord(RentalRecord[] record) {
		this.record = record;
	}

	public String getPropertyStatue() {
		return propertyStatue;
	}

	public RentalRecord[] getRecord() {
		return record;
	}

	public void setPropertyId(String propertyId) {
		this.propertyId = propertyId;
	}

	public void setStreetNum(String streetNum) {
		this.streetNum = streetNum;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public void setSuburb(String suburb) {
		this.suburb = suburb;
	}

	public void setNumBedroom(int numBedroom) {
		this.numBedroom = numBedroom;
	}

	public void setPropertyStatue(String propertyStatue) {
		this.propertyStatue = propertyStatue;
	}

	public abstract void rent(String customerId, DateTime rentDate, int numOfRentDay) throws RentException;

	public abstract void returnProperty(DateTime returnDate) throws ReturnException;

	public void performMaintenance() {
		setPropertyStatue("Maintenance"); 
	}

	public void completeMaintenance() {
		//record maintenance date and set property status
		this.setLmDate(new DateTime());
		setPropertyStatue("Available"); 

	}

	abstract String getDetails();

	public String toString() {
		return propertyId + ":" + String.valueOf(streetNum) + ":" + streetName + ":" + suburb + ":" + type + ":"
				+ String.valueOf(numBedroom) + ":" + propertyStatue + ":" + description;
	}

	public void setRentRecords(RentalRecord[] records) {
		record = records;
	}

	public DateTime getLmDate() {
		return lmDate;
	}

	public void setLmDate(DateTime lmDate) {
		this.lmDate = lmDate;
	}
}
