package es.icp.icp_commons;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;

import com.google.android.material.textfield.TextInputLayout;

@SuppressLint("AppCompatCustomView")
public class CustomEditText extends LinearLayout {
    private Context context;
    private String hintText;
    private Drawable startIconDrawable;
    private int startIconColor;
    private int errorIconColor;
    private int counterMaxLength;
    private int counterOverflowTextAppearance;
    private int textAppearance;

    private LinearLayout mainContainer;
    private TextInputLayout txtInputLayout;
    private EditText txtEditText;

    public CustomEditText(Context context) {
        this(context, null);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
    }

    public void show() {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mainContainer = (LinearLayout) inflater.inflate(R.layout.view_custom_edittext, this, true);
        mainContainer.setVisibility(View.VISIBLE);

        final ImageView startIcon = mainContainer.findViewById(R.id.startIcon);
        txtInputLayout = mainContainer.findViewById(R.id.txtInputLayout);
        txtEditText = mainContainer.findViewById(R.id.txtEditText);

        if (startIconDrawable != null) {
            startIcon.setImageDrawable(startIconDrawable);
            if (startIconColor != 0) ImageViewCompat.setImageTintList(startIcon, ColorStateList.valueOf(ContextCompat.getColor(context, startIconColor)));
        }

        if (counterMaxLength != 0) {
            txtInputLayout.setCounterEnabled(true);
            txtInputLayout.setCounterMaxLength(counterMaxLength);
            if (counterOverflowTextAppearance != 0) {
                txtInputLayout.setErrorTextAppearance(counterOverflowTextAppearance);
            }
            if (errorIconColor != 0) {
                txtEditText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (s.toString().length() > counterMaxLength) {
                            ImageViewCompat.setImageTintList(startIcon, ColorStateList.valueOf(ContextCompat.getColor(context, errorIconColor)));
                        } else {
                            ImageViewCompat.setImageTintList(startIcon, ColorStateList.valueOf(ContextCompat.getColor(context, startIconColor)));
                        }
                    }
                });
            }
        }
        if (hintText != null && !hintText.isEmpty()) {
            txtInputLayout.setHint(hintText);
        }
        if (textAppearance != 0) {
            txtInputLayout.setHintTextAppearance(textAppearance);
        }
    }

    public String getText() {
        return txtEditText.getText().toString();
    }

    public static class Builder {
        private CustomEditText customEditText;
        public Builder(Context context) {
            customEditText = new CustomEditText(context);
        }
        public Builder setHintText(String hintText) {
            customEditText.hintText = hintText;
            return this;
        }
        public Builder setStartIconDrawable(Drawable startIconDrawable) {
            customEditText.startIconDrawable = startIconDrawable;
            return this;
        }
        public Builder setStartIconColor(int startIconColor) {
            customEditText.startIconColor = startIconColor;
            return this;
        }
        public Builder setErrorIconColor(int errorIconColor) {
            customEditText.errorIconColor = errorIconColor;
            return this;
        }
        public Builder setCounterMaxLength(int counterMaxLength) {
            customEditText.counterMaxLength = counterMaxLength;
            return  this;
        }
        public Builder setCounterOverflowAppearance(int counterOverflowAppearance) {
            customEditText.counterOverflowTextAppearance = counterOverflowAppearance;
            return this;
        }
        public Builder setTextAppearance(int textAppearance) {
            customEditText.textAppearance = textAppearance;
            return this;
        }
        public CustomEditText build() {
            customEditText.show();
            return customEditText;
        }
    }
}
