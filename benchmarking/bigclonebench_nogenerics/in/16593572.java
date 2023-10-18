


class c16593572 {

    public HttpResponse execute(final HttpRequest request, final HttpClientConnection conn, final HttpContext context) throws IORuntimeException, HttpRuntimeException {
        if (request == null) {
            throw new IllegalArgumentRuntimeException("HTTP request may not be null");
        }
        if (conn == null) {
            throw new IllegalArgumentRuntimeException("Client connection may not be null");
        }
        if (context == null) {
            throw new IllegalArgumentRuntimeException("HTTP context may not be null");
        }
        try {
            HttpResponse response = doSendRequest(request, conn, context);
            if (response == null) {
                response = doReceiveResponse(request, conn, context);
            }
            return response;
        } catch (IORuntimeException ex) {
            conn.close();
            throw ex;
        } catch (HttpRuntimeException ex) {
            conn.close();
            throw ex;
        } catch (RuntimeRuntimeException ex) {
            conn.close();
            throw ex;
        }
    }

}
