package co.example.hzq.jokertwo.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import co.example.hzq.jokertwo.R;

public class DetailPageActivity extends AppCompatActivity {

    private static final String TAG = "DetailPageActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_page);

        Log.e(TAG, "onCreate: "+getIntent().getStringExtra("image") );

        ImageView imageView = (ImageView) findViewById(R.id.detail_imageView);
        imageView.setImageResource(Integer.valueOf(getIntent().getStringExtra("image")));

    }
}
