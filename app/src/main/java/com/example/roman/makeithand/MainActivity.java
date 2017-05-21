package com.example.roman.makeithand;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        c = this;
        add_button = (Button) findViewById(R.id.add_button);
        ctrl = new WritingController(this);
        lv = (ListView) findViewById(R.id.main_list_view);
        loading = (ProgressBar) findViewById(R.id.loading);

        dataset = ctrl.getAllWritings();
        aa = new WritingArrayAdapter(this, android.R.layout.simple_list_item_1, dataset);
        lv.setAdapter(aa);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Writing clicked = dataset.get(position);
                Intent intent = new Intent(MainActivity.this, ShowWritingActivity.class);
                intent.putExtra("writing", clicked);
                startActivity(intent);
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Writing writing = dataset.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(c);
                builder.setMessage(R.string.wish_delete);
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ctrl.deleteWriting(writing.id);
                        refeshList();
                    }
                });
                builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                AlertDialog dial = builder.create();
                dial.show();
                return true;
            }
        });

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewWritingActivity.class);
                startActivityForResult(intent, NEWWRITING);
            }
        });
    }

    public void refeshList ()
    {
        dataset = ctrl.getAllWritings();
        aa = new WritingArrayAdapter(this, android.R.layout.simple_list_item_1, dataset);
        lv.setAdapter(aa);
    }

    protected void onActivityResult(int request, int result, Intent data)
    {
        if (request == 1)
        {
            if (result == RESULT_OK)
            {
                String title = data.getStringExtra("title");
                String value = data.getStringExtra("value");
                new HandwritingWorker(this).execute(title, value);
            }
        }
    }

    Button add_button;
    ProgressBar loading;

    ListView lv;
    WritingArrayAdapter aa;
    WritingController ctrl;
    ArrayList<Writing> dataset;
    Context c;

    int NEWWRITING = 1;
}
