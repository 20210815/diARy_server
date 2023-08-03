package com.hanium.diARy.plan.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomDateDeserializer extends JsonDeserializer<Date> {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String dateAsString = jsonParser.getText();
        try {
            return new java.sql.Date(dateFormat.parse(dateAsString).getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}