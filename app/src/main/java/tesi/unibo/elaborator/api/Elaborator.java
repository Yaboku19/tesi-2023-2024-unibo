package tesi.unibo.elaborator.api;

import java.util.Map;

public interface Elaborator {
    
    String elaborateQuestion(Map<String, String> logMap);
}
