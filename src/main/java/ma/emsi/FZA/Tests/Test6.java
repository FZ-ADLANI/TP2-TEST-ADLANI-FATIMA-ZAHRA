package ma.emsi.FZA.Tests;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.service.AiServices;
import ma.emsi.FZA.Tests.MeteoTool;

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
        // QUESTION 1 : Météo normale
        // =========================================================================

        String question1 = "quel temps fait-il à Rabat?";
        System.out.println("Question : " + question1);

        try {
            String reponse1 = assistant.chat(question1);
            System.out.println("Réponse : " + reponse1);
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }

        // =========================================================================
        // QUESTION 2 : Conseil pratique avec parapluie
        // =========================================================================
        System.out.println("\n2. " + "=".repeat(50));
        String question2 = "J'ai prévu d'aller aujourd'hui à TAZA. Est-ce que tu me conseilles de prendre un parapluie ?";
        System.out.println("Question : " + question2);

        try {
            String reponse2 = assistant.chat(question2);
            System.out.println("Réponse : " + reponse2);
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }

        // =========================================================================
        // QUESTION 3 : Ville qui n'existe pas
        // =========================================================================
        System.out.println("\n3. " + "=".repeat(50));
        String question3 = "quel temps fait-il à fatimacity?";
        System.out.println("Question : " + question3);

        try {
            String reponse3 = assistant.chat(question3);
            System.out.println("Réponse : " + reponse3);
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }

        // =========================================================================
        // QUESTION 4 : Question hors météo
        // =========================================================================
        System.out.println("\n4. " + "=".repeat(50));
        String question4 = "quels sont les ingrédients d'un cake au chocolat ?";
        System.out.println("Question : " + question4);

        try {
            String reponse4 = assistant.chat(question4);
            System.out.println("Réponse : " + reponse4);
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
}