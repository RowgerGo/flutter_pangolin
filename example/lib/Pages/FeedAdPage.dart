import 'package:flutter/material.dart';
import 'dart:async';
import 'package:pangolin/pangolin.dart' show WebController,FeedAdView;
class FeedAdPage extends StatefulWidget {
  @override
  _FeedAdPageState createState() => _FeedAdPageState();
}

class _FeedAdPageState extends State<FeedAdPage> {
  WebController webController;

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    FeedAdView feedAdView=FeedAdView(onWebCreated: onWebCreated);

    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('PlatformView example'),
        ),
        body: Container(
          child: feedAdView,
          height: 300.0,
        ),
      ),
    );
  }

  void onWebCreated(webController){
    this.webController = webController;
    this.webController.loadUrl("http://www.baidu.com");
  }
}
