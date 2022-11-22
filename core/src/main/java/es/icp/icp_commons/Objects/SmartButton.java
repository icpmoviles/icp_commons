package es.icp.icp_commons.Objects;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;

public class SmartButton extends AppCompatButton {
    protected CustomListener customListener;

    public SmartButton(Context context) {
        super(context);
    }

    public SmartButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SmartButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setCustomListener(CustomListener customListener) {
        this.customListener = customListener;
    }

    public CustomListener getCustomListener() {
        return customListener;
    }

    public interface CustomListener {
        void onClick(String valor, AlertDialog dialog);
    }

}
