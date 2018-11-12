package com.example.hombr.beta.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.example.hombr.beta.Singletons.Singleton;
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

        getActivity().getWindow().setNavigationBarColor(getResources().getColor(R.color.Mix1));
        getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.Mix1));


if (Singleton.getInstance().isActivacioncontrol()==true) {
    Cuartos = (TextView) Rec.findViewById(R.id.FragmentoSensorCuarto);

    listas = (ListView) Rec.findViewById(R.id.FragmentListaSensores);
    TextView prueba = (TextView) Rec.findViewById(R.id.FSsensores);

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


        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });


    listas.setClickable(true);
    listas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Singleton.getInstance().setTipo(listas.getItemAtPosition(position).toString());
            FragmentManager tr = getActivity().getSupportFragmentManager();
            tr.beginTransaction().replace(R.id.escenario, new SensoresDeHabitacion(), "Sensores").commit();
            getActivity().getSupportFragmentManager().popBackStack();

        }
    });
}else {

    final AlertDialog.Builder alert= new AlertDialog.Builder(getActivity());
    alert.setTitle("Acceso restringido").setMessage("Necesitas usar el reconocimiento facial para accesar al control")
            .setPositiveButton("Ir", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FragmentManager tr= getActivity().getSupportFragmentManager();
                    tr.beginTransaction().replace(R.id.escenario, new ReconFragment()).commit();
                }
            }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    });
    alert.show();
}
        return Rec;
    }



}


