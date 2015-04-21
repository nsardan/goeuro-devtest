package com.goeuro.resttest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.json.JsonObject;

import com.goeuro.resttest.json.JsonMessageBodyReader;
import com.goeuro.resttest.json.JsonMessageBodyWriter;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class Main {
    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Please enter a single arguement of CITY_NAME");

            return;
        }

        String cityName = args[0];

        if (cityName != null && cityName.trim().isEmpty()) {
            System.out.println("Please enter a valid CITY_NAME");

            return;
        }

        ClientConfig cc = new DefaultClientConfig();
        cc.getClasses().add(JsonMessageBodyReader.class);
        cc.getClasses().add(JsonMessageBodyWriter.class);

        Client client = Client.create(cc);

        WebResource apiRoot = client
                .resource("http://api.goeuro.com/api/v2/position/suggest/en/");

        List<JsonObject> results = apiRoot.path(cityName)
                .accept("application/json")
                .get(new GenericType<List<JsonObject>>() {
                });


        if (results.isEmpty()) {
            System.out.println("The City " + cityName + " returned no results");

            return;
        }

        File f = new File(System.getProperty("java.class.path"));
        File dir = f.getAbsoluteFile().getParentFile();
        String path = dir.toString();

        String fullPath = path + "/test.csv";
        File file = new File(fullPath);

        try {
            FileWriter writer = new FileWriter(file);
            writer.append("_id");
            writer.append(',');
            writer.append("key");
            writer.append(',');
            writer.append("name");
            writer.append(',');
            writer.append("fullName");
            writer.append(',');
            writer.append("iata_airport_code");
            writer.append(',');
            writer.append("type");
            writer.append(',');
            writer.append("country");
            writer.append(',');
            writer.append("latitude");
            writer.append(',');
            writer.append("locationId");
            writer.append(',');
            writer.append("longitude");
            writer.append(',');
            writer.append("inEurope");
            writer.append(',');
            writer.append("countryCode");
            writer.append(',');
            writer.append("coreCountry");
            writer.append(',');
            writer.append("distance");
            writer.append('\n');

            for (JsonObject joData : results) {

                writer.append(joData.get("_id").toString());
                writer.append(',');

                writer.append(joData.get("key").toString());
                writer.append(',');

                writer.append(joData.get("name").toString());
                writer.append(',');

                writer.append(joData.get("fullName").toString());
                writer.append(',');

                writer.append(joData.get("iata_airport_code").toString());
                writer.append(',');

                writer.append(joData.get("type").toString());
                writer.append(',');

                writer.append(joData.get("country").toString());
                writer.append(',');

                JsonObject geo = joData.getJsonObject("geo_position");

                writer.append(geo.get("latitude").toString());
                writer.append(',');

                writer.append(geo.get("longitude").toString());
                writer.append(',');

                writer.append(joData.get("locationId").toString());
                writer.append(',');

                writer.append(joData.get("inEurope").toString());
                writer.append(',');

                writer.append(joData.get("countryCode").toString());
                writer.append(',');

                writer.append(joData.get("coreCountry").toString());
                writer.append(',');

                writer.append(joData.get("distance").toString());
                writer.append('\n');
            }


            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("File Created at: " + fullPath);

    }

}