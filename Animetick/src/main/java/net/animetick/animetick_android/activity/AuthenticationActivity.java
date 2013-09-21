package net.animetick.animetick_android.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.AndroidRuntimeException;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.HttpAuthHandler;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;

import net.animetick.animetick_android.R;
import net.animetick.animetick_android.config.Config;
import net.animetick.animetick_android.model.Authentication;

public class AuthenticationActivity extends FragmentActivity {

    private Authentication authentication = null;
    private static final String SIGN_IN_URL = "/app/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authentication = new Authentication(this);
        if (authentication.isAuthenticated()) {
            moveToMainActivity();
            finish();
        }
        setContentView(R.layout.sign_in);
        Button signInButton = (Button) findViewById(R.id.sign_in_button);
        final AuthenticationActivity activity = this;
        CookieSyncManager.createInstance(this);
        CookieSyncManager cookieSyncManager = CookieSyncManager.getInstance();
        if (cookieSyncManager != null) {
            cookieSyncManager.startSync();
        }
        CookieManager.getInstance().setAcceptCookie(true);
        CookieManager.getInstance().removeSessionCookie();
        signInButton.setOnClickListener(new OnClickListener() {
            final Dialog dialog = new Dialog(activity);

            @SuppressLint("SetJavaScriptEnabled")
            @Override
            public void onClick(View view) {
                if (dialog.isShowing()) {
                    return;
                }
                try {
                    dialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
                } catch (AndroidRuntimeException ignored) {
                }
                dialog.setContentView(R.layout.sign_in_web);
                dialog.setTitle("Sign in with Twitter");
                dialog.setCancelable(true);
                dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                WebView webView = (WebView) dialog.findViewById(R.id.sign_in_web_view);
                webView.getSettings().setJavaScriptEnabled(true);
                class JsObject {
                    @JavascriptInterface
                    public void saveSessionId(String sessionId) {
                        authentication.saveSessionId(sessionId);
                        Log.i("AnimetickLog", "session_id: " + sessionId);
                    }

                    @JavascriptInterface
                    public void saveCsrfToken(String csrfToken) {
                        authentication.saveCsrfToken(csrfToken);
                        Log.i("AnimetickLog", "csrf_token: " + csrfToken);
                    }
                }
                webView.addJavascriptInterface(new JsObject(), "animetickObj");
                webView.loadUrl(Config.ANIMETICK_SCHEME + "://" + Config.ANIMETICK_URL + SIGN_IN_URL);
                webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
                        if (Config.ANIMETICK_USER != null) {
                            handler.proceed(Config.ANIMETICK_USER, Config.ANIMETICK_PASS);
                        }
                    }

                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        if (url.equals("animetick://login_session")) {
                            view.loadUrl("javascript:animetickObj.saveSessionId(animetick.app.getSessionForNativeApp())");
                            view.loadUrl("javascript:animetickObj.saveCsrfToken(animetick.app.getCSRFTokenForNativeApp())");
                            dialog.cancel();
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Intent mainIntent = new Intent(activity, MainActivity.class);
                            startActivity(mainIntent);
                            activity.finish();
                            return false;
                        }
                        view.loadUrl(url);
                        return true;
                    }
                });
                webView.requestFocus(View.FOCUS_DOWN);
                webView.setFocusable(true);
                dialog.show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.authentication, menu);
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void moveToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
