package br.ufscar.dc.mds.taxitracker_library;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;


// Reference: http://loopj.com/android-async-http/
public class TaxiTrackerRestClientUsage {
    private final static String LOG_TAG = "REST_API";
    TaxiTrackerRestHandler handler;

    public TaxiTrackerRestClientUsage(TaxiTrackerRestHandler _handler) {
        handler = _handler;
    }

    public void ping() {
        TaxiTrackerRestClient.get("", null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                System.out.println(response.toString());
            }
        });
    }

    public void login(String id_token, String tipo) {
        RequestParams params = new RequestParams();
        params.add("id_token", id_token);
        params.add("tipo", tipo);
        TaxiTrackerRestClient.post("login/", params, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                handler.on_start_login();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                String server_token = null;

                try {
                    server_token = response.getString("token");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                handler.on_login(server_token);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                if (errorResponse != null) {
                    Log.e(LOG_TAG + "/login", errorResponse.toString());
                } else {
                    Log.e(LOG_TAG + "/login", "failed to connect " + statusCode);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                if (throwable != null) {
                    Log.e(LOG_TAG + "/login", throwable.getMessage());
                    throwable.printStackTrace();
                } else {
                    Log.e(LOG_TAG + "/login", "failed to connect " + statusCode);
                }
            }
        });
    }

    public void createRace(Context context, String origem, String destino) throws UnsupportedEncodingException {
        Corrida corrida = new Corrida(origem, destino);

        Gson gson = new Gson();
        gson.toJson(corrida);

        TaxiTrackerRestClient.postJSON(context, "corridas/", corrida, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d(LOG_TAG + "/createRace", response.toString());
                handler.on_race_created(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e(LOG_TAG + "/createRace", errorResponse.toString());
            }
        });
    }

    public void add_auth_token(String server_token) {
        TaxiTrackerRestClient.add_auth_token(server_token);
    }

    // Método de exemplo, sem utilidade para a lógica da aplicação
    /*  TODO praticamente tudo vai ser troca de dados entre servidor e app.
        provavelmente "chamando" funções no server. como fazer isso?
     */
    public void getPassageiros() {
        TaxiTrackerRestClient.get("passageiros/", null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                for (int i = 0; i < response.length(); ++i) {
                    JSONObject obj = null;
                    try {
                        obj = response.getJSONObject(i);
                        Log.i(LOG_TAG + "/getPassageiros", obj.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e(LOG_TAG, errorResponse.toString());
            }
        });
    }

    public void refreshInfo(Location mLastLocation) {
        final String PRIVATE_TAG = "motoristas/refresh/";
        RequestParams params = new RequestParams();
        if (mLastLocation != null)
            params.add("curr_pos", mLastLocation.getLatitude() + "," + mLastLocation.getLongitude());
        TaxiTrackerRestClient.post(PRIVATE_TAG, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d(LOG_TAG + PRIVATE_TAG, response.toString());
                handler.on_refresh_info(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e(LOG_TAG + PRIVATE_TAG, "" + statusCode);
                throwable.printStackTrace();
                if (errorResponse != null)
                    Log.e(LOG_TAG + PRIVATE_TAG, errorResponse.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e(LOG_TAG + PRIVATE_TAG, "" + statusCode);
                throwable.printStackTrace();
            }
        });
    }

    public void refreshPassageiroInfo() {
        final String PRIVATE_TAG = "passageiros/refresh/";
        TaxiTrackerRestClient.post(PRIVATE_TAG, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d(LOG_TAG + PRIVATE_TAG, response.toString());
                handler.on_refresh_info(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e(LOG_TAG + PRIVATE_TAG, "" + statusCode);
                throwable.printStackTrace();
                if (errorResponse != null)
                    Log.e(LOG_TAG + PRIVATE_TAG, errorResponse.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e(LOG_TAG + PRIVATE_TAG, "" + statusCode);
                throwable.printStackTrace();
            }
        });
    }
}
