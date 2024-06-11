package tesi.unibo.llmcomunication.impl;

import tesi.unibo.llmcomunication.api.Comunicator;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class ChatGPTComunicator implements Comunicator {
    private static final String URL = "https://api.openai.com/v1/chat/completions";
    private static final String KEY = "";

    @Override
    public String generateCode(final String question) {
        try {
            final HttpURLConnection con = (HttpURLConnection) new URL(URL).openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Authorization", "Bearer " + KEY);

            final JSONObject data = new JSONObject();
            data.put("model", "gpt-3.5-turbo");
            data.put("messages", getMessages(question));
            data.put("max_tokens", 4000);
            data.put("temperature", 1.0);
            con.setDoOutput(true);
            con.getOutputStream().write(data.toString().getBytes());

            // Invia la richiesta e ottieni la risposta
            final int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                final StringBuilder response = new StringBuilder();
                try (final BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                }

                final JSONObject jsonResponse = new JSONObject(response.toString());
                return jsonResponse
                    .getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content");
            } else {
                // In caso di errore, stampa il codice di risposta
                return "Errore nella richiesta. Codice di risposta: " + responseCode;
            }
        } catch (IOException e) {
            return "errore IOExcepion";
        }
    }

    private JSONArray getMessages(final String question) {
        final JSONArray messages = new JSONArray();
        final JSONObject message = new JSONObject();
        message.put("role", "user");
        message.put("content", question);
        messages.put(message);
        return messages;
    }
}
