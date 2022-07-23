import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.Scanner;

public class InternationalTransactions {

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

    public void International_exchange(String base_curr, String convert_curr, int cardcode, double amount, String name) throws IOException, SQLException {
        Character Line = '/';
        Character Line2 = '/';
        // Setting URL
        String url_str = "https://v6.exchangerate-api.com/v6/2816493d654f23cda23c4ccc/pair/";
        String join = base_curr.concat(String.valueOf(Line)).concat(convert_curr).concat(String.valueOf(Line2)).concat(String.valueOf(amount));
        String encodeStr = URLEncoder.encode(join, StandardCharsets.UTF_8.name());
        String combine = url_str + encodeStr;
        // Making Request
        URL url = new URL(combine);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.connect();

        // Convert to JSON
        JsonParser jp = new JsonParser();
        JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
        JsonObject jsonobj = root.getAsJsonObject();

        // Accessing object
        BigDecimal req_result = jsonobj.get("conversion_result").getAsBigDecimal();
        // String res = Double.toString(req_result);
        double res = req_result.doubleValue();
        Scanner scan = new Scanner(System.in);
        System.out.println("Select account");
        System.out.println("1. Debit");
        System.out.println("2. Credit");
        System.out.println("3. International Debit");
        String read_accnt = scan.nextLine();
        if (read_accnt.equals("1")) {
            send_money_debit(cardcode, res, convert_curr);
        }
        if (read_accnt.equals("2")) {
            send_Credit_to_debit(cardcode, res, convert_curr);
        }
        if (read_accnt.equals("3")) {
            send_money_debit_international(cardcode, res, name, base_curr);
        }

    }

    // International Debit
    public void send_money_debit_international(int cardcode, double amount, String recipient, String Country) throws SQLException {
        String debit_transfer = "SELECT * FROM InovInterDEBIT WHERE CARDNUM=?";
        // String credit_transfer = "SELECT * FROM CREDIT WHERE CARD-CODE=?";
        Statement state = connect().createStatement();
        ResultSet retract_debit = state.executeQuery(debit_transfer);
        String name = retract_debit.getString("NAME");
        // collect valued amount to be sent to recipient (debit check)
        int card_code = retract_debit.getInt("CARDNUM");
        if (card_code == cardcode) {
            String retrieve = "UPDATE InovInterDEBIT set CHECKING=CHECKING-? WHERE CARDNUM=?";
            PreparedStatement stat = connect().prepareStatement(retrieve);
            stat.setDouble(1, amount);
            stat.setInt(2, cardcode);
            String accept1 = "SELECT * FROM InovInterDEBIT  WHERE NAME=?";
            ResultSet retract_credit_pay = state.executeQuery(accept1);
            String email = retract_credit_pay.getString("EMAIL");
            String accept = "UPDATE InovInterDEBIT set CHECKING=CHECKING+? WHERE NAME=?";
            PreparedStatement stat2 = connect().prepareStatement(accept);
            stat2.setDouble(1, amount);
            stat2.setString(2, recipient);
            stat.executeUpdate();
            stat2.executeUpdate();
            System.out.println("Transfer processed");
            retract_debit.close();
            //send_mail_International_transaction(email, amount, name, Country);
        } else {
            send_to_debit_international(cardcode, amount, recipient, Country);
        }
    }

    public void send_to_debit_international(int cardcode, double amount, String recipient, String Country) throws SQLException {
        String debit_transfer = "SELECT * FROM InovInterDEBIT WHERE CARDNUM=?";
        // String credit_transfer = "SELECT * FROM CREDIT WHERE CARD-CODE=?";
        Statement state = connect().createStatement();
        ResultSet retract_debit = state.executeQuery(debit_transfer);
        String name = retract_debit.getString("NAME");
        int card_code = retract_debit.getInt("CARDNUM");
        if (card_code == cardcode) {
            String retrieve = "UPDATE InovInterDEBIT set CHECKING=CHECKING-? WHERE CARDNUM=?";
            PreparedStatement stat = connect().prepareStatement(retrieve);
            stat.setDouble(1, amount);
            stat.setInt(2, cardcode);
            String accept1 = "SELECT * FROM DEBITInov  WHERE NAME=?";
            ResultSet retract_credit_pay = state.executeQuery(accept1);
            String email = retract_credit_pay.getString("EMAIL");
            String accept = "UPDATE DEBITInov set CHECKING=CHECKING+? WHERE NAME=?";
            PreparedStatement stat2 = connect().prepareStatement(accept);
            stat2.setDouble(1, amount);
            stat2.setString(2, recipient);
            stat.executeUpdate();
            stat2.executeUpdate();
            System.out.println("Transfer processed");
            retract_debit.close();
            //send_mail_International_transaction(email, amount, name, Country);
        } else {
            send_to_credit_international(cardcode, amount, recipient, Country);
        }
    }

    public void send_to_credit_international(int cardcode, double amount, String recipient, String Country) throws SQLException {
        String debit_transfer = "SELECT * FROM InovInterDEBIT WHERE CARDNUM=?";
        // String credit_transfer = "SELECT * FROM CREDIT WHERE CARD-CODE=?";
        Statement state = connect().createStatement();
        ResultSet retract_debit = state.executeQuery(debit_transfer);
        String name = retract_debit.getString("NAME");
        // collect valued amount to be sent to recipient (debit check)
        String retrieve = "UPDATE InovInterDEBIT set CHECKING=CHECKING-? WHERE CARDNUM=?";
        PreparedStatement stat = connect().prepareStatement(retrieve);
        stat.setDouble(1, amount);
        stat.setInt(2, cardcode);
        String accept1 = "SELECT * FROM CREDITInov  WHERE NAME=?";
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
        //send_mail_International_transaction(email, amount, name, Country);
    }

    // Debit
    public void send_money_debit(int cardcode, double amount, String recipient) throws SQLException {
        String debit_transfer = "SELECT * FROM InovDEBIT WHERE CARDNUM=?";
        // String credit_transfer = "SELECT * FROM CREDIT WHERE CARD-CODE=?";
        Statement state = connect().createStatement();
        // collect valued amount to be sent to recipient (debit check)
        ResultSet retract_debit = state.executeQuery(debit_transfer);
        String name = retract_debit.getString("NAME");
        if (retract_debit.next()) {
            String retrieve = "UPDATE InovDEBIT set CHECKING=CHECKING-? WHERE CARDNUM=?";
            PreparedStatement stat = connect().prepareStatement(retrieve);
            stat.setDouble(1, amount);
            stat.setInt(2, cardcode);
            String accept1 = "SELECT * FROM InovInterDEBIT WHERE NAME=?";
            ResultSet retract_credit = state.executeQuery(accept1);
            String email = retract_credit.getString("EMAIL");
            String accept2 = "UPDATE InovInterDEBIT set CHECKING=CHECKING+? WHERE NAME=?";
            PreparedStatement stat2 = connect().prepareStatement(accept2);
            stat2.setDouble(1, amount);
            stat2.setString(2, recipient);
            stat.executeUpdate();
            stat2.executeUpdate();
            System.out.println("Transfer processed");
            retract_debit.close();
            //send_mail_transaction(email, amount, name);
        } else {
            System.out.println("Transfer not processed");
        }
    }



    // Credit
    public void send_Credit_to_debit(int cardcode, double amount, String recipient) throws SQLException {
        String debit_transfer = "SELECT * FROM InovCREDIT WHERE CARDNUM=?";
        // String credit_transfer = "SELECT * FROM CREDIT WHERE CARD-CODE=?";
        Statement state = connect().createStatement();
        ResultSet retract_debit = state.executeQuery(debit_transfer);
        //String name = retract_debit.getString("NAME");
        String retrieve = "UPDATE InovCREDIT set CHECKING=CHECKING-? WHERE CARDNUM=?";
        PreparedStatement stat = connect().prepareStatement(retrieve);
        stat.setDouble(1, amount);
        stat.setInt(2, cardcode);
        String accept1 = "SELECT * FROM InovInterDEBIT WHERE NAME=?";
        ResultSet retract_credit_pay = state.executeQuery(accept1);
        //String email = retract_credit_pay.getString("EMAIL");
        String accept = "UPDATE InovInterDEBIT set CHECKING=CHECKING+? WHERE NAME=?";
        PreparedStatement stat2 = connect().prepareStatement(accept);
        stat2.setDouble(1, amount);
        stat2.setString(2, recipient);
        stat.executeUpdate();
        stat2.executeUpdate();
        retract_debit.close();
        retract_credit_pay.close();
        System.out.println("Transfer processed");
        //send_mail_transaction(email, amount, name);
    }
}


