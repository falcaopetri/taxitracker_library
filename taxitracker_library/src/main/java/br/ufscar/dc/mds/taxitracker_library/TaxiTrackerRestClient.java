package br.ufscar.dc.mds.taxitracker_library;

/**
 * Created by petri on 26/12/16.
 */

import com.loopj.android.http.*;

public class TaxiTrackerRestClient {
    private static final String BASE_URL = "http://localhost:5000/api/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}