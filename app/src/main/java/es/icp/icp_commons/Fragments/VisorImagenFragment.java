package es.icp.icp_commons.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import es.icp.icp_commons.R;

public class VisorImagenFragment extends Fragment {

    private Context   context;
    private byte[]    imagen;
    private ImageView ivVisorImagen;

    public VisorImagenFragment() {
    }

    public VisorImagenFragment(Context context, byte[] imagen) {
        this.context = context;
        this.imagen  = imagen;
    }

    public static Fragment newInstance(Context context, byte[] imagen) {
        VisorImagenFragment f = new VisorImagenFragment(context, imagen);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_visor_imagen, null);
        setUpView(root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void setUpView(View view) {
        ivVisorImagen = view.findViewById(R.id.ivVisorImagen);

//        cargarImagen();
    }

    private void cargarImagen() {
        Glide.with(context)
                .asBitmap()
                .load(imagen)
//                .placeholder(R.drawable.ic_broken)
                .into(ivVisorImagen);
    }
}
