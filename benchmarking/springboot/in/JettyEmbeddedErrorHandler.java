class JettyEmbeddedErrorHandler extends ErrorPageErrorHandler {

	@Override
	public boolean errorPageForMethod(String method) {
		return true;
	}

}
