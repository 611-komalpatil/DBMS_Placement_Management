package miniproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.Scanner;

// Create class Records
class Records {
	// Declare Variables
	int Stud_Id;
	int Comp_Id;
	String Applied;
	String Shortlist;
	String Placement_Status;
	Connection conn;
	Statement stmt;
	static Scanner sc = new Scanner(System.in);

	// Initialize variables required to set connection
	static final String DB_URL = "jdbc:mysql://localhost:3306/miniproject1?useSSL=false";
	static final String USER = "root";
	static final String pass = "Dhanu@23";

	// Setting Connection
	public Records() {
		try {
			// Establish connection and create statement
			conn = DriverManager.getConnection(DB_URL, USER, pass);
			stmt = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// insert method to store input in records table
	public void insert_record() {
		try {
			boolean b = true;
			System.out.println("Enter Following Details :");
			System.out.println("Student Id: ");
			Stud_Id = sc.nextInt();

			System.out.println("Company Id: ");
			Comp_Id = sc.nextInt();
			System.out.println("Applied or Not Applied: ");
			sc.nextLine(); // Consume the newline character
			Applied = sc.nextLine();

			if (Applied.equalsIgnoreCase("Not Applied")) {
				Shortlist = "NULL";
				Placement_Status = "Not Placed";
				System.out.println("Can't proceed further");
				b = false;

			}
			if (b == true) {
				System.out.println("Shortlisted [Round 1,Round 2,Interview] : ");
				Shortlist = sc.next();
				System.out.println("Placed or Not Placed: ");
				sc.nextLine(); // Consume the newline character
				Placement_Status = sc.nextLine();
			}
			// Prepare and execute SQL insert statement
			String query = "INSERT INTO Records (Stud_Id,Comp_Id,Applied,Shortlist,Placement_Status) VALUES (?,?,?,?,?)";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, Stud_Id);
			pstmt.setInt(2, Comp_Id);
			pstmt.setString(3, Applied);
			pstmt.setString(4, Shortlist);
			pstmt.setString(5, Placement_Status);

			pstmt.executeUpdate();
			System.out.println("Record inserted successfully");

		} catch (SQLIntegrityConstraintViolationException e) {
			// Handle foreign key constraint violation
			System.out.println("Foreign key constraint violation: " + e.getMessage()); 
		} catch (Exception e) {
			// Handle other exceptions
			e.printStackTrace();
			System.out.println(e);
		}
	}

	// Helper method to check if a student exists
	private boolean isStudentExists(int studentId) throws SQLException {
		String query = "SELECT COUNT(*) FROM student_info WHERE Stud_id = ?";
		PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.setInt(1, studentId);
		ResultSet rs = pstmt.executeQuery();
		rs.next();
		int count = rs.getInt(1);
		return count > 0;
	}

	// method to display the content of Records table
	public void display_record() {
		try {

			String query = "SELECT * FROM Records";
			ResultSet rs = stmt.executeQuery(query);
			System.out.printf("%-12s%-20s%-30s%-15s%-15s%n", "Stud_Id", "Comp_Id", "Applied", "Shortlist",
					"Placement Status");

			while (rs.next()) {
				Stud_Id = rs.getInt("Stud_Id");
				Comp_Id = rs.getInt("Comp_Id");
				Applied = rs.getString("Applied");
				Shortlist = rs.getString("Shortlist");
				Placement_Status = rs.getString("Placement_Status");

				System.out.printf("%-12s%-20s%-30s%-15s%-15s%n", Stud_Id, Comp_Id, Applied, Shortlist,
						Placement_Status);
			}
			rs.close();
		} catch (SQLException e) {// handle SQL Exception
			e.printStackTrace();
			System.out.println(e);
		}
	}

	// Update method to update record
	public void update_record() {

		boolean b = false;
		try {

			String query = "SELECT * FROM Records";
			ResultSet rs = stmt.executeQuery(query);

			System.out.println("Enter your Student id : ");
			int pass = sc.nextInt();

			while (rs.next()) {

				if (pass == rs.getInt("Stud_Id")) {
					int choice = 0;

					do {
						System.out.println("Which details you want to change??");
						System.out.println("0.Exit\n1.Applied\n2.Shortlist\n3.Placement Status\n");
						choice = sc.nextInt();

						switch (choice) {

						case 0:
							b = true;
							break;
						case 1:

							System.out.println("Enter company id: ");
							int Comp_Id = sc.nextInt();
							System.out.println("Enter updated applied status: ");
							sc.nextLine();
							String new_Applied = sc.nextLine();
							String query3 = "Update Records set Applied=? where Stud_Id=? && Comp_Id=?";
							// Create a PreparedStatement
							PreparedStatement pstmt = conn.prepareStatement(query3);
							// Set the parameters
							pstmt.setString(1, new_Applied);
							pstmt.setInt(2, pass);
							pstmt.setInt(3, Comp_Id);
							// Execute the update query
							pstmt.executeUpdate();
							break;

						case 2:
							System.out.println("Enter Applied Status: ");
							sc.nextLine();
							Applied = sc.nextLine();
							if (Applied.equals("Not Applied")) {
								System.out.println("Can't move further!!");
								break;
							}
							System.out.println("Enter company id: ");
							Comp_Id = sc.nextInt();
							System.out.println("Enter updated shortlist status: ");
							String new_Shortlist = sc.next();
							String query4 = "Update Records set Shortlist=? where Stud_Id=? && Comp_Id=? ";

							pstmt = conn.prepareStatement(query4);
							// Set the parameters
							pstmt.setString(1, new_Shortlist);
							pstmt.setInt(2, pass);
							pstmt.setInt(3, Comp_Id);
							// Execute the update query
							pstmt.executeUpdate();
							break;

						case 3:
							System.out.println("Enter Applied Status: ");
							sc.nextLine();
							Applied = sc.nextLine();
							if (Applied.equals("Not Applied")) {
								System.out.println("Can't move further!!");
								break;
							}
							System.out.println("Enter company id: ");
							Comp_Id = sc.nextInt();
							System.out.println("Enter updated Placement_Status: ");
							sc.nextLine();
							String new_Placement = sc.nextLine();
							String query5 = "Update Records set Placement_Status=? where Stud_Id=? && Comp_Id=? ";

							pstmt = conn.prepareStatement(query5);
							// Set the parameters
							pstmt.setString(1, new_Placement);
							pstmt.setInt(2, pass);
							pstmt.setInt(3, Comp_Id);
							// Execute the update query
							pstmt.executeUpdate();
							break;

						}

					} while (choice != 0);
					if (b == true) {
						break;
					}
				}
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
		if (!b) {
			System.out.println("Incorrect password");
		}
	}

	public void queries_p() { // access to placement
		try {
			int ch = 0;
			do {
				System.out.println("Enter your choice: ");
				System.out.println(
						"0.Exit\n1.Student who are shortlisted but not placed\n2.No. of student Placed\n3.No. of Student not placed\n"
								+ "4.No.of Students Applied\n5.No. of Students Not applied");
				ch = sc.nextInt();
				switch (ch) {
				case 0:
					break;
				case 1:
					String query1 = "SELECT Stud_id, Comp_Id FROM Records WHERE Shortlist<>'NULL' AND Placement_Status = 'Not Placed'";
					ResultSet rs = stmt.executeQuery(query1);

					System.out.printf("%-12s%-20s%n", "Stud_id", "Comp_Id");
					while (rs.next()) {
						int studId = rs.getInt("Stud_id");
						int compId = rs.getInt("Comp_Id");
						System.out.printf("%-12s%-20s%n", studId, compId);

					}
					rs.close();
					break;
				case 2:

					String query2 = "SELECT Stud_Id, Comp_Id, Placement_Status FROM Records WHERE Placement_Status = 'Placed'";
					ResultSet rs2 = stmt.executeQuery(query2);
					System.out.printf("%-12s%-20s%-15s%n", "Stud_Id", "Comp_Id", "Placement_Status");
					while (rs2.next()) {
						int studId = rs2.getInt("Stud_Id");
						int compId = rs2.getInt("Comp_Id");
						String placementStatus = rs2.getString("Placement_Status");
						System.out.printf("%-12s%-20s%-15s%n", studId, compId, placementStatus);
					}

					rs2.close();

					String query3 = "SELECT COUNT(*) AS Count FROM Records WHERE Placement_Status = 'Placed'";
					rs2 = stmt.executeQuery(query3);
					int count = 0;
					if (rs2.next()) {
						count = rs2.getInt("Count");
					}
					System.out.println("Count: " + count);

					rs2.close();
					break;
				case 3:
					String query4 = "SELECT Stud_Id, Comp_Id, Placement_Status FROM Records WHERE Placement_Status = 'Not Placed'";
					rs2 = stmt.executeQuery(query4);
					System.out.printf("%-12s%-20s%-15s%n", "Stud_Id", "Comp_Id", "Placement_Status");
					while (rs2.next()) {
						int studId = rs2.getInt("Stud_Id");
						int compId = rs2.getInt("Comp_Id");
						String placementStatus = rs2.getString("Placement_Status");
						System.out.printf("%-12s%-20s%-15s%n", studId, compId, placementStatus);
					}

					rs2.close();

					String query5 = "SELECT COUNT(*) AS Count FROM Records WHERE Placement_Status = 'Not Placed'";
					rs2 = stmt.executeQuery(query5);
					count = 0;
					if (rs2.next()) {
						count = rs2.getInt("Count");
					}
					System.out.println("Count: " + count);

					rs2.close();
					break;

				case 4:
					String query6 = "SELECT Stud_Id, Comp_Id,Applied FROM Records WHERE Applied='Applied'";
					rs2 = stmt.executeQuery(query6);
					System.out.printf("%-12s%-20s%-15s%n", "Stud_Id", "Comp_Id", "Applied");
					while (rs2.next()) {
						int studId = rs2.getInt("Stud_Id");
						int compId = rs2.getInt("Comp_Id");
						String Applied = rs2.getString("Applied");
						System.out.printf("%-12s%-20s%-15s%n", studId, compId, Applied);
					}

					rs2.close();

					String query7 = "SELECT COUNT(*) AS Count FROM Records WHERE Applied = 'Applied'";
					rs2 = stmt.executeQuery(query7);
					count = 0;
					if (rs2.next()) {
						count = rs2.getInt("Count");
					}
					System.out.println("Count: " + count);

					rs2.close();
					break;

				case 5:
					String query8 = "SELECT Stud_Id, Comp_Id,Applied FROM Records WHERE Applied='Not Applied'";
					rs2 = stmt.executeQuery(query8);
					System.out.printf("%-12s%-20s%-15s%n", "Stud_Id", "Comp_Id", "Not Applied");
					while (rs2.next()) {
						int studId = rs2.getInt("Stud_Id");
						int compId = rs2.getInt("Comp_Id");
						String Applied = rs2.getString("Applied");
						System.out.printf("%-12s%-20s%-15s%n", studId, compId, Applied);
					}

					rs2.close();

					String query9 = "SELECT COUNT(*) AS Count FROM Records WHERE Applied = 'Not Applied'";
					rs2 = stmt.executeQuery(query9);
					count = 0;
					if (rs2.next()) {
						count = rs2.getInt("Count");
					}
					System.out.println("Count: " + count);

					rs2.close();
					break;
				}
			} while (ch != 0);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
	}

	public void queries_s() {
		try {
			int ch = 0;
			int count = 0;
			do {
				System.out.println("0.Exit\n1.Getting Platform for particular Company\n2.List of all Placed Students");
				ch = sc.nextInt();
				switch (ch) {
				case 0:
					break;
				case 1:
					System.out.println("Enter name of Platform: ");
					String plat = sc.next();

					String query1 = "SELECT distinct Comp_id, Comp_name, Platform FROM Company NATURAL JOIN Test_Information WHERE Platform=?";

					PreparedStatement pstmt = conn.prepareStatement(query1);
					pstmt.setString(1, plat);
					ResultSet rs = pstmt.executeQuery();

					while (rs.next()) {
						if (plat.equalsIgnoreCase(rs.getString("Platform"))) {
							// b=true;
							count++;
							System.out.printf("%-12s%-20s%-15s%n", "Comp_Id", "Comp_name", "Platform");

							int compId = rs.getInt("Comp_Id");
							String comp_name = rs.getString("Comp_name");
							String platform = rs.getString("Platform");
							System.out.printf("%-12s%-20s%-15s%n", compId, comp_name, platform);
						}
					}
					if (count == 0) {
						System.out.println("No such platform exist!!");
						break;
					}

					rs.close();

					break;

				case 2:
					String query2 = "Select Records.Stud_id,Student_info.Stud_name,Student_info.Email,Company.Comp_name,Records.Placement_Status "
							+ "from Records NATURAL JOIN Student_info NATURAL JOIN Company where Placement_Status='Placed'";
					pstmt = conn.prepareStatement(query2);

					rs = pstmt.executeQuery();
					System.out.printf("%-12s%-20s%-30s%-15s%-15s%n", "Stud_id", "Stud_name", "Email", "Company name",
							"Placement Status");
					while (rs.next()) {
						Stud_Id = rs.getInt("Stud_id");
						String Stud_name = rs.getString("Stud_name");
						String Email = rs.getString("Email");

						String Comp_name = rs.getString("Comp_name");
				
				Placement_Status = rs.getString("Placement_Status");

						System.out.printf("%-12s%-20s%-30s%-15s%-15s%n", Stud_Id, Stud_name, Email, Comp_name,
								Placement_Status);

					}
				}
			} while (ch != 0);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
	}

}
