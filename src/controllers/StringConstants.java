package controllers;

public class StringConstants {
    public static final String TAB = "\t\t";
    // Main Menu
    public static final String MAIN_MENU = """
                                           === Main Menu ===
                                           [1] Manage Rooms
                                           [2] Manage Patients
                                           [3] Manage Doctors
                                           [4] Manage Appointments
                                           [5] Medical Records
                                           [6] Search/View Data
                                           [7] Change Password
                                           [0] Exit
                                           """;
    
    // Room Menu
    public static final String ROOM_MENU = """
                                           === Manage Rooms ===
                                           [1] Add Room
                                           [2] Remove Room
                                           [3] Update Room Status
                                           [0] Back to Main Menu
                                           """;
    //Room type menu
    public static final String ROOM_TYPE_MENU = """
                                                === Room Type ===
                                                [1] ICU
                                                [2] Ward
                                                [3] Emergency
                                                [4] Operation
                                                [5] Isolation
                                                [0] Back to "Manage Rooms"
                                                """;
    
    // Patient Menu
    public static final String PATIENT_MENU = """
                                              === Manage Patients ===
                                              [1] Add Patient
                                              [2] Remove Patient
                                              [3] Update Patient Details
                                              [4] View Patients
                                              [0] Back to Main Menu
                                              """;
    
    // Doctor Menu
    public static final String DOCTOR_MENU = """         
                                             === Manage Doctors ===
                                             [1] Add Doctor
                                             [2] Remove Doctor
                                             [3] Update Doctor Details
                                             [4] View Doctors
                                             [0] Back to Main Menu
                                             """;
    
    // Appointment Menu
    public static final String APPOINTMENT_MENU = """
                                                  === Manage Appointments ===
                                                  [1] Add Appointment
                                                  [2] Remove Appointment
                                                  [3] Update Appointment Details
                                                  [4] View Appointments
                                                  [0] Back to Main Menu
                                                  """;

    public static final String MEDICALRECORD_MENU = """
                                                    === Manage Medical Records ===
                                                    [1] Add Medical Record
                                                    [2] Remove Medical Record
                                                    [3] Update Medical Record Details
                                                    [0] Back to Main Menu
                                                    """;
    
    // Search/View Data Menu
    public static final String SEARCH_VIEW_MENU = """
                                                  === Search/View Data ===
                                                  [1] Search Patients
                                                  [2] Search Doctors
                                                  [3] Search Appointments
                                                  [4] Search Rooms
                                                  [0] Back to Main Menu
                                                  """;
    
}