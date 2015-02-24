package com.shanghai.nyushuttle;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * Created by Alexandru on 11/5/2014.
 */
public class CustomLoading extends ProgressDialog {
    private AnimationDrawable animation;

    public CustomLoading(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_customdialog);

        ImageView la = (ImageView) findViewById(R.id.animation);
        la.setBackgroundResource(R.drawable.loading_bus_animation);
        animation = (AnimationDrawable) la.getBackground();
    }

    @Override
    public void show() {
        super.show();
        animation.start();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        animation.stop();
    }

    public static ProgressDialog ctor(Context context) {
        CustomLoading dialog = new CustomLoading(context);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        return dialog;
    }


}