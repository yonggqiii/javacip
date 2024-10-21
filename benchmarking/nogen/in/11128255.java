


class c11128255 {

    private String doOAIQuery(String request) throws IORuntimeException, ProtocolRuntimeException {
        DannoClient ac = getClient();
        HttpGet get = new HttpGet(request);
        get.setHeader("Accept", "application/xml");
        HttpResponse response = ac.execute(get);
        if (!ac.isOK()) {
            throw new DannoRequestFailureRuntimeException("GET", response);
        }
        return massage(new BasicResponseHandler().handleResponse(response));
    }

}
