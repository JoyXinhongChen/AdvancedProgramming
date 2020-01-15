package model;

import java.io.File;
import java.net.MalformedURLException;

import exception.RentException;
import exception.ReturnException;

public class PremiumSuite extends RentalProperty {
	private static int minimum = 1;
	private static int rate = 554;
	private static int lRate = 662;
	private DateTime lmDate;

	public PremiumSuite() {
	}

	public PremiumSuite(String pid, String type, String sn, String sna, String sb, String sts, String description,
			File imageFile, DateTime dt) throws MalformedURLException {
		super(pid, type, sn, sna, sb, 3, sts, description, imageFile);
		this.lmDate = dt;
	}

	public static int getMinimum() {
		return minimum;
	}

	public static int getRate() {
		return rate;
	}

	public static int getlRate() {
		return lRate;
	}

	public DateTime getLmDate() {
		return lmDate;
	}

	public static void setMinimum(int minimum) {
		PremiumSuite.minimum = minimum;
	}

	public static void setRate(int rate) {
		PremiumSuite.rate = rate;
	}

	public static void setlRate(int lRate) {
		PremiumSuite.lRate = lRate;
	}

	public void setLmDate(DateTime lmDate) {
		this.lmDate = lmDate;
	}

	public void rent(String customerId, DateTime rentDate, int numOfRentDay) throws RentException {
		// rent date must be today or in the future
		if (rentDate.diffDays(rentDate, new DateTime(-1)) < 0)
			throw new exception.RentException(3);
		// must achieve minimum rental days
		if (numOfRentDay < getMinimum())
			throw new exception.RentException(2);
		// property under maintenance
		if (new DateTime(rentDate, numOfRentDay).getTime() > new DateTime(lmDate, 10).getTime())
			throw new exception.RentException(4);
		// update status and rental record
		setPropertyStatue("Rented");
		getRecord()[0] = new RentalRecord(getPropertyId(), customerId, rentDate, numOfRentDay);

	}

	public void returnProperty(DateTime returnDate) throws ReturnException {
		if (returnDate.getTime() < getRecord()[0].getRentDate().getTime())
			throw new ReturnException(1);
		// update status, return date and calculate fee
		setPropertyStatue("Available");
		getRecord()[0].setArDate(returnDate);
		getRecord()[0].setRentalFee(new DateTime().diffDays(returnDate, getRecord()[0].getRentDate()) * rate);
		// calculate late fee
		if (new DateTime().diffDays(returnDate, getRecord()[0].getErDate()) > 0) 
			getRecord()[0].setLateFee(lRate * new DateTime().diffDays(returnDate, getRecord()[0].getErDate()));
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
				+ getImageFile().getPath() + ":" + getLmDate().toString() + ":" + this.getDescription();
	}

	@Override
	String getDetails() {
		return null;
	}

//	public String getDetails() {
//		String S;
//		String s1 = String.format("%-20s %s\n", "Property ID:", getPropertyId());
//		String s2 = String.format("%-20s %s\n", "Address:",
//				String.valueOf(getStreetNum()) + " " + getStreetName() + " " + getSuburb());
//		String s3 = String.format("%-20s %s\n", "Type:", getType());
//		String s4 = String.format("%-20s %s\n", "Bedroom:", String.valueOf(getNumBedroom()));
//		String s5 = String.format("%-20s %s\n", "Status:", getPropertyStatue());
//		String s6 = String.format("%-20s %s\n", "Last maintenance:", getLmDate().toString());
//		String s7 = String.format("%-20s ", "RENTAL RECORD:");
//		S = s1 + s2 + s3 + s4 + s5 + s6 + s7;
//		if (getRecord()[0] == null) {
//			String s8 = String.format("%-10s\n", "empty");
//			S += s8;
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
