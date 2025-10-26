package ma.emsi.FZA.Tests;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.googleai.GoogleAiEmbeddingModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.store.embedding.CosineSimilarity;
import java.time.Duration;

public class Test3 {
    public static void main(String[] args) {
        // Récupération de la clé API
        String apiKey = System.getenv("Gemini-API-Key");

        // Construction du modèle d'embedding avec le modèle spécifié
        EmbeddingModel embeddingModel = GoogleAiEmbeddingModel.builder()
                .apiKey(apiKey)
                .modelName("gemini-embedding-001") // Modèle d'embedding Gemini spécifié
                .outputDimensionality(300) // Dimension des vecteurs d'embedding
                .timeout(Duration.ofSeconds(30)) // Temps d'attente pour les requêtes
                .build();

        // Couples de phrases similaires
        String phrase1 = "Le chat dort sur le canapé";
        String phrase2 = "Un félin repose sur le sofa";

        String phrase3 = "J'aime programmer en Java";
        String phrase4 = "Je adore coder avec le langage Java";

        // Couples de phrases non similaires
        String phrase5 = "Il fait beau aujourd'hui";
        String phrase6 = "La programmation orientée objet est complexe";

        // Génération des embeddings pour le premier couple (similaire)
        Response<Embedding> embedding1 = embeddingModel.embed(phrase1);
        Response<Embedding> embedding2 = embeddingModel.embed(phrase2);

        // Génération des embeddings pour le deuxième couple (similaire)
        Response<Embedding> embedding3 = embeddingModel.embed(phrase3);
        Response<Embedding> embedding4 = embeddingModel.embed(phrase4);

        // Génération des embeddings pour le troisième couple (non similaire)
        Response<Embedding> embedding5 = embeddingModel.embed(phrase5);
        Response<Embedding> embedding6 = embeddingModel.embed(phrase6);

        // Calcul des similarités cosinus
        double similarite1 = CosineSimilarity.between(embedding1.content(), embedding2.content());
        double similarite2 = CosineSimilarity.between(embedding3.content(), embedding4.content());
        double similarite3 = CosineSimilarity.between(embedding5.content(), embedding6.content());

        // Affichage des résultats
        System.out.println("=== RÉSULTATS DE SIMILARITÉ COSINUS ===");
        System.out.println("Couple 1 (similaire) :");
        System.out.println("  Phrase 1: \"" + phrase1 + "\"");
        System.out.println("  Phrase 2: \"" + phrase2 + "\"");
        System.out.println("  Similarité: " + similarite1);
        System.out.println();

        System.out.println("Couple 2 (similaire) :");
        System.out.println("  Phrase 1: \"" + phrase3 + "\"");
        System.out.println("  Phrase 2: \"" + phrase4 + "\"");
        System.out.println("  Similarité: " + similarite2);
        System.out.println();

        System.out.println("Couple 3 (non similaire) :");
        System.out.println("  Phrase 1: \"" + phrase5 + "\"");
        System.out.println("  Phrase 2: \"" + phrase6 + "\"");
        System.out.println("  Similarité: " + similarite3);
    }
}