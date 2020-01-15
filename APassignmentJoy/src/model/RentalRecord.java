package model;

public class RentalRecord {
	private String recordId; 
	private String customerId="";
	private DateTime rentDate;
	private DateTime erDate; // estimated return date
	private DateTime arDate = null; // actual return date
	private double rentalFee; 
	private double lateFee; 

	public RentalRecord(String propertyId, String customerId, int setClockForwardInDays) {
		this.rentDate = new DateTime();
		this.erDate = new DateTime(rentDate, setClockForwardInDays);
		this.recordId = propertyId + "_" + customerId + "_" + this.rentDate.getEightDigitDate();
		this.customerId = customerId;
	}

	public RentalRecord(String propertyId, String customerId, int day, int month, int year, int setClockForwardInDays) {
		this.rentDate = new DateTime(day, month, year);
		this.erDate = new DateTime(rentDate, setClockForwardInDays);
		this.recordId = propertyId + "_" + customerId + "_" + this.rentDate.getEightDigitDate();
		this.customerId = customerId;
	}

	public RentalRecord(String propertyId, String customerId, int day, int month, int year, int eday, int emonth,
			int eyear) {
		this.rentDate = new DateTime(day, month, year);
		this.erDate = new DateTime(eday, emonth, eyear);
		this.recordId = propertyId + "_" + customerId + "_" + this.rentDate.getEightDigitDate();
		this.customerId = customerId;
	}

	public RentalRecord(String propertyId, String customerId, DateTime datetime, int setClockForwardInDays) {
		this.rentDate = datetime;
		this.erDate = new DateTime(rentDate, setClockForwardInDays);
		this.recordId = propertyId + "_" + customerId + "_" + this.rentDate.getEightDigitDate();
		this.customerId = customerId;
	}

	public RentalRecord(String propertyId, DateTime rentdate, DateTime estreturndate,
			DateTime actualreturndate, double rentalfee, double latefee) {
		this.recordId = propertyId + "_" + customerId + "_" + rentdate.getEightDigitDate();
		this.rentDate = rentdate;
		this.erDate = estreturndate;
		this.arDate = actualreturndate;
		this.rentalFee = rentalfee;
		this.lateFee = latefee;
	}

	public String getRecordId() {
		return recordId;
	}

	public DateTime getRentDate() {
		return rentDate;
	}

	public DateTime getErDate() {
		return erDate;
	}

	public DateTime getArDate() {
		return arDate;
	}

	public double getRentalFee() {
		return rentalFee;
	}

	public double getLateFee() {
		return lateFee;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public void setRentDate(DateTime rentDate) {
		this.rentDate = rentDate;
	}

	public void setErDate(DateTime erDate) {
		this.erDate = erDate;
	}

	public void setArDate(DateTime arDate) {
		this.arDate = arDate;
	}

	public void setRentalFee(double rentalFee) {
		this.rentalFee = rentalFee;
	}

	public void setLateFee(double lateFee) {
		this.lateFee = lateFee;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String toString() {
		if (getArDate() == null)
			return getRecordId() + ":" + getRentDate().toString() + ":" + getErDate().toString() + ":none:none:none";
		return getRecordId() + ":" + getRentDate().toString() + ":" + getErDate().toString() + ":"
				+ getArDate().toString() + ":" + getRentalFee() + ":" + getLateFee();
	}

	public String getDetails() {
		String s;
		if (getArDate() == null) {
			String s1 = String.format("%-22s %s\n", "Record ID:", getRecordId());
			String s2 = String.format("%-22s %s\n", "Rent Date:", getRentDate().toString());
			String s3 = String.format("%-22s %s\n", "Estimated Return Date:", getErDate().toString());
			s = s1 + s2 + s3;
		} else {
			String s1 = String.format("%-22s %s\n", "Record ID:", getRecordId());
			String s2 = String.format("%-22s %s\n", "Rent Date:", getRentDate().toString());
			String s3 = String.format("%-22s %s\n", "Estimated Return Date:", getErDate().toString());
			String s4 = String.format("%-22s %s\n", "Actual Return Date:", getArDate().toString());
			String s5 = String.format("%-22s %.2f\n", "Rental Fee:", getRentalFee());
			String s6 = String.format("%-22s %.2f\n", "Late Fee:", getLateFee());
			s = s1 + s2 + s3 + s4 + s5 + s6;
		}
		return s;
	}
}
