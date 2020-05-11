import 'dart:async';
import 'package:flutter/services.dart';
import 'package:flutter/material.dart';
import 'package:flutter/foundation.dart';
import 'package:pangolin/FeedAd/NativeAdEventDelegate.dart';
//View创建完成
typedef void ViewCreatedCallback(ViewController controller);


//View上层组件
class NativeFeedAdView extends StatefulWidget {
  final ViewCreatedCallback onViewCreated;

  String codeId;
  String time;
  double adWidth;
  double adHeight;
  int imageWidth;
  int imageHeight;
  final Function() onAdClicked;
  final Function() onRenderSuccess;
  final Function(Map<String, dynamic>) onAdFailedToLoad;

  NativeFeedAdView({Key key,
  @required this.codeId,
  @required this.adWidth,
  @required this.adHeight,
  this.time,
  this.onAdClicked,
    this.onAdFailedToLoad,
    this.onRenderSuccess,
  this.imageWidth=320,
  this.imageHeight=160,
  this.onViewCreated});



  @override
  _NativeFeedAdViewState createState() => _NativeFeedAdViewState(
    NativeAdEventDelegate(
      onAdClicked: onAdClicked,
      onAdFailedToLoad: onAdFailedToLoad,
      onRenderSuccess: onRenderSuccess
    )
  );
}

class _NativeFeedAdViewState extends State<NativeFeedAdView> {
  _NativeFeedAdViewState(this.delegate);
  final NativeAdEventDelegate delegate;

  @override
  Widget build(BuildContext context) {
    var params= {
      "codeId": widget.codeId,
      "time":widget.time,
      "adWidth":widget.adWidth,
      "adHeight":widget.adHeight,
      "imageWidth":widget.imageWidth,
      "imageHeight":widget.imageHeight
    };
    if (defaultTargetPlatform == TargetPlatform.android) {
      return AndroidView(
        viewType: 'feedadview',
        creationParams:params,
        onPlatformViewCreated: onPlatformViewCreated,
        creationParamsCodec: const StandardMessageCodec(),
      );
    } else if (defaultTargetPlatform == TargetPlatform.iOS) {
      return UiKitView(
        viewType: 'feedadview',
        creationParams: params,
        onPlatformViewCreated: onPlatformViewCreated,
        creationParamsCodec: const StandardMessageCodec(),
      );
    }

    return Text('$defaultTargetPlatform 不支持此插件');
  }

  //视图创建完成
  Future<void> onPlatformViewCreated(id) async {
    if(widget.onViewCreated == null){
      return;
    }
    //widget.onViewCreated(ViewController.init(id));
    final ViewController controller=ViewController.init(id);
    controller._channel.setMethodCallHandler(delegate.handleMethod);
  }
}

//Web控制类
class ViewController {

  MethodChannel _channel;

  ViewController.init(int id){
    _channel = MethodChannel('nativefeedview_$id');

  }
}
