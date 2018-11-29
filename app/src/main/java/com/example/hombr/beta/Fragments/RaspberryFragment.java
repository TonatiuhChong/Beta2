package com.example.hombr.beta.Fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hombr.beta.R;
import com.example.hombr.beta.Singletons.Acmin;
import com.example.hombr.beta.Singletons.Singleton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class RaspberryFragment extends Fragment {

    private ImageView btn;
    private String p;
    private int q,w;
    private TextView nombre;
    FragmentManager fm;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View Rec = inflater.inflate(R.layout.fragment_raspberry, container, false);
         btn = (ImageView) Rec.findViewById(R.id.volver);
         Button recon=(Button)Rec.findViewById(R.id.IniciarRecon);
        nombre=(TextView)Rec.findViewById(R.id.NombreRegistrado);


         q= Acmin.getInstance().getContadoragregar();
         w=Acmin.getInstance().getTotalagregar();
         p=Singleton.getInstance().getAgregar()[q];
         nombre.setText(p);

         fm = getActivity().getSupportFragmentManager();


         recon.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 DatabaseReference refe= FirebaseDatabase.getInstance().getReference().child("Facial");
                 Map<String,Object> map= new HashMap<String, Object>();
                 map.put("UsuarioActivado",p);//Cargar Usuario
                 refe.updateChildren(map);
                 map.put("Captura",true);//activar
                 refe.updateChildren(map);

                 final ProgressDialog dialog=new ProgressDialog(getActivity());
                 dialog.setMessage("Capturando Parametros de rostro");
                 dialog.setCancelable(false);
                 dialog.show();

                 DatabaseReference acabo=FirebaseDatabase.getInstance().getReference().child("Facial").child("Captura");
                 acabo.addValueEventListener(new ValueEventListener() {
                     @Override
                     public void onDataChange(DataSnapshot dataSnapshot) {
                         if ((Boolean)dataSnapshot.getValue()==false){
                             dialog.dismiss();

                             if(q==w-1) {
                                 Acmin.getInstance().setContadoragregar(0);
                                 Toast.makeText(getActivity(), "CAPTURA COMPLETA!", Toast.LENGTH_SHORT).show();
                                 fm.beginTransaction().replace(R.id.escenario, new AdminFragment()).commit();
                                 getActivity().getSupportFragmentManager().popBackStack();
                             }

                               else {
                                 q++;
                                 Acmin.getInstance().setContadoragregar(q);
                                 fm.beginTransaction().replace(R.id.escenario, new RaspberryFragment()).commit();
                                 getActivity().getSupportFragmentManager().popBackStack();
                             }


                         }
                         else {
                            dialog.show();
                         }
                     }

                     @Override
                     public void onCancelled(DatabaseError databaseError) {

                     }
                 });
             }
         });


        return Rec;
    }




}