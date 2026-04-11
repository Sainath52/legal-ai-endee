package com.legalai.model;

import java.util.List;

public class VectorData {
    private String id;
    private String text;
    private List<Double> embedding;
    private String metadata;
    
    public VectorData() {}
    
    public VectorData(String id, String text, List<Double> embedding, String metadata) {
        this.id = id;
        this.text = text;
        this.embedding = embedding;
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
    
    public List<Double> getEmbedding() {
        return embedding;
    }
    
    public void setEmbedding(List<Double> embedding) {
        this.embedding = embedding;
    }
    
    public String getMetadata() {
        return metadata;
    }
    
    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }
}
