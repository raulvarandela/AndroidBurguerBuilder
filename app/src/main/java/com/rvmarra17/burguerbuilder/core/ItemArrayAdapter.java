package com.rvmarra17.burguerbuilder.core;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.rvmarra17.burguerbuilder.R;

import java.util.List;

public class ItemArrayAdapter extends ArrayAdapter {

    //le tenemos que pasar el contexto de la aplicación y los elementos
    public ItemArrayAdapter(@NonNull Context context, @NonNull List objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final Context CONTEXT = this.getContext(); //accedo al contexto
        final LayoutInflater INFLATER = LayoutInflater.from(CONTEXT); //se usar el inflador de vistas para crear un objeto view a partir de un layout determinado
        final Item ITEM = (Item) this.getItem(position);

        if (view == null) {
            view = INFLATER.inflate(R.layout.listview_item, null); //si no es
        }

        final TextView lblNombre = view.findViewById(R.id.lblNombre);
        final TextView lblSupermercado = view.findViewById(R.id.lblSupermercado);

        lblNombre.setText(ITEM.getNombre());
        lblSupermercado.setText(ITEM.getSupermercado());

        return view;
    }

}
