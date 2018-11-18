package com.example.hombr.beta.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hombr.beta.R;
import com.example.hombr.beta.Singletons.Singleton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class RaspberryFragment extends Fragment {

    ImageView btn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View Rec = inflater.inflate(R.layout.fragment_raspberry, container, false);
         btn = (ImageView) Rec.findViewById(R.id.volver);
         Button recon=(Button)Rec.findViewById(R.id.IniciarRecon);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ActivarCamara = FirebaseDatabase.getInstance().getReference().child("Facial");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("Activacion", false);
                ActivarCamara.updateChildren(map);
                FragmentManager tr = getActivity().getSupportFragmentManager();
                tr.beginTransaction().replace(R.id.escenario, new ReconFragment()).commit();

            }
        });
        recon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Singleton.getInstance().setActivacioncontrol(true);
                Toast.makeText(getActivity(), "Validacion Completa", Toast.LENGTH_SHORT).show();
            }
        });


        return Rec;
    }

}