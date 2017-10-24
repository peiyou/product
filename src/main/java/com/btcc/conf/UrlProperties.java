package com.btcc.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by peiyou on 10/26/16.
 */
@Component
@ConfigurationProperties(prefix = "url")
@PropertySource("classpath:urlFilter.properties")
public class UrlProperties {
    public static List<String> exclude = new ArrayList<String>();

    public List<String> getExclude() {
        return exclude;
    }

    public void setExclude(List<String> exclude) {
        UrlProperties.exclude = exclude;
    }
}
