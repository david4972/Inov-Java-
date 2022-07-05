import java.net.*;
import java.io.*;
import java.nio.charset.*;
import com.google.gson.*;
//import yahoofinance.YahooFinance;
//import yahoofinance.quotes.fx.FxSymbols;
import java.net.URL;
import java.net.HttpURLConnection;
import java.math.*;
import java.sql.*;
//import java.util.Currency;
import java.util.Scanner;

public class exchangeApi {

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


    public void currency_exchange(String base_curr, String convert_curr, int cardcode) throws IOException, SQLException {
        Scanner scan = new Scanner(System.in);
        //Scanner scan2 = new Scanner(System.in);
        Character Line = '/';
        // Setting URL
        String url_str = "https://v6.exchangerate-api.com/v6/2816493d654f23cda23c4ccc/pair/";
        String join = base_curr.concat(String.valueOf(Line)).concat(convert_curr);
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
        BigDecimal req_result = jsonobj.get("conversion_rate").getAsBigDecimal();
        // String res = Double.toString(req_result);
        double res = req_result.doubleValue();
        // System.out.println(res);
        // Account
        System.out.println("Select account");
        System.out.println("1. Debit");
        System.out.println("2. Credit");
        System.out.println("1. International Debit");
        String read_accnt = scan.nextLine();
        if (read_accnt.equals("1")){
            Currency_exchange_Debit(res, cardcode, convert_curr);
        }
        if (read_accnt.equals("2")){
            Currency_exchange_Credit(res, cardcode, convert_curr);
        }
        if (read_accnt.equals("3")){
            Currency_exchange_Debit_International(res, cardcode, convert_curr);
        }
    }

    public void Currency_exchange_Debit(double rate_Debit, int cardcode, String convert_curr) throws SQLException, IOException {
        Statement state = connect().createStatement();
        //String currency = "EUR";
        // find account for requested transaction (credit check)
        String retract_debit = "UPDATE  DEBITInov set CHECKING=CHECKING/?, SAVING=SAVING/?, CURRENCY=?, WHERE CARDNUM=?";
        PreparedStatement stat = connect().prepareStatement(retract_debit);
        stat.setDouble(1, rate_Debit);
        stat.setDouble(2, rate_Debit);
        stat.setString(3, convert_curr);
        stat.setInt(4, cardcode);
        stat.executeUpdate();
        state.close();
        System.out.println("Currency Converted on account");
    }



    public void Currency_exchange_Debit_International(double rate_International_Debit, int cardcode, String convert_curr) throws SQLException, IOException {
        Statement state = connect().createStatement();
        //String currency = "EUR";
        // find account for requested transaction (credit check)
        String retract_debit = "UPDATE  InterDEBITInov set CHECKING=CHECKING/?, SAVING=SAVING/?, CURRENCY=?, WHERE CARDNUM=?";
        PreparedStatement stat = connect().prepareStatement(retract_debit);
        stat.setDouble(1, rate_International_Debit);
        stat.setDouble(2, rate_International_Debit);
        stat.setString(3, convert_curr);
        stat.setInt(4, cardcode);
        stat.executeUpdate();
        state.close();
        System.out.println("Currency Converted on account");
    }

    public void Currency_exchange_Credit(double rate_Credit, int cardcode, String convert_curr) throws SQLException, IOException {
        Statement state = connect().createStatement();
        //String currency = "EUR";
        // find account for requested transaction (credit check)
        String retract_credit = "UPDATE  CREDITInov set CHECKING=CHECKING/?, SAVING=SAVING/?, WHERE CARDNUM=?";
        PreparedStatement stat = connect().prepareStatement(retract_credit);
        stat.setDouble(1, rate_Credit);
        stat.setDouble(2, rate_Credit);
        stat.setString(3, convert_curr);
        stat.setInt(4, cardcode);
        stat.executeUpdate();
        state.close();
        System.out.println("Currency Converted on account");
    }

}
