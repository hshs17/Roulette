package jp.ac.titech.itpro.sdl.roulette;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import android.graphics.Bitmap;


public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private final static String TAG = MainActivity.class.getSimpleName();

    private RouletteView rouletteView;
    private ImageView photoView;
    // private Button resetButton;

    private SensorManager manager;
    private Sensor sensor;

    private float timestamp = 0;
    private float direction = 0;
    private float direction_t = 0;
    private int split = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rouletteView = findViewById(R.id.roulette_view);
        photoView = findViewById(R.id.photo_view);
        // resetButton = findViewById(R.id.reset_button);

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
        // Log.d(TAG, Float.toString(direction));
        if (data < -0.01 || 0.01 < data) {
            rouletteView.setDirection(direction);
        }
        Log.d(TAG, Float.toString(data));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    public void onClickReset(View v) {
        direction = 0;
        split = 6;
        rouletteView.setDirection(direction);
        rouletteView.setSplit(split);
        rouletteView.setVisibility(View.VISIBLE);
        photoView.setVisibility(View.INVISIBLE);
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
    }

    public void onClickPlus(View v) {
        split++;
        if (split > 10) {
            Toast.makeText(this, "Max 10", Toast.LENGTH_LONG).show();
            split = 10;
        }
        rouletteView.setSplit(split);
    }

    public void onClickMinus(View v) {
        split--;
        if (split < 4) {
            Toast.makeText(this, "Min 4", Toast.LENGTH_LONG).show();
            split = 4;
        }
        rouletteView.setSplit(split);
    }
}