class c16238515 {

    public void register(URL codeBase, String filePath) throws RuntimeException {
        Properties properties = new Properties();
        URL url = new URL(codeBase + filePath);
        properties.load(url.openStream());
        JavaCIPUnknownScope.initializeContext(codeBase, properties);
    }
}
