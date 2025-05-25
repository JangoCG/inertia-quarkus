package com.gurtus.inertia.runtime;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

/**
 * Represents an Inertia.js page object that gets serialized to JSON
 * for XHR requests or embedded in HTML for initial page loads.
 */
public class InertiaPage {
    
    @JsonProperty("component")
    private String component;
    
    @JsonProperty("props")
    private Map<String, Object> props;
    
    @JsonProperty("url")
    private String url;
    
    @JsonProperty("version")
    private String version;
    
    @JsonProperty("encryptHistory")
    private Boolean encryptHistory;
    
    @JsonProperty("clearHistory")
    private Boolean clearHistory;
    
    public InertiaPage() {
    }
    
    public InertiaPage(String component, Map<String, Object> props, String url, String version) {
        this.component = component;
        this.props = props;
        this.url = url;
        this.version = version;
    }
    
    public String getComponent() {
        return component;
    }
    
    public void setComponent(String component) {
        this.component = component;
    }
    
    public Map<String, Object> getProps() {
        return props;
    }
    
    public void setProps(Map<String, Object> props) {
        this.props = props;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getVersion() {
        return version;
    }
    
    public void setVersion(String version) {
        this.version = version;
    }
    
    public Boolean getEncryptHistory() {
        return encryptHistory;
    }
    
    public void setEncryptHistory(Boolean encryptHistory) {
        this.encryptHistory = encryptHistory;
    }
    
    public Boolean getClearHistory() {
        return clearHistory;
    }
    
    public void setClearHistory(Boolean clearHistory) {
        this.clearHistory = clearHistory;
    }
} 