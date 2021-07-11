package jp.ac.titech.itpro.sdl.roulette;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {

    private EditText edit1, edit2, edit3, edit4, edit5, edit6, edit7, edit8, edit9, edit10;

    public final static String EXTRA1 = "label1";
    public final static String EXTRA2 = "label2";
    public final static String EXTRA3 = "label3";
    public final static String EXTRA4 = "label4";
    public final static String EXTRA5 = "label5";
    public final static String EXTRA6 = "label6";
    public final static String EXTRA7 = "label7";
    public final static String EXTRA8 = "label8";
    public final static String EXTRA9 = "label9";
    public final static String EXTRA10 = "label10";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        edit1 = findViewById(R.id.edit1);
        edit2 = findViewById(R.id.edit2);
        edit3 = findViewById(R.id.edit3);
        edit4 = findViewById(R.id.edit4);
        edit5 = findViewById(R.id.edit5);
        edit6 = findViewById(R.id.edit6);
        edit7 = findViewById(R.id.edit7);
        edit8 = findViewById(R.id.edit8);
        edit9 = findViewById(R.id.edit9);
        edit10 = findViewById(R.id.edit10);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void onClickBack(View v) {
        Intent data = new Intent();
        String label;

        label = edit1.getText().toString().trim();
        data.putExtra(EXTRA1, label);
        label = edit2.getText().toString().trim();
        data.putExtra(EXTRA2, label);
        label = edit3.getText().toString().trim();
        data.putExtra(EXTRA3, label);
        label = edit4.getText().toString().trim();
        data.putExtra(EXTRA4, label);
        label = edit5.getText().toString().trim();
        data.putExtra(EXTRA5, label);
        label = edit6.getText().toString().trim();
        data.putExtra(EXTRA6, label);
        label = edit7.getText().toString().trim();
        data.putExtra(EXTRA7, label);
        label = edit8.getText().toString().trim();
        data.putExtra(EXTRA8, label);
        label = edit9.getText().toString().trim();
        data.putExtra(EXTRA9, label);
        label = edit10.getText().toString().trim();
        data.putExtra(EXTRA10, label);

        setResult(RESULT_OK, data);
        finish();
    }
}
