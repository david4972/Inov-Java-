import javax.crypto.KeyGenerator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
//import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;
import java.util.Currency;
import java.util.Properties;
import java.util.Scanner;
import yahoofinance.YahooFinance;
//import yahoofinance.quotes.fx.FxSymbols;

import java.math.*;




public class inov {
    invp pay = new invp();
    
    CurrenciesCurrencyExchange chan = new CurrenciesCurrencyExchange();

    Currencies current = new Currencies();

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
                //send_mail_new_Account_Debit(email, card_num, get_code);
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
                //send_mail_new_Account_International_Debit(email, card_num, get_code);
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
                //send_mail_new_Account_Credit(email, card_num, get_code);
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
        String email = retract_debit.getString("EMAIL");
        // transaction processing
        String debit_data = "UPDATE DEBITInov set CHECKING=CHECKING+? WHERE CARDNUM=?";
        PreparedStatement stat = connect().prepareStatement(debit_data);
        stat.setDouble(1, deposit);
        stat.setInt(2, cardno);
        stat.executeUpdate();// transaction complete
        System.out.println("Deposit successful");
        //send_mail_deposit_Checking(email, deposit);
    }


    public void deposit_saving_debit(int cardno, double deposit) throws SQLException {
        String debit_sql = "SELECT * FROM DEBITInov WHERE CARDNUM=?";

        Statement state = connect().createStatement();
        // Debit account check
        ResultSet retract_debit = state.executeQuery(debit_sql);
        // String name = retract_debit.getString("NAME");
        String email = retract_debit.getString("EMAIL");
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
        //send_mail_deposit_Saving(email, deposit);
    }

    public void deposit_checking_credit(int cardno, double deposit) throws SQLException {
        String credit_sql = "SELECT * FROM CREDITInov WHERE CARDNUM=?";

        Statement state = connect().createStatement();
        // Debit account check
        ResultSet retract_credit = state.executeQuery(credit_sql);
        // String name = retract_debit.getString("NAME");
        String email = retract_credit.getString("EMAIL");
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
        //send_mail_deposit_Checking(email, deposit);
    }

    public void deposit_saving_credit(int cardno, double deposit) throws SQLException {
        String credit_sql = "SELECT * FROM CREDITInov WHERE CARDNUM=?";

        Statement state = connect().createStatement();
        // Debit account check
        ResultSet retract_credit = state.executeQuery(credit_sql);
        // String name = retract_debit.getString("NAME");
        String email = retract_credit.getString("EMAIL");
        // transaction processing
        String debit_data = "UPDATE CREDITInov set SAVING=SAVING+? WHERE CARDNUM=?";
        PreparedStatement stat = connect().prepareStatement(debit_data);
        stat.setDouble(1, deposit);
        stat.setInt(2, cardno);
        stat.executeUpdate();
        retract_credit.close(); // transaction complete
        state.close();
        connect().close();
        System.out.println("Deposit successful");
        //send_mail_deposit_Saving(email, deposit);
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
        String name = retract_debit.getString("NAME");
        if (retract_debit.next()) {
            String retrieve = "UPDATE DEBITInov set CHECKING=CHECKING-? WHERE CARDNUM=?";
            PreparedStatement stat = connect().prepareStatement(retrieve);
            stat.setDouble(1, amount);
            stat.setInt(2, cardcode);
            String accept1 = "SELECT * FROM DEBITInov WHERE NAME=?";
            ResultSet retract_credit = state.executeQuery(accept1);
            String email = retract_credit.getString("EMAIL");
            String accept2 = "UPDATE DEBITInov set CHECKING=CHECKING+? WHERE NAME=?";
            PreparedStatement stat2 = connect().prepareStatement(accept2);
            stat2.setDouble(1, amount);
            stat2.setString(2, recipient);
            stat.executeUpdate();
            stat2.executeUpdate();
            System.out.println("Transfer processed");
            retract_debit.close();
            //send_mail_transaction(email, amount, name);
        } else {
            send_to_credit(cardcode, amount, recipient);
        }
    }

    public void send_to_credit(int cardcode, double amount, String recipient) throws SQLException {
        String debit_transfer = "SELECT * FROM DEBITInov WHERE CARDNUM=?";
        // String credit_transfer = "SELECT * FROM CREDIT WHERE CARD-CODE=?";
        Statement state = connect().createStatement();
        ResultSet retract_debit = state.executeQuery(debit_transfer);
        String name = retract_debit.getString("NAME");
        // collect valued amount to be sent to recipient (debit check)
        ResultSet retract_credit = state.executeQuery(debit_transfer);
        String retrieve = "UPDATE DEBITInov set CHECKING=CHECKING-? WHERE CARDNUM=?";
        PreparedStatement stat = connect().prepareStatement(retrieve);
        stat.setDouble(1, amount);
        stat.setInt(2, cardcode);
        String accept1 = "SELECT * FROM CREDITInov WHERE NAME=?";
        ResultSet retract_credit_pay = state.executeQuery(accept1);
        String email = retract_credit_pay.getString("EMAIL");
        String accept = "UPDATE CREDITInov set CHECKING=CHECKING+? WHERE NAME=?";
        PreparedStatement stat2 = connect().prepareStatement(accept);
        stat2.setDouble(1, amount);
        stat2.setString(2, recipient);
        stat.executeUpdate();
        stat2.executeUpdate();
        retract_debit.close();
        System.out.println("Transfer processed");
        //send_mail_transaction(email, amount, name);
    }

    // Credit
    public void send_money_credit(int cardcode, double amount, String recipient) throws SQLException {
        String debit_transfer = "SELECT * FROM CREDITInov WHERE CARDNUM=?";
        // String credit_transfer = "SELECT * FROM CREDIT WHERE CARD-CODE=?";
        Statement state = connect().createStatement();
        ResultSet retract_debit = state.executeQuery(debit_transfer);
        String name = retract_debit.getString("NAME");
        // collect valued amount to be sent to recipient (debit check)
        ResultSet retract_credit = state.executeQuery(debit_transfer);
        // int card_code = retract_debit.getInt("CARDNUM");
        if (retract_credit.next()) {
            String retrieve = "UPDATE CREDITInov set CHECKING=CHECKING-? WHERE CARDNUM=?";
            PreparedStatement stat = connect().prepareStatement(retrieve);
            stat.setDouble(1, amount);
            stat.setInt(2, cardcode);
            String accept1 = "SELECT * FROM CREDITInov WHERE NAME=?";
            ResultSet retract_credit_pay = state.executeQuery(accept1);
            String email = retract_credit_pay.getString("EMAIL");
            String accept = "UPDATE CREDITInov set CHECKING=CHECKING+? WHERE NAME=?";
            PreparedStatement stat2 = connect().prepareStatement(accept);
            stat2.setDouble(1, amount);
            stat2.setString(2, recipient);
            stat.executeUpdate();
            stat2.executeUpdate();
            System.out.println("Transfer processed");
            retract_credit.close();
            //send_mail_transaction(email, amount, name);
        } else {
            send_to_debit(cardcode, amount, recipient);
        }

    }

    public void send_to_debit(int cardcode, double amount, String recipient) throws SQLException {
        String debit_transfer = "SELECT * FROM CREDITInov WHERE CARDNUM=?";
        // String credit_transfer = "SELECT * FROM CREDIT WHERE CARD-CODE=?";
        Statement state = connect().createStatement();
        ResultSet retract_debit = state.executeQuery(debit_transfer);
        String name = retract_debit.getString("NAME");
        String retrieve = "UPDATE CREDITInov set CHECKING=CHECKING-? WHERE CARDNUM=?";
        PreparedStatement stat = connect().prepareStatement(retrieve);
        stat.setDouble(1, amount);
        stat.setInt(2, cardcode);
        String accept1 = "SELECT * FROM DEBITInov WHERE NAME=?";
        ResultSet retract_credit_pay = state.executeQuery(accept1);
        String email = retract_credit_pay.getString("EMAIL");
        String accept = "UPDATE DEBITInov set CHECKING=CHECKING+? WHERE NAME=?";
        PreparedStatement stat2 = connect().prepareStatement(accept);
        stat2.setDouble(1, amount);
        stat2.setString(2, recipient);
        stat.executeUpdate();
        stat2.executeUpdate();
        retract_debit.close();
        System.out.println("Transfer processed");
        //send_mail_transaction(email, amount, name);
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

    public void Currency_exchange(String base_curr, String convert_curr, int cardcode) throws SQLException, IOException {
        chan.Country_currency_CurrencyExchange(base_curr, convert_curr, cardcode);
    }

    public void International_debit_send(String base_curr, String Country_name, int cardcode, double amount, String name) throws SQLException, IOException {
        current.Country_currency_InternationalTransactions(base_curr, Country_name, cardcode, amount, name);
    }



    public static void send_mail_new_Account_Debit(String mail, int cardno, String cardcode ) {
        // email ID of  Sender.
        String sender = "monetarytransatlantic@gmail.com";
        // using host as localhost
        String host = "204.14.73.237";
        // Getting system properties
        Properties properties = System.getProperties();
        // Setting up mail server
        properties.setProperty("mail.smtp.host", host);
        // creating session object to get properties
        Session session = Session.getDefaultInstance(properties);

        try {
            // MimeMessage object.
            MimeMessage message = new MimeMessage(session);
            // Set From Field: adding senders email to from field.
            message.setFrom(new InternetAddress(sender));
            // Set To Field: adding recipient's email to from field.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(mail));
            // Set Subject: subject of the email
            message.setSubject("Congrats on your new Account!!!");
            message.setText("\"Congrats on your new Debit Bank account!!! This " + cardno + " is your card number. This " + cardcode + " is your secured \" \\\n" +
                    "            \"card \" \\\n" +
                    "            \"code that will be used to secure your account. \"");
            Transport.send(message);
        }
        catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    public static void send_mail_new_Account_Credit(String mail, int cardno, String cardcode ) {
        // email ID of  Sender.
        String sender = "monetarytransatlantic@gmail.com";
        // using host as localhost
        String host = "127.0.0.1";
        // Getting system properties
        Properties properties = System.getProperties();
        // Setting up mail server
        properties.setProperty("mail.smtp.host", host);
        // creating session object to get properties
        Session session = Session.getDefaultInstance(properties);

        try {
            // MimeMessage object.
            MimeMessage message = new MimeMessage(session);
            // Set From Field: adding senders email to from field.
            message.setFrom(new InternetAddress(sender));
            // Set To Field: adding recipient's email to from field.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(mail));
            // Set Subject: subject of the email
            message.setSubject("Congrats on your new Account!!!");
            message.setText("\"Congrats on your new Credit Bank account!!! This " + cardno + " is your card number. This " + cardcode + " is your secured \" \\\n" +
                    "            \"card \" \\\n" +
                    "            \"code that will be used to secure your account. \"");
            Transport.send(message);
        }
        catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    public static void send_mail_new_Account_International_Debit(String mail, int cardno, String cardcode ) {
        // email ID of  Sender.
        String sender = "monetarytransatlantic@gmail.com";
        // using host as localhost
        String host = "172.16.29.25";
        // Getting system properties
        Properties properties = System.getProperties();
        // Setting up mail server
        properties.setProperty("mail.smtp.host", host);
        // creating session object to get properties
        Session session = Session.getDefaultInstance(properties);

        try {
            // MimeMessage object.
            MimeMessage message = new MimeMessage(session);
            // Set From Field: adding senders email to from field.
            message.setFrom(new InternetAddress(sender));
            // Set To Field: adding recipient's email to from field.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(mail));
            // Set Subject: subject of the email
            message.setSubject("Congrats on your new Account!!!");
            message.setText("\"Congrats on your new International Debit Bank account!!! This " + cardno + " is your card number. This " + cardcode + " is your secured \" \\\n" +
                    "            \"card \" \\\n" +
                    "            \"code that will be used to secure your account. \"");
            Transport.send(message);
        }
        catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    public static void send_mail_transaction(String mail, double amount, String name ) {
        // email ID of  Sender.
        String sender = "monetarytransatlantic@gmail.com";
        // using host as localhost
        String host = "127.0.0.1";
        // Getting system properties
        Properties properties = System.getProperties();
        // Setting up mail server
        properties.setProperty("mail.smtp.host", host);
        // creating session object to get properties
        Session session = Session.getDefaultInstance(properties);
        try {
            // MimeMessage object.
            MimeMessage message = new MimeMessage(session);
            // Set From Field: adding senders email to from field.
            message.setFrom(new InternetAddress(sender));
            // Set To Field: adding recipient's email to from field.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(mail));
            // Set Subject: subject of the email
            message.setSubject("Money Sent!!!");
            message.setText("You have received $ " + amount + " from " + name);
            Transport.send(message);
            Transport.send(message);
            System.out.println("Mail successfully sent");
        }
        catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    public static void send_mail_International_transaction(String mail, double amount, String name, String Country) {
        // email ID of  Sender.
        String sender = "monetarytransatlantic@gmail.com";
        // using host as localhost
        String host = "127.0.0.1";
        // Getting system properties
        Properties properties = System.getProperties();
        // Setting up mail server
        properties.setProperty("mail.smtp.host", host);
        // creating session object to get properties
        Session session = Session.getDefaultInstance(properties);
        try
        {
            // MimeMessage object.
            MimeMessage message = new MimeMessage(session);
            // Set From Field: adding senders email to from field.
            message.setFrom(new InternetAddress(sender));
            // Set To Field: adding recipient's email to from field.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(mail));
            // Set Subject: subject of the email
            message.setSubject("Money Sent!!!");
            message.setText("You have received $ " + amount + " from " + name + "in " + Country);
            Transport.send(message);
            System.out.println("Mail successfully sent");
        }
        catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    public static void send_mail_currency_exchange(String mail, String converted_Currency) {
        // email ID of  Sender.
        String sender = "monetarytransatlantic@gmail.com";
        // using host as localhost
        String host = "127.0.0.1";
        // Getting system properties
        Properties properties = System.getProperties();
        // Setting up mail server
        properties.setProperty("mail.smtp.host", host);
        // creating session object to get properties
        Session session = Session.getDefaultInstance(properties);
        try
        {
            // MimeMessage object.
            MimeMessage message = new MimeMessage(session);
            // Set From Field: adding senders email to from field.
            message.setFrom(new InternetAddress(sender));
            // Set To Field: adding recipient's email to from field.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(mail));
            // Set Subject: subject of the email
            message.setSubject("Converted Cash account");
            // set body of the email.
            message.setText("You have converted your account to this currency = " + converted_Currency);
            // Send email.
            Transport.send(message);
            System.out.println("Mail successfully sent");
        }
        catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }


    public static void send_mail_deposit_Checking(String mail, double deposit) {
        // email ID of  Sender.
        String sender = "monetarytransatlantic@gmail.com";
        // using host as localhost
        String host = "127.0.0.1";
        // Getting system properties
        Properties properties = System.getProperties();
        // Setting up mail server
        properties.setProperty("mail.smtp.host", host);
        // creating session object to get properties
        Session session = Session.getDefaultInstance(properties);
        try
        {
            // MimeMessage object.
            MimeMessage message = new MimeMessage(session);
            // Set From Field: adding senders email to from field.
            message.setFrom(new InternetAddress(sender));
            // Set To Field: adding recipient's email to from field.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(mail));
            // Set Subject: subject of the email
            message.setSubject("Deposit");
            // set body of the email.
            message.setText("You have just deposited " + deposit + "into your checking account.");
            // Send email.
            Transport.send(message);
            System.out.println("Mail successfully sent");
        }
        catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    public static void send_mail_deposit_Saving(String mail, double deposit) {
        // email ID of  Sender.
        String sender = "monetarytransatlantic@gmail.com";
        // using host as localhost
        String host = "127.0.0.1";
        // Getting system properties
        Properties properties = System.getProperties();
        // Setting up mail server
        properties.setProperty("mail.smtp.host", host);
        // creating session object to get properties
        Session session = Session.getDefaultInstance(properties);
        try
        {
            // MimeMessage object.
            MimeMessage message = new MimeMessage(session);
            // Set From Field: adding senders email to from field.
            message.setFrom(new InternetAddress(sender));
            // Set To Field: adding recipient's email to from field.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(mail));
            // Set Subject: subject of the email
            message.setSubject("Deposit");
            // set body of the email.
            message.setText("You have just deposited " + deposit + "into your saving account.");
            // Send email.
            Transport.send(message);
            System.out.println("Mail successfully sent");
        }
        catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}



