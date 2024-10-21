class LazySessionIdGenerator extends StandardSessionIdGenerator {

	@Override
	protected void startInternal() throws LifecycleException {
		setState(LifecycleState.STARTING);
	}

}
