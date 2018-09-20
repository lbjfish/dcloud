package com.sida.security.edge.exception;

import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

import java.util.Map;
import java.util.TreeMap;

public class CustomerOAuth2Exception extends InvalidTokenException {
    public CustomerOAuth2Exception(String msg) {
        super(msg);
    }

    public CustomerOAuth2Exception(String msg, Throwable t) {
        super(msg, t);
    }

    Map<String, Object> additionalInformation = null;

    public void addAdditionalInformation(String key, Object value) {
        if (this.additionalInformation == null) {
            this.additionalInformation = new TreeMap<String, Object>();
        }

        this.additionalInformation.put(key, value);

    }

    public Map<String, Object> getAddInfo() {
        return additionalInformation;
    }

    public String getSummary() {

        StringBuilder builder = new StringBuilder();

        String delim = "";

        String error = this.getOAuth2ErrorCode();
        if (error != null) {
            builder.append(delim).append("error=\"").append(error).append("\"");
            delim = ", ";
        }

        String errorMessage = this.getMessage();
        if (errorMessage != null) {
            builder.append(delim).append("error_description=\"").append(errorMessage).append("\"");
            delim = ", ";
        }

        Map<String, Object> additionalParams = this.getAddInfo();
        if (additionalParams != null) {
            for (Map.Entry<String, Object> param : additionalParams.entrySet()) {
                builder.append(delim).append(param.getKey()).append("=\"").append(param.getValue()).append("\"");
                delim = ", ";
            }
        }

        return builder.toString();

    }

    @Override
    public String toString() {
        return "CustomerOAuth2Exception{" +
                "additionalInformation=" + additionalInformation +
                '}';
    }
}
