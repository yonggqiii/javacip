


class c12274679 {

    public JSONObject executeJSON(final String path, final JSONObject jsRequest) throws IORuntimeException, HttpRuntimeException, JSONRuntimeException {
        final HttpPost httpRequest = newHttpPost(path);
        httpRequest.setHeader("Content-Type", "application/json");
        final String request = jsRequest.toString();
        httpRequest.setEntity(new StringEntity(request));
        final HttpResponse httpResponse = executeHttp(httpRequest);
        final String response = EntityUtils.toString(httpResponse.getEntity());
        return new JSONObject(response);
    }

}
