import java.sql.*;

public class BDMS {

    inov go = new inov();

    public static Connection connect() {
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
    public static void debit_accounts() throws SQLException {
        // String debit = "SELECT NAME, CARDNUM, CARDCODE, CHECKING, SAVING, ADDRESS, CURRENCY FROM DEBITInov";
        Statement state = connect().createStatement();
        ResultSet retract_debit = state.executeQuery("SELECT NAME, CARDNUM, CARDCODE, CHECKING, SAVING, ADDRESS, CURRENCY FROM InovDEBIT");
        while (retract_debit.next()) {
            String account_name = retract_debit.getString("NAME");
            int card_num = retract_debit.getInt("CARDNUM");
            String card_code = retract_debit.getString("CARDCODE");
            double checking_balance = retract_debit.getDouble("CHECKING");
            double saving_balance = retract_debit.getDouble("SAVING");
            String address = retract_debit.getString("ADDRESS");
            String currency = retract_debit.getString("CURRENCY");
            System.out.println("Account Holder = " + account_name);
            System.out.println("Card Numbers =  " + card_num);
            System.out.println("Card Codes = " + card_code);
            System.out.println("Checking Balances = " + checking_balance);
            System.out.println("Savings Balances = " + saving_balance);
            System.out.println("Addresses = " + address);
            System.out.println("Currency = " + currency);
        }
    }

    public static void credit_accounts() throws SQLException {
        // String debit = "SELECT NAME, CARDNUM, CARDCODE, CHECKING, SAVING, ADDRESS, CURRENCY FROM DEBITInov";
        Statement state = connect().createStatement();
        ResultSet retract_credit = state.executeQuery("SELECT NAME, CARDNUM, CARDCODE, CHECKING, SAVING, ADDRESS, CURRENCY FROM InovCREDIT");
        while (retract_credit.next()) {
            String account_name = retract_credit.getString("NAME");
            int card_num = retract_credit.getInt("CARDNUM");
            String card_code = retract_credit.getString("CARDCODE");
            double checking_balance = retract_credit.getDouble("CHECKING");
            double saving_balance = retract_credit.getDouble("SAVING");
            String address = retract_credit.getString("ADDRESS");
            String currency = retract_credit.getString("CURRENCY");
            System.out.println("Account Holder = " + account_name);
            System.out.println("Card Numbers =  " + card_num);
            System.out.println("Card Codes = " + card_code);
            System.out.println("Checking Balances = " + checking_balance);
            System.out.println("Savings Balances = " + saving_balance);
            System.out.println("Addresses = " + address);
            System.out.println("Currency = " + currency);
            }
        }

    //International Debit Account systems
    public static void International_debit_accounts() throws SQLException {
        // String debit = "SELECT NAME, CARDNUM, CARDCODE, CHECKING, SAVING, ADDRESS, CURRENCY FROM DEBITInov";
        Statement state = connect().createStatement();
        ResultSet retract_debit = state.executeQuery("SELECT NAME, CARDNUM, CARDCODE, CHECKING, SAVING, ADDRESS, CURRENCY FROM InovInterDEBIT");
        while (retract_debit.next()) {
            String account_name = retract_debit.getString("NAME");
            int card_num = retract_debit.getInt("CARDNUM");
            String card_code = retract_debit.getString("CARDCODE");
            double checking_balance = retract_debit.getDouble("CHECKING");
            double saving_balance = retract_debit.getDouble("SAVING");
            String address = retract_debit.getString("ADDRESS");
            String currency = retract_debit.getString("CURRENCY");
            System.out.println("Account Holder = " + account_name);
            System.out.println("Card Numbers =  " + card_num);
            System.out.println("Card Codes = " + card_code);
            System.out.println("Checking Balances = " + checking_balance);
            System.out.println("Savings Balances = " + saving_balance);
            System.out.println("Addresses = " + address);
            System.out.println("Currency = " + currency);
        }
    }
        public static void main(String[] args) throws SQLException, ClassNotFoundException {
            credit_accounts();
            International_debit_accounts();
            debit_accounts();
    }
}




