package br.ufscar.dc.mds.taxitracker_library;

import org.json.JSONObject;

public interface TaxiTrackerRestHandler {
    void on_start_login();

    void on_login(String access_token);

    void on_refresh_info(JSONObject response);
}
