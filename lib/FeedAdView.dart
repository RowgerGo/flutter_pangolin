import 'dart:async';
import 'package:flutter/services.dart';
import 'package:flutter/material.dart';
import 'package:flutter/foundation.dart';



//View上层组件
class FeedAdView extends StatefulWidget {



  FeedAdView({
    Key key,
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
      );
    } else if(defaultTargetPlatform == TargetPlatform.iOS){
      return UiKitView(
        viewType: 'feedadview',
      );
    }

    return Text('$defaultTargetPlatform 不支持此插件');

  }

  //视图创建完成
  Future<void> onPlatformViewCreated(id) async{


  }

}
