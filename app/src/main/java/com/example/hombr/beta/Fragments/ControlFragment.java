package com.example.hombr.beta.Fragments;

import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.hombr.beta.Adapters.AdaptadorAcciones;
import com.example.hombr.beta.Adapters.ListItemAcciones;
import com.example.hombr.beta.R;
import com.example.hombr.beta.Singletons.Singleton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControlFragment extends Fragment {
    private ListView list;
    private Button btn;
    private ImageView sala,comedor, cocina1,cocina2,estudio,pasillo1,pasillo2,pasillo3,bano,servicio;
    private EditText EditHab,EditSense,EditValue;
    private String[] rooms = {"Cocina", "Habitacion1", "Sala", "Estudio", "Entrada", "Comedor"};
    private String[] Sense = {"Presencia", "Iluminación", "Ambiental"};
    private String[] automatizacion = {"motor", "servo", "luz", "puerta", "ventana"};

    //***********
    private RecyclerView recyclerView1;
    private RecyclerView.Adapter adapter1;
    private List<ListItemAcciones> listItems;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View Rec = inflater.inflate(R.layout.fragment_control, container, false);
        //*******
//        Resultado = Rec.findViewById(R.id.Mirror);
        btn = Rec.findViewById(R.id.BtnActualizar);
        //********Habitaciones
        sala=Rec.findViewById(R.id.ImgHabSala);
        comedor=Rec.findViewById(R.id.ImgHabComedor);
        cocina1=Rec.findViewById(R.id.ImgHabCocina1);
        cocina2=Rec.findViewById(R.id.ImgHabCocina2);
        estudio=Rec.findViewById(R.id.ImgHabEstudio);
        pasillo1=Rec.findViewById(R.id.ImgHabPasillo1);
        pasillo2=Rec.findViewById(R.id.ImgHabPasillo2);
        pasillo3=Rec.findViewById(R.id.ImgHabPasillo3);
        bano=Rec.findViewById(R.id.ImgHabBano);
        servicio=Rec.findViewById(R.id.ImgHabServicio);
        //*******EditText
        EditHab=Rec.findViewById(R.id.EditHabitacion);
        EditSense=Rec.findViewById(R.id.EditSensor);
        EditValue=Rec.findViewById(R.id.EditValor);
        EditHab.setText("cocina");
        EditSense.setText("notif");
        //**************
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizar();
            }
        });
        //DIALOGS EMERGENTES
        sala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogos();

            }
        });
        comedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogos();
            }
        });
        cocina1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogos();
            }
        });
        cocina2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogos();
            }
        });
        servicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogos();
            }
        });
        bano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogos();
            }
        });
        pasillo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogos();
            }
        });
        estudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogos();
            }
        });
        pasillo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogos();
            }
        });
        pasillo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogos();
            }
        });
        //***********************

        Singleton.getInstance().setModo("Falso");
        return Rec;
    }

    private void dialogos() {
        String[] NAcciones = {
                "Switch",
                "Presencia",
                "Ambiental",
                "Puerta",
                "Ventana",
                "Iluminación"
        };
        int [] images = {
                R.drawable.corriente,
                R.drawable.presencia,
                R.drawable.ambiental,
                R.drawable.puerta,
                R.drawable.ventana,
                R.drawable.iluminacion
        };

        String[] logicos = {"true", "false"};
        String[] analogicos = {"Apagar", "Bajo", "Medio", "Alto", "Encendido Completo"};


        final Dialog dialog= new Dialog(getActivity());
        dialog.setContentView(R.layout.dialogcasa);
        dialog.setTitle("Actividades");
        //********************************
        TextView text = (TextView) dialog.findViewById(R.id.text);
        text.setText(Singleton.getInstance().getHabitacion());
        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        recyclerView1=(RecyclerView)dialog.findViewById(R.id.listviewAcciones);
        //recyclerView1.hasFixedSize(true);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.HORIZONTAL,false));
        listItems=new ArrayList<>();
        //spinner
        Spinner spinner=(Spinner)dialog.findViewById(R.id.spinner);
        ArrayAdapter adapterSpinner = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, logicos);
        //Carga valores al list view custom
        for (int i=0; i<images.length; i++){
            ListItemAcciones listItem=new ListItemAcciones(
                    NAcciones[i], images[i]
            );

            listItems.add(listItem);
        }
        adapter1=new AdaptadorAcciones(listItems,getActivity());

        recyclerView1.setAdapter(adapter1);


        switch (Singleton.getInstance().getModo()){
            case "Switch":
                spinner.setAdapter(adapterSpinner);
                break;

        }




        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    private void actualizar() {
        String Habitacion=EditHab.getText().toString();
        String TipoSensor=EditSense.getText().toString();
        String valor=EditValue.getText().toString();

        if (Habitacion.isEmpty()){
            EditHab.setError("Ingresa o selecciona una habitacion");
            EditHab.requestFocus();
        }
        if (TipoSensor.isEmpty()){
            EditSense.setError("Ingresa un tipo de sensor");
            EditSense.requestFocus();
        }
        if (valor.isEmpty()){
            EditValue.setError("Ingresa el valor del sensor");
            EditValue.requestFocus();
        }

        else{

            Singleton.getInstance().setHabitacion(Habitacion);
            Singleton.getInstance().setTipo(TipoSensor);
            Singleton.getInstance().setValor(valor);


            DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Habitaciones").child(Singleton.getInstance().getHabitacion());
            Map<String,Object> map= new HashMap<String, Object>();
            map.put(TipoSensor,valor);
            ref.updateChildren(map);
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://ideorreas.mx/inmotica-domotica/"));

            PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, intent, 0);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity());
            builder.setContentIntent(pendingIntent);

            builder.setSmallIcon(R.drawable.ambiental);
            builder.setAutoCancel(true);
            builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_notifications_black_24dp));
            builder.setContentTitle("Cambio de Valor");
            builder.setContentText("Se ha actualizado en " + Singleton.getInstance().getHabitacion() +" de la acción " +Singleton.getInstance().getTipo() +" con el valor de " +Singleton.getInstance().getValor());
            builder.setSubText("Presiona para abrir el mapa");

            NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(
                    getActivity().NOTIFICATION_SERVICE);
            notificationManager.notify(1, builder.build());


            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //Resultado.setText(dataSnapshot.getValue().toString());
                    //nino=dataSnapshot.getValue().toString();
                    //Toast.makeText(getActivity(), "Dato Actualizado", Toast.LENGTH_SHORT).show();
                    EditHab.getText().clear();
                    EditSense.getText().clear();
                    EditValue.getText().clear();


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });}
    }



}