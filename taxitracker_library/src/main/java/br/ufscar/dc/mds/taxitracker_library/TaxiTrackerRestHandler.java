package br.ufscar.dc.mds.taxitracker_library;

public interface TaxiTrackerRestHandler {
    void on_start_login();

    void on_login(String access_token);
}
