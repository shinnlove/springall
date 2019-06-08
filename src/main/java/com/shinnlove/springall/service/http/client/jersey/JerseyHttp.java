package com.shinnlove.springall.service.http.client.jersey;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

public class JerseyHttp {

    /** http请求网址 */
    private static final String REQUEST_URL = "http://127.0.0.1:8080/springall";

    public static void main(String[] args) {

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(REQUEST_URL).path("user/queryUserById");
        Response res = target.request().get();
        System.out.println("Response status code:"+res.getStatus());
        System.out.println("Response body:");
        System.out.println(res.readEntity(String.class));
        res.close();

    }

}
