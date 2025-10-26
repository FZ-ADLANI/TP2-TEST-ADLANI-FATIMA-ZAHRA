package ma.emsi.FZA.Tests;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;

public class Test1 {

    public static void main(String[] args) {
        // Récupération de la clé API Gemini depuis les variables d'environnement
        String cle = System.getenv("Gemini-API-Key");

        // Création du modèle Gemini-2.5 flash avec le pattern builder
        ChatModel modele = GoogleAiGeminiChatModel.builder()
                .apiKey(cle)
                .modelName("gemini-2.5-flash")
                .temperature(0.7)
                .build();

       // Exemple de question posée au modèle
        String question = "Dans quel continent se trouve la Jamaïque?";
        String reponse = modele.chat(question);
        // Affichage de la question et de la réponse
        System.out.println("Question -->" + question);
        System.out.println("Réponse : \n " + reponse);

    }
}
