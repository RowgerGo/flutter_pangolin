import 'package:flutter/services.dart';
class NativeAdEventDelegate {
  const NativeAdEventDelegate({
    this.onAdClicked,
    this.onAdFailedToLoad,
  });


  final Function() onAdClicked;
  final Function(Map<String, dynamic>) onAdFailedToLoad;


  Future<dynamic> handleMethod(MethodCall call) async {
    switch (call.method) {
     case 'onAdClicked':

      case 'nativeAdDidRecordClick':

        onAdClicked();

        break;

      case 'onAdFailedToLoad':

      case 'didFailToReceiveAdWithError':

        onAdFailedToLoad(Map<String, dynamic>.from(call.arguments));

        break;

    }

  }

}