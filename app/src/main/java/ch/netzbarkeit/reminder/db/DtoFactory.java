package ch.netzbarkeit.reminder.db;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import ch.netzbarkeit.reminder.models.Reminder;

public class DtoFactory extends Application {

    private SharedPreferences preferences;
    private DatabaseHelper databaseHelper = null;

    private Dao<Reminder, Integer> reminderDAO = null;

    @Override
    public void onCreate() {
        super.onCreate();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        databaseHelper = new DatabaseHelper(this);
    }

//    public File getVideosDir() {return videos_dir;}
    public SharedPreferences getPreferences() {
        return preferences;
    }

    public Dao<Reminder, Integer> getReminderDAO() throws SQLException {
        if (reminderDAO == null) {
            reminderDAO = databaseHelper.getDao(Reminder.class);
        }
        return reminderDAO;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }
}