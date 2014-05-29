package ch.netzbarkeit.reminder.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ch.netzbarkeit.reminder.login.Login;

public class LoginActivity extends Activity {

    private Login login = new Login();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // check login state
        if(this.login.getLoginState()){
//            alertInformation("Logged in");
            // return to dashboard
            toDashboard();
        } else {
//            alertInformation("Not logged in");
            // proceed login view
        }

        // login button is being clicked
        Button b = (Button)findViewById(R.id.buttonLogin);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginButtonListener();
            }
        });
    }





    protected void loginButtonListener() {
        // first check login and redirect
        if(this.login.getLoginState()) {
            toDashboard();
        } else {
//            alertInformation("Before login not logged in.");
        }

        EditText u = (EditText)findViewById(R.id.textEditUsername);
        EditText p = (EditText)findViewById(R.id.textEditPassword);

        // convert to strings
        String us = u.getText().toString();
        String ps = p.getText().toString();

        // @ToDo: implement a function that checks string length
//        alertInformation("Username: " + us + " || Password: " + ps);

        // do login
        if(this.login.doLogin(us, ps)){
            alertInformation("Login successful.");
            toDashboard();
        } else {
            alertInformation("Login failed. Please try again.");
        }
    }



    private void toDashboard(){
//        alertInformation("Redirect to dashboard after logged in");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }






    protected void alertInformation(String string) {
        Toast.makeText(this, string , Toast.LENGTH_SHORT).show();
        System.out.println("[ALERT INFORMATION] " + string);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //set true is menu selection handled
        boolean bRet=false;

        switch (item.getItemId()) {
            case R.id.action_quit:
                this.login.doLogout();
                finish();
                break;
            case R.id.action_settings:
                Toast.makeText(this, "Information:\nSettings not implemented yet.", Toast.LENGTH_LONG).show();
                bRet = true;
                break;
            case R.id.action_about:
                Toast.makeText(this,  "Made by Netzbarkeit GmbH\ncopyright 2014\ninfo@netzbarkeit.ch", Toast.LENGTH_LONG).show();
                bRet = true;
                return bRet;
            default:
                bRet=super.onOptionsItemSelected(item);
        }
        return bRet;
    }

}
