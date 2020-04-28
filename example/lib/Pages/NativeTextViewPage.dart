import 'package:flutter/material.dart';
import 'dart:async';
import 'package:pangolin/pangolin.dart'
    show ViewController, NativeFeedAdView, NativeTextView;

class NativeTextViewPage extends StatefulWidget {
  @override
  _NativeTextViewPageState createState() => _NativeTextViewPageState();
}

class _NativeTextViewPageState extends State<NativeTextViewPage> {
  ViewController _controller;
  String _text = "垃圾代码";

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('PlatformView example'),
        ),
        body: Container(
          child: Column(
            children: <Widget>[
              FutureBuilder(
                future: Future.delayed(Duration(microseconds: 2000)),
                builder: (c, s) => Expanded(
                  child: NativeFeedAdView(
                    onViewCreated: _onViewCreated,
                    text: _text,
                    color: 0xFFf2173b,
                    size: 150540,
                  ),
                ),
              ),
              GestureDetector(
                  onTap: () {
                    _controller.updateView('545445');
                  },
                  child: Text(_text))
            ],
          ),
          height: double.infinity,
          color: Colors.black26,
        ),
      ),
    );
  }

  void _onViewCreated(ViewController controller) {
    _controller = controller;
    Timer.periodic(Duration(milliseconds: 1000), (timer) {
      var now = new DateTime.now();
      this.setState(() {
        _text = now.second.toString();
      });
      // 每隔 1 秒钟会调用一次，如果要结束187468
      //timer.cancel()
    });
    var now = new DateTime.now();
    controller.updateView(now.second.toString());
  }
}
