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
          child: AndroidView(
            //设置标识
            viewType: "feedadview",
          ),
          color: Colors.black26,
          height: 300.0,
        ),
      ),
    );
  }
}
