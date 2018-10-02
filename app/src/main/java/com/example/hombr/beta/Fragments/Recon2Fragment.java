package com.example.hombr.beta.Fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.hombr.beta.Adapters.AdaptadorUsuarios;
import com.example.hombr.beta.Adapters.ListItemUsuarios;
import com.example.hombr.beta.R;
import com.example.hombr.beta.Singletons.Singleton;
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


public class Recon2Fragment extends Fragment {

    private TextView NombreU,EmailU,PerfilU;
    private ImageView ImgUSer;
    public  Boolean VActivacion=Boolean.TRUE;
    private RecyclerView rv;
    private RecyclerView.Adapter adapter;
    private List<ListItemUsuarios> usuarios;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View Rec= inflater.inflate(R.layout.fragment_recon2,container,false);

        NombreU=(TextView)Rec.findViewById(R.id.FragmentNameUser2);
        EmailU=(TextView)Rec.findViewById(R.id.FragmentEmailUser2);
        PerfilU=(TextView)Rec.findViewById(R.id.FragmentValueUser2);
        ImgUSer=(ImageView)Rec.findViewById(R.id.FragmentFotoUser2);



        NombreU.setText(Singleton.getInstance().getUser());
        EmailU.setText(Singleton.getInstance().getEmail());
        PerfilU.setText(Singleton.getInstance().getPassword());
        Glide.with(this).load(Singleton.getInstance().getFoto()).apply(RequestOptions.circleCropTransform()).into(ImgUSer);

        FloatingActionButton fab = (FloatingActionButton) Rec.findViewById(R.id.fab2);
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
        rv=(RecyclerView)Rec.findViewById(R.id.recyclerusuarios);
        rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL,false));
        usuarios=new ArrayList<>();
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Users");
        adapter = new AdaptadorUsuarios(usuarios,getActivity());
        rv.setAdapter(adapter);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                usuarios.removeAll(usuarios);
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    ListItemUsuarios users=snapshot.getValue(ListItemUsuarios.class);
                    usuarios.add(users);


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