package net.animetick.animetick_android.model;

import android.util.Log;

import net.animetick.animetick_android.config.Config;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by kazz on 2013/08/19.
 */
public class Networking {

    private DefaultHttpClient client = new DefaultHttpClient();
    private String baseUrl = Config.ANIMETICK_SCHEME + "://" + Config.ANIMETICK_URL + "/";

    public Networking(Authentication authentication) {
        if (Config.ANIMETICK_USER != null) {
            Credentials credentials = new UsernamePasswordCredentials(Config.ANIMETICK_USER, Config.ANIMETICK_PASS);
            AuthScope scope = new AuthScope(Config.ANIMETICK_URL, Config.ANIMETICK_PORT);
            client.getCredentialsProvider().setCredentials(scope, credentials);
        }
        BasicClientCookie cookie = new BasicClientCookie(Config.ANIMETICK_SESSION_KEY, authentication.getSessionId());
        cookie.setDomain(Config.ANIMETICK_URL);
        client.getCookieStore().addCookie(cookie);
    }

    public InputStream get(String path) throws IOException {
        HttpGet httpGet = new HttpGet(baseUrl + path);
        HttpResponse httpResponse = client.execute(httpGet);
        HttpEntity httpEntity = httpResponse.getEntity();
        return httpEntity.getContent();
    }

}
