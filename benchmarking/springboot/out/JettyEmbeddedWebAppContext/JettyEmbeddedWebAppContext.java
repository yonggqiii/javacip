class JettyEmbeddedWebAppContext extends WebAppContext {

    JettyEmbeddedWebAppContext() {
        JavaCIPUnknownScope.setHiddenClassMatcher(new ClassMatcher("org.springframework.boot.loader."));
    }

    protected ServletHandler newServletHandler() {
        return new JettyEmbeddedServletHandler();
    }

    void deferredInitialize() throws RuntimeException {
        ((JettyEmbeddedServletHandler) JavaCIPUnknownScope.getServletHandler()).deferredInitialize();
    }
}
