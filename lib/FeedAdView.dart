import 'dart:async';
import 'package:flutter/services.dart';
import 'package:flutter/material.dart';
import 'package:flutter/foundation.dart';

//View创建完成回调
typedef void WebViewCreatedCallback(WebController controller);

//View上层组件
class FeedAdView extends StatefulWidget {

  final WebViewCreatedCallback onWebCreated;

  FeedAdView({
    Key key,
    @required this.onWebCreated,
  });

  @override
  _FeedAdViewState createState() => _FeedAdViewState();

}

class _FeedAdViewState extends State<FeedAdView>{

  @override
  Widget build(BuildContext context) {

    if(defaultTargetPlatform == TargetPlatform.android){
      return AndroidView(
        viewType: 'feedadview',
        onPlatformViewCreated: onPlatformViewCreated,
        creationParamsCodec: const StandardMessageCodec(),
      );
    } else if(defaultTargetPlatform == TargetPlatform.iOS){
      return UiKitView(
        viewType: 'feedadview',
        onPlatformViewCreated: onPlatformViewCreated,
        creationParamsCodec: const StandardMessageCodec(),
      );
    }

    return Text('$defaultTargetPlatform 不支持此插件');

  }

  //视图创建完成
  Future<void> onPlatformViewCreated(id) async{
    if(widget.onWebCreated == null){
      return;
    }
    widget.onWebCreated(WebController.init(id));

  }

}


//Web控制类
class WebController {

  MethodChannel _channel;

  WebController.init(int id){
    _channel = MethodChannel('feedadview_$id');
  }

  //提供加载URL方法
  Future<void> loadUrl(String url) async{
    assert(url != null);
    return _channel.invokeMethod('loadUrl',url);
  }

}
