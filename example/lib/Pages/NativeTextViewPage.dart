import 'package:flutter/material.dart';
import 'dart:async';
import 'package:pangolin/pangolin.dart' show NativeTextView;

class NativeTextViewPage extends StatefulWidget {
  @override
  _NativeTextViewPageState createState() => _NativeTextViewPageState();
}

class _NativeTextViewPageState extends State<NativeTextViewPage> {
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
              Container(
                height: 100,
                child: NativeTextView(
                  text: "我是原生视图1",
                  color: 0xFFf2173b,
                  size: 30,
                ),
              ),
              Container(
                height: 100,
                child: NativeTextView(
                  text: "我是原生视图2",
                  color: 0xFFf2173b,
                  size: 30,
                ),
              )
            ],
          ),
          height: double.infinity,
          color: Colors.black26,
        ),
      ),
    );
  }
}
