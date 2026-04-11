package com.legalai.service;

import com.legalai.model.VectorData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Endee Vector Database Service
 * Handles all interactions with the Endee Vector DB
 * For now, uses in-memory storage (mock). In production, would use REST API calls to actual Endee instance.
 */
@Service
public class EndeeService {
    
    @Autowired
    private EmbeddingService embeddingService;
    
    // In-memory storage of vectors (Mock Endee DB)
    private Map<String, VectorData> vectorStore = new HashMap<>();
    
    // Sample legal documents for initial seeding
    private static final List<String> SAMPLE_LEGAL_DOCS = Arrays.asList(
        // Contract Law
        "A contract is a legally binding agreement between two or more parties. It consists of an offer and acceptance with mutual intent. Consideration is essential for a valid contract.",
        "Breach of contract occurs when one party fails to perform their obligations. Remedies include damages, specific performance, and injunction.",
        "Employment contracts specify terms of employment including salary, benefits, and responsibilities. Employment can be at-will or for a fixed term.",
        
        // Property Law
        "Real property refers to land and anything permanently attached to it. It includes buildings, fixtures, and natural resources.",
        "A deed is a legal document that transfers property ownership from one person to another. It must be signed, delivered, and recorded.",
        "Easement is a legal right to use someone else's property for a specific purpose, such as accessing a common area.",
        
        // Intellectual Property
        "Copyright protects original works of authorship including literature, music, and software. It grants exclusive rights to reproduce and distribute.",
        "Patent protection lasts 20 years from filing date and prevents others from making, using, or selling the invention.",
        "Trademark law protects brand names, logos, and symbols that distinguish goods or services from others.",
        
        // Corporate Law
        "A corporation is a legal entity separate from its owners (shareholders). It can enter contracts, own property, and be sued.",
        "Shareholders are the owners of a corporation. Their liability is limited to their investment amount.",
        "Directors have a fiduciary duty to act in the best interest of the corporation and its shareholders.",
        
        // Tort Law
        "Negligence requires duty, breach, causation, and damages. The plaintiff must prove all four elements.",
        "Strict liability applies to dangerous activities regardless of care taken. The defendant is liable simply for causing harm.",
        "Product liability can arise from defective design, defective manufacture, or failure to warn.",
        
        // Criminal Law
        "Criminal law deals with offenses against the state and society. Penalties include fines and imprisonment.",
        "Mens rea refers to criminal intent. Different crimes require different levels of intent.",
        "DUI (Driving Under Influence) is a criminal offense with penalties including license suspension and jail time."
    );
    
    public EndeeService() {
        initializeMockData();
    }
    
    /**
     * Initialize mock data in the vector store
     */
    private void initializeMockData() {
        for (int i = 0; i < SAMPLE_LEGAL_DOCS.size(); i++) {
            String doc = SAMPLE_LEGAL_DOCS.get(i);
            String docId = "legal_doc_" + i;
            
            VectorData vectorData = new VectorData();
            vectorData.setId(docId);
            vectorData.setText(doc);
            vectorData.setEmbedding(embeddingService.embed(doc));
            vectorData.setMetadata("Sample legal document " + i);
            
            vectorStore.put(docId, vectorData);
        }
    }
    
    /**
     * Store a vector in Endee
     * @param vectorData Vector data to store
     * @return true if successfully stored
     */
    public boolean storeVector(String id, String text, String metadata) {
        try {
            VectorData vectorData = new VectorData();
            vectorData.setId(id);
            vectorData.setText(text);
            vectorData.setEmbedding(embeddingService.embed(text));
            vectorData.setMetadata(metadata);
            
            vectorStore.put(id, vectorData);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Search for similar vectors in Endee
     * @param queryText Text to search for
     * @param topK Number of top results to return
     * @return List of similar vectors with scores
     */
    public List<Map<String, Object>> searchSimilarVectors(String queryText, int topK) {
        List<Double> queryEmbedding = embeddingService.embed(queryText);
        
        // Calculate similarity for all stored vectors
        List<Map<String, Object>> results = vectorStore.values().stream()
            .map(vectorData -> {
                Map<String, Object> resultMap = new HashMap<>();
                double similarity = embeddingService.cosineSimilarity(queryEmbedding, vectorData.getEmbedding());
                resultMap.put("id", vectorData.getId());
                resultMap.put("text", vectorData.getText());
                resultMap.put("score", similarity);
                resultMap.put("metadata", vectorData.getMetadata());
                return resultMap;
            })
            .sorted((a, b) -> Double.compare((Double) b.get("score"), (Double) a.get("score")))
            .limit(topK)
            .collect(Collectors.toList());
        
        return results;
    }
    
    /**
     * Get all vectors
     * @return List of all vectors in the database
     */
    public List<VectorData> getAllVectors() {
        return new ArrayList<>(vectorStore.values());
    }
    
    /**
     * Delete a vector by ID
     * @param id Vector ID to delete
     * @return true if deleted, false if not found
     */
    public boolean deleteVector(String id) {
        return vectorStore.remove(id) != null;
    }
    
    /**
     * Get vector by ID
     * @param id Vector ID
     * @return VectorData if found, null otherwise
     */
    public VectorData getVector(String id) {
        return vectorStore.get(id);
    }
}
