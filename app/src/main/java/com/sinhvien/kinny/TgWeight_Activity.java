package com.sinhvien.kinny;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import Database.DBHelper;
import Models.MucTieu;
import Models.NguoiDung;
import Models.Session;

public class TgWeight_Activity extends AppCompatActivity {

    EditText startTime, targetTime, startWeight, targetWeight;
    TextView tv_userName2;
    ImageButton btn_us_usDeTails;
    ImageButton btn_backHome;
    Button btn_logOut, btn_save;
    DialogInterface dialogInterface;
    Session session;
    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tg_weight);


        tv_userName2 = findViewById(R.id.tv_usName2);
        startTime = findViewById(R.id.txt_tg_StartD);
        targetTime = findViewById(R.id.txt_user_TgDate);
        startWeight = findViewById(R.id.txt_tg_StarW);
        targetWeight = findViewById(R.id.txt_tg_TgW);
        btn_us_usDeTails = findViewById(R.id.btn_user_usDetails2);
        btn_backHome = findViewById(R.id.btn_backHome2);
        btn_logOut = findViewById(R.id.btn_LogOut2);
        btn_save = findViewById(R.id.btn_saveTgW2);
        session = new Session(this);
        db = new DBHelper(this);
        hienthiChiTietCanNang();



//        startTime.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ChonStartDate();
//            }
//        });
        targetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChonTargetDate();
            }
        });
        btn_us_usDeTails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it_us_usDT= new Intent(TgWeight_Activity.this, UserDetails_Activity.class);
                startActivity(it_us_usDT);
            }
        });

        btn_backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it_main = new Intent(TgWeight_Activity.this, MainActivity.class);
                startActivity(it_main);
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String canNang_BD = startWeight.getText().toString();
                String canNang_MT = targetWeight.getText().toString();
                String thoiGian_BD = startTime.getText().toString();
                String thoiGian_MT = targetTime.getText().toString();

                //Kiem tra nhap thong tin
                if(canNang_BD.equals("") || canNang_MT.equals("") || thoiGian_BD.equals("") || thoiGian_MT.equals("") ){
                    Toast.makeText(TgWeight_Activity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                }
                else if (Double.parseDouble(canNang_BD) < 20 || Double.parseDouble(canNang_BD) >200){
                    Toast.makeText(TgWeight_Activity.this, "20kg <= Your weight <= 200kg", Toast.LENGTH_SHORT).show();
                }
                else if (Double.parseDouble(canNang_MT) < 20 || Double.parseDouble(canNang_MT) >200){
                    Toast.makeText(TgWeight_Activity.this, "20kg <= Your weight <= 200kg", Toast.LENGTH_SHORT).show();
                }

                else{
                    Boolean luuThongTinNguoiDung = db.capnhatMucTieu(session.laySDT(),
                            Double.parseDouble(canNang_MT),
                            thoiGian_BD, thoiGian_MT);

                    if(luuThongTinNguoiDung){
                        Toast.makeText(TgWeight_Activity.this, "Profile saved", Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Toast.makeText(TgWeight_Activity.this, "Saving failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btn_logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TgWeight_Activity.this);

                builder.setTitle("Logout Confirm");
                builder.setMessage("Are you sure to log out ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        Intent it_logIn = new Intent(TgWeight_Activity.this, Login_Activity.class);
                        startActivity(it_logIn);
                        finish();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    public void hienthiChiTietCanNang() {
        NguoiDung nguoiDung1 = layDuLieuNguoiDung();
        MucTieu mucTieu1 = layDuLieuMucTieu();

        if(nguoiDung1 != null && mucTieu1 != null){
            startWeight.setText(String.valueOf(nguoiDung1.get_cannangbandau()));
            startTime.setText(mucTieu1.get_ngayBatDau());
            targetWeight.setText(String.valueOf(mucTieu1.get_cannangMT()));
            targetTime.setText(mucTieu1.get_ngayKetThuc());
            tv_userName2.setText("Hi, " + nguoiDung1.get_ten());
        }

    }

    @SuppressLint("Range")
    public NguoiDung layDuLieuNguoiDung () {
        Cursor cursor = db.layTatcaDuLieuNguoiDung(session.laySDT());

        if(cursor != null) {
            while(cursor.moveToNext()) {
                NguoiDung nguoiDung = new NguoiDung();

                nguoiDung.set_ten(cursor.getString(cursor.getColumnIndex(db.COT_TEN)));
                nguoiDung.set_cannangbandau(Double.parseDouble(cursor.getString(cursor.getColumnIndex(db.COT_CANNANGBD))));

                return nguoiDung;
            }
        }
        return null;
    }
    @SuppressLint("Range")
    public MucTieu layDuLieuMucTieu () {
        Cursor cursor = db.layMucTieuNguoidung(session.laySDT());

        if(cursor != null) {
            while(cursor.moveToNext()) {
                MucTieu mucTieu = new MucTieu();

                mucTieu.set_ngayBatDau(cursor.getString(cursor.getColumnIndex(db.COT_NGATBATDAU)));
                mucTieu.set_cannangMT(Double.parseDouble(cursor.getString(cursor.getColumnIndex(db.COT_CANNANGMT))));
                mucTieu.set_ngayKetThuc(cursor.getString(cursor.getColumnIndex(db.COT_NGAYTKETTHUC)));

                return mucTieu;
            }
        }
        return null;
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
                targetTime.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam, thang, ngay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()- 1000);
        datePickerDialog.show();
    }

//    private void ChonStartDate(){
//        Calendar calendar = Calendar.getInstance();
//        int ngay = calendar.get(Calendar.DATE);
//        int thang = calendar.get(Calendar.MONTH);
//        int nam = calendar.get(Calendar.YEAR);
//        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
//                //i: nam, i1: thang, i2: ngay
//                calendar.set(i, i1, i2);
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
//                startTime.setText(simpleDateFormat.format(calendar.getTime()));
//            }
//        }, nam, thang, ngay);
//        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()- 1000);
//        datePickerDialog.show();
//    }



}