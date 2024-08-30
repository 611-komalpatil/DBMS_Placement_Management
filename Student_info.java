package miniproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

class Student_info {
	static Scanner sc = new Scanner(System.in);
	static final String DB_URL = "jdbc:mysql://localhost:3306/miniproject1?useSSL=false";
	static final String USER = "root";
	static final String pass = "Dhanu@23";
	Connection conn = null;
	Statement stmt = null;

	public Student_info() {
		try {
			conn = DriverManager.getConnection(DB_URL, USER, pass);
			stmt = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Close statement
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			// Close connection
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	int Stud_id;
	int Comp_id;
	String Stud_name;
	String Email;
	String PhoneNo;
	String DOB;
	String Branch;
	float CGPA;
	String Resume_Uploaded;
	String Applied;
	String Shortlist;
	String Placement_Status;

	public void student_input() {
		try {

			// String query = "SELECT * FROM Student_info";

			conn = DriverManager.getConnection(DB_URL, USER, pass);

			stmt = conn.createStatement();

			String query = "SELECT * FROM Student_info";

			PreparedStatement pstmt = conn.prepareStatement(query);

			ResultSet rs = pstmt.executeQuery();

			System.out.println("Provide your data: ");
			System.out.println("Enter Student ID: ");
			Stud_id = sc.nextInt();
			boolean b=true;
		
		
			while (rs.next()) {

				if (Stud_id == rs.getInt("Stud_id")) {
					b=false;
					System.out.println("Student already exists!!");
					break;
				}
				
			}
	
			if(b==true) {
				System.out.println("Student name : ");
				sc.nextLine();
				Stud_name = sc.nextLine();
				System.out.println("Student Email: ");
				Email = sc.next();
				System.out.println("Phone no: ");
				PhoneNo = sc.next();
				System.out.println("Student DOB (YYYY-MM-DD) : ");
				DOB = sc.next();
				System.out.println("Student Branch : ");
				sc.nextLine();
				Branch = sc.nextLine();
				System.out.println("CGPA : ");
				CGPA = sc.nextFloat();
				System.out.println("Resume (yes or no) : "); // uploaded or not
				Resume_Uploaded = sc.next();
			
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insert() {
	    try {// Use try-with-resources to ensure proper resource closure

	        // Insert into Student_info table
	        String sqlStudent = "INSERT INTO Student_info (Stud_id, Stud_name, Email, PhoneNo, DOB) "
	                             + "VALUES (?, ?, ?, ?, ?)";
	        PreparedStatement pstmtStudent = conn.prepareStatement(sqlStudent);
	        pstmtStudent.setInt(1, Stud_id);
	        pstmtStudent.setString(2, Stud_name);
	        pstmtStudent.setString(3, Email);
	        pstmtStudent.setString(4, PhoneNo);
	        pstmtStudent.setDate(5, java.sql.Date.valueOf(DOB)); // Use java.sql.Date for DOB

	        int studentRowsAffected = pstmtStudent.executeUpdate();
	        System.out.println(studentRowsAffected + " row(s) inserted into Student_info table.");

	        if (studentRowsAffected > 0) {
	            // Insert into Academic table only if student insertion succeeded
	           
	            String sqlAcademic = "INSERT INTO Academic (Stud_id, Branch, CGPA, Resume_Uploaded) "
	                                 + "VALUES (?, ?, ?, ?)";
	            PreparedStatement pstmtAcademic = conn.prepareStatement(sqlAcademic);
	            pstmtAcademic.setInt(1, Stud_id);
	            pstmtAcademic.setString(2, Branch);
	            pstmtAcademic.setFloat(3, CGPA);
	            pstmtAcademic.setString(4, Resume_Uploaded.equals("yes") ? "yes" : "no");

	            int academicRowsAffected = pstmtAcademic.executeUpdate();
	            System.out.println(academicRowsAffected + " row(s) inserted into Academic table.");

	        } else {
	            System.out.println("Failed to insert student information.");
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
}
	

	public void display() {
		try {

			String query = "SELECT * FROM Student_info natural join Academic";
			
			conn = DriverManager.getConnection(DB_URL, USER, pass);
			stmt = conn.createStatement();

			// Database operations here

			ResultSet rs = stmt.executeQuery(query);
		
			System.out.printf("%-12s%-20s%-30s%-15s%-15s%-20s%-15s%-15s%n", "Stud_id", "Stud_name", "Email",
					"PhoneNo", "DOB", "Branch", "CGPA", "Resume_Uploaded");

			while (rs.next()) {
				Stud_id = rs.getInt("Stud_id");
				Stud_name = rs.getString("Stud_name");
				Email = rs.getString("Email");
				PhoneNo = rs.getString("PhoneNo");
				DOB = rs.getString("DOB");
				Branch = rs.getString("Branch");
				CGPA = rs.getFloat("CGPA");
				Resume_Uploaded = rs.getString("Resume_Uploaded");

				System.out.printf("%-12s%-20s%-30s%-15s%-15s%-20s%-15.2f%-15s%n", Stud_id, Stud_name, Email,
						PhoneNo, DOB, Branch, CGPA, Resume_Uploaded);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e);
		}
	}

	public void update() {
		boolean b = false;
		PreparedStatement pstmt = null;
		try {
			// Establish connection
			conn = DriverManager.getConnection(DB_URL, USER, pass);
			stmt = conn.createStatement();

			// Retrieve data
			String query = "SELECT * FROM Student_info natural join Academic";
			ResultSet rs = stmt.executeQuery(query);

			System.out.println("Enter your Student id(password): ");
			String pass = sc.next();

			// if sql integreity constraints violation exception comes then we cannot update
			// or delete foreign key because it is related to other tables
			while (rs.next()) {
				if (pass.equals(rs.getString("Stud_id"))) {
					b = true;
					int choice = 0;

					do {
						System.out.println("Which details you want to change??");
						System.out.println(
								"0.Exit\n1.Stud name\n2.Email\n3.PhoneNo\n4.DOB\n5.Branch\n6.CGPA\n7.Resume_Uploaded");
						choice = sc.nextInt();

						switch (choice) {
						case 0:
							break;
						case 1:
							sc.nextLine();
							System.out.println("Enter new name: ");
							String new_name = sc.nextLine();
							String query2 = "Update Student_info set Stud_name=? where Stud_id=?";
							// Create a PreparedStatement
							pstmt = conn.prepareStatement(query2);
							// Set the parameters
							pstmt.setString(1, new_name);
							pstmt.setString(2, pass);
							// Execute the update query
							pstmt.executeUpdate();
							break;

						case 2:
							System.out.println("Enter new Email: ");
							String new_Email = sc.next();
							String query3 = "Update Student_info set Email=? where Stud_id=?";
							// Create a PreparedStatement
							pstmt = conn.prepareStatement(query3);
							// Set the parameters
							pstmt.setString(1, new_Email);
							pstmt.setString(2, pass);
							// Execute the update query
							pstmt.executeUpdate();
							break;

						case 3:
							System.out.println("Enter new Phoneno: ");
							String new_PhoneNo = sc.next();
							String query4 = "Update Student_info set PhoneNo=? where Stud_id=?";

							pstmt = conn.prepareStatement(query4);
							// Set the parameters
							pstmt.setString(1, new_PhoneNo);
							pstmt.setString(2, pass);
							// Execute the update query
							pstmt.executeUpdate();
							break;

						case 4:
							System.out.println("Enter new DOB: ");
							String new_DOB = sc.next();
							String query5 = "Update Student_info set DOB=? where Stud_id=?";

							pstmt = conn.prepareStatement(query5);
							// Set the parameters
							pstmt.setString(1, new_DOB);
							pstmt.setString(2, pass);
							// Execute the update query
							pstmt.executeUpdate();
							break;

						case 5:

							System.out.println("Enter new branch: ");
							String new_branch = sc.next();

							String query6 = "Update Academic set Branch=? where Stud_id=?";

							pstmt = conn.prepareStatement(query6);
							// Set the parameters
							pstmt.setString(1, new_branch);
							pstmt.setString(2, pass);
							// Execute the update query
							pstmt.executeUpdate();
							break;

						case 6:
							System.out.println("Enter new CGPA: ");
							float new_CGPA = sc.nextFloat();
							String query7 = "Update Academic set CGPA=? where Stud_id=? ";

							pstmt = conn.prepareStatement(query7);
							// Set the parameters
							pstmt.setFloat(1, new_CGPA);
							pstmt.setString(2, pass);
							// Execute the update query

							pstmt.executeUpdate();
							break;

						case 7:
							System.out.println("Enter new Resume (Yes or No) : ");
							sc.nextLine();
							String new_Resume = sc.nextLine();
							String query8 = "Update Academic set Resume=? where Stud_id=?";
							pstmt = conn.prepareStatement(query8);
							// Set the parameters
							pstmt.setString(1, new_Resume);
							pstmt.setString(2, pass);
							// Execute the update query
							pstmt.executeUpdate();
							break;

						}
						// Other cases omitted for brevity

					} while (choice != 0);
				}
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		} finally {
			try {
				if (stmt != null) {
					stmt.close(); // Close Statement
				}
				if (conn != null) {
					conn.close(); // Close Connection
				}
				if (pstmt != null) {
					pstmt.close(); // Close PreparedStatement
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (!b) {
			System.out.println("Incorrect password");
		}
	}

	
	public void search() {
		PreparedStatement pstmt = null;
		try {
			conn = DriverManager.getConnection(DB_URL, USER, pass);
			stmt = conn.createStatement();

			String query = "SELECT * FROM Student_info";
			ResultSet rs = stmt.executeQuery(query);
			int choice = 0;
			do {
				System.out.println(
						"Select from the following :=\n0.Exit\n1.Search By Student ID\n2.Search By Name\n3.Search By Branch\n4.Search By CGPA");
				choice = sc.nextInt();
				
				switch (choice) {
				case 0:
					break;
				case 1:
					System.out.println("Enter Student ID to be searched by :=");
					String search_id = sc.next();
					boolean b = false;
					String query1 = "Select * from Student_info natural join Academic where Stud_id=?";
					pstmt = conn.prepareStatement(query1);
					pstmt.setString(1, search_id);
					rs = pstmt.executeQuery();
					
					while (rs.next()) {
						if (search_id.equals(rs.getString("Stud_id"))) {
							System.out.printf("%-12s%-20s%-30s%-15s%-15s%-20s%-15s%-15s%n", "Stud_id", "Stud_name",
									"Email", "PhoneNo", "DOB", "Branch", "CGPA", "Resume_Uploaded");
							Stud_id = rs.getInt("Stud_id");
							Stud_name = rs.getString("Stud_name");
							Email = rs.getString("Email");
							PhoneNo = rs.getString("PhoneNo");
							DOB = rs.getString("DOB");
							Branch = rs.getString("Branch");
							CGPA = rs.getFloat("CGPA");
							Resume_Uploaded= rs.getString("Resume_Uploaded");
							

							System.out.printf("%-12s%-20s%-30s%-15s%-15s%-20s%-15.2f%-15s%n", Stud_id, Stud_name, Email,
									PhoneNo, DOB, Branch, CGPA, Resume_Uploaded);
							b = true;
							break;
						}
					}
					if (b == false) {
						System.out.println("No such Student exists!!");
					}
					break;

				case 2:
					System.out.println("Enter Student Name(FirstName LastName) to be searched by: ");
					sc.nextLine();
					String search_name = sc.nextLine();
					b = false;
					String query2 = "Select * from Student_info natural join Academic where Stud_name=?";
					pstmt = conn.prepareStatement(query2);
					pstmt.setString(1, search_name);
					rs = pstmt.executeQuery();

					while (rs.next()) {
						if (search_name.equalsIgnoreCase(rs.getString("Stud_name"))) {
							System.out.printf("%-12s%-20s%-30s%-15s%-15s%-20s%-15s%-15s%n", "Stud_id", "Stud_name",
									"Email", "PhoneNo", "DOB", "Branch", "CGPA", "Resume_Uploaded");
							Stud_id = rs.getInt("Stud_id");
							Stud_name = rs.getString("Stud_name");
							Email = rs.getString("Email");
							PhoneNo = rs.getString("PhoneNo");
							DOB = rs.getString("DOB");
							Branch = rs.getString("Branch");
							CGPA = rs.getFloat("CGPA");
							Resume_Uploaded = rs.getString("Resume_Uploaded");
							

							System.out.printf("%-12s%-20s%-30s%-15s%-15s%-20s%-15.2f%-15s%n", Stud_id, Stud_name, Email,
									PhoneNo, DOB, Branch, CGPA, Resume_Uploaded);
							b = true;
							break;
						}
					}
					rs.close();
					if (b == false) {
						System.out.println("No such student exists!!");
					}
					break;

				case 3:
					b = false;
					pstmt = null;
					rs = null;
					try {
						// Establish connection
						conn = DriverManager.getConnection(DB_URL, USER, pass);
						stmt = conn.createStatement();
						System.out.println("Enter Student branch to be searched by: ");
						sc.nextLine();
						String search_branch = sc.nextLine().trim().toUpperCase(); // Normalize input
//					        b = false;
						query = "SELECT * FROM Student_info natural join Academic WHERE UPPER(TRIM(Branch)) = ?";
						pstmt = conn.prepareStatement(query);
						pstmt.setString(1, search_branch);
						rs = pstmt.executeQuery();

						boolean found = false;
						while (rs.next()) {
							found = true;
							String Stud_id = rs.getString("Stud_id");
							String Stud_name = rs.getString("Stud_name");
							String Email = rs.getString("Email");
							String PhoneNo = rs.getString("PhoneNo");
							String DOB = rs.getString("DOB");
							String Branch = rs.getString("Branch");
							float CGPA = rs.getFloat("CGPA");
							String Resume_Uploaded = rs.getString("Resume_Uploaded");

							// Handle null values
							Stud_id = Stud_id != null ? Stud_id : "";
							Stud_name = Stud_name != null ? Stud_name : "";
							Email = Email != null ? Email : "";
							PhoneNo = PhoneNo != null ? PhoneNo : "";
							DOB = DOB != null ? DOB : "";
							Branch = Branch != null ? Branch : "";
							Resume_Uploaded = Resume_Uploaded != null ? Resume_Uploaded : "";

							System.out.printf("%-12s%-20s%-30s%-15s%-15s%-20s%-15.2f%-15s%n", Stud_id, Stud_name, Email,
									PhoneNo, DOB, Branch, CGPA, Resume_Uploaded);
						}
						if (!found) {
							System.out.println("No records found for the given branch.");
						}
					} catch (SQLException e) {
						System.out.println("SQL Exception: " + e.getMessage());
						e.printStackTrace();
					}
					
					break;

				case 4:
					System.out.println("Enter student CGPA to be searched by: ");
					float search_cgpa = sc.nextFloat();
					String query4 = "Select * from Student_info natural join Academic where CGPA=?";
					pstmt = conn.prepareStatement(query4);
					pstmt.setFloat(1, search_cgpa);
					rs = pstmt.executeQuery();
					b = false;

					while (rs.next()) {
						if (search_cgpa == (rs.getFloat("CGPA"))) {
							System.out.printf("%-12s%-20s%-30s%-15s%-15s%-20s%-15s%-15s%n", "Stud_id", "Stud_name",
									"Email", "PhoneNo", "DOB", "Branch", "CGPA", "Resume");
							Stud_id = rs.getInt("Stud_id");
							Stud_name = rs.getString("Stud_name");
							Email = rs.getString("Email");
							PhoneNo = rs.getString("PhoneNo");
							DOB = rs.getString("DOB");
							Branch = rs.getString("Branch");
							CGPA = rs.getFloat("CGPA");
							Resume_Uploaded = rs.getString("Resume_Uploaded");
							

							System.out.printf("%-12s%-20s%-30s%-15s%-15s%-20s%-15.2f%-15s%n", Stud_id, Stud_name, Email,
									PhoneNo, DOB, Branch, CGPA, Resume_Uploaded);
							b = true;
							break;
						}
					}
					rs.close();
					if (b == false) {
						System.out.println("No record found for given CGPA!!");
					}
					break;
				}
			} while (choice != 0);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
	}

	public void Extra_query() { // access to placement
		try {
			conn = DriverManager.getConnection(DB_URL, USER, pass);
			stmt = conn.createStatement();
			int choice = 0;
			do {
				System.out.println("Enter your choice: ");
				System.out.println("0.Exit\n1.Sorting Students based on CGPA\n2.Getting count of students\n"
						+ "3.Getting count of students of specific branch\n4.Top n students\n5.Top n students from specific branch");
				choice = sc.nextInt();
				switch (choice) {
				case 0:
					break;

				case 1:
					String query1 = "Select Stud_id,Branch,CGPA from Academic order by CGPA";
					ResultSet rs = stmt.executeQuery(query1);

					System.out.printf("%-12s%-15s%-20s%n", "Stud_id", "Branch", "CGPA");

					while (rs.next()) {
						Stud_id = rs.getInt("Stud_id");
						Branch = rs.getString("Branch");
						CGPA = rs.getFloat("CGPA");
						System.out.printf("%-12s%-15s%-20s%n", Stud_id, Branch, CGPA);
					}
					rs.close();
					break;

				case 2:

					String query2 = "Select count(Stud_id) as Count from Student_info";
					rs = stmt.executeQuery(query2);

					while (rs.next()) {
						int count = rs.getInt("Count");
						System.out.println("Total number of students: " + count + "\n");
					}
					rs.close();
					break;

				case 3:
					String query3 = "Select Branch,count(Stud_id) as Count from Academic group by Branch";
					rs = stmt.executeQuery(query3);
					while (rs.next()) {
						String branch = rs.getString("Branch");
						int count = rs.getInt("Count");
						System.out.println("Count " + "(" + branch + ")" + ": " + count);
					}
					rs.close();
					break;

				case 4:
					System.out.println("Enter number, how many top students you want to display: ");
					int n = sc.nextInt();

					String query4 = "Select Stud_id,Branch,CGPA from Academic order by CGPA desc limit ?";
					PreparedStatement pstmt = conn.prepareStatement(query4);
					pstmt.setInt(1, n);
					rs = pstmt.executeQuery();

					System.out.printf("%-12s%-20s%-10s%n", "Stud_id", "Branch", "CGPA");

					while (rs.next()) {
						String Stud_id = rs.getString("Stud_id");
						String Branch = rs.getString("Branch");
						float CGPA = rs.getFloat("CGPA");
						System.out.printf("%-12s%-20s%-10.2f%n", Stud_id, Branch, CGPA);
					}
					rs.close();
					break;

				case 5:
					try {
						conn = DriverManager.getConnection(DB_URL, USER, pass);
						stmt = conn.createStatement();

						boolean b=false;
						System.out.println("Enter branch: ");
						sc.nextLine(); // Consume newline
						String branch1 = sc.nextLine();

						System.out.println(
								"Enter the number of top students from the specific branch you want to display: ");
						int n1 = sc.nextInt();

						String query = "SELECT Stud_id, Branch, CGPA FROM Academic WHERE Branch=? ORDER BY CGPA DESC LIMIT ?";
						PreparedStatement pstmt1 = conn.prepareStatement(query);
						pstmt1.setString(1, branch1);
						pstmt1.setInt(2, n1);

						ResultSet rs1 = pstmt1.executeQuery();

						
						while (rs1.next()) {
							if(branch1.equalsIgnoreCase(rs1.getString("Branch"))) {
								
							System.out.printf("%-12s%-20s%-10s%n", "Stud_id", "Branch", "CGPA");

							String studId = rs1.getString("Stud_id");
							String branchName = rs1.getString("Branch");
							float cgpa = rs1.getFloat("CGPA");
							System.out.printf("%-12s%-20s%-10.2f%n", studId,  branchName, cgpa);
							b=true;
							break;
						}
						}if(b==false) {
							System.out.println("No such branch exist!!");
						}
						rs1.close();
						pstmt1.close();
					} catch (SQLException e) {
						System.out.println("SQL Exception: " + e.getMessage());
						e.printStackTrace();
					} finally {
						try {
							if (stmt != null) {
								stmt.close();
							}
							if (conn != null) {
								conn.close();
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
					break;
				}

			} while (choice != 0);
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
	}

}
