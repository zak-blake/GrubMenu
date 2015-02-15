
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class WebUtils {

    public static final int BASIC_TIMEOUT = 60 * 1000;        // 60 seconds
    public static final int DOWNLOAD_TIMEOUT = 5 * 60 * 1000; // 5 minutes
    public final static Logger logger = Logger.getLogger("WebUtils");


    public static void main(String [] args) throws Exception {

        //Make API Call
        WebUtils w = new WebUtils();
        String ur = "http://gauchogrub.azurewebsites.net/api/Menus?diningCommon=Ortega&date=02/17/2015";
        URL url = new URL(ur);
        String result = w.httpRequest(url,HttpMethod.GET,100000);

        //FILE I/O
        File statText = new File("OrtegaTestMenu.json");
        FileOutputStream is = new FileOutputStream(statText);
        OutputStreamWriter osw = new OutputStreamWriter(is);    
        Writer w2 = new BufferedWriter(osw);
        w2.write(result);
        w2.close();

    }


    /* A synchronous method that performs an HTTP request returning data received from the sever as a String */
    public String httpRequest(URL url, HttpMethod method, int timeout) throws IOException {
        return httpRequest(url, method, timeout, new Hashtable<String, String>());
    }

    public String httpRequest(URL url, HttpMethod method, int timeout, Hashtable<String, String> headers) throws IOException {
        log(url);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        if (headers != null) {
            for (String key : headers.keySet()) {
                connection.setRequestProperty(key, headers.get(key));
            }
        }
        connection.setRequestMethod(method.toString());
        connection.setConnectTimeout(timeout);
        connection.setReadTimeout(timeout);
        connection.connect();

        return readResponse(connection);
    }

    /* Used exclusively by updatePassword method */
    public String httpRequest(URL url, HttpMethod method, int timeout, String requestBody) throws IOException {
        byte[] byteArray = requestBody.getBytes("UTF-8");
        InputStream inputStream = new ByteArrayInputStream(byteArray);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        return uploadData(url, bufferedInputStream, "password", byteArray.length);
    }

    /* Uploads data in the body of HTTP request */
    public String uploadData(URL url, BufferedInputStream inputStream, String contentType, long length) throws IOException {
        log(url);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(HttpMethod.POST.toString());
        connection.setRequestProperty("Content-Type", contentType);
        connection.setRequestProperty("Content-Length", Long.toString(length));
        connection.setFixedLengthStreamingMode(length);
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.connect();

        BufferedOutputStream outputStream = new BufferedOutputStream(connection.getOutputStream());

        // Copy contents to output stream
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, len);
        }
        outputStream.flush();
        outputStream.close();

        return readResponse(connection);
    }

    /* Helper method */
    private String readResponse(HttpURLConnection connection) throws IOException {
        int responseCode = connection.getResponseCode();
        if (responseCode == 200 || responseCode == 204) {
            String response = readInputStream(connection.getInputStream());
            connection.disconnect();
            return response;
        } else {
            throw new RuntimeException("Bad request");
        }
    }

    /* Reads data (String) from an input stream */
    private String readInputStream(InputStream stream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuffer response = new StringBuffer();
        String inputLine;
        while ((inputLine = reader.readLine()) != null) {
            response.append(inputLine);
        }
        reader.close();
        return response.toString();
    }

    /* Logs the URL */
    private void log(URL url) {
        logger.log(Level.INFO, url.toString());
    }

    public enum HttpMethod {
        GET, POST, DELETE, PUT
    }
}