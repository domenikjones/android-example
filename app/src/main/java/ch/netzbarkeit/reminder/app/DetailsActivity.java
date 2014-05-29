package ch.netzbarkeit.reminder.app;

import android.app.Activity;
import android.os.Bundle;

import ch.netzbarkeit.reminder.login.Login;

/**
 * Created by djones on 25/05/14.
 */
public class DetailsActivity extends Activity {

    private Login login = new Login();
    private long _id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

}
