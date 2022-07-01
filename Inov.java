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
import yahoofinance.quotes.fx.FxSymbols;

import java.math.*;




public class inov {
    Currency usa = Currency.getInstance("USD"); //base currency (US only)

    invp pay = new invp();

    //BDMS cur = new BDMS();
    public Connection connect() {
        // Database connection string
        String url = "jdbc:sqlite:inov.db";
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

    public void create_debit_account(String name, String email, String address) throws NoSuchAlgorithmException {
        //Country
        Currency usa = Currency.getInstance("USD");
        String curr_code = usa.getCurrencyCode();
        // Read input scan
        Scanner scan = new Scanner(System.in);
        //Creating a KeyGenerator object Cryptography key
        KeyGenerator keyGen = KeyGenerator.getInstance("DES");

        //Creating a SecureRandom object
        SecureRandom secRandom = new SecureRandom();

        //Initializing the KeyGenerator
        keyGen.init(secRandom);

        //Creating/Generating a key
        Key key = keyGen.generateKey();
        String secure_code = key.toString();
        String get_code = secure_code.substring(28);
        // System.out.println(get_code);
        //create account details
        int card_num = secRandom.nextInt(11111);
        System.out.println("Enter Amount you want to Deposit into Checking account (minimum deposit $25 ):  ");
        double checking = scan.nextDouble();
        System.out.println("Enter Amount you want to Deposit into Saving account (minimum deposit $25):  ");
        double saving = scan.nextDouble();
        if (checking < 25.00 && saving < 25.00) {
            System.out.println("deposits are less than required amount");
            create_debit_account(name, email, address);
        } else {
            //Insert inputs into database Table (creating account)
            String read_data = "INSERT INTO DEBITInov(NAME, EMAIL, CARDNUM, CARDCODE, CHECKING, SAVING, ADDRESS, CURRENCY) VALUES " +
                    "(?, ?, ?, ?, ?, ?, ?, ?)";

            try (Connection conn = this.connect();
                 PreparedStatement pstmt = conn.prepareStatement(read_data)) {
                pstmt.setString(1, name);
                pstmt.setString(2, email);
                pstmt.setInt(3, card_num);
                pstmt.setString(4, get_code);
                pstmt.setDouble(5, checking);
                pstmt.setDouble(6, saving);
                pstmt.setString(7, address);
                pstmt.setString(8, curr_code);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        // mail notification
        System.out.println("Debit Account created");

    }

    public void create_International_debit_account(String name, String email, String address, String Country) throws NoSuchAlgorithmException {
        //Country
        Currency cou = Currency.getInstance(Country);
        String curr_code = cou.getCurrencyCode();
        // Read input scan
        Scanner scan = new Scanner(System.in);
        //Creating a KeyGenerator object Cryptography key
        KeyGenerator keyGen = KeyGenerator.getInstance("DES");

        //Creating a SecureRandom object
        SecureRandom secRandom = new SecureRandom();

        //Initializing the KeyGenerator
        keyGen.init(secRandom);

        //Creating/Generating a key
        Key key = keyGen.generateKey();
        String secure_code = key.toString();
        String get_code = secure_code.substring(28);
        // System.out.println(get_code);
        //create account details
        int card_num = secRandom.nextInt(11111);
        System.out.println("Enter Amount you want to Deposit into Checking account (minimum deposit $25 ):  ");
        double checking = scan.nextDouble();
        System.out.println("Enter Amount you want to Deposit into Saving account (minimum deposit $25):  ");
        double saving = scan.nextDouble();
        if (checking < 25.00 && saving < 25.00) {
            System.out.println("deposits are less than required amount");
            create_debit_account(name, email, address);
        } else {
            //Insert inputs into database Table (creating account)
            String read_data = "INSERT INTO InterDEBITInov(NAME, EMAIL, CARDNUM, CARDCODE, CHECKING, SAVING, ADDRESS, CURRENCY) VALUES " +
                    "(?, ?, ?, ?, ?, ?, ?, ?)";

            try (Connection conn = this.connect();
                 PreparedStatement pstmt = conn.prepareStatement(read_data)) {
                pstmt.setString(1, name);
                pstmt.setString(2, email);
                pstmt.setInt(3, card_num);
                pstmt.setString(4, get_code);
                pstmt.setDouble(5, checking);
                pstmt.setDouble(6, saving);
                pstmt.setString(7, address);
                pstmt.setString(8, curr_code);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        // mail notification
        System.out.println("International Debit Account created");

    }

    public void create_credit_account(String name, String email, String address) throws NoSuchAlgorithmException {
        //Country
        Currency usa = Currency.getInstance("USD");
        String curr_code = usa.getCurrencyCode();
        // Read input scan
        Scanner scan = new Scanner(System.in);
        //Creating a KeyGenerator object Cryptography key
        KeyGenerator keyGen = KeyGenerator.getInstance("DES");

        //Creating a SecureRandom object
        SecureRandom secRandom = new SecureRandom();

        //Initializing the KeyGenerator
        keyGen.init(secRandom);

        //Creating/Generating a key
        Key key = keyGen.generateKey();
        String secure_code = key.toString();
        String get_code = secure_code.substring(28);
        // System.out.println(get_code);
        //create account details
        int card_num = secRandom.nextInt(11111);
        System.out.println("Enter Amount you want to Deposit into Checking account (minimum deposit $25 ):  ");
        double checking = scan.nextDouble();
        System.out.println("Enter Amount you want to Deposit into Saving account (minimum deposit $25):  ");
        double saving = scan.nextDouble();
        if (checking < 25.00 && saving < 25.00) {
            System.out.println("deposits are less than required amount");
            create_debit_account(name, email, address);
        } else {
            //Insert inputs into database Table (creating account)
            String read_data = "INSERT INTO CREDITInov(NAME, EMAIL, CARDNUM, CARDCODE, CHECKING, SAVING, ADDRESS, CURRENCY) VALUES " +
                    "(?, ?, ?, ?, ?, ?, ?, ?)";

            try (Connection conn = this.connect();
                 PreparedStatement pstmt = conn.prepareStatement(read_data)) {
                pstmt.setString(1, name);
                pstmt.setString(2, email);
                pstmt.setInt(3, card_num);
                pstmt.setString(4, get_code);
                pstmt.setDouble(5, checking);
                pstmt.setDouble(6, saving);
                pstmt.setString(7, address);
                pstmt.setString(8, curr_code);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        // mail notification
        System.out.println("Credit Account created");

    }

    public void deposit_checking_debit(int cardno, double deposit) throws SQLException {
        String debit_sql = "SELECT * FROM DEBITInov WHERE CARDNUM=?";

        Statement state = connect().createStatement();
        // Debit account check
        ResultSet retract_debit = state.executeQuery(debit_sql);
        // String name = retract_debit.getString("NAME");
        // String email = retract_debit.getString("EMAIL");
        // transaction processing
        String debit_data = "UPDATE DEBITInov set CHECKING=CHECKING+? WHERE CARDNUM=?";
        PreparedStatement stat = connect().prepareStatement(debit_data);
        stat.setDouble(1, deposit);
        stat.setInt(2, cardno);
        stat.executeUpdate();// transaction complete
        System.out.println("Deposit successful");
    }


    public void deposit_saving_debit(int cardno, double deposit) throws SQLException {
        String debit_sql = "SELECT * FROM DEBITInov WHERE CARDNUM=?";

        Statement state = connect().createStatement();
        // Debit account check
        ResultSet retract_debit = state.executeQuery(debit_sql);
        // String name = retract_debit.getString("NAME");
        // String email = retract_debit.getString("EMAIL");
        // transaction processing
        String debit_data = "UPDATE DEBITInov set SAVING=SAVING+? WHERE CARDNUM=?";
        PreparedStatement stat = connect().prepareStatement(debit_data);
        stat.setDouble(1, deposit);
        stat.setInt(2, cardno);
        stat.executeUpdate();
        retract_debit.close(); // transaction complete
        state.close();
        connect().close();
        System.out.println("Deposit successful");
    }

    public void deposit_checking_credit(int cardno, double deposit) throws SQLException {
        String debit_sql = "SELECT * FROM CREDITInov WHERE CARDNUM=?";

        Statement state = connect().createStatement();
        // Debit account check
        ResultSet retract_credit = state.executeQuery(debit_sql);
        // String name = retract_debit.getString("NAME");
        // String email = retract_debit.getString("EMAIL");
        // transaction processing
        String credit_data = "UPDATE CREDITInov set CHECKING=CHECKING+? WHERE CARDNUM=?";
        PreparedStatement stat = connect().prepareStatement(credit_data);
        stat.setDouble(1, deposit);
        stat.setInt(2, cardno);
        stat.executeUpdate();
        retract_credit.close(); // transaction complete
        state.close();
        connect().close();
        System.out.println("Deposit successful");
    }

    public void deposit_saving_credit(int cardno, double deposit) throws SQLException {
        String debit_sql = "SELECT * FROM CREDITInov WHERE CARDNUM=?";

        Statement state = connect().createStatement();
        // Debit account check
        ResultSet retract_debit = state.executeQuery(debit_sql);
        // String name = retract_debit.getString("NAME");
        // String email = retract_debit.getString("EMAIL");
        // transaction processing
        String debit_data = "UPDATE CREDITInov set SAVING=SAVING+? WHERE CARDNUM=?";
        PreparedStatement stat = connect().prepareStatement(debit_data);
        stat.setDouble(1, deposit);
        stat.setInt(2, cardno);
        stat.executeUpdate();
        retract_debit.close(); // transaction complete
        state.close();
        connect().close();
        System.out.println("Deposit successful");
    }


    public void withdraw_checking_Debit(int cardnum, double withdraw) throws SQLException {
        String debit_sql = "SELECT * FROM DEBITInov WHERE CARDNUM=?";
        Statement state = connect().createStatement();
        // Debit account check
        ResultSet retract_debit = state.executeQuery(debit_sql);
        String debit_data = "UPDATE DEBITInov set CHECKING=CHECKING-? WHERE CARDNUM=?";
        PreparedStatement stat = connect().prepareStatement(debit_data);
        stat.setDouble(1, withdraw);
        stat.setInt(2, cardnum);
        stat.executeUpdate();
        retract_debit.close(); // transaction complete
        state.close();
        connect().close();
        System.out.println("Withdrawal successful");
    }

    public void withdraw_saving_Debit(int cardnum, double withdraw) throws SQLException {
        String debit_sql = "SELECT * FROM DEBITInov WHERE CARDNUM=?";
        Statement state = connect().createStatement();
        // Debit account check
        ResultSet retract_debit = state.executeQuery(debit_sql);
        String debit_data = "UPDATE DEBITInov set SAVING=SAVING-? WHERE CARDNUM=?";
        PreparedStatement stat = connect().prepareStatement(debit_data);
        stat.setDouble(1, withdraw);
        stat.setInt(2, cardnum);
        stat.executeUpdate();
        retract_debit.close(); // transaction complete
        state.close();
        connect().close();
        System.out.println("Withdrawal successful");
    }

    public void withdraw_checking_Credit(int cardnum, double withdraw) throws SQLException {
        String debit_sql = "SELECT * FROM CREDITInov WHERE CARDNUM=?";
        Statement state = connect().createStatement();
        // Debit account check
        ResultSet retract_debit = state.executeQuery(debit_sql);
        String debit_data = "UPDATE CREDITInov set CHECKING=CHECKING-? WHERE CARDNUM=?";
        PreparedStatement stat = connect().prepareStatement(debit_data);
        stat.setDouble(1, withdraw);
        stat.setInt(2, cardnum);
        stat.executeUpdate();
        retract_debit.close(); // transaction complete
        state.close();
        connect().close();
        System.out.println("Withdrawal successful");
    }

    public void withdraw_saving_Credit(int cardnum, double withdraw) throws SQLException {
        String debit_sql = "SELECT * FROM CREDITInov WHERE CARDNUM=?";
        Statement state = connect().createStatement();
        // Debit account check
        ResultSet retract_debit = state.executeQuery(debit_sql);
        String debit_data = "UPDATE CREDITInov set SAVING=SAVING-? WHERE CARDNUM=?";
        PreparedStatement stat = connect().prepareStatement(debit_data);
        stat.setDouble(1, withdraw);
        stat.setInt(2, cardnum);
        stat.executeUpdate();
        retract_debit.close(); // transaction complete
        state.close();
        connect().close();
        System.out.println("Withdrawal successful");
    }

    // Debit
    public void send_money_debit(int cardcode, double amount, String recipient) throws SQLException {
        String debit_transfer = "SELECT * FROM DEBITInov WHERE CARDNUM=?";
        // String credit_transfer = "SELECT * FROM CREDIT WHERE CARD-CODE=?";
        Statement state = connect().createStatement();
        // collect valued amount to be sent to recipient (debit check)
        ResultSet retract_debit = state.executeQuery(debit_transfer);
        if (retract_debit.next()) {
            String retrieve = "UPDATE DEBITInov set CHECKING=CHECKING-? WHERE CARDNUM=?";
            PreparedStatement stat = connect().prepareStatement(retrieve);
            stat.setDouble(1, amount);
            stat.setInt(2, cardcode);
            String accept = "UPDATE DEBITInov set CHECKING=CHECKING+? WHERE NAME=?";
            PreparedStatement stat2 = connect().prepareStatement(accept);
            stat2.setDouble(1, amount);
            stat2.setString(2, recipient);
            stat.executeUpdate();
            stat2.executeUpdate();
            System.out.println("Transfer processed");
            retract_debit.close();
        } else {
            send_to_credit(cardcode, amount, recipient);
        }
    }

    public void send_to_credit(int cardcode, double amount, String recipient) throws SQLException {
        String debit_transfer = "SELECT * FROM DEBITInov WHERE CARDNUM=?";
        // String credit_transfer = "SELECT * FROM CREDIT WHERE CARD-CODE=?";
        Statement state = connect().createStatement();
        // collect valued amount to be sent to recipient (debit check)
        ResultSet retract_debit = state.executeQuery(debit_transfer);
        String retrieve = "UPDATE DEBITInov set CHECKING=CHECKING-? WHERE CARDNUM=?";
        PreparedStatement stat = connect().prepareStatement(retrieve);
        stat.setDouble(1, amount);
        stat.setInt(2, cardcode);
        String accept = "UPDATE CREDITInov set CHECKING=CHECKING+? WHERE NAME=?";
        PreparedStatement stat2 = connect().prepareStatement(accept);
        stat2.setDouble(1, amount);
        stat2.setString(2, recipient);
        stat.executeUpdate();
        stat2.executeUpdate();
        retract_debit.close();
        System.out.println("Transfer processed");
    }

    // Credit
    public void send_money_credit(int cardcode, double amount, String recipient) throws SQLException {
        String debit_transfer = "SELECT * FROM CREDITInov WHERE CARDNUM=?";
        // String credit_transfer = "SELECT * FROM CREDIT WHERE CARD-CODE=?";
        Statement state = connect().createStatement();
        // collect valued amount to be sent to recipient (debit check)
        ResultSet retract_credit = state.executeQuery(debit_transfer);
        // int card_code = retract_debit.getInt("CARDNUM");
        if (retract_credit.next()) {
            String retrieve = "UPDATE CREDITInov set CHECKING=CHECKING-? WHERE CARDNUM=?";
            PreparedStatement stat = connect().prepareStatement(retrieve);
            stat.setDouble(1, amount);
            stat.setInt(2, cardcode);
            String accept = "UPDATE CREDITInov set CHECKING=CHECKING+? WHERE NAME=?";
            PreparedStatement stat2 = connect().prepareStatement(accept);
            stat2.setDouble(1, amount);
            stat2.setString(2, recipient);
            stat.executeUpdate();
            stat2.executeUpdate();
            System.out.println("Transfer processed");
            retract_credit.close();
        } else {
            send_to_debit(cardcode, amount, recipient);
        }

    }

    public void send_to_debit(int cardcode, double amount, String recipient) throws SQLException {
        String debit_transfer = "SELECT * FROM CREDITInov WHERE CARDNUM=?";
        // String credit_transfer = "SELECT * FROM CREDIT WHERE CARD-CODE=?";
        Statement state = connect().createStatement();
        // collect valued amount to be sent to recipient (debit check)
        ResultSet retract_debit = state.executeQuery(debit_transfer);
        String retrieve = "UPDATE CREDITInov set CHECKING=CHECKING-? WHERE CARDNUM=?";
        PreparedStatement stat = connect().prepareStatement(retrieve);
        stat.setDouble(1, amount);
        stat.setInt(2, cardcode);
        String accept = "UPDATE DEBITInov set CHECKING=CHECKING+? WHERE NAME=?";
        PreparedStatement stat2 = connect().prepareStatement(accept);
        stat2.setDouble(1, amount);
        stat2.setString(2, recipient);
        stat.executeUpdate();
        stat2.executeUpdate();
        retract_debit.close();
        System.out.println("Transfer processed");
    }

    public void send_money_debit_international(int cardcode, double amount, String recipient) throws SQLException {
        String debit_transfer = "SELECT * FROM InterDEBITInov WHERE CARDNUM=?";
        // String credit_transfer = "SELECT * FROM CREDIT WHERE CARD-CODE=?";
        Statement state = connect().createStatement();
        // collect valued amount to be sent to recipient (debit check)
        ResultSet retract_debit = state.executeQuery(debit_transfer);
        int card_code = retract_debit.getInt("CARDNUM");
        if (card_code == cardcode) {
            String retrieve = "UPDATE InterDEBITInov set CHECKING=CHECKING-? WHERE CARDNUM=?";
            PreparedStatement stat = connect().prepareStatement(retrieve);
            stat.setDouble(1, amount);
            stat.setInt(2, cardcode);
            String accept = "UPDATE InterDEBITInov set CHECKING=CHECKING+? WHERE NAME=?";
            PreparedStatement stat2 = connect().prepareStatement(accept);
            stat2.setDouble(1, amount);
            stat2.setString(2, recipient);
            stat.executeUpdate();
            stat2.executeUpdate();
            System.out.println("Transfer processed");
            retract_debit.close();
        } else {
            send_to_debit_international(cardcode, amount, recipient);
        }
    }

    public void send_to_debit_international(int cardcode, double amount, String recipient) throws SQLException {
        String debit_transfer = "SELECT * FROM InterDEBITInov WHERE CARDNUM=?";
        // String credit_transfer = "SELECT * FROM CREDIT WHERE CARD-CODE=?";
        Statement state = connect().createStatement();
        // collect valued amount to be sent to recipient (debit check)
        ResultSet retract_debit = state.executeQuery(debit_transfer);
        int card_code = retract_debit.getInt("CARDNUM");
        if (card_code == cardcode) {
            String retrieve = "UPDATE InterDEBITInov set CHECKING=CHECKING-? WHERE CARDNUM=?";
            PreparedStatement stat = connect().prepareStatement(retrieve);
            stat.setDouble(1, amount);
            stat.setInt(2, cardcode);
            String accept = "UPDATE DEBITInov set CHECKING=CHECKING+? WHERE NAME=?";
            PreparedStatement stat2 = connect().prepareStatement(accept);
            stat2.setDouble(1, amount);
            stat2.setString(2, recipient);
            stat.executeUpdate();
            stat2.executeUpdate();
            System.out.println("Transfer processed");
            retract_debit.close();
        } else {
            send_to_credit_international(cardcode, amount, recipient);
        }
    }

    public void send_to_credit_international(int cardcode, double amount, String recipient) throws SQLException {
        String debit_transfer = "SELECT * FROM InterDEBITInov WHERE CARDNUM=?";
        // String credit_transfer = "SELECT * FROM CREDIT WHERE CARD-CODE=?";
        Statement state = connect().createStatement();
        // collect valued amount to be sent to recipient (debit check)
        ResultSet retract_debit = state.executeQuery(debit_transfer);
        String retrieve = "UPDATE InterDEBITInov set CHECKING=CHECKING-? WHERE CARDNUM=?";
        PreparedStatement stat = connect().prepareStatement(retrieve);
        stat.setDouble(1, amount);
        stat.setInt(2, cardcode);
        String accept = "UPDATE CREDITInov set CHECKING=CHECKING+? WHERE NAME=?";
        PreparedStatement stat2 = connect().prepareStatement(accept);
        stat2.setDouble(1, amount);
        stat2.setString(2, recipient);
        stat.executeUpdate();
        stat2.executeUpdate();
        retract_debit.close();
        System.out.println("Transfer processed");
    }





    public void debit_bank_statement(int cardcode) throws SQLException {
        // String debit = "SELECT NAME, CARDNUM, CARDCODE, CHECKING, SAVING, ADDRESS, CURRENCY FROM DEBITInov";
        Statement state = connect().createStatement();
        ResultSet retract_debit = state.executeQuery("SELECT NAME, CARDNUM, CARDCODE, CHECKING, SAVING, ADDRESS, CURRENCY FROM DEBITInov WHERE CARDNUM=?");
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

    public void credit_bank_statement(int cardcode) throws SQLException {
        // String debit = "SELECT NAME, CARDNUM, CARDCODE, CHECKING, SAVING, ADDRESS, CURRENCY FROM DEBITInov";
        Statement state = connect().createStatement();
        ResultSet retract_credit = state.executeQuery("SELECT NAME, CARDNUM, CARDCODE, CHECKING, SAVING, ADDRESS, CURRENCY FROM CREDITInov WHERE CARDNUM=?");
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

    public void payment_processor(String Cardcode) throws SQLException {
        Scanner scan3 = new Scanner(System.in);
        double price = 20;
        System.out.println("Please select ?");
        System.out.println("1. Debit ");
        System.out.println("2. Credit ");
        String accnt_line = scan3.nextLine();
        if (accnt_line.equals("1")) {
            pay.getCard_Debit(Cardcode, price);
        }
        if (accnt_line.equals("2")) {
            pay.getCard_Credit(Cardcode, price);
        }
    }

    public void Currency_exchange_Debit_EUR(int cardcode) throws SQLException, IOException {
        BigDecimal convert = YahooFinance.getFx(FxSymbols.USDEUR).getPrice();
        Statement state = connect().createStatement();
        String currency = "EUR";
        // find account for requested transaction (credit check)
        String retract_debit = "UPDATE  DEBITInov set CHECKING=CHECKING/?, SAVING=SAVING/?, CURRENCY=?, WHERE CARDNUM=?";
        PreparedStatement stat = connect().prepareStatement(retract_debit);
        stat.setBigDecimal(1, convert);
        stat.setBigDecimal(2, convert);
        stat.setString(3, currency);
        stat.setInt(4, cardcode);
        stat.executeUpdate();
        state.close();
        System.out.println("Currency Converted on account");
    }

    public void Currency_exchange_Debit_GBP(int cardcode) throws SQLException, IOException {
        BigDecimal convert = YahooFinance.getFx(FxSymbols.USDGBP).getPrice();
        Statement state = connect().createStatement();
        String currency = "GBP";
        // find account for requested transaction (credit check)
        String retract_debit = "UPDATE  DEBITInov set CHECKING=CHECKING/?, SAVING=SAVING/?, CURRENCY=?, WHERE CARDNUM=?";
        PreparedStatement stat = connect().prepareStatement(retract_debit);
        stat.setBigDecimal(1, convert);
        stat.setBigDecimal(2, convert);
        stat.setString(3, currency);
        stat.setInt(4, cardcode);
        stat.executeUpdate();
        state.close();
        System.out.println("Currency Converted on account");
    }

    public void Currency_exchange_Debit_AUS(int cardcode) throws SQLException, IOException {
        BigDecimal convert = YahooFinance.getFx(FxSymbols.USDAUD).getPrice();
        Statement state = connect().createStatement();
        String currency = "AUD";
        // find account for requested transaction (credit check)
        String retract_debit = "UPDATE  DEBITInov set CHECKING=CHECKING/?, SAVING=SAVING/?, CURRENCY=?, WHERE CARDNUM=?";
        PreparedStatement stat = connect().prepareStatement(retract_debit);
        stat.setBigDecimal(1, convert);
        stat.setBigDecimal(2, convert);
        stat.setString(3, currency);
        stat.setInt(4, cardcode);
        stat.executeUpdate();
        state.close();
        System.out.println("Currency Converted on account");
    }

    public void Currency_exchange_Debit_JPY(int cardcode) throws SQLException, IOException {
        BigDecimal convert = YahooFinance.getFx(FxSymbols.USDJPY).getPrice();
        Statement state = connect().createStatement();
        String currency = "JPY";
        // find account for requested transaction (credit check)
        String retract_debit = "UPDATE  DEBITInov set CHECKING=CHECKING/?, SAVING=SAVING/?, CURRENCY=?, WHERE CARDNUM=?";
        PreparedStatement stat = connect().prepareStatement(retract_debit);
        stat.setBigDecimal(1, convert);
        stat.setBigDecimal(2, convert);
        stat.setString(3, currency);
        stat.setInt(4, cardcode);
        stat.executeUpdate();
        state.close();
        System.out.println("Currency Converted on account");
    }

    public void Currency_exchange_Debit_HKD(int cardcode) throws SQLException, IOException {
        BigDecimal convert = YahooFinance.getFx(FxSymbols.USDHKD).getPrice();
        Statement state = connect().createStatement();
        String currency = "HKD";
        // find account for requested transaction (credit check)
        String retract_debit = "UPDATE  DEBITInov set CHECKING=CHECKING/?, SAVING=SAVING/?, CURRENCY=?, WHERE CARDNUM=?";
        PreparedStatement stat = connect().prepareStatement(retract_debit);
        stat.setBigDecimal(1, convert);
        stat.setBigDecimal(2, convert);
        stat.setString(3, currency);
        stat.setInt(4, cardcode);
        stat.executeUpdate();
        state.close();
        System.out.println("Currency Converted on account");
    }

    public void Currency_exchange_Debit_International(int cardcode, String base_country, String Converto_country) throws SQLException, IOException {
        // base currency will be converted to this
        String base = String.valueOf(Currency.getInstance(base_country));
        String conv  = String.valueOf(Currency.getInstance(Converto_country));
        String concat =  base.concat(conv);
        BigDecimal convert = YahooFinance.getFx(concat).getPrice();
        BigDecimal num = BigDecimal.valueOf(convert.doubleValue());
        //String debit_statement = "SELECT *  FROM DEBITInov WHERE CARDNUM=?";
        Statement state = connect().createStatement();
        // find account for requested transaction (credit check)
        String retract_debit = "UPDATE  InterDEBITInov set CHECKING=CHECKING/?, SAVING=SAVING/?, WHERE CARDNUM=?";
        PreparedStatement stat = connect().prepareStatement(retract_debit);
        stat.setBigDecimal(1, num);
        stat.setBigDecimal(2, num);
        stat.setInt(3, cardcode);
        stat.executeUpdate();
        state.close();
        System.out.println("Currency Converted on account");
    }

    public void Currency_exchange_Credit_EUR(int cardcode) throws SQLException, IOException {
        BigDecimal convert = YahooFinance.getFx(FxSymbols.USDEUR).getPrice();
        Statement state = connect().createStatement();
        String currency = "EUR";
        // find account for requested transaction (credit check)
        String retract_credit = "UPDATE  CREDITInov set CHECKING=CHECKING/?, SAVING=SAVING/?, WHERE CARDNUM=?";
        PreparedStatement stat = connect().prepareStatement(retract_credit);
        stat.setBigDecimal(1, convert);
        stat.setBigDecimal(2, convert);
        stat.setString(3, currency);
        stat.setInt(4, cardcode);
        stat.executeUpdate();
        state.close();
        System.out.println("Currency Converted on account");
    }

    public void Currency_exchange_Credit_GBP(int cardcode) throws SQLException, IOException {
        BigDecimal convert = YahooFinance.getFx(FxSymbols.USDGBP).getPrice();
        Statement state = connect().createStatement();
        String currency = "GBP";
        // find account for requested transaction (credit check)
        String retract_credit = "UPDATE  CREDITInov set CHECKING=CHECKING/?, SAVING=SAVING/?, WHERE CARDNUM=?";
        PreparedStatement stat = connect().prepareStatement(retract_credit);
        stat.setBigDecimal(1, convert);
        stat.setBigDecimal(2, convert);
        stat.setString(3, currency);
        stat.setInt(4, cardcode);
        stat.executeUpdate();
        state.close();
        System.out.println("Currency Converted on account");
    }

    public void Currency_exchange_Credit_AUS(int cardcode) throws SQLException, IOException {
        BigDecimal convert = YahooFinance.getFx(FxSymbols.USDAUD).getPrice();
        Statement state = connect().createStatement();
        String currency = "AUD";
        // find account for requested transaction (credit check)
        String retract_credit = "UPDATE  CREDITInov set CHECKING=CHECKING/?, SAVING=SAVING/?, WHERE CARDNUM=?";
        PreparedStatement stat = connect().prepareStatement(retract_credit);
        stat.setBigDecimal(1, convert);
        stat.setBigDecimal(2, convert);
        stat.setString(3, currency);
        stat.setInt(4, cardcode);
        stat.executeUpdate();
        state.close();
        System.out.println("Currency Converted on account");
    }

    public void Currency_exchange_Credit_JPY(int cardcode) throws SQLException, IOException {
        BigDecimal convert = YahooFinance.getFx(FxSymbols.USDJPY).getPrice();
        Statement state = connect().createStatement();
        String currency = "JPY";
        // find account for requested transaction (credit check)
        String retract_credit = "UPDATE  CREDITInov set CHECKING=CHECKING/?, SAVING=SAVING/?, WHERE CARDNUM=?";
        PreparedStatement stat = connect().prepareStatement(retract_credit);
        stat.setBigDecimal(1, convert);
        stat.setBigDecimal(2, convert);
        stat.setString(3, currency);
        stat.setInt(4, cardcode);
        stat.executeUpdate();
        state.close();
        System.out.println("Currency Converted on account");
    }

    public void Currency_exchange_Credit_HongKong(int cardcode) throws SQLException, IOException {
        BigDecimal convert = YahooFinance.getFx(FxSymbols.USDHKD).getPrice();
        Statement state = connect().createStatement();
        String currency = "HKD";
        // find account for requested transaction (credit check)
        String retract_credit = "UPDATE  CREDITInov set CHECKING=CHECKING/?, SAVING=SAVING/?, CURRENCY=?, WHERE CARDNUM=?";
        PreparedStatement stat = connect().prepareStatement(retract_credit);
        stat.setBigDecimal(1, convert);
        stat.setBigDecimal(2, convert);
        stat.setString(3, currency);
        stat.setInt(4, cardcode);
        stat.executeUpdate();
        state.close();
        System.out.println("Currency Converted on account");
    }

    public void International_transactions_Debit(int cardcode, String base_country, String Converto_country, BigDecimal amount, String name) throws SQLException, IOException {
        Currency currency_base = Currency.getInstance(base_country);// base currency will be converted to this
        String convert_base = currency_base.getCurrencyCode();
        Currency convert_into = Currency.getInstance(Converto_country);
        String convertion = convert_into.getCurrencyCode();
        String concat = convert_base + convertion;
        BigDecimal convert = YahooFinance.getFx(concat).getPrice();
        BigDecimal num = BigDecimal.valueOf(convert.doubleValue());
        BigDecimal Transaction_value_interdebit = amount.divide(num, RoundingMode.CEILING);
        if (convert_base.equals(concat)) {
            Statement state = connect().createStatement();
            String debit_statement = "UPDATE InterDEBITInov set CHECKING=CHECKING-? WHERE CARDNUM=?";
            PreparedStatement stat = connect().prepareStatement(debit_statement);
            stat.setBigDecimal(1, Transaction_value_interdebit);
            stat.setInt(2, cardcode);
            String recieve_money = "UPDATE InterDEBITInov set CHECKING=CHECKING+? WHERE NAME=?";
            PreparedStatement stat2 = connect().prepareStatement(recieve_money);
            stat2.setBigDecimal(1, Transaction_value_interdebit);
            stat2.setString(2, name);
            stat2.executeUpdate();
            System.out.println("Transfer complete");
            state.close();
        } else {
            International_transactions_send_to_Debit(cardcode, base_country, Converto_country, amount, name);
        }
    }

    public void International_transactions_send_to_Debit(int cardcode, String base_country, String Converto_country, BigDecimal amount, String name) throws SQLException, IOException {
        Currency currency_base = Currency.getInstance(base_country);// base currency will be converted to this
        String convert_base = currency_base.getCurrencyCode();
        Currency convert_into = Currency.getInstance(Converto_country);
        String convertion = convert_into.getCurrencyCode();
        String concat = convert_base + convertion;
        BigDecimal convert = YahooFinance.getFx(concat).getPrice();
        BigDecimal num = BigDecimal.valueOf(convert.doubleValue());
        BigDecimal Transaction_value_debit = amount.divide(num, RoundingMode.CEILING);
        if (convert_base.equals(concat)) {
            Statement state = connect().createStatement();
            String debit_statement = "UPDATE InterDEBITInov set CHECKING=CHECKING-? WHERE CARDNUM=?";
            PreparedStatement stat = connect().prepareStatement(debit_statement);
            stat.setBigDecimal(1, Transaction_value_debit);
            stat.setInt(2, cardcode);
            String recieve_money = "UPDATE DEBITInov set CHECKING=CHECKING+? WHERE NAME=?";
            PreparedStatement stat2 = connect().prepareStatement(recieve_money);
            stat2.setBigDecimal(1, Transaction_value_debit);
            stat2.setString(2, name);
            stat2.executeUpdate();
            System.out.println("Transfer complete");
            state.close();
        } else {
            International_transactions_send_to_credit(cardcode, base_country, Converto_country, amount, name);
        }
    }

    public void International_transactions_send_to_credit(int cardcode, String base_country, String Converto_country, BigDecimal amount, String name) throws IOException, SQLException {
        Currency currency_base_intercredit = Currency.getInstance(base_country);// base currency will be converted to this
        String convert_base_intercredit = currency_base_intercredit.getCurrencyCode();
        Currency convert_into_intercredit = Currency.getInstance(Converto_country);
        String convertion_intercredit = convert_into_intercredit.getCurrencyCode();
        String concat_intercredit = convert_base_intercredit + convertion_intercredit;
        BigDecimal conver_intercredit = YahooFinance.getFx(concat_intercredit).getPrice();
        BigDecimal num_intercredit = BigDecimal.valueOf(conver_intercredit.doubleValue());
        BigDecimal Transaction_value_credit = amount.divide(num_intercredit, RoundingMode.CEILING);
        Statement state = connect().createStatement();
        String debit_statement = "UPDATE InterDEBITInov set CHECKING=CHECKING-? WHERE CARDNUM=?";
        PreparedStatement stat = connect().prepareStatement(debit_statement);
        stat.setBigDecimal(1, Transaction_value_credit);
        stat.setInt(2, cardcode);
        String recieve_money = "UPDATE CREDITInov set CHECKING=CHECKING+? WHERE NAME=?";
        PreparedStatement stat2 = connect().prepareStatement(recieve_money);
        stat2.setBigDecimal(1, Transaction_value_credit);
        stat2.setString(2, name);
        stat2.executeUpdate();
        System.out.println("Transfer complete");
        state.close();

        }
    }






