package movierater;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author miro
 */
public class ImdbApiCall {

    private static String getWebData(String url) {
        InputStreamReader in = null;
        String result = null;
        try {
            URL page = new URL(url);
            StringBuffer text = new StringBuffer();
            HttpURLConnection conn = (HttpURLConnection) page.openConnection();
            conn.addRequestProperty("User-Agent", "Mozilla/4.76");
            conn.connect();
            in = new InputStreamReader(conn.getInputStream());
            BufferedReader buff = new BufferedReader(in);
            String line = buff.readLine();
            while (line != null) {
                text.append(line + "\n");
                line = buff.readLine();
            }
            result = text.toString();
        } catch (MalformedURLException ex) {
            Logger.getLogger(ImdbApiCall.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ImdbApiCall.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
            } catch (IOException ioe) {
                // just going to ignore this one
            }

        }
        return result;
    }

    private static String getRatings(String url) {
        String page = getWebData(url);
        return RegTest.getRating(page);
    }

    public static String getMovieData(String name, String year) {
        String res="";

        String url = "http://www.deanclatworthy.com/imdb/?";
        name = name.replaceAll(" ", "+");
        if (year != null) {
            url += "q=" + name + "&year=" + year;
        } else {
            url += "q=" + name + "&yg=0";
        }

        String data = getWebData(url);
        if(data.contains("ERROR"))return null;
        try {
            JSONObject jo = new JSONObject(data);
            res = jo.getString("title").replaceAll(" ", ".")+"["+jo.getString("year")+"]_"+getRatings(jo.getString("imdburl"))+"_"+jo.getString("genres");
        } catch (JSONException ex) {
            System.err.println(ex.getMessage());
            return null;
        }
        return res;
    }
}
