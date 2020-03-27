package es.icp.icp_commons.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import es.icp.icp_commons.Interfaces.ListenerAccion;
import es.icp.icp_commons.Objects.ImagenCommons;
import es.icp.icp_commons.R;
import es.icp.icp_commons.Utils.Utils;

public class VisorImagenesAdapter extends PagerAdapter {

    private Context                  context;
    private ArrayList<byte[]>        imagenes;
    private ArrayList<ImagenCommons> imagenesCommons;
    private ListenerAccion           listenerAccion;

    public VisorImagenesAdapter(Context context, ArrayList<ImagenCommons> imagenes) {
        this(context, imagenes, null);
    }

    public VisorImagenesAdapter(Context context, ArrayList<ImagenCommons> imagenesCommons, ListenerAccion listenerAccion) {
        this.context         = context;
        this.imagenes        = new ArrayList<>();
        this.imagenesCommons = imagenesCommons;
        this.listenerAccion  = listenerAccion;
        addImagesToByteArray(imagenesCommons);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        byte[] imagen = imagenes.get(position);

        LayoutInflater                      inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View layout   = inflater != null ? inflater.inflate(R.layout.fragment_visor_imagen, null) : new View(context);

        setUpView(layout, position);

        container.addView(layout);
        cargarImagen(imagen, layout.findViewById(R.id.ivVisorImagen));
        return layout;
    }

    private void setUpView(View layout, int position) {
        layout.setVisibility(View.VISIBLE);
        if (listenerAccion != null) {
            ImageView btnEliminarImagen = layout.findViewById(R.id.btnEliminarImagen);
            btnEliminarImagen.setVisibility(View.VISIBLE);
            btnEliminarImagen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listenerAccion.accion(0, position);
                }
            });
        }
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return imagenes != null ? imagenes.size() : 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    private void addImagesToByteArray(ArrayList<ImagenCommons> imagenes) {
        this.imagenes = new ArrayList<>();
        for (ImagenCommons imagen : imagenes) this.imagenes.add(Utils.convertBase64ToByteArray(imagen));
    }

    public void setData(ArrayList<ImagenCommons> imagenes) {
        this.imagenesCommons = imagenes;
        addImagesToByteArray(imagenes);
        notifyDataSetChanged();
    }

    private void cargarImagen(byte[] imagen, ImageView ivVisorImagen) {
        Glide.with(context).asBitmap().load(imagen).into(ivVisorImagen);
    }
}
