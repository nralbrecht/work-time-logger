package de.nralbrecht.apps.worktimelogger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ListDetailActivity extends AppCompatActivity {
    private int index = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_detail);

        Intent intent = getIntent();

        final WorkTime editTime;

        if (intent.hasExtra("endDate")) {
            editTime = new WorkTime(
                    intent.getStringExtra("startDate"),
                    intent.getStringExtra("endDate"));
        } else {
            editTime = new WorkTime(
                    intent.getStringExtra("startDate"));
        }

        index = intent.getIntExtra("index", -1);

        final EditText startEdit = (EditText) this.findViewById(R.id.editStart);
        startEdit.setText(editTime.getStart());

        final EditText endEdit = (EditText) this.findViewById(R.id.editEnd);
        endEdit.setText(editTime.getEnd());

        if (!editTime.hasEnd()) {
            endEdit.setEnabled(false);
            endEdit.setFocusable(false);
        }

        Button okBtn = (Button) this.findViewById(R.id.next);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTime.setStart(startEdit.getText().toString());

                if (editTime.hasEnd()) {
                    editTime.setEnd(endEdit.getText().toString());
                }

                finishWithResult(1, editTime);
            }
        });

        Button cancelBtn = (Button) findViewById(R.id.cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishWithResult(0, editTime);
            }
        });
    }

    public void finishWithResult(int resultCode, WorkTime editTime)
    {
        Intent data = new Intent();
        data.putExtra("index", index);
        data.putExtra("startDate", editTime.getStart());

        if (editTime.hasEnd()) {
            data.putExtra("endDate", editTime.getEnd());
        }

        setResult(resultCode, data);
        finish();
    }
}
