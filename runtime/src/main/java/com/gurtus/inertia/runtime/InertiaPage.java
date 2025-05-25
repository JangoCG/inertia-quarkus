package com.gurtus.inertia.runtime;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean encryptHistory;
    
    @JsonProperty("clearHistory")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean clearHistory;
    
    @JsonProperty("deferredProps")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, List<String>> deferredProps;
    
    @JsonProperty("mergeProps")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<String> mergeProps;
    
    @JsonProperty("deepMergeProps")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<String> deepMergeProps;
    
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
    
    public Map<String, List<String>> getDeferredProps() {
        return deferredProps;
    }
    
    public void setDeferredProps(Map<String, List<String>> deferredProps) {
        this.deferredProps = deferredProps;
    }
    
    public List<String> getMergeProps() {
        return mergeProps;
    }
    
    public void setMergeProps(List<String> mergeProps) {
        this.mergeProps = mergeProps;
    }
    
    public List<String> getDeepMergeProps() {
        return deepMergeProps;
    }
    
    public void setDeepMergeProps(List<String> deepMergeProps) {
        this.deepMergeProps = deepMergeProps;
    }
} 