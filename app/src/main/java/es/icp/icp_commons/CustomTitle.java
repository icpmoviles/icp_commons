package es.icp.icp_commons;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;

@SuppressLint("AppCompatCustomView")
public class CustomTitle extends LinearLayout {
    private Context context;
    private int backgroundColor;
    private int textColor;
    private int iconColor;
    private Drawable icon;
    private String title;

    public CustomTitle(Context context) {
        this(context, null);
    }

    public CustomTitle(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTitle(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public CustomTitle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
    }

    public void show() {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        LinearLayout mainContainer = (LinearLayout) inflater.inflate(R.layout.view_custom_title, this, true);
        mainContainer.setVisibility(View.VISIBLE);

        ImageView ivIcono = mainContainer.findViewById(R.id.icono);
        TextView txtTitle = mainContainer.findViewById(R.id.title);

        ivIcono.setImageDrawable(icon);
        ImageViewCompat.setImageTintList(ivIcono, ColorStateList.valueOf(ContextCompat.getColor(context, iconColor)));
        mainContainer.setBackgroundColor(ContextCompat.getColor(context, backgroundColor));
        txtTitle.setText(title);
        txtTitle.setTextColor(ContextCompat.getColor(context, textColor));

    }

    public static class Builder {
        private CustomTitle customTitle;
        public Builder(Context context) {
            customTitle = new CustomTitle(context);
        }
        public Builder setBackgroundColor(int backgroundColor) {
            customTitle.backgroundColor = backgroundColor;
            return this;
        }
        public Builder setTextColor(int textColor) {
            customTitle.textColor = textColor;
            return this;
        }
        public Builder setIcon(Drawable icon) {
            customTitle.icon = icon;
            return this;
        }
        public Builder setIconColor(int iconColor) {
            customTitle.iconColor = iconColor;
            return this;
        }
        public Builder setTitle(String title) {
            customTitle.title = title;
            return this;
        }
        public CustomTitle build() {
            customTitle.show();
            return customTitle;
        }
    }
}
