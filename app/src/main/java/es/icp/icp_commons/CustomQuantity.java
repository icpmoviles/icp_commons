package es.icp.icp_commons;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class CustomQuantity extends LinearLayout {
    private Context  context;
    private int      quantity;
    private TextView txtQuantity;

    public CustomQuantity(Context context) {
        this(context, null);
    }

    public CustomQuantity(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomQuantity(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public CustomQuantity(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        quantity     = 0;
    }

    public void show() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        LinearLayout mainContainer = (LinearLayout) inflater.inflate(R.layout.view_custom_smart_dialog_quantity, this, true);
        mainContainer.setVisibility(View.VISIBLE);

        Button btnMenos = mainContainer.findViewById(R.id.btnMenos);
        Button btnMas   = mainContainer.findViewById(R.id.btnMas);
        txtQuantity = mainContainer.findViewById(R.id.txtQuantity);

        pintarCantidad();

        btnMenos.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity != 0) {
                    quantity--;
                    pintarCantidad();
                }
            }
        });

        btnMas.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity++;
                pintarCantidad();
            }
        });
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        pintarCantidad();
    }

    public int getQuantity() {
        return quantity;
    }

    private void pintarCantidad() {
        txtQuantity.setText(String.valueOf(quantity));
    }

    public static class Builder {
        private CustomQuantity customQuantity;

        public Builder(Context context) {
            customQuantity = new CustomQuantity(context);
        }

        public Builder setQuantity(int quantity) {
            customQuantity.quantity = quantity;
            return this;
        }

        public CustomQuantity build() {
            customQuantity.show();
            return customQuantity;
        }
    }
}
