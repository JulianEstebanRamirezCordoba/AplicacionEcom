package com.cdp.agenda.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cdp.agenda.InformacionCli;
import com.cdp.agenda.R;
import com.cdp.agenda.entidades.CLiente;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ListaClienteAdapter extends RecyclerView.Adapter<ListaClienteAdapter.clienteViewHolder> {

    ArrayList<CLiente> listaclientes;
    ArrayList<CLiente> listaOriginal;

    public ListaClienteAdapter(ArrayList<CLiente> listaclientes) {
        this.listaclientes = listaclientes;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listaclientes);
    }

    @NonNull
    @Override
    public clienteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_cliente, null, false);
        return new clienteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull clienteViewHolder holder, int position) {
        holder.viewNombre.setText(listaclientes.get(position).getNombre()+ " " +
                listaclientes.get(position).getApellido() + "      " + listaclientes.get(position).getEdad());
        holder.viewCorreo.setText(listaclientes.get(position).getCorreo_electornico());
        holder.viewTelefono.setText(listaclientes.get(position).getTelefono());
    }

    public void Extraccion(String buscar){
        int longitud = buscar.length();

        if(longitud == 0){
            listaclientes.clear();
            listaclientes.addAll(listaOriginal);

        }else{
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<CLiente> collecion = listaclientes.stream().filter(i -> i.getNombre().toLowerCase().
                        contains(buscar.toLowerCase())).collect(Collectors.toList());
            listaclientes.clear();
            listaclientes.addAll(collecion);

            }else{
                for (CLiente Cliente: listaOriginal){
                    if(Cliente.getNombre().toLowerCase().contains(buscar.toLowerCase()));
                    listaclientes.add(Cliente);

                }
            }

        }

        notifyDataSetChanged();

    }


    @Override
    public int getItemCount() {
        return listaclientes.size();
    }

    public class clienteViewHolder extends RecyclerView.ViewHolder {

        TextView viewNombre, viewCorreo, viewTelefono;

        public clienteViewHolder(@NonNull View itemView) {
            super(itemView);

            viewNombre = itemView.findViewById(R.id.viewNombre);
            viewCorreo = itemView.findViewById(R.id.viewCorreo);
            viewTelefono = itemView.findViewById(R.id.viewTelefono);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                     Context context = view.getContext();
                     Intent intent = new Intent(context, InformacionCli.class);
                     intent.putExtra("Id_cli", listaclientes.get(getAdapterPosition()).getId());
                     context.startActivity(intent);

                }
            });
        }

    }
}
