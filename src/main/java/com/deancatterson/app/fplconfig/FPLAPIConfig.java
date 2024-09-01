package com.deancatterson.app.fplconfig;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("fpl-api")
public class FPLAPIConfig {

    private String baseurl;

    public void setBaseurl(String baseurl) {
        this.baseurl = baseurl;
    }

    public String getBaseurl() {
        return baseurl;
    }

    @Override
    public String toString() {
        return "GlobalProperties{" +
                "baseurl=" + baseurl +
                '}';
    }

}
