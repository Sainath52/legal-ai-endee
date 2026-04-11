package com.legalai.service;

import com.legalai.model.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * RAG (Retrieval Augmented Generation) Service
 * Implements the RAG pipeline for legal search:
 * 1. Embed the user query
 * 2. Search Endee vector DB for relevant documents
 * 3. Return ranked results
 */
@Service
public class RAGService {
    
    @Autowired
    private EndeeService endeeService;
    
    @Autowired
    private EmbeddingService embeddingService;
    
    /**
     * Execute RAG pipeline for legal search
     * @param query User's legal query
     * @param topK Number of results to return
     * @return List of SearchResult objects
     */
    public List<SearchResult> ragSearch(String query, int topK) {
        // Step 1: Embed the query
        List<Double> queryEmbedding = embeddingService.embed(query);
        
        // Step 2: Search Endee vector DB
        List<Map<String, Object>> vectorSearchResults = endeeService.searchSimilarVectors(query, topK);
        
        // Step 3: Convert results to SearchResult objects with ranking
        List<SearchResult> results = new ArrayList<>();
        for (int i = 0; i < vectorSearchResults.size(); i++) {
            Map<String, Object> result = vectorSearchResults.get(i);
            SearchResult searchResult = new SearchResult();
            searchResult.setId((String) result.get("id"));
            searchResult.setText((String) result.get("text"));
            searchResult.setScore((Double) result.get("score"));
            searchResult.setMetadata((String) result.get("metadata"));
            
            results.add(searchResult);
        }
        
        return results;
    }
    
    /**
     * Store a legal document for future retrieval
     * @param id Document ID
     * @param text Document text
     * @param metadata Document metadata
     * @return true if successful
     */
    public boolean storeLegalDocument(String id, String text, String metadata) {
        return endeeService.storeVector(id, text, metadata);
    }
}
