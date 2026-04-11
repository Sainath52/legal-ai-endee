package com.legalai.controller;

import com.legalai.model.SearchRequest;
import com.legalai.model.SearchResponse;
import com.legalai.model.SearchResult;
import com.legalai.service.RAGService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * Search Controller - REST API endpoints for legal search
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class SearchController {
    
    @Autowired
    private RAGService ragService;
    
    /**
     * Perform semantic search on legal documents
     * POST /api/search
     * @param searchRequest Search query and parameters
     * @return SearchResponse with results
     */
    @PostMapping("/search")
    public ResponseEntity<SearchResponse> search(@RequestBody SearchRequest searchRequest) {
        long startTime = System.currentTimeMillis();
        
        try {
            // Validate input
            if (searchRequest.getQuery() == null || searchRequest.getQuery().trim().isEmpty()) {
                SearchResponse response = new SearchResponse();
                response.setSuccess(false);
                response.setMessage("Query cannot be empty");
                response.setResults(List.of());
                response.setProcessingTimeMs(System.currentTimeMillis() - startTime);
                return ResponseEntity.badRequest().body(response);
            }
            
            // Set default topK if not provided
            int topK = searchRequest.getTopK() <= 0 ? 5 : searchRequest.getTopK();
            
            // Execute RAG search
            List<SearchResult> results = ragService.ragSearch(searchRequest.getQuery(), topK);
            
            // Build response
            SearchResponse response = new SearchResponse();
            response.setSuccess(true);
            response.setMessage("Search completed successfully");
            response.setResults(results);
            response.setProcessingTimeMs(System.currentTimeMillis() - startTime);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            SearchResponse errorResponse = new SearchResponse();
            errorResponse.setSuccess(false);
            errorResponse.setMessage("Error: " + e.getMessage());
            errorResponse.setResults(List.of());
            errorResponse.setProcessingTimeMs(System.currentTimeMillis() - startTime);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    /**
     * Store a new legal document
     * POST /api/store
     * @param vectorData Vector data to store
     * @return Response
     */
    @PostMapping("/store")
    public ResponseEntity<?> storeDocument(
            @RequestParam String id,
            @RequestParam String text,
            @RequestParam(required = false, defaultValue = "User document") String metadata) {
        try {
            boolean success = ragService.storeLegalDocument(id, text, metadata);
            if (success) {
                return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Document stored successfully",
                    "id", id
                ));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "success", false,
                    "message", "Failed to store document"
                ));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "success", false,
                "message", "Error: " + e.getMessage()
            ));
        }
    }
    
    /**
     * Health check endpoint
     * @return OK status
     */
    @GetMapping("/health")
    public ResponseEntity<?> health() {
        return ResponseEntity.ok(Map.of(
            "status", "UP",
            "service", "LegalAI-Endee RAG Engine",
            "version", "1.0.0"
        ));
    }
}
