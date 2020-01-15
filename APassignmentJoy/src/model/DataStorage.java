package model;

import java.io.File;
import java.util.ArrayList;

import model.RentalProperty;

public class DataStorage {
	private static ArrayList <RentalProperty> RP;
	private static File f;
	public static void initialization() {
		RP = new ArrayList <RentalProperty>();
		f = null;
		
	}
	
	public static ArrayList<RentalProperty> getRP() {
		return RP;
	}
	public static void setRP(ArrayList<RentalProperty> rP) {
		RP = rP;
	}
	public static void setSelectImage(File file) {
		f = file;
	}
	
	public static File getSelectImage() {
		return f;
	}
	
}
