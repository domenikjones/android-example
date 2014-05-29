package ch.netzbarkeit.reminder.app;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import ch.netzbarkeit.reminder.db.DtoFactory;
import ch.netzbarkeit.reminder.login.Login;
import ch.netzbarkeit.reminder.models.Reminder;


public class MainActivity extends ActionBarActivity {
    
    private Login login = new Login();
    String url = "http://192.168.1.144:8000/api/case/";
    private Integer page = 1;
    private DtoFactory dtoFactory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dtoFactory = (DtoFactory) getApplication();
//        this.updateItemList();
    }


    private void updateItemList() {
        String url_page = url+"?page="+page;
        try {
            InputStream source = retrieveStream(url_page);
            Gson gson = new Gson();
            Reader reader = new InputStreamReader(source);
            SearchResponse response = gson.fromJson(reader, SearchResponse.class);

            Toast.makeText(this, "Total: ", Toast.LENGTH_SHORT).show();

            List<Result> results = response.results;
            appendItemToListView(results);
            page += 1;
        } catch (Exception e) {
            System.out.println("Error getting JSON from URL "+url_page);
        }
    }

    private void appendItemToListView(List<Result> results) {
        ListView listView = (ListView)findViewById(R.id.listView);
//        ArrayList<String> myStringList = new ArrayList<String>();

        // Add the items in to the array
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
        adapter.notifyDataSetChanged();

        for (Result result : results) {
            String string = result.title+"\n"+result.subtitle;
            Reminder _r = null;

            // get object from database and update
            try {
                Dao<Reminder, Integer> rDao = dtoFactory.getReminderDAO();
                _r = rDao.queryForId(result.id);
                if(_r == null) {
                    System.out.println(result.id+" not found in database, create");
                } else {
                    // update item
                    _r.title = result.title;
                    _r.subtitle = result.subtitle;
                    _r.caret_priority = result.caret_priority;
                    // update DAO in database
                    rDao.update(_r);
                }
            } catch (Exception e) {
                System.out.println(e);
            }

            // create new object
            try {
                if(_r == null) {
                    Dao<Reminder, Integer> rDao = dtoFactory.getReminderDAO();
                    System.out.println("Create a new database object: "+string);
                    Reminder r = new Reminder();
                    r.id = result.id;
                    r.title = result.title;
                    r.subtitle = result.subtitle;
                    // create DAO in database
                    rDao.create(r);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        // read database and add items to list
        try {
            Dao<Reminder, Integer> rDao = dtoFactory.getReminderDAO();

            QueryBuilder<Reminder, Integer> reminderQB = rDao.queryBuilder();
            reminderQB.orderBy("caret_priority", false);
            reminderQB.orderBy("id", false);
            List<Reminder> _results = reminderQB.query();

            for(Reminder r : _results) {
                String string = r.title+"\n"+r.subtitle;
                // add item to adapter String
                adapter.add(string);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        // reset list adapter
        listView.setAdapter(null);
        // set adapter
        listView.setAdapter(adapter);

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener {
//            @Override
//            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
//                Toast.makeText(getApplicationContext(),"Click ListItem Number " + position, Toast.LENGTH_LONG).show();
//            }
//        });

    }


    private InputStream retrieveStream(String url) {

        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet getRequest = new HttpGet(url);

        try {

            HttpResponse getResponse = client.execute(getRequest);
            final int statusCode = getResponse.getStatusLine().getStatusCode();

            if (statusCode != HttpStatus.SC_OK) {
                Log.w(getClass().getSimpleName(),
                        "Error " + statusCode + " for URL " + url);
                return null;
            }

            HttpEntity getResponseEntity = getResponse.getEntity();
            return getResponseEntity.getContent();

        }
        catch (Exception e) {
            getRequest.abort();
            Log.w(getClass().getSimpleName(), "Error for URL " + url, e);
        }
        return null;
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
                Toast.makeText(this,  "Information:\nSettings not implemented yet.", Toast.LENGTH_LONG).show();
                bRet = true;
                break;
            case R.id.action_sync:
                Toast.makeText(this,  "Synchronizing...", Toast.LENGTH_LONG).show();
                this.updateItemList();
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
