package es.icp.icp_commons.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import es.icp.icp_commons.Adapters.ViewHolders.ListadoViewHolder;
import es.icp.icp_commons.Interfaces.OnItemSelectedListener;
import es.icp.icp_commons.R;

public class ListadoAdapter extends RecyclerView.Adapter<ListadoViewHolder> {

    private Context                context;
    private ArrayList<String>      datos;
    private OnItemSelectedListener listener;
    private boolean                listadoConfirmacion;

    public ListadoAdapter(Context context, ArrayList<String> datos, OnItemSelectedListener listener, boolean listadoConfirmacion) {
        this.context             = context;
        this.datos               = datos;
        this.listener            = listener;
        this.listadoConfirmacion = listadoConfirmacion;
    }

    public void setData(ArrayList<String> datos) {
        this.datos = datos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListadoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View           view     = inflater != null ? inflater.inflate(R.layout.item_listado, parent, false) : new View(context);
        return new ListadoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListadoViewHolder holder, int position) {
        String item = datos.get(position);

        holder.txtItemListado.setText(item);
        holder.mainPanel.setOnClickListener(view -> {

            if (listadoConfirmacion) {
                // TODO: 09/09/2021 Hacer que se requiera una confirmación por diálogo sobre el objeto seleccionado
                listener.onItemSelected(item);
            } else {
                listener.onItemSelected(item);
            }

        });
    }

    @Override
    public int getItemCount() {
        return datos != null ? datos.size() : 0;
    }
}
