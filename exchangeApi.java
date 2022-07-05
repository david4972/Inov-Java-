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
        double res = req_result.doubleValue();
        // Account
        System.out.println("Select account");
        System.out.println("1. Debit");
        System.out.println("2. Credit");
        System.out.println("3. International Debit");
        String read_accnt = scan.nextLine();
        if (read_accnt.equals("1")) {
            Currency_exchange_Debit(res, cardcode, convert_curr);
        }
        if (read_accnt.equals("2")) {
            Currency_exchange_Credit(res, cardcode, convert_curr);
        }
        if (read_accnt.equals("3")) {
            Currency_exchange_Debit_International(res, cardcode, convert_curr);
        }
    }

    public void Currency_exchange_Debit(double rate_Debit, int cardcode, String convert_curr) throws SQLException, IOException {
        PreparedStatement stat2 = connect().prepareStatement("SELECT * FROM InovDEBIT WHERE CARDNUM=?");
        stat2.setInt(1, cardcode);
        ResultSet retract_credit = stat2.executeQuery();
        while (retract_credit.next()) {
            double checking_balance = retract_credit.getDouble("CHECKING");
            double rate_chec = checking_balance * rate_Debit;
            double saving_balance = retract_credit.getDouble("SAVING");
            double rate_sav = saving_balance * rate_Debit;
            System.out.println("Currency Conversion processing");
            echange_debit(cardcode, convert_curr, rate_chec, rate_sav);

        }
    }

    public void echange_debit(int cardcode, String convert_curr, double rate_chec, double rate_sav) throws SQLException {
        String credit_sql = "SELECT * FROM InovDEBIT WHERE CARDNUM=?";
        Statement state = connect().createStatement();
        // Debit account check
        ResultSet retract_credit = state.executeQuery(credit_sql);
        // String name = retract_debit.getString("NAME");
        //String email = retract_credit.getString("EMAIL");
        // transaction processing
        String credit_data = "UPDATE InovDEBIT set CHECKING=?, SAVING=?, CURRENCY=? WHERE CARDNUM=?";
        PreparedStatement stat = connect().prepareStatement(credit_data);
        stat.setDouble(1, rate_chec);
        stat.setDouble(2, rate_sav);
        stat.setString(3, convert_curr);
        stat.setInt(4, cardcode);
        //stat.executeUpdate();
        retract_credit.close(); // transaction complete
        //state.close();
        connect().close();
        System.out.println("Conversion successful");
        //send_mail_deposit_Checking(email, deposit);

    }


    public void Currency_exchange_Debit_International(double rate_International_Debit, int cardcode, String convert_curr) throws SQLException, IOException {
        PreparedStatement stat2 = connect().prepareStatement("SELECT * FROM InovDEBIT WHERE CARDNUM=?");
        stat2.setInt(1, cardcode);
        ResultSet retract_credit = stat2.executeQuery();
        while (retract_credit.next()) {
            double checking_balance = retract_credit.getDouble("CHECKING");
            double rate_chec = checking_balance * rate_International_Debit;
            double saving_balance = retract_credit.getDouble("SAVING");
            double rate_sav = saving_balance * rate_International_Debit;
            System.out.println("Currency Conversion processing");
            echange_Debit_International(cardcode, convert_curr, rate_chec, rate_sav);

        }
    }

    public void echange_Debit_International(int cardcode, String convert_curr, double rate_chec, double rate_sav) throws SQLException {
        String credit_sql = "SELECT * FROM InovDEBIT WHERE CARDNUM=?";
        Statement state = connect().createStatement();
        // Debit account check
        ResultSet retract_credit = state.executeQuery(credit_sql);
        // String name = retract_debit.getString("NAME");
        //String email = retract_credit.getString("EMAIL");
        // transaction processing
        String credit_data = "UPDATE InovDEBIT set CHECKING=?, SAVING=?, CURRENCY=? WHERE CARDNUM=?";
        PreparedStatement stat = connect().prepareStatement(credit_data);
        stat.setDouble(1, rate_chec);
        stat.setDouble(2, rate_sav);
        stat.setString(3, convert_curr);
        stat.setInt(4, cardcode);
        //stat.executeUpdate();
        retract_credit.close(); // transaction complete
        //state.close();
        connect().close();
        System.out.println("Conversion successful");
        //send_mail_deposit_Checking(email, deposit);

    }

    public void Currency_exchange_Credit(double rate_Credit, int cardcode, String convert_curr) throws SQLException, IOException {
        PreparedStatement stat2 = connect().prepareStatement("SELECT * FROM InovCREDIT WHERE CARDNUM=?");
        stat2.setInt(1, cardcode);
        ResultSet retract_credit = stat2.executeQuery();
        while (retract_credit.next()) {
            double checking_balance = retract_credit.getDouble("CHECKING");
            double rate_chec = checking_balance * rate_Credit;
            double saving_balance = retract_credit.getDouble("SAVING");
            double rate_sav = saving_balance * rate_Credit;
            System.out.println("Currency Conversion processing");
            echange_credit(cardcode, convert_curr, rate_chec, rate_sav);

        }
    }

    public void echange_credit(int cardcode, String convert_curr, double rate_chec, double rate_sav) throws SQLException {
        String debit_transfer = "SELECT * FROM InovCREDIT WHERE CARDNUM=?";
        // String credit_transfer = "SELECT * FROM CREDIT WHERE CARD-CODE=?";
        PreparedStatement state = connect().prepareStatement(debit_transfer);
        state.setInt(1,cardcode);
        //String name = retract_debit.getString("NAME");
        // collect valued amount to be sent to recipient (debit check)
        ResultSet retract_credit = state.executeQuery();
        // int card_code = retract_debit.getInt("CARDNUM");
        if (retract_credit.next()) {
            //String retrieve = "UPDATE InovCREDIT set CHECKING=CHECKING*?, SAVING=SAVING*?, CURRENCY=? WHERE CARDNUM=?";
            PreparedStatement stat = connect().prepareStatement("UPDATE InovCREDIT set CHECKING=CHECKING*?, SAVING=SAVING*?, CURRENCY=? WHERE CARDNUM=?");
            stat.setDouble(1, rate_chec);
            stat.setDouble(2, rate_sav);
            stat.setString(3, convert_curr);
            stat.setInt(4, cardcode);
            System.out.println("Conversion successful");
            //send_mail_deposit_Checking(email, deposit);
        }
    }

}
