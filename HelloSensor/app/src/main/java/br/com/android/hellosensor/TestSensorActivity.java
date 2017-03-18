package br.com.android.hellosensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class TestSensorActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor sensor;
    private TextView tValor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_sensor);

        int position = getIntent().getIntExtra("position", 0);
        //Busca o sensor pela posição
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);

        sensor = sensorList.get(position);

        tValor = (TextView) findViewById(R.id.tView);
        getSupportActionBar().setTitle(sensor.getName());

    }

    @Override
    protected void onResume() {
        super.onResume();

        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();

        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {


        StringBuffer sb = new StringBuffer();
        for(int i=0; i< sensorEvent.values.length; i++){
            sb.append(i).append(": ").append(sensorEvent.values[i]).append("\n");
        }
        tValor.setText(sb.toString());
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
