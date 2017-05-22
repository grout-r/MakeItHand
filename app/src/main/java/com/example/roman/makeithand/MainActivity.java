package com.example.roman.makeithand;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

/**
 * Main activity. Containing a listview representing the database entries and a "new" button.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Classical init of views.
     * Set the OnClickListener and initialise the list with the database data.
     *
     * @param savedInstanceState
     *          Default. Use on super constructor
     */
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
            /**
             * Trigger the view for display a generated writing
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Writing clicked = dataset.get(position);
                Intent intent = new Intent(MainActivity.this, ShowWritingActivity.class);
                intent.putExtra("writing", clicked);
                startActivity(intent);
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            /**
             * Trigger the modal to destroy a created writing
             */
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Writing writing = dataset.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(c);
                builder.setMessage(R.string.wish_delete);
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ctrl.deleteWriting(writing.id);
                        refreshList();
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
            /**
             * Start the activity for a new writing
             */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewWritingActivity.class);
                startActivityForResult(intent, NEW_WRITING);
            }
        });
    }

    /**
     * After database changes, refresh the new with the new data.
     */
    public void refreshList()
    {
        dataset = ctrl.getAllWritings();
        aa = new WritingArrayAdapter(this, android.R.layout.simple_list_item_1, dataset);
        lv.setAdapter(aa);
    }

    /**
     * Handle the ending of a launched activity.
     * For NEW_WRITING -> OK
     *          Get the set title and value from the intend, then launch the Async Task with thoses data
     */
    protected void onActivityResult(int request, int result, Intent data)
    {
        if (request == NEW_WRITING)
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

    int NEW_WRITING = 1;
}
