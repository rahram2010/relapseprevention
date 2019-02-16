package drugapp.myapplication;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpPostRequest implements Runnable {

    private String name = "Drew";

    // Defining run() method - required for implementing Runnable interface
    public void run() {
        executePost();
    }

    // Executes POST request using urlParameters, to targetURL
    private String executePost() {

        StringBuilder sb = new StringBuilder();

        try {


            String params = "name=" + ConfirmName.name + "&num=" + Confirmation.phonenum;

            URL url = new URL("http://50.112.71.162:1337/sms");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            //connection.setRequestProperty("Accept-Charset", "UTF-8");
            //connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

//            connection.setRequestProperty("Content-Length",
//                    String.valueOf(testparam.getBytes().length)); // switch out testparam for urlParameters
//            connection.setRequestProperty("Content-Language", "en-US");


            connection.connect();

            //Send request
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(params);
            writer.flush();
            writer.close();

            int HttpResult = connection.getResponseCode();

            if (HttpResult == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        connection.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
            }

        } catch (Exception e) {

        }

        return null;
    }
}
