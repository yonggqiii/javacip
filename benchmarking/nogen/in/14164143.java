


class c14164143 {

    protected HttpResponse executeRequest(AbstractHttpRequest request) throws ConnectionRuntimeException, RequestCancelledRuntimeException {
        try {
            HttpResponse response = getHttpClient().execute(request);
            if (!response.is2xxSuccess()) {
                throw new ConnectionRuntimeException();
            }
            return response;
        } catch (IORuntimeException ex) {
            throw new ConnectionRuntimeException();
        } catch (TimeoutRuntimeException ex) {
            throw new ConnectionRuntimeException();
        }
    }

}
