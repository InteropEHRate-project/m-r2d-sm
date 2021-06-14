package eu.interopehrate.mr2dsm.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import eu.interopehrate.mr2dsm.R;
import eu.interopehrate.mr2dsm.model.AuthRequest;
import eu.interopehrate.mr2dsm.util.SecurityUtil;

import static eu.interopehrate.mr2dsm.util.SecurityUtil.storeKeystore;

public class EidasRegistrationWebViewActivity extends AppCompatActivity implements EidasMixin {
    public static final String URL = "URL";

    private WebView mWebView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);

        Intent intent = getIntent();
        String loginUrl = intent.getStringExtra(URL);

        mWebView = (WebView) findViewById(R.id.webView1);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new EidasRegistrationWebViewActivity.EidasWebViewClient());
        mWebView.setWebChromeClient(new EidasRegistrationWebViewActivity.EidasWebCromeClient());

        AuthRequest auth = new AuthRequest();
        loadPage(loginUrl, auth);
    }

    private void loadPage(String url, AuthRequest auth){
        String requestUrl = generateRequestUrl(url, auth);
        Log.d("loadPage", "request URL: " + requestUrl);
        mWebView.loadUrl(requestUrl);
    }

    private void finishActivity(String keystore){
        Intent intent = new Intent();
        try {
            storeKeystore(this, keystore);
            setResult(RESULT_OK, intent);
        } catch (IOException e) {
            setResult(RESULT_CANCELED, intent);
            Log.e("storeKeystore", "Failed to store keystore: " + e.getMessage());
        }
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
            //TODO: what about password???
            if(SecurityUtil.isKeystore(msg, "menelaos")){
                finishActivity(msg);
            }
            return true;
        }
    }
}

