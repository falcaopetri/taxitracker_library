package br.ufscar.dc.mds.taxitracker_library;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by petri on 26/12/16.
 */

public class TaxiTrackerRestClientUsage {
//    Example from: http://loopj.com/android-async-http/
//
//    public void getPublicTimeline() throws JSONException {
//        TaxiTrackerRestClient.get("statuses/public_timeline.json", null, new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                // If the response is JSONObject instead of expected JSONArray
//            }
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
//                // Pull out the first event on the public timeline
//                JSONObject firstEvent = null;
//                String tweetText = null;
//                try {
//                    firstEvent = (JSONObject) timeline.get(0);
//                    tweetText = firstEvent.getString("text");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                // Do something with the response
//                System.out.println(tweetText);
//            }
//        });
//    }
}
