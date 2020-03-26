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

import es.icp.icp_commons.Objects.ImagenCommons;
import es.icp.icp_commons.R;
import es.icp.icp_commons.Utils.Utils;

public class VisorImagenesAdapter extends PagerAdapter {

    private Context           context;
    private ArrayList<byte[]> imagenes;

    public VisorImagenesAdapter(Context context, ArrayList<ImagenCommons> imagenes) {
        this.context  = context;
        this.imagenes = new ArrayList<>();
        addImagesToByteArray(imagenes);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        byte[] imagen = imagenes.get(position);

        LayoutInflater                      inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View layout   = inflater != null ? inflater.inflate(R.layout.fragment_visor_imagen, null) : new View(context);

        layout.setVisibility(View.VISIBLE);
        container.addView(layout);
        cargarImagen(imagen, layout.findViewById(R.id.ivVisorImagen));
        return layout;
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
        for (ImagenCommons imagen : imagenes) this.imagenes.add(Utils.convertBase64ToByteArray(imagen));
    }

    public void setData(ArrayList<ImagenCommons> imagenes) {
        addImagesToByteArray(imagenes);
        notifyDataSetChanged();
    }

    private void cargarImagen(byte[] imagen, ImageView ivVisorImagen) {
        Glide.with(context).asBitmap().load(imagen).into(ivVisorImagen);
    }
}
