package com.peleg.runserver;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        RUN myThread = new RUN();
        myThread.start();

        final int id = 16352775;


    }


        public class RUN extends Thread {
        public void run() {
            URL obj;
            try {
                obj = new URL("https://www.pythonanywhere.com/api/v0/user/elikopeleg//consoles/16352775/send_input/");

            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setRequestProperty ("Authorization", "Token 242fa8569f24430b576c163b70545297a0652117");

            // For POST only - START
            con.setDoOutput(true);
            OutputStream os = con.getOutputStream();
            os.write("input=python3 chordServer.py \n".getBytes());
            os.flush();
            os.close();
            // For POST only - END

            int responseCode = con.getResponseCode();
            System.out.println("POST Response Code :: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) { //success
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // print result
                System.out.println(response.toString());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "success!", Toast.LENGTH_LONG).show();
                    }
                });
                Thread.sleep(Toast.LENGTH_LONG); // As I am using LENGTH_LONG in Toast
                MainActivity.this.finish();


            } else {
                System.out.println("POST request not worked");

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "POST request not worked", Toast.LENGTH_LONG).show();
                    }
                });
                Thread.sleep(Toast.LENGTH_LONG); // As I am using LENGTH_LONG in Toast
                MainActivity.this.finish();
            }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }



        /*public void run(){
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost httpPost;
            ArrayList<NameValuePair> postParameters;
            httpPost = new HttpPost("https://www.pythonanywhere.com/api/v0/user/elikopeleg//consoles/16352775/send_input/");

            httpPost.addHeader("Authorization", "Token 242fa8569f24430b576c163b70545297a0652117");
            postParameters = new ArrayList<NameValuePair>();
            postParameters.add(new BasicNameValuePair("input", "python3 chordServer.py \n"));

            try {
                httpPost.setEntity(new UrlEncodedFormEntity(postParameters, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            HttpResponse response = null;
            try {
                response = client.execute(httpPost);
            } catch (IOException e) {
                e.printStackTrace();
            }
            HttpEntity entity = response.getEntity();

            //try {
//                HttpResponse response = httpclient.execute(httpPost);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

    }
*/
    }

}
