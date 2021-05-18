package eu.interopehrate.mr2dsm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URLEncoder;

import eu.interopehrate.mr2dsm.model.AuthRequest;

public class EidasWebViewActivity extends AppCompatActivity {
    public static final String JWT_TOKEN = "JWT_TOKEN";
    private static final String LOGIN_URL = "http://212.101.173.84:8080/login";

    private static final String LOG_TAG = "EidasWebViewActivity";

    private WebView mWebView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);

        mWebView = (WebView) findViewById(R.id.webView1);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new EidasWebViewClient());
        mWebView.setWebChromeClient(new EidasWebCromeClient());

        AuthRequest auth = new AuthRequest();
        loadLoginPage(LOGIN_URL, auth);
    }

    private String generateRequestUrl(String url, AuthRequest auth){
        String request = "";
        try {
            String json = new ObjectMapper().writeValueAsString(auth);
            request = URLEncoder.encode(json, "UTF-8");
        } catch (Exception e) {
            Log.e(LOG_TAG, "Failed to generate request URL: " + e.getMessage());
        }

        String requestUrl = url + "?attr=" + request;
        return requestUrl;
    }

    private void loadLoginPage(String url, AuthRequest auth){
        String requestUrl = generateRequestUrl(url, auth);
        Log.d(LOG_TAG, "request URL: " + requestUrl);
        mWebView.loadUrl(requestUrl);
    }

    private void finishActivity(String jwt){
        Intent intent = new Intent();
        intent.putExtra(JWT_TOKEN, jwt);
        setResult(RESULT_OK, intent);
        finish();
    }


    private class EidasWebViewClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            view.loadUrl("javascript:console.log(document.body.innerText);");
        }
    }

    private class EidasWebCromeClient extends WebChromeClient {
        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            String msg = consoleMessage.message();
            if (JwtUtil.isJWT(msg)){
                finishActivity(msg);
            }
            return true;
        }
    }
}
