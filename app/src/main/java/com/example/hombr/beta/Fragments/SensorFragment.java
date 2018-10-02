package com.example.hombr.beta.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hombr.beta.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class SensorFragment extends Fragment {

    private ListView listas;
    private TextView Cuartos, Sensores;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> sensores = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View Rec = inflater.inflate(R.layout.fragment_sensor, container, false);

        Cuartos = (TextView) Rec.findViewById(R.id.FragmentoSensorCuarto);
        Sensores = (TextView) Rec.findViewById(R.id.FragmentoSensorCantidad);
        listas = (ListView) Rec.findViewById(R.id.FragmentListaSensores);
        TextView prueba=(TextView)Rec.findViewById(R.id.FSsensores);

        arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, sensores);
        listas.setAdapter(arrayAdapter);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Habitaciones");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Set<String> set = new HashSet<String>();
                Iterator i = dataSnapshot.getChildren().iterator();
                while (i.hasNext()) {
                    set.add(((DataSnapshot) i.next()).getKey());
                }
                sensores.clear();
                sensores.addAll(set);
                arrayAdapter.notifyDataSetChanged();
                Cuartos.setText(getResources().getString(R.string.Totalhabitaciones) + " " + sensores.size());
                //Sensores.setText(@string/Habitacion"+" "+sensores.size());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        listas.setClickable(true);
        listas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager tr= getActivity().getSupportFragmentManager();
                tr.beginTransaction().replace(R.id.escenario, new Recon2Fragment()).commit();

            }
        });

        return Rec;
    }


    private void lista(){

        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Habitaciones");

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}


