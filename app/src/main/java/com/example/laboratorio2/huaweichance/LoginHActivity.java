package com.example.laboratorio2.huaweichance;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import java.util.ArrayList;



public class LoginHActivity extends Activity {
    EditText txtUsername;
    EditText txtPass;
    Button btnLogin;
    TextView txtRegister;
    String response = null;
    TextView txt_Error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_h);

        txtUsername = (EditText)this.findViewById(R.id.txtUsername);
        txtPass = (EditText)this.findViewById(R.id.txtPass);
        btnLogin = (Button)this.findViewById(R.id.send);
        txt_Error = (TextView)this.findViewById(R.id.textView_Error);

        btnLogin.setOnClickListener(new OnClickListener(){
            public void onClick(View v){

                String uname = txtUsername.getText().toString();
                String pwd = txtPass.getText().toString();
                validateUserTask task = new validateUserTask();
                task.execute(new String[]{uname, pwd});
            }
        });
        txtRegister = (TextView)this.findViewById(R.id.txtRegister);
        txtRegister.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itemIntent = new Intent (LoginHActivity.this,RegisterActivity.class);
                LoginHActivity.this.startActivity(itemIntent);
                finish();

            }
        });

    }

    private class validateUserTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
            postParameters.add(new BasicNameValuePair("email", params[0] ));
            postParameters.add(new BasicNameValuePair("password", params[1] ));
            String res = null;
            try {
                response = CustomHttpClient.executeHttpPost("http://ing-sis.jairoesc.com/sessions", postParameters);
                res=response.toString();
                res= res.replaceAll("\\s+","");
            }
            catch (Exception e) {
                txt_Error.setText(e.toString());
            }
            return res;
        }//close doInBackground

        @Override
        protected void onPostExecute(String result) {
            if(result.equals("1")){
                //navigate to Main Menu
                Intent listview = new Intent(LoginHActivity.this,ListChanceActivity.class);
                LoginHActivity.this.startActivity(listview);
            }
            else{
                txt_Error.setText("Sorry!! Incorrect Username or Password");
            }
        }//close onPostExecute
    }// close validateUserTask


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login_h, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
