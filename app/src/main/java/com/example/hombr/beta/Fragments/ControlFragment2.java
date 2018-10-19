package com.example.hombr.beta.Fragments;

import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.hombr.beta.Activities.MenuActivity;
import com.example.hombr.beta.Adapters.AdaptadorAcciones;
import com.example.hombr.beta.Adapters.ListItemAcciones;
import com.example.hombr.beta.R;
import com.example.hombr.beta.Singletons.Singleton;
import com.example.hombr.beta.Singletons.config;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControlFragment2 extends Fragment {
    private Vibrator v;
    private ListView list;
    private Button btn;
    private ImageView sala,comedor, cocina1,cocina2,estudio,pasillo1,pasillo2,pasillo3,bano,servicio;
    private EditText EditHab,EditSense,EditValue;
    String[] NAcciones = {"Presencia","Ambiental","Puerta","Ventana","Luz (%)","AutoLuz"};
    int [] images = {R.drawable.presencia,R.drawable.ambiental,R.drawable.puerta,R.drawable.ventana,R.drawable.iluminacion, R.drawable.corriente};

    String[] logicos = {"Apagar", "Encender"};
    String[] analogicos = {"Apagar", "Bajo", "Medio", "Alto", "Encendido Completo"};
    String[] iluminacionValores={"0","10","20","30","40","50","60","70","80","90","100"};
    String[] NO={"No aplica"};

    private Spinner spinner;
    ArrayAdapter adapterSpinner;

    //***********
    private RecyclerView recyclerView1;
    private RecyclerView.Adapter adapter1;
    private List<ListItemAcciones> listItems;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View Rec = inflater.inflate(R.layout.fragment_control2, container, false);


        Button volver=(Button) Rec.findViewById(R.id.VolverPiso);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager= getActivity().getSupportFragmentManager();
                FragmentTransaction tx = manager.beginTransaction();
                tx.replace(R.id.escenario,  new ControlFragment());
                tx.commit();
            }
        });

        return Rec;
    }

    }