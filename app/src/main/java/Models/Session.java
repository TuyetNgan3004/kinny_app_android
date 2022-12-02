package Models;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.sinhvien.kinny.Login_Activity;

public class Session {

    private SharedPreferences shared;

    public Session(Context context) {
        //Auto_generated contructor stub
        shared = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setSDT(String sdt) {
        shared.edit().putString("sdt", sdt).commit();
    }

    public String laySDT() {
        String sdt = shared.getString("sdt", "");
        return sdt;
    }
}

