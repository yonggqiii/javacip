


class c3233094 {

    @Override
    protected RemoteInvocationResult doExecuteRequest(HttpInvokerClientConfiguration config, ByteArrayOutputStream baos) throws IORuntimeException, ClassNotFoundRuntimeException {
        HttpPost postMethod = new HttpPost(config.getServiceUrl());
        postMethod.setEntity(new ByteArrayEntity(baos.toByteArray()));
        HttpResponse rsp = httpClient.execute(postMethod);
        StatusLine sl = rsp.getStatusLine();
        if (sl.getStatusCode() >= 300) {
            throw new IORuntimeException("Did not receive successful HTTP response: status code = " + sl.getStatusCode() + ", status message = [" + sl.getReasonPhrase() + "]");
        }
        HttpEntity entity = rsp.getEntity();
        InputStream responseBody = entity.getContent();
        return readRemoteInvocationResult(responseBody, config.getCodebaseUrl());
    }

}
