package miniproject;
//
//import java.sql.*;
//import java.util.*;
//
//public class Company {
//	Scanner sc = new Scanner(System.in);
//	Connection conn;
//	Statement stmt;
//
//	// Constructor to initialize JDBC connection
//	public Company() {
//		try {
//
//			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/miniproject1?useSSL=false", "root", "Dhanu@23");
//			stmt = conn.createStatement();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
//
//	public void insertCompany() {
//		try {
//			System.out.println("Enter Job id: ");
//			int Job_id=sc.nextInt();
//			System.out.print("Enter Company ID: ");
//			int compId = sc.nextInt();
//			System.out.println("Enter Test id: ");
//			int Test_id=sc.nextInt();
//			sc.nextLine(); // Consume newline
//			System.out.print("Enter Company Name: ");
//			String compName = sc.nextLine();
//			System.out.print("Enter Post: ");
//			String post = sc.nextLine();
//			System.out.print("Enter Location: ");
//			String location = sc.nextLine();
//			System.out.print("Enter CGPA Criteria: ");
//			float cgpaCriteria = sc.nextFloat();
//			System.out.print("Enter Salary: ");
//			int salary = sc.nextInt();
//			System.out.print("Enter Vacancy: ");
//			int vacancy = sc.nextInt();
//			sc.nextLine(); // Consume newline
//			System.out.print("Enter Duration Intern: ");
//			String duration= sc.nextLine();
//			System.out.print("Enter Coming Date (yyyy-MM-dd): ");
//			String comingDate = sc.next();
//			sc.nextLine(); // Consume newline
//			System.out.print("Enter Event: ");
//			String event = sc.nextLine();
//			System.out.print("Enter Platform: ");
//			String platform = sc.nextLine();
//			System.out.print("Enter Time: ");
//			String time = sc.nextLine();
//
//			String q1 = "INSERT INTO Company (Comp_id, Comp_name,Location) "
//					+ "VALUES (?, ?, ?)";
//			PreparedStatement pstmt = conn.prepareStatement(q1);
//			pstmt.setInt(1, compId);
//			pstmt.setString(2, compName);
//			pstmt.setString(3, location);	
//			
//			String q2="Insert into Job_Opportunity (Job_id,Comp_id,Post,CGPA_Criteria, Salary, Vacancy, Duration, Coming_Dt) VALUES (?,?,?,?,?,?,?,?)";
//			String q3="Insert into Test_Information (Test_id,Job_id,Event,Platform,Time) VALUES (?,?,?,?,?)";
//
//			pstmt = conn.prepareStatement(q2);
//			
//			pstmt.setInt(1, Job_id);
//			pstmt.setInt(2, compId);
//			pstmt.setString(3, post);			
//			pstmt.setFloat(4, cgpaCriteria);
//			pstmt.setInt(5, salary);
//			pstmt.setInt(6, vacancy);
//			pstmt.setString(7, duration);
//			pstmt.setString(8, comingDate);
//		
//			pstmt = conn.prepareStatement(q3);
//			pstmt.setInt(1, Test_id);
//			pstmt.setInt(2, Job_id);
//			pstmt.setString(3, event);			
//			pstmt.setString(4, platform);
//			pstmt.setString(5, time);
//
//			int rowsAffected = pstmt.executeUpdate();
//			System.out.println(rowsAffected + " row(s) inserted.");
//			pstmt.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
//
//	public void deleteCompany() {
//		try {
//			System.out.print("Enter Company ID to delete: ");
//			int compId = sc.nextInt();
//			sc.nextLine(); // Consume newline
//
//			String sql = "DELETE FROM Company natural join Job_Opportunity WHERE Comp_id = ?";
//			PreparedStatement pstmt = conn.prepareStatement(sql);
//			pstmt.setInt(1, compId);
//
//			int rowsAffected = pstmt.executeUpdate();
//			if (rowsAffected > 0) {
//				System.out.println(rowsAffected + " row(s) deleted.");
//			} else {
//				System.out.println("No company found with ID: " + compId);
//			}
//			pstmt.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
//
//	public void displayAllCompanies() {
//		try {
//			String sql = "SELECT * FROM Company natural join Job_Opportunity natural join Test_Information";
//			ResultSet rs = stmt.executeQuery(sql);
//
//			StringBuilder tableBuilder = new StringBuilder();
//			tableBuilder.append(
//					"----------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
//			tableBuilder.append(
//					"| Comp_id | Job_id |   Company Name                   | Post                  |  Location             | CGPA Criteria | Salary | Vacancy | Duration Intern | Coming Date |   Test_id |   Event    |   Platform   |  Time  |\n");
//			tableBuilder.append(
//					"----------------------------------------------------------------------------------------------------------------------------------\n");
//
//			while (rs.next()) {
//				int compId = rs.getInt("Comp_id");
//				String compName = rs.getString("Comp_name");
//				String location = rs.getString("Location");
//				int Job_id=rs.getInt("Job_id");	
//				String post = rs.getString("Post");
//				float cgpaCriteria = rs.getFloat("CGPA_Criteria");
//				int salary = rs.getInt("Salary");
//				int vacancy = rs.getInt("Vacancy");
//				String duration = rs.getString("Duration");
//				String comingDate = rs.getString("Coming_Dt");
//				int Test_id=rs.getInt("Test_id");
//				String event = rs.getString("Event");
//				String platform = rs.getString("Platform");
//				String time = rs.getString("Time");
//
//				tableBuilder.append(String.format(
//						"| %-8s| %-13s| %-30s| %-15s| %-14s| %-7s| %-8s| %-16s| %-12s| %-13s| %-13s| %-7s|\n", compId,Job_id,
//						compName, post, location, cgpaCriteria, salary, vacancy, duration, comingDate,Test_id, event,
//						platform, time));
//			}
//
//			tableBuilder.append(
//					"----------------------------------------------------------------------------------------------------------------------------------\n");
//			System.out.println(tableBuilder.toString());
//
//			rs.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
//
//	public void updateCompany() {
//		try {
//			System.out.print("Enter Company ID to update: ");
//			int compId = sc.nextInt();
//			sc.nextLine(); // Consume newline
//
//			System.out.println("What do you want to update?");
//			System.out.println("1. Company Name");
//			System.out.println("2. Post");
//			System.out.println("3. Location");
//			System.out.println("4. CGPA Criteria");
//			System.out.println("5. Salary");
//			System.out.println("6. Vacancy");
//			System.out.println("7. Duration Intern");
//			System.out.println("8. Coming Date");
//			System.out.println("9. Event");
//			System.out.println("10. Platform");
//			System.out.println("11. Time");
//			System.out.println("12. Cancel");
//
//			int choice = sc.nextInt();
//			sc.nextLine(); // Consume newline
//
//			String columnName = "";
//			String newValue = "";
//
//			switch (choice) {
//			case 1:
//				columnName = "Comp_name";
//				System.out.print("Enter new Company Name: ");
//				newValue = sc.nextLine();
//				break;
//			case 2:
//				columnName = "Post";
//				System.out.print("Enter new Post: ");
//				newValue = sc.nextLine();
//				break;
//			case 3:
//				columnName = "Location";
//				System.out.print("Enter new Location: ");
//				newValue = sc.nextLine();
//				break;
//			case 4:
//				columnName = "CGPA_Criteria";
//				System.out.print("Enter new CGPA Criteria: ");
//				newValue = sc.nextFloat() + "";
//				break;
//			case 5:
//				columnName = "Salary";
//				System.out.print("Enter new Salary: ");
//				newValue = sc.nextInt() + "";
//				break;
//			case 6:
//				columnName = "Vacancy";
//				System.out.print("Enter new Vacancy: ");
//				newValue = sc.nextInt() + "";
//				break;
//			case 7:
//				columnName = "Duration_Intern";
//				System.out.print("Enter new Duration Intern: ");
//				newValue = sc.nextLine();
//				break;
//			case 8:
//				columnName = "Coming_Dt";
//				System.out.print("Enter new Coming Date (yyyy-MM-dd): ");
//				newValue = sc.nextLine();
//				break;
//			case 9:
//				columnName = "Event";
//				System.out.print("Enter new Event: ");
//				newValue = sc.nextLine();
//				break;
//			case 10:
//				columnName = "Platform";
//				System.out.print("Enter new Platform: ");
//				newValue = sc.nextLine();
//				break;
//			case 11:
//				columnName = "Time";
//				System.out.print("Enter new Time: ");
//				newValue = sc.nextLine();
//				break;
//			case 12:
//				System.out.println("Update canceled.");
//				return;
//			default:
//				System.out.println("Invalid option");
//				return;
//			}
////
////			String sql = "UPDATE Company SET " + columnName + " = ? WHERE Comp_id = ?";
//			String sql1 = "UPDATE Job_Opportunity SET " + columnName + " = ? WHERE Job_id = ?";
//			String sql2 = "UPDATE Test_Information SET " + columnName + " = ? WHERE Comp_id = ?";
//			//PreparedStatement pstmt = conn.prepareStatement(sql);
//			PreparedStatement pstmt = conn.prepareStatement(sql1);
//			pstmt = conn.prepareStatement(sql2);
//			pstmt.setString(1, newValue);
//			pstmt.setInt(2, compId);
//
//			int rowsAffected = pstmt.executeUpdate();
//			if (rowsAffected > 0) {
//				System.out.println(rowsAffected + " row(s) updated.");
//			} else {
//				System.out.println("No company found with ID: " + compId);
//			}
//			pstmt.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
//
//	public void comp_salary() {
//		int choice = 0;
//		do {
//			System.out.print("Enter the salary to search a company: ");
//			int sal = sc.nextInt();
//
//			try {
//				System.out.println("1. Greater than " + sal + "\n2. Greater than or equal to " + sal + "\n3. Less than "
//						+ sal + "\n4. Less than or equal to " + sal + "\n5. Equal to " + sal
//						+ "\n6. Go back <<---\nCHOOSE: ");
//				choice = sc.nextInt();
//
//				String sql = "";
//				switch (choice) {
//				case 1:
//					sql = "SELECT * FROM Job_Opportunity WHERE Salary > ?";
//					break;
//				case 2:
//					sql = "SELECT * FROM Job_Opportunity WHERE Salary >= ?";
//					break;
//				case 3:
//					sql = "SELECT * FROM Job_Opportunity WHERE Salary < ?";
//					break;
//				case 4:
//					sql = "SELECT * FROM Job_Opportunity WHERE Salary <= ?";
//					break;
//				case 5:
//					sql = "SELECT * FROM Job_Opportunity WHERE Salary = ?";
//					break;
//				case 6:
//					return; // Go back
//				default:
//					System.out.println("Invalid option");
//					continue; // Continue loop for invalid input
//				}
//
//				PreparedStatement pstmt = conn.prepareStatement(sql);
//				pstmt.setInt(1, sal);
//				ResultSet rs = pstmt.executeQuery();
//
//				if (!rs.next()) {
//					System.out.println("Not found in our records");
//				} else {
//					rs.beforeFirst(); // Move cursor back to first position
//					while (rs.next()) {
//						int compId = rs.getInt("Comp_id");
//						
//						System.out.println("Company ID: " + compId);
//					}
//				}
//
//				rs.close();
//				pstmt.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		} while (choice != 6);
//	}
//
//	public void comp_CGPA() {
//		System.out.print("Enter the CGPA criteria to search companies: ");
//		float cgpaCriteria = sc.nextFloat();
//		try {
//			String sql = "SELECT * FROM Job_Opportunity WHERE CGPA_Criteria >= ?";
//			PreparedStatement pstmt = conn.prepareStatement(sql);
//			pstmt.setFloat(1, cgpaCriteria);
//			ResultSet rs = pstmt.executeQuery();
//
//			if (!rs.next()) {
//				System.out.println("Not found in our records");
//			} else {
//				rs.beforeFirst(); // Move cursor back to first position
//				while (rs.next()) {
//					int compId = rs.getInt("Comp_id");
//					String compName = rs.getString("Comp_name");
//					System.out.println("Company ID: " + compId + ", Company Name: " + compName);
//				}
//			}
//
//			rs.close();
//			pstmt.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
//
//	public void comp_coming_dt() {
//		int choice = 0;
//		String dateString;
//		do {
//			System.out.print("Enter the date in (yyyy-mm-dd): ");
//			dateString = sc.next();
//			sc.nextLine();
//			if (dateString.matches("\\d{4}-\\d{2}-\\d{2}")) {
//				System.out.println("---------------------");
//				try {
//					System.out.println("Choose an option:");
//					System.out.println("1. Greater than " + dateString);
//					System.out.println("2. Equal to " + dateString);
//					System.out.println("3. Less than " + dateString);
//					System.out.println("4. Go back <<---");
//
//					choice = sc.nextInt();
//					String operator = "";
//
//					switch (choice) {
//					case 1:
//						operator = ">";
//						break;
//					case 2:
//						operator = "=";
//						break;
//					case 3:
//						operator = "<";
//						break;
//					case 4:
//						return; // Go back
//					default:
//						System.out.println("Invalid option");
//						continue; // Continue loop for invalid input
//					}
//
//					String sql = "SELECT * FROM Job_Opportunity WHERE Coming_Dt " + operator + " ?";
//					PreparedStatement pstmt = conn.prepareStatement(sql);
//					pstmt.setString(1, dateString);
//					ResultSet rs = pstmt.executeQuery();
//
//					if (!rs.next()) {
//						System.out.println("Not found in our records");
//					} else {
//						rs.beforeFirst(); // Move cursor back to first position
//						while (rs.next()) {
//							int compId = rs.getInt("Comp_id");
//							String compName = rs.getString("Comp_name");
//							System.out.println("Company ID: " + compId + ", Company Name: " + compName);
//						}
//					}
//
//					rs.close();
//					pstmt.close();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			} else {
//				System.out.println("Invalid date format! Please use the format (yyyy-mm-dd).");
//			}
//		} while (choice != 4);
//	}
//
//	public void checkCompaniesAtLocationForBranch() {
//		try {
//			System.out.println("PRINTS COMPANY DETAILS FOR SPECIFIC STUDENT BRANCH AND REQUIRED LOCATION-");
//			System.out.print("Enter Branch: ");
//			String branch = sc.nextLine();
//			System.out.print("Enter Location: ");
//			String location = sc.nextLine();
//
//			String sql = "SELECT c.Comp_name, c.Post, c.Location " + "FROM Company c "
//					+ "INNER JOIN Records r ON c.Comp_id = r.Comp_Id "
//					+ "INNER JOIN Student_info s ON r.Stud_Id = s.Stud_id " + "WHERE s.Branch = ? AND c.Location = ?";
//			PreparedStatement pstmt = conn.prepareStatement(sql);
//			pstmt.setString(1, branch);
//			pstmt.setString(2, location);
//
//			ResultSet rs = pstmt.executeQuery();
//			if (!rs.next()) {
//				System.out.println("Not found in our records");
//			} else {
//				rs.beforeFirst(); // Move cursor back to first position
//				while (rs.next()) {
//					String companyName = rs.getString("Comp_name");
//					String post = rs.getString("Post");
//					String compLocation = rs.getString("Location");
//
//					System.out.println("Company Name: " + companyName);
//					System.out.println("Post: " + post);
//					System.out.println("Location: " + compLocation);
//					System.out.println("-----------------------------------");
//				}
//			}
//
//			rs.close();
//			pstmt.close();
//		} catch (SQLException e) {
//			System.out.println("Error: " + e.getMessage());
//		}
//	}
//
//	public void printStudentsForCompanyBranch() {
//		try {
//			System.out.print("Enter Company Name: ");
//			String companyName = sc.nextLine();
//
//			System.out.print("Enter Branch: ");
//			String branch = sc.nextLine();
//
//			String sql = "SELECT s.Stud_name, s.Branch " + "FROM Student_info s "
//					+ "INNER JOIN Records r ON s.Stud_id = r.Stud_Id "
//					+ "INNER JOIN Company c ON r.Comp_Id = c.Comp_id " + "WHERE c.Comp_name = ? AND s.Branch = ?";
//			PreparedStatement pstmt = conn.prepareStatement(sql);
//			pstmt.setString(1, companyName);
//			pstmt.setString(2, branch);
//
//			ResultSet rs = pstmt.executeQuery();
//			if (!rs.next()) {
//				System.out.println("Not found in our records");
//			} else {
//				rs.beforeFirst(); // Move cursor back to first position
//				while (rs.next()) {
//					String studentName = rs.getString("Stud_name");
//					String studentBranch = rs.getString("Branch");
//
//					System.out.println("Student Name: " + studentName);
//					System.out.println("Branch: " + studentBranch);
//					System.out.println("-----------------------------------");
//				}
//			}
//
//			rs.close();
//			pstmt.close();
//		} catch (SQLException e) {
//			System.out.println("Error: " + e.getMessage());
//		}
//	}
//
//	public void printQualifyingStudentsForCompany() {
//		try {
//			System.out.print("Enter Company Name: ");
//			String companyName = sc.nextLine();
//			System.out.print("Enter CGPA to get students above that\n(and are present in record): ");
//			float cgpaCriteria = sc.nextFloat();
//
//			String sql = "SELECT s.Stud_name, s.CGPA " + "FROM Student_info s "
//					+ "INNER JOIN Records r ON s.Stud_id = r.Stud_Id "
//					+ "INNER JOIN Company c ON r.Comp_Id = c.Comp_id " + "WHERE c.Comp_name = ? AND s.CGPA >= ?";
//			PreparedStatement pstmt = conn.prepareStatement(sql);
//			pstmt.setString(1, companyName);
//			pstmt.setFloat(2, cgpaCriteria);
//
//			ResultSet rs = pstmt.executeQuery();
//			if (!rs.next()) {
//				System.out.println("Not found in our records");
//			} else {
//				rs.beforeFirst(); // Move cursor back to first position
//				while (rs.next()) {
//					String studentName = rs.getString("Stud_name");
//					float studentCGPA = rs.getFloat("CGPA");
//
//					System.out.println("Student Name: " + studentName);
//					System.out.println("CGPA: " + studentCGPA);
//					System.out.println("-----------------------------------");
//				}
//			}
//
//			rs.close();
//			pstmt.close();
//		} catch (SQLException e) {
//			System.out.println("Error: " + e.getMessage());
//		}
//	}
//
//	public void getVacancyForCompanyPost() {
//		try {
//			System.out.print("Enter Company Name: ");
//			String companyName = sc.nextLine();
//
//			System.out.print("Enter Post: ");
//			String post = sc.nextLine();
//
//			String sql = "SELECT Comp_name, Post, Vacancy " + "FROM Company " + "WHERE Comp_name = ? AND Post = ?";
//			PreparedStatement pstmt = conn.prepareStatement(sql);
//			pstmt.setString(1, companyName);
//			pstmt.setString(2, post);
//
//			ResultSet rs = pstmt.executeQuery();
//			if (!rs.next()) {
//				System.out.println("Not found in our records");
//			} else {
//				rs.beforeFirst(); // Move cursor back to first position
//				while (rs.next()) {
//					String compName = rs.getString("Comp_name");
//					String compPost = rs.getString("Post");
//					int vacancy = rs.getInt("Vacancy");
//
//					System.out.println("Company Name: " + compName);
//					System.out.println("Post: " + compPost);
//					System.out.println("Vacancy: " + vacancy);
//					System.out.println("-----------------------------------");
//				}
//			}
//
//			rs.close();
//			pstmt.close();
//		} catch (SQLException e) {
//			System.out.println("Error: " + e.getMessage());
//		}
//	}
//
//	public void getStudentsForCompanySalary() {
//		try {
//			System.out.println("GET STUDENTS WHO ARE IN OUR RECORD WITH GIVEN SALARY-");
//			System.out.print("Enter Salary: ");
//			int salary = sc.nextInt();
//			sc.nextLine(); // Consume newline
//
//			String sql = "SELECT s.Stud_name, s.Branch, c.Comp_name, c.Post " + "FROM Student_info s "
//					+ "INNER JOIN Records r ON s.Stud_id = r.Stud_Id "
//					+ "INNER JOIN Company c ON r.Comp_Id = c.Comp_id " + "WHERE c.Salary = ?";
//			PreparedStatement pstmt = conn.prepareStatement(sql);
//			pstmt.setInt(1, salary);
//
//			ResultSet rs = pstmt.executeQuery();
//			if (!rs.next()) {
//				System.out.println("Not found in our records");
//			} else {
//				rs.beforeFirst(); // Move cursor back to first position
//				while (rs.next()) {
//					String studentName = rs.getString("Stud_name");
//					String studentBranch = rs.getString("Branch");
//					String compName = rs.getString("Comp_name");
//					String compPost = rs.getString("Post");
//
//					System.out.println("Student Name: " + studentName);
//					System.out.println("Branch: " + studentBranch);
//					System.out.println("Company Name: " + compName);
//					System.out.println("Post: " + compPost);
//					System.out.println("-----------------------------------");
//				}
//			}
//
//			rs.close();
//			pstmt.close();
//		} catch (SQLException e) {
//			System.out.println("Error: " + e.getMessage());
//		}
//	
//	}
//}

import java.sql.*;
import java.util.*;
public class Company {
    Scanner sc = new Scanner(System.in);
    Connection conn;
    Statement stmt;

    // Constructor to initialize JDBC connection
    public Company() {
        try {
         
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/miniproject1?useSSL=false", "root", "Dhanu@23");
            stmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
   
   
    public void insertCompany() {
        try {
            System.out.print("Enter Company ID: ");
            int compId = sc.nextInt();
            sc.nextLine(); // Consume newline
            System.out.print("Enter Company Name: ");
            String compName = sc.nextLine();
            System.out.print("Enter Location: ");
            String location = sc.nextLine();

            String sql = "INSERT INTO Company (Comp_id, Comp_name, Location) " +
                         "VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, compId);
            pstmt.setString(2, compName);
            pstmt.setString(3, location);

            int rowsAffected = pstmt.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted into Company table.");

            // Inserting into Job_Opportunity table
            System.out.println("Enter Job Opportunity Details:");

            System.out.print("Enter Job ID: ");
            int jobId = sc.nextInt();
            sc.nextLine(); // Consume newline
            System.out.print("Enter Post: ");
            String post = sc.nextLine();
            System.out.print("Enter CGPA Criteria (greater than 6.0): ");
            float cgpaCriteria = sc.nextFloat();
            System.out.print("Enter Salary: ");
            int salary = sc.nextInt();
            System.out.print("Enter Vacancy: ");
            int vacancy = sc.nextInt();
            sc.nextLine(); // Consume newline
            System.out.print("Enter Duration (default 'NULL'): ");
            String duration = sc.nextLine();
            System.out.print("Enter Coming Date (yyyy-MM-dd): ");
            String comingDate = sc.next();

            String sqlJob = "INSERT INTO Job_Opportunity (Job_id, Comp_id, Post, CGPA_Criteria, Salary, Vacancy, Duration, Coming_Dt) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmtJob = conn.prepareStatement(sqlJob);
            pstmtJob.setInt(1, jobId);
            pstmtJob.setInt(2, compId);
            pstmtJob.setString(3, post);
            pstmtJob.setFloat(4, cgpaCriteria);
            pstmtJob.setInt(5, salary);
            pstmtJob.setInt(6, vacancy);
            pstmtJob.setString(7, duration);
            pstmtJob.setString(8, comingDate);

            int jobRowsAffected = pstmtJob.executeUpdate();
            System.out.println(jobRowsAffected + " row(s) inserted into Job_Opportunity table.");

            // Inserting into Test_Information table
            System.out.println("Enter Test Information:");

            System.out.print("Enter Test ID: ");
            int testId = sc.nextInt();
            sc.nextLine(); // Consume newline
            System.out.print("Enter Event (default 'NULL'): ");
            String event = sc.nextLine();
            System.out.print("Enter Platform (default 'NULL'): ");
            String platform = sc.nextLine();
            System.out.print("Enter Time (default 'NULL'): ");
            String time = sc.nextLine();

            String sqlTest = "INSERT INTO Test_Information (Test_id, Job_id, Event, Platform, Time) " +
                             "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmtTest = conn.prepareStatement(sqlTest);
            pstmtTest.setInt(1, testId);
            pstmtTest.setInt(2, jobId);
            pstmtTest.setString(3, event);
            pstmtTest.setString(4, platform);
            pstmtTest.setString(5, time);

            int testRowsAffected = pstmtTest.executeUpdate();
            System.out.println(testRowsAffected + " row(s) inserted into Test_Information table.");

            pstmt.close();
            pstmtJob.close();
            pstmtTest.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   
   
    public void deleteCompany() {
        try {
            System.out.print("Enter Company ID to delete: ");
            int compId = sc.nextInt();
            sc.nextLine(); // Consume newline

            // Deleting from Test_Information table
            String sqlTest = "DELETE FROM Test_Information WHERE Job_id IN " +
                             "(SELECT Job_id FROM Job_Opportunity WHERE Comp_id = ?)";
            PreparedStatement pstmtTest = conn.prepareStatement(sqlTest);
            pstmtTest.setInt(1, compId);

            int testRowsAffected = pstmtTest.executeUpdate();
            pstmtTest.close();

            // Deleting from Job_Opportunity table
            String sqlJob = "DELETE FROM Job_Opportunity WHERE Comp_id = ?";
            PreparedStatement pstmtJob = conn.prepareStatement(sqlJob);
            pstmtJob.setInt(1, compId);

            int jobRowsAffected = pstmtJob.executeUpdate();
            pstmtJob.close();

            // Deleting from Company table
            String sql = "DELETE FROM Company WHERE Comp_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, compId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println(rowsAffected + " row(s) deleted from Company table.");
                System.out.println(jobRowsAffected + " row(s) deleted from Job_Opportunity table.");
                System.out.println(testRowsAffected + " row(s) deleted from Test_Information table.");
            } else {
                System.out.println("No company found with ID: " + compId);
            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   
   
   
    public void displayAllCompanies() {
        try {
            String sql = "SELECT c.Comp_id, c.Comp_name, j.Post, c.Location, j.CGPA_Criteria, j.Salary, j.Vacancy, j.Duration, j.Coming_Dt, t.Event, t.Platform, t.Time " +
                         "FROM Company c " +
                         "INNER JOIN Job_Opportunity j ON c.Comp_id = j.Comp_id " +
                         "INNER JOIN Test_Information t ON j.Job_id = t.Job_id";
            ResultSet rs = stmt.executeQuery(sql);

            StringBuilder tableBuilder = new StringBuilder();
            tableBuilder.append("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
            tableBuilder.append("| Comp_id | Company Name  |       Post       |  Location  | CGPA Criteria | Salary | Vacancy |   Duration   | Coming Date |    Event    |   Platform   |  Time  |\n");
            tableBuilder.append("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");

            while (rs.next()) {
                int compId = rs.getInt("Comp_id");
                String compName = rs.getString("Comp_name");
                String post = rs.getString("Post");
                String location = rs.getString("Location");
                float cgpaCriteria = rs.getFloat("CGPA_Criteria");
                int salary = rs.getInt("Salary");
                int vacancy = rs.getInt("Vacancy");
                String duration = rs.getString("Duration");
                String comingDate = rs.getString("Coming_Dt");
                String event = rs.getString("Event");
                String platform = rs.getString("Platform");
                String time = rs.getString("Time");

                tableBuilder.append(String.format("| %-8s| %-13s| %-17s| %-12s| %-14s| %-7s| %-8s| %-13s| %-12s| %-13s| %-13s| %-7s|\n",
                        compId, compName, post, location, cgpaCriteria, salary, vacancy, duration, comingDate, event, platform, time));
            }

            tableBuilder.append("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
            System.out.println(tableBuilder.toString());

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


   
   
    public void updateCompany() {
        try {
            System.out.print("Enter Company ID to update: ");
            int compId = sc.nextInt();
            sc.nextLine(); // Consume newline

            System.out.println("What do you want to update?");
            System.out.println("1. Company Name");
            System.out.println("2. Location");
            System.out.println("3. Job Opportunity Details");
            System.out.println("4. Test Information");
            System.out.println("5. Cancel");

            int choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    updateCompanyName(compId);
                    break;
                case 2:
                    updateCompanyLocation(compId);
                    break;
                case 3:
                    updateJobOpportunity(compId);
                    break;
                case 4:
                    updateTestInformation(compId);
                    break;
                case 5:
                    System.out.println("Update canceled.");
                    return;
                default:
                    System.out.println("Invalid option");
                    return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateCompanyName(int compId) throws SQLException {
        System.out.print("Enter new Company Name: ");
        String newName = sc.nextLine();

        String sql = "UPDATE Company SET Comp_name = ? WHERE Comp_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, newName);
        pstmt.setInt(2, compId);

        int rowsAffected = pstmt.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println(rowsAffected + " row(s) updated.");
        } else {
            System.out.println("No company found with ID: " + compId);
        }
        pstmt.close();
    }

    private void updateCompanyLocation(int compId) throws SQLException {
        System.out.print("Enter new Location: ");
        String newLocation = sc.nextLine();

        String sql = "UPDATE Company SET Location = ? WHERE Comp_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, newLocation);
        pstmt.setInt(2, compId);

        int rowsAffected = pstmt.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println(rowsAffected + " row(s) updated.");
        } else {
            System.out.println("No company found with ID: " + compId);
        }
        pstmt.close();
    }

    private void updateJobOpportunity(int compId) throws SQLException {
        System.out.print("Enter new Post: ");
        String newPost = sc.nextLine();
        System.out.print("Enter new CGPA Criteria: ");
        float newCGPA = sc.nextFloat();
        System.out.print("Enter new Salary: ");
        int newSalary = sc.nextInt();
        System.out.print("Enter new Vacancy: ");
        int newVacancy = sc.nextInt();
        sc.nextLine(); // Consume newline

        String sql = "UPDATE Job_Opportunity SET Post = ?, CGPA_Criteria = ?, Salary = ?, Vacancy = ? WHERE Comp_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, newPost);
        pstmt.setFloat(2, newCGPA);
        pstmt.setInt(3, newSalary);
        pstmt.setInt(4, newVacancy);
        pstmt.setInt(5, compId);

        int rowsAffected = pstmt.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println(rowsAffected + " row(s) updated.");
        } else {
            System.out.println("No job opportunity found for company ID: " + compId);
        }
        pstmt.close();
    }

    private void updateTestInformation(int compId) throws SQLException {
        System.out.print("Enter new Event: ");
        String newEvent = sc.nextLine();
        System.out.print("Enter new Platform: ");
        String newPlatform = sc.nextLine();
        System.out.print("Enter new Time: ");
        String newTime = sc.nextLine();

        String sql = "UPDATE Test_Information SET Event = ?, Platform = ?, Time = ? WHERE Job_id IN " +
                     "(SELECT Job_id FROM Job_Opportunity WHERE Comp_id = ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, newEvent);
        pstmt.setString(2, newPlatform);
        pstmt.setString(3, newTime);
        pstmt.setInt(4, compId);

        int rowsAffected = pstmt.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println(rowsAffected + " row(s) updated.");
        } else {
            System.out.println("No test information found for company ID: " + compId);
        }
        pstmt.close();
    }
   
   
   

    public void comp_salary() {
        int choice = 0;
        do {
            System.out.print("Enter the salary to search a company: ");
            int sal = sc.nextInt();

            try {
                System.out.println("1. Greater than " + sal + "\n2. Greater than or equal to " + sal +
                        "\n3. Less than " + sal + "\n4. Less than or equal to " + sal + "\n5. Equal to " + sal + "\n6. Go back <<---\nCHOOSE: ");
                choice = sc.nextInt();

                String sql = "";
                switch (choice) {
                    case 1:
                        sql = "SELECT c.Comp_id, c.Comp_name FROM Company c INNER JOIN Job_Opportunity j ON c.Comp_id = j.Comp_id WHERE j.Salary > ?";
                        break;
                    case 2:
                        sql = "SELECT c.Comp_id, c.Comp_name FROM Company c INNER JOIN Job_Opportunity j ON c.Comp_id = j.Comp_id WHERE j.Salary >= ?";
                        break;
                    case 3:
                        sql = "SELECT c.Comp_id, c.Comp_name FROM Company c INNER JOIN Job_Opportunity j ON c.Comp_id = j.Comp_id WHERE j.Salary < ?";
                        break;
                    case 4:
                        sql = "SELECT c.Comp_id, c.Comp_name FROM Company c INNER JOIN Job_Opportunity j ON c.Comp_id = j.Comp_id WHERE j.Salary <= ?";
                        break;
                    case 5:
                        sql = "SELECT c.Comp_id, c.Comp_name FROM Company c INNER JOIN Job_Opportunity j ON c.Comp_id = j.Comp_id WHERE j.Salary = ?";
                        break;
                    case 6:
                        return; // Go back
                    default:
                        System.out.println("Invalid option");
                        continue; // Continue loop for invalid input
                }

                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, sal);
                ResultSet rs = pstmt.executeQuery();

                if (!rs.next()) {
                    System.out.println("Not found in our records");
                } else {
                    rs.beforeFirst(); // Move cursor back to first position
                    while (rs.next()) {
                        int compId = rs.getInt("Comp_id");
                        String compName = rs.getString("Comp_name");
                        System.out.println("Company ID: " + compId + ", Company Name: " + compName);
                    }
                }

                rs.close();
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } while (choice != 6);
    }

   

    public void comp_CGPA() {
        System.out.print("Enter the CGPA criteria to search companies: ");
        float cgpaCriteria = sc.nextFloat();
        try {
            String sql = "SELECT c.Comp_id, c.Comp_name FROM Company c INNER JOIN Job_Opportunity j ON c.Comp_id = j.Comp_id WHERE j.CGPA_Criteria >= ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setFloat(1, cgpaCriteria);
            ResultSet rs = pstmt.executeQuery();

            if (!rs.next()) {
                System.out.println("Not found in our records");
            } else {
                rs.beforeFirst(); // Move cursor back to first position
                while (rs.next()) {
                    int compId = rs.getInt("Comp_id");
                    String compName = rs.getString("Comp_name");
                    System.out.println("Company ID: " + compId + ", Company Name: " + compName);
                }
            }

            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void comp_coming_dt() {
        int choice = 0;
        String dateString;
        do {
            System.out.print("Enter the date in (yyyy-mm-dd): ");
            dateString = sc.next();
            sc.nextLine();
            if (dateString.matches("\\d{4}-\\d{2}-\\d{2}")) {
                System.out.println("---------------------");
                try {
                    System.out.println("Choose an option:");
                    System.out.println("1. Greater than " + dateString);
                    System.out.println("2. Equal to " + dateString);
                    System.out.println("3. Less than " + dateString);
                    System.out.println("4. Go back <<---");

                    choice = sc.nextInt();
                    String operator = "";

                    switch (choice) {
                        case 1:
                            operator = ">";
                            break;
                        case 2:
                            operator = "=";
                            break;
                        case 3:
                            operator = "<";
                            break;
                        case 4:
                            return; // Go back
                        default:
                            System.out.println("Invalid option");
                            continue; // Continue loop for invalid input
                    }

                    String sql = "SELECT c.Comp_id, c.Comp_name FROM Company c INNER JOIN Job_Opportunity j ON c.Comp_id = j.Comp_id WHERE j.Coming_Dt " + operator + " ?";
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, dateString);
                    ResultSet rs = pstmt.executeQuery();

                    if (!rs.next()) {
                        System.out.println("Not found in our records");
                    } else {
                        rs.beforeFirst(); // Move cursor back to first position
                        while (rs.next()) {
                            int compId = rs.getInt("Comp_id");
                            String compName = rs.getString("Comp_name");
                            System.out.println("Company ID: " + compId + ", Company Name: " + compName);
                        }
                    }

                    rs.close();
                    pstmt.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Invalid date format! Please use the format (yyyy-mm-dd).");
            }
        } while (choice != 4);
    }

   
    public void checkCompaniesAtLocationForBranch() {
        try {
            System.out.println("PRINTS COMPANY DETAILS FOR SPECIFIC STUDENT BRANCH AND REQUIRED LOCATION-");        
            System.out.print("Enter Branch: ");
            String branch = sc.nextLine();
            System.out.print("Enter Location: ");
            String location = sc.nextLine();
           
            String sql = "SELECT c.Comp_name, c.Location " +
                         "FROM Company c " +
                         "INNER JOIN Records r ON c.Comp_id = r.Comp_Id " +
                         "INNER JOIN Student_info s ON r.Stud_Id = s.Stud_id " +
                         "INNER JOIN Academic a ON s.Stud_id = a.Stud_id " +
                         "WHERE a.Branch = ? AND c.Location = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, branch);
            pstmt.setString(2, location);
           
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {
                System.out.println("No companies found at the specified location for the given branch.");
            } else {
                rs.beforeFirst(); // Move cursor back to first position
                System.out.println("Companies at " + location + " for " + branch + " branch:");
                while (rs.next()) {
                    String companyName = rs.getString("Comp_name");
                    String compLocation = rs.getString("Location");
                   
                    System.out.println("Company Name: " + companyName);
                    System.out.println("Location: " + compLocation);
                    System.out.println("-----------------------------------");
                }
            }
           
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

   
    public void printStudentsForCompanyBranch() {
        try {
            System.out.print("Enter Company Name: ");
            String companyName = sc.nextLine();

            System.out.print("Enter Branch: ");
            String branch = sc.nextLine();

            String sql = "SELECT s.Stud_name, a.Branch " +
                         "FROM Student_info s " +
                         "INNER JOIN Records r ON s.Stud_id = r.Stud_Id " +
                         "INNER JOIN Company c ON r.Comp_Id = c.Comp_id " +
                         "INNER JOIN Academic a ON s.Stud_id = a.Stud_id " +
                         "WHERE c.Comp_name = ? AND a.Branch = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, companyName);
            pstmt.setString(2, branch);

            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {
                System.out.println("No students found in our records for the specified company and branch.");
            } else {
                rs.beforeFirst(); // Move cursor back to first position
                System.out.println("Students of " + companyName + " in " + branch + " branch:");
                while (rs.next()) {
                    String studentName = rs.getString("Stud_name");
                    String studentBranch = rs.getString("Branch");

                    System.out.println("Student Name: " + studentName);
                    System.out.println("Branch: " + studentBranch);
                    System.out.println("-----------------------------------");
                }
            }

            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }




    public void printQualifyingStudentsForCompany() {
        try {
            System.out.print("Enter Company Name: ");
            String companyName = sc.nextLine();
            System.out.print("Enter CGPA to get students above that (and are present in the record): ");
            float cgpaCriteria = sc.nextFloat();
           
            String sql = "SELECT s.Stud_name, a.CGPA " +
                         "FROM Student_info s " +
                         "INNER JOIN Academic a ON s.Stud_id = a.Stud_id " +
                         "INNER JOIN Records r ON s.Stud_id = r.Stud_Id " +
                         "INNER JOIN Company c ON r.Comp_Id = c.Comp_id " +
                         "WHERE c.Comp_name = ? AND a.CGPA >= ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, companyName);
            pstmt.setFloat(2, cgpaCriteria);
           
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {
                System.out.println("No students found in our records with the specified criteria.");
            } else {
                rs.beforeFirst(); // Move cursor back to the first position
                System.out.println("Qualifying Students for " + companyName + " with CGPA >= " + cgpaCriteria + ":");
                while (rs.next()) {
                    String studentName = rs.getString("Stud_name");
                    float studentCGPA = rs.getFloat("CGPA");
                   
                    System.out.println("Student Name: " + studentName);
                    System.out.println("CGPA: " + studentCGPA);
                    System.out.println("-----------------------------------");
                }
            }
           
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


   
   
    public void getVacancyForCompanyPost() {
        try {
            System.out.print("Enter Company Name: ");
            String companyName = sc.nextLine();

            System.out.print("Enter Post: ");
            String post = sc.nextLine();

            String sql = "SELECT c.Comp_name, j.Post, j.Vacancy " +
                         "FROM Company c " +
                         "INNER JOIN Job_Opportunity j ON c.Comp_id = j.Comp_id " +
                         "WHERE c.Comp_name = ? AND j.Post = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, companyName);
            pstmt.setString(2, post);

            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {
                System.out.println("No records found for the specified company and post.");
            } else {
                rs.beforeFirst(); // Move cursor back to first position
                while (rs.next()) {
                    String compName = rs.getString("Comp_name");
                    String compPost = rs.getString("Post");
                    int vacancy = rs.getInt("Vacancy");

                    System.out.println("Company Name: " + compName);
                    System.out.println("Post: " + compPost);
                    System.out.println("Vacancy: " + vacancy);
                    System.out.println("-----------------------------------");
                }
            }

            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }




   
    public void getStudentsForCompanySalary() {
        try {
            System.out.println("GET STUDENTS WHO ARE IN OUR RECORD WITH GIVEN SALARY-");
            System.out.print("Enter Salary: ");
            int salary = sc.nextInt();
            sc.nextLine(); // Consume newline

            String sql = "SELECT s.Stud_name, a.Branch, c.Comp_name, j.Post " +
                         "FROM Student_info s " +
                         "INNER JOIN Records r ON s.Stud_id = r.Stud_Id " +
                         "INNER JOIN Academic a ON s.Stud_id = a.Stud_id " +
                         "INNER JOIN Job_Opportunity j ON r.Comp_Id = j.Comp_id " +
                         "INNER JOIN Company c ON j.Comp_id = c.Comp_id " +
                         "WHERE j.Salary = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, salary);

            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {
                System.out.println("Not found in our records");
            } else {
                rs.beforeFirst(); // Move cursor back to first position
                while (rs.next()) {
                    String studentName = rs.getString("Stud_name");
                    String studentBranch = rs.getString("Branch");
                    String compName = rs.getString("Comp_name");
                    String compPost = rs.getString("Post");

                    System.out.println("Student Name: " + studentName);
                    System.out.println("Branch: " + studentBranch);
                    System.out.println("Company Name: " + compName);
                    System.out.println("Post: " + compPost);
                    System.out.println("-----------------------------------");
                }
            }

            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
