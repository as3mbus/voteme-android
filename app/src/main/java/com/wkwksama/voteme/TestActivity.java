package com.wkwksama.voteme;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        this.findViewById(R.id.queryButton).setOnClickListener(new QueryButtonListener());
    }
    protected class QueryButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            new RetrieveFeedTask().execute();
        }
    }

    class RetrieveFeedTask extends AsyncTask<Void, Void, String> {

        private Exception exception;

        protected void onPreExecute() {
            ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
            TextView responseView = (TextView) findViewById(R.id.responseView);

            responseView.setText("");
        }

        protected String doInBackground(Void... urls) {
            EditText emailText= (EditText) findViewById(R.id.emailText);
            String email = emailText.getText().toString();
            // Do some validation here

            try {
                URL url = new URL("http://voting.o-por.com/index.php/calon");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    stringBuilder.append("[");
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    bufferedReader.close();
                    stringBuilder.append("]");
                    return stringBuilder.toString();
                }
                finally{
                    urlConnection.disconnect();
                }
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String response) {
            if(response == null) {
                response = "THERE WAS AN ERROR";
            }
            ProgressBar progressBar= (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.GONE);
            Log.i("INFO", response);
            TextView responseView = (TextView) findViewById(R.id.responseView);
            String name="nama";
            try {
                JSONArray jsonarray = new JSONArray(response);
//                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject jsonobject = jsonarray.getJSONObject(0);
                    name = jsonobject.getString("nama");

                    responseView.setText(name);
//                }



            } catch (JSONException e) {
                // Appropriate error handling code
                responseView.setText(response);
            }

        }
    }

}
