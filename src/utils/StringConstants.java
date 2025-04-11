package utils;

public class StringConstants {
    public static final String TAB = "\t\t";

    public static final String LOGO = """
              \u001B[37m.-:/+oossssoo+/:-.\u001B[0m              
         \u001B[37m`:+ssssssssssssssssssssss+:`\u001B[0m         
       \u001B[37m-+ssssssssssssssssssssssssssss+-\u001B[0m       
     \u001B[37m.osssssssssssssssssssssssssssssssso.\u001B[0m     
    \u001B[37m/ssssssssssssssssssssssssssssssssssss/\u001B[0m    
   \u001B[37m+sssssssssss\u001B[31m      ++++      \u001B[37mssssssssssss+\u001B[0m   
  \u001B[37m/ssssssssssss\u001B[31m      ++++      \u001B[37mssssssssssss/\u001B[0m  
 \u001B[37m.sssssssssssss\u001B[31m     ++++++     \u001B[37mssssssssssssss.\u001B[0m 
 \u001B[37m+sssssssssssss\u001B[31m     ++++++     \u001B[37mssssssssssssss+\u001B[0m 
 \u001B[37mosssssssssssss\u001B[31m                \u001B[37msssssssssssssss\u001B[0m 
 \u001B[37mosssssssssssss\u001B[31m     ++++++     \u001B[37msssssssssssssss\u001B[0m 
 \u001B[37m+sssssssssssss\u001B[31m     ++++++     \u001B[37mssssssssssssss+\u001B[0m 
 \u001B[37m.sssssssssssss\u001B[31m     ++++++     \u001B[37mssssssssssssss.\u001B[0m 
  \u001B[37m/ssssssssssss\u001B[31m      ++++      \u001B[37mssssssssssss/\u001B[0m  
   \u001B[37m+sssssssssss\u001B[31m      ++++      \u001B[37mssssssssssss+\u001B[0m   
    \u001B[37m/ssssssssssssssssssssssssssssssssssss/\u001B[0m    
     \u001B[37m.osssssssssssssssssssssssssssssssso.\u001B[0m     
       \u001B[37m-+ssssssssssssssssssssssssssss+-\u001B[0m       
         \u001B[37m`:+ssssssssssssssssssssss+:`\u001B[0m         
              \u001B[37m.-:/+oossssoo+/:-.\u001B[0m               

            """;
    public static final String LOGO_2 = """
                \u001B[37m.-:/+oossssoo+/:-.\u001B[0m              
           \u001B[37m`:+ssssssssssssssssssssss+:`\u001B[0m         
         \u001B[37m-+ssssssssssssssssssssssssssss+-\u001B[0m       
       \u001B[37m.osssssssssssssssssssssssssssssssso.\u001B[0m     
      \u001B[37m/ssssssssssssssssssssssssssssssssssss/\u001B[0m    
     \u001B[37m+sssssssssss\u001B[32m      ++++      \u001B[37mssssssssssss+\u001B[0m   
    \u001B[37m/ssssssssssss\u001B[32m      ++++      \u001B[37mssssssssssss/\u001B[0m  
   \u001B[37m.sssssssssssss\u001B[32m     ++++++     \u001B[37mssssssssssssss.\u001B[0m 
   \u001B[37m+sssssssssssss\u001B[32m     ++++++     \u001B[37mssssssssssssss+\u001B[0m 
   \u001B[37mosssssssssssss\u001B[32m                \u001B[37msssssssssssssss\u001B[0m 
   \u001B[37mosssssssssssss\u001B[32m     ++++++     \u001B[37msssssssssssssss\u001B[0m 
   \u001B[37m+sssssssssssss\u001B[32m     ++++++     \u001B[37mssssssssssssss+\u001B[0m 
   \u001B[37m.sssssssssssss\u001B[32m     ++++++     \u001B[37mssssssssssssss.\u001B[0m 
    \u001B[37m/ssssssssssss\u001B[32m      ++++      \u001B[37mssssssssssss/\u001B[0m  
     \u001B[37m+sssssssssss\u001B[32m      ++++      \u001B[37mssssssssssss+\u001B[0m   
      \u001B[37m/ssssssssssssssssssssssssssssssssssss/\u001B[0m    
       \u001B[37m.osssssssssssssssssssssssssssssssso.\u001B[0m     
         \u001B[37m-+ssssssssssssssssssssssssssss+-\u001B[0m       
           \u001B[37m`:+ssssssssssssssssssssss+:`\u001B[0m         
                \u001B[37m.-:/+oossssoo+/:-.\u001B[0m               
              """;

    // Main Menu
    public static final String MAIN_MENU = LOGO_2 + """

    
                                           ╔════════════════════ Main Menu ════════════════════╗
                                           ║ Option ║ Description                              ║
                                           ╠════════╬══════════════════════════════════════════╣
                                           ║ \u001B[33m  1   \u001B[0m ║ Manage Rooms                             ║
                                           ╠════════╬══════════════════════════════════════════╣
                                           ║ \u001B[33m  2   \u001B[0m ║ Manage Patients                          ║
                                           ╠════════╬══════════════════════════════════════════╣
                                           ║ \u001B[33m  3   \u001B[0m ║ Manage Doctors                           ║
                                           ╠════════╬══════════════════════════════════════════╣
                                           ║ \u001B[33m  4   \u001B[0m ║ Manage Appointments                      ║
                                           ╠════════╬══════════════════════════════════════════╣
                                           ║ \u001B[33m  5   \u001B[0m ║ Medical Records                          ║
                                           ╠════════╬══════════════════════════════════════════╣
                                           ║ \u001B[33m  6   \u001B[0m ║ Search/View Data                         ║
                                           ╠════════╬══════════════════════════════════════════╣
                                           ║ \u001B[33m  7   \u001B[0m ║ Analytic Dashboard                       ║
                                           ╠════════╬══════════════════════════════════════════╣
                                           ║ \u001B[33m  8   \u001B[0m ║ Change Password                          ║
                                           ╠════════╬══════════════════════════════════════════╣
                                           ║ \u001B[33m  0   \u001B[0m ║ Exit                                     ║
                                           ╚════════╩══════════════════════════════════════════╝
                                           """;
    
    // Room Menu
    public static final String ROOM_MENU = """
                                           ╔════════════════════ Manage Rooms ═══════════════════╗
                                           ║ Option ║ Description                                ║
                                           ╠════════╬════════════════════════════════════════════╣
                                           ║ \u001B[33m  1   \u001B[0m ║ Add Room                                   ║
                                           ╠════════╬════════════════════════════════════════════╣
                                           ║ \u001B[33m  2   \u001B[0m ║ Remove Room                                ║
                                           ╠════════╬════════════════════════════════════════════╣
                                           ║ \u001B[33m  3   \u001B[0m ║ Update Room Status                         ║
                                           ╠════════╬════════════════════════════════════════════╣
                                           ║ \u001B[33m  0   \u001B[0m ║ Back to Main Menu                          ║
                                           ╚════════╩════════════════════════════════════════════╝
                                           """;

    // Room Type Menu
    public static final String ROOM_TYPE_MENU = """
                         ╔════════════════════ Room Type ══════════════════════╗
                         ║ Option ║ Description                                ║
                         ╠════════╬════════════════════════════════════════════╣
                         ║ \u001B[34m  1   \u001B[0m ║ ICU                                        ║
                         ╠════════╬════════════════════════════════════════════╣
                         ║ \u001B[35m  2   \u001B[0m ║ Ward                                       ║
                         ╠════════╬════════════════════════════════════════════╣
                         ║ \u001B[31m  3   \u001B[0m ║ Emergency                                  ║
                         ╠════════╬════════════════════════════════════════════╣
                         ║ \u001B[32m  4   \u001B[0m ║ Operation                                  ║
                         ╠════════╬════════════════════════════════════════════╣
                         ║ \u001B[36m  5   \u001B[0m ║ Isolation                                  ║
                         ╠════════╬════════════════════════════════════════════╣
                         ║ \u001B[37m  0   \u001B[0m ║ Back to "Manage Rooms"                     ║
                         ╚════════╩════════════════════════════════════════════╝
                         """;

    // Patient Menu
    public static final String PATIENT_MENU = """
                                           ╔═══════════════════ Manage Patients ═════════════════╗
                                           ║ Option ║ Description                                ║
                                           ╠════════╬════════════════════════════════════════════╣
                                           ║ \u001B[33m  1   \u001B[0m ║ Add Patient                                ║
                                           ╠════════╬════════════════════════════════════════════╣
                                           ║ \u001B[33m  2   \u001B[0m ║ Remove Patient                             ║
                                           ╠════════╬════════════════════════════════════════════╣
                                           ║ \u001B[33m  3   \u001B[0m ║ Update Patient Details                     ║
                                           ╠════════╬════════════════════════════════════════════╣
                                           ║ \u001B[33m  4   \u001B[0m ║ View Patients                              ║
                                           ╠════════╬════════════════════════════════════════════╣
                                           ║ \u001B[33m  0   \u001B[0m ║ Back to Main Menu                          ║
                                           ╚════════╩════════════════════════════════════════════╝
                                           """;

    // Sample Data Format for Adding a Patient
    public static final String PATIENT_SAMPLE_DATA = """
                         ╔════════════════════════════════════════════════════════════════════╗
                         ║ Example Input Format for Adding a Patient                          ║
                         ╠═══════════════╬════════════════════════════════════════════════════╣
                         ║ Field         ║ Example Value                                      ║
                         ╠═══════════════╬════════════════════════════════════════════════════╣
                         ║ Name          ║ John Doe                                           ║
                         ╠═══════════════╬════════════════════════════════════════════════════╣
                         ║ Age           ║ 30                                                 ║
                         ╠═══════════════╬════════════════════════════════════════════════════╣
                         ║ Gender        ║ M (Male) or F (Female)                             ║
                         ╠═══════════════╬════════════════════════════════════════════════════╣
                         ║ Address       ║ 123-Main-St-City                                   ║
                         ╠═══════════════╬════════════════════════════════════════════════════╣
                         ║ Phone Number  ║ 1234567890                                         ║
                         ╚═══════════════╩════════════════════════════════════════════════════╝
                         """;

    public static final String DOCTOR_SAMPLE_DATA = """
               ╔════════════════════════════════════════════════════════════════════════════════════╗
               ║ Example Input Format for Adding a Doctor                                           ║
               ╠══════════════════════════╬═════════════════════════════════════════════════════════╣
               ║ Field                    ║ Example Value                                           ║
               ╠══════════════════════════╬═════════════════════════════════════════════════════════╣
               ║ Name                     ║ Dr. John Doe                                            ║
               ╠══════════════════════════╬═════════════════════════════════════════════════════════╣
               ║ Age                      ║ 35                                                      ║
               ╠══════════════════════════╬═════════════════════════════════════════════════════════╣
               ║ Gender                   ║ M (Male) or F (Female)                                  ║
               ╠══════════════════════════╬═════════════════════════════════════════════════════════╣
               ║ Address                  ║ 123 Main St, City                                       ║
               ╠══════════════════════════╬═════════════════════════════════════════════════════════╣
               ║ Phone Number             ║ 1234567890                                              ║
               ╠══════════════════════════╬═════════════════════════════════════════════════════════╣
               ║ Department               ║ Cardiology                                              ║
               ╠══════════════════════════╬═════════════════════════════════════════════════════════╣
               ║ Shift                    ║ Morning                                                 ║
               ╠══════════════════════════╬═════════════════════════════════════════════════════════╣
               ║ Years of Experience      ║ 10                                                      ║
               ╠══════════════════════════╬═════════════════════════════════════════════════════════╣
               ║ Specialization           ║ Surgeon                                                 ║
               ╚══════════════════════════╩═════════════════════════════════════════════════════════╝
               """;

    public static final String APPOINTMENT_SAMPLE_DATA = """
                         ╔════════════════════════════════════════════════════════════════════╗
                         ║ Example Input Format for Adding an Appointment                     ║
                         ╠════════════════════════════════════════════════════════════════════╣
                         ║ Field             ║ Example Value                                  ║
                         ╠═══════════════════╬════════════════════════════════════════════════╣
                         ║ Patient ID        ║ 12345                                          ║
                         ╠═══════════════════╬════════════════════════════════════════════════╣
                         ║ Doctor ID         ║ 67890                                          ║
                         ╠═══════════════════╬════════════════════════════════════════════════╣
                         ║ Date (YYYY-MM-DD) ║ 2024-01-01                                     ║
                         ╠═══════════════════╬════════════════════════════════════════════════╣
                         ║ Time (HH:MM)      ║ 10:00                                          ║
                         ╚═══════════════════╩════════════════════════════════════════════════╝
                         """;

    public static final String ROOM_SAMPLE_DATA = """
                         ╔════════════════════════════════════════════════════════════════════╗
                         ║ Example Input Format for Adding a Room                             ║
                         ╠════════════════════════════════════════════════════════════════════╣
                         ║ Field             ║ Example Value                                  ║
                         ╠═══════════════════╬════════════════════════════════════════════════╣
                         ║ Room Number       ║ 101                                            ║
                         ╠═══════════════════╬════════════════════════════════════════════════╣
                         ║ Room Type         ║ ICU, Ward, Emergency, Operation, Isolation     ║
                         ╠═══════════════════╬════════════════════════════════════════════════╣
                         ║ Availability      ║ true or false                                  ║
                         ╚═══════════════════╩════════════════════════════════════════════════╝
                         """;

    // Doctor Menu
    public static final String DOCTOR_MENU = """         
                                           ╔═══════════════════ Manage Doctors ══════════════════╗
                                           ║ Option ║ Description                                ║
                                           ╠════════╬════════════════════════════════════════════╣
                                           ║ \u001B[33m  1   \u001B[0m ║ Add Doctor                                 ║
                                           ╠════════╬════════════════════════════════════════════╣
                                           ║ \u001B[33m  2   \u001B[0m ║ Remove Doctor                              ║
                                           ╠════════╬════════════════════════════════════════════╣
                                           ║ \u001B[33m  3   \u001B[0m ║ Update Doctor Details                      ║
                                           ╠════════╬════════════════════════════════════════════╣
                                           ║ \u001B[33m  4   \u001B[0m ║ View Doctors                               ║
                                           ╠════════╬════════════════════════════════════════════╣
                                           ║ \u001B[33m  0   \u001B[0m ║ Back to Main Menu                          ║
                                           ╚════════╩════════════════════════════════════════════╝
                                           """;

    // Appointment Menu
    public static final String APPOINTMENT_MENU = """
                                           ╔════════════════ Manage Appointments ════════════════╗
                                           ║ Option ║ Description                                ║
                                           ╠════════╬════════════════════════════════════════════╣
                                           ║ \u001B[33m  1   \u001B[0m ║ Add Appointment                            ║
                                           ╠════════╬════════════════════════════════════════════╣
                                           ║ \u001B[33m  2   \u001B[0m ║ Remove Appointment                         ║
                                           ╠════════╬════════════════════════════════════════════╣
                                           ║ \u001B[33m  3   \u001B[0m ║ Update Appointment Details                 ║
                                           ╠════════╬════════════════════════════════════════════╣
                                           ║ \u001B[33m  4   \u001B[0m ║ View Appointments                          ║
                                           ╠════════╬════════════════════════════════════════════╣
                                           ║ \u001B[33m  0   \u001B[0m ║ Back to Main Menu                          ║
                                           ╚════════╩════════════════════════════════════════════╝
                                           """;

    public static final String MEDICALRECORD_MENU = """
                         ╔═══════════════ Manage Medical Records ══════════════╗
                         ║ Option ║ Description                                ║
                         ╠════════╬════════════════════════════════════════════╣
                         ║ \u001B[33m  1   \u001B[0m ║ Add Medical Record                         ║
                         ╠════════╬════════════════════════════════════════════╣
                         ║ \u001B[33m  2   \u001B[0m ║ Remove Medical Record                      ║
                         ╠════════╬════════════════════════════════════════════╣
                         ║ \u001B[33m  3   \u001B[0m ║ Update Medical Record Details              ║
                         ╠════════╬════════════════════════════════════════════╣
                         ║ \u001B[33m  4   \u001B[0m ║ View Medical Records                       ║
                         ╠════════╬════════════════════════════════════════════╣
                         ║ \u001B[33m  0   \u001B[0m ║ Back to Main Menu                          ║
                         ╚════════╩════════════════════════════════════════════╝
                         """;

    // Search/View Data Menu
    public static final String SEARCH_VIEW_MENU = """
                                           ╔═══════════════════ Search/View Data ════════════════╗
                                           ║ Option ║ Description                                ║
                                           ╠════════╬════════════════════════════════════════════╣
                                           ║ \u001B[33m  1   \u001B[0m ║ Search Patients                            ║
                                           ╠════════╬════════════════════════════════════════════╣
                                           ║ \u001B[33m  2   \u001B[0m ║ Search Doctors                             ║
                                           ╠════════╬════════════════════════════════════════════╣
                                           ║ \u001B[33m  3   \u001B[0m ║ Search Appointments                        ║
                                           ╠════════╬════════════════════════════════════════════╣
                                           ║ \u001B[33m  4   \u001B[0m ║ Search Rooms                               ║
                                           ╠════════╬════════════════════════════════════════════╣
                                           ║ \u001B[33m  0   \u001B[0m ║ Back to Main Menu                          ║
                                           ╚════════╩════════════════════════════════════════════╝
                                           """;
    
}