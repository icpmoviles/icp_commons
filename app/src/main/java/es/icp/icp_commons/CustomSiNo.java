package es.icp.icp_commons;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;

import es.icp.icp_commons.Interfaces.CustomSmartDialogQResponse;
import es.icp.icp_commons.Interfaces.CustomSmartDialogSiNoResponse;

@SuppressLint("AppCompatCustomView")
public class CustomSiNo extends LinearLayout {
    private Context                       context;
    private CustomSmartDialogSiNoResponse listener;
    private String positivo = "SI";
    private String negativo= "NO";

    public CustomSiNo(Context context, String positivo, String negativo) {
        this(context, null);
        this.positivo = positivo;
        this.negativo = negativo;
    }

    public CustomSiNo(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomSiNo(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public CustomSiNo(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
    }

    public void show() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        LinearLayout mainContainer = (LinearLayout) inflater.inflate(R.layout.view_custom_si_no, this, true);
        mainContainer.setVisibility(View.VISIBLE);

        Button btnSi = mainContainer.findViewById(R.id.btnSi);
        Button btnNo = mainContainer.findViewById(R.id.btnNo);

        btnSi.setText(positivo);
        btnNo.setText(negativo);

        if (listener != null) {
            btnSi.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.positivo();
                }
            });
            btnNo.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.negativo();
                }
            });
        }
    }

    public CustomSmartDialogSiNoResponse getListener() {
        return listener;
    }

    public void setListener(CustomSmartDialogSiNoResponse listener) {
        this.listener = listener;
    }

    public static class Builder {
        private CustomSiNo customSiNo;

        public Builder(Context context, String positivo ,String negativo) {
            customSiNo = new CustomSiNo(context, positivo, negativo);
        }

        public Builder setListener(CustomSmartDialogSiNoResponse listener) {
            customSiNo.listener = listener;
            return this;
        }

        public CustomSiNo build() {
            customSiNo.show();
            return customSiNo;
        }
    }
}
