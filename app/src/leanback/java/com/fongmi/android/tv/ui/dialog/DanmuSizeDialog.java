package com.fongmi.android.gongjin.ui.dialog;

import android.view.LayoutInflater;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import com.fongmi.android.gongjin.Setting;
import com.fongmi.android.gongjin.databinding.DialogDanmuSizeBinding;
import com.fongmi.android.gongjin.impl.DanmuSizeCallback;
import com.fongmi.android.gongjin.utils.KeyUtil;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class DanmuSizeDialog {

    private final DialogDanmuSizeBinding binding;
    private final DanmuSizeCallback callback;
    private final AlertDialog dialog;

    public static DanmuSizeDialog create(FragmentActivity activity) {
        return new DanmuSizeDialog(activity);
    }

    public DanmuSizeDialog(FragmentActivity activity) {
        this.callback = (DanmuSizeCallback) activity;
        this.binding = DialogDanmuSizeBinding.inflate(LayoutInflater.from(activity));
        this.dialog = new MaterialAlertDialogBuilder(activity).setView(binding.getRoot()).create();
    }

    public void show() {
        initDialog();
        initView();
        initEvent();
    }

    private void initDialog() {
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
    }

    private void initView() {
        binding.slider.setValue(Setting.getDanmuSize());
    }

    private void initEvent() {
        binding.slider.addOnChangeListener((slider, value, fromUser) -> callback.setDanmuSize((float) (Math.round(value * 100.0) / 100.0)));
        binding.slider.setOnKeyListener((view, keyCode, event) -> {
            boolean enter = KeyUtil.isEnterKey(event);
            if (enter) dialog.dismiss();
            return enter;
        });
    }
}
