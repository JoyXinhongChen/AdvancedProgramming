package controller;

import java.io.File;
import java.util.ArrayList;

import exception.AddPropertyException;
import model.Apartment;
import model.DataStorage;

public class AddPropertyHandler {
	//when add property, check all inputs
	private static ArrayList<model.RentalProperty> PL;

	public static void addProperty(String id, String aorP, String stnum, String stname, String suburb, String bednum,
			String lastMaintenanceDate, String desription, File selectImage) throws AddPropertyException {
		PL = model.DataStorage.getRP();
		//update new property ID
		if (aorP.equals("Apartment") && !id.startsWith("A_"))
			id = "A_" + id;
		else if (aorP.equals("Premium Suite") && !id.startsWith("S_"))
			id = "S_" + id;

		if (repeatId(id))// new ID already exist in the Property List.
			throw new AddPropertyException(1);

		// when user add property, he needs to enter all these information, otherwise throw exception
		if (id.equals("") || aorP.isEmpty() || stnum.equals("") || stname.equals("") || suburb.equals("")
				|| bednum.equals(""))
				//|| desription.equals(""))
			throw new AddPropertyException(2);
		else {

			if (aorP.equals("Apartment"))
				try {
					//apartment bednum must from 1 to 3
					if (Integer.parseInt(bednum) < 1 && Integer.parseInt(bednum) > 3)
						throw new AddPropertyException(5);
					//add new property to the ArrayList
					PL.add(new Apartment(id, aorP, stnum, stname, suburb, Integer.parseInt(bednum), "Available",
							desription, selectImage));
				} catch (Exception e) {
					throw new AddPropertyException(2);
				}
			else {//if property type is premium suite, maintenance date is needed.
				try {
					model.DateTime maintenanceDate = new model.DateTime(lastMaintenanceDate);
					System.out.println(maintenanceDate.getFormattedDate());
					System.out.println(maintenanceDate.diffDays(new model.DateTime(-1), maintenanceDate));
					if (maintenanceDate.diffDays(new model.DateTime(-1), maintenanceDate) < 0)
						throw new AddPropertyException(3);
					//check if the maintenance date is appropriate, it must early than current time.
					model.PremiumSuite newSuite = new model.PremiumSuite(id, aorP, stnum, stname, suburb, "Available",
							desription, selectImage, maintenanceDate);
					PL.add(newSuite);

				} catch (Exception e) {
					throw new AddPropertyException(4);
				}

			}

		}

		DataStorage.setRP(PL);
		DataStorage.setSelectImage(null);
	}

	private static boolean repeatId(String id) {
		int i = 0;
		while (i < model.DataStorage.getRP().size()) {
			if (model.DataStorage.getRP().get(i).getPropertyId().equals(id))
				return true;
			i++;
		}
		return false;
	}

}
