package com.github.tvbox.gongjin.ui.holder;

import androidx.annotation.NonNull;

import com.github.tvbox.gongjin.bean.Episode;
import com.github.tvbox.gongjin.databinding.AdapterEpisodeVertBinding;
import com.github.tvbox.gongjin.ui.adapter.EpisodeAdapter;
import com.github.tvbox.gongjin.ui.base.BaseEpisodeHolder;

public class EpisodeVertHolder extends BaseEpisodeHolder {

    private final EpisodeAdapter.OnClickListener listener;
    private final AdapterEpisodeVertBinding binding;

    public EpisodeVertHolder(@NonNull AdapterEpisodeVertBinding binding, EpisodeAdapter.OnClickListener listener) {
        super(binding.getRoot());
        this.binding = binding;
        this.listener = listener;
    }

    @Override
    public void initView(Episode item) {
        binding.text.setSelected(item.isActivated());
        binding.text.setActivated(item.isActivated());
        binding.text.setText(item.getDesc().concat(item.getName()));
        binding.text.setOnClickListener(v -> listener.onItemClick(item));
    }
}
