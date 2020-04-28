package com.tongyangsheng.pangolin;

import android.content.Context;
import android.view.View;

import java.util.Map;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.platform.PlatformViewFactory;

public class NativeTextViewFactory extends PlatformViewFactory {
    private final BinaryMessenger messenger;

    public NativeTextViewFactory(BinaryMessenger messenger, View containerView){
        super(StandardMessageCodec.INSTANCE);
        this.messenger = messenger;

    }

    @SuppressWarnings("unchecked")
    @Override
    public PlatformView create(Context context, int id, Object args) {
        Map<String, Object> params = (Map<String, Object>) args;
        return new NativeTextView(context, messenger, id, params);
    }
}
