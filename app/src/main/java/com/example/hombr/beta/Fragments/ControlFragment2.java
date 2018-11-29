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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hombr.beta.Activities.MenuActivity;
import com.example.hombr.beta.Adapters.AdaptadorAcciones;
import com.example.hombr.beta.Adapters.AdaptadorAcciones2;
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
    private ImageView cuarto1,cuart2,cuarto3;
    private EditText EditHab,EditSense,EditValue;
    String[] NAcciones = {"Puerta","Ventana","Luz","AutoLuz"};
    int [] images = {R.drawable.puerta,R.drawable.ventana,R.drawable.iluminacion, R.drawable.corriente};
    String[] logicos2 = {"Activar", "Desactivar"};
    String[] logicos = {"Cerrar", "Abrir"};
    String[] analogicos = {"Apagar", "Bajo", "Medio", "Alto", "Encendido Completo"};
    String[] iluminacionValores={"0","10","20","30","40","50","60","70","80","90","100"};
    String[] NO={"No aplica"};
    String[]hab={"Cuarto1","Cuarto2","Cuarto3"};

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

        final ControlFragment2 test=(ControlFragment2)getActivity().getSupportFragmentManager().findFragmentByTag("Control");
        if (test != null && test.isVisible()){

        Toast.makeText(getActivity(), "Control2", Toast.LENGTH_SHORT).show();}


        Button volver=(Button) Rec.findViewById(R.id.VolverPiso);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager= getActivity().getSupportFragmentManager();
                FragmentTransaction tx = manager.beginTransaction();
                tx.replace(R.id.escenario, new ControlFragment(), "Control2")
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null).commit();

            }
        });
        cuarto1=(ImageView)Rec.findViewById(R.id.cuartoyo);
        cuarto1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Singleton.getInstance().setHabitacion("Cuarto1");
                dialogos();
            }
        });
        
        cuart2=(ImageView)Rec.findViewById(R.id.cuartochali);
        cuart2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Singleton.getInstance().setHabitacion("Cuarto2");
                dialogos();
            }
        });
        cuarto3=(ImageView)Rec.findViewById(R.id.cuartomama);
        cuarto3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Singleton.getInstance().setHabitacion("Cuarto3");
                dialogos();
            }
        });
        Fragment frag= getActivity().getSupportFragmentManager().findFragmentByTag("Control2");
        String tagg= frag.getTag();

        Switch regulador=(Switch) Rec.findViewById(R.id.AutoLuzAlta);

        if(config.getInstance().isAutoluz2()==true)regulador.setChecked(true);
        else regulador.setChecked(false);
        regulador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean on=((Switch)v).isChecked();

                if(on){
                    config.getInstance().setAutoluz2(true);
                    for (int p=0;p<hab.length;p++){
                        DatabaseReference auto=FirebaseDatabase.getInstance().getReference().child("Habitaciones").child(hab[p].toString());
                        Map<String,Object> map= new HashMap<String, Object>();
                        map.put("AutoLuz",true);
                        auto.updateChildren(map);
                    }}
                else{
                    config.getInstance().setAutoluz2(false);
                    for (int p=0;p<hab.length;p++){
                        DatabaseReference auto=FirebaseDatabase.getInstance().getReference().child("Habitaciones").child(hab[p].toString());
                        Map<String,Object> map= new HashMap<String, Object>();
                        map.put("AutoLuz",false);
                        auto.updateChildren(map);
                    }

                }
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
        adapter1=new AdaptadorAcciones2(listItems,getActivity(),this);
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
        if (Singleton.getInstance().getModo()=="Luz"){
            int p;
            p=Integer.parseInt(Singleton.getInstance().getValor());
            map.put(Singleton.getInstance().getModo(),p);

        }else if(Singleton.getInstance().getModo()=="AutoLuz"){
            if (Singleton.getInstance().getValor()=="Cerrar"){
                map.put(Singleton.getInstance().getModo(),false);
            }
            else {
                map.put(Singleton.getInstance().getModo(),true);
            }
        }

        else {
            map.put(Singleton.getInstance().getModo(),Singleton.getInstance().getValor());
        }

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
        datos();
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
                        adapterSpinner = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, logicos2);
                        OpcionSeleccionada(adapterSpinner,spinner);
                        Singleton.getInstance().setAccionExtra((String) dataSnapshot.getValue());

                        break;
                    case "Presencia":
                        adapterSpinner = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, NO);
                       // OpcionSeleccionada(adapterSpinner,spinner);
                        Singleton.getInstance().setAccionExtra((String) dataSnapshot.getValue());
                        break;
                    case "Ambiental":
                        adapterSpinner = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, NO);
                        //OpcionSeleccionada(adapterSpinner,spinner);
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

    private void datos() {
        DatabaseReference lec1=FirebaseDatabase.getInstance().getReference().child("Habitaciones").child("Cuarto1").child("Presencia");
        DatabaseReference lec2=FirebaseDatabase.getInstance().getReference().child("Habitaciones").child("Cuarto2").child("Presencia");
        DatabaseReference lec3=FirebaseDatabase.getInstance().getReference().child("Habitaciones").child("Cuarto3").child("Presencia");

        lec1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if((Boolean)dataSnapshot.getValue()==true && cuarto1 != null){cuarto1.setBackgroundColor(getResources().getColor(R.color.Presencia));}
                else {cuarto1.setBackgroundColor(0);}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        lec2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                if((Boolean)dataSnapshot.getValue()==true && cuart2 != null){cuart2.setBackgroundColor(getResources().getColor(R.color.Presencia));}
                else {cuart2.setBackgroundColor(0);}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        lec3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if((Boolean)dataSnapshot.getValue()==true && cuarto3 != null){cuarto3.setBackgroundColor(getResources().getColor(R.color.Presencia));}
                else {cuarto3.setBackgroundColor(0);}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//LUCES
        DatabaseReference lec11=FirebaseDatabase.getInstance().getReference().child("Habitaciones").child("Cuarto1").child("Luz");
        DatabaseReference lec21=FirebaseDatabase.getInstance().getReference().child("Habitaciones").child("Cuarto2").child("Luz");
        DatabaseReference lec31=FirebaseDatabase.getInstance().getReference().child("Habitaciones").child("Cuarto3").child("Luz");

        lec11.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(Integer.parseInt(dataSnapshot.getValue().toString())>10&& cuarto1 != null){cuarto1.setBackgroundColor(getResources().getColor(R.color.Luz));}
                else {cuarto1.setBackgroundColor(0);}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        lec21.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                if(Integer.parseInt(dataSnapshot.getValue().toString())>10 && cuart2 != null){cuart2.setBackgroundColor(getResources().getColor(R.color.Presencia));}
                else {cuart2.setBackgroundColor(0);}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        lec31.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(Integer.parseInt(dataSnapshot.getValue().toString())>10 && cuarto3 != null){cuarto3.setBackgroundColor(getResources().getColor(R.color.Presencia));}
                else {cuarto3.setBackgroundColor(0);}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}