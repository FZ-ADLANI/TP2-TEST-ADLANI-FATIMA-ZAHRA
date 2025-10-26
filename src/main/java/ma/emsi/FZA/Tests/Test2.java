package ma.emsi.FZA.Tests;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;
import java.util.HashMap;
import java.util.Map;

public class Test2 {

    public static void main(String[] args) {
        // Récupération de la clé API Gemini depuis les variables d'environnement
        String cle = System.getenv("Gemini-API-Key");

        // Création du modèle Gemini-2.5 flash avec le pattern builder
        ChatModel modele = GoogleAiGeminiChatModel.builder()
                .apiKey(cle)
                .modelName("gemini-2.5-flash")
                .temperature(0.7)
                .build();

        // Création du PromptTemplate pour un traducteur
        PromptTemplate promptTemplate = PromptTemplate.from("Traduis le texte suivant en anglais : {{texte}}");

        // Création d'une Map pour les valeurs du template
        Map<String, Object> variables = new HashMap<>();
        variables.put("texte", "Je développe une application IA en utilisant le framework LangChain4J pour l'intégration des modèles de langage");

        // Traduction avec application de la Map au template
        Prompt prompt = promptTemplate.apply(variables);
        String reponse = modele.chat(prompt.text());

        System.out.println("Texte à traduire : " + variables.get("texte"));
        System.out.println(reponse);
    }
}
