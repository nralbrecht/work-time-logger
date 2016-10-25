package de.nralbrecht.apps.worktimelogger;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private StorageWrapper storageWrapper;
    private CustomAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        storageWrapper = new StorageWrapper(this);

        final ListView entryList = (ListView) this.findViewById(R.id.lvEntryList);

        arrayAdapter = new CustomAdapter(
                this,
                new ArrayList<>());

        entryList.setAdapter(arrayAdapter);
        updateListView();

        final Button button = (Button) findViewById(R.id.btnAdd);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addDate();
                updateListView();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.clear_dates) {
            storageWrapper.clear();
            updateListView();
        }
        return true;
    }

    public void addDate(){
        SimpleDateFormat saveFormatter = new SimpleDateFormat("EEE d.MM H:mm", Locale.GERMAN);
        storageWrapper.addLine(saveFormatter.format(new Date()));
    }

    public void updateListView() {
        ArrayList<String> data = storageWrapper.getAllLines();
        ArrayList<WorkTime> res = new ArrayList<>();

        for (int i = 1; i < data.size() + 1; i += 2) {
            String d0 = data.get(i - 1);
            try {
                String d1 = data.get(i);
                res.add(new WorkTime(d0, d1));
            } catch (Exception e) {
                res.add(new WorkTime(d0));
            }
        }

        arrayAdapter.replaceAll(ArrayHelper.Reverse(res));
        arrayAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();
        storageWrapper.Save();
    }

    @Override
    protected void onResume() {
        super.onResume();
        storageWrapper.Load();
    }
}
