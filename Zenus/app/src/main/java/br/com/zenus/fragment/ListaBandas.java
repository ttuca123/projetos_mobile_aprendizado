package br.com.zenus.fragment;

import android.app.TabActivity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.TabHost;

/**
 * Created by Tuca on 14/03/2017.
 */

public class ListaBandas extends TabActivity {


    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        TabHost.TabSpec descritor = getTabHost().newTabSpec("tag1");

    }
}
