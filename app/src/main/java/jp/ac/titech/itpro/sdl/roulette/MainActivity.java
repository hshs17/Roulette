package jp.ac.titech.itpro.sdl.roulette;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private final static String TAG = MainActivity.class.getSimpleName();

    private final static String EXTRA1 = "label1";
    private final static String EXTRA2 = "label2";
    private final static String EXTRA3 = "label3";
    private final static String EXTRA4 = "label4";
    private final static String EXTRA5 = "label5";
    private final static String EXTRA6 = "label6";
    private final static String EXTRA7 = "label7";
    private final static String EXTRA8 = "label8";
    private final static String EXTRA9 = "label9";
    private final static String EXTRA10 = "label10";

    private final static int REQ_EDIT = 1234;

    private RouletteView rouletteView;
    private ImageView photoView;
    private TextView resultTextView;

    private SensorManager manager;
    private Sensor sensor;

    private float timestamp = 0;
    private float direction = 0;
    private float direction_t = 0;
    private int split = 6;
    private int splitMax = 10;
    private int splitMin = 4;
    private Map<Integer, String> label = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rouletteView = findViewById(R.id.roulette_view);
        photoView = findViewById(R.id.photo_view);
        resultTextView = findViewById(R.id.result_text);

        for(int i=0; i<10; i++) {
            label.put(i, Integer.toString(i+1));
        }

        manager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (manager == null) {
            Toast.makeText(this, "No sensor manager available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        sensor = manager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        if (sensor == null) {
            Toast.makeText(this, "No gyroscope available", Toast.LENGTH_LONG).show();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        manager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        manager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float data = event.values[2];
        float dt = (event.timestamp - timestamp) / 1000000000;
        timestamp = event.timestamp;
        direction_t += data * dt;
        direction = direction_t * 180 / (float)Math.PI;
        Log.d(TAG, Float.toString(direction));
        rouletteView.setDirection(direction);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_label) {
            Intent intent = new Intent(MainActivity.this, EditActivity.class);
            startActivityForResult(intent, REQ_EDIT);
        }
        return true;
    }

    @Override
    protected void onActivityResult(int reqCode, int resCode, Intent data) {
        Log.d(TAG, "getResult");
        super.onActivityResult(reqCode, resCode, data);
        if (reqCode == REQ_EDIT &&  resCode == RESULT_OK) {
            String new_label;

            new_label = data.getStringExtra(EXTRA1);
            if (new_label != null && !new_label.isEmpty()) {
                label.put(0, new_label);
            }
            new_label = data.getStringExtra(EXTRA2);
            if (new_label != null && !new_label.isEmpty()) {
                label.put(1, new_label);
            }
            new_label = data.getStringExtra(EXTRA3);
            if (new_label != null && !new_label.isEmpty()) {
                label.put(2, new_label);
            }
            new_label = data.getStringExtra(EXTRA4);
            if (new_label != null && !new_label.isEmpty()) {
                label.put(3, new_label);
            }
            new_label = data.getStringExtra(EXTRA5);
            if (new_label != null && !new_label.isEmpty()) {
                label.put(4, new_label);
            }
            new_label = data.getStringExtra(EXTRA6);
            if (new_label != null && !new_label.isEmpty()) {
                label.put(5, new_label);
            }
            new_label = data.getStringExtra(EXTRA7);
            if (new_label != null && !new_label.isEmpty()) {
                label.put(6, new_label);
            }
            new_label = data.getStringExtra(EXTRA8);
            if (new_label != null && !new_label.isEmpty()) {
                label.put(7, new_label);
            }
            new_label = data.getStringExtra(EXTRA9);
            if (new_label != null && !new_label.isEmpty()) {
                label.put(8, new_label);
            }
            new_label = data.getStringExtra(EXTRA10);
            if (new_label != null && !new_label.isEmpty()) {
                label.put(9, new_label);
            }
            rouletteView.setMap(label);
        }
    }

    public void onClickReset(View v) {
        direction = 0;
        for(int i=0; i<10; i++) {
            label.put(i, Integer.toString(i+1));
        }
        rouletteView.setDirection(direction);
        rouletteView.setSplit(split);
        rouletteView.setMap(label);
        rouletteView.setVisibility(View.VISIBLE);
        photoView.setVisibility(View.INVISIBLE);
        resultTextView.setVisibility(View.INVISIBLE);
    }

    public void onClickScreenShot(View v) {
        Bitmap screenshot;

        rouletteView.setDrawingCacheEnabled(true);
        screenshot = rouletteView.getDrawingCache();
        screenshot = Bitmap.createBitmap(screenshot);
        rouletteView.setDrawingCacheEnabled(false);
        photoView.setImageBitmap(screenshot);
        rouletteView.setVisibility(View.INVISIBLE);
        photoView.setVisibility(View.VISIBLE);

        float direction_r = direction;
        while (direction_r < 0) direction_r += 360;
        int result_num = (int)((direction_r%360) / (360/split)) + 1;
        resultTextView.setText(label.get(result_num-1) + " is chosen");
        resultTextView.setVisibility(View.VISIBLE);
    }

    public void onClickPlus(View v) {
        split++;
        if (split > splitMax) {
            Toast.makeText(this, "Max " + splitMax, Toast.LENGTH_LONG).show();
            split = splitMax;
        }
        rouletteView.setSplit(split);

        for(int i=0; i<10; i++) {
            label.put(i, Integer.toString(i+1));
        }
        rouletteView.setMap(label);
        resultTextView.setVisibility(View.INVISIBLE);
    }

    public void onClickMinus(View v) {
        split--;
        if (split < splitMin) {
            Toast.makeText(this, "Min " + splitMin, Toast.LENGTH_LONG).show();
            split = splitMin;
        }
        rouletteView.setSplit(split);

        for(int i=0; i<10; i++) {
            label.put(i, Integer.toString(i+1));
        }
        rouletteView.setMap(label);
        resultTextView.setVisibility(View.INVISIBLE);
    }
}