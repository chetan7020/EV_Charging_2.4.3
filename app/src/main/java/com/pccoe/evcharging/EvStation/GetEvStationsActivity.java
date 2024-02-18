package com.pccoe.evcharging.EvStation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.pccoe.evcharging.databinding.ActivityGetEvStationsBinding;
import com.pccoe.evcharging.models.EVStation;

import java.util.ArrayList;
import java.util.List;

public class GetEvStationsActivity extends AppCompatActivity {

    ActivityGetEvStationsBinding binding;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    List<EVStation> evStations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityGetEvStationsBinding.inflate(getLayoutInflater());

        init();

        getAllStations();

        setContentView(binding.getRoot());
    }

    private void getAllStations() {
        firebaseFirestore
                .collection("Owner")
                .document(firebaseAuth.getCurrentUser().getEmail())
                .collection("EV_Station")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot snaps) {
                        if (snaps == null) return;
                        evStations.addAll(snaps.toObjects(EVStation.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(GetEvStationsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }); 
    }

    private void init() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
    }
}