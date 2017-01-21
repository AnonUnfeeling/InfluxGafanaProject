package com.jdroidcoder.dto;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.Map;

/**
 * Created by jdroidcoder on 21.01.17.
 */
public class PointDto {
    @NotEmpty(message = "Database policy can not be empty")
    private String measurement;
    @NotEmpty(message = "Database policy can not be empty")
    private Map<String, Object> fields;

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public Map<String, Object> getFields() {
        return fields;
    }

    public void setFields(Map<String, Object> fields) {
        this.fields = fields;
    }
}
