Human Resource Management System (HRMS)
This project is a Java-based Human Resource Management System (HRMS) developed as part of an Object-Oriented Programming (OOP) coursework assignment. It allows for the management of employee records, processing of employee data, and performing automated actions such as email notifications and salary updates.

Features
Employee Management
Implements a class hierarchy for employees, each uniquely identified based on their area and manager status.

Employees and managers are represented as objects, with managers having direct references to employees they supervise.

Employees are categorized into different areas, determined by the getArea() method.

Encapsulation ensures data protection, with getters to access private fields.

Data Processing & Storage
Employee data is imported from employees_v1.csv and can be exported to employees_v2.csv.

Uses streams and lambda expressions for efficient data filtering and processing.

Supports error handling for invalid employee data (e.g., negative salary, missing names, invalid IDs).

Includes a List structure for employee storage.

EmployeeList Class
Stores employee objects in a List.

Provides methods for:

Adding employees individually or from a CSV file.

Assigning employees to managers, ensuring validity checks on manager IDs and areas.

Processing employees using Predicate (filtering criteria) and Consumer (action execution).

Mapping action names to specific employee processing functions.

Running actions dynamically based on a given name.

Exporting employee records to a file.

Automated Actions & Notifications
Manager Analysis: Identifies managers who supervise fewer than 5 employees and outputs relevant details.

Email Notifications:

Sends notification emails to employees who are not managers and whose last name starts with ‘A’.

Email content is dynamically generated with the employee’s name.

Uses Notifications (from ca_apis.zip) to retrieve email contacts.

Sends emails via the EmailSystem (from ca_apis.zip).

Salary Adjustments:

Employees earning less than 40,000 receive a 10% salary increase.
