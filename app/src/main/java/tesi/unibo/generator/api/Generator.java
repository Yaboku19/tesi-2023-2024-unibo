package tesi.unibo.generator.api;

public interface Generator {

    Class<?> generateTest(String data);

    String generateTestFileContent(final String data);
}