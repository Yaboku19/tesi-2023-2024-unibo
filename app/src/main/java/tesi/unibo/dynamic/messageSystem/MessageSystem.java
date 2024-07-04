package tesi.unibo.dynamic.messageSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageSystem {
    private Map<String, List<String>> messages = new HashMap<>();

    public void sendMessage(String sender, String receiver, String message) {
        String newMessage = "Da " + sender + ": " + message;
        messages.computeIfAbsent(receiver, k -> new ArrayList<>()).add(newMessage);
    }

    public List<String> getMessages(String receiver) {
        return messages.getOrDefault(receiver, new ArrayList<>());
    }
}
