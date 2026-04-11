package com.legalai.service;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Embedding Service - Converts text to vector embeddings
 * For now, this generates mock embeddings.
 * In production, this would integrate with OpenAI, HuggingFace, or other embedding providers.
 */
@Service
public class EmbeddingService {
    
    private final Random random = new Random(42); // Seeded for consistency
    private static final int EMBEDDING_DIMENSION = 384; // Standard embedding dimension
    
    /**
     * Convert text to a vector embedding
     * @param text Input text to embed
     * @return List of Double representing the embedding
     */
    public List<Double> embed(String text) {
        List<Double> embedding = new ArrayList<>();
        
        // Generate deterministic embedding based on text hash
        int textHash = text.hashCode();
        
        for (int i = 0; i < EMBEDDING_DIMENSION; i++) {
            // Create pseudo-random but deterministic values based on text and index
            long seed = ((long) textHash) * 31 + i;
            Random seededRandom = new Random(seed);
            
            // Generate value between -1 and 1, normalized
            double value = (seededRandom.nextDouble() * 2) - 1;
            embedding.add(value);
        }
        
        // Normalize the vector
        return normalizeVector(embedding);
    }
    
    /**
     * Normalize vector to unit length (L2 normalization)
     * @param vector Input vector
     * @return Normalized vector
     */
    private List<Double> normalizeVector(List<Double> vector) {
        double magnitude = 0.0;
        
        for (Double val : vector) {
            magnitude += val * val;
        }
        magnitude = Math.sqrt(magnitude);
        
        if (magnitude == 0.0) {
            return vector;
        }
        
        List<Double> normalized = new ArrayList<>();
        for (Double val : vector) {
            normalized.add(val / magnitude);
        }
        
        return normalized;
    }
    
    /**
     * Calculate cosine similarity between two vectors
     * @param vec1 First vector
     * @param vec2 Second vector
     * @return Similarity score between -1 and 1
     */
    public double cosineSimilarity(List<Double> vec1, List<Double> vec2) {
        if (vec1.size() != vec2.size()) {
            throw new IllegalArgumentException("Vectors must have the same dimension");
        }
        
        double dotProduct = 0.0;
        double magnitude1 = 0.0;
        double magnitude2 = 0.0;
        
        for (int i = 0; i < vec1.size(); i++) {
            double v1 = vec1.get(i);
            double v2 = vec2.get(i);
            
            dotProduct += v1 * v2;
            magnitude1 += v1 * v1;
            magnitude2 += v2 * v2;
        }
        
        magnitude1 = Math.sqrt(magnitude1);
        magnitude2 = Math.sqrt(magnitude2);
        
        if (magnitude1 == 0.0 || magnitude2 == 0.0) {
            return 0.0;
        }
        
        return dotProduct / (magnitude1 * magnitude2);
    }
}
