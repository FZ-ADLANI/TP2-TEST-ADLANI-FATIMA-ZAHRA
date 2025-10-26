package ma.emsi.FZA.Tests;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.googleai.GoogleAiEmbeddingModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import java.time.Duration;

/**
 * Le RAG facile !
 */
public class Test4 {

    interface Assistant {
        String chat(String userMessage);
    }

    public static void main(String[] args) {
        String apiKey = System.getenv("Gemini-API-Key");

        // Création du modèle de chat
        ChatModel modele = GoogleAiGeminiChatModel.builder()
                .apiKey(apiKey)
                .modelName("gemini-2.5-flash")
                .temperature(0.3)
                .build();

        // Création du modèle d'embeddings
        EmbeddingModel embeddingModel = GoogleAiEmbeddingModel.builder()
                .apiKey(apiKey)
                .modelName("text-embedding-004")
                .timeout(Duration.ofSeconds(30))
                .build();

        // Chargement du document
        String nomDocument = "infos.txt";
        Document document = FileSystemDocumentLoader.loadDocument(nomDocument);
        EmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();

        // Ingestion avec le builder
        EmbeddingStoreIngestor.builder()
                .embeddingModel(embeddingModel)
                .embeddingStore(embeddingStore)
                .build()
                .ingest(document);

        // Création du retriever avec le modèle d'embedding
        EmbeddingStoreContentRetriever retriever = EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(embeddingModel) // Ajout du modèle d'embedding ici
                .maxResults(2) // Optionnel : nombre maximum de résultats
                .build();

        // Création de l'assistant
        Assistant assistant =
                AiServices.builder(Assistant.class)
                        .chatModel(modele)
                        .chatMemory(MessageWindowChatMemory.withMaxMessages(10))
                        .contentRetriever(retriever) // Utilisation du retriever configuré
                        .build();


        // Test 1
        String question1 = "Pierre appelle son chat. Qu'est-ce qu'il pourrait dire ?";
        String reponse1 = assistant.chat(question1);
        System.out.println("Question 1: " + question1);
        System.out.println("Réponse 1: " + reponse1);
        System.out.println();


    }
}