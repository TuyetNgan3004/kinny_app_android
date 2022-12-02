
package com.sinhvien.kinny;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import Database.DBHelper;
import Models.Session;

public class Profile1_Activity extends AppCompatActivity {

    EditText et_name, et_age, et_weight, et_height;
    Button btn_next;
    RadioGroup radioGroup;
    RadioButton rbMale_Female;
    Session session;
    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile1);

        et_name= findViewById(R.id.et_name);
        et_age=findViewById(R.id.et_age);
        et_height = findViewById(R.id.et_height);
        et_weight = findViewById(R.id.et_weight);
        radioGroup = findViewById(R.id.radioGroup);

        session = new Session(this);
        db = new DBHelper(this);

        btn_next=findViewById(R.id.btn_next);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten=et_name.getText().toString();
                String tuoi = et_age.getText().toString();
                String chieucao = et_height.getText().toString();
                String cannang = et_weight.getText().toString();

                int selected = radioGroup.getCheckedRadioButtonId();
                rbMale_Female = findViewById(selected);
                String gioitinh = rbMale_Female.getText().toString();

                //Kiem tra nhap thong tin
                if(ten.equals("") || tuoi.equals("") || chieucao.equals("") || cannang.equals("") ){
                    Toast.makeText(Profile1_Activity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                }
                else if (Double.parseDouble(chieucao) < 120 || (Double.parseDouble(chieucao) > 200)){
                    Toast.makeText(Profile1_Activity.this, "120cm < Your height < 200cm", Toast.LENGTH_SHORT).show();
                }
                else if (Integer.parseInt(tuoi) < 6 || Integer.parseInt(tuoi) >100  ){
                    Toast.makeText(Profile1_Activity.this, "6 < Your age < 100", Toast.LENGTH_SHORT).show();
                }
                else if (Double.parseDouble(cannang) < 20 || Double.parseDouble(cannang) >200){
                    Toast.makeText(Profile1_Activity.this, "20kg <= Your weight <= 200kg", Toast.LENGTH_SHORT).show();
                }
                else{
                    Boolean luuThongTinNguoiDung = db.capnhatThongTinNguoiDung(ten, Integer.parseInt(tuoi), gioitinh,
                            Double.parseDouble(chieucao), Double.parseDouble(cannang), session.laySDT());

                    if(luuThongTinNguoiDung == true){
                        Toast.makeText(Profile1_Activity.this, "Profile saved", Toast.LENGTH_SHORT).show();
                        db.capnhatCheDoLuyenTap(1.2, session.laySDT());
                        Intent it_profile1= new Intent(Profile1_Activity.this, BMIResult_Activity.class);
                        startActivity(it_profile1);
                    }
                    else{
                        Toast.makeText(Profile1_Activity.this, "Saving failed", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }
}