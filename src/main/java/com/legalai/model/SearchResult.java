package com.legalai.model;

public class SearchResult {
    private String id;
    private String text;
    private Double score;
    private String metadata;
    
    public SearchResult() {}
    
    public SearchResult(String id, String text, Double score, String metadata) {
        this.id = id;
        this.text = text;
        this.score = score;
        this.metadata = metadata;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
    }
    
    public Double getScore() {
        return score;
    }
    
    public void setScore(Double score) {
        this.score = score;
    }
    
    public String getMetadata() {
        return metadata;
    }
    
    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }
}
