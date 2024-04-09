package tesi.unibo.tester.impl;

import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import tesi.unibo.tester.api.Tester;
import java.util.HashMap;
import java.util.Map;

public class TesterJava implements Tester {

    @Override
    public Map<String, String> test(final Class<?> testClass) {
        // Creazione della richiesta di scoperta dei test
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
            .selectors(DiscoverySelectors.selectClass(testClass))
            .build();

        // Creazione del launcher
        Launcher launcher = LauncherFactory.create();

        // Listener per raccogliere i sommari dei test eseguiti
        SummaryGeneratingListener listener = new SummaryGeneratingListener();

        // Esecuzione dei test
        launcher.execute(request, listener);

        // Estrazione dei risultati
        final Map<String, String> logMap = new HashMap<>();
        listener.getSummary().getFailures().forEach(failure -> {
            String testName = failure.getTestIdentifier().getDisplayName();
            String message = failure.getException().getMessage();
            logMap.put(testName, message);
        });

        return logMap;
    }
}