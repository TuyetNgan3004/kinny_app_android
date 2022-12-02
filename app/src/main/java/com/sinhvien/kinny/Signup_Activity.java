package com.sinhvien.kinny;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import Database.DBHelper;

public class Signup_Activity extends AppCompatActivity {

    EditText et_phone, et_password, et_confirmpass;
    Button btn_signup;
    TextView tv_login;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        et_phone= findViewById(R.id.et_phoneNumber_signup);
        et_password= findViewById(R.id.et_password_signup);
        et_confirmpass= findViewById(R.id.et_confirmpass);

        tv_login=findViewById(R.id.tv_login);

        db = new DBHelper(this);

        btn_signup=findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sdt= et_phone.getText().toString();
                String matkhau= et_password.getText().toString();
                String xacnhan=et_confirmpass.getText().toString();

                if(sdt.equals("") || matkhau.equals("") || xacnhan.equals("")){
                    Toast.makeText(Signup_Activity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(matkhau.equals(xacnhan)) {
                        Boolean kiemtraSDT = db.kiemtraSDT(sdt);
                        if(kiemtraSDT == false){
                            Boolean themNguoiDung = db.themNguoiDung(sdt, matkhau);

                            if(themNguoiDung == true){
                                Toast.makeText(Signup_Activity.this,"Signup Successfully!" , Toast.LENGTH_SHORT).show();
                                Intent it_signup= new Intent(Signup_Activity.this, Login_Activity.class);
                                it_signup.putExtra("sdt", sdt);
                                it_signup.putExtra("matkhau", matkhau);
                                setResult(101,it_signup);
                                finish();
                            }
                            else {
                                Toast.makeText(Signup_Activity.this,"Signup Failed " , Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(Signup_Activity.this,"User already exist! Please log in" , Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(Signup_Activity.this,"Password is not matching " , Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it_login= new Intent(Signup_Activity.this, Login_Activity.class);
                startActivity(it_login);
            }
        });
    }

}


// if(pass.compareToIgnoreCase(confirm)==0){
//                    Intent it_signup= new Intent(Signup_Activity.this, Login_Activity.class);
//                    it_signup.putExtra("email", email);
//                    it_signup.putExtra("password", pass);
//                    setResult(101,it_signup);
//                    finish();
//                    Toast.makeText(Signup_Activity.this,"Signup!!!" , Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    Toast.makeText(Signup_Activity.this,"Wrong Password!!!" , Toast.LENGTH_SHORT).show();
//                }