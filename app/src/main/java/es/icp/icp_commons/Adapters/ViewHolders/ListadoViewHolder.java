package es.icp.icp_commons.Adapters.ViewHolders;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import es.icp.icp_commons.R;

public class ListadoViewHolder extends RecyclerView.ViewHolder {

    public LinearLayout mainPanel;
    public TextView     txtItemListado;

    public ListadoViewHolder(@NonNull View itemView) {
        super(itemView);

        mainPanel      = itemView.findViewById(R.id.mainPanel);
        txtItemListado = itemView.findViewById(R.id.txtItemListado);
    }
}
