package ma.emsi.FZA.Tests;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.service.AiServices;

public class Test6 {

    public static void main(String[] args) {
        String apiKey = System.getenv("Gemini-API-Key");

        // Création du modèle de chat AVEC LOGGING ACTIVÉ
        ChatModel modele = GoogleAiGeminiChatModel.builder()
                .apiKey(apiKey)
                .modelName("gemini-2.5-flash")
                .temperature(0.3)
                .logRequestsAndResponses(true)
                .build();

        // Création de l'assistant avec l'outil météo
        AssistantMeteo assistant =
                AiServices.builder(AssistantMeteo.class)
                        .chatModel(modele)
                        .tools(new MeteoTool())
                        .build();


        // =========================================================================
        // QUESTION 1 : Question valide
        // =========================================================================
        System.out.println("\n1. " + "=".repeat(50));
        String question1 = "J'ai prévu d'aller aujourd'hui à TAZA. Est-ce que tu me conseilles de prendre un parapluie ?";
        System.out.println("Question : " + question1);

        try {
            String reponse1 = assistant.chat(question1);
            System.out.println("Réponse : " + reponse1);
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }

        // =========================================================================
        // QUESTION 2 : Ville qui n'existe pas
        // =========================================================================
        System.out.println("\n2. " + "=".repeat(50));
        String question2 = "quel temps fait-il à fatimacity?";
        System.out.println("Question : " + question2);

        try {
            String reponse2 = assistant.chat(question2);
            System.out.println("Réponse : " + reponse2);
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }

        // =========================================================================
        // QUESTION 4 : Question hors météo
        // =========================================================================
        System.out.println("\n3. " + "=".repeat(50));
        String question3 = "quels sont les ingrédients d'un cake au chocolat ?";
        System.out.println("Question : " + question3);

        try {
            String reponse3 = assistant.chat(question3);
            System.out.println("Réponse : " + reponse3);
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
}