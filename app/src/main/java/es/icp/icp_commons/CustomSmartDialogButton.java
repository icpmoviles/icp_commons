package es.icp.icp_commons;

import android.view.View;

public class CustomSmartDialogButton {

    private String text;
    private int style;
    private View.OnClickListener onClickListener;

    public CustomSmartDialogButton() {
    }

    public CustomSmartDialogButton(String text, int style, View.OnClickListener onClickListener) {
        this.text = text;
        this.style = style;
        this.onClickListener = onClickListener;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public String toString() {
        return "CustomSmartDialogButton{" +
                "text='" + text + '\'' +
                ", style=" + style +
                ", onClickListener=" + onClickListener +
                '}';
    }
}
