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

public class InternationalTransactions {

    public Connection connect() {
        // Database connection string
        String url = "jdbc:sqlite:inovproj3.0.db";
        Connection conn = null;
        // Statement state = null;
        try {
            conn = DriverManager.getConnection(url);
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
        send_money_debit_international(cardcode, res, name, base_curr);
    }

    public void send_money_debit_international(int cardcode, double amount, String recipient, String Country) throws SQLException {
        String debit_transfer = "SELECT * FROM InterDEBITInov WHERE CARDNUM=?";
        // String credit_transfer = "SELECT * FROM CREDIT WHERE CARD-CODE=?";
        Statement state = connect().createStatement();
        ResultSet retract_debit = state.executeQuery(debit_transfer);
        String name = retract_debit.getString("NAME");
        // collect valued amount to be sent to recipient (debit check)
        int card_code = retract_debit.getInt("CARDNUM");
        if (card_code == cardcode) {
            String retrieve = "UPDATE InterDEBITInov set CHECKING=CHECKING-? WHERE CARDNUM=?";
            PreparedStatement stat = connect().prepareStatement(retrieve);
            stat.setDouble(1, amount);
            stat.setInt(2, cardcode);
            String accept1 = "SELECT * FROM InterDEBITInov  WHERE NAME=?";
            ResultSet retract_credit_pay = state.executeQuery(accept1);
            String email = retract_credit_pay.getString("EMAIL");
            String accept = "UPDATE InterDEBITInov set CHECKING=CHECKING+? WHERE NAME=?";
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
        String debit_transfer = "SELECT * FROM InterDEBITInov WHERE CARDNUM=?";
        // String credit_transfer = "SELECT * FROM CREDIT WHERE CARD-CODE=?";
        Statement state = connect().createStatement();
        ResultSet retract_debit = state.executeQuery(debit_transfer);
        String name = retract_debit.getString("NAME");
        int card_code = retract_debit.getInt("CARDNUM");
        if (card_code == cardcode) {
            String retrieve = "UPDATE InterDEBITInov set CHECKING=CHECKING-? WHERE CARDNUM=?";
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
        String debit_transfer = "SELECT * FROM InterDEBITInov WHERE CARDNUM=?";
        // String credit_transfer = "SELECT * FROM CREDIT WHERE CARD-CODE=?";
        Statement state = connect().createStatement();
        ResultSet retract_debit = state.executeQuery(debit_transfer);
        String name = retract_debit.getString("NAME");
        // collect valued amount to be sent to recipient (debit check)
        String retrieve = "UPDATE InterDEBITInov set CHECKING=CHECKING-? WHERE CARDNUM=?";
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
}
