package com.pccoe.evcharging.EnergySold;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pccoe.evcharging.databinding.ActivityEnergySoldChartBinding;

import java.util.ArrayList;

public class EnergySoldChartActivity extends AppCompatActivity {

    private ActivityEnergySoldChartBinding binding;
    private int mani_amount, mani_energy_sold, mani_user_served;
    private ArrayList<Entry> amount, energy_sold, user_served;
    private ArrayList<ILineDataSet> iLineDataSets;
    private LineData lineData;
    private LineDataSet lineDataSet_amount, lineDataSet_energy_sold, lineDataSet_user_served;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEnergySoldChartBinding.inflate(getLayoutInflater());

        init();

        setContentView(binding.getRoot());
    }

    private void setEventList() {
        binding.btnAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchAmount();
            }
        });

        binding.btnEnergySold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchEnergySold();
            }
        });

        binding.btnUserServed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchUserServed();
            }
        });
    }

    private void switchAmount() {

        if (mani_amount == 1) {
            iLineDataSets.remove(lineDataSet_amount);
        } else {
            if (amount == null) return;
            lineDataSet_amount = new LineDataSet(amount, "Amount");
            iLineDataSets.add(lineDataSet_amount);
        }

        binding.lcChat.invalidate();

        mani_amount = 1 - mani_amount;
    }

    private void switchEnergySold() {

        if (mani_energy_sold == 1) {
            iLineDataSets.remove(lineDataSet_energy_sold);
        } else {
            if (energy_sold == null) return;
            lineDataSet_energy_sold = new LineDataSet(energy_sold, "Energy Sold");
            iLineDataSets.add(lineDataSet_energy_sold);
        }

        binding.lcChat.invalidate();

        mani_energy_sold = 1 - mani_energy_sold;
    }

    private void switchUserServed() {

        if (mani_user_served == 1) {
            iLineDataSets.remove(lineDataSet_user_served);
        } else {
            if (user_served == null) return;
            lineDataSet_user_served = new LineDataSet(user_served, "User Served");
            iLineDataSets.add(lineDataSet_user_served);
        }

        binding.lcChat.invalidate();

        mani_user_served = 1 - mani_user_served;
    }

    private void setChartData() {
        dataValues_amount();
        dataValues_energy_sold();
        dataValues_user_served();

        //Amount
        if (amount == null) return;
        lineDataSet_amount = new LineDataSet(amount, "Amount");
        iLineDataSets.add(lineDataSet_amount);

        //Energy Sold
        if (energy_sold == null) return;
        lineDataSet_energy_sold = new LineDataSet(energy_sold, "Energy Sold");
        iLineDataSets.add(lineDataSet_energy_sold);

        //User Served
        if (user_served == null) return;
        lineDataSet_user_served = new LineDataSet(user_served, "User Served");
        iLineDataSets.add(lineDataSet_user_served);


        lineData = new LineData(iLineDataSets);

        binding.lcChat.setData(lineData);
        binding.lcChat.invalidate();
    }

    private void dataValues_amount() {
        amount = new ArrayList<>();

        amount.add(new Entry(1, 20));
        amount.add(new Entry(2, 100));
        amount.add(new Entry(3, 200));
        amount.add(new Entry(4, 10));
        amount.add(new Entry(5, 80));
        amount.add(new Entry(6, 180));
        amount.add(new Entry(7, 200));
        amount.add(new Entry(8, 25));
        amount.add(new Entry(9, 75));
        amount.add(new Entry(10, 110));
        amount.add(new Entry(11, 230));
        amount.add(new Entry(12, 390));
    }

    private void dataValues_energy_sold() {
        energy_sold = new ArrayList<>();

        energy_sold.add(new Entry(1, 200));
        energy_sold.add(new Entry(2, 10));
        energy_sold.add(new Entry(3, 230));
        energy_sold.add(new Entry(4, 390));
        energy_sold.add(new Entry(5, 20));
        energy_sold.add(new Entry(6, 80));
        energy_sold.add(new Entry(7, 180));
        energy_sold.add(new Entry(8, 300));
        energy_sold.add(new Entry(9, 25));
        energy_sold.add(new Entry(10, 75));
        energy_sold.add(new Entry(11, 110));
        energy_sold.add(new Entry(12, 100));
    }

    private void dataValues_user_served() {
        user_served = new ArrayList<>();

        user_served.add(new Entry(1, 110));
        user_served.add(new Entry(2, 10));
        user_served.add(new Entry(3, 180));
        user_served.add(new Entry(4, 20));
        user_served.add(new Entry(5, 100));
        user_served.add(new Entry(6, 80));
        user_served.add(new Entry(7, 200));
        user_served.add(new Entry(8, 230));
        user_served.add(new Entry(9, 390));
        user_served.add(new Entry(10, 25));
        user_served.add(new Entry(11, 290));
        user_served.add(new Entry(12, 30));
    }

    private void init() {
        mani_amount = mani_energy_sold = mani_user_served = 1;

        iLineDataSets = new ArrayList<>();

        //amount
        amount = null;
        lineDataSet_amount = null;

        //energy_sold
        energy_sold = null;
        lineDataSet_energy_sold = null;

        //user_served
        user_served = null;
        lineDataSet_user_served = null;

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
    }
}