package com.tongyangsheng.pangolin.FeedAd;
import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
//import android.widget.FrameLayout.LayoutParams.MATCH_PARENT;
//import android.widget.FrameLayout.LayoutParams.WRAP_CONTENT;
import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdManager;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTNativeExpressAd;
import com.tongyangsheng.pangolin.TTAdManagerHolder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.Result;

import io.flutter.plugin.platform.PlatformView;

import static io.flutter.plugin.common.MethodChannel.MethodCallHandler;

// 广告sdk
/*
* 需要优化的地方：
* 1.加载。
* 2.在dispose中remove掉view
* */

public class FeedAdView implements PlatformView, MethodCallHandler {
    FrameLayout feedAdView;
    View feedView;
    View lastView;
    private final MethodChannel methodChannel;
    private TTAdNative mTTAdNative;
    float adWidth=320;
    float adHeight=100;
    int imageWidth=320;
    int imageHeight=100;

    FeedAdView(
            final Context context,
            BinaryMessenger messenger,
            int id,
            Map<String, Object> params
            ) {
        methodChannel = new MethodChannel(messenger, "nativefeedview_" + id);
        methodChannel.setMethodCallHandler(this);
        //mExpressContainer = (FrameLayout) findViewById(R.id.express_container);
        //step1:初始化sdk
        TTAdManager ttAdManager = TTAdManagerHolder.get();
        //step2:(可选，强烈建议在合适的时机调用):申请部分权限，如read_phone_state,防止获取不了imei时候，下载类广告没有填充的问题。
        TTAdManagerHolder.get().requestPermissionIfNecessary(context);
        //step3:创建TTAdNative对象,用于调用广告请求接口
        mTTAdNative = ttAdManager.createAdNative(context);
        //feedAdView
        feedAdView = new FrameLayout(context);

        feedView=new View(context);

        if (params!=null&&params.containsKey("imageHeight")) {
            int imageHeight = (int) params.get("imageHeight");
            this.imageHeight=imageHeight;
        }
        if (params!=null&&params.containsKey("imageWidth")) {
            int imageWidth = (int) params.get("imageWidth");
            this.imageWidth=imageWidth;
        }

        if (params!=null&&params.containsKey("adWidth")) {
            double adWidth = (double) params.get("adWidth");
            this.adWidth=(float)adWidth;
        }
        if (params!=null&&params.containsKey("adHeight")) {
            double adHeight = (double) params.get("adHeight");
            this.adHeight=(float)adHeight;
        }
        if (params!=null&&params.containsKey("codeId")) {
            String codeId = (String) params.get("codeId");
            loadAd(codeId);
        }else {
            System.out.print("===============codeId不可为空！！！======================");
            System.out.print("===============codeId不可为空！！！======================");
            System.out.print("===============codeId不可为空！！！======================");
        }

    }


    @Override
    public View getView() {
        return  feedAdView;
    }

    @Override
    public void dispose() {
      feedAdView.removeAllViews();
    }
    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
        System.out.println(call.method);
        switch (call.method) {
            case "updateView":
                updateView(call, result);
                break;
            default:
                result.notImplemented();
        }
        System.out.println(call.arguments);
    }
    private void updateView(MethodCall methodCall, Result result) {
        String text = (String) methodCall.arguments;

        System.out.println(text);

        result.success(null);

    }
    private void loadAd(String codeId){
        //step4:创建feed广告请求类型参数AdSlot,具体参数含义参考文档
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(codeId)
                .setSupportDeepLink(true)
                .setImageAcceptedSize(imageWidth, imageHeight)
                .setExpressViewAcceptedSize(adWidth,adHeight)
                .setAdCount(3) //请求广告数量为1到3条
                .build();
        //step5:请求广告，调用feed广告异步请求接口，加载到广告后，拿到广告素材自定义渲染
        mTTAdNative.loadNativeExpressAd(adSlot,new TTAdNative.NativeExpressAdListener(){

            @Override
            public void onError(int i, String s) {
                System.out.println("****************** loadNativeExpressAd   onError ******************");
                System.out.println(i);
                System.out.println(s);
            }
            @Override
            public void onNativeExpressAdLoad(List<TTNativeExpressAd> list) {
                if (list == null || list.isEmpty()) {
                    System.out.println("****************** on onNativeExpressAdLoad: ad is null! ******************");
                    return;
                }
                System.out.println("****************** 获取到 ad列表, ******************长度为："+list.size());
                TTNativeExpressAd ad=list.get(0);
                bindAdListener(ad);
                View ad_view=ad.getExpressAdView();
                feedAdView.addView(ad_view);
                feedView=ad_view;
                lastView=ad_view;
                ad.render();


            }
        });

    }
    void bindAdListener(TTNativeExpressAd ad){
      ad.setExpressInteractionListener(new TTNativeExpressAd.ExpressAdInteractionListener() {
          @Override
          public void onAdClicked(View view, int i) {
              methodChannel.invokeMethod("onAdClicked", null);
          }

          @Override
          public void onAdShow(View view, int i) {

          }

          @Override
          public void onRenderFail(View view, String s, int i) {
              System.out.println("******************  bindAdListener onRenderFail ******************");
              Map<String, String> map = new HashMap<String,String>();
              map.put("msg", s);
              map.put("code", String.valueOf(i));
              methodChannel.invokeMethod("onAdFailedToLoad",map);
          }

          @Override
          public void onRenderSuccess(View view, float v, float v1) {
              methodChannel.invokeMethod("onRenderSuccess", null);
          }
      });
    }

}

