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
import java.security.spec.InvalidKeySpecException;

import eu.interopehrate.mr2dsm.R;
import eu.interopehrate.mr2dsm.model.AuthRequest;
import eu.interopehrate.mr2dsm.util.FileUtil;
import eu.interopehrate.mr2dsm.util.SecurityUtil;
import io.jsonwebtoken.Claims;

import static eu.interopehrate.mr2dsm.util.SecurityUtil.storeKeystore;

public class CertificationWebViewActivity extends AppCompatActivity implements EidasMixin {
    public static final String URL = "URL";

    private WebView mWebView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);

        Intent intent = getIntent();
        String loginUrl = intent.getStringExtra(URL);

        mWebView = (WebView) findViewById(R.id.webView1);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new CertificationWebViewActivity.EidasWebViewClient());
        mWebView.setWebChromeClient(new CertificationWebViewActivity.EidasWebCromeClient());

        AuthRequest auth = new AuthRequest();
        loadPage(loginUrl, auth);
    }

    private void loadPage(String url, AuthRequest auth){
        String requestUrl = generateRequestUrl(url, auth);
        Log.d("loadPage", "request URL: " + requestUrl);
        mWebView.loadUrl(requestUrl);
    }

    private void finishActivity(String jwt){
        Intent intent = new Intent();
        try {
            String keystore = decode(jwt);
            storeKeystore(this, keystore);
            setResult(RESULT_OK, intent);
        } catch (IOException | InvalidKeySpecException e) {
            setResult(RESULT_CANCELED, intent);
            Log.e("storeKeystore", "Failed to decode and store keystore: " + e.getMessage());
        }
        finish();
    }

    private String decode(String jwt) throws InvalidKeySpecException {
        String key = "";
        try {
            key = FileUtil.LoadData(this, "private_ca.pub");
        } catch (IOException e) {
            Log.e("Decode", "Failed to load ca private.pub :" + e.getMessage());
        }
        Log.d("key", key);

        Claims jwtClaims = SecurityUtil.decode(jwt, key);
        String keystore = jwtClaims.get("UserKeystore").toString();
        Log.d("keystore", keystore);
        return keystore;
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
            Log.d("onConsoleMessage",msg);
            if(SecurityUtil.isJWT(msg)){
                finishActivity(msg);
            }
            return true;
        }
    }
}

