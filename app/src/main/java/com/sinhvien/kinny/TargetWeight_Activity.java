package com.sinhvien.kinny;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import Database.DBHelper;
import Models.Session;

public class TargetWeight_Activity extends AppCompatActivity {

    Button btn_getStarted;
    EditText txt_tgW;
    EditText txt_tgD;
    Session session;
    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target_weight);

        btn_getStarted = findViewById(R.id.btn_getStarted);
        txt_tgW = findViewById(R.id.txt_targetWeight);
        txt_tgD = findViewById(R.id.txt_targetDate);
        session = new Session(this);
        dbHelper = new DBHelper(this);

        txt_tgD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChonTargetDate();
            }
        });

        btn_getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cannangMT = txt_tgW.getText().toString();
                String ngayKetThuc = txt_tgD.getText().toString();
                String ngayBatDau = themNgayBatDau();

                if(cannangMT.equals("") || ngayKetThuc.equals("")){
                    Toast.makeText(TargetWeight_Activity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                }
                else if (Double.parseDouble(cannangMT) < 20 || Double.parseDouble(cannangMT) > 200){
                    Toast.makeText(TargetWeight_Activity.this, "20kg <= Your weight <= 200kg", Toast.LENGTH_SHORT).show();
                }
                else {
                    Boolean themMucTieu = dbHelper.themMucTieu(session.laySDT());
                    if(themMucTieu == true){
                        Boolean capnhatMucTieu = dbHelper.capnhatMucTieu(session.laySDT(),Double.parseDouble(cannangMT), ngayBatDau, ngayKetThuc);
                        if(capnhatMucTieu == true) {
                            Toast.makeText(TargetWeight_Activity.this, "Set target succesfully!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(TargetWeight_Activity.this, "Set target failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(TargetWeight_Activity.this, "Set target failed !!!!", Toast.LENGTH_SHORT).show();
                    }
                }

                Intent it_main= new Intent(TargetWeight_Activity.this, MainActivity.class);
                startActivity(it_main);
            }
        });
    }

    private void ChonTargetDate(){
        Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                //i: nam, i1: thang, i2: ngay
                calendar.set(i, i1, i2);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                txt_tgD.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam, thang, ngay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()- 1000);
        datePickerDialog.show();
    }


    public String themNgayBatDau() {
        Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        calendar.set(nam, thang, ngay);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String ngayBatDau = simpleDateFormat.format(calendar.getTime());
        return ngayBatDau;
    }

}