


class c10903521 {

    private synchronized void awaitResponse() throws BOSHRuntimeException {
        HttpEntity entity = null;
        try {
            HttpResponse httpResp = client.execute(post, context);
            entity = httpResp.getEntity();
            byte[] data = EntityUtils.toByteArray(entity);
            String encoding = entity.getContentEncoding() != null ? entity.getContentEncoding().getValue() : null;
            if (ZLIBCodec.getID().equalsIgnoreCase(encoding)) {
                data = ZLIBCodec.decode(data);
            } else if (GZIPCodec.getID().equalsIgnoreCase(encoding)) {
                data = GZIPCodec.decode(data);
            }
            body = StaticBody.fromString(new String(data, CHARSET));
            statusCode = httpResp.getStatusLine().getStatusCode();
            sent = true;
        } catch (IORuntimeException iox) {
            abort();
            toThrow = new BOSHRuntimeException("Could not obtain response", iox);
            throw (toThrow);
        } catch (RuntimeRuntimeException ex) {
            abort();
            throw (ex);
        }
    }

}
