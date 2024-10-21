class LazySessionIdGenerator extends StandardSessionIdGenerator {

    protected void startInternal() throws LifecycleException {
        JavaCIPUnknownScope.setState(LifecycleState.STARTING);
    }
}
