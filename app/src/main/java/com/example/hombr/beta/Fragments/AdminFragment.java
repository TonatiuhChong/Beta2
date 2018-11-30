package com.example.hombr.beta.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.hombr.beta.Adapters.AdaptadorUsuarios;
import com.example.hombr.beta.Adapters.ListItemUsuarios;
import com.example.hombr.beta.R;
import com.example.hombr.beta.Singletons.Acmin;
import com.example.hombr.beta.Singletons.Singleton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;



public class AdminFragment extends Fragment {
    private TextView NombreU,EmailU,PerfilU;
    private ImageView ImgUSer;
    public  Boolean VActivacion=Boolean.FALSE;
    private RecyclerView rv;
    private RecyclerView.Adapter adapter;
    private List<ListItemUsuarios> usuarios;
    private List<String> cagada;
    private ArrayList<String> sensores = new ArrayList<>();
    private ImageButton agregarUser,borrarUser;
    private Button MultiUser;
    private Switch Configurar;
    private Spinner Spinner;
    private ArrayAdapter adapterSpinner;
    private String borrado;
    private ArrayList<Integer> mUserItems=new ArrayList<>();
    private ArrayList<Integer> mSelectedItems;
    private List<String> finalprro=new ArrayList<String>();
    private boolean[] checkedboolean;
    private String[]myArray;
    public String[]item;
    public  String[] Activados;
    private ProgressDialog Entrenando;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View Vista = inflater.inflate(R.layout.admin,container,false);

        getActivity().getWindow().setNavigationBarColor(getResources().getColor(R.color.admin));
        getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.admin));

        Configurar=(Switch)Vista.findViewById(R.id.ResetearRecon);
        NombreU=(TextView)Vista.findViewById(R.id.Adminuser);
        EmailU=(TextView)Vista.findViewById(R.id.Adminemail);
        PerfilU=(TextView)Vista.findViewById(R.id.Adminextra);
        ImgUSer=(ImageView)Vista.findViewById(R.id.AdminFoto);
        NombreU.setText(Singleton.getInstance().getUser());
        EmailU.setText(Singleton.getInstance().getEmail());
        PerfilU.setText(Singleton.getInstance().getPassword());
        Glide.with(this).load(Singleton.getInstance().getFoto()).apply(RequestOptions.circleCropTransform()).into(ImgUSer);

        rv=(RecyclerView)Vista.findViewById(R.id.AdminRecycler);
        agregarUser=(ImageButton)Vista.findViewById(R.id.AdminAgregar);
        borrarUser=(ImageButton)Vista.findViewById(R.id.AdminBorrar);
        MultiUser=(Button) Vista.findViewById(R.id.AdminMulti);
        Spinner=(Spinner)Vista.findViewById(R.id.AdminSpinner);

        Entrenando=new ProgressDialog(getActivity());
        Entrenando.setMessage("Entrenando modelo SVM");
        Entrenando.setCancelable(false);

        DatabaseReference Error= FirebaseDatabase.getInstance().getReference().child("Facial").child("Error");
        Error.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                switch ((String)dataSnapshot.getValue()){
                    case "Captura":
                        Snackbar.make(Vista,"Error en "+ (String)dataSnapshot.getValue(),Snackbar.LENGTH_SHORT).show();
                        break;

                    case "Train":
                        Snackbar.make(Vista,"Error en "+ (String)dataSnapshot.getValue(),Snackbar.LENGTH_SHORT).show();
                        break;
                    default:
                        break;

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        DatabaseReference config =FirebaseDatabase.getInstance().getReference().child("Facial").child("Configurado");
        config.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if ((Boolean)dataSnapshot.getValue()==true){
                    Configurar.setChecked(true);
                }
                else {
                    Configurar.setChecked(false);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Borrar();

        rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL,false));
        usuarios=new ArrayList<>();
        cagada=new ArrayList<String>();

        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Users");
        adapter = new AdaptadorUsuarios(usuarios,getActivity());
        rv.setAdapter(adapter);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                usuarios.removeAll(usuarios);
                cagada.removeAll(cagada);
                finalprro.removeAll(finalprro);
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    ListItemUsuarios users=snapshot.getValue(ListItemUsuarios.class);
                    usuarios.add(users);
                    cagada.add(users.getName());

                }
                adapter.notifyDataSetChanged();

                for (int i = 0; i <cagada.size(); i++) {
                    if(sensores.contains((cagada.get(i)))){

                    }
                    else{
                        finalprro.add(cagada.get(i));
                    }
                }
                myArray=new String[finalprro.size()];
                finalprro.toArray(myArray);
                checkedboolean = new boolean[myArray.length];
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        MultiUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder pp= new AlertDialog.Builder(v.getContext());
                pp.setTitle("Usuarios inactivos");
                pp.setMultiChoiceItems(myArray, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked){
                            if (!mUserItems.contains(which)){
                                mUserItems.add(which);
                            }
                        }
                        else {
                            if(mUserItems.contains(which)){
                                mUserItems.remove(which);
                            }
                        }
                    }
                });


                pp.setPositiveButton("Añadir", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        item=new String[mUserItems.size()];
                        for (int i =0; i<mUserItems.size();i++){
                            item[i]=myArray[mUserItems.get(i)];

                        }

                        int q,w;
                        Acmin.getInstance().setTotalagregar(item.length);//totales
                        Acmin.getInstance().setContadoragregar(0);//Contador
                        Singleton.getInstance().setAgregar(item);
                        q=Acmin.getInstance().getContadoragregar();//Contador;
                        w=Acmin.getInstance().getTotalagregar();//totales
                        mUserItems=new ArrayList<Integer>();

                    }
                });
                pp.show();
            }
        });

        if (Spinner!=null){
            adapterSpinner = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,sensores);
            Spinner.setAdapter(adapterSpinner);
        }

        agregarUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
                DatabaseReference auto=FirebaseDatabase.getInstance().getReference().child("Facial");
                Map<String,Object> map= new HashMap<String, Object>();
                int a=item.length+sensores.size();
                map.put("NumeroUsuarios",a);
                auto.updateChildren(map);
                fm.beginTransaction().replace(R.id.escenario, new RaspberryFragment()).addToBackStack(null).commit();
//                getActivity().getSupportFragmentManager().popBackStack();

            }
        });


        Configurar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder borrado=new AlertDialog.Builder(getActivity());
                borrado.setTitle("Advertencia")
                        .setMessage("Desea borrar todo el registro de reconocimiento?")
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                               DatabaseReference borrado= FirebaseDatabase.getInstance().getReference().child("Facial");
                                Map<String,Object> map= new HashMap<String, Object>();
                                map.put("Captura",false);
                                map.put("Configurado",false);
                                map.put("Detener",false);
                                map.put("Error","STOP");
                                map.put("ProcesoFinalizado",true);
                                map.put("UsuarioActivado","Desconocido");
                                borrado.updateChildren(map);
                            }
                        }).create().show();
            }
        });


DatabaseReference CheckIfIsFinished =FirebaseDatabase.getInstance().getReference().child("Facial").child("ProcesoFinalizado");
CheckIfIsFinished.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        if (dataSnapshot.getValue()=="En Proceso"){
            if (Entrenando!=null)
            Entrenando.show();

        }
        else {
            if (Entrenando!=null)
            Entrenando.dismiss();
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
});
        return Vista;
    }

    private void Borrar() {

        final DatabaseReference refErase= FirebaseDatabase.getInstance().getReference().child("Facial").child("UsuariosActivados");
        refErase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Set<String> set = new HashSet<String>();
                Iterator i = dataSnapshot.getChildren().iterator();
                while (i.hasNext()) {
                    set.add(((DataSnapshot) i.next()).getKey());
                }
                sensores.clear();
                sensores.addAll(set);
                Activados =sensores.toArray(new String[sensores.size()]);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                borrado=(String)parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        borrarUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refErase.child(borrado).setValue(null);
            }
        });
    }


}
