import javax.crypto.KeyGenerator;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;
import java.util.Currency;
import java.util.Scanner;
import yahoofinance.YahooFinance;

//import static java.math.BigDecimal.ONE;
//import yahoofinance.exchanges.*;


public class Inov {

    Currency usa = Currency.getInstance("USD"); //base currency (US only)
    invp pay = new invp(); //payment processor


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

    public static void intro() {
        System.out.println("WELCOME TO INOV!!!!!! We are a SaaS startup that build creative, innovative and simpler apis for more transparent financial services. " +
                "We only operate in the US as of right now but we look to expand.");
    }

    public String create_debit_account(String name, String email, String address) throws NoSuchAlgorithmException {
        //Country
        String base_currency = usa.getCurrencyCode();
        // Read input scan
        Scanner scan = new Scanner(System.in);
        //Creating a KeyGenerator object Cryptography key
        KeyGenerator keyGen = KeyGenerator.getInstance("DES");
        //Creating a SecureRandom object (credit & debit card numbers)
        SecureRandom rand_num = new SecureRandom();
        //Initializing the KeyGenerator
        keyGen.init(rand_num);
        //Creating/Generating a key
        Key key = keyGen.generateKey();
        //create account details
        int card_num = rand_num.nextInt(10000);
        String secure_code = String.valueOf(key);
        String card_code = secure_code.substring(0, 5);
        System.out.println("Enter Amount you want to Deposit into Checking account (minimum deposit $25 ):  ");
        double checking = scan.nextDouble();
        System.out.println("Enter Amount you want to Deposit into Saving account (minimum deposit $25):  ");
        double saving = scan.nextDouble();
        if (checking < 25.00 && saving < 25.00) {
            System.out.println("deposits are less than required amount");
            create_debit_account(name, email, address);
        } else {
            //Insert inputs into database Table (creating account)
            String read_data = "INSERT INTO DEBIT(NAME, EMAIL, CARD-NUM, CARD-CODE, SECURITY, CHECKING, SAVING, ADDRESS, CURRENCY) VALUES " +
                    "(?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (Connection conn = this.connect();
                 PreparedStatement pstmt = conn.prepareStatement(read_data)) {
                pstmt.setString(1, name);
                pstmt.setString(2, email);
                pstmt.setInt(3, card_num);
                pstmt.setString(4, card_code);
                pstmt.setString(5, secure_code);
                pstmt.setDouble(6, checking);
                pstmt.setDouble(7, saving);
                pstmt.setString(8, address);
                pstmt.setString(9, base_currency);
                pstmt.executeUpdate(read_data);
                pstmt.close();
                conn.commit();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return "Debit account created, Welcome to Inov";
    }

    public String create_credit_account(String name, String email, String address) throws NoSuchAlgorithmException {
        //Country
        String base_currency = usa.getCurrencyCode();
        // Read input scan
        Scanner scan = new Scanner(System.in);
        //Creating a KeyGenerator object Cryptography key
        KeyGenerator keyGen = KeyGenerator.getInstance("DES");
        //Creating a SecureRandom object (credit & debit card numbers)
        SecureRandom rand_num = new SecureRandom();
        //Initializing the KeyGenerator
        keyGen.init(rand_num);
        //Creating/Generating a key
        Key key = keyGen.generateKey();
        //create account details
        int card_num = rand_num.nextInt(10000);
        String secure_code = String.valueOf(key);
        String card_code = secure_code.substring(0, 5);
        System.out.println("Enter Amount you want to Deposit into Checking account (minimum deposit $25 ):  ");
        double checking = scan.nextDouble();
        System.out.println("Enter Amount you want to Deposit into Saving account (minimum deposit $25):  ");
        double saving = scan.nextDouble();
        if (checking < 25.00 && saving < 25.00) {
            System.out.println("deposits are less than required amount");
            create_credit_account(name, email, address);
        } else {
            //Insert inputs into database Table (creating account)
            String read_data = "INSERT INTO CREDIT(NAME, EMAIL, CARD-NUM, CARD-CODE, SECURITY, CHECKING, SAVING, ADDRESS, CURRENCY) VALUES " +
                    "(?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (Connection conn = this.connect();
                 PreparedStatement pstmt = conn.prepareStatement(read_data)) {
                pstmt.setString(1, name);
                pstmt.setString(2, email);
                pstmt.setInt(3, card_num);
                pstmt.setString(4, card_code);
                pstmt.setString(5, secure_code);
                pstmt.setDouble(6, checking);
                pstmt.setDouble(7, saving);
                pstmt.setString(8, address);
                pstmt.setString(9, base_currency);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return "Credit account created, Welcome to Inov";
    }

    public void deposit_option(String choice, String cardcode, double deposit) throws SQLException {
        if (choice.equals("1")) {
            deposit_checking(cardcode, deposit);
        } else {
            deposit_saving(cardcode, deposit);
        }
    }

     public String deposit_checking(String cardcode, double deposit) throws SQLException {
        String debit_sql = "SELECT * FROM DEBIT WHERE CARD-CODE=?";
        String credit_sql = "SELECT * FROM CREDIT WHERE CARD-CODE=?";
        Statement state = connect().createStatement();
        // credit account check
        ResultSet retract_credit = state.executeQuery(credit_sql);
        String card_code = retract_credit.getString("CARD-CODE");
        // Debit account check
        ResultSet retract_debit = state.executeQuery(debit_sql);
        // String name = retract_debit.getString("NAME");
        // String email = retract_debit.getString("EMAIL");
        String c_code = retract_debit.getString("CARD-CODE");
        if (c_code.equals(cardcode)) {
            // transaction processing
            String debit_data = "UPDATE DEBIT set CHECKING=CHECKING+? WHERE CARD-CODE=?";
            PreparedStatement stat = connect().prepareStatement(debit_data);
            stat.setDouble(1, deposit);
            stat.setString(2, cardcode);
            stat.executeUpdate();
            retract_debit.close(); // transaction complete
        } else if (card_code.equals(cardcode)) {
            String credit_data = "UPDATE CREDIT set CHECKING=CHECKING+? WHERE CARD-CODE=?";
            // transaction processing
            PreparedStatement stat = connect().prepareStatement(credit_data);
            stat.setDouble(1, deposit);
            stat.setString(2, cardcode);
            retract_credit.close(); // transaction complete
        } else {
            System.out.println("Deposit cannot be made, please try again");
            deposit_checking(cardcode, deposit);
        }
        state.close();
        connect().close();
        return "Deposit successful";
    }

    public String deposit_saving(String cardcode, double deposit) throws SQLException {
        String debit_sql = "SELECT * FROM DEBIT WHERE CARD-CODE=?";
        String credit_sql = "SELECT * FROM CREDIT WHERE CARD-CODE=?";
        Statement state = connect().createStatement();
        // credit account check
        ResultSet retract_credit = state.executeQuery(credit_sql);
        String card_code = retract_credit.getString("CARD-CODE");
        // Debit account check
        ResultSet retract_debit = state.executeQuery(debit_sql);
        // String name = retract_debit.getString("NAME");
        //String email = retract_debit.getString("EMAIL");
        String c_code = retract_debit.getString("CARD-CODE");
        if (c_code.equals(cardcode)) {
            // transaction processing
            String debit_data = "UPDATE DEBIT set SAVING=SAVING+? WHERE CARD-CODE=?";
            PreparedStatement stat = connect().prepareStatement(debit_data);
            stat.setDouble(1, deposit);
            stat.setString(2, cardcode);
            stat.executeUpdate();
            retract_debit.close(); // transaction complete
        } else if (card_code.equals(cardcode)) {
            String credit_data = "UPDATE CREDIT set SAVING=SAVING+? WHERE CARD-CODE=?";
            // transaction processing
            PreparedStatement stat = connect().prepareStatement(credit_data);
            stat.setDouble(1, deposit);
            stat.setString(2, cardcode);
            retract_credit.close(); // transaction complete
        } else {
            System.out.println("Deposit cannot be made, please try again");
            deposit_saving(cardcode, deposit);
        }
        state.close();
        connect().close();
        return "Deposit successful";
    }

    public void atm_option(String choice, String cardcode, double withdraw) throws SQLException {
        if (choice.equals("1")) {
            withdraw_checking(cardcode, withdraw);
        } else {
            withdraw_saving(cardcode, withdraw);
        }
    }

    public String withdraw_checking(String cardcode, double withdraw) throws SQLException {
        String debit_sql = "SELECT * FROM DEBIT WHERE CARD-CODE=?";
        String credit_sql = "SELECT * FROM CREDIT WHERE CARD-CODE=?";
        Statement state = connect().createStatement();
        // credit account check
        ResultSet retract_credit = state.executeQuery(credit_sql);
        String card_code = retract_credit.getString("CARD-CODE");
        // Debit account check
        ResultSet retract_debit = state.executeQuery(debit_sql);
        // String name = retract_debit.getString("NAME");
        // String email = retract_debit.getString("EMAIL");
        String c_code = retract_debit.getString("CARD-CODE");
        if (c_code.equals(cardcode)) {
            // transaction processing
            String debit_data = "UPDATE DEBIT set CHECKING=CHECKING-? WHERE CARD-CODE=?";
            PreparedStatement stat = connect().prepareStatement(debit_data);
            stat.setDouble(1, withdraw);
            stat.setString(2, cardcode);
            stat.executeUpdate();
            retract_debit.close();
        } else if (card_code.equals(cardcode)){
            String credit_data = "UPDATE CREDIT set CHECKING=CHECKING-? WHERE CARD-CODE=?";
            PreparedStatement stat = connect().prepareStatement(credit_data);
            stat.setDouble(1, withdraw);
            stat.setString(2, cardcode);
            retract_credit.close();
        } else {
            System.out.println("withdrawal cannot be processed, feel free to try again");
            withdraw_checking(cardcode, withdraw);
        }
        state.close();
        connect().close();
        return "withdrawal successful";
    }

    public String withdraw_saving(String cardcode, double withdraw) throws SQLException {
        String debit_sql = "SELECT * FROM DEBIT WHERE CARD-CODE=?";
        String credit_sql = "SELECT * FROM CREDIT WHERE CARD-CODE=?";
        Statement state = connect().createStatement();
        // credit account check
        ResultSet retract_credit = state.executeQuery(credit_sql);
        String card_code = retract_credit.getString("CARD-CODE");
        // Debit account check
        ResultSet retract_debit = state.executeQuery(debit_sql);
        // String name = retract_debit.getString("NAME");
        // String email = retract_debit.getString("EMAIL");
        String c_code = retract_debit.getString("CARD-CODE");
        if (c_code.equals(cardcode)) {
            // transaction processing
            String debit_data = "UPDATE DEBIT set SAVING=SAVING-? WHERE CARD-CODE=?";
            PreparedStatement stat = connect().prepareStatement(debit_data);
            stat.setDouble(1, withdraw);
            stat.setString(2, cardcode);
            stat.executeUpdate();
            retract_debit.close();
        } else if (card_code.equals(cardcode)) {
            String credit_data = "UPDATE CREDIT set SAVING=SAVING-? WHERE CARD-CODE=?";
            PreparedStatement stat = connect().prepareStatement(credit_data);
            stat.setDouble(1, withdraw);
            stat.setString(2, cardcode);
            stat.executeUpdate();
            retract_credit.close();
        } else {
            System.out.println("withdrawal cannot be processed, feel free to try again");
            withdraw_saving(cardcode, withdraw);
        }
        state.close();
        connect().close();
        return "withdrawal successful";
    }

    public String send_money(String cardcode, double amount, String recipient) throws SQLException {
        String debit_transfer = "SELECT * FROM DEBIT WHERE CARD-CODE=?";
        String credit_transfer = "SELECT * FROM CREDIT WHERE CARD-CODE=?";
        Statement state = connect().createStatement();
        // collect valued amount to be sent to recipient (credit check)
        ResultSet retract_credit = state.executeQuery(credit_transfer);
        String card_code = retract_credit.getString("CARD-CODE");
        // collect valued amount to be sent to recipient (debit check)
        ResultSet retract_debit = state.executeQuery(debit_transfer);
        String c_code = retract_debit.getString("CARD-CODE");
        if (c_code.equals(cardcode)) {
            String retrieve = "UPDATE DEBIT set CHECKING=CHECKING-? WHERE CARD-CODE=?";
            PreparedStatement stat = connect().prepareStatement(retrieve);
            stat.setDouble(1, amount);
            stat.setString(2, cardcode);
            stat.executeUpdate();
            retract_debit.close();
        } else if (card_code.equals(cardcode)) {
            String retrieve = "UPDATE CREDIT set CHECKING=CHECKING-? WHERE CARD-CODE=?";
            PreparedStatement stat = connect().prepareStatement(retrieve);
            stat.setDouble(1, amount);
            stat.setString(2, cardcode);
            stat.executeUpdate();
            retract_credit.close();
        } else {
            System.out.println("Account cannot be found, feel free to try again");
            send_money(cardcode, amount, recipient);
        }
        // process transaction
        String debit_send = "SELECT * FROM DEBIT WHERE NAME=?";
        String credit_send = "SELECT * FROM CREDIT WHERE NAME=?";
        // collect valued amount to be sent to recipient (credit check)
        ResultSet send_credit = state.executeQuery(credit_send);
        String recipient_name_credit = retract_credit.getString("NAME");
        // collect valued amount to be sent to recipient (debit check)
        ResultSet send_debit = state.executeQuery(debit_send);
        String recipient_name_debit = retract_credit.getString("NAME");
        if (recipient_name_debit.equals(recipient)) {
            String send = "UPDATE DEBIT set CHECKING=CHECKING+? WHERE NAME=?";
            PreparedStatement stat = connect().prepareStatement(send);
            stat.setDouble(1, amount);
            stat.setString(2, recipient);
            stat.executeUpdate();
            send_debit.close();
        } else if (recipient_name_credit.equals(recipient)) {
            String send = "UPDATE CREDIT set CHECKING=CHECKING+? WHERE NAME=?";
            PreparedStatement stat = connect().prepareStatement(send);
            stat.setDouble(1, amount);
            stat.setString(2, recipient);
            stat.executeUpdate();
            send_credit.close();
        } else {
            System.out.println("Transfer cannot be processed, feel free to try again");
            send_money(cardcode, amount, recipient);
        }
        state.close();
        connect().close();
        return "Transaction complete";
    }

    public String process_payments(String CardCode, double price) throws SQLException {
        pay.getCard(CardCode, price);
        return "payment being processed";
    }

    public String Bank_statement(String cardcode) throws SQLException {
        String debit_statement = "SELECT * FROM DEBIT WHERE CARD-CODE=?";
        String credit_statement = "SELECT * FROM CREDIT WHERE CARD-CODE=?";
        Statement state = connect().createStatement();
        // find account for requested bank statement (credit check)
        ResultSet retract_credit = state.executeQuery(credit_statement);
        String card_code = retract_credit.getString("CARD-CODE");
        // find account for requested bank statement (debit check)
        ResultSet retract_debit = state.executeQuery(debit_statement);
        String c_code = retract_debit.getString("CARD-CODE");
        if (c_code.equals(cardcode)) {
            while (retract_debit.next()) {
                String account_name = retract_debit.getString("NAME");
                double checking_balance = retract_debit.getDouble("CHECKING");
                double saving_balance = retract_debit.getDouble("SAVING");
                String currency = retract_debit.getString("CURRENCY");
                String title_debit = "Bank Statement";
                String bank_statement_debit = "Account Holder = " + account_name + "\n" +
                                              "Checking Balance = " + checking_balance + "\n" +
                                              "Savings Balance = " + saving_balance + "\n" +
                                              "Currency = " + currency;
                // Files.File_creator(title_debit, bank_statement_debit)
                retract_debit.close();

            }
        } else if (card_code.equals(cardcode)) {
            while (retract_credit.next()) {
                String account_name = retract_debit.getString("NAME");
                double checking_balance = retract_debit.getDouble("CHECKING");
                double saving_balance = retract_debit.getDouble("SAVING");
                String currency = retract_debit.getString("CURRENCY");
                String title_credit = "Bank Statement";
                String bank_statement_credit = "Account Holder = " + account_name + "\n" +
                        "Checking Balance = " + checking_balance + "\n" +
                        "Savings Balance = " + saving_balance + "\n" +
                        "Currency = " + currency;
                // Files.File_creator(title_credit, bank_statement_credit)
                retract_credit.close();

            }

        } else {
            System.out.println("Bank statement cannot be processed, please try again");
            Bank_statement(cardcode);
        }
        state.close();
        connect().close();
        return "Bank Statement has been processed, please check email";
    }
    public String Currency_exchange(String cardcode, String country) throws SQLException, IOException {
        Currency currency_convert = Currency.getInstance(country);// base currency will be converted to this
        String convert_to = currency_convert.getCurrencyCode();
        String base = usa.getCurrencyCode();
        String concat = base + convert_to;
        BigDecimal convert = YahooFinance.getFx(concat).getPrice();
        BigDecimal num = BigDecimal.valueOf(convert.doubleValue());
        String debit_statement = "SELECT *  FROM DEBIT WHERE CARD-CODE=?";
        String credit_statement = "SELECT * FROM CREDIT WHERE CARD-CODE=?";
        Statement state = connect().createStatement();
        // find account for requested transaction (credit check)
        ResultSet retract_credit = state.executeQuery(credit_statement);
        String card_code = retract_credit.getString("CARD-CODE");
        // find account for requested transaction (debit check)
        ResultSet retract_debit = state.executeQuery(debit_statement);
        String c_code = retract_debit.getString("CARD-CODE");
        if (c_code.equals(cardcode)) {
            String send = "UPDATE DEBIT set CHECKING=CHECKING/?, CURRENCY=?  WHERE CARD-CODE=?";
            PreparedStatement stat = connect().prepareStatement(send);
            stat.setBigDecimal(1, num);
            stat.setString(2, convert_to);
            stat.setString(2, cardcode);
            stat.executeUpdate();
            retract_debit.close();
        } else if (card_code.equals(cardcode)) {
            String send = "UPDATE CREDIT set CHECKING=CHECKING/?, CURRENCY=? WHERE NAME=?";
            PreparedStatement stat = connect().prepareStatement(send);
            stat.setBigDecimal(1, num);
            stat.setString(2, convert_to);
            stat.setString(3, cardcode);
            stat.executeUpdate();
            retract_credit.close();
        } else {
            System.out.println("Request cannot be processed, feel free to try again");
            Currency_exchange(cardcode, country);
        }
        state.close();
        connect().close();


        return "Conversion complete";
    }

    public String International_transactions(String cardcode, String country, BigDecimal amount, String name) throws SQLException, IOException {
        Currency currency_convert = Currency.getInstance(country);// base currency will be converted to this
        String convert_to = currency_convert.getCurrencyCode();
        String base = usa.getCurrencyCode();
        String concat = base + convert_to;
        BigDecimal convert = YahooFinance.getFx(concat).getPrice();
        BigDecimal num = BigDecimal.valueOf(convert.doubleValue());
        BigDecimal exchange = amount.divide(num);
        String debit_statement = "SELECT *  FROM DEBIT WHERE CARD-CODE=?";
        String credit_statement = "SELECT * FROM CREDIT WHERE CARD-CODE=?";
        Statement state = connect().createStatement();
        // find account for requested transaction (credit check)
        ResultSet retract_credit = state.executeQuery(credit_statement);
        String card_code = retract_credit.getString("CARD-CODE");
        // find account for requested transaction (debit check)
        ResultSet retract_debit = state.executeQuery(debit_statement);
        String c_code = retract_debit.getString("CARD-CODE");
        if (c_code.equals(cardcode)) {
            String send = "UPDATE DEBIT set CHECKING=CHECKING-?, CURRENCY=?  WHERE CARD-CODE=?";
            PreparedStatement stat = connect().prepareStatement(send);
            stat.setBigDecimal(1, exchange);
            stat.setString(2, convert_to);
            stat.setString(2, cardcode);
            stat.executeUpdate();
            retract_debit.close();
        } else if (card_code.equals(cardcode)) {
            String send = "UPDATE CREDIT set CHECKING=CHECKING-?, CURRENCY=? WHERE NAME=?";
            PreparedStatement stat = connect().prepareStatement(send);
            stat.setBigDecimal(1, exchange);
            stat.setString(2, convert_to);
            stat.setString(3, cardcode);
            stat.executeUpdate();
            retract_credit.close();
        } else {
            System.out.println("Account cannot be found, feel free to try again");
            International_transactions(cardcode, country, amount, name);
        }

        // Process Transaction
        String debit_send = "SELECT * FROM DEBIT WHERE NAME=?";
        String credit_send = "SELECT * FROM CREDIT WHERE NAME=?";
        // collect valued amount to be sent to recipient (credit check)
        ResultSet send_credit = state.executeQuery(credit_send);
        String recipient_name_credit = retract_credit.getString("NAME");
        // collect valued amount to be sent to recipient (debit check)
        ResultSet send_debit = state.executeQuery(debit_send);
        String recipient_name_debit = retract_credit.getString("NAME");
        if (recipient_name_debit.equals(name)) {
            String send = "UPDATE DEBIT set CHECKING=CHECKING+? WHERE NAME=?";
            PreparedStatement stat = connect().prepareStatement(send);
            stat.setBigDecimal(1, exchange);
            stat.setString(2, name);
            stat.executeUpdate();
            send_debit.close();
        } else if (recipient_name_credit.equals(name)) {
            String send = "UPDATE CREDIT set CHECKING=CHECKING+? WHERE NAME=?";
            PreparedStatement stat = connect().prepareStatement(send);
            stat.setBigDecimal(1, exchange);
            stat.setString(2, name);
            stat.executeUpdate();
            send_credit.close();
        } else {
            System.out.println("Transfer cannot be processed, feel free to try again");
            International_transactions(cardcode, country, amount, name);
        }
        state.close();
        connect().close();
        return "Transaction complete";
    }
    
    public static void main(String[] args) {
        Inov.intro();
    }


}
