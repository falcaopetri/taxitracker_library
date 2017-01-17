package br.ufscar.dc.mds.taxitracker_library;

import android.content.Context;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;

public class TaxiTrackerRestClient {
    private static final String BASE_URL = "http://192.168.1.37:5000/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void add_auth_token(String server_token) {
        client.addHeader("Authorization", "Token " + server_token);
    }

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void postJSON(Context context, String url, Object serializable, AsyncHttpResponseHandler responseHandler) throws UnsupportedEncodingException {
        Gson gson = new Gson();
        String json = gson.toJson(serializable);

        StringEntity entity = new StringEntity(json);
        client.post(context, getAbsoluteUrl(url), entity, ContentType.APPLICATION_JSON.getMimeType(), responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}