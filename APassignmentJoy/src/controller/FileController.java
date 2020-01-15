package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DateTime;
import model.RentalProperty;
import model.DataStorage;
import model.RentalRecord;

public class FileController {
	private static File F = null;

	public static void importFile() throws Exception {
		ArrayList<RentalProperty> RP = DataStorage.getRP();
		if (choose()) {
			Scanner input = null;
			String line = null;
			String[] ss;
			RentalRecord[] records = new RentalRecord[11];
			for (int i = 0; i < 11; i++) {
				records[i] = null;
			}
			int i = 1;
			model.RentalProperty p = null;
			int number = 0;
			try {
				input = new Scanner(new FileInputStream(F.getAbsolutePath()));

			} catch (FileNotFoundException e) {
				System.err.println("No Such File.");
				System.exit(0);
			}

			while (input.hasNextLine()) {
				// to do
				line = input.nextLine();
				System.out.println(line);
				ss = line.split(":");
				if (ss.length == 6) {
					if (p.getPropertyStatue().equals("Rented") && ss[5].equals("none")) {
						RentalRecord r = new RentalRecord(ss[0], new model.DateTime(ss[1]), new model.DateTime(ss[2]),
								null, 0, 0);
						records[0] = r;
					} else {
						RentalRecord r = new RentalRecord(ss[0], new model.DateTime(ss[1]), new model.DateTime(ss[2]),
								new model.DateTime(ss[3]), Double.parseDouble(ss[4]), Double.parseDouble(ss[5]));
						records[i] = r;
						i++;
						p.setRentRecords(records);
					}
				} else {
					if (number != 0) {
						p.setRentRecords(records);
						RP.add(p);
					}
					if (ss.length == 9)
						p = new model.Apartment(ss[0], ss[4], ss[1], ss[2], ss[3], Integer.parseInt(ss[5]), ss[6],
								ss[8], new File(ss[7]));
					else if (ss.length == 10) {
						// Integer.parseInt(ss[5])
						p = new model.PremiumSuite(ss[0], ss[4], ss[1], ss[2], ss[3], ss[6], ss[9],
								new File(ss[7]), new DateTime(ss[8]));
					}

					number++;
					i = 1;
					records = new RentalRecord[11];
					for (int j = 0; j < 11; j++) {
						records[j] = null;
					}
				}
				System.out.println(line);

			}
			if (number != 0) {
				RP.add(p);
			}
			DataStorage.setRP(RP);
			input.close();

		}
	}

	public static void exportFile() throws Exception {
		if (saver()) {
			File f = new File(F.getAbsolutePath() + "/ExportFile.txt");
			try {
				PrintWriter output = new PrintWriter(f);
				for (int i = 0; i < DataStorage.getRP().size(); i++) {
					output.write(DataStorage.getRP().get(i).toString() + "\n");
					System.out.println(model.DataStorage.getRP().get(i).toString());
					if (DataStorage.getRP().get(i).getPropertyStatue().equals("Rented")) {
						output.write(DataStorage.getRP().get(i).getRecord()[0].toString() + "\n");
						System.out.println(DataStorage.getRP().get(i).getRecord()[0].toString());
					}
					for (int j = 1; j < 11; j++) {
						if (DataStorage.getRP().get(i).getRecord()[j] == null)
							break;
						output.write(DataStorage.getRP().get(i).getRecord()[j].toString() + "\n");
						System.out.println(DataStorage.getRP().get(i).getRecord()[j]);
					}
				}

				output.close(); // don't forget this method
			} catch (FileNotFoundException e) {
				System.out.println(e.getMessage());
			}
		}

	}

	public static boolean choose() {
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Upload Data");
		FileChooser F1 = new FileChooser();
		F = F1.showOpenDialog(stage);
		if (F != null)
			return true;
		else
			return false;
	}

	public static boolean saver() {
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Export Data");
		DirectoryChooser F1 = new DirectoryChooser();
		F = F1.showDialog(stage);
		System.out.println(F.getAbsolutePath());
		if (F != null)
			return true;
		else
			return false;
	}
}