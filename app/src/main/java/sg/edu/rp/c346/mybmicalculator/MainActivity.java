package sg.edu.rp.c346.mybmicalculator;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    EditText etweight;
    EditText etheight;
    Button btnCalc;
    Button btnreset;
    TextView tvDate;
    TextView tvBMI;
    TextView tvOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etweight=findViewById(R.id.editTextWeight);
        etheight=findViewById(R.id.editTextHeight);
        btnCalc=findViewById(R.id.buttonCalculate);
        btnreset=findViewById(R.id.buttonResetData);
        tvDate=findViewById(R.id.textViewDate);
        tvBMI=findViewById(R.id.textViewBMI);
        tvOut=findViewById(R.id.textViewOutcome);
        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etheight.setText("");
                etweight.setText("");
                tvDate.setText("Last calculated Date:");
                tvBMI.setText("Last calculated BMI:");
                tvOut.setText("");
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor prefEdit = prefs.edit();
                prefEdit.clear();
                prefEdit.commit();

            }


        });

        btnCalc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                float weight = Float.parseFloat(etweight.getText().toString());
                float height =Float.parseFloat(etheight.getText().toString());
                Float BMI=(weight/(height*height));
                Calendar now = Calendar.getInstance();
                String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                        (now.get(Calendar.MONTH)+1) + "/" +
                        now.get(Calendar.YEAR) + " " +
                        now.get(Calendar.HOUR_OF_DAY) + ":" +
                        now.get(Calendar.MINUTE);
                tvDate.setText("Last calculated Date: " + datetime );
                tvBMI.setText("Last calculated BMI: "+String.format("%.3f",BMI));
                if (BMI<18.5){
                    tvOut.setText("You are underweight");
                }else if (BMI<25){
                    tvOut.setText("Your BMI is normal");
                }else if (BMI<29.9){
                    tvOut.setText("You are overweight");
                }else if (BMI>=30){
                    tvOut.setText("You are obese");

                }
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor prefEdit = prefs.edit();
                prefEdit.putFloat("BMI",BMI);
                prefEdit.putString("datetime",datetime);
                prefEdit.commit();
                etweight.setText("");
                etheight.setText("");
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!etweight.getText().toString().isEmpty()) {
            float weight = Float.parseFloat(etweight.getText().toString());
            float height =Float.parseFloat(etheight.getText().toString());
            Float BMI=(weight/(height*height));
            Calendar now = Calendar.getInstance();
            String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                    (now.get(Calendar.MONTH)+1) + "/" +
                    now.get(Calendar.YEAR) + " " +
                    now.get(Calendar.HOUR_OF_DAY) + ":" +
                    now.get(Calendar.MINUTE);

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Float lastBMI =prefs.getFloat("BMI",0.0f);
        String lastDate=prefs.getString("datetime","");
        tvBMI.setText("Last calculated BMI: " + String.format("%.3f",lastBMI));
        tvDate.setText("Last calculated Date: " + lastDate);
    }
}
