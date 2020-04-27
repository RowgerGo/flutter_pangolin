import 'package:flutter/material.dart';
import 'dart:async';
import 'package:pangolin/pangolin.dart' show FeedAdView;
class FeedAdPage extends StatefulWidget {
  @override
  _FeedAdPageState createState() => _FeedAdPageState();
}

class _FeedAdPageState extends State<FeedAdPage> {

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    FeedAdView feedAdView=FeedAdView();

    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('PlatformView example'),
        ),
        body: Container(
          child: FeedAdView(
            text: "我是原生参数",
            color: 0xFFf2173b,
            size: 30,
          ),
          color: Colors.black26,
          height: 300.0,
        ),
      ),
    );
  }
}
