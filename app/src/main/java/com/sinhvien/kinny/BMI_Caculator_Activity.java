package com.sinhvien.kinny;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class BMI_Caculator_Activity extends AppCompatActivity {

    ImageButton btn_backHome;
    Button btn_calBMI;
    TextView danhgia_calBMI, bmi;
    EditText et_calWeight, et_calHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_caculator);

        danhgia_calBMI = findViewById(R.id.tv_BMICalT);
        et_calHeight = findViewById(R.id.txt_cal_Height);
        et_calWeight = findViewById(R.id.txt_cal_Weight);
        btn_calBMI = findViewById(R.id.btn_calBMI);
        bmi = findViewById(R.id.tv_BMICal);
        btn_backHome = findViewById(R.id.btn_backHome3);

        btn_backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it_main = new Intent(BMI_Caculator_Activity.this, MainActivity.class);
                startActivity(it_main);
            }
        });
        btn_calBMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String calHeight = et_calHeight.getText().toString();
                String calWeight = et_calWeight.getText().toString();

                if (calHeight.equals("") || calWeight.equals("")){
                    Toast.makeText(BMI_Caculator_Activity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                }
                else if (Double.parseDouble(calHeight) < 120 || Double.parseDouble(calHeight) > 200){
                    Toast.makeText(BMI_Caculator_Activity.this, "120cm < Your height < 200cm", Toast.LENGTH_SHORT).show();
                }
                else if (Double.parseDouble(calWeight) < 20 || Double.parseDouble(calWeight) > 200){
                    Toast.makeText(BMI_Caculator_Activity.this, "20kg < Your weight < 200kg", Toast.LENGTH_SHORT).show();
                }
                else {
                    double chieucao = Double.parseDouble(calHeight)/100;
                    double cannang = Double.parseDouble(calWeight);
                    double result_BMI = cannang/(chieucao*chieucao);

                    if(result_BMI <= 18.5){
                        bmi.setText(String.valueOf(String.format("%,.2f", result_BMI)));
                        danhgia_calBMI.setText("Light weight");
                    }
                    else if (result_BMI > 18.5 && result_BMI < 25 ){
                        bmi.setText(String.valueOf(String.format("%,.2f", result_BMI)));
                        danhgia_calBMI.setText("Normal weight");
                    }
                    else if (result_BMI >= 25 && result_BMI < 30 ){
                        bmi.setText(String.valueOf(String.format("%,.2f", result_BMI)));
                        danhgia_calBMI.setText("Overweight");
                    }
                    else if (result_BMI >= 30 && result_BMI < 40 ){
                        bmi.setText(String.valueOf(String.format("%,.2f", result_BMI)));
                        danhgia_calBMI.setText("Fat 1");
                    }
                    else if (result_BMI >= 40 ){
                        bmi.setText(String.valueOf(String.format("%,.2f", result_BMI)));
                        danhgia_calBMI.setText("Fat 2");
                    }
                }

            }
        });

    }

}