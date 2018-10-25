package com.example.hombr.beta.Fragments;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.Toast;

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

public class ControlFragment extends Fragment {
    private Vibrator v;
    private ListView list;
    private Button btn;
    private ImageView sala,comedor, cocina1,cocina2,estudio,pasillo1,pasillo2,pasillo3,bano,servicio;
    private EditText EditHab,EditSense,EditValue;
    String[] NAcciones = {"Presencia","Ambiental","Puerta","Ventana","Luz","AutoLuz"};
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
        View Rec = inflater.inflate(R.layout.fragment_control, container, false);
        //*******
        Singleton.getInstance().setModo("Luz");
        config.getInstance().setNotif(true);
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

        TextView cambio=Rec.findViewById(R.id.CambioPiso);
        cambio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager= getActivity().getSupportFragmentManager();
                FragmentTransaction tx = manager.beginTransaction();
                tx.replace(R.id.escenario,  new ControlFragment2());
                tx.commit();
            }
        });

        sala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Singleton.getInstance().setHabitacion("Sala");
                dialogos();

            }
        });
        comedor.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Singleton.getInstance().setHabitacion("Comedor");
                dialogos();
            }
        });
        cocina1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Singleton.getInstance().setHabitacion("Cocina Parte 1");
                dialogos();
            }
        });
        cocina2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Singleton.getInstance().setHabitacion("Cocina Parte 2");
                dialogos();
            }
        });
        servicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Singleton.getInstance().setHabitacion("Servicio");
                dialogos();
            }
        });
        bano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
//                Singleton.getInstance().setHabitacion("Sanitario");
//                dialogos();
                Toast.makeText(getContext(), "No esta disponible", Toast.LENGTH_SHORT).show();
            }
        });
        pasillo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Singleton.getInstance().setHabitacion("Pasillo 1");
                dialogos();
            }
        });
        estudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Singleton.getInstance().setHabitacion("Estudio");
                dialogos();
            }
        });
        pasillo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Singleton.getInstance().setHabitacion("Pasillo 2");
                dialogos();
            }
        });
        pasillo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Singleton.getInstance().setHabitacion("Entrada");
                dialogos();
            }
        });

        return Rec;
    }

    private void dialogos() {

        final Dialog dialog= new Dialog(getActivity());
        dialog.setContentView(R.layout.dialogcasa);
        dialog.setTitle("Actividades");
        //********************************
        TextView text = (TextView) dialog.findViewById(R.id.DialogoHabitacion);
        final TextView info = (TextView) dialog.findViewById(R.id.InformacionDialogo);
        text.setText(Singleton.getInstance().getHabitacion());

        if(Singleton.getInstance().getAccionExtra()==null){info.setText("Seleccione una acción para ver sus detalles.");}
        else{info.setText("La accion"+Singleton.getInstance().getModo()+"Actualmente tiene el valor de "+Singleton.getInstance().getAccionExtra());}
        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        Button cancelButton =(Button) dialog.findViewById(R.id.dialogButtonCancel);



        recyclerView1=(RecyclerView)dialog.findViewById(R.id.listviewAcciones);
        //recyclerView1.hasFixedSize(true);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.HORIZONTAL,false));
        listItems=new ArrayList<>();
        //spinner
        spinner=(Spinner)dialog.findViewById(R.id.spinner);
        adapterSpinner = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, NO);
        //Carga valores al list view custom
        for (int i=0; i<images.length; i++){
            ListItemAcciones listItem=new ListItemAcciones(
                    NAcciones[i], images[i]
            );

            listItems.add(listItem);
        }
        adapter1=new AdaptadorAcciones(listItems,getActivity(),this);
        recyclerView1.setAdapter(adapter1);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               FirebaseConexion();
                dialog.dismiss();

            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void FirebaseConexion() {
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Habitaciones").child(Singleton.getInstance().getHabitacion());
        Map<String,Object> map= new HashMap<String, Object>();
        map.put(Singleton.getInstance().getModo(),Singleton.getInstance().getValor());
        ref.updateChildren(map);
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://ideorreas.mx/inmotica-domotica/"));
        Intent home= new Intent(getContext(), MenuActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, home, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity());
        builder.setContentIntent(pendingIntent);

        builder.setSmallIcon(R.drawable.ambiental);
        builder.setAutoCancel(true);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_notifications_black_24dp));
        builder.setContentTitle("Cambio de Valor");
        builder.setContentText("Se ha actualizado en " + Singleton.getInstance().getHabitacion() +" de la acción " +Singleton.getInstance().getModo() +" con el valor de " +Singleton.getInstance().getValor());
        builder.setSubText("Presiona para abrir el mapa");


        if (config.getInstance().isNotif()==true) {

            NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(
                    getActivity().NOTIFICATION_SERVICE);
            notificationManager.notify(1, builder.build());
            v = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(500);

        }
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public  void pintar (){
        DatabaseReference informe=FirebaseDatabase.getInstance().getReference()
                .child(Singleton.getInstance().getHabitacion())
                .child(Singleton.getInstance().getModo());

        informe.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                switch (Singleton.getInstance().getModo()){
                    case "AutoLuz":
                        adapterSpinner = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, logicos);
                        OpcionSeleccionada(adapterSpinner,spinner);
                        Singleton.getInstance().setAccionExtra((String) dataSnapshot.getValue());
                        break;
                    case "Presencia":
                        adapterSpinner = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, NO);
                        OpcionSeleccionada(adapterSpinner,spinner);
                        Singleton.getInstance().setAccionExtra((String) dataSnapshot.getValue());
                        break;
                    case "Ambiental":
                        adapterSpinner = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, NO);
                        OpcionSeleccionada(adapterSpinner,spinner);
                        Singleton.getInstance().setAccionExtra((String) dataSnapshot.getValue());
                        break;
                    case "Puerta":
                        if(Singleton.getInstance().getHabitacion()=="Entrada"){
                            adapterSpinner = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, logicos);
                            OpcionSeleccionada(adapterSpinner,spinner);
                        }
                        else{ adapterSpinner = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, NO);
                            OpcionSeleccionada(adapterSpinner,spinner);}
                        break;
                    case "Ventana":
                        if(Singleton.getInstance().getHabitacion()=="Sala"){
                            adapterSpinner = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, analogicos);
                            OpcionSeleccionada(adapterSpinner,spinner);}
                        else{
                            adapterSpinner = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, NO);
                            OpcionSeleccionada(adapterSpinner,spinner);
                        }
                        break;
                    case "Luz":
                        adapterSpinner = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, iluminacionValores);
                        OpcionSeleccionada(adapterSpinner,spinner);
                        Singleton.getInstance().setAccionExtra((String) dataSnapshot.getValue());
                        break;
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    private void OpcionSeleccionada(ArrayAdapter adapterSpinner, Spinner spinner) {

        spinner.setAdapter(adapterSpinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Singleton.getInstance().setValor((String)parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}