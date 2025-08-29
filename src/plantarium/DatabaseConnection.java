import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Δηλώνουμε το URL της βάσης δεδομένων, το όνομα χρήστη και τον κωδικό πρόσβασης.
    private static final String URL = "jdbc:mysql://localhost:3306/plantarium";
    private static final String USER = "admin"; 
    private static final String PASSWORD = "1234"; 

    // Μέθοδος για σύνδεση με τη βάση δεδομένων.
    public static Connection getConnection() {
       
        Connection connection = null;
        try {
             // Δημιουργία σύνδεσης με βάση το URL, το όνομα χρήστη και τον κωδικό πρόσβασης.
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            // Εμφάνιση μηνύματος λάθους αν δεν μπορεί να γίνει σύνδεση με τη βάση δεδομένων.
            e.printStackTrace();
        }
        return connection;
    }
}

