package controller;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.DateTime;
import model.RentalProperty;
import model.RentalRecord;

public class DatabaseController {
	private static final String DB_NAME = "testDB";
	private static final String table1 = "PROPERTY";
	private static final String table2 = "RECOREDS";

	public static void connect() {

		// use try-with-resources Statement
		try (Connection con = getConnection(DB_NAME)) {
			System.out.println("Connection to database " + DB_NAME + " created successfully");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void create1() {

		// use try-with-resources Statement
		try (Connection con = getConnection(DB_NAME); Statement stmt = con.createStatement();) {
			int result = stmt.executeUpdate("CREATE TABLE PROPERTY (" + "id VARCHAR(20) NOT NULL,"
					+ "type VARCHAR(20) NOT NULL," + "stnum VARCHAR(8) NOT NULL," + "stname VARCHAR(20) NOT NULL,"
					+ "suburb VARCHAR(20) NOT NULL," + "bednum INT NOT NULL," + "description VARCHAR(4000) NOT NULL,"
					+ "status VARCHAR(20) NOT NULL," + "lastmaintain VARCHAR(12),"
					+ "imagelocation VARCHAR(200) NOT NULL," + "PRIMARY KEY (id))");
			if (result == 0) {
				System.out.println("Table " + table1 + " has been created successfully");
			} else {
				System.out.println("Table " + table1 + " is not created");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void create2() {
		try (Connection con = getConnection(DB_NAME); Statement stmt = con.createStatement();) {
			int result = stmt.executeUpdate("CREATE TABLE RECOREDS (" + "PROPERTY_ID VARCHAR(20) NOT NULL,"
					+ "RECORD_ID VARCHAR(300) NOT NULL," + "CUSTOMER_ID VARCHAR(20)," + "RENTDATE VARCHAR(20) NOT NULL,"
					+ "ESTMATEDATE VARCHAR(20) NOT NULL," + "ACTUALRETURNDATE VARCHAR(20) NOT NULL,"
					+ "RENTALFEE DOUBLE NOT NULL," + "LATEFEE DOUBLE NOT NULL," + "PRIMARY KEY (RECORD_ID))");
			if (result == 0) {
				System.out.println("Table " + table2 + " has been created successfully");
			} else {
				System.out.println("Table " + table2 + " is not created");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public static void insert(RentalProperty p) {
		// use try-with-resources Statement
		try (Connection con = getConnection(DB_NAME); Statement stmt = con.createStatement();) {
			String query = "INSERT INTO " + table1 + " VALUES ('" + p.getPropertyId() + "', '" + p.getType() + "', '"
					+ p.getStreetNum() + "', '" + p.getStreetName() + "', '" + p.getSuburb() + "', " + p.getNumBedroom()
					+ ", '" + p.getDescription() + "', '" + p.getPropertyStatue() + "', '"
					+ p.getLmDate().getFormattedDate() + "', '" + p.getImageFile().getPath() + "')";

			int result = stmt.executeUpdate(query);

			con.commit();
			System.out.println("Insert into table " + table1 + " executed successfully");
			System.out.println(result + " row(s) affected");

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		try (Connection con = getConnection(DB_NAME); Statement stmt = con.createStatement();) {

			if (p.getPropertyStatue().equals("Rented")) {
				String query = "INSERT INTO " + table2 + " VALUES ('" + p.getPropertyId() + "', '"
						+ p.getRecord()[0].getRecordId() + "', '" + "null" + "', '"
						+ p.getRecord()[0].getRentDate().getFormattedDate() + "', '"
						+ p.getRecord()[0].getErDate().getFormattedDate() + "', '" + " " + "', " + -1 + ", " + -1
						+ ")";
				int result = stmt.executeUpdate(query);
				con.commit();
				System.out.println(query);
				System.out.println("Insert into table " + table2 + " executed successfully");
				System.out.println(result + " row(s) affected");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		model.RentalRecord[] rentRecords = p.getRecord();
		for (int i = 1; i < rentRecords.length; i++) {
			if (rentRecords[i] != null) {
				try (Connection con = getConnection(DB_NAME); Statement stmt = con.createStatement();) {
					String query = "INSERT INTO " + table2 + " VALUES ('" + p.getPropertyId() + "', '"
							+ rentRecords[i].getRecordId() + "', '" + rentRecords[i].getCustomerId() + "', '"
							+ rentRecords[i].getRentDate().getFormattedDate() + "', '"
							+ rentRecords[i].getErDate().getFormattedDate() + "', '"
							+ rentRecords[i].getArDate().getFormattedDate() + "', " + rentRecords[i].getRentalFee()
							+ ", " + rentRecords[i].getLateFee() + ")";
					System.out.println(query);
					int result = stmt.executeUpdate(query);
					con.commit();
					System.out.println("Insert into table " + table2 + " executed successfully");
					System.out.println(result + " row(s) affected");
				} catch (

				Exception e) {
					System.out.println(e.getMessage());
				}
			} else
				break;
		}
	}

	public static void selectAll() {
		ArrayList<RentalProperty> propertylist = new ArrayList<RentalProperty>();
		propertylist = selectTable1(propertylist);
		model.DataStorage.setRP(propertylist);
	}

	public static ArrayList<RentalProperty> selectTable1(ArrayList<RentalProperty> propertylist) {
		String id;
		String stname;
		String suburb;
		String type;
		String status;
		String stnum;
		int bednum;
		DateTime lastMaintenance;
		File imageFile;
		String Description;
		RentalProperty p;

		try (Connection con = getConnection(DB_NAME); Statement stmt = con.createStatement();) {
			String query = "SELECT * FROM " + table1;

			try (ResultSet resultSet = stmt.executeQuery(query)) {
				while (resultSet.next()) {
					id = resultSet.getString("id");
					type = resultSet.getString("type");
					stnum = resultSet.getString("stnum");
					stname = resultSet.getString("stname");
					suburb = resultSet.getString("suburb");
					bednum = resultSet.getInt("bednum");
					Description = resultSet.getString("description");
					status = resultSet.getString("status");
					lastMaintenance = new DateTime(resultSet.getString("lastmaintain"));
					imageFile = new File(resultSet.getString("imagelocation"));

					if (type.equals("Apartment"))
						p = new model.Apartment(id, type, stnum, stname, suburb, bednum, status, Description,
								imageFile);
					else
						p = new model.PremiumSuite(id, type, stnum, stname, suburb, status, Description, imageFile,
								lastMaintenance);

					p = selectTable2(p);
					propertylist.add(p);

				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return propertylist;

	}

	public static RentalProperty selectTable2(RentalProperty p) {
		String record_id;
		String customerid;
		RentalRecord[] rentRecords = new RentalRecord[11];
		RentalRecord record;
		DateTime rentdate;
		DateTime estreturndate;
		DateTime actualreturndate;
		Double rentfee;
		Double latefee;

		for (int i = 0; i < 11; i++) {
			rentRecords[i] = null;
		}

		int j=1;
		
		try (Connection con = getConnection(DB_NAME); Statement stmt = con.createStatement();) {
			String query = "SELECT * FROM " + table2 + " WHERE PROPERTY_ID = '" + p.getPropertyId() + "'";

			try (ResultSet resultSet = stmt.executeQuery(query)) {
				while (resultSet.next()) {
					record_id = resultSet.getString("RECORD_ID");
					customerid = resultSet.getString("CUSTOMER_ID");
					rentdate = new DateTime(resultSet.getString("RENTDATE"));
					estreturndate = new DateTime(resultSet.getString("ESTMATEDATE"));
					if (resultSet.getString("ACTUALRETURNDATE").equals(" "))
						actualreturndate = null;
					else
						actualreturndate = new DateTime(resultSet.getString("ACTUALRETURNDATE"));
					rentfee = resultSet.getDouble("RENTALFEE");
					latefee = resultSet.getDouble("LATEFEE");

					if (rentfee == -1) {
						record = new RentalRecord(p.getPropertyId(), customerid, rentdate,
								estreturndate.diffDays(estreturndate, rentdate));
						rentRecords[0] = record;
					} else {
						record = new RentalRecord(record_id, rentdate, estreturndate, actualreturndate, rentfee,
								latefee);
						rentRecords[j] = record;
						j++;
					}

					p.setRentRecords(rentRecords);

				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return p;

	}

	public static void deleteTable() {

		// use try-with-resources Statement
		try (Connection con = getConnection(DB_NAME); Statement stmt = con.createStatement();) {
			stmt.executeUpdate("DROP TABLE " + table1);
			int result = stmt.executeUpdate("DROP TABLE " + table2);

			System.out.println(result);

			if (result == 0) {
				System.out.println("Table " + table1 + " has been deleted successfully");
				System.out.println("Table " + table2 + " has been deleted successfully");

			} else {
				System.out.println("Table " + table1 + " was not deleted");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static Connection getConnection(String dbName) throws SQLException, ClassNotFoundException {
		Class.forName("org.hsqldb.jdbc.JDBCDriver");

		Connection con = DriverManager.getConnection("jdbc:hsqldb:file:database/" + dbName, "SA", "");
		return con;
	}
}
