package br.com.zenus.main;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import br.com.zenus.prazercity.R;

public class SobreActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);

       ActionBar actionBar = getSupportActionBar();


            if (actionBar != null) {

                actionBar.setTitle("Sobre");

            actionBar.setDisplayHomeAsUpEnabled(true);
        }




    }
}
