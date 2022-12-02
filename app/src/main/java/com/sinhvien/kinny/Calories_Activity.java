package com.sinhvien.kinny;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import Database.DBHelper;
import Models.HistoryWeight;
import Models.MucTieu;
import Models.NguoiDung;
import Models.Session;

public class Calories_Activity extends AppCompatActivity {

    ImageButton btn_backHome;
    TextView tv_calo,exDes, caloAdvice;
    TextInputLayout drop_menu;
    AutoCompleteTextView drop_items;
    Session session;
    DBHelper db;
    ArrayAdapter<String> itemAdapter;
    private static String[] items = {"Sedentary lifestyle", "Slightly active", "Moderately active", "Active lifestyle"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calories);

        btn_backHome = findViewById(R.id.btn_backHome_Calo);
        tv_calo = findViewById(R.id.tv_Calo);
        session = new Session(this);
        db = new DBHelper(this);
        exDes = findViewById(R.id.exDes);
        caloAdvice = findViewById(R.id.tv_caloAdvice);

        drop_menu = findViewById(R.id.drop_menu);
        drop_items = findViewById(R.id.drop_items);
        itemAdapter = new ArrayAdapter<>(Calories_Activity.this, R.layout.items_dropmenu,items);
        drop_items.setAdapter(itemAdapter);

        hienThiDuLieu();

        drop_items.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemSelected = (String)parent.getItemAtPosition(position);
                if(itemSelected == "Sedentary lifestyle"){
                    Boolean capnhatLT = db.capnhatCheDoLuyenTap(1.2, session.laySDT());
                    if(capnhatLT){
                        hienThiDuLieu();
                    }
                    else {
                        Toast.makeText(Calories_Activity.this, "Update failed", Toast.LENGTH_SHORT).show();
                    }
                }
                else if(itemSelected == "Slightly active"){
                    Boolean capnhatLT = db.capnhatCheDoLuyenTap(1.375, session.laySDT());
                    if(capnhatLT){
                        hienThiDuLieu();
                    }
                    else {
                        Toast.makeText(Calories_Activity.this, "Update failed", Toast.LENGTH_SHORT).show();
                    }
                }
                else if(itemSelected == "Moderately active"){
                    Boolean capnhatLT = db.capnhatCheDoLuyenTap(1.55, session.laySDT());
                    if(capnhatLT){
                        hienThiDuLieu();
                    }
                    else {
                        Toast.makeText(Calories_Activity.this, "Update failed", Toast.LENGTH_SHORT).show();
                    }
                }
                else if(itemSelected == "Active lifestyle"){
                    Boolean capnhatLT = db.capnhatCheDoLuyenTap(1.725, session.laySDT());
                    if(capnhatLT){
                        hienThiDuLieu();
                    }
                    else {
                        Toast.makeText(Calories_Activity.this, "Update failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btn_backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it_main = new Intent(Calories_Activity.this, MainActivity.class);
                startActivity(it_main);
            }
        });
    }

    public void hienThiDuLieu() {
        NguoiDung nguoiDung1 = layDuLieuNguoiDung();
        HistoryWeight canNang1 = layDuLieuCanNang();
        MucTieu mucTieu1 = layMucTieuNguoiDung();

        if(nguoiDung1.get_luyentap() == 1.2){
            drop_items.setText("Sedentary lifestyle");
            exDes.setText("Little to or no physical activity and exercise");
            itemAdapter = new ArrayAdapter<>(Calories_Activity.this, R.layout.items_dropmenu,items);
            drop_items.setAdapter(itemAdapter);
        }
        else if (nguoiDung1.get_luyentap() == 1.375)
        {
            drop_items.setText("Slightly active");
            exDes.setText("1-3 times a week");
            itemAdapter = new ArrayAdapter<>(Calories_Activity.this, R.layout.items_dropmenu,items);
            drop_items.setAdapter(itemAdapter);
        }
        else if (nguoiDung1.get_luyentap() == 1.55)
        {
            drop_items.setText("Moderately active");
            exDes.setText("3-5 times a week");
            itemAdapter = new ArrayAdapter<>(Calories_Activity.this, R.layout.items_dropmenu,items);
            drop_items.setAdapter(itemAdapter);
        }
        else {
            drop_items.setText("Active lifestyle");
            exDes.setText("6-7 times a week");
            itemAdapter = new ArrayAdapter<>(Calories_Activity.this, R.layout.items_dropmenu,items);
            drop_items.setAdapter(itemAdapter);
        }

        if(nguoiDung1 != null){
            if(canNang1 != null){
                double mucTieu = mucTieu1.get_cannangMT() - canNang1.getWeight();
                if(mucTieu < 0){
                    caloAdvice.setText("Advice: You should have a lower comsumpsion");
                }
                else if(mucTieu > 0){
                    caloAdvice.setText("Advice: You should have a higher comsumpsion");
                }
                else {
                    caloAdvice.setText("Keep up the good work!");
                }
                double calo = tinhCalories(nguoiDung1, canNang1);
                tv_calo.setText(String.valueOf(String.format("%,.2f", calo)));
            }
            else{
                double mucTieu = mucTieu1.get_cannangMT() - nguoiDung1.get_cannangbandau();
                if(mucTieu < 0){
                    caloAdvice.setText("Advice: You should have a lower comsumpsion");
                }
                else if(mucTieu > 0){
                    caloAdvice.setText("Advice: You should have a higher comsumpsion");
                }
                else {
                    caloAdvice.setText("Keep up the good work!");
                }
                double calo = tinhCaloriesBanDau(nguoiDung1);
                tv_calo.setText(String.valueOf(String.format("%,.2f", calo)));
            }
        }

    }

    public double tinhCaloriesBanDau(NguoiDung nguoiDung){
            if(nguoiDung.get_gioitinh().equals("Male")){
                return ((13.397 * nguoiDung.get_cannangbandau())+(4.799 * nguoiDung.get_chieucao()) - (5.677 * nguoiDung.get_tuoi()) + 88.362) * nguoiDung.get_luyentap();
            }
            else {
                return ((9.247 * nguoiDung.get_cannangbandau())+(3.098 * nguoiDung.get_chieucao())-(4.330 * nguoiDung.get_tuoi()) + 447.593) * nguoiDung.get_luyentap();
            }
    }

    public double tinhCalories(NguoiDung nguoiDung, HistoryWeight canNang){
        double calo;
        if(nguoiDung.get_gioitinh().equals("Male")){
            calo = ((13.397 * canNang.getWeight())+(4.799 * nguoiDung.get_chieucao()) - (5.677 * nguoiDung.get_tuoi()) + 88.362) * nguoiDung.get_luyentap();
        }
        else {
            calo = ((9.247 * canNang.getWeight())+(3.098 * nguoiDung.get_chieucao())-(4.330 * nguoiDung.get_tuoi()) + 447.593) * nguoiDung.get_luyentap();
        }
        return calo;
    }

    @SuppressLint("Range")
    public NguoiDung layDuLieuNguoiDung () {
        Cursor cursor = db.layTatcaDuLieuNguoiDung(session.laySDT());

        if(cursor != null) {
            while(cursor.moveToNext()) {
                NguoiDung nguoiDung = new NguoiDung();

                nguoiDung.set_cannangbandau(Double.parseDouble(cursor.getString(cursor.getColumnIndex(db.COT_CANNANGBD))));
                nguoiDung.set_chieucao(Double.parseDouble(cursor.getString(cursor.getColumnIndex(db.COT_CHIEUCAO))));
                nguoiDung.set_gioitinh(cursor.getString(cursor.getColumnIndex(db.COT_GIOITINH)));
                nguoiDung.set_tuoi(Integer.parseInt(cursor.getString(cursor.getColumnIndex(db.COT_TUOI))));
                nguoiDung.set_luyentap(Double.parseDouble(cursor.getString(cursor.getColumnIndex(db.COT_LUYENTAP))));

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

                return historyWeight;
            }
        }
        return null;
    }

    @SuppressLint("Range")
    public MucTieu layMucTieuNguoiDung() {
        Cursor cursor = db.layMucTieuNguoidung(session.laySDT());

        if(cursor != null) {
            while (cursor.moveToNext()) {
                MucTieu mucTieu = new MucTieu();
                mucTieu.set_cannangMT(Double.parseDouble(cursor.getString(cursor.getColumnIndex(db.COT_CANNANGMT))));
                return mucTieu;
            }
        }
        return null;
    }
}

