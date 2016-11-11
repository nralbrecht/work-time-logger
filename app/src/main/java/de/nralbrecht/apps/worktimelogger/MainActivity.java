package de.nralbrecht.apps.worktimelogger;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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

        entryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                WorkTime item = (WorkTime) arrayAdapter.getItem(position);

                Intent intent = new Intent(MainActivity.this, ListDetailActivity.class);
                intent.putExtra("index", position);
                intent.putExtra("startDate", item.getStart());

                if (item.hasEnd()) {
                    intent.putExtra("endDate", item.getEnd());
                }

                startActivityForResult(intent, 0);
            }
        });

        final Button button = (Button) findViewById(R.id.btnAdd);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addDate();
                updateListView();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 1) {
            int index = data.getIntExtra("index", -1);

            if (index != -1) {
                int i1 = (arrayAdapter.getCount() - index - 1) * 2;

                storageWrapper.updateLine(i1, data.getStringExtra("startDate"));

                if (data.hasExtra("endDate")) {
                    storageWrapper.updateLine(i1 + 1, data.getStringExtra("endDate"));
                }

                updateListView();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
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
