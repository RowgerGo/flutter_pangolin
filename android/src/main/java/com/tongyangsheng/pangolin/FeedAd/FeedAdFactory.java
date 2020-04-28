package com.tongyangsheng.pangolin.FeedAd;
import android.content.Context;
import android.view.View;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.platform.PlatformViewFactory;
import java.util.Map;

public class FeedAdFactory extends PlatformViewFactory {
    private final BinaryMessenger messenger;

    public FeedAdFactory(BinaryMessenger messenger, View containerView){
        super(StandardMessageCodec.INSTANCE);
        this.messenger = messenger;

    }

    @SuppressWarnings("unchecked")
    @Override
    public PlatformView create(Context context, int id, Object args) {
        Map<String, Object> params = (Map<String, Object>) args;
        return new FeedAdView(context, messenger, id, params);
    }
}
