package br.com.android.video;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String url = "https://www.youtube.com/watch?v=js8pCCxcVuc";
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "video/*");
        getBaseContext().startActivity(intent);
    }
}
