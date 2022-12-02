package com.sinhvien.kinny;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import Database.DBHelper;
import Models.NguoiDung;
import Models.Session;

public class BMIResult_Activity extends AppCompatActivity {

    Button btn_setTarget;
    TextView bmi, danhgiaBMI, loikhuyen;
    DBHelper db;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmiresult);

        db = new DBHelper(this);
        session = new Session(this);
        bmi = findViewById(R.id.tv_bmi);
        danhgiaBMI = findViewById(R.id.tv_bmiTest);
        loikhuyen = findViewById(R.id.tv_advice);

       double tinhBMI = tinhBMI();
       if(tinhBMI <= 18.5)
       {

           bmi.setText(String.valueOf(String.format("%,.2f",tinhBMI)));
            danhgiaBMI.setText("Light weight");
           loikhuyen.setText("Advice: You should gain weight.");
       }
       else if (tinhBMI > 18.5 && tinhBMI <= 25){
           bmi.setText(String.valueOf(String.format("%,.2f",tinhBMI)));
           danhgiaBMI.setText("Normal weight");
           loikhuyen.setText("Advice: You should keep your weight.");
       }
       else if (tinhBMI > 25 && tinhBMI <=30 ){
           bmi.setText(String.valueOf(String.format("%,.2f",tinhBMI)));
           danhgiaBMI.setText("Overweight");
           loikhuyen.setText("Advice: You should lose weight.");
       }
       else if (tinhBMI > 30 && tinhBMI <=40 ){
           bmi.setText(String.valueOf(String.format("%,.2f",tinhBMI)));
           danhgiaBMI.setText("Fat 1");
           loikhuyen.setText("Advice: You should lose weight.");
       }
       else if (tinhBMI > 40 ){
           bmi.setText(String.valueOf(String.format("%,.2f",tinhBMI)));
           danhgiaBMI.setText("Fat 2");
           loikhuyen.setText("Advice: You should lose weight.");
       }
       else{
           Toast.makeText(this, "Cannot calculate BMI", Toast.LENGTH_SHORT).show();
       }

        btn_setTarget = findViewById(R.id.btn_setTarget);
        btn_setTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it_setTarget= new Intent(BMIResult_Activity.this, TargetWeight_Activity.class);
                startActivity(it_setTarget);
            }
        });

    }

    @SuppressLint("Range")
    public NguoiDung layDuLieuNguoiDung () {
        Cursor cursor = db.layTatcaDuLieuNguoiDung(session.laySDT());
        
        if(cursor != null) {
            while(cursor.moveToNext()) {
                NguoiDung nguoiDung = new NguoiDung();
                
                nguoiDung.set_cannangbandau(Double.parseDouble(cursor.getString(cursor.getColumnIndex(db.COT_CANNANGBD))));
                nguoiDung.set_chieucao(Double.parseDouble(cursor.getString(cursor.getColumnIndex(db.COT_CHIEUCAO))));

                return nguoiDung;
            }
        }
        return null;
    }

    public Double tinhBMI(){
        NguoiDung nguoiDung1 = layDuLieuNguoiDung();
        if(nguoiDung1 != null){
            double cannang = nguoiDung1.get_cannangbandau();
            double chieucao = nguoiDung1.get_chieucao()/100;
            double bmi = cannang/(chieucao*chieucao);
            return bmi;
        }
        return 0.0;
    }
}