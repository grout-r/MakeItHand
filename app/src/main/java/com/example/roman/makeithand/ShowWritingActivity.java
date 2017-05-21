package com.example.roman.makeithand;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.graphics.BitmapCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class ShowWritingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_writing);

        Writing writing = getIntent().getParcelableExtra("writing");
        img = (ImageView) findViewById(R.id.show_img);
        File imgFile = new File(writing.path);
        show_title = (TextView) findViewById(R.id.show_title);
        show_value = (TextView) findViewById(R.id.show_value);
        back_bt = (Button) findViewById(R.id.back_bt);

        back_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        show_title.setText(writing.title);
        show_value.setText(writing.value);

        if (imgFile.exists())
        {
            Bitmap map = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            img.setImageBitmap(map);
        }


    }

    ImageView img;
    TextView show_title;
    TextView show_value;
    Button back_bt;
}
