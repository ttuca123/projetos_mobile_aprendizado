package br.com.zenus.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import br.com.zenus.prazercity.R;

/**
 * Created by Tuca on 30/06/2017.
 */

public class CustomDialog extends Dialog implements View.OnClickListener {


    public Activity activity;
    public Dialog dialog;
    public Button yes;
    public Button  no;

    public CustomDialog(Activity activity) {

        super(activity);
        this.activity = activity;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.alert_gps);
        yes = (Button) findViewById(R.id.btnAtivarGPS);
        no = (Button) findViewById(R.id.btnCancelarGPS);


        yes.setOnClickListener(this);
        no.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAtivarGPS:

                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);

                activity.startActivity(intent);
                activity.finish();
                break;
            case R.id.btnCancelarGPS:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }


}
