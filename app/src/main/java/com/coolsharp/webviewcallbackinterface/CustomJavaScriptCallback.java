package com.coolsharp.webviewcallbackinterface;

/**
 * 자바 스크립트 콜백 인터페이스
 */
public interface CustomJavaScriptCallback {

    String webViewToApp(int valueA, int valueB);

    String appToWebViewNative();
}
