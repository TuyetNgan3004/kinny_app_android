package com.sinhvien.kinny;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import Database.DBHelper;
import Models.HistoryWeight;
import Models.MucTieu;
import Models.NguoiDung;
import Models.Session;

public class MainActivity extends AppCompatActivity {

    TextView tv_main_tgW;
    TextView tv_main_tgD, tv_startW, tv_startD, tv_startBMI, tv_tgBMI;
    TextView tv_CurrentWeight, tv_CurrentBMI, tv_CurrentStateBMI;
    TextView tv_GetWeight, tv_Process;
    TextView tv_StateGetWeight, tv_StateProcess;
    Button btn_addW;
    ImageView img_BMICal,img_User, img_Calo;
    Session session;
    DBHelper db;
    TextClock textClock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_CurrentWeight = findViewById(R.id.tv_currentWeight);
        tv_CurrentBMI = findViewById(R.id.tv_currentBMI);
        tv_CurrentStateBMI = findViewById(R.id.tv_currenBMITest);

        tv_main_tgW = findViewById(R.id.tv_main_tgWeight);
        tv_main_tgD = findViewById(R.id.tv_main_tgD);

        tv_startW = findViewById(R.id.tv_main_starWeight);
        tv_startD = findViewById(R.id.tv_main_starD);
        tv_startBMI = findViewById(R.id.tv_main_startBMI);
        tv_tgBMI = findViewById(R.id.tv_main_tgBMI);

        tv_GetWeight = findViewById(R.id.tv_Main_GetWeight);
        tv_Process = findViewById(R.id.tv_Main_Process);

        tv_StateGetWeight = findViewById(R.id.tv_Main_StateGetWeight);
        tv_StateProcess = findViewById(R.id.tv_Main_StateProcess);

        textClock = findViewById(R.id.textclock);

        String formatdate = "d/M/yyyy";
        textClock.setFormat12Hour(formatdate);
        textClock.setFormat24Hour(formatdate);

        session = new Session(this);
        db = new DBHelper(this);
        hienthiDuLieu();

        quaTrinhCanNang();

        btn_addW = findViewById(R.id.btn_main_AddW);
        btn_addW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it_addW = new Intent(MainActivity.this, AddWeight_Activity.class);
                startActivity(it_addW);
            }
        });
        img_BMICal = findViewById(R.id.img_main_BMI);
        img_BMICal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it_BMICal = new Intent(MainActivity.this, BMI_Caculator_Activity.class);
                startActivity(it_BMICal);
            }
        });
        img_User = findViewById(R.id.img_main_User);
        img_User.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it_User = new Intent(MainActivity.this, UserDetails_Activity.class);
                startActivity(it_User);
            }
        });

        img_Calo = findViewById(R.id.img_main_calo);
        img_Calo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it_Calo = new Intent(MainActivity.this, Calories_Activity.class);
                startActivity(it_Calo);
            }
        });

    }

    public void hienthiDuLieu() {
        //Boolean capnhatLT = db.capnhatCheDoLuyenTap(1.2, session.laySDT());
       NguoiDung nguoiDung1 = layDuLieuNguoiDung();
       MucTieu mucTieu1 = layMucTieuNguoiDung();
        HistoryWeight canNang = layDuLieuCanNang();


       if(nguoiDung1 != null){
           tv_startW.setText(String.valueOf(nguoiDung1.get_cannangbandau()));
           tv_startBMI.setText("BMI: "+ tinhBMIBanDau());
           tv_CurrentWeight.setText(String.valueOf(nguoiDung1.get_cannangbandau()));
           tv_CurrentBMI.setText(tinhBMIBanDau());
           if (canNang != null){
              tv_CurrentWeight.setText(String.valueOf(canNang.getWeight()));
              tv_CurrentBMI.setText(String.valueOf(String.format("%,.2f",canNang.getBmi())));
              //Danh gia BMI ham main
              if( canNang.getBmi() < 18.5){
                    tv_CurrentStateBMI.setText("Light Weight");
              }
              else if (canNang.getBmi() >= 18.5 && canNang.getBmi() <25){
                  tv_CurrentStateBMI.setText("Normal Weight");
              }
              else if (canNang.getBmi() >= 25 && canNang.getBmi() <30){
                  tv_CurrentStateBMI.setText("OverWeight");
              }
              else if (canNang.getBmi() >= 30 && canNang.getBmi() < 40){
                  tv_CurrentStateBMI.setText("Fat 1");
              }
              else if (canNang.getBmi() >= 40){
                  tv_CurrentStateBMI.setText("Fat 2");
              }
           }

       }
       if(mucTieu1 != null) {
           tv_main_tgW.setText(String.valueOf(mucTieu1.get_cannangMT()));
           tv_startD.setText(mucTieu1.get_ngayBatDau());
           tv_main_tgD.setText(mucTieu1.get_ngayKetThuc());
           tv_tgBMI.setText(tinhBMIMucTieu());
       }


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

    @SuppressLint("Range")
    public HistoryWeight layDuLieuCanNang () {
      Cursor cursor = db.layDuLieuCanNangMoiNhat(session.laySDT());
        if(cursor != null) {
            while(cursor.moveToNext()) {
                HistoryWeight historyWeight = new HistoryWeight();

                historyWeight.setWeight(Double.parseDouble(cursor.getString(cursor.getColumnIndex(db.COT_CANNANG))));
                historyWeight.setBmi(Double.parseDouble(cursor.getString(cursor.getColumnIndex(db.COT_BMI))));



                return historyWeight;
            }
        }
        return null;
    }


    public String tinhBMIBanDau(){
        NguoiDung nguoiDung1 = layDuLieuNguoiDung();
        if(nguoiDung1 != null){
            double cannang = nguoiDung1.get_cannangbandau();
            double chieucao = nguoiDung1.get_chieucao()/100;
            double bmi = cannang/(chieucao*chieucao);
            return String.valueOf(String.format("%,.2f",bmi));
        }
        return null;
    }

    public String tinhBMIMucTieu(){
        NguoiDung nguoiDung1 = layDuLieuNguoiDung();
        MucTieu mucTieu1 = layMucTieuNguoiDung();
        if(nguoiDung1 != null && mucTieu1 != null) {
            double cannang = mucTieu1.get_cannangMT();
            double chieucao = nguoiDung1.get_chieucao()/100;
            double bmi = cannang/(chieucao*chieucao);
            return String.valueOf("BMI " + String.format("%,.2f",bmi));
        }
        return null;
    }

    public void quaTrinhCanNang(){
        NguoiDung nguoiDung = layDuLieuNguoiDung();
        HistoryWeight canNang = layDuLieuCanNang();
        MucTieu mucTieu = layMucTieuNguoiDung();
        double getWeight;
        double canNangCanDat;
        double process ;

        //Cannang add - canangbandau (negative - loss, positive - gain, 0 - keep)
        if(canNang != null){
            getWeight = canNang.getWeight() - nguoiDung.get_cannangbandau();

            //Cannangbandau - cannangmuctieu (negative - loss, positive - gain, 0 - keep)
            canNangCanDat = mucTieu.get_cannangMT() - nguoiDung.get_cannangbandau();

            if (canNangCanDat < 0 ){
                if(getWeight < 0){
                    process= (getWeight/(canNangCanDat)*100);
                    tv_Process.setText(String.valueOf(String.format("%,.1f",process )) + " %");
                }
                else {
                    process=0;
                    tv_Process.setText(String.valueOf(String.format("%,.1f",process )) + " %");
                }
            }
            else {
                if(getWeight > 0){
                    process= (getWeight/(canNangCanDat)*100);
                    tv_Process.setText(String.valueOf(String.format("%,.1f",process )) + " %");
                }
                else {
                    process=0;
                    tv_Process.setText(String.valueOf(String.format("%,.1f",process )) + " %");
                }
            }

            if(getWeight <0){
                tv_StateGetWeight.setText("Weight loss");
                tv_GetWeight.setText(String.valueOf(String.format("%,.1f",getWeight * (-1)) + " kg"));
            }
            else if (getWeight == 0){
                tv_StateGetWeight.setText("Keep weight");
                tv_GetWeight.setText(String.valueOf(String.format("%,.1f",getWeight) + " kg"));

            }
            else if (getWeight > 0){
                tv_StateGetWeight.setText("Weight gain");
                tv_GetWeight.setText(String.valueOf(String.format("%,.1f",getWeight) + " kg"));
            }
        }
        else {
            process=0;
            tv_Process.setText(String.valueOf(String.format("%,.1f",process )) + " %");
            tv_StateGetWeight.setText("Keep weight");
            getWeight=0;
            tv_GetWeight.setText(String.valueOf(String.format("%,.1f",getWeight) + " kg"));
        }

    }

    @SuppressLint("Range")
    public MucTieu layMucTieuNguoiDung() {
        Cursor cursor = db.layMucTieuNguoidung(session.laySDT());

        if(cursor != null) {
            while (cursor.moveToNext()) {
                MucTieu mucTieu = new MucTieu();
                mucTieu.set_cannangMT(Double.parseDouble(cursor.getString(cursor.getColumnIndex(db.COT_CANNANGMT))));
                mucTieu.set_ngayBatDau(cursor.getString(cursor.getColumnIndex(db.COT_NGATBATDAU)));
                mucTieu.set_ngayKetThuc(cursor.getString(cursor.getColumnIndex(db.COT_NGAYTKETTHUC)));
                return mucTieu;
            }
        }
        return null;
    }
}