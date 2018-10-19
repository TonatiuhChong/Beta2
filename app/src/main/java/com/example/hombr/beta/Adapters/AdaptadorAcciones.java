package com.example.hombr.beta.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hombr.beta.Fragments.ControlFragment;
import com.example.hombr.beta.R;
import com.example.hombr.beta.Singletons.Singleton;

import java.util.List;

public class AdaptadorAcciones extends RecyclerView.Adapter<AdaptadorAcciones.ViewHolder> {

    private List<ListItemAcciones> listItems;
    ControlFragment p;

    public AdaptadorAcciones(List<ListItemAcciones> listItems, Context context, ControlFragment p) {
        this.listItems = listItems;
        this.context = context;
        this.p= p;
    }

    private Context context;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.accioneslist,viewGroup,false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final ListItemAcciones listItem=listItems.get(i);

        viewHolder.nombre.setText(listItem.getDesc());
        viewHolder.imagen.setImageResource(listItem.getFoto());
        viewHolder.imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Singleton.getInstance().setModo(listItem.getDesc());
                //Si no funciona, poner el actualizador de datos aqui.
                p.pintar();


            }
        });
    }


    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView nombre;
        public ImageView imagen;
        public LinearLayout layout;


        public ViewHolder(View itemView) {
            super(itemView);

            nombre=(TextView)itemView.findViewById(R.id.nombre);
            imagen=(ImageView)itemView.findViewById(R.id.imagen);
            layout=(LinearLayout)itemView.findViewById(R.id.LinearCartita);


        }
    }
}
