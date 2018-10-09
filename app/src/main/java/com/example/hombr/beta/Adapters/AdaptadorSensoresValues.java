package com.example.hombr.beta.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hombr.beta.R;

import java.util.List;

public class AdaptadorSensoresValues extends RecyclerView.Adapter<AdaptadorSensoresValues.ViewHolder> {
    private List <String> listItems2;
    private Context context;

    public AdaptadorSensoresValues(List<String> listItems2, Context context) {
        this.listItems2 = listItems2;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.valoressensor,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder( ViewHolder viewHolder, int i) {
        String campoActual =listItems2.get(i);
    viewHolder.ambiental.setText(campoActual);
    }

    @Override
    public int getItemCount() {
        return listItems2.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        public TextView ambiental,iluminacion,presencia,puerta,ventana,cambio;

        public  ViewHolder(View itemView){
            super(itemView);

            ambiental=(TextView)itemView.findViewById(R.id.ambiental);


        }
    }



}
