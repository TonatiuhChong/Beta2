package com.example.hombr.beta.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.hombr.beta.Adapters.AdaptadorSensoresValues;
import com.example.hombr.beta.Adapters.AdaptadorUsuarios;
import com.example.hombr.beta.Adapters.ListItemUsuarios;
import com.example.hombr.beta.Adapters.ListItemValuesSensor;
import com.example.hombr.beta.R;
import com.example.hombr.beta.Singletons.Singleton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class ReconFragment extends Fragment {


    private TextView Cuartos;
    private TextView Sensores;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> sensores = new ArrayList<>();
    //----------------------------------------
    private RecyclerView rp;
    private RecyclerView.Adapter adapter;
    private List<ListItemValuesSensor> values;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {

        View Rec= inflater.inflate(R.layout.fragment_recon,container,false);
        Cuartos = (TextView) Rec.findViewById(R.id.FS2TotalSHab);
        Sensores = (TextView) Rec.findViewById(R.id.FS2TotalSens);
        ListView listas = (ListView) Rec.findViewById(R.id.FSkeysFirebase);
        arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, sensores);
        listas.setAdapter(arrayAdapter);


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Habitaciones").child(Singleton.getInstance().getTipo());
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

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        rp = (RecyclerView) Rec.findViewById(R.id.FSValuesFirebase);
        rp.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL,false));
        values= new ArrayList<>();
        DatabaseReference ref2= FirebaseDatabase.getInstance().getReference().child("Habitaciones").child(Singleton.getInstance().getTipo());
        adapter = new AdaptadorSensoresValues(values,getActivity());
        rp.setAdapter(adapter);
        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                values.removeAll(values);
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    ListItemValuesSensor valoress=snapshot.getValue(ListItemValuesSensor.class);
                    values.add(valoress);


                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        return Rec;
    }


}