package es.icp.icp_commons;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import es.icp.icp_commons.CommonsCore.CommonsExecutors;
import es.icp.icp_commons.Interfaces.CustomSmartDialogInputResponse;
import es.icp.icp_commons.Interfaces.CustomSmartDialogQResponse;
import es.icp.icp_commons.Interfaces.CustomSmartDialogQuantityResponse;
import es.icp.icp_commons.Interfaces.CustomSmartDialogResponse;
import es.icp.icp_commons.Interfaces.CustomSmartDialogSiNoResponse;
import es.icp.icp_commons.Objects.AlertDialog2;
import es.icp.icp_commons.Objects.SmartButton;
import es.icp.icp_commons.Utils.Utils;

/**
 * Clase CustomSmartDialog. Diálogos principalmente creados para el proyecto Securitas Seguridad.
 *
 * @author Ventura de Lucas
 */
public class CustomSmartDialog {

    private static      Context                 context;
    private             CustomTitle             customTitle;
    private             LinearLayout            layout;
    private             List<Button>            buttons;
    private             boolean                 isCancellable;
    private             boolean                 isLoadingDialog  = false;
    private             LoadingListener         loadingListener;
    private             Message                 message;
    private static      boolean                 enConstruccion   = false;
    public static       ArrayList<AlertDialog2> dialogs          = new ArrayList<>();
    private             boolean                 generico         = false;
    private static      EditText                txtEditText;
    private static      NestedScrollView        nestedMensaje;
    private             VisorImagenes           visorImagenes;
    private             int                     tiempo           = 0;
    public static final int                     MAX_HIDE_LOADING = 10;
    public static       int                     contadorLoading  = 0;
    private             boolean                 makeULTRA        = false;
    private             FrameLayout             frameUltra;

    public CustomSmartDialog() {
    }

    /**
     * Constructor de 1 parámetro
     *
     * @param context Context. Contexto de la Activity.
     * @author Ventura de Lucas
     */
    public CustomSmartDialog(Context context) {
        this.context = context;
        layout       = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        buttons = new ArrayList<>();
    }

    public static boolean isEnConstruccion() {
        return enConstruccion;
    }

    /**
     * Método para ocultar el diálogo.
     *
     * @author Ventura de Lucas
     */
    public void dismiss() {
        try {
            dialogs.get(dialogs.size() - 1).getDialog().dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Método para eliminar el diálogo.
     *
     * @author Ventura de Lucas
     */
    public void remove() {
        try {
            dialogs.remove(dialogs.size() - 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Método para eliminar todos los diálogos.
     *
     * @author Ventura de Lucas
     */
    public void removeAll() {
        dialogs.clear();
    }

    /**
     * Método para ocultar el diálogo.
     *
     * @author Ventura de Lucas
     */
    public void dismissLoading() {
        boolean encontrado = false;
        try {
            for (int i = 0; i < dialogs.size(); i++) {
                AlertDialog2 dialog2 = dialogs.get(i);
                if (dialog2.isLoadingDialog()) {
                    encontrado = true;
                    dialog2.getDialog().dismiss();
                    removeLoading(i);
                }
            }
            if (!encontrado && contadorLoading < MAX_HIDE_LOADING) {
                Timer timer = new Timer();
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        dismissLoading();
                    }
                };
                timer.schedule(timerTask, 500);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    /**
     * Método para eliminar el diálogo.
     *
     * @author Ventura de Lucas
     */
    public void removeLoading(int i) {
        try {
            dialogs.remove(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Método para cancelar y ocultar el diálogo.
     *
     * @author Ventura de Lucas
     */
    public void cancel() {
        try {
            dialogs.get(dialogs.size() - 1).getDialog().cancel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Método para añadir una vista nueva al diálogo.
     *
     * @param view View. Vista nueva a añadir.
     * @author Ventura de Lucas
     */
    public void setView(View view) {
        layout.addView(view);
    }

    /**
     * Método para devolver una vista, dependiendo del orden en el que ésta se introdujo. El index empieza en el 0.
     *
     * @param index int. Índice para la extracción de la vista.
     * @return View. Vista devuelta.
     * @author Ventura de Lucas
     */
    public View getView(int index) {
        return layout.getChildAt(index);
    }

    /**
     * Método para indicar si el diálogo se está mostrando actualmente al usuario o no.
     *
     * @return boolean. Devuelve 'true' en caso de que se muestre el diálogo; 'false' en el caso contrario.
     * @author Ventura de Lucas
     */
    public boolean isShowing() {
        boolean isShowing = false;
        try {
            isShowing = dialogs.get(dialogs.size() - 1).getDialog().isShowing();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isShowing;
    }

    /**
     * Método para indicar si el diálogo se está mostrando actualmente al usuario o no.
     *
     * @return boolean. Devuelve 'true' en caso de que se muestre el diálogo; 'false' en el caso contrario.
     * @author Ventura de Lucas
     */
    public boolean isShowingLoading() {
        try {
            for (AlertDialog2 dialog2 : dialogs) {
                if (dialog2.isLoadingDialog()) {
                    return dialogs.get(dialogs.size() - 1).getDialog().isShowing();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Método para, en caso de tener un EditText, modificar el texto del mismo.
     *
     * @param text String. Texto amostrar dentro del EditText.
     * @author Ventura de Lucas
     */
    public void setText(String text) {
        if (txtEditText != null) {
            txtEditText.setText(text);
        }
    }

    /**
     * Método para, en caso de tener un EditText, obtener el texto del mismo.
     *
     * @return String. Texto del EditText. Se devuelve 'null' en caso de no encontrar ningún 'EditText'
     * @author Ventura de Lucas
     */
    @Nullable
    public String getText() {
        if (txtEditText != null) {
            return txtEditText.getText().toString();
        } else {
            return null;
        }
    }

    /**
     * Método para, en caso de tener un EditText, obtenerlo.
     *
     * @return EditText. EdiText del diálogo. Se devuelve 'null' en caso de no encontrar ningún 'EditText'
     * @author Ventura de Lucas
     */
    @Nullable
    public EditText getEditText() {
        if (txtEditText != null) {
            return txtEditText;
        } else {
            return null;
        }
    }

    /**
     * Método para mostrar el diálogo construido anteriormente.
     *
     * @author Ventura de Lucas
     */
    @SuppressLint("RestrictedApi")
    public synchronized void show() {
        AlertDialog.Builder builder = null;
        if (makeULTRA) {
            builder = new AlertDialog.Builder(context, R.style.CustomDialogULTRA).setCustomTitle(customTitle).setCancelable(isCancellable).setView(layout);
        } else {
            if (generico) {
                builder = new AlertDialog.Builder(context, R.style.CustomDialog).setCustomTitle(customTitle).setCancelable(isCancellable).setView(layout);
            } else {
                builder = new AlertDialog.Builder(context).setCustomTitle(customTitle).setCancelable(isCancellable).setView(layout, Utils.dpToPx(context, 16), Utils.dpToPx(context, 16), Utils.dpToPx(context, 16), Utils.dpToPx(context, 16));
            }
        }
        for (final Button button : buttons) {
            if (button.type == Button.Type.POSSITIVE) {
                builder.setPositiveButton(button.text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (button.onClickListener != null) button.onClickListener.onClick(dialog, which);
                    }
                });
            } else if (button.type == Button.Type.NEGATIVE) {
                builder.setNegativeButton(button.text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (button.onClickListener != null) button.onClickListener.onClick(dialog, which);
                    }
                });
            } else {
                builder.setNeutralButton(button.text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (button.onClickListener != null) button.onClickListener.onClick(dialog, which);
                    }
                });
            }
        }
        AlertDialog2 dialog = new AlertDialog2(builder.create(), isLoadingDialog);
        dialogs.add(dialog);
        //        if (isLoadingDialog) isLoadingDialog = false;
        for (int i = 0; i < layout.getChildCount(); i++) {
            View view = layout.getChildAt(i);
            if (view instanceof android.widget.Button) {
                final android.widget.Button button = (android.widget.Button) view;
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            dialogs.get(dialogs.size() - 1).getDialog().dismiss();
                            dialogs.remove(dialogs.size() - 1);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        ((View.OnClickListener) button.getTag()).onClick(v);
                    }
                });
            } else if (view instanceof CustomSiNo) {
                CustomSiNo                    customSiNo = (CustomSiNo) view;
                CustomSmartDialogSiNoResponse listener   = customSiNo.getListener();
                customSiNo.setListener(new CustomSmartDialogSiNoResponse() {
                    @Override
                    public void positivo(String valor, AlertDialog dialog) {
                        dialog.dismiss();
                        try {
                            dialogs.remove(dialogs.size() - 1);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        listener.positivo(valor, dialog);
                    }

                    @Override
                    public void negativo(String valor, AlertDialog dialog) {
                        dialog.dismiss();
                        try {
                            dialogs.remove(dialogs.size() - 1);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        listener.negativo(valor, dialog);
                    }
                });
            }
        }
        try {
            dialogs.get(dialogs.size() - 1).getDialog().setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface arg0) {
                    for (final Button button : buttons) {
                        if (button.type == Button.Type.POSSITIVE) {
                            if (button.textColor != 0) {
                                try {
                                    dialogs.get(dialogs.size() - 1).getDialog().getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getColor(button.textColor));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            if (button.textSize != 0) {
                                try {
                                    dialogs.get(dialogs.size() - 1).getDialog().getButton(AlertDialog.BUTTON_POSITIVE).setTextSize((float) button.textSize);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            if (button.onClickListener != null) {
                                try {
                                    dialogs.get(dialogs.size() - 1).getDialog().getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            try {
                                                button.onClickListener.onClick(dialogs.get(dialogs.size() - 1).getDialog(), 0);
                                                dialogs.remove(dialogs.size() - 1);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } else if (button.type == Button.Type.NEGATIVE) {
                            if (button.textColor != 0) {
                                try {
                                    dialogs.get(dialogs.size() - 1).getDialog().getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(context.getColor(button.textColor));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            if (button.textSize != 0) {
                                try {
                                    dialogs.get(dialogs.size() - 1).getDialog().getButton(AlertDialog.BUTTON_NEGATIVE).setTextSize((float) button.textSize);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            if (button.onClickListener != null) {
                                try {
                                    dialogs.get(dialogs.size() - 1).getDialog().getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            try {
                                                button.onClickListener.onClick(dialogs.get(dialogs.size() - 1).getDialog(), 0);
                                                dialogs.remove(dialogs.size() - 1);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            if (button.textColor != 0) {
                                try {
                                    dialogs.get(dialogs.size() - 1).getDialog().getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(context.getColor(button.textColor));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            if (button.textSize != 0) {
                                try {
                                    dialogs.get(dialogs.size() - 1).getDialog().getButton(AlertDialog.BUTTON_NEUTRAL).setTextSize((float) button.textSize);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            //                        CustomSmartDialog.this.dialog.getDialog().getButton(AlertDialog.BUTTON_NEUTRAL).setGravity(Gravity.CENTER_VERTICAL);
                            if (button.onClickListener != null) {
                                try {
                                    dialogs.get(dialogs.size() - 1).getDialog().getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            try {
                                                button.onClickListener.onClick(dialogs.get(dialogs.size() - 1).getDialog(), 0);
                                                dialogs.remove(dialogs.size() - 1);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        final int indiceActual = dialogs.size() - 1;
        if (tiempo > 0) {
            Timer timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    try {
                        dialogs.get(indiceActual).getDialog().dismiss();
                        dialogs.remove(dialogs.get(indiceActual));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            timer.schedule(timerTask, tiempo);
        }

        try {
            AlertDialog2 dialog2 = dialogs.get(dialogs.size() - 1);
            dialog2.getDialog().show();
            if (makeULTRA) {
                Window window = dialog2.getDialog().getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                window.setGravity(Gravity.BOTTOM);

                window.setBackgroundDrawableResource(android.R.color.transparent);

                window.setWindowAnimations(R.style.DialogAnimation);

            }
            if (!isLoadingDialog) Loading.HideSmartLoading();
            isLoadingDialog = false;
            enConstruccion  = false;
            if (loadingListener != null) loadingListener.onLoadingFinished();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static CustomSmartDialog dialogSiNo(final Context context, String mensaje, final CustomSmartDialogSiNoResponse listener) {
        return CustomSmartDialog.dialogTextos(context, mensaje, "SI", "NO", listener);
    }

    public static CustomSmartDialog dialogTextos(final Context context, String mensaje, String positivo, String negativo, final CustomSmartDialogSiNoResponse listener) {
        try {
            CustomTitle customTitle = new CustomTitle.Builder(context).setTitle(context.getString(R.string.custom_smart_dialog_atencion)).setIcon(context.getDrawable(R.drawable.ic_help_outline_black_24dp)).setBackgroundColor(R.color.colorPrimary).setTextColor(R.color.white).setIconColor(R.color.white).build();

            TextView message = new CustomSmartDialog.Message.Builder(context).setText(mensaje).build();

            ImageView imageView = new ImageView(context);
            imageView.setImageDrawable(context.getDrawable(R.drawable.ic_help_outline_black_100dp));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.CENTER;
            imageView.setLayoutParams(layoutParams);

            CustomSiNo customSiNo = new CustomSiNo.Builder(context, positivo, negativo).setListener(new CustomSmartDialogSiNoResponse() {
                @Override
                public void positivo(String valor, AlertDialog dialog) {
                    listener.positivo(valor, dialog);
                }

                @Override
                public void negativo(String valor, AlertDialog dialog) {
                    listener.negativo(valor, dialog);
                }
            }).build();

            return new CustomSmartDialog.Builder(context).setTitle(customTitle).addView(imageView).addView(message).addView(customSiNo).build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void dialogGenerico(final Context context, String titulo, String mensaje, final CustomSmartDialogSiNoResponse listener) {
        dialogGenerico(context, new DialogConfig(), listener);
    }

    public void dialogGenerico(final Context mContext, DialogConfig config, final CustomSmartDialogSiNoResponse listener) {
        dialogGenerico(mContext, config, listener, null);
    }

    public void dialogGenerico(final Context mContext, DialogConfig config, final CustomSmartDialogSiNoResponse listener, LoadingListener loadingListener) {
        enConstruccion = true;
        context        = mContext;
        if (!config.isMostrarLoading() && config.isWithLoading()) {
            Loading.ShowSmartLoading(mContext, mContext.getString(R.string.cargando), mContext.getString(R.string.cargando_espere_porfavor), false, new LoadingListener() {
                @Override
                public void onLoadingFinished() {
                    crearDialogGenerico(mContext, config, listener, null);
                }
            });
        } else {
            crearDialogGenerico(mContext, config, listener, loadingListener);
        }
    }

    private void crearDialogGenerico(final Context mContext, DialogConfig config, final CustomSmartDialogSiNoResponse listener, LoadingListener loadingListener) {
        CommonsExecutors.getExecutor().Sec().execute(new Runnable() {
            @Override
            public void run() {

                try {
                    LayoutInflater inflater      = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    LinearLayout   mainContainer = (LinearLayout) inflater.inflate(R.layout.view_custom_smart_dialog_all, null);

                    //-------------------------------------------------------
                    //--------------- BÁSICO -------------------
                    TextView              txtTitulo     = mainContainer.findViewById(R.id.txtTitulo);
                    TextView              txtMensaje    = mainContainer.findViewById(R.id.txtMensaje);
                    android.widget.Button btnPositivo   = mainContainer.findViewById(R.id.btnPositivo);
                    android.widget.Button btnNegativo   = mainContainer.findViewById(R.id.btnNegativo);
                    android.widget.Button btnNeutral    = mainContainer.findViewById(R.id.btnNeutral);
                    ImageView             imagen        = mainContainer.findViewById(R.id.imagen);
                    ImageView             iconoTitulo   = mainContainer.findViewById(R.id.iconoTitulo);
                    LinearLayout          botonesSiNo   = mainContainer.findViewById(R.id.botonesSiNo);
                    NestedScrollView      nestedMensaje = mainContainer.findViewById(R.id.nestedMensaje);
                    //----------------------------------------------------------------------------------------------------

                    //-------------------------------------------------------
                    //--------------- EDITTEXT -------------------
                    LinearLayout    llEditText     = mainContainer.findViewById(R.id.editText);
                    TextInputLayout txtInputLayout = mainContainer.findViewById(R.id.txtInputLayout);
                    txtEditText = llEditText.findViewById(R.id.txtEditText);
                    ImageView startIcon = llEditText.findViewById(R.id.startIcon);
                    //----------------------------------------------------------------------------------------------------

                    //-------------------------------------------------------
                    //--------------- CANTIDAD -------------------
                    LinearLayout          quantity    = mainContainer.findViewById(R.id.quantity);
                    TextView              txtCantidad = mainContainer.findViewById(R.id.txtQuantity);
                    android.widget.Button btnMas      = mainContainer.findViewById(R.id.btnMas);
                    android.widget.Button btnMenos    = mainContainer.findViewById(R.id.btnMenos);
                    //----------------------------------------------------------------------------------------------------

                    //-------------------------------------------------------
                    //--------------- IMAGEN -------------------
                    ImageView imagenOpcional = mainContainer.findViewById(R.id.imagenOpcional);
                    //----------------------------------------------------------------------------------------------------

                    //-------------------------------------------------------
                    //--------------- BUTTONS -------------------
                    LinearLayout llBotones = mainContainer.findViewById(R.id.llBotones);
                    //----------------------------------------------------------------------------------------------------

                    //-------------------------------------------------------
                    //--------------- TIEMPO -------------------
                    ProgressBar progressTemporizador = mainContainer.findViewById(R.id.progressTemporizador);
                    TextView    txtCerrando          = mainContainer.findViewById(R.id.txtCerrando);
                    //----------------------------------------------------------------------------------------------------

                    //-------------------------------------------------------
                    //--------------- ULTRA -------------------
                    LinearLayout mainLayout = mainContainer.findViewById(R.id.mainLayout);
                    FrameLayout  frameUltra = mainContainer.findViewById(R.id.frameUltra);
                    //----------------------------------------------------------------------------------------------------

                    //-------------------------------------------------------
                    //--------------- LOADING -------------------
                    //                    ProgressBar pbLoading = mainContainer.findViewById(R.id.pbLoading);
                    //----------------------------------------------------------------------------------------------------

//                    boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
//                    boolean hasHomeKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_HOME);
//                    ((Activity) context).getWindow().getWindowManager().has
//                    if (config.isMakeULTRA()) {
//                        frameUltra.setVisibility(View.VISIBLE);
//                    } else {
//                        frameUltra.setVisibility(View.GONE);
//                    }

                    if (config.isMakeULTRA()) {
                        if (config.getUltraConfig().getMinHeight() != 0f) {
                            mainLayout.setMinimumHeight(Utils.pixelsHeightPercent(context, config.getUltraConfig().getMinHeight()));
                        }
                        mainLayout.setGravity(Gravity.CENTER);
                    }

                    if (config.getTiempo() > 0 && config.isShowTemporizador()) {
                        progressTemporizador.setVisibility(View.VISIBLE);
                        txtCerrando.setVisibility(View.VISIBLE);
                        progressTemporizador.setProgress(0);
                        progressTemporizador.setMax(1000);
                        ObjectAnimator animation = ObjectAnimator.ofInt(progressTemporizador, "progress", 0, 1000);
                        animation.setDuration(config.getTiempo());
                        animation.setInterpolator(new LinearInterpolator());
                        CommonsExecutors.getExecutor().Main().execute(animation::start);
                        CommonsExecutors.getExecutor().Main().execute(new Runnable() {
                            @Override
                            public void run() {
                                new CountDownTimer(config.getTiempo(), 1000) {
                                    public void onTick(long millisUntilFinished) {
                                        CommonsExecutors.getExecutor().Main().execute(() -> txtCerrando.setText("Cerrando en " + (((int) millisUntilFinished) / 900) + " segundos..."));
                                    }

                                    public void onFinish() {
                                        if (listener != null) listener.positivo("", null);
                                    }
                                }.start();
                            }
                        });
                    } else {
                        progressTemporizador.setVisibility(View.GONE);
                        txtCerrando.setVisibility(View.GONE);
                    }

                    if (config.getImagen() == null && config.getImagenInt() != 0) config.setImagen(mContext);
                    if (config.getIconoEditText() == null && config.getIconoEditTextInt() != 0) config.setIconoEditText(mContext);
                    if (config.getIconoTitulo() == null && config.getIconoTituloInt() != 0 && !config.isIconoGif()) config.setIconoTitulo(mContext);

                    txtTitulo.setText(Html.fromHtml(config.getTitulo()));
                    txtMensaje.setText(Html.fromHtml(config.getMensaje()));
                    if (config.isMakeULTRA()) {
                        GradientDrawable shape = new GradientDrawable();
                        shape.setShape(GradientDrawable.RECTANGLE);
                        int pxRadius = Utils.dpToPx(context, 20);
                        shape.setCornerRadii(new float[]{pxRadius, pxRadius, pxRadius, pxRadius, 0, 0, 0, 0});
                        if (config.getColorTitulo() != 0) {
                            shape.setColor(mContext.getColor(config.getColorTitulo()));
                        } else {
                            shape.setColor(ContextCompat.getColor(context, R.color.colorPrimary));
                        }
                        txtTitulo.setBackground(shape);
                    } else {
                        if (config.getColorTitulo() != 0) {
                            txtTitulo.setBackgroundColor(mContext.getColor(config.getColorTitulo()));
                        }
                    }
                    if (config.isMostrarIconoTitulo()) {
                        iconoTitulo.setVisibility(View.VISIBLE);
                        if (config.isIconoGif()) {
                            CommonsExecutors.getExecutor().Main().execute(new Runnable() {
                                @Override
                                public void run() {
                                    //                                    iconoTitulo.setBackground(context.getDrawable(R.drawable.circled_background_white));
                                    Glide.with(context).asGif().load(config.getIconoTituloInt())/*.apply(RequestOptions.circleCropTransform())*/.into(iconoTitulo);
                                }
                            });
                        } else {
                            iconoTitulo.setImageDrawable(config.getIconoTitulo());
                        }
                    }
                    if (config.isMostrarPositivo()) {
                        btnPositivo.setVisibility(View.VISIBLE);
                        btnPositivo.setText(Html.fromHtml(config.getTextoPositivo()));
                        if (!config.isMostrarNegativo()) {
                            if (config.getColorTitulo() != 0) {
                                btnPositivo.setBackgroundTintList(ColorStateList.valueOf(mContext.getColor(config.getColorTitulo())));
                            } else {
                                btnPositivo.setBackgroundTintList(ColorStateList.valueOf(mContext.getColor(R.color.colorPrimary)));
                            }
                        }
                        btnPositivo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (config.isAutoDismiss()) {
                                    try {
                                        dialogs.get(dialogs.size() - 1).getDialog().dismiss();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                if (listener != null) {
                                    if (config.getAdjuntarImagenesListener() != null) config.getAdjuntarImagenesListener().aceptar(config.getImagenes());
                                    if (config.isMostrarEditText()) {
                                        try {
                                            listener.positivo(txtEditText.getText().toString(), dialogs.get(dialogs.size() - 1).getDialog());
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    } else if (config.isMostrarCantidad()) {
                                        try {
                                            listener.positivo(txtCantidad.getText().toString(), dialogs.get(dialogs.size() - 1).getDialog());
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        try {
                                            listener.positivo("Positivo", dialogs.get(dialogs.size() - 1).getDialog());
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                                try {
                                    dialogs.remove(dialogs.size() - 1);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                    if (config.isMostrarNegativo()) {
                        btnNegativo.setVisibility(View.VISIBLE);
                        btnNegativo.setText(Html.fromHtml(config.getTextoNegativo()));
                        if (!config.isMostrarPositivo()) {
                            if (config.getColorTitulo() != 0) {
                                btnNegativo.setBackgroundTintList(ColorStateList.valueOf(mContext.getColor(config.getColorTitulo())));
                            } else {
                                btnNegativo.setBackgroundTintList(ColorStateList.valueOf(mContext.getColor(R.color.colorPrimary)));
                            }
                        }
                        btnNegativo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    if (config.isAutoDismiss()) {
                                        try {
                                            dialogs.get(dialogs.size() - 1).getDialog().dismiss();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    if (listener != null) {
                                        try {
                                            if (config.isMostrarEditText()) listener.negativo(txtEditText.getText().toString(), dialogs.get(dialogs.size() - 1).getDialog());
                                            else if (config.isMostrarCantidad()) listener.negativo(txtCantidad.getText().toString(), dialogs.get(dialogs.size() - 1).getDialog());
                                            else listener.negativo("Negativo", dialogs.get(dialogs.size() - 1).getDialog());
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    try {
                                        dialogs.remove(dialogs.size() - 1);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } catch (IndexOutOfBoundsException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }

                    if (config.isMostrarLoading()) { //----------------------------------------------------------------------------------
                        //                        pbLoading.setVisibility(View.VISIBLE);
                        isLoadingDialog = true;
                    }
                    if (config.isMostrarEditText()) { //----------------------------------------------------------------------------------
                        llEditText.setVisibility(View.VISIBLE);
                        txtInputLayout.setHint(Html.fromHtml(config.getHint()));
                        txtInputLayout.getEditText().setInputType(config.getInputType().getInputType());
                        CommonsExecutors.getExecutor().Main().execute(new Runnable() {
                            @Override
                            public void run() {
                                if (!config.getTextoEditText().equals("")) txtEditText.setText(Html.fromHtml(config.getTextoEditText()));
                            }
                        });
                        if (config.getIconoEditText() != null) startIcon.setImageDrawable(config.getIconoEditText());
                        if (config.getMaxLength() != 0) {
                            txtInputLayout.setCounterMaxLength(config.getMaxLength());
                            txtEditText.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                }

                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {

                                }

                                @Override
                                public void afterTextChanged(Editable s) {
                                    if (s.toString().length() > config.getMaxLength()) {
                                        startIcon.setImageTintList(ColorStateList.valueOf(mContext.getColor(android.R.color.holo_red_light)));
                                    } else {
                                        startIcon.setImageTintList(null);
                                    }
                                }
                            });
                        }
                    }
                    if (config.isMostrarBotones()) { //----------------------------------------------------------------------------
                        llBotones.setVisibility(View.VISIBLE);
                        List<SmartButton> botones = config.getBotones();
                        for (SmartButton boton : botones) {
                            //                    if (boton.getBackground() == null) {
                            //                        boton.setBackground(context.getDrawable(R.drawable.rounded_blue_button));
                            //                    }
                            android.widget.Button b = new android.widget.Button(mContext);
                            b.setText(Html.fromHtml(boton.getText().toString()));
                            b.setBackground(mContext.getDrawable(R.drawable.rounded_blue_button));
                            b.setBackgroundTintList(boton.getBackgroundTintList());
                            b.setAllCaps(false);
                            b.setTextColor(mContext.getColor(R.color.white));
                            b.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (config.isAutoDismiss()) {
                                        try {
                                            dialogs.get(dialogs.size() - 1).getDialog().dismiss();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    try {
                                        boton.getCustomListener().onClick("", dialogs.get(dialogs.size() - 1).getDialog());
                                        dialogs.remove(dialogs.size() - 1);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            layoutParams.setMargins(Utils.dpToPx(mContext, 5), Utils.dpToPx(mContext, 5), Utils.dpToPx(mContext, 5), Utils.dpToPx(mContext, 5));
                            b.setLayoutParams(layoutParams);
                            b.setVisibility(View.VISIBLE);
                            if (botones.size() != 1) llBotones.addView(b);
                            else botonesSiNo.addView(b, 0);
                        }
                    }
                    if (config.isMostrarCantidad()) { //---------------------------------------------------------------------------
                        quantity.setVisibility(View.VISIBLE);
                        int cantidadInicial = 0;
                        if(config.getCantidadMinima() > config.getCantidadInicial()) {
                            cantidadInicial = config.getCantidadMinima();
                        } else {
                            cantidadInicial = config.getCantidadInicial();
                        }
                        txtCantidad.setText(Html.fromHtml(String.valueOf(cantidadInicial)));

                        btnMas.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int cantidad = Integer.parseInt(txtCantidad.getText().toString());
                                cantidad++;
                                txtCantidad.setText(String.valueOf(cantidad));
                            }
                        });
                        btnMenos.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int cantidad = Integer.parseInt(txtCantidad.getText().toString());
                                if (cantidad != 0 && cantidad > config.getCantidadMinima()) {
                                    cantidad--;
                                }
                                txtCantidad.setText(String.valueOf(cantidad));
                            }
                        });
                    }
                    if (config.isMostrarImagen()) { //----------------------------------------------------------------------------
                        imagenOpcional.setVisibility(View.VISIBLE);
                        imagen.setVisibility(View.GONE);
                        imagenOpcional.setImageDrawable(config.getImagen());
                    } //-----------------------------------------------------------------------------------------------------------------
                    if (!config.isMostrarImagenPredeterminada()) {
                        imagen.setVisibility(View.GONE);
                    } //-----------------------------------------------------------------------------------------------------------------
                    if (config.isMakeULTRA()) {
                        mainLayout.setGravity(Gravity.CENTER);
                    }
                    if (config.isMostrarVisorImagenes()) {
                        CommonsExecutors.getExecutor().Main().execute(new Runnable() {
                            @Override
                            public void run() {
                                //                                VisorImagenes visorImagenes = VisorImagenes.getVisor(context);
                                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                nestedMensaje.setLayoutParams(layoutParams);
                                visorImagenes = new VisorImagenes(context, mainContainer, config);
                                //                                visorImagenes.cargarVisorImagenes(context, mainContainer, config);
                            }
                        });
                    } //-----------------------------------------------------------------------------------------------------------------
                    CommonsExecutors.getExecutor().Main().execute(new Runnable() {
                        @Override
                        public void run() {
                            new Builder(mContext).addView(mainContainer).isGenerico(true).makeULTRA(config.isMakeULTRA()).setTiempo(config.getTiempo()).isLoadingDialog(config.isMostrarLoading()).setLoadingListener(loadingListener).isCancelable(config.isCancelable()).build();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static CustomSmartDialog dialogImage(final Context context, String titulo, Drawable iconoTitulo, Drawable image, final CustomSmartDialogResponse listener) {
        try {
            CustomTitle customTitle = new CustomTitle.Builder(context).setTitle(titulo).setIcon(iconoTitulo).setBackgroundColor(R.color.colorPrimary).setTextColor(R.color.white).setIconColor(R.color.white).build();

            CustomSmartDialog.Button buttonAceptar = new Button.Builder(Button.Type.POSSITIVE, context.getString(R.string.custom_smart_dialog_aceptar)).setTextColor(R.color.colorPrimary).setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    listener.onResponse(CustomSmartDialogInputResponse.ACEPTAR, dialog);
                }
            }).build();

            ImageView imageView = new ImageView(context);
            imageView.setImageDrawable(image);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.CENTER;
            imageView.setLayoutParams(layoutParams);

            return new CustomSmartDialog.Builder(context).setTitle(customTitle).addView(imageView).addButton(buttonAceptar).build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static CustomSmartDialog dialogInputExtra(final Context context, String titulo, String mensaje, String hint, Drawable iconoTitulo, Drawable iconoEditText, int maxLength, final String neutralButtonText, final CustomSmartDialogInputResponse listener) {
        try {
            CustomTitle customTitle = new CustomTitle.Builder(context).setTitle(titulo).setIcon(iconoTitulo).setBackgroundColor(R.color.colorPrimary).setTextColor(R.color.white).setIconColor(R.color.white).build();

            TextView message = new CustomSmartDialog.Message.Builder(context).setText(mensaje).build();

            final CustomEditText customEditText = new CustomEditText.Builder(context).setHintText(hint).setStartIconDrawable(iconoEditText).setStartIconColor(R.color.colorPrimary).setTextAppearance(R.style.MyHintStyle).setCounterMaxLength(maxLength).setCounterOverflowAppearance(android.R.color.holo_red_light).setErrorIconColor(android.R.color.holo_red_light).build();

            CustomSmartDialog.Button buttonAceptar = new CustomSmartDialog.Button.Builder(CustomSmartDialog.Button.Type.POSSITIVE, context.getString(R.string.custom_smart_dialog_aceptar)).setTextColor(R.color.colorPrimary).setOnClickListener(new CustomSmartDialog.Button.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    listener.onResponse(CustomSmartDialogInputResponse.ACEPTAR, customEditText.getText(), dialog);
                }
            }).build();

            CustomSmartDialog.Button buttonCancelar = new Button.Builder(Button.Type.NEGATIVE, context.getString(R.string.custom_smart_dialog_cancelar)).setTextColor(android.R.color.darker_gray).setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    listener.onResponse(CustomSmartDialogInputResponse.CANCELAR, "", dialog);
                }
            }).build();

            CustomSmartDialog.Button buttonNeutral = new CustomSmartDialog.Button.Builder(Button.Type.NEUTRAL, neutralButtonText).setTextColor(android.R.color.darker_gray).setOnClickListener(new CustomSmartDialog.Button.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //                            dialog.dismiss();
                    listener.onResponse(CustomSmartDialogInputResponse.NEUTRAL, "", dialog);
                }
            }).setTextSize(12).build();

            return new CustomSmartDialog.Builder(context).setTitle(customTitle).addView(message).addView(customEditText).addButton(buttonAceptar).addButton(buttonCancelar).addButton(buttonNeutral).build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static CustomSmartDialog dialogInput(final Context context, String titulo, String mensaje, String hint, Drawable iconoTitulo, Drawable iconoEditText, int maxLength, final CustomSmartDialogInputResponse listener) {
        try {
            CustomTitle customTitle = new CustomTitle.Builder(context).setTitle(titulo).setIcon(iconoTitulo).setBackgroundColor(R.color.colorPrimary).setTextColor(R.color.white).setIconColor(R.color.white).build();

            TextView message = new CustomSmartDialog.Message.Builder(context).setText(mensaje).build();

            final CustomEditText customEditText = new CustomEditText.Builder(context).setHintText(hint).setStartIconDrawable(iconoEditText).setStartIconColor(R.color.colorPrimary).setTextAppearance(R.style.MyHintStyle).setCounterMaxLength(maxLength).setCounterOverflowAppearance(android.R.color.holo_red_light).setErrorIconColor(android.R.color.holo_red_light).build();

            CustomSmartDialog.Button buttonAceptar = new CustomSmartDialog.Button.Builder(CustomSmartDialog.Button.Type.POSSITIVE, context.getString(R.string.custom_smart_dialog_aceptar)).setTextColor(R.color.colorPrimary).setOnClickListener(new CustomSmartDialog.Button.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    listener.onResponse(CustomSmartDialogInputResponse.ACEPTAR, customEditText.getText(), dialog);
                }
            }).build();

            CustomSmartDialog.Button buttonCancelar = new CustomSmartDialog.Button.Builder(CustomSmartDialog.Button.Type.NEGATIVE, context.getString(R.string.custom_smart_dialog_cancelar)).setTextColor(android.R.color.darker_gray).setOnClickListener(new CustomSmartDialog.Button.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    listener.onResponse(CustomSmartDialogInputResponse.CANCELAR, "", dialog);
                }
            }).build();

            return new CustomSmartDialog.Builder(context).setTitle(customTitle).addView(message).addView(customEditText).addButton(buttonAceptar).addButton(buttonCancelar).build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static CustomSmartDialog dialogToast(final Context context, String titulo, String mensaje, final CustomSmartDialogResponse listener) {
        try {
            CustomTitle customTitle = new CustomTitle.Builder(context).setTitle(titulo).setIcon(context.getDrawable(R.drawable.ic_info_black_24dp)).setBackgroundColor(R.color.colorPrimary).setTextColor(R.color.white).setIconColor(R.color.white).build();

            TextView message = new CustomSmartDialog.Message.Builder(context).setText(mensaje).build();

            CustomSmartDialog.Button buttonAceptar = new CustomSmartDialog.Button.Builder(CustomSmartDialog.Button.Type.POSSITIVE, context.getString(R.string.custom_smart_dialog_aceptar)).setTextColor(R.color.colorPrimary).setOnClickListener(new CustomSmartDialog.Button.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    listener.onResponse(CustomSmartDialogInputResponse.ACEPTAR, dialog);
                }
            }).build();

            return new CustomSmartDialog.Builder(context).setTitle(customTitle).addView(message).addButton(buttonAceptar).build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Deprecated
    public static CustomSmartDialog dialogToastListener(final Context context, String titulo, String mensaje, final CustomSmartDialogResponse listener) {
        try {
            CustomTitle customTitle = new CustomTitle.Builder(context).setTitle(titulo).setIcon(context.getDrawable(R.drawable.ic_info_black_24dp)).setBackgroundColor(R.color.colorPrimary).setTextColor(R.color.white).setIconColor(R.color.white).build();

            TextView message = new CustomSmartDialog.Message.Builder(context).setText(mensaje).build();

            CustomSmartDialog.Button buttonAceptar = new CustomSmartDialog.Button.Builder(CustomSmartDialog.Button.Type.POSSITIVE, context.getString(R.string.custom_smart_dialog_aceptar)).setTextColor(R.color.colorPrimary).setOnClickListener(new CustomSmartDialog.Button.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    listener.onResponse(CustomSmartDialogInputResponse.ACEPTAR, dialog);
                }
            }).build();

            return new CustomSmartDialog.Builder(context).setTitle(customTitle).addView(message).addButton(buttonAceptar).build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static CustomSmartDialog dialogToast(final Context context, String titulo, String mensaje) {
        try {
            CustomTitle customTitle = new CustomTitle.Builder(context).setTitle(titulo).setIcon(context.getDrawable(R.drawable.ic_info_black_24dp)).setBackgroundColor(R.color.colorPrimary).setTextColor(R.color.white).setIconColor(R.color.white).build();

            TextView message = new CustomSmartDialog.Message.Builder(context).setText(mensaje).build();

            CustomSmartDialog.Button buttonAceptar = new CustomSmartDialog.Button.Builder(CustomSmartDialog.Button.Type.POSSITIVE, context.getString(R.string.custom_smart_dialog_aceptar)).setTextColor(R.color.colorPrimary).setOnClickListener(new CustomSmartDialog.Button.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).build();

            return new CustomSmartDialog.Builder(context).setTitle(customTitle).addView(message).addButton(buttonAceptar).build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static CustomSmartDialog dialogQuantity(final Context context, String titulo, String mensaje, Drawable iconoTitulo, int cantidadInicial, final CustomSmartDialogQResponse listener) {
        try {
            CustomTitle customTitle = new CustomTitle.Builder(context).setTitle(titulo).setIcon(iconoTitulo).setBackgroundColor(R.color.colorPrimary).setTextColor(R.color.white).setIconColor(R.color.white).build();

            TextView message = new CustomSmartDialog.Message.Builder(context).setText(mensaje).build();

            final CustomQuantity customQuantity = new CustomQuantity.Builder(context).setQuantity(cantidadInicial).build();

            CustomSmartDialog.Button buttonAceptar = new CustomSmartDialog.Button.Builder(CustomSmartDialog.Button.Type.POSSITIVE, context.getString(R.string.custom_smart_dialog_aceptar)).setTextColor(R.color.colorPrimary).setOnClickListener(new CustomSmartDialog.Button.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    listener.onAccept(CustomSmartDialogInputResponse.ACEPTAR, customQuantity.getQuantity(), dialog);
                }
            }).build();

            CustomSmartDialog.Button buttonCancelar = new CustomSmartDialog.Button.Builder(CustomSmartDialog.Button.Type.NEGATIVE, context.getString(R.string.custom_smart_dialog_cancelar)).setTextColor(android.R.color.darker_gray).setOnClickListener(new CustomSmartDialog.Button.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    listener.onCancel(CustomSmartDialogInputResponse.CANCELAR, 0, dialog);
                }
            }).build();

            return new CustomSmartDialog.Builder(context).setTitle(customTitle).addView(message).addView(customQuantity).addButton(buttonAceptar).addButton(buttonCancelar).build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Deprecated
    public static CustomSmartDialog dialogQuantity(final Context context, String titulo, String mensaje, Drawable iconoTitulo, int cantidadInicial, final CustomSmartDialogQuantityResponse listener) {
        try {
            CustomTitle customTitle = new CustomTitle.Builder(context).setTitle(titulo).setIcon(iconoTitulo).setBackgroundColor(R.color.colorPrimary).setTextColor(R.color.white).setIconColor(R.color.white).build();

            TextView message = new CustomSmartDialog.Message.Builder(context).setText(mensaje).build();

            final CustomQuantity customQuantity = new CustomQuantity.Builder(context).setQuantity(cantidadInicial).build();

            CustomSmartDialog.Button buttonAceptar = new CustomSmartDialog.Button.Builder(CustomSmartDialog.Button.Type.POSSITIVE, context.getString(R.string.custom_smart_dialog_aceptar)).setTextColor(R.color.colorPrimary).setOnClickListener(new CustomSmartDialog.Button.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    listener.onResponse(CustomSmartDialogInputResponse.ACEPTAR, customQuantity.getQuantity(), dialog);
                }
            }).build();

            CustomSmartDialog.Button buttonCancelar = new CustomSmartDialog.Button.Builder(CustomSmartDialog.Button.Type.NEGATIVE, context.getString(R.string.custom_smart_dialog_cancelar)).setTextColor(android.R.color.darker_gray).setOnClickListener(new CustomSmartDialog.Button.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    listener.onResponse(CustomSmartDialogInputResponse.CANCELAR, 0, dialog);
                }
            }).build();

            return new CustomSmartDialog.Builder(context).setTitle(customTitle).addView(message).addView(customQuantity).addButton(buttonAceptar).addButton(buttonCancelar).build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static CustomSmartDialog dialogButtons(final Context context, String titulo, Drawable iconoTitulo, CustomSmartDialogButton... buttons) {
        try {
            CustomTitle customTitle = new CustomTitle.Builder(context).setTitle(titulo).setIcon(iconoTitulo).setBackgroundColor(R.color.colorPrimary).setTextColor(R.color.white).setIconColor(R.color.white).build();

            CustomSmartDialog.Builder builder = new CustomSmartDialog.Builder(context).setTitle(customTitle);

            List<android.widget.Button> botones = new ArrayList<>();
            for (CustomSmartDialogButton button : buttons) {
                android.widget.Button boton = new android.widget.Button(context);
                boton.setBackgroundResource(button.getStyle());
                boton.setTextColor(context.getColor(R.color.white));
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, 0, 0, 20);
                boton.setLayoutParams(params);
                boton.setAllCaps(false);
                boton.setTag(button.getOnClickListener());
                boton.setText(Html.fromHtml(button.getText()));
                botones.add(boton);

                builder.addView(boton);
            }

            CustomSmartDialog.Button buttonAceptar = new CustomSmartDialog.Button.Builder(Button.Type.NEGATIVE, context.getString(R.string.custom_smart_dialog_cancelar)).setTextColor(R.color.colorPrimary).build();

            return builder.addButton(buttonAceptar).build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static class Message {
        private TextView mensaje;
        private String   html;
        private Context  context;

        public Message(Context context) {
            mensaje      = new TextView(context);
            this.context = context;
        }

        public TextView getMensaje() {
            LinearLayout.LayoutParams txtLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            txtLayoutParams.gravity      = Gravity.CENTER_VERTICAL;
            txtLayoutParams.topMargin    = Utils.dpToPx(context, 8);
            txtLayoutParams.bottomMargin = Utils.dpToPx(context, 15);
            mensaje.setLayoutParams(txtLayoutParams);
            mensaje.setTypeface(null, Typeface.BOLD);
            mensaje.setVisibility(View.VISIBLE);
            mensaje.setTextSize(18f);
            mensaje.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            if (!html.isEmpty()) mensaje.setText(Html.fromHtml(html));
            return mensaje;
        }

        public static class Builder {
            private Message message;

            public Builder(Context context) {
                message = new Message(context);
            }

            public Builder setText(String text) {
                message.html = text;
                message.mensaje.setText(Html.fromHtml(text).toString());
                return this;
            }

            public TextView build() {
                return message.getMensaje();
            }
        }
    }

    public static class Button {
        private String          text;
        private int             textSize;
        private int             textColor;
        private Type            type;
        private OnClickListener onClickListener;

        public enum Type {POSSITIVE, NEGATIVE, NEUTRAL}

        public Button() {
        }

        public Button(String text) {
            this.text = text;
        }

        public Button(String text, int textSize) {
            this.text     = text;
            this.textSize = textSize;
        }

        public Button(String text, int textSize, int textColor) {
            this.text      = text;
            this.textSize  = textSize;
            this.textColor = textColor;
        }

        public Button(String text, int textSize, int textColor, Type type) {
            this.text      = text;
            this.textSize  = textSize;
            this.textColor = textColor;
            this.type      = type;
        }

        public Button(Type type, String text) {
            this.type = type;
            this.text = text;
        }

        public interface OnClickListener {
            void onClick(DialogInterface dialog, int which);
        }

        public static class Builder {
            private Button button;

            public Builder(Type type, String text) {
                button = new Button(type, text);
            }

            public Builder setTextSize(int textSize) {
                button.textSize = textSize;
                return this;
            }

            public Builder setTextColor(int textColor) {
                button.textColor = textColor;
                return this;
            }

            public Builder setOnClickListener(OnClickListener onClickListener) {
                button.onClickListener = onClickListener;
                return this;
            }

            public Button build() {
                return button;
            }
        }
    }

    public static class Builder {
        private CustomSmartDialog customSmartDialog;

        public Builder(Context context) {
            customSmartDialog               = new CustomSmartDialog(context);
            customSmartDialog.isCancellable = false;
        }

        public Builder makeULTRA(boolean ultra) {
            customSmartDialog.makeULTRA = ultra;
            return this;
        }

        public Builder addView(View view) {
            customSmartDialog.layout.addView(view);
            return this;
        }

        public Builder setTitle(CustomTitle customTitle) {
            customSmartDialog.customTitle = customTitle;
            return this;
        }

        public Builder setTiempo(int tiempo) {
            customSmartDialog.setTiempo(tiempo);
            return this;
        }

        public Builder setMessage(Message message) {
            customSmartDialog.message = message;
            return this;
        }

        public Builder isGenerico(boolean generico) {
            customSmartDialog.generico = generico;
            return this;
        }

        public Builder isLoadingDialog(boolean loading) {
            customSmartDialog.isLoadingDialog = loading;
            return this;
        }

        public Builder setLoadingListener(LoadingListener loadingListener) {
            customSmartDialog.loadingListener = loadingListener;
            return this;
        }

        public Builder addButton(Button button) {
            customSmartDialog.buttons.add(button);
            return this;
        }

        public Builder isCancelable(boolean isCancellable) {
            customSmartDialog.isCancellable = isCancellable;
            return this;
        }

        public CustomSmartDialog build() {
            customSmartDialog.show();
            return customSmartDialog;
        }
    }

    public interface LoadingListener {
        void onLoadingFinished();
    }
}
