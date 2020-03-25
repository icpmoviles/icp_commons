package es.icp.icp_commons;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.textfield.TextInputLayout;
import com.viewpagerindicator.LinePageIndicator;

import java.util.ArrayList;
import java.util.List;

import es.icp.icp_commons.Adapters.VisorImagenesAdapter;
import es.icp.icp_commons.Fragments.VisorImagenFragment;
import es.icp.icp_commons.Helpers.DepthPageTransformer;
import es.icp.icp_commons.Utils.Utils;
import es.icp.icp_commons.Interfaces.CustomSmartDialogInputResponse;
import es.icp.icp_commons.Interfaces.CustomSmartDialogQResponse;
import es.icp.icp_commons.Interfaces.CustomSmartDialogQuantityResponse;
import es.icp.icp_commons.Interfaces.CustomSmartDialogResponse;
import es.icp.icp_commons.Interfaces.CustomSmartDialogSiNoResponse;
import es.icp.icp_commons.Objects.SmartButton;

/**
 * Clase CustomSmartDialog. Diálogos creados para el proyecto Securitas Seguridad.
 *
 * @author Ventura de Lucas
 */
public class CustomSmartDialog {

    private        Context      context;
    private        CustomTitle  customTitle;
    private        LinearLayout layout;
    private        List<Button> buttons;
    private        boolean      isCancellable;
    private        Message      message;
    private static AlertDialog  dialog;
    private        boolean      generico = false;
    private static EditText     txtEditText;

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

    /**
     * Método para ocultar el diálogo.
     *
     * @author Ventura de Lucas
     */
    public void dismiss() {
        dialog.dismiss();
    }

    /**
     * Método para cancelar y ocultar el diálogo.
     *
     * @author Ventura de Lucas
     */
    public void cancel() {
        dialog.cancel();
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
        return dialog.isShowing();
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
    public void show() {
        AlertDialog.Builder builder;
        if (generico) {
            builder = new AlertDialog.Builder(context, R.style.CustomDialog)
                    .setCustomTitle(customTitle)
                    .setCancelable(isCancellable)
                    .setView(layout);
        } else {
            builder = new AlertDialog.Builder(context)
                    .setCustomTitle(customTitle)
                    .setCancelable(isCancellable)
                    .setView(layout, Utils.dpToPx(context, 16), Utils.dpToPx(context, 16), Utils.dpToPx(context, 16), Utils.dpToPx(context, 16));
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
        dialog = builder.create();
        for (int i = 0; i < layout.getChildCount(); i++) {
            View view = layout.getChildAt(i);
            if (view instanceof android.widget.Button) {
                final android.widget.Button button = (android.widget.Button) view;
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
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
                        listener.positivo(valor, dialog);
                    }

                    @Override
                    public void negativo(String valor, AlertDialog dialog) {
                        dialog.dismiss();
                        listener.negativo(valor, dialog);
                    }
                });
            }
        }
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                for (final Button button : buttons) {
                    if (button.type == Button.Type.POSSITIVE) {
                        if (button.textColor != 0) dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getColor(button.textColor));
                        if (button.textSize != 0) dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize((float) button.textSize);
                        if (button.onClickListener != null) dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                button.onClickListener.onClick(dialog, 0);
                            }
                        });
                    } else if (button.type == Button.Type.NEGATIVE) {
                        if (button.textColor != 0) dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(context.getColor(button.textColor));
                        if (button.textSize != 0) dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextSize((float) button.textSize);
                        if (button.onClickListener != null) dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                button.onClickListener.onClick(dialog, 0);
                            }
                        });
                    } else {
                        if (button.textColor != 0) dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(context.getColor(button.textColor));
                        if (button.textSize != 0) dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextSize((float) button.textSize);
//                        dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setGravity(Gravity.CENTER_VERTICAL);
                        if (button.onClickListener != null) dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                button.onClickListener.onClick(dialog, 0);
                            }
                        });
                    }
                }
            }
        });
        dialog.show();
        Loading.HideLoading();
    }

    public static CustomSmartDialog dialogSiNo(final Context context, String mensaje, final CustomSmartDialogSiNoResponse listener) {
        return CustomSmartDialog.dialogTextos(context, mensaje, "SI", "NO", listener);
    }

    public static CustomSmartDialog dialogTextos(final Context context, String mensaje, String positivo, String negativo, final CustomSmartDialogSiNoResponse listener) {
        try {
            CustomTitle customTitle = new CustomTitle.Builder(context)
                    .setTitle(context.getString(R.string.custom_smart_dialog_atencion))
                    .setIcon(context.getDrawable(R.drawable.ic_help_outline_black_24dp))
                    .setBackgroundColor(R.color.colorPrimary)
                    .setTextColor(R.color.white)
                    .setIconColor(R.color.white)
                    .build();

            TextView message = new CustomSmartDialog.Message.Builder(context)
                    .setText(mensaje)
                    .build();


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

            return new CustomSmartDialog.Builder(context)
                    .setTitle(customTitle)
                    .addView(imageView)
                    .addView(message)
                    .addView(customSiNo)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static CustomSmartDialog dialogGenerico(final Context context, String titulo, String mensaje, final CustomSmartDialogSiNoResponse listener) {
        return dialogGenerico(context, new DialogConfig(), listener);
    }

    public static CustomSmartDialog dialogGenerico(final Context context, DialogConfig config, final CustomSmartDialogSiNoResponse listener) {
        Loading.ShowLoading(context, context.getString(R.string.cargando), context.getString(R.string.cargando_espere_porfavor), false);
        try {
            LayoutInflater inflater      = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout   mainContainer = (LinearLayout) inflater.inflate(R.layout.view_custom_smart_dialog_all, null);

            //-------------------------------------------------------
            //--------------- BÁSICO -------------------
            TextView              txtTitulo   = mainContainer.findViewById(R.id.txtTitulo);
            TextView              txtMensaje  = mainContainer.findViewById(R.id.txtMensaje);
            android.widget.Button btnPositivo = mainContainer.findViewById(R.id.btnPositivo);
            android.widget.Button btnNegativo = mainContainer.findViewById(R.id.btnNegativo);
            ImageView             imagen      = mainContainer.findViewById(R.id.imagen);
            ImageView             iconoTitulo = mainContainer.findViewById(R.id.iconoTitulo);
            LinearLayout botonesSiNo = mainContainer.findViewById(R.id.botonesSiNo);
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
            //--------------- VISOR IMÁGENES -------------------
            RelativeLayout    rlImagenes = mainContainer.findViewById(R.id.rlImagenes);
            ViewPager         vpImagenes = mainContainer.findViewById(R.id.vpImagenes);
            LinePageIndicator pageIndicator = mainContainer.findViewById(R.id.pageIndicator);
            TextView txtConteoImagenes = mainContainer.findViewById(R.id.txtConteoImagenes);
            ImageView ivPageLeft = mainContainer.findViewById(R.id.ivPageLeft);
            ImageView ivPageRight = mainContainer.findViewById(R.id.ivPageRight);
            //----------------------------------------------------------------------------------------------------

            if (config.getImagen() == null && config.getImagenInt() != 0) config.setImagen(context);
            if (config.getIconoEditText() == null && config.getIconoEditTextInt() != 0) config.setIconoEditText(context);
            if (config.getIconoTitulo() == null && config.getIconoTituloInt() != 0) config.setIconoTitulo(context);

            txtTitulo.setText(Html.fromHtml(config.getTitulo()));
            txtMensaje.setText(Html.fromHtml(config.getMensaje()));
            if (config.getColorTitulo() != 0){
                txtTitulo.setBackgroundColor(context.getColor(config.getColorTitulo()));
            }
            if (config.isMostrarIconoTitulo()) {
                iconoTitulo.setVisibility(View.VISIBLE);
                iconoTitulo.setImageDrawable(config.getIconoTitulo());
            }
            if (config.isMostrarPositivo()) {
                btnPositivo.setVisibility(View.VISIBLE);
                btnPositivo.setText(Html.fromHtml(config.getTextoPositivo()));
                if (!config.isMostrarNegativo()) {
                    if (config.getColorTitulo() != 0){
                        btnPositivo.setBackgroundTintList(ColorStateList.valueOf(context.getColor(config.getColorTitulo())));
                    } else {
                        btnPositivo.setBackgroundTintList(ColorStateList.valueOf(context.getColor(R.color.colorPrimary)));
                    }
                }
                btnPositivo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (config.isAutoDismiss()) dialog.dismiss();
                        if (listener != null) {
                            if (config.isMostrarEditText()) listener.positivo(txtEditText.getText().toString(), dialog);
                            else if (config.isMostrarCantidad()) listener.positivo(txtCantidad.getText().toString(), dialog);
                            else listener.positivo("Positivo", dialog);
                        }
                    }
                });
            }
            if (config.isMostrarNegativo()) {
                btnNegativo.setVisibility(View.VISIBLE);
                btnNegativo.setText(Html.fromHtml(config.getTextoNegativo()));
                if (!config.isMostrarPositivo()) {
                    if (config.getColorTitulo() != 0){
                        btnNegativo.setBackgroundTintList(ColorStateList.valueOf(context.getColor(config.getColorTitulo())));
                    } else {
                        btnNegativo.setBackgroundTintList(ColorStateList.valueOf(context.getColor(R.color.colorPrimary)));
                    }
                }
                btnNegativo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (config.isAutoDismiss()) dialog.dismiss();
                        if (listener != null) {
                            if (config.isMostrarEditText()) listener.negativo(txtEditText.getText().toString(), dialog);
                            else if (config.isMostrarCantidad()) listener.negativo(txtCantidad.getText().toString(), dialog);
                            else listener.negativo("Negativo", dialog);
                        }
                    }
                });
            }

            if (config.isMostrarEditText()) { //----------------------------------------------------------------------------------
                llEditText.setVisibility(View.VISIBLE);
                txtInputLayout.setHint(Html.fromHtml(config.getHint()));
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
                                startIcon.setImageTintList(ColorStateList.valueOf(context.getColor(android.R.color.holo_red_light)));
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
                    android.widget.Button b = new android.widget.Button(context);
                    b.setText(Html.fromHtml(boton.getText().toString()));
                    b.setBackground(context.getDrawable(R.drawable.rounded_blue_button));
                    b.setBackgroundTintList(boton.getBackgroundTintList());
                    b.setAllCaps(false);
                    b.setTextColor(context.getColor(R.color.white));
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (config.isAutoDismiss()) dialog.dismiss();
                            boton.getCustomListener().onClick("", dialog);
                        }
                    });
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(Utils.dpToPx(context, 5), Utils.dpToPx(context, 5), Utils.dpToPx(context, 5), Utils.dpToPx(context, 5));
                    b.setLayoutParams(layoutParams);
                    b.setVisibility(View.VISIBLE);
                    if (botones.size() != 1) llBotones.addView(b);
                    else botonesSiNo.addView(b, 0);
                }

            }
            if (config.isMostrarCantidad()) { //---------------------------------------------------------------------------
                quantity.setVisibility(View.VISIBLE);
                txtCantidad.setText(Html.fromHtml(String.valueOf(config.getCantidadInicial())));
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
                        if (cantidad != 0) cantidad--;
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
            }
            if (config.isMostrarVisorImagenes() && config.getImagenes().size() > 0) {
                rlImagenes.setVisibility(View.VISIBLE);
                VisorImagenesAdapter adapter = new VisorImagenesAdapter(context, config.getImagenes());
                vpImagenes.setAdapter(adapter);
                vpImagenes.setCurrentItem(0);
                pageIndicator.setViewPager(vpImagenes);
                vpImagenes.setPageMargin(20);
                vpImagenes.setPageTransformer(true, new DepthPageTransformer());

                txtConteoImagenes.setText("1/" + config.getImagenes().size());
                ivPageLeft.setVisibility(View.GONE);
                if (config.getImagenes().size() == 1) ivPageRight.setVisibility(View.GONE);
                ivPageLeft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        vpImagenes.setCurrentItem(vpImagenes.getCurrentItem() - 1);
                        if (vpImagenes.getCurrentItem() == 0) ivPageLeft.setVisibility(View.GONE);
                        ivPageRight.setVisibility(View.VISIBLE);
                        txtConteoImagenes.setText(vpImagenes.getCurrentItem() + 1 + "/" + config.getImagenes().size());
                    }
                });
                ivPageRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        vpImagenes.setCurrentItem(vpImagenes.getCurrentItem() + 1);
                        if (vpImagenes.getCurrentItem() == config.getImagenes().size() - 1) ivPageRight.setVisibility(View.GONE);
                        ivPageLeft.setVisibility(View.VISIBLE);
                        txtConteoImagenes.setText(vpImagenes.getCurrentItem() + 1 + "/" + config.getImagenes().size());
                    }
                });
                vpImagenes.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        ivPageLeft.setVisibility(position == 0 ? View.GONE : View.VISIBLE);
                        ivPageRight.setVisibility(position == config.getImagenes().size() - 1 ? View.GONE : View.VISIBLE);
                        txtConteoImagenes.setText(position + 1 + "/" + config.getImagenes().size());
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
            }
            return new CustomSmartDialog.Builder(context)
                    .addView(mainContainer)
                    .isGenerico(true)
                    .isCancelable(config.isCancelable())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static CustomSmartDialog dialogImage(final Context context, String titulo, Drawable iconoTitulo, Drawable image, final CustomSmartDialogResponse listener) {
        try {
            CustomTitle customTitle = new CustomTitle.Builder(context)
                    .setTitle(titulo)
                    .setIcon(iconoTitulo)
                    .setBackgroundColor(R.color.colorPrimary)
                    .setTextColor(R.color.white)
                    .setIconColor(R.color.white)
                    .build();

            CustomSmartDialog.Button buttonAceptar = new Button.Builder(Button.Type.POSSITIVE, context.getString(R.string.custom_smart_dialog_aceptar))
                    .setTextColor(R.color.colorPrimary)
                    .setOnClickListener(new Button.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            listener.onResponse(CustomSmartDialogInputResponse.ACEPTAR, dialog);
                        }
                    })
                    .build();

            ImageView imageView = new ImageView(context);
            imageView.setImageDrawable(image);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.CENTER;
            imageView.setLayoutParams(layoutParams);

            return new CustomSmartDialog.Builder(context)
                    .setTitle(customTitle)
                    .addView(imageView)
                    .addButton(buttonAceptar)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static CustomSmartDialog dialogInputExtra(final Context context, String titulo, String mensaje, String hint, Drawable iconoTitulo, Drawable iconoEditText, int maxLength, final String neutralButtonText, final CustomSmartDialogInputResponse listener) {
        try {
            CustomTitle customTitle = new CustomTitle.Builder(context)
                    .setTitle(titulo)
                    .setIcon(iconoTitulo)
                    .setBackgroundColor(R.color.colorPrimary)
                    .setTextColor(R.color.white)
                    .setIconColor(R.color.white)
                    .build();

            TextView message = new CustomSmartDialog.Message.Builder(context)
                    .setText(mensaje)
                    .build();

            final CustomEditText customEditText = new CustomEditText.Builder(context)
                    .setHintText(hint)
                    .setStartIconDrawable(iconoEditText)
                    .setStartIconColor(R.color.colorPrimary)
                    .setTextAppearance(R.style.MyHintStyle)
                    .setCounterMaxLength(maxLength)
                    .setCounterOverflowAppearance(android.R.color.holo_red_light)
                    .setErrorIconColor(android.R.color.holo_red_light)
                    .build();

            CustomSmartDialog.Button buttonAceptar = new CustomSmartDialog.Button.Builder(CustomSmartDialog.Button.Type.POSSITIVE, context.getString(R.string.custom_smart_dialog_aceptar))
                    .setTextColor(R.color.colorPrimary)
                    .setOnClickListener(new CustomSmartDialog.Button.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            listener.onResponse(CustomSmartDialogInputResponse.ACEPTAR, customEditText.getText(), dialog);
                        }
                    })
                    .build();

            CustomSmartDialog.Button buttonCancelar = new Button.Builder(Button.Type.NEGATIVE, context.getString(R.string.custom_smart_dialog_cancelar))
                    .setTextColor(android.R.color.darker_gray)
                    .setOnClickListener(new Button.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            listener.onResponse(CustomSmartDialogInputResponse.CANCELAR, "", dialog);
                        }
                    })
                    .build();

            CustomSmartDialog.Button buttonNeutral = new CustomSmartDialog.Button.Builder(Button.Type.NEUTRAL, neutralButtonText)
                    .setTextColor(android.R.color.darker_gray)
                    .setOnClickListener(new CustomSmartDialog.Button.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
                            listener.onResponse(CustomSmartDialogInputResponse.NEUTRAL, "", dialog);
                        }
                    })
                    .setTextSize(12)
                    .build();

            return new CustomSmartDialog.Builder(context)
                    .setTitle(customTitle)
                    .addView(message)
                    .addView(customEditText)
                    .addButton(buttonAceptar)
                    .addButton(buttonCancelar)
                    .addButton(buttonNeutral)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static CustomSmartDialog dialogInput(final Context context, String titulo, String mensaje, String hint, Drawable iconoTitulo, Drawable iconoEditText, int maxLength, final CustomSmartDialogInputResponse listener) {
        try {
            CustomTitle customTitle = new CustomTitle.Builder(context)
                    .setTitle(titulo)
                    .setIcon(iconoTitulo)
                    .setBackgroundColor(R.color.colorPrimary)
                    .setTextColor(R.color.white)
                    .setIconColor(R.color.white)
                    .build();

            TextView message = new CustomSmartDialog.Message.Builder(context)
                    .setText(mensaje)
                    .build();

            final CustomEditText customEditText = new CustomEditText.Builder(context)
                    .setHintText(hint)
                    .setStartIconDrawable(iconoEditText)
                    .setStartIconColor(R.color.colorPrimary)
                    .setTextAppearance(R.style.MyHintStyle)
                    .setCounterMaxLength(maxLength)
                    .setCounterOverflowAppearance(android.R.color.holo_red_light)
                    .setErrorIconColor(android.R.color.holo_red_light)
                    .build();

            CustomSmartDialog.Button buttonAceptar = new CustomSmartDialog.Button.Builder(CustomSmartDialog.Button.Type.POSSITIVE, context.getString(R.string.custom_smart_dialog_aceptar))
                    .setTextColor(R.color.colorPrimary)
                    .setOnClickListener(new CustomSmartDialog.Button.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            listener.onResponse(CustomSmartDialogInputResponse.ACEPTAR, customEditText.getText(), dialog);
                        }
                    })
                    .build();

            CustomSmartDialog.Button buttonCancelar = new CustomSmartDialog.Button.Builder(CustomSmartDialog.Button.Type.NEGATIVE, context.getString(R.string.custom_smart_dialog_cancelar))
                    .setTextColor(android.R.color.darker_gray)
                    .setOnClickListener(new CustomSmartDialog.Button.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            listener.onResponse(CustomSmartDialogInputResponse.CANCELAR, "", dialog);
                        }
                    })
                    .build();

            return new CustomSmartDialog.Builder(context)
                    .setTitle(customTitle)
                    .addView(message)
                    .addView(customEditText)
                    .addButton(buttonAceptar)
                    .addButton(buttonCancelar)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static CustomSmartDialog dialogToast(final Context context, String titulo, String mensaje, final CustomSmartDialogResponse listener) {
        try {
            CustomTitle customTitle = new CustomTitle.Builder(context)
                    .setTitle(titulo)
                    .setIcon(context.getDrawable(R.drawable.ic_info_black_24dp))
                    .setBackgroundColor(R.color.colorPrimary)
                    .setTextColor(R.color.white)
                    .setIconColor(R.color.white)
                    .build();

            TextView message = new CustomSmartDialog.Message.Builder(context)
                    .setText(mensaje)
                    .build();

            CustomSmartDialog.Button buttonAceptar = new CustomSmartDialog.Button.Builder(CustomSmartDialog.Button.Type.POSSITIVE, context.getString(R.string.custom_smart_dialog_aceptar))
                    .setTextColor(R.color.colorPrimary)
                    .setOnClickListener(new CustomSmartDialog.Button.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            listener.onResponse(CustomSmartDialogInputResponse.ACEPTAR, dialog);
                        }
                    })
                    .build();

            return new CustomSmartDialog.Builder(context)
                    .setTitle(customTitle)
                    .addView(message)
                    .addButton(buttonAceptar)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Deprecated
    public static CustomSmartDialog dialogToastListener(final Context context, String titulo, String mensaje, final CustomSmartDialogResponse listener) {
        try {
            CustomTitle customTitle = new CustomTitle.Builder(context)
                    .setTitle(titulo)
                    .setIcon(context.getDrawable(R.drawable.ic_info_black_24dp))
                    .setBackgroundColor(R.color.colorPrimary)
                    .setTextColor(R.color.white)
                    .setIconColor(R.color.white)
                    .build();

            TextView message = new CustomSmartDialog.Message.Builder(context)
                    .setText(mensaje)
                    .build();

            CustomSmartDialog.Button buttonAceptar = new CustomSmartDialog.Button.Builder(CustomSmartDialog.Button.Type.POSSITIVE, context.getString(R.string.custom_smart_dialog_aceptar))
                    .setTextColor(R.color.colorPrimary)
                    .setOnClickListener(new CustomSmartDialog.Button.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            listener.onResponse(CustomSmartDialogInputResponse.ACEPTAR, dialog);
                        }
                    })
                    .build();

            return new CustomSmartDialog.Builder(context)
                    .setTitle(customTitle)
                    .addView(message)
                    .addButton(buttonAceptar)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static CustomSmartDialog dialogToast(final Context context, String titulo, String mensaje) {
        try {
            CustomTitle customTitle = new CustomTitle.Builder(context)
                    .setTitle(titulo)
                    .setIcon(context.getDrawable(R.drawable.ic_info_black_24dp))
                    .setBackgroundColor(R.color.colorPrimary)
                    .setTextColor(R.color.white)
                    .setIconColor(R.color.white)
                    .build();

            TextView message = new CustomSmartDialog.Message.Builder(context)
                    .setText(mensaje)
                    .build();

            CustomSmartDialog.Button buttonAceptar = new CustomSmartDialog.Button.Builder(CustomSmartDialog.Button.Type.POSSITIVE, context.getString(R.string.custom_smart_dialog_aceptar))
                    .setTextColor(R.color.colorPrimary)
                    .setOnClickListener(new CustomSmartDialog.Button.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .build();

            return new CustomSmartDialog.Builder(context)
                    .setTitle(customTitle)
                    .addView(message)
                    .addButton(buttonAceptar)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static CustomSmartDialog dialogQuantity(final Context context, String titulo, String mensaje, Drawable iconoTitulo, int cantidadInicial, final CustomSmartDialogQResponse listener) {
        try {
            CustomTitle customTitle = new CustomTitle.Builder(context)
                    .setTitle(titulo)
                    .setIcon(iconoTitulo)
                    .setBackgroundColor(R.color.colorPrimary)
                    .setTextColor(R.color.white)
                    .setIconColor(R.color.white)
                    .build();

            TextView message = new CustomSmartDialog.Message.Builder(context)
                    .setText(mensaje)
                    .build();

            final CustomQuantity customQuantity = new CustomQuantity.Builder(context).setQuantity(cantidadInicial).build();

            CustomSmartDialog.Button buttonAceptar = new CustomSmartDialog.Button.Builder(CustomSmartDialog.Button.Type.POSSITIVE, context.getString(R.string.custom_smart_dialog_aceptar))
                    .setTextColor(R.color.colorPrimary)
                    .setOnClickListener(new CustomSmartDialog.Button.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            listener.onAccept(CustomSmartDialogInputResponse.ACEPTAR, customQuantity.getQuantity(), dialog);
                        }
                    })
                    .build();

            CustomSmartDialog.Button buttonCancelar = new CustomSmartDialog.Button.Builder(CustomSmartDialog.Button.Type.NEGATIVE, context.getString(R.string.custom_smart_dialog_cancelar))
                    .setTextColor(android.R.color.darker_gray)
                    .setOnClickListener(new CustomSmartDialog.Button.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            listener.onCancel(CustomSmartDialogInputResponse.CANCELAR, 0, dialog);
                        }
                    })
                    .build();

            return new CustomSmartDialog.Builder(context)
                    .setTitle(customTitle)
                    .addView(message)
                    .addView(customQuantity)
                    .addButton(buttonAceptar)
                    .addButton(buttonCancelar)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Deprecated
    public static CustomSmartDialog dialogQuantity(final Context context, String titulo, String mensaje, Drawable iconoTitulo, int cantidadInicial, final CustomSmartDialogQuantityResponse listener) {
        try {
            CustomTitle customTitle = new CustomTitle.Builder(context)
                    .setTitle(titulo)
                    .setIcon(iconoTitulo)
                    .setBackgroundColor(R.color.colorPrimary)
                    .setTextColor(R.color.white)
                    .setIconColor(R.color.white)
                    .build();

            TextView message = new CustomSmartDialog.Message.Builder(context)
                    .setText(mensaje)
                    .build();

            final CustomQuantity customQuantity = new CustomQuantity.Builder(context).setQuantity(cantidadInicial).build();

            CustomSmartDialog.Button buttonAceptar = new CustomSmartDialog.Button.Builder(CustomSmartDialog.Button.Type.POSSITIVE, context.getString(R.string.custom_smart_dialog_aceptar))
                    .setTextColor(R.color.colorPrimary)
                    .setOnClickListener(new CustomSmartDialog.Button.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            listener.onResponse(CustomSmartDialogInputResponse.ACEPTAR, customQuantity.getQuantity(), dialog);
                        }
                    })
                    .build();

            CustomSmartDialog.Button buttonCancelar = new CustomSmartDialog.Button.Builder(CustomSmartDialog.Button.Type.NEGATIVE, context.getString(R.string.custom_smart_dialog_cancelar))
                    .setTextColor(android.R.color.darker_gray)
                    .setOnClickListener(new CustomSmartDialog.Button.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            listener.onResponse(CustomSmartDialogInputResponse.CANCELAR, 0, dialog);
                        }
                    })
                    .build();

            return new CustomSmartDialog.Builder(context)
                    .setTitle(customTitle)
                    .addView(message)
                    .addView(customQuantity)
                    .addButton(buttonAceptar)
                    .addButton(buttonCancelar)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static CustomSmartDialog dialogButtons(final Context context, String titulo, Drawable iconoTitulo, CustomSmartDialogButton... buttons) {
        try {
            CustomTitle customTitle = new CustomTitle.Builder(context)
                    .setTitle(titulo)
                    .setIcon(iconoTitulo)
                    .setBackgroundColor(R.color.colorPrimary)
                    .setTextColor(R.color.white)
                    .setIconColor(R.color.white)
                    .build();

            CustomSmartDialog.Builder builder = new CustomSmartDialog.Builder(context)
                    .setTitle(customTitle);

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

            CustomSmartDialog.Button buttonAceptar = new CustomSmartDialog.Button.Builder(Button.Type.NEGATIVE, context.getString(R.string.custom_smart_dialog_cancelar))
                    .setTextColor(R.color.colorPrimary)
                    .build();

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

        public Builder addView(View view) {
            customSmartDialog.layout.addView(view);
            return this;
        }

        public Builder setTitle(CustomTitle customTitle) {
            customSmartDialog.customTitle = customTitle;
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
}
