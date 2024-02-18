package com.pccoe.evcharging.payment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.pccoe.evcharging.databinding.ActivityViewAllPaymentsBinding;
import com.pccoe.evcharging.models.EVStation;
import com.pccoe.evcharging.models.Payment;

import java.util.ArrayList;
import java.util.List;

public class ViewAllPaymentsActivity extends AppCompatActivity {

    private ActivityViewAllPaymentsBinding binding;
    private List<Payment> payments;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityViewAllPaymentsBinding.inflate(getLayoutInflater());

        init();

        setEventLis();

        getAllPayments();

        setContentView(binding.getRoot());
    }

    private void getAllPayments() {
        firebaseFirestore
                .collection("Owner")
                .document(firebaseAuth.getCurrentUser().getEmail())
                .collection("Payments")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot snaps) {
                        if(snaps==null) return;

                        payments.addAll(snaps.toObjects(Payment.class));

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    private void setEventLis() {

    }

    private void init() {
        payments = new ArrayList<>();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

}