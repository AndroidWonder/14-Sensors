package com.course.example.sensors;

import android.hardware.SensorEventListener;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.widget.TextView;
import android.hardware.SensorManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private final String tag = "SENSOR";
    private SensorManager sm = null;

    private List<Sensor> list;
    private TextView sensors=null;

    TextView xViewA = null;
    TextView yViewA = null;
    TextView zViewA = null;
    TextView xViewO = null;
    TextView yViewO = null;
    TextView zViewO = null;
    TextView tViewT = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensors = (TextView)findViewById(R.id.sensors);

        //create a sensor manager object
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);

        //write list of all available sensors and some attributes to the log
        list = sm.getSensorList(Sensor.TYPE_ALL);
        for (Sensor s : list) {
            Log.e(tag, s.toString());
            String name = s.getName();
            int type = s.getType();
            float range = s.getMaximumRange();
            String str = String.format("%12s    %4d  %10.2f", name, type, range);
            sensors.append("\n" + str);
        }

        xViewA = (TextView) findViewById(R.id.xbox);
        yViewA = (TextView) findViewById(R.id.ybox);
        zViewA = (TextView) findViewById(R.id.zbox);
        xViewO = (TextView) findViewById(R.id.xboxo);
        yViewO = (TextView) findViewById(R.id.yboxo);
        zViewO = (TextView) findViewById(R.id.zboxo);
        tViewT = (TextView) findViewById(R.id.xboxt);

    }

    //listener for changes in sensor values
    public void onSensorChanged(SensorEvent event) {

        //get information about sensor
        int type = event.sensor.getType();

        if (type == Sensor.TYPE_GYROSCOPE) {
            xViewO.setText("Rotation X: " + event.values[0]);
            yViewO.setText("Rotation Y: " + event.values[1]);
            zViewO.setText("Rotation Z: " + event.values[2]);
        }
        if (type == Sensor.TYPE_ACCELEROMETER) {
            xViewA.setText("Accel X: " + event.values[0]);
            yViewA.setText("Accel Y: " + event.values[1]);
            zViewA.setText("Accel Z: " + event.values[2]);
        }

        if (type == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            tViewT.setText("Temperature: " + event.values[0]);
        }

    }

    //listener for changes in sensor accuracy
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.e(tag, "onAccuracyChanged: " + sensor + ", accuracy: " + accuracy);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //register listeners
        sm.registerListener(this,
                sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE),
                SensorManager.SENSOR_DELAY_NORMAL);

        sm.registerListener(this,
                sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);

        sm.registerListener(this,
                sm.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onStop() {
        super.onStop();
        sm.unregisterListener(this);

    }
}