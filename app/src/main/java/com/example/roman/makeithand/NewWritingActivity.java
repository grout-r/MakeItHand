package com.example.roman.makeithand;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Activity for creating a writing.
 */
public class NewWritingActivity extends AppCompatActivity {

    /**
     * Classic behavior. Set the OnClickListener.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_writing);

        c = this;
        cancel_button = (Button) findViewById(R.id.cancel_button);
        save_button = (Button) findViewById(R.id.save_button);
        et_title = (EditText) findViewById(R.id.et_title);
        et_value = (EditText) findViewById(R.id.et_value);


        cancel_button.setOnClickListener(new View.OnClickListener() {
            /**
             * Do nothing and finish the activity
             */
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        save_button.setOnClickListener(new View.OnClickListener() {
            /**
             * Check that all the fields are OK and set the intent with the data to get the data back in the MainActivity.
             */
            @Override
            public void onClick(View v) {
                String title = et_title.getText().toString();
                String value = et_value.getText().toString();
                if (title.isEmpty() || value.isEmpty())
                {
                    Toast.makeText(c, "Please fill all the fields", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("title", title);
                intent.putExtra("value", value);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    Button cancel_button;
    Button save_button;
    EditText et_title;
    EditText et_value;

    Context c;
}
