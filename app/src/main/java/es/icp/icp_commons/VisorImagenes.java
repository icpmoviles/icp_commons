package es.icp.icp_commons;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.viewpagerindicator.LinePageIndicator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import es.icp.icp_commons.Adapters.VisorImagenesAdapter;
import es.icp.icp_commons.Helpers.Constantes;
import es.icp.icp_commons.Helpers.DepthPageTransformer;
import es.icp.icp_commons.Interfaces.AdjuntarImagenesListener;
import es.icp.icp_commons.Interfaces.ListenerAccion;
import es.icp.icp_commons.Objects.ImagenCommons;
import es.icp.icp_commons.Utils.Localizacion;
import es.icp.icp_commons.Utils.Utils;
import es.icp.icp_commons.Utils.UtilsFechas;
import es.icp.icp_commons.Utils.CommonsUtilsImagenes;
import id.zelory.compressor.Compressor;

//import android.widget.ImageView;

public class VisorImagenes extends AppCompatActivity {

    private static Context      ctx;
    private        LinearLayout mainContainer;
    private static DialogConfig cnf;

    private static RelativeLayout    rlImagenes;
    private static ViewPager         vpImagenes;
    private        LinePageIndicator pageIndicator;
    private static TextView          txtConteoImagenes;
    //    private static ImageView         ivPageLeft;
    //    private static ImageView         ivPageRight;
    private        Button            btnNeutral;

    private static File                     archivoTemporal;
    private static VisorImagenesAdapter     visorImagenesAdapter;
    private static AdjuntarImagenesListener ail;

    public VisorImagenes() {
    }

    public VisorImagenes(Context context) {
        ctx = context;
    }

    public VisorImagenes(Context context, LinearLayout mainContainer, DialogConfig config) {
        cargarVisorImagenes(context, mainContainer, config);
    }

    public void cargarVisorImagenes(Context context, LinearLayout mainContainer, DialogConfig config) {
        ctx                = context;
        this.mainContainer = mainContainer;
        cnf                = config;

        setUpView();

        inicializarComponentes();
    }

    //    @Override
    public static void onActivityResult(int requestCode, int resultCode) {
        //        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            if (requestCode == Constantes.CAMERA_REQUEST_CODE) {
                agregarImagenAdapter();
            }
        }
    }

    //    @Override
    public static void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults, int diferencial) {
        //        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constantes.CODE_PERMISSIONS) {
            for (int i = 0; i < permissions.length; i++) {
                if (permissions[i].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE) && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    if (ctx != null) adjuntarImagen(ctx);
                }
            }
        }
    }

    private void eliminarImagen(int position) {
        vpImagenes.setCurrentItem(1);

        ImagenCommons imagenCommons = cnf.getImagenes().remove(position);
        //        visorImagenesAdapter.setData(cnf.getImagenes());
        ail.imagenEliminada(position, imagenCommons);
        actualizarViewPager();

        if (cnf.getAdjuntarImagenesListener() == null) visorImagenesAdapter = new VisorImagenesAdapter(ctx, cnf.getImagenes());
        else visorImagenesAdapter = new VisorImagenesAdapter(ctx, cnf.getImagenes(), new ListenerAccion() {
            @Override
            public void accion(int code, Object object) {
                int position = (Integer) object;
                eliminarImagen(position);
            }
        });
        vpImagenes.setAdapter(visorImagenesAdapter);
        //        vpImagenes.setCurrentItem(0);
        //        pageIndicator.setViewPager(vpImagenes);
        //        visorImagenesAdapter.notifyDataSetChanged();

        if (cnf.getImagenes().size() > 0) {
            //            if (position == 0) vpImagenes.setCurrentItem(1);
            vpImagenes.setCurrentItem(0);
            if (cnf.getImagenes().size() == 0) rlImagenes.setVisibility(View.GONE);
            if (cnf.getImagenes().size() > 0) txtConteoImagenes.setText("1/" + cnf.getImagenes().size());
            //            ivPageLeft.setVisibility(View.GONE);
            //            if (cnf.getImagenes().size() == 1/* || config.getImagenes().size() == 0*/) ivPageRight.setVisibility(View.GONE);
        }
    }

    private void inicializarComponentes() {
        rlImagenes.setVisibility(View.VISIBLE);

        if (cnf.getAdjuntarImagenesListener() == null) visorImagenesAdapter = new VisorImagenesAdapter(ctx, cnf.getImagenes());
        else visorImagenesAdapter = new VisorImagenesAdapter(ctx, cnf.getImagenes(), new ListenerAccion() {
            @Override
            public void accion(int code, Object object) {
                int position = (Integer) object;
                eliminarImagen(position);
            }
        });

        vpImagenes.setAdapter(visorImagenesAdapter);
        vpImagenes.setCurrentItem(0);
        pageIndicator.setViewPager(vpImagenes);
        vpImagenes.setPageMargin(20);
        vpImagenes.setPageTransformer(true, new DepthPageTransformer());

        vpImagenes.addOnAdapterChangeListener(new ViewPager.OnAdapterChangeListener() {
            @Override
            public void onAdapterChanged(@NonNull ViewPager viewPager, @Nullable PagerAdapter oldAdapter, @Nullable PagerAdapter newAdapter) {
                vpImagenes.setCurrentItem(0);
            }
        });

        if (cnf.getImagenes().size() == 0) rlImagenes.setVisibility(View.GONE);

        if (cnf.getImagenes().size() > 0) txtConteoImagenes.setText("1/" + cnf.getImagenes().size());
        //        ivPageLeft.setVisibility(View.GONE);
        //        if (cnf.getImagenes().size() == 1/* || config.getImagenes().size() == 0*/) ivPageRight.setVisibility(View.GONE);
        //        ivPageLeft.setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View v) {
        //                vpImagenes.setCurrentItem(vpImagenes.getCurrentItem() - 1);
        //                if (vpImagenes.getCurrentItem() == 0) ivPageLeft.setVisibility(View.GONE);
        //                ivPageRight.setVisibility(View.VISIBLE);
        //                actualizarViewPager();
        //            }
        //        });
        //        ivPageRight.setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View v) {
        //                vpImagenes.setCurrentItem(vpImagenes.getCurrentItem() + 1);
        //                if (vpImagenes.getCurrentItem() == cnf.getImagenes().size() - 1) ivPageRight.setVisibility(View.GONE);
        //                ivPageLeft.setVisibility(View.VISIBLE);
        //                actualizarViewPager();
        //            }
        //        });
        vpImagenes.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //                ivPageLeft.setVisibility(position == 0 ? View.GONE : View.VISIBLE);
                //                ivPageRight.setVisibility(position == cnf.getImagenes().size() - 1 ? View.GONE : View.VISIBLE);
                txtConteoImagenes.setText(position + 1 + "/" + cnf.getImagenes().size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        if (cnf.getAdjuntarImagenesListener() != null) { //el visor de imágenes estará en modo edición
            cnf.setImagenes(cnf.getImagenes().size() == 0 ? new ArrayList<>() : cnf.getImagenes());
            ail = cnf.getAdjuntarImagenesListener();
            btnNeutral.setVisibility(View.VISIBLE);
            btnNeutral.setText(R.string.dialog_visor_imagenes_adjuntar_imagen);
            btnNeutral.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String[] permisos = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};

                    if (Utils.comprobarPermisos(ctx, permisos)) {
                        adjuntarImagen(ctx);
                    }
                }
            });
        }
    }

    private static void actualizarViewPager() {
        rlImagenes.setVisibility(cnf.getImagenes().size() == 0 ? View.GONE : View.VISIBLE);
        txtConteoImagenes.setText(vpImagenes.getCurrentItem() + 1 + "/" + cnf.getImagenes().size());
        //        ivPageLeft.setVisibility(vpImagenes.getCurrentItem() == 0 ? View.GONE : View.VISIBLE);
        //        ivPageRight.setVisibility(vpImagenes.getCurrentItem() == cnf.getImagenes().size() - 1 ? View.GONE : View.VISIBLE);
    }

    private static void agregarImagenAdapter() {
        rlImagenes.setVisibility(View.VISIBLE);

        File compressedImgFile = comprimirArchivo();
        if (compressedImgFile != null) {
            ImagenCommons imagenCommons = new ImagenCommons(CommonsUtilsImagenes.fromFileToBase64Image(compressedImgFile), Utils.getFormatFromFile(compressedImgFile.getAbsolutePath()), Utils.getCameraPhotoOrientation(compressedImgFile.getAbsolutePath()), UtilsFechas.getHoy(Localizacion.getInstance().formatoFechas));
            cnf.getImagenes().add(imagenCommons);
            visorImagenesAdapter.setData(cnf.getImagenes());
            actualizarViewPager();
            ail.imagenAdjuntada(imagenCommons);
            archivoTemporal.delete();
            compressedImgFile.delete();
        }
    }

    private static File comprimirArchivo() {
        try {
            return new Compressor(ctx).compressToFile(archivoTemporal);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void adjuntarImagen(Context context) {
        archivoTemporal = new File(context.getExternalFilesDir(Environment.getDataDirectory().getAbsolutePath()), "/temporalpicture_" + System.currentTimeMillis() + ".jpg"); // TODO: 26/03/2020 Permitir la introduccion de tipo JPG / PNG
        if (!archivoTemporal.exists()) {
            try {
                if (archivoTemporal.createNewFile()) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(context, context.getPackageName(), archivoTemporal));
                    intent.putExtra("android.intent.extras.CAMERA_FACING", Camera.CameraInfo.CAMERA_FACING_BACK);
                    ((Activity) ctx).startActivityForResult(intent, Constantes.CAMERA_REQUEST_CODE);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setUpView() {
        rlImagenes        = mainContainer.findViewById(R.id.rlImagenes);
        vpImagenes        = mainContainer.findViewById(R.id.vpImagenes);
        pageIndicator     = mainContainer.findViewById(R.id.pageIndicator);
        txtConteoImagenes = mainContainer.findViewById(R.id.txtConteoImagenes);
        //        ivPageLeft        = mainContainer.findViewById(R.id.ivPageLeft);
        //        ivPageRight       = mainContainer.findViewById(R.id.ivPageRight);
        btnNeutral = mainContainer.findViewById(R.id.btnNeutral);
    }
}
