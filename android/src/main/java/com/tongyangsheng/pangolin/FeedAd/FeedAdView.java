package com.tongyangsheng.pangolin.FeedAd;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdManager;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTFeedAd;
import com.bytedance.sdk.openadsdk.TTImage;
import com.bytedance.sdk.openadsdk.TTNativeAd;
import com.bytedance.sdk.openadsdk.TTNativeExpressAd;
import com.tongyangsheng.pangolin.R;
import com.tongyangsheng.pangolin.TTAdManagerHolder;
import com.tongyangsheng.pangolin.TToast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;
import io.flutter.plugin.platform.PlatformView;

import static io.flutter.plugin.common.MethodChannel.MethodCallHandler;

// 广告sdk

interface Callback {
    public void getView(View _view);

}

public class FeedAdView implements PlatformView, MethodCallHandler {
    private FrameLayout mExpressContainer;
    public Context context;
    Registrar registrar;
    View feedView;
    private final MethodChannel methodChannel;
    private TTAdNative mTTAdNative;

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
        // codeid暂时先写死，后面传入
        feedView=new View(context);
        loadAd("945155596");
    }


    @Override
    public View getView() {
        System.out.print("=============== FeedAdView ======================");
        //return  mTestTextView;
        return  feedView;
    }

    @Override
    public void dispose() {

    }
    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
        System.out.println("===============================");
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
        System.out.println("********************************");
        String text = (String) methodCall.arguments;
        System.out.println("===============================");
        System.out.println(text);

        result.success(null);

    }
    private void loadAd(String codeId){
        //step4:创建feed广告请求类型参数AdSlot,具体参数含义参考文档
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(codeId)
                .setSupportDeepLink(true)
                .setImageAcceptedSize(320, 160)
                .setExpressViewAcceptedSize(700,400)
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

                View ad_view=ad.getExpressAdView();

                feedView=ad_view;

                ad.render();


            }
        });

    }

//    //绑定广告行为
//    private void bindAdListener(TTNativeExpressAd ad) {
//        ad.setExpressInteractionListener(new TTNativeExpressAd.ExpressAdInteractionListener() {
//            @Override
//            public void onAdClicked(View view, int type) {
//                TToast.show(context, "广告被点击");
//            }
//
//            @Override
//            public void onAdShow(View view, int type) {
//                TToast.show(context, "广告展示");
//            }
//
//            @Override
//            public void onRenderFail(View view, String msg, int code) {
//                Log.e("ExpressView","render fail:"+(System.currentTimeMillis()));
//                TToast.show(context, msg+" code:"+code);
//            }
//
//            @Override
//            public void onRenderSuccess(View view, float width, float height) {
//                //返回view的宽高 单位 dp
//                TToast.show(context, "渲染成功");
//                //在渲染成功回调时展示广告，提升体验
//                mExpressContainer.removeAllViews();
//                mExpressContainer.addView(view);
//            }
//        });
//        //dislike设置
//        bindDislike(ad, false);
//        if (ad.getInteractionType() != TTAdConstant.INTERACTION_TYPE_DOWNLOAD){
//            return;
//        }
//        //可选，下载监听设置
//        ad.setDownloadListener(new TTAppDownloadListener() {
//            @Override
//            public void onIdle() {
//                TToast.show(context, "点击开始下载", Toast.LENGTH_LONG);
//            }
//
//            @Override
//            public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
//                if (!mHasShowDownloadActive) {
//                    mHasShowDownloadActive = true;
//                    TToast.show(context, "下载中，点击暂停", Toast.LENGTH_LONG);
//                }
//            }
//
//            @Override
//            public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
//                TToast.show(context, "下载暂停，点击继续", Toast.LENGTH_LONG);
//            }
//
//            @Override
//            public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
//                TToast.show(context, "下载失败，点击重新下载", Toast.LENGTH_LONG);
//            }
//
//            @Override
//            public void onInstalled(String fileName, String appName) {
//                TToast.show(context, "安装完成，点击图片打开", Toast.LENGTH_LONG);
//            }
//
//            @Override
//            public void onDownloadFinished(long totalBytes, String fileName, String appName) {
//                TToast.show(context, "点击安装", Toast.LENGTH_LONG);
//            }
//        });
//    }
//


}

