package tesi.unibo.llmcomunication.impl;

import tesi.unibo.llmcomunication.api.Comunicator;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class ChatGPTComunicator implements Comunicator {
    // URL dell'API di OpenAI
    private static final String URL = "https://api.openai.com/v1/chat/completions";
    // Chiave API fornita da OpenAI
    private final static String KEY = "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";

    @Override
    public String generateCode(final String question) {
        return "";
    }

    public static void chatGPT(String text) throws Exception {
        HttpURLConnection con = (HttpURLConnection) new URL(URL).openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Authorization", "Bearer " + KEY);

        JSONObject data = new JSONObject();
        data.put("model", "text-chatgpt-3.5");
        data.put("prompt", text);
        data.put("max_tokens", 4000);
        data.put("temperature", 1.0);

        con.setDoOutput(true);
        con.getOutputStream().write(data.toString().getBytes());

        // Invia la richiesta e ottieni la risposta
        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            StringBuilder response = new StringBuilder();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
            }

            JSONObject jsonResponse = new JSONObject(response.toString());
            String output = jsonResponse.getJSONArray("choices").getJSONObject(0).getString("text");
            System.out.println(output);
        } else {
            // In caso di errore, stampa il codice di risposta
            System.out.println("Errore nella richiesta. Codice di risposta: " + responseCode);
        }
    }

    public static void main(String[] args) throws Exception {
        chatGPT("Hello, how are you?");
    }
    
}
