package com.dzy.commemlib.ui.widget.ColorfulAnimView;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.dzy.commemlib.R;


public class AnimProgressDialog extends Dialog {

    private TextView titleView;
    private String title;

    private ColorfulAnimView animView;

    public AnimProgressDialog(Context context) {
        this(context, true, null);
    }

    public AnimProgressDialog(Context context, String title) {
        this(context, true, null, title);
    }

    public AnimProgressDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        this(context, cancelable, cancelListener, "载入中...");
    }

    public AnimProgressDialog(Context context, boolean cancelable, OnCancelListener cancelListener, String title) {
        super(context, cancelable, cancelListener);
        this.title = title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去标题栏

        setContentView(R.layout.dialog_anim_progress);
        titleView = (TextView) findViewById(R.id.tv_title);
        if (!title.isEmpty()) {
            titleView.setText(title);
        }
        animView = (ColorfulAnimView) findViewById(R.id.view_anim);
    }

    @Override
    protected void onStart() {
        super.onStart();
        animView.startAnim();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        animView.stopAnim();
    }
}

