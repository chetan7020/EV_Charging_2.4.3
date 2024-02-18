package com.pccoe.evcharging;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.pccoe.evcharging.EvStation.GetEvStationsActivity;
import com.pccoe.evcharging.EvStation.UpdateEvStationActivity;
import com.pccoe.evcharging.databinding.ActivityMainBinding;
import com.pccoe.evcharging.payment.ViewAllPaymentsActivity;
import com.pccoe.evcharging.review.GetAllReveiwsActivity;

public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setEventLis();

        setContentView(binding.getRoot());
    }

    private void setEventLis() {
        binding.btnManageEV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, GetEvStationsActivity.class));
            }
        });

        binding.btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });

        binding.btnGetAllReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, GetAllReveiwsActivity.class));
            }
        });

        binding.btnPaymentHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ViewAllPaymentsActivity.class));
            }
        });
    }
}