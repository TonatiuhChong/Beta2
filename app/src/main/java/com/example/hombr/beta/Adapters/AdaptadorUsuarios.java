package com.example.hombr.beta.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hombr.beta.R;

import java.util.List;

public class AdaptadorUsuarios extends RecyclerView.Adapter<AdaptadorUsuarios.ViewHolder> {
    private List <ListItemUsuarios> listItems2;
    private Context context;

    public AdaptadorUsuarios(List<ListItemUsuarios> listItems2, Context context) {
        this.listItems2 = listItems2;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.usuarioslist,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder( ViewHolder viewHolder, int i) {
     final ListItemUsuarios listitem =listItems2.get(i);
    viewHolder.email.setText(listitem.getCorreo());
    viewHolder.nombre.setText(listitem.getNombre());
    viewHolder.fotito.setImageResource(listitem.getFotoUsuario());
    }

    @Override
    public int getItemCount() {
        return listItems2.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        public TextView nombre, email,nivel;
        public ImageView fotito;

        public  ViewHolder(View itemView){
            super(itemView);

            nombre=(TextView)itemView.findViewById(R.id.FRNombreUsuario);
            email=(TextView)itemView.findViewById(R.id.FREmailUsuario);
            fotito=(ImageView)itemView.findViewById(R.id.FRImagenUsuario);



        }
    }



}
