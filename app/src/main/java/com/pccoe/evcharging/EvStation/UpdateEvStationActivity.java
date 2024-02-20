package com.pccoe.evcharging.EvStation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pccoe.evcharging.databinding.ActivityUpdateEvStationBinding;
import com.pccoe.evcharging.models.EVStation;

import java.util.HashMap;
import java.util.Map;

public class UpdateEvStationActivity extends AppCompatActivity {

    ActivityUpdateEvStationBinding binding;
    private String evs_id;
    private int evs_available, evs_energy, previous_energy, type;

    int[] slot = new int[48];
    Map<String, Object> data = new HashMap<>(); //for update
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityUpdateEvStationBinding.inflate(getLayoutInflater());

        evs_id = getIntent().getStringExtra("evs_id");

        init();

        setEventLis();

        setPreviousData();

        setContentView(binding.getRoot());
    }

    private void updateData() {
        firebaseFirestore
                .collection("Owner")
                .document(firebaseAuth.getCurrentUser().getEmail())
                .collection("EV_Station")
                .document(evs_id)
                .update(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(UpdateEvStationActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                        setText();
                        setPreviousData();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdateEvStationActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setEventLis() {
        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();

                if (check() == 1) {

                    if (previous_energy > evs_energy) {
                        Toast.makeText(UpdateEvStationActivity.this, "Current Energy Should be Greater", Toast.LENGTH_SHORT).show();
                    } else {
                        data.put("evs_available", evs_available);
                        data.put("evs_energy", evs_energy);
                        data.put("type", type);

                        updateData();
                    }

                } else {
                    Toast.makeText(UpdateEvStationActivity.this, "Mandatory", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.btnAvailable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    String txt = binding.btnAvailable.getText().toString();

                    if(txt.equals("Unavailable")) evs_available = 0;
                    else evs_available = 1;
                }
            }
        });

        binding.rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = findViewById(i);

                type = Integer.parseInt(radioButton.getText().toString());
            }
        });

    }

    private void setText() {
        evs_available = evs_energy = 0;
    }

    private void setPreviousData() {

        firebaseFirestore
                .collection("Owner")
                .document(firebaseAuth.getCurrentUser().getEmail())
                .collection("EV_Station")
                .document(evs_id)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot doc) {
                        EVStation evs = doc.toObject(EVStation.class);

                        if (evs == null) return;

                        previous_energy = evs.getEvs_available();

                        type = evs.getType();

                        if(type!=0){
                            if(type==1){
                                binding.btnType1.setChecked(true);
                            }
                            if(type==2){
                                binding.btnType2.setChecked(true);
                            }
                            if(type==3){
                                binding.btnType3.setChecked(true);
                            }
                        }

                        evs_available = evs.getEvs_available();

                        if(evs_available==1){
                            binding.btnAvailable.setChecked(true);
                        }else{
                            binding.btnAvailable.setChecked(false);
                        }

                        binding.etEmail.setText(Integer.toString(evs.getEvs_energy()));
//                        binding.etAvailable.setText(Integer.toString(previous_energy));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdateEvStationActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private int check() {
        if (evs_energy == 0 || evs_available == 0 || type == 0) return 0;
        return 1;
    }

    private void getData() {
        evs_energy = Integer.parseInt(binding.etEmail.getText().toString().trim());
    }

    private void init() {
        evs_available = evs_energy = 0;
        type = 0;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
    }
}