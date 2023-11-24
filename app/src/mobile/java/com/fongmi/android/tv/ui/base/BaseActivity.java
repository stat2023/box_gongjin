package com.github.tvbox.gongjin.ui.base;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.DisplayCutout;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import com.github.tvbox.gongjin.R;
import com.github.tvbox.gongjin.Setting;
import com.github.tvbox.gongjin.api.WallConfig;
import com.github.tvbox.gongjin.event.RefreshEvent;
import com.github.tvbox.gongjin.utils.FileUtil;
import com.github.tvbox.gongjin.utils.ResUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

public abstract class BaseActivity extends AppCompatActivity {

    protected abstract ViewBinding getBinding();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (transparent()) setTransparent(this);
        setContentView(getBinding().getRoot());
        EventBus.getDefault().register(this);
        setWall();
        initView(savedInstanceState);
        initEvent();
    }

    protected Activity getActivity() {
        return this;
    }

    protected boolean transparent() {
        return true;
    }

    protected boolean customWall() {
        return true;
    }

    protected void initView(Bundle savedInstanceState) {
    }

    protected void initEvent() {
    }

    protected boolean isVisible(View view) {
        return view.getVisibility() == View.VISIBLE;
    }

    protected boolean isGone(View view) {
        return view.getVisibility() == View.GONE;
    }

    protected void setPadding(ViewGroup layout) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) return;
        DisplayCutout cutout = ResUtil.getDisplay(this).getCutout();
        if (cutout == null) return;
        int top = cutout.getSafeInsetTop();
        int left = cutout.getSafeInsetLeft();
        int right = cutout.getSafeInsetRight();
        int bottom = cutout.getSafeInsetBottom();
        int padding = left | right | top | bottom;
        layout.setPadding(padding, 0, padding, 0);
    }

    protected void noPadding(ViewGroup layout) {
        layout.setPadding(0, 0, 0, 0);
    }

    private void setWall() {
        try {
            if (!customWall()) return;
            File file = FileUtil.getWall(Setting.getWall());
            if (file.exists() && file.length() > 0) getWindow().setBackgroundDrawable(WallConfig.drawable(Drawable.createFromPath(file.getAbsolutePath())));
            else getWindow().setBackgroundDrawableResource(ResUtil.getDrawable(file.getName()));
        } catch (Exception e) {
            getWindow().setBackgroundDrawableResource(R.drawable.wallpaper_1);
        }
    }

    private void setTransparent(Activity activity) {
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshEvent(RefreshEvent event) {
        if (event.getType() != RefreshEvent.Type.WALL) return;
        WallConfig.get().setDrawable(null);
        setWall();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
