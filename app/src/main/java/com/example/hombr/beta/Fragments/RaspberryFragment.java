package com.example.hombr.beta.Fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ProgressDialog;
import android.os.Build;
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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.hombr.beta.R;
import com.example.hombr.beta.Singletons.Singleton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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



        recon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference refe= FirebaseDatabase.getInstance().getReference().child("Facial");
                Map<String,Object> map= new HashMap<String, Object>();
                map.put("Activacion",true);
                refe.updateChildren(map);
                DatabaseReference refe2= FirebaseDatabase.getInstance().getReference().child("Facial");
                Map<String,Object> map2= new HashMap<String, Object>();
                map2.put("UsuarioActivado",Singleton.getInstance().getUser());
                refe.updateChildren(map2);

                final ProgressDialog dialog=new ProgressDialog(getActivity());
                dialog.setMessage("Detectando Rostro");
                dialog.show();

                DatabaseReference pp= FirebaseDatabase.getInstance().getReference().child("Facial").child("EntrenamientoHecho");
                pp.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if ((Boolean)dataSnapshot.getValue()==true){
                            dialog.dismiss();
//                            Toast.makeText(getActivity(), "Entrenamiento Exitoso", Toast.LENGTH_SHORT).show();
                            FragmentManager tr = getActivity().getSupportFragmentManager();
                            tr.beginTransaction().replace(R.id.escenario, new ReconFragment()).commit();
                            Singleton.getInstance().setActivacioncontrol(true);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        return Rec;
    }


}