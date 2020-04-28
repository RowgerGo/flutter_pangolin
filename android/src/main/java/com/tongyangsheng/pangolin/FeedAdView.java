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

public class FeedAdView implements PlatformView, MethodCallHandler {
    public Context context;
    Registrar registrar;
    View FeedAdView;

    private TTAdNative mTTAdNative;

    FeedAdView(
            final Context context,
            BinaryMessenger messenger,
            int id,
            Map<String, Object> params
            ) {

        //step1:初始化sdk
        TTAdManager ttAdManager = TTAdManagerHolder.get();
        //step2:(可选，强烈建议在合适的时机调用):申请部分权限，如read_phone_state,防止获取不了imei时候，下载类广告没有填充的问题。
        TTAdManagerHolder.get().requestPermissionIfNecessary(context);
        //step3:创建TTAdNative对象,用于调用广告请求接口
        mTTAdNative = ttAdManager.createAdNative(context);
        // codeid暂时先写死，后面传入
        loadAd("945155596");
    }


    @Override
    public View getView() {
        System.out.print("=============== FeedAdView ======================");
        //return  mTestTextView;
        return  FeedAdView;
    }

    @Override
    public void dispose() {

    }
    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
        System.out.println(call.arguments);
    }

    private void loadAd(String codeId){
        //step4:创建feed广告请求类型参数AdSlot,具体参数含义参考文档
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(codeId)
                .setSupportDeepLink(true)
                .setImageAcceptedSize(640, 320)
                .setAdCount(3) //请求广告数量为1到3条
                .build();
        //step5:请求广告，调用feed广告异步请求接口，加载到广告后，拿到广告素材自定义渲染
        mTTAdNative.loadFeedAd(adSlot,new TTAdNative.FeedAdListener(){

            @Override
            public void onError(int i, String s) {
                System.out.println("************** mTTAdNative.loadFeedAd  onError ******************");
                System.out.println(i);
                System.out.println(s);
            }

            @Override
            public void onFeedAdLoad(List<TTFeedAd> list) {
                if (list == null || list.isEmpty()) {
                    System.out.println("****************** on FeedAdLoaded: ad is null! ******************");
                    return;
                }
                for (TTFeedAd ad:list){
                   View ad_view= ad.getAdView();
                   FeedAdView=ad_view;
                }
            }
        });
    }

}
