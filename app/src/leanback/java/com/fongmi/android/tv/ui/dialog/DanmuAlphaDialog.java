package com.fongmi.android.gongjin.ui.dialog;

import android.view.LayoutInflater;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import com.fongmi.android.gongjin.Setting;
import com.fongmi.android.gongjin.databinding.DialogDanmuAlphaBinding;
import com.fongmi.android.gongjin.impl.DanmuAlphaCallback;
import com.fongmi.android.gongjin.utils.KeyUtil;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class DanmuAlphaDialog {

    private final DialogDanmuAlphaBinding binding;
    private final DanmuAlphaCallback callback;
    private final AlertDialog dialog;

    public static DanmuAlphaDialog create(FragmentActivity activity) {
        return new DanmuAlphaDialog(activity);
    }

    public DanmuAlphaDialog(FragmentActivity activity) {
        this.callback = (DanmuAlphaCallback) activity;
        this.binding = DialogDanmuAlphaBinding.inflate(LayoutInflater.from(activity));
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
        binding.slider.setValue(Setting.getDanmuAlpha());
    }

    private void initEvent() {
        binding.slider.addOnChangeListener((slider, value, fromUser) -> callback.setDanmuAlpha((int) value));
        binding.slider.setOnKeyListener((view, keyCode, event) -> {
            boolean enter = KeyUtil.isEnterKey(event);
            if (enter) dialog.dismiss();
            return enter;
        });
    }
}
