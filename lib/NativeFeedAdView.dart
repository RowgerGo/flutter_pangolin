import 'dart:async';
import 'package:flutter/services.dart';
import 'package:flutter/material.dart';
import 'package:flutter/foundation.dart';

//View创建完成
typedef void ViewCreatedCallback(ViewController controller);


//View上层组件
class NativeFeedAdView extends StatefulWidget {
  final ViewCreatedCallback onViewCreated;
  String text;
  int color;
  int size;
  NativeFeedAdView({Key key, @required this.text,this.color,this.size,this.onViewCreated});

  @override
  _NativeFeedAdViewState createState() => _NativeFeedAdViewState();
}

class _NativeFeedAdViewState extends State<NativeFeedAdView> {
  @override
  Widget build(BuildContext context) {
    var params= {
      "text": widget.text,
      "color":widget.color,
      "size":widget.size
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
    widget.onViewCreated(ViewController.init(id));

  }
}

//Web控制类
class ViewController {

  MethodChannel _channel;

  ViewController.init(int id){
    _channel = MethodChannel('nativefeedview_$id');
  }

  Future<void> updateView(String url) async{
    assert(url != null);
    return _channel.invokeMethod('updateView',url);
  }
}
