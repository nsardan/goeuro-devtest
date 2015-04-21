package com.goeuro.resttest.json;



import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.Consumes;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;

import java.lang.reflect.Type;
import java.lang.annotation.Annotation;

@Provider
@Consumes(MediaType.APPLICATION_JSON)
public class JsonMessageBodyReader implements MessageBodyReader<JsonArray> {
    
    @Override
    public boolean isReadable(java.lang.Class<?> type,
                   Type genericType,
                   Annotation[] annotations,
                   MediaType mediaType) {
        return true;
        
    }
    
    @Override
    public JsonArray readFrom(java.lang.Class<JsonArray> type,
           Type genericType,
           Annotation[] annotations,
           MediaType mediaType,
           MultivaluedMap<java.lang.String,java.lang.String> httpHeaders,
           java.io.InputStream entityStream)
           throws java.io.IOException,
                  WebApplicationException {
        
    	JsonArray a =  Json.createReader(entityStream).readArray();
        return a;
        
    }
    
}
