package tesi.unibo.reader.api;

public abstract class Reader {
    private final String testName;
    private final String packageTest;
    private String className = "";

    public Reader(final String pacakageTest, final String testName) {
        this.packageTest = pacakageTest;
        this.testName = testName;
    }

    protected String getTestName() {
        return this.testName;
    }

    protected String getPackageTest() {
        return this.packageTest;
    }

    public String getClassName() {
        return this.className;
    }

    protected void setClassName(final String className) {
        this.className = className;
    }

    protected String addTab(final int count) {
        final StringBuilder toReturn = new StringBuilder();
        for (int j = 0; j < count; j++) {
            toReturn.append("\t");
        }
        return toReturn.toString();
    }

    public abstract String readFromFile(final String url) throws Exception;
}
