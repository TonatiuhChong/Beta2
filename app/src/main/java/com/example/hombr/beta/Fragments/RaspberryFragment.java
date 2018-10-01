package com.example.hombr.beta.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.hombr.beta.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class RaspberryFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View Rec= inflater.inflate(R.layout.fragment_raspberry,container,false);
        Button btn=(Button)Rec.findViewById(R.id.button3);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ActivarCamara= FirebaseDatabase.getInstance().getReference().child("Facial");
                Map<String,Object> map= new HashMap<String, Object>();
                map.put("Activacion",false);
                ActivarCamara.updateChildren(map);
                FragmentManager tr= getActivity().getSupportFragmentManager();
                tr.beginTransaction().replace(R.id.escenario, new Recon2Fragment()).commit();

            }
        });

        return Rec;
    }





}