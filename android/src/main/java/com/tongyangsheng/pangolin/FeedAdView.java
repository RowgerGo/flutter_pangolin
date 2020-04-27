package com.tongyangsheng.pangolin;

import android.view.View;
import android.content.Context;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

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

public class FeedAdView implements PlatformView, MethodCallHandler {
    public Context context;
    Registrar registrar;
    TextView mTestTextView;
    LinearLayout feedAdView;



    WebView webView;
    TextView textView;
    String url = "";
    MethodChannel channel;
    private TTFeedAd mFeedAData;

    private TTAdNative mTTAdNative;
    private TTAdDislike mTTAdDislike;
    private TTNativeExpressAd mTTAd;

    FeedAdView(
            final Context context,
            BinaryMessenger messenger,
            int id,
            Map<String, Object> params,
            View containerView) {
        System.out.print("=============FeedAdView begin============");
//        //step1:初始化sdk
//        TTAdManager ttAdManager = TTAdManagerHolder.get();
//        //step2:(可选，强烈建议在合适的时机调用):申请部分权限，如read_phone_state,防止获取不了imei时候，下载类广告没有填充的问题。
//        TTAdManagerHolder.get().requestPermissionIfNecessary(context);
//        //step3:创建TTAdNative对象,用于调用广告请求接口
//        mTTAdNative = ttAdManager.createAdNative(context);
//        System.out.print("=============FeedAdView feedAd============");
//        feedAd("5365685",100,100);

        TextView lTextView = new TextView(context);
        lTextView.setText("Android的原生TextView");
        this.mTestTextView = lTextView;


        channel = new MethodChannel(messenger, "feedadview_" + id);
        channel.setMethodCallHandler(this);


    }

    @Override
    public void onMethodCall(MethodCall call,MethodChannel.Result result){
        switch (call.method){
            case "loadUrl":
                System.out.print("=============== loadUrl ======================");
                break;
            default:
                result.notImplemented();
        }
    }

    @Override
    public View getView() {
        System.out.print("=============== getView ======================");
        return  mTestTextView;
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
    /**
     * 加载feed广告
     */
    private void feedAd(final String codeId, int SizeW, int SizeH){
        System.out.print("=============mTTAdNative.loadFeedAd  feedAd============");
        //step4:创建feed广告请求类型参数AdSlot,具体参数含义参考文档
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(codeId)
                .setSupportDeepLink(true)
                .setImageAcceptedSize(SizeW,SizeH)
                .setAdCount(3) //请求广告数量为1到3条
                .build();
        mTTAdNative.loadFeedAd(adSlot, new TTAdNative.FeedAdListener() {
            @Override
            public void onError(int code, String message) {
              System.out.print("=============mTTAdNative.loadFeedAd onError============");
              System.out.print(code);
              System.out.print(message);
            }

            @Override
            public void onFeedAdLoad(List<TTFeedAd> list) {
                if (list==null||list.size()==0){
                    return;
                }
                mFeedAData=list.get(0);
                System.out.println(mFeedAData);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                feedAdView.addView(mFeedAData.getAdView(), layoutParams);

            }


        });

    }

}
