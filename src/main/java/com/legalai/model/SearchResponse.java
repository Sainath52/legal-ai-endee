package com.legalai.model;

import java.util.List;

public class SearchResponse {
    private boolean success;
    private String message;
    private List<SearchResult> results;
    private long processingTimeMs;
    
    public SearchResponse() {}
    
    public SearchResponse(boolean success, String message, List<SearchResult> results, long processingTimeMs) {
        this.success = success;
        this.message = message;
        this.results = results;
        this.processingTimeMs = processingTimeMs;
    }
    
    public boolean isSuccess() {
        return success;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public List<SearchResult> getResults() {
        return results;
    }
    
    public void setResults(List<SearchResult> results) {
        this.results = results;
    }
    
    public long getProcessingTimeMs() {
        return processingTimeMs;
    }
    
    public void setProcessingTimeMs(long processingTimeMs) {
        this.processingTimeMs = processingTimeMs;
    }
}
