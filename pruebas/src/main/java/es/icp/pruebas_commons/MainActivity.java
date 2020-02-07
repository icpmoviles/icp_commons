package es.icp.pruebas_commons;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import es.icp.icp_commons.CustomNotification;
import es.icp.pruebas_commons.databinding.MainActivityBinding;

public class MainActivity extends Activity {

    private Context context = MainActivity.this;
    private MainActivityBinding binding;
    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        setEvents();
        binding.setHandler(handler);
    }

    private void setEvents() {
        handler = new Handler() {
            @Override
            public void onClickBtn1(View view) {
                crearNotif1();
            }
        };
    }

    public void crearNotif1() {
        CustomNotification customNotification = new CustomNotification.Builder(context)
                .setSimpleMode()
                .setDuration(CustomNotification.LENGTH_SHORT)
                .build();
        customNotification.showText("Notificación nº1");
    }




    public interface Handler {
        void onClickBtn1(View view);
    }
}
