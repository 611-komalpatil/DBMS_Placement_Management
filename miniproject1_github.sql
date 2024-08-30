Create Database miniproject1;
Use miniproject1;

select * from academic;
Select * from Student_info;
Select * from Company;
Select * from Records;
select *from Job_Opportunity;
select *from Test_information;

Create Table Records(
Stud_id int,
Comp_id int,
primary key(Stud_id,Comp_id),
foreign key(Stud_id) references Student_info(Stud_id),
foreign key(Comp_id) references Company(Comp_id),
Applied varchar(20) default 'Not Applied',
Shortlist varchar(20) default 'NULL',
Placement_Status varchar(20) default 'Not Placed'
);
INSERT INTO Records (Stud_id, Comp_id, Applied, Shortlist, Placement_Status) VALUES
(1, 1, 'Applied', 'Round 1', 'Not Placed'),
(2, 1, 'Applied', 'Interview', ' Not Placed'),
(2, 3, 'Applied', 'Round 2', 'Not Placed'),
(2, 4, 'Not Applied', NULL, 'Not Placed'),
(3, 5, 'Applied', 'Interview', 'Placed'),
(3, 6, 'Applied', 'Round 1', 'Not Placed'),
(4, 4, 'Applied', 'Interview', 'Placed'), -- Modified Comp_id to match existing record in the Company table
(4, 6, 'Not Applied', NULL, 'Not Placed');


CREATE TABLE Student_info (
    Stud_id INT PRIMARY KEY,
    Stud_name VARCHAR(50),
    Email VARCHAR(100),
    PhoneNo char(10),
    DOB DATE
);

CREATE TABLE Academic (
    Stud_id INT PRIMARY KEY,
    Branch VARCHAR(50),
    CGPA float check(CGPA>6),
    Resume_Uploaded VARCHAR(3) default 'no',
    FOREIGN KEY (Stud_id) REFERENCES Student_info(Stud_id)
);

-- -- Inserting data into the Students table
INSERT INTO Student_info (Stud_id, Stud_name, Email, PhoneNo, DOB) VALUES
(1, 'Sneha Deshmukh', 'sneha.deshmukh@example.com', 1234567890, '2000-05-15'),
(2, 'Akshay Patil', 'akshay.patil@example.com', 2345678901, '2001-01-20'),
(3, 'Neha Kulkarni', 'neha.kulkarni@example.com', 4567890123, '1999-11-10'),
(4, 'Aniket Pawar', 'aniket.pawar@example.com', 6789012345, '2000-03-08'),
(5, 'Pooja More', 'pooja.more@example.com', 8901234567, '2000-07-12'),
(6, 'Sonali Kadam', 'sonali.kadam@example.com', 9012345678, '2000-10-29'),
(7, 'Vishal Mane', 'vishal.mane@example.com', 3456789012, '2000-07-03'),
(8, 'Priya Gaikwad', 'priya.gaikwad@example.com', 7890123456, '2000-11-18'),
(9, 'Prasad Kulkarni', 'prasad.kulkarni@example.com', 9012345678, '2000-12-03'),
(10, 'Aarti Chavan', 'aarti.chavan@example.com', 6789012345, '2000-05-30');

-- Inserting data into the Academic table
INSERT INTO Academic (Stud_id, Branch, CGPA, Resume_Uploaded) VALUES
(1, 'Computer Science', 8.5, 'yes'),
(2, 'Electrical', 7.9, 'no'),
(3, 'Mechanical', 8.0, 'yes'),
(4, 'Instrumentation', 8.6, 'no'),
(5, 'ENTC', 8.4, 'yes'),
(6, 'Computer Science', 7.3, 'yes'),
(7, 'Mechanical', 7.7, 'yes'),
(8, 'Computer Science', 8.9, 'no'),
(9, 'Electrical', 9.5, 'yes'),
(10, 'Computer Science', 7.1, 'no');

-- Create Company table
CREATE TABLE Company (
    Comp_id INT PRIMARY KEY,
    Comp_name VARCHAR(100),
    Location VARCHAR(100)
);

-- Create Job_Opportunity table
CREATE TABLE Job_Opportunity (
    Job_id INT unique not null,
    Comp_id INT,
    Post VARCHAR(100),
    primary key(Job_id,Comp_id),
    CGPA_Criteria float check(CGPA_Criteria >6),
    Salary int,
    Vacancy int,
    Duration VARCHAR(20) default 'NULL',
    Coming_Dt date ,
    FOREIGN KEY (Comp_id) REFERENCES Company(Comp_id)
);

-- Create Test_Information table
CREATE TABLE Test_Information (
    Test_id INT PRIMARY KEY,
    Job_id INT,
    Event VARCHAR(100) default 'NULL',
    Platform VARCHAR(100) default 'NULL',
    Time varchar(20) default 'NULL',
    FOREIGN KEY (Job_id) REFERENCES Job_Opportunity(Job_id)
);

INSERT INTO Company (Comp_id, Comp_name, Location) VALUES
(1, 'Amazon', 'Mumbai'),
(2, 'Google', 'California'),
(3, 'Microsoft', 'Washington'),
(4, 'Apple', 'California'),
(5, 'Facebook', 'California'),
(6, 'Tesla', 'California'),
(7, 'IBM', 'New York'),
(8, 'Samsung', 'Seoul'),
(9, 'Intel', 'California'),
(10, 'Oracle', 'California');

INSERT INTO Job_Opportunity (Job_id, Comp_id, Post,CGPA_Criteria, Salary, Vacancy,Duration) VALUES
(1, 1, 'Software Engineer', 8.0, 100000, 3,'Full-time'),
(2, 2, 'Data Analyst', 7.5, 80000, 2, 'Contract'),
(3, 3, 'Marketing Specialist', 7.0, 90000, 2, 'Full-time'),
(4, 4, 'Financial Analyst', 8.5, 120000, 3, 'Full-time'),
(5, 5, 'Software Developer', 8.2, 110000, 2,'Full-time'),
(6, 6, 'Sales Representative', 7.8, 95000, 2, 'Full-time'),
(7, 7, 'IT Consultant', 8.5, 115000, 3,'Contract'),
(8, 8, 'Project Manager', 8.0, 130000,3, 'Full-time'),
(9, 9, 'Business Analyst', 7.5, 100000,2, 'Contract'),
(10, 10, 'Operations Manager', 8.0, 120000,1, 'Full-time');

INSERT INTO Test_Information (Test_id, Job_id, Event, Platform, Time) VALUES
(1, 1, 'Online', 'Hackerrank', '09:00:00'),
(2, 2, 'In-person', 'Company Premises', '10:00:00'),
(3, 3, 'Online', 'GFG', '11:00:00'),
(4, 4, 'Online', 'LeetCode', '12:00:00'),
(5, 5, 'Online', 'CodeSignal', '13:00:00'),
(6, 6, 'In-person', 'Recruitment Event', '14:00:00'),
(7, 7, 'Online', 'Hackerearth', '15:00:00'),
(8, 8, 'In-person', 'Company Office', '16:00:00'),
(9, 9, 'Online', 'Test Platform X', '17:00:00'),
(10, 10, 'Online', 'Test Platform Y', '18:00:00');


Update Job_Opportunity
set coming_Dt='2024-04-12'
where Comp_id=1;

Update Job_Opportunity
set coming_Dt='2024-02-13'
where Comp_id=2;

Update Job_Opportunity
set coming_Dt='2024-04-12'
where Comp_id=3;

select *from Job_Opportunity;