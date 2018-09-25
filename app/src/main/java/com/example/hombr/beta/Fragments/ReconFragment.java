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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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
import java.util.Map;
import java.util.Set;


public class ReconFragment extends Fragment {

    private TextView NombreU,EmailU,PerfilU;
    private ImageView ImgUSer;
    private ListView lista;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> rooms= new ArrayList<>();
    public  Boolean VActivacion=Boolean.TRUE;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View Rec= inflater.inflate(R.layout.fragment_recon,container,false);

        lista=(ListView)Rec.findViewById(R.id.FragmentListaUsers);
        NombreU=(TextView)Rec.findViewById(R.id.FragmentNameUser);
        EmailU=(TextView)Rec.findViewById(R.id.FragmentEmailUser);
        PerfilU=(TextView)Rec.findViewById(R.id.FragmentValueUser);
        ImgUSer=(ImageView)Rec.findViewById(R.id.FragmentFotoUser);

        arrayAdapter= new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,rooms);
        lista.setAdapter(arrayAdapter);


        NombreU.setText(Singleton.getInstance().getUser());
        EmailU.setText(Singleton.getInstance().getEmail());
        PerfilU.setText(Singleton.getInstance().getPassword());
        Glide.with(this).load(Singleton.getInstance().getFoto()).apply(RequestOptions.circleCropTransform()).into(ImgUSer);

        FloatingActionButton fab = (FloatingActionButton) Rec.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                VActivacion=!VActivacion;
                Snackbar.make(view, "Acerquese a la camara para activar el reconocimiento Facial", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                DatabaseReference ActivarCamara= FirebaseDatabase.getInstance().getReference().child("Facial");
                Map<String,Object> map= new HashMap<String, Object>();
                map.put("Activacion",VActivacion);
                ActivarCamara.updateChildren(map);
                FragmentManager tr= getActivity().getSupportFragmentManager();
                tr.beginTransaction().replace(R.id.escenario, new RaspberryFragment()).commit();



            }
        });

        conexion();
        return Rec;
    }

    private void conexion() {


        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Users");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Set<String> set = new HashSet<String>();
               final String[]emails = new String[0];
                Iterator i = dataSnapshot.getChildren().iterator();
                while (i.hasNext()){
                    set.add(((DataSnapshot)i.next()).getKey());
                    DatabaseReference ref2=FirebaseDatabase.getInstance().getReference().child("Users").child(set.iterator().next());
                    ref2.child("email").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            Toast.makeText(getActivity(), dataSnapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
                           emails[i.next()]=dataSnapshot.getValue().toString();

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                rooms.clear();
                rooms.addAll(set);

                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void hijos(DatabaseReference ref2) {
        ref2.child("email").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}