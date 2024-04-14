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
        final LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
            .selectors(DiscoverySelectors.selectClass(testClass))
            .build();
        final Launcher launcher = LauncherFactory.create();
        final SummaryGeneratingListener listener = new SummaryGeneratingListener();
        launcher.execute(request, listener);
        final Map<String, String> logMap = new HashMap<>();
        listener.getSummary().getFailures().forEach(failure -> {
            logMap.put(failure.getTestIdentifier().getDisplayName(), failure.getException().getMessage());
        });
        return logMap;
    }
}