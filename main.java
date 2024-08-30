package miniproject;

import java.util.*;

import java.sql.*;

class Main {

	static int Stud_id;

	static String Stud_name;

	static String Email;

	static String PhoneNo;

	static String DOB;

	static String Branch;

	static float CGPA;

	static String Resume;

	static String Internship;

	static final String DB_URL = "jdbc:mysql://localhost:3306/miniproject1?useSSL=false";

	static final String USER = "root";

	static final String pass = "Dhanu@23";

	public static void main(String[] args) throws ClassNotFoundException {

		Scanner sc = new Scanner(System.in);

		Connection conn = null;

		Statement stmt = null;

		boolean b = false;

		boolean adminMode = false; // Flag to track admin mode

		String password = null;

		try {

// Load the driver

			Class.forName("com.mysql.cj.jdbc.Driver");

// Make connection

			conn = DriverManager.getConnection(DB_URL, USER, pass);

			stmt = conn.createStatement();

			int choice = 1;

			int ch = 1;

			Student_info obj = new Student_info();

			Records r = new Records();

			while (choice != 0) {

				System.out.println("Press p for placement cell and s for student, e for exit:");

				String n = sc.next();

				if (n.equalsIgnoreCase("p")) {

					if (!adminMode) {

						System.out.println("Enter admin password:");

						password = sc.next();

						if (password.equals("cummins@123")) {

							adminMode = true;

							System.out.println("Admin mode activated.");

						} else {

							System.out.println("Incorrect password.");

							continue; // Restart the loop to allow re-entry of username

						}

					}

					System.out.println(
							"MENU:\n0.Exit\n1.Insert Student details\n2.Display Student Details\n3.Search Student Details\n4.Extra queries\n5.Queries based on records table\n"

									+ "6.Insert details Records for Students\n7.Display Student Record\n8.Update Student Record\n9.Company related queries\n");

					ch = sc.nextInt();

					switch (ch) {

					case 1:

						if (b) {

							obj.insert();

						} else {

							System.out.println("First take input from Student then insert.");

						}

						break;

					case 2:

						obj.display();

						break;

					case 3:

						obj.search();

						break;

					case 4:

						obj.Extra_query();

						break;

					case 5:

						r.queries_p();

						break;

					case 6:

						r.insert_record();

						break;

					case 7:

						r.display_record();

						break;

					case 8:

						r.update_record();

						break;

					case 9:

						Company company = new Company();

						int choice1;

						do {

							System.out.println("Choose an option:");

							System.out.println("1. Search by salary");

							System.out.println("2. Search by CGPA criteria");

							System.out.println("3. Search by coming date");

							System.out.println("4. Insert new company");

							System.out.println("5. Delete company");

							System.out.println("6. Display all companies");

							System.out.println("7. Update company");

							System.out.println("8. More queries");

							System.out.println("9. Exit");

							choice1 = company.sc.nextInt();

							company.sc.nextLine(); // Consume newline

							switch (choice1) {

							case 1:

								company.comp_salary(); // placement student

								break;

							case 2:

								company.comp_CGPA(); // placement student

								break;

							case 3:

								company.comp_coming_dt(); // placement student

								break;

							case 4:

								company.insertCompany(); // placement

								break;

							case 5:

								company.deleteCompany(); // placement

								break;

							case 6:

								company.displayAllCompanies(); // placement student

								break;

							case 7:

								company.updateCompany(); // placement

								break;

							case 8:

								int subChoice;

								do {

									System.out.println("More Queries:");

									System.out.println(
											"1. Print students from record acc to company and respective branch");

									System.out.println("2. Print company acc to location and student branch");

									System.out
											.println("3. Print students from record for company's sorting acc to CGPA");

									System.out.println("4. Print vacancies for particular company's Post");

									System.out.println(
											"5. Print details of student and company for particular Salary which are present in records");

									System.out.println("6. Back to main menu");

									subChoice = company.sc.nextInt();

									company.sc.nextLine(); // Consume newline

									switch (subChoice) {

									case 1:

										company.printStudentsForCompanyBranch(); // placement student

										break;

									case 2:

										company.checkCompaniesAtLocationForBranch(); // placement student

										break;

									case 3:

										company.printQualifyingStudentsForCompany(); // placement student

										break;

									case 4:

										company.getVacancyForCompanyPost(); // placement student

										break;

									case 5:

										company.getStudentsForCompanySalary(); // placement student

										break;

									case 6:

										System.out.println("Returning to main menu...");

										break;

									default:

										System.out.println("Invalid option");

										break;

									}

								} while (subChoice != 6);

								break;

							case 9:

								System.out.println("Exiting...");

								break;

							default:

								System.out.println("Invalid option");

								break;

							}

						} while (choice1 != 9);

						break;

					case 0:

// exit

						System.out.println("Exit");

						break;

					default:

						System.out.println("Invalid option");

						break;

					}

				} else if (n.equalsIgnoreCase("s")) {

					System.out.println("Student mode activated.");

					int ch2 = 1;

					do {

						System.out.println(
								"MENU: \n0.Exit\n1.Take input from students\n2.Queries from students\n3.Update Student Details\n4.Company related queries\n");

						ch2 = sc.nextInt();

						switch (ch2) {

						case 0:

							break;

						case 1:

							obj.student_input();

							b = true;

							break;

						case 2:

							r.queries_s();

							break;

						case 3:

							obj.update();

							break;

						case 4:

// Student mode company queries

							Company company = new Company();

							int choice2 = 0;

							do {

								System.out.println("Choose an option:");

								System.out.println("1. Search by salary");

								System.out.println("2. Search by CGPA criteria");

								System.out.println("3. Search by coming date");

								System.out.println("4. Display all companies");

								System.out.println("5. More queries");

								System.out.println("6. Exit");

								choice2 = company.sc.nextInt();

								company.sc.nextLine(); // Consume newline

								switch (choice2) {

								case 1:

									company.comp_salary(); // placement student

									break;

								case 2:

									company.comp_CGPA(); // placement student

									break;

								case 3:

									company.comp_coming_dt(); // placement student

									break;

								case 4:

									company.displayAllCompanies(); // placement student

									break;

								case 5:

									int subChoice;

									do {

										System.out.println("More Queries:");

										System.out.println(
												"1. Print students from record acc to company and respective branch");

										System.out.println("2. Print company acc to location and student branch");

										System.out.println(
												"3. Print students from record for company's sorting acc to CGPA");

										System.out.println("4. Print vacancies for particular company's Post");

										System.out.println(
												"5. Print details of student and company for particular Salary which are present in records");

										System.out.println("6. Back to main menu");

										subChoice = company.sc.nextInt();

										company.sc.nextLine(); // Consume newline

										switch (subChoice) {

										case 1:

											company.printStudentsForCompanyBranch(); // placement student

											break;

										case 2:

											company.checkCompaniesAtLocationForBranch(); // placement student

											break;

										case 3:

											company.printQualifyingStudentsForCompany(); // placement student

											break;

										case 4:

											company.getVacancyForCompanyPost(); // placement student

											break;

										case 5:

											company.getStudentsForCompanySalary(); // placement student

											break;

										case 6:

											System.out.println("Returning to main menu...");

											break;

										default:

											System.out.println("Invalid option");

											break;

										}

									} while (subChoice != 6);

									break;

								case 6:

									System.out.println("Exiting...");

									break;

								default:

									System.out.println("Invalid option");

									break;

								}

							} while (choice2 != 6);

							break;

						}

					} while (ch2 != 0);

					adminMode = false;

				} else if (n.equalsIgnoreCase("e")) {

					System.out.println("Exiting");

					break;

				} else {

					System.out.println("Please enter p or s or e !!!");

				}

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

	}

}