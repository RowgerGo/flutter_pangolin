import 'package:flutter/material.dart';
import 'dart:async';
import 'package:pangolin/pangolin.dart'
    show ViewController, NativeFeedAdView, NativeTextView;

class NativeTextViewPage extends StatefulWidget {
  @override
  _NativeTextViewPageState createState() => _NativeTextViewPageState();
}

class _NativeTextViewPageState extends State<NativeTextViewPage> {
  Timer _timer;
  ViewController _controller;
  String _text = "垃圾代码";


  @override
  void initState() {
    super.initState();
  }

  @override
  void dispose() {
    _timer.cancel();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    // print(this._text);
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('PlatformView example'),
        ),
        body: Container(
          child: Column(
            children: <Widget>[
              Expanded(
                child: NativeFeedAdView(
                  onViewCreated: _onViewCreated,
                  onAdClicked:(){
                     print("==========================广告被点击111");
                     print("==========================广告被点击222");
                  } ,
                  onRenderSuccess: (){

                  },
                  onAdFailedToLoad:(Map<String, dynamic> error){
                    print("==========================广告加载失败");
                     print("onAdFailedToLoad!!! $error");
                  },
                  codeId: '945155596',
                  adWidth: 500,
                  adHeight: 250,
                  time: _text,
                ),
              ),
              GestureDetector(
                  onTap: () {

                  },
                  child: Text(_text))
            ],
          ),
        ),
      ),
    );
  }

  void _onViewCreated(ViewController controller) {
    // 垃圾代码，有bug，需要在页面上放一个字符串，通过在UI创建完成之后，使用setState触发页面rebuild，从而使原生View在flutter端渲染出来
    _controller = controller;

  }
}
