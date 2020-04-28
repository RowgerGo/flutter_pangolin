import 'dart:async';
import 'package:flutter/services.dart';
import 'package:flutter/material.dart';
import 'package:flutter/foundation.dart';

//View上层组件
class NativeTextView extends StatefulWidget {
  String text;
  int color;
  int size;
  NativeTextView({Key key, @required this.text,this.color,this.size});

  @override
  _NativeTextViewState createState() => _NativeTextViewState();
}

class _NativeTextViewState extends State<NativeTextView> {
  @override
  Widget build(BuildContext context) {
    var params= {
      "text": widget.text,
      "color":widget.color,
      "size":widget.size
    };
    if (defaultTargetPlatform == TargetPlatform.android) {
      return AndroidView(
        viewType: 'nativetextview',
        creationParams:params,
        creationParamsCodec: const StandardMessageCodec(),
      );
    } else if (defaultTargetPlatform == TargetPlatform.iOS) {
      return UiKitView(
        viewType: 'nativetextview',
        creationParams: params,
        creationParamsCodec: const StandardMessageCodec(),
      );
    }

    return Text('$defaultTargetPlatform 不支持此插件');
  }

  //视图创建完成
  Future<void> onPlatformViewCreated(id) async {}
}
