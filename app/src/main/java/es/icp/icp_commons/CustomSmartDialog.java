package es.icp.icp_commons;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;

import es.icp.icp_commons.Helpers.Utils;
import es.icp.icp_commons.Interfaces.CustomSmartDialogInputResponse;
import es.icp.icp_commons.Interfaces.CustomSmartDialogQResponse;
import es.icp.icp_commons.Interfaces.CustomSmartDialogQuantityResponse;
import es.icp.icp_commons.Interfaces.CustomSmartDialogResponse;
import es.icp.icp_commons.Interfaces.CustomSmartDialogSiNoResponse;

public class CustomSmartDialog {

    private Context context;
    private CustomTitle customTitle;
    private LinearLayout layout;
    private List<Button> buttons;
    private boolean isCancellable;
    private Message message;
    private AlertDialog dialog;

    public CustomSmartDialog(Context context) {
        this.context = context;
        layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        buttons = new ArrayList<>();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public void cancel() {
        dialog.cancel();
    }

    public void setView(View view) {
        layout.addView(view);
    }

    public View getView(int index) {
        return layout.getChildAt(index);
    }

    public boolean isShowing() {
        return dialog.isShowing();
    }

    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setCustomTitle(customTitle)
                .setCancelable(isCancellable)
                .setView(layout, Utils.dpToPx(context, 16), Utils.dpToPx(context, 16), Utils.dpToPx(context, 16), Utils.dpToPx(context, 16));
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
                CustomSiNo customSiNo = (CustomSiNo) view;
                CustomSmartDialogSiNoResponse listener = customSiNo.getListener();
                customSiNo.setListener(new CustomSmartDialogSiNoResponse() {
                    @Override
                    public void positivo() {
                        dialog.dismiss();
                        listener.positivo();
                    }

                    @Override
                    public void negativo() {
                        dialog.dismiss();
                        listener.negativo();
                    }
                });
            }
        }
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                for (final Button button : buttons) {
                    if (button.type == Button.Type.POSSITIVE) {
                        if (button.textColor != 0)  dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getColor(button.textColor));
                        if (button.textSize != 0)  dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize((float) button.textSize);
                        if (button.onClickListener != null) dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                button.onClickListener.onClick(dialog, 0);
                            }
                        });
                    } else if (button.type == Button.Type.NEGATIVE) {
                        if (button.textColor != 0)  dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(context.getColor(button.textColor));
                        if (button.textSize != 0)  dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextSize((float) button.textSize);
                        if (button.onClickListener != null) dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                button.onClickListener.onClick(dialog, 0);
                            }
                        });
                    } else {
                        if (button.textColor != 0)  dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(context.getColor(button.textColor));
                        if (button.textSize != 0)  dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextSize((float) button.textSize);
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
                public void positivo() {
                    listener.positivo();
                }

                @Override
                public void negativo() {
                    listener.negativo();
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
                params.setMargins(0,0,0, 20);
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
        private String html;
        private Context context;
        public Message(Context context) {
            mensaje = new TextView(context);
            this.context = context;
        }
        public TextView getMensaje() {
            LinearLayout.LayoutParams txtLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            txtLayoutParams.gravity = Gravity.CENTER_VERTICAL;
            txtLayoutParams.topMargin = Utils.dpToPx(context, 8);
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
        private String text;
        private int textSize;
        private int textColor;
        private Type type;
        private OnClickListener onClickListener;
        public enum Type {POSSITIVE, NEGATIVE, NEUTRAL}
        public Button() {
        }
        public Button(String text) {
            this.text = text;
        }
        public Button(String text, int textSize) {
            this.text = text;
            this.textSize = textSize;
        }
        public Button(String text, int textSize, int textColor) {
            this.text = text;
            this.textSize = textSize;
            this.textColor = textColor;
        }
        public Button(String text, int textSize, int textColor, Type type) {
            this.text = text;
            this.textSize = textSize;
            this.textColor = textColor;
            this.type = type;
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
            customSmartDialog = new CustomSmartDialog(context);
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
