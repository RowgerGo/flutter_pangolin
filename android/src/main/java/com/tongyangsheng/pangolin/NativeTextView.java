package com.tongyangsheng.pangolin;

import android.provider.CalendarContract;
import android.view.View;
import android.content.Context;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Map;

import static io.flutter.plugin.common.MethodChannel.MethodCallHandler;


import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.common.PluginRegistry.Registrar;

// 广告sdk
import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdDislike;
import com.bytedance.sdk.openadsdk.TTAdManager;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTFeedAd;
import com.bytedance.sdk.openadsdk.TTNativeExpressAd;

public class NativeTextView implements PlatformView, MethodCallHandler {
    public Context context;
    Registrar registrar;
    private final TextView mTestTextView;
    LinearLayout feedAdView;

    WebView webView;
    TextView textView;
    String url = "";
    MethodChannel channel;
    private TTFeedAd mFeedAData;

    private TTAdNative mTTAdNative;
    private TTAdDislike mTTAdDislike;
    private TTNativeExpressAd mTTAd;

    NativeTextView(
            final Context context,
            BinaryMessenger messenger,
            int id,
            Map<String, Object> params
    ) {
        //创建 TextView
        TextView lTextView = new TextView(context);
        lTextView.setText("Android的原生TextView");
        lTextView.setTextColor(0xFF944913);
        this.mTestTextView = lTextView;
        updateTextDara(params);

    }


    @Override
    public View getView() {
        System.out.print("=============== getView ======================");
        return  mTestTextView;
    }

    @Override
    public void dispose() {

    }
    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
        System.out.println(call.arguments);
    }
    private void updateTextDara(Map<String, Object> params){
        //flutter 传递过来的参数
        if (params!=null&&params.containsKey("text")) {
            String myContent = (String) params.get("text");
            this.mTestTextView.setText(myContent);
        }
        if (params!=null&&params.containsKey("size")) {
            Integer size = (Integer) params.get("size");
            int i = size.intValue();
            float rr = (float)i;
            System.out.println("========================");
            System.out.println(rr);
            this.mTestTextView.setTextSize(rr);
        }
        if (params!=null&&params.containsKey("color")) {
            Long _color=(Long) params.get("color");
            int ii= new Long(_color).intValue();
            this.mTestTextView.setTextColor(ii);
        }
    }
    //创建WebView对象
    private TextView getTextView(Context context,View containerView){
        TextView lTextView = new TextView(context);
        lTextView.setText("Android的原生TextView");
        lTextView.setTextColor(0xFF944913);
        return lTextView;
    }




}
