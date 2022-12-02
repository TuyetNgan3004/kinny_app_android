package Models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sinhvien.kinny.AddWeight_Activity;
import com.sinhvien.kinny.R;

import java.util.ArrayList;

public class HWAdapter extends ArrayAdapter {
    Context context;
    ArrayList<HistoryWeight> arrayList;
    int layout;

    public HWAdapter(@NonNull Context context, int resource, @NonNull ArrayList<HistoryWeight> objects) {
        super(context, resource, objects);
        this.context = context;
        this.arrayList = objects;
        this.layout = resource;
    }

//    @Override
//    public int getCount() {
//        return AddWeight_Activity.arrayList.size();
//    }
//
//    @Nullable
//    @Override
//    public Object getItem(int position) {
//        return AddWeight_Activity.arrayList.get(position);
//    }

//    @Override
//    public long getItemId(int position) {
//        return AddWeight_Activity.arrayList.get(position).getId();
//    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        HistoryWeight hw = arrayList.get(position);

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(layout,null);
        }

        TextView tv_Weight = convertView.findViewById(R.id.tv_list_Weight);
        tv_Weight.setText(String.valueOf(hw.getWeight()));
        TextView tv_BMI = convertView.findViewById(R.id.tv_list_BMI);
        tv_BMI.setText(String.valueOf(String.format("%,.2f", hw.getBmi())));
        TextView tv_Date = convertView.findViewById(R.id.tv_list_Date);
        tv_Date.setText(hw.getDate());

        return convertView;
    }

}