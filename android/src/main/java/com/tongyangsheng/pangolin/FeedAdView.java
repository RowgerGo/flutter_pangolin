package com.tongyangsheng.pangolin;

import android.hardware.display.DisplayManager;
import android.view.View;
import android.annotation.SuppressLint;
import android.content.Context;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import java.util.Map;

import static io.flutter.plugin.common.MethodChannel.MethodCallHandler;

import androidx.annotation.NonNull;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.common.PluginRegistry.Registrar;

public class FeedAdView implements PlatformView, MethodCallHandler {
    Context context;
    Registrar registrar;
    WebView webView;
    TextView textView;
    String url = "";
    MethodChannel channel;

    @SuppressWarnings("unchecked")
    FeedAdView(
            final Context context,
            BinaryMessenger messenger,
            int id,
            Map<String, Object> params,
            View containerView) {

        webView=getWebView(context, containerView);

        // Allow local storage.
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        channel = new MethodChannel(messenger, "feedadview_" + id);
        channel.setMethodCallHandler(this);

    }

    @Override
    public void onMethodCall(MethodCall call,MethodChannel.Result result){
        switch (call.method){
            case "loadUrl":
                System.out.print("=============== loadUrl ======================");
                String url = call.arguments.toString();
                webView.loadUrl(url);
                break;
            default:
                result.notImplemented();
        }
    }

    @Override
    public View getView() {
        return  webView;
    }

    @Override
    public void dispose() {

    }
    //创建WebView对象
    private WebView getWebView(Context context,View containerView){
        WebView webView = new WebView(context);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        return webView;
    }


}
