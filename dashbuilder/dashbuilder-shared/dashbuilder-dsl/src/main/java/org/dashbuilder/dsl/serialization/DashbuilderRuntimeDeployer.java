package org.dashbuilder.dsl.serialization;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.dashbuilder.dsl.model.Dashboard;

public class DashbuilderRuntimeDeployer {

    private String url;
    private String user;
    private String password;

    public DashbuilderRuntimeDeployer(String url, String user, String password) {
        super();
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public void deploy(Dashboard dashboard) throws Exception {
        // TODO: Figure out best way to post the file bytes
        URL url = new URL(this.url);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setInstanceFollowRedirects(false);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "text/plain");
        addAuth(connection);
        connection.connect();
    }

    private void addAuth(HttpURLConnection connection) {
        String auth = user + ":" + password;
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
        connection.setRequestProperty("Authorization", "Basic " + new String(encodedAuth));
        
    }

}
