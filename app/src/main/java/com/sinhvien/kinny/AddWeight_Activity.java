package com.sinhvien.kinny;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import Database.DBHelper;
import Models.HWAdapter;
import Models.HistoryWeight;
import Models.NguoiDung;
import Models.Session;

public class AddWeight_Activity extends AppCompatActivity {

    ListView listView;
    HWAdapter hwAdapter;
    public static ArrayList<HistoryWeight> historyWeights;
    ImageButton btn_backHome;
    Button addWeight;
    EditText et_weight;
    String canNang;
    DBHelper db;
    Session session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_weight);

        addWeight = findViewById(R.id.btn_main_AddW2);
        et_weight = findViewById(R.id.txt_add_Weight);
        listView = findViewById(R.id.listView);
        historyWeights = new ArrayList<HistoryWeight>();
//        historyWeights = HistoryWeight.initHW();
        hwAdapter = new HWAdapter(this, R.layout.item_listview, historyWeights);

        listView.setAdapter(hwAdapter);
        session = new Session(this);
        db = new DBHelper(this);

        capNhatDuLieu();
        if(historyWeights != null){
            listView.setAdapter(hwAdapter);
        }

        btn_backHome = findViewById(R.id.btn_backHome4);

        btn_backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent it_main = new Intent(AddWeight_Activity.this, MainActivity.class);
                startActivity(it_main);
            }
        });
        addWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cannang = et_weight.getText().toString();
                String ngayThem = themNgayBatDau();
                double bmi = tinhBMI();

                if (Double.parseDouble(cannang) < 20 || Double.parseDouble(cannang) >200){
                    Toast.makeText(AddWeight_Activity.this, "20kg <= Your weight <= 200kg", Toast.LENGTH_SHORT).show();
                }
                else if (cannang.equals("")){
                    Toast.makeText(AddWeight_Activity.this, "Please enter your weight", Toast.LENGTH_SHORT).show();
                }
                else {
                    themLSCanNang(v);
                    Toast.makeText(getApplicationContext(), "Inserted", Toast.LENGTH_SHORT).show();
                    if (historyWeights != null) {
                        listView.setAdapter(new HWAdapter(AddWeight_Activity.this, R.layout.item_listview, historyWeights));
                    }
                }
            }
        });
    }



    @SuppressLint("Range")
    public NguoiDung layDuLieuNguoiDung () {
        Cursor cursor = db.layTatcaDuLieuNguoiDung(session.laySDT());

        if(cursor != null ) {
            while(cursor.moveToNext() ) {
                NguoiDung nguoiDung = new NguoiDung();
                nguoiDung.set_chieucao(Double.parseDouble(cursor.getString(cursor.getColumnIndex(db.COT_CHIEUCAO))));

                return nguoiDung;
            }
        }
        return null;
    }

    @SuppressLint("Range")
    public HistoryWeight layDuLieuCanNang () {

        String cannang = et_weight.getText().toString();
        String ngayThem = themNgayBatDau();
        double bmi = tinhBMI();

        HistoryWeight historyWeight = new HistoryWeight();

        historyWeight.setWeight(Double.parseDouble(cannang));
        historyWeight.setBmi(bmi);
        historyWeight.setDate(ngayThem);
        return historyWeight;
    }

    @SuppressLint("Range")
    public void capNhatDuLieu() {
        if (historyWeights == null) {
            historyWeights = new ArrayList<HistoryWeight>();
        } else {
            historyWeights.removeAll(historyWeights);
        }
        // Lấy dữ liệu, dùng Cursor nhận lại
        Cursor cursor = db.layDuLieuCanNang(session.laySDT());

        if (cursor != null) {
            /*
             * Di chuyển đến từng dòng dữ liệu
             *  thông qua phương thức moveToNext
             */
            while (cursor.moveToNext()) {
                HistoryWeight cannang = new HistoryWeight();

                /*
                 * Mỗi dòng dữ liệu chúng ra sẽ lấy
                 *  theo cột và gán vào đối tượng
                 *  SinhVien
                 */
                // sinhVien.set_id(Long.parseLong(cursor.getString(cursor.getColumnIndex(DBHelper.COT_ID))));
                cannang.setWeight(Double.parseDouble(cursor.getString(cursor.getColumnIndex(DBHelper.COT_CANNANG))));
                cannang.setBmi(Double.parseDouble(cursor.getString(cursor.getColumnIndex(DBHelper.COT_BMI))));
                cannang.setDate(cursor.getString(cursor.getColumnIndex(DBHelper.COT_NGAYTHEM)));

                // thêm vào danh sách SinhVien
                historyWeights.add(cannang);
            }
        }
    }
//    @SuppressLint("Range")
//    public void hienThiDuLieuCanNang () {
//
//        if(arrayList == null) {
//           arrayList = new ArrayList<HistoryWeight>();
//        }
//        else {
//            arrayList.removeAll(arrayList);
//        }
//        Cursor cursor = db.layDuLieuCanNang(session.laySDT());
//       if(cursor != null){
//           while (cursor.moveToNext()){
//               HistoryWeight historyWeight = new HistoryWeight();
//
//               historyWeight.setWeight(cursor.getString(cursor.getColumnIndex(DBHelper.COT_CANNANG)));
//               historyWeight.setBmi(cursor.getString(cursor.getColumnIndex(DBHelper.COT_BMI)));
//               historyWeight.setDate(cursor.getString(cursor.getColumnIndex(DBHelper.COT_NGAYTHEM)));
//               arrayList.add(historyWeight);
//           }
//       }
//    }

    public Double tinhBMI(){
        NguoiDung nguoiDung1 = layDuLieuNguoiDung();

        if(nguoiDung1 != null ){
            double cannang = Double.parseDouble(et_weight.getText().toString());
            double chieucao = nguoiDung1.get_chieucao()/100;
            double bmi = cannang/(chieucao*chieucao);
            return bmi;
        }
        return 0.0;
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

    public void themLSCanNang(View view){
        HistoryWeight historyWeight = layDuLieuCanNang();

        if(historyWeights == null){
            historyWeights = new ArrayList<HistoryWeight>();
        }
        if (historyWeight != null){
            if(db.themCanNang(historyWeight, session.laySDT()) != -1){
                historyWeights.add(historyWeight);
                capNhatDuLieu();

                listView.invalidateViews();
                et_weight.setText(null);
            }
        }
    }
}