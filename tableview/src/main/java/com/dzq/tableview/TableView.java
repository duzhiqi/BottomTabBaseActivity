package com.dzq.tableview;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by dzq on 2017/5/15.
 * Description:
 */

public class TableView extends LinearLayout {

    private List<TableItemView> tableItemViews;
    private int currentIndex = 0;

    public TableView(Context context) {
        this(context, null);
    }

    public TableView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setTableItemViews(List<TableItemView> views){
        this.setTableItemViews(views, null);
    }

    public void setTableItemViews(List<TableItemView> views, final View centerView){

        if (views == null) throw new NullPointerException("passed a null reference of views");

        this.tableItemViews = views;

        for (int i = 0; i < views.size(); i++) {

            if (centerView != null && i == views.size() / 2) {
                this.addView(centerView);
                centerView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateView(-1);
                    }
                });
            }

            final TableItemView tableItemView = tableItemViews.get(i);
            this.addView(tableItemView);

            final int position = i;

            tableItemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateView(position);
                    if (mOnTableViewEventListener != null) {
                        mOnTableViewEventListener.onTableItemViewSelected(v, position);
                    }
                    if (currentIndex == position){
                        //第二次点击
                    }
                }
            });
        }

        updateView(0);

    }

    private void updateView(int position){
        if (position == -1 && currentIndex != -1){
            tableItemViews.get(currentIndex).setStatus(TableItemView.UNSELECTED);
        } else if (position >= 0 && position < tableItemViews.size()){
            if (currentIndex != -1) {
                tableItemViews.get(currentIndex).setStatus(TableItemView.UNSELECTED);
            }
            tableItemViews.get(position).setStatus(TableItemView.SELECTED);
        }
        currentIndex = position;
    }

    public void setOnTableViewEventListener(OnTableViewEventListener listener){
        mOnTableViewEventListener = listener;
    }
    private OnTableViewEventListener mOnTableViewEventListener;
    public interface OnTableViewEventListener{
        void onTableItemViewSelected(View view, int position);
    }


    public static class TableItemView extends RelativeLayout{

        @IntDef({SELECTED, UNSELECTED})
        public @interface statusValue{
        }

        public static final int SELECTED = 0x0001;
        public static final int UNSELECTED = SELECTED - 1;

        public String title;
        @ColorInt public int titleColorNormal;
        @ColorInt public int titleColorSelected;

        @DrawableRes public int iconNormal;
        @DrawableRes public int iconSelected;

        public TextView titleTV;
        public ImageView iconIV;


        public TableItemView(Context context, int iconSelected, int iconNormal, int titleColorSelected, int titleColorNormal, String title) {
            super(context);

            this.iconSelected = iconSelected;
            this.iconNormal = iconNormal;
            this.titleColorSelected = titleColorSelected;
            this.titleColorNormal = titleColorNormal;
            this.title = title;
            init();
        }


        private void init(){
            View view = LayoutInflater.from(super.getContext()).inflate(R.layout.table_item_view, this);
            iconIV = (ImageView) view.findViewById(R.id.icon_iv);
            titleTV = (TextView) view.findViewById(R.id.title_tv);
            titleTV.setText(title);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.weight = 1;
            view.setLayoutParams(layoutParams);
        }

        public void setStatus(@statusValue int status){
            boolean isSelected = status == SELECTED;
            titleTV.setTextColor(isSelected ? titleColorSelected : titleColorNormal);
            iconIV.setImageResource(isSelected ? iconSelected : iconNormal);
        }
    }
}
