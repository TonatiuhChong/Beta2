package com.example.hombr.beta.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hombr.beta.R;

import java.util.List;

public class AdaptadorListaSensores extends RecyclerView.Adapter<AdaptadorListaSensores.ViewHolder> {

    private List<ListItemSensores> listItems;
    private Context context;

    public AdaptadorListaSensores(List<ListItemSensores> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.sensoreslist,viewGroup,false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final ListItemSensores listSensores=listItems.get(i);
        viewHolder.HabitacionListview.setText(listSensores.getHabitacion());
        viewHolder.TipoSensor.setText(listSensores.get());
        viewHolder.ValorSensor.setText(listSensores.getValorSensor());
        viewHolder.TipoMotor.setText(listSensores.getTmotor());
        viewHolder.ValorMotor.setText(listSensores.getEstadoMotor());



    }


    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView TipoSensor,ValorSensor,TipoMotor,ValorMotor,HabitacionListview;
        public LinearLayout layout;


        public ViewHolder(View itemView) {
            super(itemView);

            HabitacionListview=(TextView)itemView.findViewById(R.id.FSHabitacion);
            TipoSensor=(TextView)itemView.findViewById(R.id.FSTSensor);
            ValorSensor=(TextView)itemView.findViewById(R.id.FSVSensor);
            TipoMotor=(TextView)itemView.findViewById(R.id.FSTMotor);
            ValorMotor=(TextView)itemView.findViewById(R.id.FSVMotor);

            layout=(LinearLayout)itemView.findViewById(R.id.LinearCartita);


        }
    }
}

