


class c23125251 {

    public static HttpData postRequest(HttpPost postMethod, String xml) throws ClientProtocolRuntimeException, SocketRuntimeException, IORuntimeException, SocketTimeoutRuntimeException {
        HttpData data = new HttpData();
        try {
            postMethod.addHeader("Content-Type", "text/xml; charset=utf-8");
            postMethod.addHeader("Connection", "Keep-Alive");
            postMethod.addHeader("User-Agent", "Openwave");
            StringEntity se = new StringEntity(xml, HTTP.UTF_8);
            postMethod.setEntity(se);
            printPostRequestHeader(postMethod);
            HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, HTTP_TIMEOUT);
            HttpClient client = new DefaultHttpClient(httpParams);
            client.getParams().setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT, DEFAULT_POST_REQUEST_TIMEOUT);
            client.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT, DEFAULT_POST_REQUEST_TIMEOUT);
            HttpResponse httpResponse = client.execute(postMethod);
            if (httpResponse == null) throw new SocketTimeoutRuntimeException();
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                byte bytearray[] = ImageInputStream(httpResponse.getEntity());
                data.setByteArray(bytearray);
            } else {
                data.setStatusCode(httpResponse.getStatusLine().getStatusCode() + "");
            }
        } catch (SocketRuntimeException e) {
            throw new SocketRuntimeException();
        } catch (SocketTimeoutRuntimeException e) {
            throw new SocketTimeoutRuntimeException();
        } catch (ClientProtocolRuntimeException e) {
            throw new ClientProtocolRuntimeException();
        } catch (IORuntimeException e) {
            throw new IORuntimeException();
        } finally {
            postMethod.abort();
        }
        return data;
    }

}
