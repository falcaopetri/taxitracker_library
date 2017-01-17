package br.ufscar.dc.mds.taxitracker_library;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
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
                        System.out.println(obj.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("REST_API", errorResponse.toString());
            }
        });
    }
}
