class c10903521 {

    private synchronized void awaitResponse() throws BOSHRuntimeException {
        HttpEntity entity = null;
        try {
            HttpResponse httpResp = JavaCIPUnknownScope.client.execute(JavaCIPUnknownScope.post, JavaCIPUnknownScope.context);
            entity = httpResp.getEntity();
            byte[] data = EntityUtils.toByteArray(entity);
            String encoding = entity.getContentEncoding() != null ? entity.getContentEncoding().getValue() : null;
            if (ZLIBCodec.getID().equalsIgnoreCase(encoding)) {
                data = ZLIBCodec.decode(data);
            } else if (GZIPCodec.getID().equalsIgnoreCase(encoding)) {
                data = GZIPCodec.decode(data);
            }
            JavaCIPUnknownScope.body = StaticBody.fromString(new String(data, JavaCIPUnknownScope.CHARSET));
            JavaCIPUnknownScope.statusCode = httpResp.getStatusLine().getStatusCode();
            JavaCIPUnknownScope.sent = true;
        } catch (IORuntimeException iox) {
            JavaCIPUnknownScope.abort();
            JavaCIPUnknownScope.toThrow = new BOSHRuntimeException("Could not obtain response", iox);
            throw (JavaCIPUnknownScope.toThrow);
        } catch (RuntimeRuntimeException ex) {
            JavaCIPUnknownScope.abort();
            throw (ex);
        }
    }
}
