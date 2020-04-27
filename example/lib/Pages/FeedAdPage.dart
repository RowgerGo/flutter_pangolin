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
    // this.webController.loadUrl("https://mbd.baidu.com/newspage/data/landingshare?context=%7B%22nid%22%3A%22news_9653070956301072326%22%7D&n_type=-1&p_from=-1&pageType=1");
  }
}
