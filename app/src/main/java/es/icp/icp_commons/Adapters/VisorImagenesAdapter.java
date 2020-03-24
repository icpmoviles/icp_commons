package es.icp.icp_commons.Adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

import es.icp.icp_commons.Fragments.VisorImagenFragment;
import es.icp.icp_commons.Utils.Utils;

public class VisorImagenesAdapter extends FragmentPagerAdapter {

    private Context           context;
    private ArrayList<byte[]> imagenes;

    public VisorImagenesAdapter(Context context, ArrayList<String> imagenes, FragmentManager fm) {
        super(fm);
        this.context = context;
        addImagesToByteArray(imagenes);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return VisorImagenFragment.newInstance(context, imagenes.get(position));
    }

    @Override
    public int getCount() {
        return 3;
    }

    private void addImagesToByteArray(ArrayList<String> imagenes) {
        for (String imagen : imagenes) this.imagenes.add(Utils.convertBase64ToByteArray(imagen));
    }

    public void setData(ArrayList<String> imagenes) {
        addImagesToByteArray(imagenes);
        notifyDataSetChanged();
    }
}
