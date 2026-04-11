#!/bin/bash

# LegalAI-Endee Test Script
# Tests all available endpoints

echo "=========================================="
echo "LegalAI-Endee API Test Suite"
echo "=========================================="
echo ""

# Configuration
API_URL="http://localhost:8080/api"
DELAY=1

# Colors for output
GREEN='\033[0;32m'
RED='\033[0;31m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

echo -e "${BLUE}[1/4] Testing Health Endpoint${NC}"
echo "GET $API_URL/health"
curl -s -X GET "$API_URL/health" | jq . && echo "" || echo "Failed"
sleep $DELAY

echo -e "${BLUE}[2/4] Testing Search - Contract Query${NC}"
echo "POST $API_URL/search"
curl -s -X POST "$API_URL/search" \
  -H "Content-Type: application/json" \
  -d '{
    "query": "What is a breach of contract?",
    "topK": 3
  }' | jq .
sleep $DELAY

echo -e "${BLUE}[3/4] Testing Search - Copyright Query${NC}"
curl -s -X POST "$API_URL/search" \
  -H "Content-Type: application/json" \
  -d '{
    "query": "How does copyright protection work?",
    "topK": 3
  }' | jq .
sleep $DELAY

echo -e "${BLUE}[4/4] Testing Document Storage${NC}"
curl -s -X POST "$API_URL/store" \
  -H "Content-Type: application/json" \
  -d '{
    "id": "custom_doc_1",
    "text": "Liability insurance protects against financial loss due to legal claims",
    "metadata": "Insurance Law"
  }' | jq .

echo ""
echo "=========================================="
echo -e "${GREEN}✓ Test Suite Complete${NC}"
echo "=========================================="
