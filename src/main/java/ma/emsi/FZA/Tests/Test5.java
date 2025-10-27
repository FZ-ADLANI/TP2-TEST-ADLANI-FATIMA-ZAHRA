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
import java.util.Scanner;
import java.time.Duration;

public class Test5 {
    // Interface pour l'assistant IA
    interface Assistant {
        String chat(String message);
    }

    public static void main(String[] args) {
        String apiKey = System.getenv("Gemini-API-Key");

        // Création du modèle de chat
        ChatModel modele = GoogleAiGeminiChatModel.builder()
                .apiKey(apiKey)
                .temperature(0.2)
                .modelName("gemini-2.5-flash")
                .build();

        // Création du modèle d'embedding
        EmbeddingModel embeddingModel = GoogleAiEmbeddingModel.builder()
                .apiKey(apiKey)
                .modelName("text-embedding-004")
                .timeout(Duration.ofSeconds(30))
                .build();

        // Chargement du document PDF
        String nomDocument = "agentsmcp.pdf";
        Document document = FileSystemDocumentLoader.loadDocument(nomDocument);
        EmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();

        // Ingestion du document avec le modèle d'embedding
        EmbeddingStoreIngestor.builder()
                .embeddingModel(embeddingModel)
                .embeddingStore(embeddingStore)
                .build()
                .ingest(document);

        // Création du retriever avec le modèle d'embedding
        EmbeddingStoreContentRetriever retriever = EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(embeddingModel)
                .maxResults(2)
                .build();

        // Création de l'assistant avec RAG
        Assistant assistant =
                AiServices.builder(Assistant.class)
                        .chatModel(modele)
                        .chatMemory(MessageWindowChatMemory.withMaxMessages(10))
                        .contentRetriever(retriever)
                        .build();

        // Conversation interactive avec l'utilisateur
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("==================================================");
                System.out.println("Posez votre question : ");
                String question = scanner.nextLine();
                if (question.isBlank()) {
                    continue;
                }
                System.out.println("==================================================");
                if ("fin".equalsIgnoreCase(question)) {
                    break;
                }
                String reponse = assistant.chat(question);
                System.out.println("Assistant : " + reponse);
                System.out.println("==================================================");
            }
        }
    }
}