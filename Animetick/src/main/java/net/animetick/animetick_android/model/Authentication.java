package net.animetick.animetick_android.model;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by kazz on 2013/08/08.
 */
public class Authentication {

    private static final String PREF_AUTH = "authentication";
    private static final String KEY_SESSION_ID = "session_id";
    private static final String KEY_CSRF_TOKEN = "csrf_token";
    private SharedPreferences preferences;
    private String sessionId = null;
    private String csrfToken = null;

    public Authentication(Context context) {
        preferences = context.getSharedPreferences(PREF_AUTH, Context.MODE_PRIVATE);
    }

    public boolean isAuthenticated() {
        return preferences.contains(KEY_SESSION_ID);
    }

    public String getSessionId() {
        if (sessionId == null) {
            sessionId = preferences.getString(KEY_SESSION_ID, null);
        }
        return sessionId;
    }

    public String getCsrfToken() {
        if (csrfToken == null) {
            csrfToken = preferences.getString(KEY_CSRF_TOKEN, null);
        }
        return csrfToken;
    }

    public void saveSessionId(String sessionId) {
        this.sessionId = sessionId;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_SESSION_ID, sessionId);
        editor.commit();
    }

    public void saveCsrfToken(String csrfToken) {
        this.csrfToken = csrfToken;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_CSRF_TOKEN, csrfToken);
        editor.commit();
    }

    public void removeSessionId() {
        SharedPreferences.Editor editor = preferences.edit();
        sessionId = null;
        editor.remove(KEY_SESSION_ID);
        editor.commit();
    }

    public void removeCsrfToken() {
        SharedPreferences.Editor editor = preferences.edit();
        csrfToken = null;
        editor.remove(KEY_CSRF_TOKEN);
        editor.commit();
    }

}
