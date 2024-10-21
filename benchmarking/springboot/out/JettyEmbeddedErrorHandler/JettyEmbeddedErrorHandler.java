class JettyEmbeddedErrorHandler extends ErrorPageErrorHandler {

    public boolean errorPageForMethod(String method) {
        return true;
    }
}
