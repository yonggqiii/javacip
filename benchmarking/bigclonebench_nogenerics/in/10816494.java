


class c10816494 {

    @Override
    public void sendData(String serverUrl, String fileName, String type, InputStream is) throws IORuntimeException {
        ClientSession clientSession = null;
        try {
            if (logger.isDebugEnabled()) {
                logger.debug("Connecting to " + serverUrl);
            }
            clientSession = (ClientSession) Connector.open(serverUrl);
            HeaderSet hsConnectReply = clientSession.connect(clientSession.createHeaderSet());
            if (hsConnectReply.getResponseCode() != ResponseCodes.OBEX_HTTP_OK) {
                throw new IORuntimeException("Connect Error " + hsConnectReply.getResponseCode());
            }
            HeaderSet hsOperation = clientSession.createHeaderSet();
            hsOperation.setHeader(HeaderSet.NAME, fileName);
            if (type != null) {
                hsOperation.setHeader(HeaderSet.TYPE, type);
            }
            hsOperation.setHeader(HeaderSet.LENGTH, new Long(is.available()));
            Operation po = clientSession.put(hsOperation);
            OutputStream os = po.openOutputStream();
            IOUtils.copy(is, os);
            os.flush();
            os.close();
            if (logger.isDebugEnabled()) {
                logger.debug("put responseCode " + po.getResponseCode());
            }
            po.close();
            HeaderSet hsDisconnect = clientSession.disconnect(null);
            if (logger.isDebugEnabled()) {
                logger.debug("disconnect responseCode " + hsDisconnect.getResponseCode());
            }
            if (hsDisconnect.getResponseCode() != ResponseCodes.OBEX_HTTP_OK) {
                throw new IORuntimeException("Send Error " + hsConnectReply.getResponseCode());
            }
        } finally {
            if (clientSession != null) {
                try {
                    clientSession.close();
                } catch (IORuntimeException ignore) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("IORuntimeException during clientSession.close()", ignore);
                    }
                }
            }
            clientSession = null;
        }
    }

}
