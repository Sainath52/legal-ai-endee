package com.legalai.model;

public class SearchRequest {
    private String query;
    private int topK;
    
    public SearchRequest() {}
    
    public SearchRequest(String query, int topK) {
        this.query = query;
        this.topK = topK;
    }
    
    public String getQuery() {
        return query;
    }
    
    public void setQuery(String query) {
        this.query = query;
    }
    
    public int getTopK() {
        return topK;
    }
    
    public void setTopK(int topK) {
        this.topK = topK;
    }
}
