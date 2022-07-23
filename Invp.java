import java.sql.*;

public class invp {

    public Connection connect() {
        // Database connection string
        Connection conn = null;
        // Statement state = null;
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost/inovjava", "postgres", "");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    // Debit
    public void getCard_Debit(String CardCode, double price) throws SQLException {
        //String debit_sql = "SELECT * FROM DEBIT WHERE CARD-CODE=?";
        //String credit_sql = "SELECT * FROM CREDIT WHERE CARD-CODE=?";
        Statement state = connect().createStatement();
        // find account for requested bank statement (credit check)
        ResultSet retract_debit = state.executeQuery("SELECT * FROM InovDEBIT WHERE CARD-CODE=?");
        String email = retract_debit.getString("EMAIL");
        String name = retract_debit.getString("NAME");
        retract_debit.close();
        state.close();
        chargeCard_Debit(name, CardCode, price);
    }


    public void chargeCard_Debit(String name, String CardCode, double price) throws SQLException {
        String Vendor = "INVP";

        Statement state = connect().createStatement();;
        // find account for requested bank statement (debit check)
        ResultSet retract_debit = state.executeQuery("SELECT * FROM InovDEBIT WHERE CARDCODE=?, WHERE NAME=?");
        String charge = "UPDATE InovDEBIT set CHECKING=CHECKING-? WHERE CARDCODE=?";
        PreparedStatement stat = connect().prepareStatement(charge);
        stat.setDouble(1, price);
        stat.setString(2, CardCode);
        retract_debit.close();
        String Payment_info = "Account name = " + name + "\n" +
                "Vendor = " + Vendor + "\n" +
                "Amount = " + price + "\n";
        process_payment_Debit(Payment_info);
    }

    public void process_payment_Debit(String message){
        System.out.print(message);
    }


    // Credit
    public void getCard_Credit(String CardCode, double price) throws SQLException {
        //String debit_sql = "SELECT * FROM DEBIT WHERE CARD-CODE=?";
        //String credit_sql = "SELECT * FROM CREDIT WHERE CARD-CODE=?";
        Statement state = connect().createStatement();
        // find account for requested bank statement (credit check)
        ResultSet retract_debit = state.executeQuery("SELECT * FROM CREDITInov WHERE CARD-CODE=?");
        String email = retract_debit.getString("EMAIL");
        String name = retract_debit.getString("NAME");
        retract_debit.close();
        state.close();
        chargeCard_Credit(name, CardCode, price);
    }


    public void chargeCard_Credit(String name, String CardCode, double price) throws SQLException {
        String Vendor = "INVP";

        Statement state = connect().createStatement();;
        // find account for requested bank statement (debit check)
        ResultSet retract_debit = state.executeQuery("SELECT * FROM CREDITInov WHERE CARDCODE=?, WHERE NAME=?");
        String charge = "UPDATE CREDITInov set CHECKING=CHECKING-? WHERE CARDCODE=?";
        PreparedStatement stat = connect().prepareStatement(charge);
        stat.setDouble(1, price);
        stat.setString(2, CardCode);
        retract_debit.close();
        String Payment_info = "Account name = " + name + "\n" +
                "Vendor = " + Vendor + "\n" +
                "Amount = " + price + "\n";
        process_payment_Credit(Payment_info);
    }

    public void process_payment_Credit(String message){
        System.out.print(message);
    }
}



