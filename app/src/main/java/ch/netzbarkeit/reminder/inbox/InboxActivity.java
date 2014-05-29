package ch.netzbarkeit.reminder.inbox;

import android.app.ListActivity;
import android.os.Bundle;

import ch.netzbarkeit.reminder.app.R;

public class InboxActivity extends ListActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inbox_list);
    }
}