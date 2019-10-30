package com.coolsharp.webviewcallbackinterface;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 메인 엑티비티 클래스
 */
public class MainActivity extends AppCompatActivity {
    /** 웹뷰 */
    WebView webView;

    /**
     * 엑티비티 생성
     * @param savedInstanceState
     */
    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webView); // 웹뷰 매핑

        webviewInit(); // 웹뷰 초기화

        // 웹뷰 얼럿 활성화
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }
        });

        // 웹뷰 자바 스크립트 인터페이스 추가(인터페이스 명 : WebViewCallbackInterface)
        webView.addJavascriptInterface(new CustomJavaScriptCallback() {
            /**
             * 웹에서 네이티브 메소드 호출
             * @param valueA 인자
             * @param valueB 인자
             * @return 반한값
             */
            @JavascriptInterface
            @Override
            public String webViewToApp(int valueA, int valueB) {
                return "계산 결과 : " + (valueA + valueB);
            }

            /**
             * 웹뷰에서 호출할 수 없는 메소드
             * @return 반환값
             */
            @Override
            public String appToWebViewNative() {
                return "접근불가";
            }
        }, "WebViewCallbackInterface");

        // 웹뷰 로딩
        webView.loadUrl("file:///android_asset/sample.html");

        // 네이티브에서 웹뷰 자바 스크립트 호출
        findViewById(R.id.button).setOnClickListener(
                view -> webView.evaluateJavascript("javascript:executeFunction(\"앱에서 웹뷰 스크립트 호출\");"
                , value -> Toast.makeText(MainActivity.this, value.replace("\"", ""), Toast.LENGTH_SHORT).show()));
    }

    /**
     * 웹뷰 초기 셋팅
     */
    public void webviewInit() {
        WebSettings settings = webView.getSettings();
        // Javascript 사용하기
        settings.setJavaScriptEnabled(true);
        // WebView 내장 줌 사용여부
        settings.setBuiltInZoomControls(true);
        // 화면에 맞게 WebView 사이즈를 정의
        settings.setLoadWithOverviewMode(true);
        // ViewPort meta tag를 활성화 여부
        settings.setUseWideViewPort(true);
        // 줌 컨트롤 사용 여부
        settings.setDisplayZoomControls(false);
        // 사용자 제스처를 통한 줌 기능 활성화 여부
        settings.setSupportZoom(false);
        // TextEncoding 이름 정의
        settings.setDefaultTextEncodingName("UTF-8");

        // Setting Local Storage
        settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);

        // 캐쉬 사용 방법을 정의
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
    }
}
