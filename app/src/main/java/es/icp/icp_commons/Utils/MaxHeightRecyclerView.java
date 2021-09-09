package es.icp.icp_commons.Utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import es.icp.icp_commons.R;

public class MaxHeightRecyclerView extends RecyclerView {

    private       int maxHeight;
    private final int defaultHeight = 500;

    public MaxHeightRecyclerView(Context context) {
        super(context);
    }

    public MaxHeightRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            init(context, attrs);
        }
    }

    public MaxHeightRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode()) {
            init(context, attrs);
        }
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.MaxHeightRecyclerView);
            //500 is a defualt value
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            maxHeight = styledAttrs.getDimensionPixelSize(R.styleable.MaxHeightRecyclerView_maxRecyclerHeight, defaultHeight);
            styledAttrs.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(maxHeight, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}