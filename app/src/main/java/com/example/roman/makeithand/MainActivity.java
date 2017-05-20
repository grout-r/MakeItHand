package com.example.roman.makeithand;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add_button = (Button) findViewById(R.id.add_button);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewWriting.class);
                startActivityForResult(intent, NEWWRITING);
            }
        });
    }

    protected void onActivityResult(int request, int result, Intent data)
    {
        if (request == 1)
        {
            if (result == RESULT_OK)
            {
                String title = data.getStringExtra("title");
                String value = data.getStringExtra("value");
                new HandwritingWorker().execute(value);
            }
        }
    }
    Button add_button;

    int NEWWRITING = 1;
}
