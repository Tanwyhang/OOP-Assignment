# Hospital Management System

A comprehensive Java-based Hospital Management System with a console interface. This application enables efficient management of hospital operations through various modules.

## Features

- Patient Management (register, update, discharge patients)
- Doctor Management (hire, update doctor information)
- Nurse Management (hire, update nurse information)
- Room Management (add, remove, update room status)
- Appointment Scheduling (schedule, cancel, update appointments)
- Medical Records Management (create records, add diagnoses and treatments)
- Search functionality across all entities
- Analytics Dashboard
- Data persistence (save/load from CSV files)

## How to Use

To run the app, just go to the src folder which contains a driver class "Main.java"
do:
- javac Main.java
- java Main

1. Run the application (compile or use an extension in vscode)
2. Log in with admin credentials
3. Navigate through the main menu to access different modules:
   - Manage Rooms
   - Manage Patients
   - Manage Doctors
   - Manage Nurses
   - Manage Appointments
   - Manage Medical Records
   - Search/View Data
   - Analytics Dashboard

## Project Structure

### Models
- `Person.java`: Abstract base class for patients and staff
- `Patient.java`: Stores patient information
- `Staff.java`: Base class for medical staff
- `Doctor.java`: Extends Staff with doctor-specific attributes
- `Nurse.java`: Extends Staff with nurse-specific attributes
- `Room.java`: Represents hospital rooms
- `Appointment.java`: Manages appointment information
- `MedicalRecord.java`: Stores patient medical history
- `Admin.java`: Admin user credentials

### Controllers
- `PatientController.java`: Manages patient operations
- `DoctorController.java`: Manages doctor operations
- `NurseController.java`: Manages nurse operations
- `RoomController.java`: Manages room operations
- `AppointmentController.java`: Manages appointment operations
- `MedicalRecordController.java`: Manages medical records
- `AdminController.java`: Handles authentication

### Utils
- `StringUtils.java`: Text formatting utilities
- `StringConstants.java`: Menu text and other constants
- `ColorCodes.java`: Terminal color formatting
- `DateTimeUtils.java`: Date and time utilities

## Requirements

- Java Development Kit (JDK) 11 or higher
- Console environment supporting ANSI color codes