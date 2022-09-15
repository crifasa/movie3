package com.example.movies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PelisAdapter extends RecyclerView.Adapter<PelisAdapter.pelisView> {

    private List<Pelis> pelisList = new ArrayList<>();
    private Context context;

    //private ArrayList<Pelis> pelisArrayList;

    public PelisAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public pelisView onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.itemmostrar, viewGroup, false);
        return new pelisView(view);
    }

    @Override
    public void onBindViewHolder(pelisView pelisView, int i) {
        pelisView.hola(pelisList.get(i));
    }

    @Override
    public int getItemCount() {
        return pelisList.size();
    }

    public void agregarPelicula(Pelis pelis) {
        pelisList.add(pelis);
        Collections.reverse(pelisList);
        this.notifyDataSetChanged();
    }

    public class pelisView extends RecyclerView.ViewHolder {
        private TextView titulo, fecha, precio;

        public pelisView(@NonNull View itemView) {
            super(itemView);

            titulo = itemView.findViewById(R.id.titulo);
            fecha = itemView.findViewById(R.id.fecha);
            precio = itemView.findViewById(R.id.precio);
        }
        private void hola(Pelis pelis){
            titulo.setText(pelis.getTitle());
            fecha.setText(pelis.getFecha());
            precio.setText(pelis.getPrecio());

        }
    }

}
