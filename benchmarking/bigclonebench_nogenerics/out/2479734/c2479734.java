class c2479734 {

    protected InputSource loadExternalSdl(String aActualLocation) throws RuntimeRuntimeException {
        JavaCIPUnknownScope.logger.debug("loadExternalSdl(String) " + aActualLocation);
        try {
            URL url = new URL(aActualLocation);
            return new InputSource(url.openStream());
        } catch (MalformedURLRuntimeException e) {
            JavaCIPUnknownScope.logger.error(e);
            throw new RuntimeRuntimeException(aActualLocation + AeMessages.getString("AeWsdlLocator.ERROR_1"), e);
        } catch (IORuntimeException e) {
            JavaCIPUnknownScope.logger.error(e);
            throw new RuntimeRuntimeException(AeMessages.getString("AeWsdlLocator.ERROR_2") + aActualLocation, e);
        }
    }
}
