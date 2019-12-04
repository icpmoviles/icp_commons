package es.icp.icp_commons;

import android.annotation.SuppressLint;
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

@SuppressWarnings("unused")
public class CustomNotification extends FrameLayout {

    // PROPIEDADES

    private Context context;
    private TextView txtDescripcion;
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


    // CONSTRUCTOR

    private CustomNotification(Context context) {
        super(context);
        this.context = context;

        activity = (Activity) context;
        this.trans = null;
    }

    /**
     * Obtiene la Activity en la que está siendo usada la notificación.
     *
     * @author Ventura de Lucas
     * @return Devuelve la Activity de la notificación.
     */
    public Activity getActivity() {
        return activity;
    }

    /**
     * Modifica la Activity en la que está siendo usada la notificación.
     *
     * @author Ventura de Lucas
     * @param activity Activity. Activity de la notificación.
     */
    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    // MÉTODOS PÚBLICOS

    /**
     * Muestra la notificación en pantalla.
     *
     * @author Ventura de Lucas
     */
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

    /**
     * Oculta la notificación.
     *
     * @author Ventura de Lucas
     */
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

    /**
     * Añade un botón a la notificación. (sólo sirve en el caso de que sea una NOTIFICATION_MULTIPLE)
     *
     * @author Ventura de Lucas
     * @param text String. Texto del botón.
     * @param response CustomNotificationResponse. Listener para saber cuándo es clicado el botón.
     * @param style int. Estilo del botón.
     */
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

        if (inflater != null) {
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
    }

    private void inicializar() {
        this.txtDescripcion = findViewById(R.id.txtDecripcion);
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

    /**
     * Modifica el progreso visualizado por la notificación. (sólo sirve en el caso de que sea una NOTIFICATION_PROGRESS)
     *
     * @author Ventura de Lucas
     * @param progress int. Valor numérico del nuevo progreso para el progressBar de la notificación.
     */
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

    /**
     * Modifica el máximo valor del progreso de la notificación. (sólo sirve en el caso de que sea una NOTIFICATION_PROGRESS)
     *
     * @author Ventura de Lucas
     * @param max int. Valor numérico del nuevo máximo para el progressBar de la notificación.
     */
    public void setMax(int max) {
        if (this.mode != NOTIFICATION_PROGRESS) {
            Toast.makeText(activity, "La notificación no permite progress", Toast.LENGTH_SHORT).show();
        } else {
            ((ProgressBar) findViewById(R.id.progressBar)).setMax(max);
            actualizarPorcentaje();
        }
    }

    @SuppressLint("SetTextI18n")
    private void actualizarPorcentaje() {
        int porcentaje = ( getProgress() * 100 ) / getMax();
        ((TextView) findViewById(R.id.txtPorcentajeProgress)).setText(porcentaje + "%");
    }

    /**
     * Obtiene el progreso actual del progressBar de la notificación. (sólo sirve en el caso de que sea una NOTIFICATION_PROGRESS)
     *
     * @author Ventura de Lucas
     * @return Devuelve el progreso actual del progressBar de la notificación.
     */
    public int getProgress() {
        if (this.mode != NOTIFICATION_PROGRESS) {
            Toast.makeText(activity, "La notificación no permite progress", Toast.LENGTH_SHORT).show();
            return 0;
        } else {
            return ((ProgressBar) findViewById(R.id.progressBar)).getProgress();
        }
    }

    /**
     * Obtiene el máximo valor del progreso del progressBar de la notificación. (sólo sirve en el caso de que sea una NOTIFICATION_PROGRESS)
     *
     * @author Ventura de Lucas
     * @return Devuelve el máximo valor del progressBar de la notificación.
     */
    public int getMax() {
        if (this.mode != NOTIFICATION_PROGRESS) {
            Toast.makeText(activity, "La notificación no permite progress", Toast.LENGTH_SHORT).show();
            return 0;
        } else {
            return ((ProgressBar) findViewById(R.id.progressBar)).getMax();
        }
    }

    /**
     * Modifica el texto del cuerpo de la notificación.
     *
     * @author Ventura de Lucas
     * @param text String. Nuevo texto del cuerpo de la notificación.
     */
    public void setText(String text) {
        ((TextView)findViewById(R.id.txtDecripcion)).setText(text);
    }

    /**
     * Modifica el texto del botón de la notificación. (sólo sirve en el caso de que sea una NOTIFICATION_BUTTON)
     *
     * @author Ventura de Lucas
     * @param action String. Nuevo texto del botón de la notificación.
     */
    public void setAction(String action) {
        ((TextView)findViewById(R.id.btnNormal)).setText(action);
    }

    /**
     * Modifica el texto del cuerpo de la notificación.
     * Posteriormente, muestra dicha notificación.
     *
     * @author Ventura de Lucas
     * @param text String. Nuevo texto del cuerpo de la notificación.
     */
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

    /**
     * Modifica la respuesta del botón de la notificación. (sólo sirve en el caso de que sea una NOTIFICATION_BUTTON)
     *
     * @author Ventura de Lucas
     * @param clickListener CustomNotificationResponse. Nuevo listener para la respuesta del botón de la notificación.
     */
    public void setResponse(final CustomNotificationResponse clickListener) {
        findViewById(R.id.btnNormal).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                clickListener.onResponse(CustomNotification.this);
            }
        });
    }

    /**
     * Muestra un fondo oscuro detrás de la notificación para resaltarla.
     * Se bloquea el 'touch' de lo que haya detrás de dicho fondo.
     *
     * @author Ventura de Lucas
     * @param darkness boolean. Sirve para indicar si se desea o no activar este modo. Por defecto, se encuentra a 'false'.
     */
    public void aboveDarkness(boolean darkness) {
        this.darkness = darkness;
    }

    /**
     * Indica si está o no activado el modo 'Darkness'. (fondo oscuro detrás de la notificación)
     *
     * @author Ventura de Lucas
     * @return Devuelve 'true' se el modo 'Darkness' se encuentra activo; en caso contrario, devuelve 'false'
     */
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

    @SuppressLint("ClickableViewAccessibility")
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

    /**
     * Sirve para 'matar' la notificación. Se oculta, se elimina de la vista y se resetean todos los valores.
     *
     * @author Ventura de Lucas
     */
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
        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return true;
        }
    };

    private OnTouchListener clickableUnder = new OnTouchListener() {
        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return false;
        }
    };

    // BUILDER

    /**
     * Clase Builder básica para la construcción guiada del objeto CustomNotification.
     *
     * @author Ventura de Lucas
     */
    public static class Builder {
        private CustomNotification customNotification;

        /**
         * Constructor Builder
         *
         * @author Ventura de Lucas
         * @param context Context. Contexto de la aplicación.
         */
        public Builder(Context context) {
            customNotification = new CustomNotification(context);
        }

        /**
         * Establece el tiempo que se mostrará la notificación:
         * SHORT: 2 segundos.
         * MEDIUM: 5 segundos.
         * LONG: 8 segundos.
         * UNDEFINED: siempre se muestra (tiempo infinito).
         *
         * @author Ventura de Lucas
         * @param duration Valor de constantes (SHORT, MEDIUM, LONG, UNDEFINED). También, se permite introducir un valor numérico a desear en milisegundos.
         */
        public Builder setDuration(int duration) {
            customNotification.setDuration(duration);
            return this;
        }

        /**
         * Acceso a los métodos propios del modo NOTIFICATION_BUTTON
         *
         * @author Ventura de Lucas
         * @return Devuelve un BuilderButton para proceder con una construcción más específica del objeto CustomNotification.
         */
        public BuilderButton setButtonMode() {
            customNotification.setMode(NOTIFICATION_BUTTON);
            return new CustomNotification.BuilderButton(customNotification);
        }

        /**
         * Acceso a los métodos propios del modo NOTIFICATION_SIMPLE
         *
         * @author Ventura de Lucas
         * @return Devuelve un BuilderSimple para proceder con una construcción más específica del objeto CustomNotification.
         */
        public BuilderSimple setSimpleMode() {
            customNotification.setMode(NOTIFICATION_SIMPLE);
            return new CustomNotification.BuilderSimple(customNotification);
        }

        /**
         * Acceso a los métodos propios del modo NOTIFICATION_PROGRESS
         *
         * @author Ventura de Lucas
         * @return Devuelve un BuilderProgress para proceder con una construcción más específica del objeto CustomNotification.
         */
        public BuilderProgress setProgressMode() {
            customNotification.setMode(NOTIFICATION_PROGRESS);
            return new CustomNotification.BuilderProgress(customNotification);
        }

        /**
         * Acceso a los métodos propios del modo NOTIFICATION_MULTIPLE
         *
         * @author Ventura de Lucas
         * @return Devuelve un BuilderMultiple para proceder con una construcción más específica del objeto CustomNotification.
         */
        public BuilderMultiple setMultipleMode() {
            customNotification.setMode(NOTIFICATION_MULTIPLE);
            return new CustomNotification.BuilderMultiple(customNotification);
        }
    }

    /**
     * Builder específico del modo NOTIFICATION_BUTTON
     *
     * @author Ventura de Lucas
     */
    public static class BuilderButton {
        private CustomNotification customNotification;

        /**
         * Constructor BuilderButton
         *
         * @author Ventura de Lucas
         * @param customNotification CustomNotification. Notificación preparada previamente por la clase Builder.
         */
        private BuilderButton(CustomNotification customNotification) {
            this.customNotification = customNotification;
        }

        /**
         * Modifica la respuesta del botón de la notificación. (sólo sirve en el caso de que sea una NOTIFICATION_BUTTON)
         *
         * @author Ventura de Lucas
         * @param response CustomNotificationResponse. Nuevo listener para la respuesta del botón de la notificación.
         */
        public BuilderButton setResponse(CustomNotificationResponse response) {
            customNotification.setResponse(response);
            return this;
        }

        /**
         * Indica si está o no activado el modo 'Darkness'. (fondo oscuro detrás de la notificación)
         *
         * @author Ventura de Lucas
         * @return Devuelve 'true' se el modo 'Darkness' se encuentra activo; en caso contrario, devuelve 'false'
         */
        public BuilderButton aboveDarkness(boolean darkness) {
            customNotification.aboveDarkness(darkness);
            return this;
        }

        /**
         * Modifica el texto del cuerpo de la notificación.
         *
         * @author Ventura de Lucas
         * @param text String. Nuevo texto del cuerpo de la notificación.
         */
        public BuilderButton setText(String text) {
            customNotification.setText(text);
            return this;
        }

        /**
         * Modifica el texto del botón de la notificación. (sólo sirve en el caso de que sea una NOTIFICATION_BUTTON)
         *
         * @author Ventura de Lucas
         * @param action String. Nuevo texto del botón de la notificación.
         */
        public BuilderButton setAction(String action) {
            customNotification.setAction(action);
            return this;
        }

        /**
         * Establece el tiempo que se mostrará la notificación:
         * SHORT: 2 segundos.
         * MEDIUM: 5 segundos.
         * LONG: 8 segundos.
         * UNDEFINED: siempre se muestra (tiempo infinito).
         *
         * @author Ventura de Lucas
         * @param duration Valor de constantes (SHORT, MEDIUM, LONG, UNDEFINED). También, se permite introducir un valor numérico a desear en milisegundos.
         */
        public BuilderButton setDuration(int duration) {
            customNotification.setDuration(duration);
            return this;
        }

        /**
         * Se termina la construcción de la notificación.
         *
         * @author Ventura de Lucas
         * @return CustomNotification. La notificación está lista para mostrarse (llamar al método show())
         */
        public CustomNotification build() {
            return customNotification;
        }
    }

    /**
     * Builder específico del modo NOTIFICATION_SIMPLE
     *
     * @author Ventura de Lucas
     */
    public static class BuilderSimple {
        private CustomNotification customNotification;

        /**
         * Constructor BuilderSimple
         *
         * @author Ventura de Lucas
         * @param customNotification CustomNotification. Notificación preparada previamente por la clase Builder.
         */
        private BuilderSimple(CustomNotification customNotification) {
            this.customNotification = customNotification;
        }

        /**
         * Modifica el texto del cuerpo de la notificación.
         *
         * @author Ventura de Lucas
         * @param text String. Nuevo texto del cuerpo de la notificación.
         */
        public BuilderSimple setText(String text) {
            customNotification.setText(text);
            return this;
        }

        /**
         * Indica si está o no activado el modo 'Darkness'. (fondo oscuro detrás de la notificación)
         *
         * @author Ventura de Lucas
         * @return Devuelve 'true' se el modo 'Darkness' se encuentra activo; en caso contrario, devuelve 'false'
         */
        public BuilderSimple aboveDarkness(boolean darkness) {
            customNotification.aboveDarkness(darkness);
            return this;
        }

        /**
         * Establece el tiempo que se mostrará la notificación:
         * SHORT: 2 segundos.
         * MEDIUM: 5 segundos.
         * LONG: 8 segundos.
         * UNDEFINED: siempre se muestra (tiempo infinito).
         *
         * @author Ventura de Lucas
         * @param duration Valor de constantes (SHORT, MEDIUM, LONG, UNDEFINED). También, se permite introducir un valor numérico a desear en milisegundos.
         */
        public BuilderSimple setDuration(int duration) {
            customNotification.setDuration(duration);
            return this;
        }

        /**
         * Se termina la construcción de la notificación.
         *
         * @author Ventura de Lucas
         * @return CustomNotification. La notificación está lista para mostrarse (llamar al método show())
         */
        public CustomNotification build() {
            return customNotification;
        }
    }

    /**
     * Builder específico del modo NOTIFICATION_PROGRESS
     *
     * @author Ventura de Lucas
     */
    public static class BuilderProgress {
        private CustomNotification customNotification;

        /**
         * Constructor BuilderProgress.
         * Por defecto, el progressBar se inicializa con un valor máximo de 100 y un progreso del 0%.
         *
         * @author Ventura de Lucas
         * @param customNotification CustomNotification. Notificación preparada previamente por la clase Builder.
         */
        private BuilderProgress(CustomNotification customNotification) {
            this.customNotification = customNotification;
            this.customNotification.setMax(100);
            this.customNotification.setProgress(0);
        }

        /**
         * Modifica el texto del cuerpo de la notificación.
         *
         * @author Ventura de Lucas
         * @param text String. Nuevo texto del cuerpo de la notificación.
         */
        public BuilderProgress setText(String text) {
            customNotification.setText(text);
            return this;
        }

        /**
         * Indica si está o no activado el modo 'Darkness'. (fondo oscuro detrás de la notificación)
         *
         * @author Ventura de Lucas
         * @return Devuelve 'true' se el modo 'Darkness' se encuentra activo; en caso contrario, devuelve 'false'
         */
        public BuilderProgress aboveDarkness(boolean darkness) {
            customNotification.aboveDarkness(darkness);
            return this;
        }

        /**
         * Establece el tiempo que se mostrará la notificación:
         * SHORT: 2 segundos.
         * MEDIUM: 5 segundos.
         * LONG: 8 segundos.
         * UNDEFINED: siempre se muestra (tiempo infinito).
         *
         * @author Ventura de Lucas
         * @param duration Valor de constantes (SHORT, MEDIUM, LONG, UNDEFINED). También, se permite introducir un valor numérico a desear en milisegundos.
         */
        public BuilderProgress setDuration(int duration) {
            customNotification.setDuration(duration);
            return this;
        }

        /**
         * Modifica el máximo valor del progreso de la notificación. (sólo sirve en el caso de que sea una NOTIFICATION_PROGRESS)
         *
         * @author Ventura de Lucas
         * @param max int. Valor numérico del nuevo máximo para el progressBar de la notificación.
         */
        public BuilderProgress setMax(int max) {
            customNotification.setMax(max);
            return this;
        }

        /**
         * Modifica el progreso visualizado por la notificación. (sólo sirve en el caso de que sea una NOTIFICATION_PROGRESS)
         *
         * @author Ventura de Lucas
         * @param progress int. Valor numérico del nuevo progreso para el progressBar de la notificación.
         */
        public BuilderProgress setProgress(int progress) {
            customNotification.setProgress(progress);
            return this;
        }

        /**
         * Se termina la construcción de la notificación.
         *
         * @author Ventura de Lucas
         * @return CustomNotification. La notificación está lista para mostrarse (llamar al método show())
         */
        public CustomNotification build() {
            customNotification.built = true;
            return customNotification;
        }
    }

    /**
     * Builder específico del modo NOTIFICATION_PROGRESS
     *
     * @author Ventura de Lucas
     */
    public static class BuilderMultiple {
        private CustomNotification customNotification;
        private BuilderMultiple(CustomNotification customNotification) {
            this.customNotification = customNotification;
        }

        /**
         * Establece el tiempo que se mostrará la notificación:
         * SHORT: 2 segundos.
         * MEDIUM: 5 segundos.
         * LONG: 8 segundos.
         * UNDEFINED: siempre se muestra (tiempo infinito).
         *
         * @author Ventura de Lucas
         * @param duration Valor de constantes (SHORT, MEDIUM, LONG, UNDEFINED). También, se permite introducir un valor numérico a desear en milisegundos.
         */
        public BuilderMultiple setDuration(int duration) {
            customNotification.setDuration(duration);
            return this;
        }

        /**
         * Modifica el texto del cuerpo de la notificación.
         *
         * @author Ventura de Lucas
         * @param text String. Nuevo texto del cuerpo de la notificación.
         */
        public BuilderMultiple setText(String text) {
            customNotification.setText(text);
            return this;
        }

        /**
         * Indica si está o no activado el modo 'Darkness'. (fondo oscuro detrás de la notificación)
         *
         * @author Ventura de Lucas
         * @return Devuelve 'true' se el modo 'Darkness' se encuentra activo; en caso contrario, devuelve 'false'
         */
        public BuilderMultiple aboveDarkness(boolean darkness) {
            customNotification.aboveDarkness(darkness);
            return this;
        }

        /**
         * Añade un botón a la notificación. (sólo sirve en el caso de que sea una NOTIFICATION_MULTIPLE)
         *
         * @author Ventura de Lucas
         * @param text String. Texto del botón.
         * @param response CustomNotificationResponse. Listener para saber cuándo es clicado el botón.
         * @param style int. Estilo del botón.
         */
        public BuilderMultiple addButton(String text, final CustomNotificationResponse response, int style) {
            this.customNotification.AddButton(text, response, style);
            return this;
        }

        /**
         * Se termina la construcción de la notificación.
         *
         * @author Ventura de Lucas
         * @return CustomNotification. La notificación está lista para mostrarse (llamar al método show())
         */
        public CustomNotification build() {
            return customNotification;
        }
    }
}
