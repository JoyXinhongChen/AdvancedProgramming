package model;

import java.io.File;
import java.util.Calendar;

import exception.ReturnException;

public class Apartment extends RentalProperty {
	private int minimum;
	private static int maximum = 28;
	private int rate;

	public Apartment() {
	}

	public Apartment(String pid, String type, String sn, String sna, String sb, int nb, String sts, String description,
			File imageFile) throws Exception {
		super(pid, type, sn, sna, sb, nb, sts, description, imageFile);
		if (nb == 3)
			this.rate = 319;
		else if (nb == 2)
			this.rate = 210;
		else if (nb == 1)
			this.rate = 143;
		this.setLmDate(new DateTime());
	}

	public int getMinimum(DateTime rentDate) {
		// apartment can be rented 2 days or 3 days minimum
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(rentDate.getTime());
		if (calendar.get(Calendar.DAY_OF_WEEK) >= 1 && calendar.get(Calendar.DAY_OF_WEEK) <= 5) {
			minimum = 2;
		} else if (calendar.get(Calendar.DAY_OF_WEEK) == 6 || calendar.get(Calendar.DAY_OF_WEEK) == 7) {
			minimum = 3;
		}
		return minimum;
	}

	public int getRate() {
		return rate;
	}

	public static int getMaximum() {
		return maximum;
	}

	public void rent(String customerId, DateTime rentDate, int numOfRentDay) throws exception.RentException {
		// rent date must be today or in the future.
		if (rentDate.diffDays(rentDate, new DateTime(-1)) < 0)
			throw new exception.RentException(3);
		// must achieve minimum rental days
		if (numOfRentDay < getMinimum(rentDate))
			throw new exception.RentException(2);
		// must less than 28 days
		if (numOfRentDay > getMaximum())
			throw new exception.RentException(1);
		// update status and rental record
		setPropertyStatue("Rented");
		getRecord()[0] = new RentalRecord(getPropertyId(), customerId, rentDate, numOfRentDay);

	}

	public void returnProperty(DateTime returnDate) throws ReturnException {
		// return date must after rent date
		if (returnDate.getTime() < getRecord()[0].getRentDate().getTime())
			throw new ReturnException(1);
		// update status, return date and calculate fee
		setPropertyStatue("Available");
		getRecord()[0].setArDate(returnDate);
		getRecord()[0].setRentalFee(new DateTime().diffDays(returnDate, getRecord()[0].getRentDate()) * rate);
		// calculate late fee
		if (new DateTime().diffDays(returnDate, getRecord()[0].getErDate()) > 0) 
			getRecord()[0].setLateFee(1.15 * rate * new DateTime().diffDays(returnDate, getRecord()[0].getErDate()));
		else
			getRecord()[0].setLateFee(0);
		// update record and delete the old one
		for (int i = getRecord().length - 2; i >= 0; i--) { 
			if (getRecord()[i] != null && i != getRecord().length - 1) {
				getRecord()[i + 1] = getRecord()[i];
			}
		}
	}

	public String toString() {
		return getPropertyId() + ":" + String.valueOf(getStreetNum()) + ":" + getStreetName() + ":" + getSuburb() + ":"
				+ getType() + ":" + String.valueOf(getNumBedroom()) + ":" + getPropertyStatue() + ":"
				+ this.getImageFile().getPath() + ":" + this.getDescription();
	}

	@Override
	String getDetails() {
		return null;
	}

//	public String getDetails() {
//		String S;
//		String s1 = String.format("%-15s %s\n", "Property ID:", getPropertyId());
//		String s2 = String.format("%-15s %s\n", "Address:",
//				String.valueOf(getStreetNum()) + " " + getStreetName() + " " + getSuburb());
//		String s3 = String.format("%-15s %s\n", "Type:", getPropertyType());
//		String s4 = String.format("%-15s %s\n", "Bedroom:", String.valueOf(getNumBedroom()));
//		String s5 = String.format("%-15s %s\n", "Status:", getPropertyStatue());
//		String s6 = String.format("%-15s ", "RENTAL RECORD:");
//		S = s1 + s2 + s3 + s4 + s5 + s6;
//		if (getRecord()[0] == null) {
//			String s7 = String.format("%-10s\n", "empty");
//			S += s7;
//		} else {
//			S += "\n";
//			for (int j = 0; j < getRecord().length; j++) {
//				if (getRecord()[j] != null) {
//					S = S + getRecord()[j].getDetails() + "--------------------------------------\n";
//				}
//			}
//		}
//		return S;
//	}
	//
}
