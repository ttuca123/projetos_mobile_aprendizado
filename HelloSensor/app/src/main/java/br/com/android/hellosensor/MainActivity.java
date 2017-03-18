package br.com.android.hellosensor;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private SensorManager sensorManager;
    private List<Sensor> sensorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        List<String> nomes = new ArrayList<>();

        for(Sensor sensor: sensorList){

            nomes.add(sensor.getName() + " - "+sensor.getVendor() + " - " + sensor.getType());

        }

        ListView listView = (ListView) findViewById(R.id.listView);

        listView.setOnItemClickListener(this);

        int layout = android.R.layout.simple_list_item_1;

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, layout, nomes);

        listView.setAdapter(adaptador);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

        Sensor sensor = sensorList.get(position);
//        String msg = sensor.getName() + " - " +sensor.getVendor();
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

           Intent intent = new Intent(this, TestSensorActivity.class);
           intent.putExtra("position", position);
           startActivity(intent);
    }
}
