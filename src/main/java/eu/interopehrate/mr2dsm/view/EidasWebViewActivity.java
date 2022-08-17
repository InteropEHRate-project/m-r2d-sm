package eu.interopehrate.mr2dsm.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import eu.interopehrate.mr2dsm.util.SecurityUtil;
import eu.interopehrate.mr2dsm.R;
import eu.interopehrate.mr2dsm.model.AuthRequest;

public class EidasWebViewActivity extends AppCompatActivity implements EidasMixin{
    public static final String JWT_TOKEN = "JWT_TOKEN";
    public static final String LOGIN_URL = "LOGIN_URL";
    private static final String LOG_TAG = "EidasWebViewActivity";

    private WebView mWebView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);

        Intent intent = getIntent();
        String loginUrl = intent.getStringExtra(LOGIN_URL);

        mWebView = (WebView) findViewById(R.id.webView1);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new EidasWebViewClient());
        mWebView.setWebChromeClient(new EidasWebCromeClient());

        AuthRequest auth = new AuthRequest();
        loadLoginPage(loginUrl, auth);
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
            if (SecurityUtil.isJWT(msg)){
                finishActivity(msg);
            }
            return true;
        }
    }
}
