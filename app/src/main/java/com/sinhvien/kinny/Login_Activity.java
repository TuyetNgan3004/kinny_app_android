package com.sinhvien.kinny;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import Database.DBHelper;
import Models.Session;

public class Login_Activity extends AppCompatActivity {

    TextView tv_signup;
    Button btn_login;
    EditText et_phone, et_password;
    DBHelper db;
    private Session session;
    public SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_phone=findViewById(R.id.et_phoneNumber);
        et_password=findViewById(R.id.et_password);

        tv_signup=findViewById(R.id.tv_signup);

        session = new Session(this);
        db = new DBHelper(this);

        btn_login=findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sdt= et_phone.getText().toString();
                String matkhau= et_password.getText().toString();

                if(sdt.equals("") || matkhau.equals("")){
                    Toast.makeText(Login_Activity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    Boolean kiemtraSDTMatKhau = db.kiemtraSDTMatKhau(sdt, matkhau);
                    if(kiemtraSDTMatKhau){
                        Toast.makeText(Login_Activity.this, "Login Successfully", Toast.LENGTH_SHORT).show();

                        session.setSDT(sdt);

                        Boolean kiemtraNguoiDungMoi = db.kiemtraNguoiDungMoi(sdt);


                        if(kiemtraNguoiDungMoi) {
                            Intent it_profile1= new Intent(Login_Activity.this, Profile1_Activity.class);
                            startActivity(it_profile1);
                        }
                        else{
                            Intent it_main= new Intent(Login_Activity.this, MainActivity.class);
                            startActivity(it_main);
                        }

                    }
                    else {
                        Toast.makeText(Login_Activity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        tv_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it_signup= new Intent(Login_Activity.this, Signup_Activity.class);
                startActivityForResult(it_signup,100);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 101) {
            et_phone.setText(data.getStringExtra("sdt"));
            et_password.setText(data.getStringExtra("matkhau"));
        }
    }

}
