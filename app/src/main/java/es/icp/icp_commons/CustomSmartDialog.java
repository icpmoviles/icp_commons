package es.icp.icp_commons;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;

import es.icp.icp_commons.Helpers.Utils;
import es.icp.icp_commons.Interfaces.CustomSmartDialogInputResponse;

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
                // TODO : Neutral Type Button
            }
        }
        dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                for (final Button button : buttons) {
                    if (button.type == Button.Type.POSSITIVE) {
                        if (button.textColor != 0)  dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getColor(button.textColor));
                        if (button.textSize != 0)  dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize((float) button.textSize);
                    } else if (button.type == Button.Type.NEGATIVE) {
                        if (button.textColor != 0)  dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(context.getColor(button.textColor));
                        if (button.textSize != 0)  dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextSize((float) button.textSize);
                    }
                }
            }
        });
        dialog.show();
    }

    public static void dialogInput(final Context context, String titulo, String mensaje, String hint, Drawable iconoTitulo, Drawable iconoEditText, int maxLength, final CustomSmartDialogInputResponse listener) {
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
                    .setErrorIconColor(android.R.color.holo_red_dark)
                    .build();

            CustomSmartDialog.Button buttonAceptar = new CustomSmartDialog.Button.Builder(CustomSmartDialog.Button.Type.POSSITIVE, "ACEPTAR")
                    .setTextColor(R.color.colorPrimary)
                    .setOnClickListener(new CustomSmartDialog.Button.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            listener.onResponse(CustomSmartDialogInputResponse.ACEPTAR, customEditText.getText(), dialog);
                        }
                    })
                    .build();

            CustomSmartDialog.Button buttonCancelar = new CustomSmartDialog.Button.Builder(CustomSmartDialog.Button.Type.NEGATIVE, "CANCELAR")
                    .setTextColor(android.R.color.darker_gray)
                    .setOnClickListener(new CustomSmartDialog.Button.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            listener.onResponse(CustomSmartDialogInputResponse.CANCELAR, "", dialog);
                        }
                    })
                    .build();

            new CustomSmartDialog.Builder(context)
                    .setTitle(customTitle)
                    .addView(message)
                    .addView(customEditText)
                    .addButton(buttonAceptar)
                    .addButton(buttonCancelar)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class Message {
        private TextView mensaje;
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
            return mensaje;
        }
        public static class Builder {
            private Message message;
            public Builder(Context context) {
                message = new Message(context);
            }
            public Builder setText(String text) {
                message.mensaje.setText(text);
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
