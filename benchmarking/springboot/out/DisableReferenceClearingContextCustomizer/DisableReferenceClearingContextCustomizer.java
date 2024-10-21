class DisableReferenceClearingContextCustomizer implements TomcatContextCustomizer {

    public void customize(Context context) {
        if (!(context instanceof StandardContext)) {
            return;
        }
        StandardContext standardContext = (StandardContext) context;
        standardContext.setClearReferencesObjectStreamClassCaches(false);
        standardContext.setClearReferencesRmiTargets(false);
        standardContext.setClearReferencesThreadLocals(false);
    }
}
