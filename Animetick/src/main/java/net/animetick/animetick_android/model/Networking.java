package net.animetick.animetick_android.model;

import net.animetick.animetick_android.config.Config;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by kazz on 2013/08/19.
 */
public class Networking {

    private DefaultHttpClient client = new DefaultHttpClient();
    private String baseUrl = Config.ANIMETICK_SCHEME + "://" + Config.ANIMETICK_URL + "/";
    private Authentication authentication;

    public Networking(Authentication authentication) {
        this.authentication = authentication;
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

    public InputStream post(String path, Map<String, String> rawParams) throws IOException {
        HttpPost httpPost = new HttpPost(baseUrl + path);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : rawParams.entrySet()) {
            params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        params.add(new BasicNameValuePair("authenticity_token", authentication.getCsrfToken()));
        httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        HttpResponse httpResponse = client.execute(httpPost);
        HttpEntity httpEntity = httpResponse.getEntity();
        return httpEntity.getContent();
    }

}
