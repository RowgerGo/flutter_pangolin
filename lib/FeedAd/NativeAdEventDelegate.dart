import 'package:flutter/services.dart';
class NativeAdEventDelegate {
  const NativeAdEventDelegate({
    this.onAdClicked,
    this.onAdFailedToLoad,
    this.onRenderSuccess,
  });


  final Function() onAdClicked;
  final Function() onRenderSuccess;
  final Function(Map<String, dynamic>) onAdFailedToLoad;


  Future<dynamic> handleMethod(MethodCall call) async {
    switch (call.method) {
      case 'onRenderSuccess':
        onRenderSuccess();
        break;
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