package es.icp.icp_commons;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import es.icp.icp_commons.Helpers.OnSwipeTouchListener;
import es.icp.icp_commons.Interfaces.CustomNotificationResponse;

public class CustomNotification extends FrameLayout {

    // PROPIEDADES

    private Context context;
    private LinearLayout linearProgress;
    private TextView txtDescripcion;
    private ProgressBar loaderIcon;
    private ProgressBar progressBar;
    private TextView txtPorcentajeProgress;
    private TextView txtDeslizar;
    private LinearLayout notificationBox;
    private boolean minimizado = false;
    private boolean enMovimiento = false;
    private int mode = NOTIFICATION_SIMPLE;
    private int showTime = LENGTH_MEDIUM;
    private Activity activity;
    private Timer timer;
    private boolean firstTime = true;
    private boolean hide = true;
    private ArrayList<Button> buttons = new ArrayList<>();
    private boolean built = false;
    private int alturaMinimizado = 0;
    private boolean darkness = false;
    private TransitionDrawable trans;

    // CONSTANTES

    public static final int NOTIFICATION_SIMPLE = 0;
    public static final int NOTIFICATION_BUTTON = 1;
    public static final int NOTIFICATION_PROGRESS = 2;
    public static final int NOTIFICATION_MULTIPLE = 3;
    public static final int LENGTH_SHORT = 2000;
    public static final int LENGTH_MEDIUM = 5000;
    public static final int LENGTH_LONG = 8000;
    public static final int LENGTH_UNDEFINED = 0;
    private static final int MARGIN_BOTTOM_CABECERO = 2;
    private static final float LIGHT_ALPHA = 0.0f;
    private static final float DARK_ALPHA = 6.0f;


    // CONSTRUCTOR

    private CustomNotification(Context context) {
        super(context);
        this.context = context;

        activity = (Activity) context;
        this.trans = null;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    // MÉTODOS PÚBLICOS

    public void show() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                CustomNotification.this.kill(); // por si acaso la notificación se encontraba ya abierta
                if (mode == NOTIFICATION_MULTIPLE) {
                    for (Button button : buttons) {
                        LinearLayout linearLayout = findViewById(R.id.linearButtonsNotification);
                        for (Button btn : buttons)
                        {
                            if (btn.getParent() != null) ((ViewGroup) btn.getParent()).removeView(btn);
                            linearLayout.addView(btn);
                        }
                    }
                }
                if (CustomNotification.this.getParent() != null) {
                    ((ViewGroup) CustomNotification.this.getParent()).removeView(CustomNotification.this);
                }
                ((ViewGroup)activity.findViewById(android.R.id.content)).addView(CustomNotification.this);
                hide = false;
            }
        });
    }

    public void hide() {
        if (!hide) {
            CustomNotification.this.notificationBox.startAnimation(leaveAnimation());
        }
    }

    // MÉTODOS PRIVADOS

    private void setDuration(int showTime) {
        this.showTime = showTime;
//        temporizador();
    }

    private TimerTask reiniciarShowTime() {
        return new TimerTask() {
            @Override
            public void run() {
                if (enMovimiento) {
                    Timer timer = new Timer();
                    timer.schedule(reiniciarShowTime(), 100);
                } else {
                    activity.runOnUiThread(hiloTerminar);
                }
            }
        };
    }

    public void AddButton(String text, final CustomNotificationResponse response, int style)
    {
        Button btn = new Button(context);
        btn.setBackgroundResource(style);
        btn.setTextColor(Color.WHITE);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,0,0, 5);
        btn.setLayoutParams(params);
        btn.setText(text);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response.onResponse(CustomNotification.this);
            }
        });


        if (buttons == null)
            buttons = new ArrayList<>();
        buttons.add(btn);
    }

    private void inicializarVistas() {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        switch (mode) {
            case NOTIFICATION_BUTTON:
                inflater.inflate(R.layout.view_custom_notification_button, this);
                break;
            case NOTIFICATION_SIMPLE:
                inflater.inflate(R.layout.view_custom_notification_simple, this);
                break;
            case NOTIFICATION_PROGRESS:
                inflater.inflate(R.layout.view_custom_notification_progress, this);
                break;
            case NOTIFICATION_MULTIPLE:
                inflater.inflate(R.layout.view_custom_notification_multiple, this);
                break;
        }

        this.inicializar();
    }

    private void inicializar() {
        this.txtDescripcion = findViewById(R.id.txtDecripcion);
        this.loaderIcon = findViewById(R.id.loaderIcon);
        this.progressBar = findViewById(R.id.progressBar);
        this.txtPorcentajeProgress = findViewById(R.id.txtPorcentajeProgress);
        this.notificationBox = findViewById(R.id.notificationBox);
        this.txtDeslizar = findViewById(R.id.txtDeslizar);
        this.crearTouchListener();
        this.bringToFront();
    }

    private Thread hiloTerminar = new Thread() {
        @Override
        public void run() {
            if (!hide) CustomNotification.this.notificationBox.startAnimation(leaveAnimation());
        }
    };

    public synchronized void setProgress(int progress) {
        if (this.mode != NOTIFICATION_PROGRESS) {
            Toast.makeText(activity, "La notificación no permite progress", Toast.LENGTH_SHORT).show();
        } else {
            if (hide && built) {
                hide = false;
//                show();
                temporizador();
                CustomNotification.this.notificationBox.startAnimation(enterAnimation());
            }
            temporizador();
            ((ProgressBar) findViewById(R.id.progressBar)).setProgress(progress);
            actualizarPorcentaje();
//            if (minimizado) {
//                maximizar();
//            }
        }
    }

    public void setMax(int max) {
        if (this.mode != NOTIFICATION_PROGRESS) {
            Toast.makeText(activity, "La notificación no permite progress", Toast.LENGTH_SHORT).show();
        } else {
            ((ProgressBar) findViewById(R.id.progressBar)).setMax(max);
            actualizarPorcentaje();
        }
    }

    private void actualizarPorcentaje() {
        int porcentaje = ( getProgress() * 100 ) / getMax();
        ((TextView) findViewById(R.id.txtPorcentajeProgress)).setText(porcentaje + "%");
    }

    public int getProgress() {
        if (this.mode != NOTIFICATION_PROGRESS) {
            Toast.makeText(activity, "La notificación no permite progress", Toast.LENGTH_SHORT).show();
            return 0;
        } else {
            return ((ProgressBar) findViewById(R.id.progressBar)).getProgress();
        }
    }

    public int getMax() {
        if (this.mode != NOTIFICATION_PROGRESS) {
            Toast.makeText(activity, "La notificación no permite progress", Toast.LENGTH_SHORT).show();
            return 0;
        } else {
            return ((ProgressBar) findViewById(R.id.progressBar)).getMax();
        }
    }

    public void setText(String text) {
        ((TextView)findViewById(R.id.txtDecripcion)).setText(text);
    }

    public void setAction(String action) {
        ((TextView)findViewById(R.id.btnNormal)).setText(action);
    }

    public void showText(String text) {
        if (mode != CustomNotification.NOTIFICATION_SIMPLE) {
            Toast.makeText(activity, "La notificación no permite simple", Toast.LENGTH_SHORT).show();
        } else {
            this.setText(text);
            this.show();
        }
    }

    private void temporizador() {
        if (timer != null) timer.cancel();
        if (showTime == LENGTH_UNDEFINED) return ;
        timer = new Timer();
        timer.schedule(reiniciarShowTime(), showTime);
    }

    private void crearTouchListener(){
        findViewById(R.id.notificationBox).setOnTouchListener(new OnSwipeTouchListener(this.context) {
            public void onSwipeBottom(){
                if (!minimizado && !enMovimiento) {
                    CustomNotification.this.minimizar();
                }
            }

            public void onSwipeTop(){
                if (minimizado && !enMovimiento) {
                    CustomNotification.this.maximizar();
                }
            }
        });
    }

    public void setResponse(final CustomNotificationResponse clickListener) {
        findViewById(R.id.btnNormal).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                clickListener.onResponse(CustomNotification.this);
            }
        });
    }

    public void aboveDarkness(boolean darkness) {
        this.darkness = darkness;
    }

    public boolean isAboveDarkness() {
        return this.darkness;
    }

    private void setMode(int mode) {
        this.mode = mode;
        inicializarVistas();
    }

    private synchronized void maximizar(){
        if (this.enMovimiento) return ;
        this.enMovimiento = true;
        int alturaMaximizado = this.notificationBox.getHeight();
        int alturaMinimizado = this.txtDeslizar.getHeight();
        int alturaAuxiliar = (mode == CustomNotification.NOTIFICATION_PROGRESS) ? this.txtDescripcion.getHeight() + CustomNotification.MARGIN_BOTTOM_CABECERO : 0;
        int deslizamiento = alturaMaximizado - alturaMinimizado - alturaAuxiliar;

        final TranslateAnimation mAnimation = new TranslateAnimation(0, 0, deslizamiento, 0);
        mAnimation.setDuration(400);
        mAnimation.setFillAfter(true);
        mAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                txtDeslizar.setText(activity.getString(R.string.custom_notification_maximizar));
                minimizado = false;
                enMovimiento = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        if (this.darkness) {
            this.trans.startTransition(400);
            findViewById(R.id.touchListenerLayout).setOnTouchListener(noClickableUnder);
        }

        CustomNotification.this.notificationBox.startAnimation(mAnimation);
    }

    private synchronized void minimizar(){
        if (this.enMovimiento) return ;
        this.enMovimiento = true;
        int alturaMaximizado = this.notificationBox.getHeight();
        int alturaMinimizado = this.txtDeslizar.getHeight();
        int alturaAuxiliar = (mode == CustomNotification.NOTIFICATION_PROGRESS) ? this.txtDescripcion.getHeight() + CustomNotification.MARGIN_BOTTOM_CABECERO : 0;
        int deslizamiento = alturaMaximizado - alturaMinimizado - alturaAuxiliar;
        this.alturaMinimizado = deslizamiento;

        final TranslateAnimation mAnimation = new TranslateAnimation(0,0,0,deslizamiento);
        mAnimation.setDuration(400);
        mAnimation.setFillAfter(true);
        mAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                txtDeslizar.setText(activity.getString(R.string.custom_notification_minimizar));
                minimizado = true;
                enMovimiento = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        if (this.darkness) {
            this.trans.reverseTransition(400);
            findViewById(R.id.touchListenerLayout).setOnTouchListener(clickableUnder);
        }

        CustomNotification.this.notificationBox.startAnimation(mAnimation);
    }

    // MÉTODOS FRAMELAYOUT

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (this.firstTime) {
            this.firstTime = false;
            CustomNotification.this.notificationBox.startAnimation(enterAnimation());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.inicializar();
    }

    // ANIMACIONES

    private Animation enterAnimation() {
        this.enMovimiento = true;
        int alturaMaximizado = this.notificationBox.getHeight();
        int alturaAuxiliar = 0;
        if (minimizado) alturaAuxiliar = alturaMinimizado;

        final TranslateAnimation mAnimation = new TranslateAnimation(0,0, alturaMaximizado, alturaAuxiliar);
        mAnimation.setDuration(400);
        mAnimation.setFillAfter(true);
        mAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                enMovimiento = false;
                temporizador();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        if (this.darkness) {
            if (this.trans == null) {
                ColorDrawable colorOscuro = new ColorDrawable(getResources().getColor(R.color.colorDarkness));
                ColorDrawable colorTransparente = new ColorDrawable(getResources().getColor(R.color.colorTransparent));
                ColorDrawable[] color = {colorTransparente, colorOscuro};
                this.trans = new TransitionDrawable(color);
                findViewById(R.id.touchListenerLayout).setBackground(this.trans);
                findViewById(R.id.touchListenerLayout).setOnTouchListener(noClickableUnder);
            }
            this.trans.startTransition(400);
        }

        return mAnimation;
    }

    public void kill() {
        hide = true;
        if (CustomNotification.this.getParent() != null) ((ViewGroup)CustomNotification.this.getParent()).removeView(CustomNotification.this);
        firstTime = true;
    }

    private synchronized Animation leaveAnimation() {
        this.enMovimiento = true;
        int alturaMaximizado = this.notificationBox.getHeight();
        int alturaMinimizado = this.txtDeslizar.getHeight();
        int comienzo = (minimizado) ? alturaMaximizado - alturaMinimizado : 0;

        final TranslateAnimation mAnimation = new TranslateAnimation(0,0, comienzo, alturaMaximizado);
        mAnimation.setDuration(400);
        mAnimation.setFillAfter(true);
        mAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                enMovimiento = false;
                hide = true;
                ((ViewGroup)CustomNotification.this.getParent()).removeView(CustomNotification.this);
                firstTime = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        if (this.darkness && !minimizado && trans != null) {
            this.trans.reverseTransition(400);
            findViewById(R.id.touchListenerLayout).setOnTouchListener(clickableUnder);
            this.trans = null;
        }

        return mAnimation;
    }

    private OnTouchListener noClickableUnder = new OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return true;
        }
    };

    private OnTouchListener clickableUnder = new OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return false;
        }
    };

    // BUILDER

    public static class Builder {
        private CustomNotification customNotification;

        public Builder(Context context) {
            customNotification = new CustomNotification(context);
        }

        public Builder setDuration(int duration) {
            customNotification.setDuration(duration);
            return this;
        }

        public BuilderButton setButtonMode() {
            customNotification.setMode(NOTIFICATION_BUTTON);
            return new CustomNotification.BuilderButton(customNotification);
        }

        public BuilderSimple setSimpleMode() {
            customNotification.setMode(NOTIFICATION_SIMPLE);
            return new CustomNotification.BuilderSimple(customNotification);
        }

        public BuilderProgress setProgressMode() {
            customNotification.setMode(NOTIFICATION_PROGRESS);
            return new CustomNotification.BuilderProgress(customNotification);
        }

        public BuilderMultiple setMultipleMode() {
            customNotification.setMode(NOTIFICATION_MULTIPLE);
            return new CustomNotification.BuilderMultiple(customNotification);
        }
    }

    public static class BuilderButton {
        private CustomNotification customNotification;
        private BuilderButton(CustomNotification customNotification) {
            this.customNotification = customNotification;
        }

        public BuilderButton setResponse(CustomNotificationResponse response) {
            customNotification.setResponse(response);
            return this;
        }

        public BuilderButton aboveDarkness(boolean darkness) {
            customNotification.aboveDarkness(darkness);
            return this;
        }

        public BuilderButton setText(String text) {
            customNotification.setText(text);
            return this;
        }

        public BuilderButton setAction(String action) {
            customNotification.setAction(action);
            return this;
        }

        public BuilderButton setDuration(int duration) {
            customNotification.setDuration(duration);
            return this;
        }

        public CustomNotification build() {
            return customNotification;
        }
    }

    public static class BuilderSimple {
        private CustomNotification customNotification;
        private BuilderSimple(CustomNotification customNotification) {
            this.customNotification = customNotification;
        }

        public BuilderSimple setText(String text) {
            customNotification.setText(text);
            return this;
        }

        public BuilderSimple aboveDarkness(boolean darkness) {
            customNotification.aboveDarkness(darkness);
            return this;
        }

        public BuilderSimple setDuration(int duration) {
            customNotification.setDuration(duration);
            return this;
        }

        public CustomNotification build() {
            return customNotification;
        }
    }

    public static class BuilderProgress {
        private CustomNotification customNotification;
        private BuilderProgress(CustomNotification customNotification) {
            this.customNotification = customNotification;
            this.customNotification.setMax(100);
            this.customNotification.setProgress(0);
        }

        public BuilderProgress setText(String text) {
            customNotification.setText(text);
            return this;
        }

        public BuilderProgress aboveDarkness(boolean darkness) {
            customNotification.aboveDarkness(darkness);
            return this;
        }

        public BuilderProgress setDuration(int duration) {
            customNotification.setDuration(duration);
            return this;
        }

        public BuilderProgress setMax(int max) {
            customNotification.setMax(max);
            return this;
        }

        public BuilderProgress setProgress(int progress) {
            customNotification.setProgress(progress);
            return this;
        }

        public CustomNotification build() {
            customNotification.built = true;
            return customNotification;
        }
    }

    public static class BuilderMultiple {
        private CustomNotification customNotification;
        private BuilderMultiple(CustomNotification customNotification) {
            this.customNotification = customNotification;
        }

        public BuilderMultiple setDuration(int duration) {
            customNotification.setDuration(duration);
            return this;
        }

        public BuilderMultiple setText(String text) {
            customNotification.setText(text);
            return this;
        }


        public BuilderMultiple aboveDarkness(boolean darkness) {
            customNotification.aboveDarkness(darkness);
            return this;
        }

        public BuilderMultiple addButton(String text, final CustomNotificationResponse response, int style) {
            this.customNotification.AddButton(text, response, style);
            return this;
        }

        public CustomNotification build() {
            return customNotification;
        }
    }
}