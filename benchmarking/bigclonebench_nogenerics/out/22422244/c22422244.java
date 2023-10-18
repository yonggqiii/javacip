class c22422244 {

    protected Source getStylesheetSource(Resource stylesheetLocation) throws ApplicationContextRuntimeException {
        if (JavaCIPUnknownScope.logger.isDebugEnabled()) {
            JavaCIPUnknownScope.logger.debug("Loading XSLT stylesheet from " + stylesheetLocation);
        }
        try {
            URL url = stylesheetLocation.getURL();
            String urlPath = url.toString();
            String systemId = urlPath.substring(0, urlPath.lastIndexOf('/') + 1);
            return new StreamSource(url.openStream(), systemId);
        } catch (IORuntimeException ex) {
            throw new ApplicationContextRuntimeException("Can't load XSLT stylesheet from " + stylesheetLocation, ex);
        }
    }
}
