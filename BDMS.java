// Database System
import java.sql.*;

public class BDMS {

    Inov go = new Inov();

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

    //delete account
    public String delete_account(String Cardcode) throws SQLException {
        String debit_sql = "SELECT * FROM DEBIT WHERE CARD-CODE=?";
        String credit_sql = "SELECT * FROM CREDIT WHERE CARD-CODE=?";
        Statement state = connect().createStatement();
        // credit account check
        ResultSet retract_credit = state.executeQuery(credit_sql);
        String card_code = retract_credit.getString("CARD-CODE");
        // Debit account check
        ResultSet retract_debit = state.executeQuery(debit_sql);
        String c_code = retract_debit.getString("CARD-CODE");
        if (c_code.equals(Cardcode)) {
            // deleting account
            String debit_data = "DELETE FROM DEBIT WHERE CARD-CODE=?";
            PreparedStatement stat = connect().prepareStatement(debit_data);
            stat.setString(1, Cardcode);
            stat.executeUpdate();
            retract_debit.close();// account deleted
        } else if (card_code.equals(Cardcode)) {
            // deleting account
            String credit_data = "DELETE FROM CREDIT WHERE CARD-CODE=?";
            PreparedStatement stat = connect().prepareStatement(credit_data);
            stat.setString(1, Cardcode);
            stat.executeUpdate();
            retract_debit.close();// account deleted
        } else {
            System.out.println("Account not found");
        }
        state.close();
        connect().close();
        return "account deleted";
    }

    //Debit Account systems
    public String debit_accounts() throws SQLException {
        String debit = "SELECT NAME, CARD-NUM, CARD-CODE, CHECKING, SAVING, ADDRESS, CURRENCY FROM DEBIT";
        Statement state = connect().createStatement();
        ResultSet retract_debit = state.executeQuery(debit);
        String debit_account_info = null;
        while (retract_debit.next()) {
            String account_name = retract_debit.getString("NAME");
            String card_num = retract_debit.getString("CARD-NUM");
            String card_code = retract_debit.getString("CARD-CODE");
            double checking_balance = retract_debit.getDouble("CHECKING");
            double saving_balance = retract_debit.getDouble("SAVING");
            String address = retract_debit.getString("ADDRESS");
            String currency = retract_debit.getString("CURRENCY");
            debit_account_info = "Account Holder = " + account_name + "\n" +
                                 "Card Numbers = " + card_num + "\n" +
                                 "Card Codes = " + card_code + "\n" +
                                 "Checking Balances = " + checking_balance + "\n" +
                                 "Savings Balances = " + saving_balance + "\n" +
                                 "Addresses = " + address + "\n" +
                                 "Currency = " + currency;
            retract_debit.close();
        }
        return debit_account_info;
    }

    //Credit Account systems
    public String credit_accounts() throws SQLException {
        String debit = "SELECT NAME, CARD-NUM, CARD-CODE, CHECKING, SAVING, ADDRESS, CURRENCY FROM CREDIT";
        Statement state = connect().createStatement();
        ResultSet retract_credit = state.executeQuery(debit);
        String credit_account_info = null;
        while (retract_credit.next()) {
            String account_name = retract_credit.getString("NAME");
            String card_num = retract_credit.getString("CARD-NUM");
            String card_code = retract_credit.getString("CARD-CODE");
            double checking_balance = retract_credit.getDouble("CHECKING");
            double saving_balance = retract_credit.getDouble("SAVING");
            String address = retract_credit.getString("ADDRESS");
            String currency = retract_credit.getString("CURRENCY");
            credit_account_info = "Account Holder = " + account_name + "\n" +
                                  "Card Numbers = " + card_num + "\n" +
                                  "Card Codes = " + card_code + "\n" +
                                  "Checking Balances = " + checking_balance + "\n" +
                                  "Savings Balances = " + saving_balance + "\n" +
                                  "Addresses = " + address + "\n" +
                                  "Currency = " + currency;
            retract_credit.close();
        }
        return credit_account_info;
    }
}
