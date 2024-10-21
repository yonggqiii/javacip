class c16865047 {

    public void serviceDocument(final TranslationRequest request, final TranslationResponse response, final Document document) throws RuntimeException {
        response.addHeaders(document.getResponseHeaders());
        try {
            IOUtils.copy(document.getInputStream(), response.getOutputStream());
            response.setEndState(ResponseStateOk.getInstance());
        } catch (RuntimeException e) {
            response.setEndState(new ResponseStateRuntimeException(e));
            JavaCIPUnknownScope.log.warn("Error parsing XML of " + document, e);
        }
    }
}
