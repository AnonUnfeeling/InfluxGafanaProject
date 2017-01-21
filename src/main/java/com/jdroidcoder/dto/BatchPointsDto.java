package com.jdroidcoder.dto;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

/**
 * Created by jdroidcoder on 21.01.17.
 */
public class BatchPointsDto {
    @NotEmpty(message = "Database name can not be empty")
    @Size(max = 64, message = "Name is too long (maximum is 64 characters)")
    private String dbName;
    @NotEmpty(message = "Database policy can not be empty")
    private String policy;
    @NotEmpty(message = "Database tag can not be empty")
    private String tag;
    @NotEmpty(message = "Database tag value can not be empty")
    private String tagValue;

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTagValue() {
        return tagValue;
    }

    public void setTagValue(String tagValue) {
        this.tagValue = tagValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BatchPointsDto)) return false;

        BatchPointsDto that = (BatchPointsDto) o;

        if (!getDbName().equals(that.getDbName())) return false;
        if (!getPolicy().equals(that.getPolicy())) return false;
        if (!getTag().equals(that.getTag())) return false;
        return getTagValue().equals(that.getTagValue());
    }

    @Override
    public int hashCode() {
        int result = getDbName().hashCode();
        result = 31 * result + getPolicy().hashCode();
        result = 31 * result + getTag().hashCode();
        result = 31 * result + getTagValue().hashCode();
        return result;
    }
}
