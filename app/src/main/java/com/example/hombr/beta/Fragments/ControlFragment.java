package com.example.hombr.beta.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.preference.Preference;
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
import com.example.hombr.beta.Adapters.AdaptadorSensoresValues;
import com.example.hombr.beta.Adapters.ListItemAcciones;
import com.example.hombr.beta.NotificationHelper;
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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

public class ControlFragment extends Fragment {
    private Vibrator v;
    private ListView list;
    private Button btn;
    String[] AccionesValores = {"Puerta","Ventana","Luz","AutoLuz"};
    private List<String> values;
    private ImageView Sala,Comedor, Cocina1,Cocina2,Estudio,Pasillo1,Pasillo2,Pasillo3,bano,servicio;
    private EditText EditHab,EditSense,EditValue;
    String[] NAcciones = {"Puerta","Ventana","Luz","AutoLuz"};
    int [] images = {R.drawable.puerta,R.drawable.ventana,R.drawable.iluminacion, R.drawable.corriente};
    String[] logicos = {"Apagar", "Encender"};
    String[] analogicos = {"Apagar", "Bajo", "Medio", "Alto", "Encendido Completo"};
    String[] iluminacionValores={"0","10","20","30","40","50","60","70","80","90","100"};
    String[] NO={"No aplica"};

    float var;
    boolean sala, comedor, cocina1,cocina2,estudio,pasillo1,pasillo2,entrada,sala1, comedor1, cocina11,cocina21,estudio1,pasillo11,pasillo21,entrada1;

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
        getActivity().getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
        getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));

        //*******
        Singleton.getInstance().setModo("Luz");
        config.getInstance().setNotif(true);
        //********Habitaciones
        Sala=Rec.findViewById(R.id.ImgHabSala);
        Comedor=Rec.findViewById(R.id.ImgHabComedor);
        Cocina1=Rec.findViewById(R.id.ImgHabCocina1);
        Cocina2=Rec.findViewById(R.id.ImgHabCocina2);
        Estudio=Rec.findViewById(R.id.ImgHabEstudio);
        Pasillo1=Rec.findViewById(R.id.ImgHabPasillo1);
        Pasillo2=Rec.findViewById(R.id.ImgHabPasillo2);
        Pasillo3=Rec.findViewById(R.id.ImgHabPasillo3);
        bano=Rec.findViewById(R.id.ImgHabBano);
        servicio=Rec.findViewById(R.id.ImgHabServicio);


        if (Singleton.getInstance().isActivacioncontrol()==true) {

            TextView cambio = Rec.findViewById(R.id.CambioPiso);
            cambio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final ControlFragment test = (ControlFragment) getActivity().getSupportFragmentManager().findFragmentByTag("Control");
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    FragmentTransaction tx = manager.beginTransaction();
                    tx.replace(R.id.escenario, new ControlFragment2(), "Control2");

                    tx.commit();

                    getActivity().getSupportFragmentManager().popBackStack();
                }
            });

            Sala.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Singleton.getInstance().setHabitacion("Sala");
                    dialogos();

                }
            });
            Comedor.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Singleton.getInstance().setHabitacion("Comedor");
                    dialogos();
                }
            });
            Cocina1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Singleton.getInstance().setHabitacion("Cocina Parte 1");
                    dialogos();
                }
            });
            Cocina2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Singleton.getInstance().setHabitacion("Cocina Parte 2");
                    dialogos();
                }
            });
            servicio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                Singleton.getInstance().setHabitacion("Servicio");
//                dialogos();
                    Toast.makeText(getActivity(), "No esta disponible", Toast.LENGTH_SHORT).show();
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
            Pasillo1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Singleton.getInstance().setHabitacion("Pasillo 1");
                    dialogos();
                }
            });
            Estudio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Singleton.getInstance().setHabitacion("Estudio");
                    dialogos();
                }
            });
            Pasillo2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Singleton.getInstance().setHabitacion("Pasillo 2");
                    dialogos();
                }
            });
            Pasillo3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Singleton.getInstance().setHabitacion("Entrada");
                    dialogos();
                }
            });

            Fragment frag = getActivity().getSupportFragmentManager().findFragmentByTag("Control1");
            datos();

        }
        else{
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

    private void datos() {
        final ControlFragment test=(ControlFragment)getActivity().getSupportFragmentManager().findFragmentByTag("Control");

     DatabaseReference lec1=FirebaseDatabase.getInstance().getReference().child("Habitaciones").child("Sala").child("Presencia");
     DatabaseReference lec2=FirebaseDatabase.getInstance().getReference().child("Habitaciones").child("Comedor").child("Presencia");
     DatabaseReference lec3=FirebaseDatabase.getInstance().getReference().child("Habitaciones").child("Cocina Parte 1").child("Presencia");
     DatabaseReference lec4=FirebaseDatabase.getInstance().getReference().child("Habitaciones").child("Cocina Parte 2").child("Presencia");
     DatabaseReference lec5=FirebaseDatabase.getInstance().getReference().child("Habitaciones").child("Pasillo 2").child("Presencia");
     DatabaseReference lec6=FirebaseDatabase.getInstance().getReference().child("Habitaciones").child("Pasillo 1").child("Presencia");
     DatabaseReference lec7=FirebaseDatabase.getInstance().getReference().child("Habitaciones").child("Estudio").child("Presencia");
     DatabaseReference lec8=FirebaseDatabase.getInstance().getReference().child("Habitaciones").child("Entrada").child("Presencia");

     lec1.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(DataSnapshot dataSnapshot) {

             if((Boolean)dataSnapshot.getValue()==true){Sala.setBackgroundColor(getResources().getColor(R.color.Presencia));sala1=true;}
             else {Sala.setBackgroundColor(0);sala=false;}
         }

         @Override
         public void onCancelled(DatabaseError databaseError) {

         }
     });
        lec2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if((Boolean)dataSnapshot.getValue()==true){Comedor.setBackgroundColor(getResources().getColor(R.color.Presencia));}
                else {Comedor.setBackgroundColor(0);}


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        lec3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if((Boolean)dataSnapshot.getValue()==true){Cocina1.setBackgroundColor(getResources().getColor(R.color.Presencia));}
                 else {Cocina1.setBackgroundColor(0);cocina21=false;}


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        lec4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if((Boolean)dataSnapshot.getValue()==true){Cocina2.setBackgroundColor(getResources().getColor(R.color.Presencia));cocina21=true;}
                else {Cocina2.setBackgroundColor(0);cocina21=false;}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        lec5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                if((Boolean)dataSnapshot.getValue()==true){Pasillo2.setBackgroundColor(getResources().getColor(R.color.Presencia));pasillo21=true;}
                else {Pasillo2.setBackgroundColor(0);pasillo21=false;}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        lec6.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if((Boolean)dataSnapshot.getValue()==true){Pasillo1.setBackgroundColor(getResources().getColor(R.color.Presencia)); pasillo11=true;}
                else {Pasillo1.setBackgroundColor(0);pasillo11=false;}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        lec7.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if((Boolean)dataSnapshot.getValue()==true){Estudio.setBackgroundColor(getResources().getColor(R.color.Presencia));estudio1=true;}
                else {Estudio.setBackgroundColor(0);estudio1=false;}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        lec8.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if((Boolean)dataSnapshot.getValue()==true){Pasillo3.setBackgroundColor(getResources().getColor(R.color.Presencia));}
                else {Pasillo3.setBackgroundColor(0);}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
///luz//////////////////////////////////////////////////////////////////////////////
//        DatabaseReference lec11=FirebaseDatabase.getInstance().getReference().child("Habitaciones").child("Sala").child("Luz");
//        DatabaseReference lec21=FirebaseDatabase.getInstance().getReference().child("Habitaciones").child("Comedor").child("Luz");
//        DatabaseReference lec31=FirebaseDatabase.getInstance().getReference().child("Habitaciones").child("Cocina Parte 1").child("Luz");
//        DatabaseReference lec41=FirebaseDatabase.getInstance().getReference().child("Habitaciones").child("Cocina Parte 2").child("Luz");
//        DatabaseReference lec51=FirebaseDatabase.getInstance().getReference().child("Habitaciones").child("Pasillo 2").child("Luz");
//        DatabaseReference lec61=FirebaseDatabase.getInstance().getReference().child("Habitaciones").child("Pasillo 1").child("Luz");
//        DatabaseReference lec71=FirebaseDatabase.getInstance().getReference().child("Habitaciones").child("Estudio").child("Luz");
//
//        lec11.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//
//                if(Integer.parseInt(dataSnapshot.getValue().toString())>var){Sala.setBackgroundColor(getResources().getColor(R.color.Luz));sala=true;}
//                else {Sala.setBackgroundColor(0);sala=false;}
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//        lec21.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                int p= Integer.parseInt(dataSnapshot.getValue().toString());
//                if(Integer.parseInt(dataSnapshot.getValue().toString())<10){Comedor.setBackgroundColor(getResources().getColor(R.color.Luz));comedor=true;}
//                else {Comedor.setBackgroundColor(0);comedor=false;}
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//        lec31.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//            int p= Integer.parseInt(dataSnapshot.getValue().toString());
//
//                if(p>10){
//                    Cocina1.setBackgroundColor(getResources().getColor(R.color.Luz));
//                    cocina1=true;
//                    if(cocina1==true&cocina11==true){
//                    Cocina1.setBackgroundColor(getResources().getColor(R.color.Mix1));
//                }}
//                else {Cocina1.setBackgroundColor(0);cocina1=false;}
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//        lec41.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                if(Integer.parseInt(dataSnapshot.getValue().toString())>10){Cocina2.setBackgroundColor(getResources().getColor(R.color.Luz));}
//                else {Cocina2.setBackgroundColor(0);}
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//        lec51.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//
//                if(Integer.parseInt(dataSnapshot.getValue().toString())<10){Pasillo2.setBackgroundColor(getResources().getColor(R.color.Luz));}
//                else {Pasillo2.setBackgroundColor(0);}
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//        lec61.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                if(Integer.parseInt(dataSnapshot.getValue().toString())<10){Pasillo1.setBackgroundColor(getResources().getColor(R.color.Luz));}
//                else {Pasillo1.setBackgroundColor(0);}
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//        lec71.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if(Integer.parseInt(dataSnapshot.getValue().toString())<10){Estudio.setBackgroundColor(getResources().getColor(R.color.Luz));}
//                else {Estudio.setBackgroundColor(0);}
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });




        

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
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // only for gingerbread and newer versions
            NotificationHelper l=new NotificationHelper(getActivity());
            l.createNotification("Cambio de Valor","Se ha actualizado en " + Singleton.getInstance().getHabitacion() + " de la acción " + Singleton.getInstance().getModo() + " con el valor de " + Singleton.getInstance().getValor());

        }
        else {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity());
            builder.setContentIntent(pendingIntent);

            builder.setSmallIcon(R.drawable.ambiental);
            builder.setAutoCancel(true);
            builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_notifications_black_24dp));
            builder.setContentTitle("Cambio de Valor");
            builder.setContentText("Se ha actualizado en " + Singleton.getInstance().getHabitacion() + " de la acción " + Singleton.getInstance().getModo() + " con el valor de " + Singleton.getInstance().getValor());
            builder.setSubText("Presiona para abrir el mapa");


        if (config.getInstance().isNotif()==true) {

            NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(
                    getActivity().NOTIFICATION_SERVICE);
            notificationManager.notify(1, builder.build());
            v = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(500);

        }}
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
                        //OpcionSeleccionada(adapterSpinner,spinner);
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
                            adapterSpinner = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, logicos);
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