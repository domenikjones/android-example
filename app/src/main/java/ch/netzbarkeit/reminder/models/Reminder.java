package ch.netzbarkeit.reminder.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by djones on 26/05/14.
 */

@DatabaseTable(tableName = "reminder")
public class Reminder {

    // generatedId set to false, because we will get it from the server!
    @DatabaseField(id = true, generatedId = false) public int id;
    @DatabaseField public String title;
    @DatabaseField public String subtitle;
    @DatabaseField public Integer caret_priority;

    public Reminder() { /* Default constructor */ }
}
