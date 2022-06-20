import java.sql.*;

public class invp {

    public Connection connect() {
        // Database connection string
        String url = "jdbc:sqlite:inovbankdata.db";
        Connection conn = null;
        // Statement state = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public String getCard(String CardCode, double price) throws SQLException {
        String debit_sql = "SELECT * FROM DEBIT WHERE CARD-CODE=?";
        String credit_sql = "SELECT * FROM CREDIT WHERE CARD-CODE=?";
        Statement state = connect().createStatement();
        // find account for requested bank statement (credit check)
        ResultSet retract_credit = state.executeQuery(credit_sql);
        String card_code = retract_credit.getString("CARD-CODE");
        // find account for requested bank statement (debit check)
        ResultSet retract_debit = state.executeQuery(debit_sql);
        String c_code = retract_debit.getString("CARD-CODE");
        if (c_code.equals(CardCode)) {
             String name = retract_debit.getString("NAME");
             retract_debit.close();
             state.close();
             connect().close();
             chargeCard(name, CardCode, price);

        } else if (card_code.equals(CardCode)) {
            String name = retract_credit.getString("NAME");
            chargeCard(name, CardCode, price);
            retract_credit.close();
            state.close();
            connect().close();
        } else {
            System.out.println("card cannot be processed, feel free to try again");
            getCard(CardCode, price);
        }
        state.close();
        connect().close();
        return "card being processed";
    }

    public String chargeCard(String name, String CardCode, double price) throws SQLException {
        String Vendor = "INVP";
        String debit_sql = "SELECT * FROM DEBIT WHERE CARD-CODE=?, WHERE NAME=?";
        String credit_sql = "SELECT * FROM CREDIT WHERE CARD-CODE=?, WHERE NAME=?";
        Statement state = connect().createStatement();
        // find account for requested bank statement (credit check)
        ResultSet retract_credit = state.executeQuery(credit_sql);
        String card_code = retract_credit.getString("CARD-CODE");
        // find account for requested bank statement (debit check)
        ResultSet retract_debit = state.executeQuery(debit_sql);
        String c_code = retract_debit.getString("CARD-CODE");
        if (c_code.equals(CardCode)) {
            String charge = "UPDATE DEBIT set CHECKING=CHECKING-? WHERE CARD-CODE=?";
            PreparedStatement stat = connect().prepareStatement(charge);
            stat.setDouble(1, price);
            stat.setString(2, CardCode);
            retract_debit.close();
        } else if (card_code.equals(CardCode)) {
            String charge = "UPDATE CREDIT set CHECKING=CHECKING-? WHERE CARD-CODE=?";
            PreparedStatement stat = connect().prepareStatement(charge);
            stat.setDouble(1, price);
            stat.setString(2, CardCode);
            retract_credit.close();
        } else {
            System.out.println("payment cannot be processed, feel free to try again");
            chargeCard(name, CardCode, price);
        }
        String Payment_info = "Account name = " + name + "\n" +
                              "Vendor = " + Vendor + "\n" +
                              "Amount = " + price + "\n";
        return "payment processed";
    }
}
