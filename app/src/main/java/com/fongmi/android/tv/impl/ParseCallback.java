package com.github.tvbox.gongjin.impl;

import java.util.Map;

public interface ParseCallback {

    void onParseSuccess(Map<String, String> headers, String url, String from);

    void onParseError();
}
