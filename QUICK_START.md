# Quick Start Guide - LegalAI-Endee

## 🚀 Get Started in 3 Steps

### Step 1: Build the Application
```bash
cd /workspaces/legal-ai-app
mvn clean install -DskipTests
```

**Expected:** Build should complete in ~30 seconds with `BUILD SUCCESS`

### Step 2: Start the Server
```bash
java -jar target/legal-ai-endee-1.0.0.jar
```

**Expected Output:**
```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_|\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                   (v3.2.0)

2026-04-11 17:46:30 - Starting LegalAIApplication
2026-04-11 17:46:32 - Tomcat started on port(s): 8080
2026-04-11 17:46:32 - Started LegalAIApplication in 2.8 seconds
```

### Step 3: Access the Application

**Web UI:**
Open your browser and navigate to:
```
http://localhost:8080
```

You should see the beautiful LegalAI-Endee interface with:
- Search bar with icon
- Status badge showing "Ready"
- Sample queries to try
- Professional gradient UI

---

## 🧪 Testing the API

### Option A: Using the Test Script
```bash
chmod +x test_api.sh
./test_api.sh
```

### Option B: Manual Testing

**Test 1: Health Check**
```bash
curl http://localhost:8080/api/health
```

**Expected Response:**
```json
{
  "status": "UP",
  "service": "LegalAI-Endee RAG Engine",
  "version": "1.0.0"
}
```

**Test 2: Search Query**
```bash
curl -X POST http://localhost:8080/api/search \
  -H "Content-Type: application/json" \
  -d '{
    "query": "What is copyright protection?", "topK": 5
  }' | jq .
```

**Expected Response:**
```json
{
  "success": true,
  "message": "Search completed successfully",
  "processingTimeMs": 15,
  "results": [
    {
      "id": "legal_doc_8",
      "text": "Copyright protects original works of authorship...",
      "score": 0.87,
      "metadata": "Sample legal document 8"
    }
  ]
}
```

---

## 📁 Project Files Overview

| File | Purpose |
|------|---------|
| `pom.xml` | Maven dependencies and build config |
| `README.md` | Comprehensive project documentation |
| `QUICK_START.md` | This file |
| `src/main/java/com/legalai/` | Java source code |
| `src/main/resources/static/index.html` | Web UI |
| `target/legal-ai-endee-1.0.0.jar` | Compiled JAR (after build) |

---

## 🎯 Try These Search Queries

1. **Contract Law**
   - "What constitutes breach of contract?"
   - "What is mutual agreement required for?"

2. **Intellectual Property**
   - "How does copyright protection work?"
   - "What are patent rights?"

3. **Corporate Law**
   - "What is shareholder liability?"
   - "What are director duties?"

4. **Criminal Law**
   - "What is criminal intent?"
   - "What is the definition of negligence?"

---

## 🔧 Configuration

Default settings in `src/main/resources/application.properties`:
- **Server Port**: 8080
- **Context Path**: / (root)
- **Embedding Dimension**: 384
- **Number of Documents**: 20 sample legal docs

---

## 🐛 Troubleshooting

### Port 8080 Already in Use
```bash
# Find what's using port 8080
lsof -i :8080

# Kill the process (if needed)
kill -9 <PID>

# Or use a different port
java -Dserver.port=8081 -jar target/legal-ai-endee-1.0.0.jar
```

### Application Won't Start
1. Ensure Java 17+ is installed: `java -version`
2. Check Maven installed: `mvn -version`
3. Run rebuild: `mvn clean install -DskipTests`

### No Search Results
- Try simpler keywords: "contract", "copyright", "criminal"
- Results are ranked by semantic similarity (0.0 to 1.0)
- Check console logs for errors

### Build Fails
```bash
# Clear Maven cache
rm -rf ~/.m2/repository

# Fresh rebuild
mvn clean install -DskipTests
```

---

## 📊 Understanding Results

Each search result includes:

- **#1, #2, #3**: Result ranking
- **92% Match**: Relevance score (cosine similarity)
- **Text**: The legal document content
- **Metadata**: Document category or source
- **ID**: Unique identifier

### Score Interpretation
- **0.9+**: Excellent match, highly relevant
- **0.7-0.9**: Good match, very relevant
- **0.5-0.7**: Fair match, somewhat relevant
- **<0.5**: Weak match, loosely related

---

## 🚀 Next Steps

1. **Explore the Code**
   - Check `EndeeService.java` for mock vector store
   - Review `EmbeddingService.java` for semantic similarity
   - Study `SearchController.java` for API endpoints

2. **Extend Functionality**
   - Add document upload feature
   - Integrate real Endee Vector DB
   - Add filtering by document type
   - Implement user authentication

3. **Production Deployment**
   - Use real embedding models (OpenAI, HuggingFace)
   - Connect to actual Endee instance
   - Add caching layer (Redis)
   - Implement authentication & rate limiting

---

## 📞 Need Help?

1. **Check the main README**: `README.md`
2. **Review code comments**: All Java files have detailed comments
3. **Test with curl**: `curl http://localhost:8080/api/health`
4. **Check logs**: Application logs appear in console

---

**Happy Searching!** 🎉
