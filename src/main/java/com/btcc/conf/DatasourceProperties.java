package com.btcc.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by peiyou on 10/27/16.
 */
@Component
@ConfigurationProperties(prefix = "datasource")
public class DatasourceProperties {

    public static Map<String,String> btcchinaPhpbb = new HashMap<>();
    public static Map<String,String> dashboard = new HashMap<>();
    public static Map<String,String> btcchinaMk = new HashMap<>();
    public static Map<String,String> report = new HashMap<>();
    public static Map<String,String> prog = new HashMap<>();
    public static Map<String,String> spotusd = new HashMap<>();

    public static Map<String,String> bttx = new HashMap<>();


    public Map<String, String> getDashboard() {
        return dashboard;
    }

    public void setDashboard(Map<String, String> dashboard) {
        DatasourceProperties.dashboard = dashboard;
    }

    public Map<String, String> getBtcchinaMk() {
        return btcchinaMk;
    }

    public void setBtcchinaMk(Map<String, String> btcchinaMk) {
        DatasourceProperties.btcchinaMk = btcchinaMk;
    }

    public  Map<String, String> getReport() {
        return report;
    }

    public void setReport(Map<String, String> report) {
        DatasourceProperties.report = report;
    }

    public Map<String, String> getBtcchinaPhpbb() {
        return btcchinaPhpbb;
    }

    public void setBtcchinaPhpbb(Map<String, String> btcchinaPhpbb) {
        DatasourceProperties.btcchinaPhpbb = btcchinaPhpbb;
    }

    public Map<String, String> getProg() {
        return prog;
    }

    public void setProg(Map<String, String> prog) {
        DatasourceProperties.prog = prog;
    }

    public Map<String, String> getBttx(){
        return bttx;
    }

    public void setBttx(Map<String, String> bttx){
        DatasourceProperties.bttx = bttx;
    }

    public Map<String, String> getSpotusd() {
        return spotusd;
    }

    public void setSpotusd(Map<String, String> spotusd) {
        DatasourceProperties.spotusd = spotusd;
    }
}
