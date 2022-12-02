package Models;

import android.view.View;

import java.util.ArrayList;

public class HistoryWeight {
    private double weight;
    private double bmi;
    private String date;



    public HistoryWeight(double weight, double bmi, String date) {

        this.weight = weight;
        this.bmi = bmi;
        this.date = date;
    }
    public HistoryWeight(){

    }


    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getBmi() {
        return bmi;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public static ArrayList initHW(){
        ArrayList<HistoryWeight> arrayList = new ArrayList<>();
//        arrayList.add(new HistoryWeight("55", "20.7", "03/11/2021"));
//        arrayList.add(new HistoryWeight("54.3", "20.4", "10/11/2021"));
        return arrayList;
    }


}
