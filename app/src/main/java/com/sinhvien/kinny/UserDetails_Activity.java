package com.sinhvien.kinny;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import Database.DBHelper;
import Models.MucTieu;
import Models.NguoiDung;
import Models.Session;

public class UserDetails_Activity extends AppCompatActivity {

    ImageButton btn_us_tgW;
    Button btn_logOut, btn_save;
    ImageButton btn_backHome;
    AlertConFirmLogOut alertConFirmLogOut;
    DialogInterface dialogInterface;
    EditText txt_ten, txt_tuoi, txt_,txt_gioitinh, txt_chieucao;
    TextView tv_userName;
    Session session;
    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        txt_ten = findViewById(R.id.txt_user_Name);
        txt_tuoi = findViewById(R.id.txt_user_Age);
        txt_gioitinh = findViewById(R.id.txt_user_Gender);
        txt_chieucao = findViewById(R.id.txt_user_Height);
        tv_userName = findViewById(R.id.tv_usName);

        session = new Session(this);
        db = new DBHelper(this);
        hienthiChiTietNguoiDung();

        btn_us_tgW = findViewById(R.id.btn_tgWeight);
        btn_logOut = findViewById(R.id.btn_LogOut);
        btn_backHome = findViewById(R.id.btn_backHome1);
        btn_save = findViewById(R.id.btn_SaveUserdt);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = txt_ten.getText().toString();
                String tuoi = txt_tuoi.getText().toString();
                String gioiTinh = txt_gioitinh.getText().toString();
                String chieuCao = txt_chieucao.getText().toString();


                //Kiem tra nhap thong tin
                if(ten.equals("") || tuoi.equals("") || chieuCao.equals("") || gioiTinh.equals("") ){
                    Toast.makeText(UserDetails_Activity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                }
                else if (Double.parseDouble(chieuCao) < 120 || (Double.parseDouble(chieuCao) > 200)){
                    Toast.makeText(UserDetails_Activity.this, "120cm < Your height < 200cm", Toast.LENGTH_SHORT).show();
                }
                else if (Integer.parseInt(tuoi) < 6 || Integer.parseInt(tuoi) >100  ){
                    Toast.makeText(UserDetails_Activity.this, "6 < Your age < 100", Toast.LENGTH_SHORT).show();
                }

                else{
                    Boolean luuThongTinNguoiDung = db.capnhatThongTinUserDail(ten, Integer.parseInt(tuoi), gioiTinh,
                            Double.parseDouble(chieuCao), session.laySDT());

                    if(luuThongTinNguoiDung){
                        Toast.makeText(UserDetails_Activity.this, "Profile saved", Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Toast.makeText(UserDetails_Activity.this, "Saving failed", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        btn_us_tgW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it_us_tgW= new Intent(UserDetails_Activity.this, TgWeight_Activity.class);
                startActivity(it_us_tgW);
            }
        });
        btn_backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it_main = new Intent(UserDetails_Activity.this, MainActivity.class);
                startActivity(it_main);
            }
        });

        btn_logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UserDetails_Activity.this);

                builder.setTitle("Logout Confirm");
                builder.setMessage("Are you sure to log out ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        Intent it_logIn = new Intent(UserDetails_Activity.this, Login_Activity.class);
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

    public void hienthiChiTietNguoiDung() {
        NguoiDung nguoiDung1 = layDuLieuNguoiDung();

        if(nguoiDung1 != null){
            txt_ten.setText(nguoiDung1.get_ten());
            txt_tuoi.setText(String.valueOf(nguoiDung1.get_tuoi()));
            txt_gioitinh.setText(nguoiDung1.get_gioitinh());
            txt_chieucao.setText(String.valueOf(nguoiDung1.get_chieucao()));
            tv_userName.setText("Hi, " + nguoiDung1.get_ten());
        }

    }

    @SuppressLint("Range")
    public NguoiDung layDuLieuNguoiDung () {
        Cursor cursor = db.layTatcaDuLieuNguoiDung(session.laySDT());

        if(cursor != null) {
            while(cursor.moveToNext()) {
                NguoiDung nguoiDung = new NguoiDung();

                nguoiDung.set_ten(cursor.getString(cursor.getColumnIndex(db.COT_TEN)));
                nguoiDung.set_tuoi(Integer.parseInt(cursor.getString(cursor.getColumnIndex(db.COT_TUOI))));
                nguoiDung.set_gioitinh(cursor.getString(cursor.getColumnIndex(db.COT_GIOITINH)));
                nguoiDung.set_cannangbandau(Double.parseDouble(cursor.getString(cursor.getColumnIndex(db.COT_CANNANGBD))));
                nguoiDung.set_chieucao(Double.parseDouble(cursor.getString(cursor.getColumnIndex(db.COT_CHIEUCAO))));

                return nguoiDung;
            }
        }
        return null;
    }

}