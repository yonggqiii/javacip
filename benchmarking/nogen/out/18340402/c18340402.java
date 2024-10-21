class c18340402 {

    public String tranportRemoteUnitToLocalTempFile(String urlStr) throws UnitTransportRuntimeException {
        URL url = null;
        File tempUnit = null;
        BufferedOutputStream bos = null;
        try {
            url = new URL(urlStr);
        } catch (MalformedURLRuntimeException e1) {
            JavaCIPUnknownScope.logger.error(String.format("The url [%s] is illegal.", urlStr), e1);
            throw new UnitTransportRuntimeException(String.format("The url [%s] is illegal.", urlStr), e1);
        }
        URLConnection con = null;
        BufferedInputStream in = null;
        try {
            con = url.openConnection();
            in = new BufferedInputStream(con.getInputStream());
        } catch (IORuntimeException e) {
            JavaCIPUnknownScope.logger.error(String.format("Can't open url [%s].", urlStr));
            throw new UnitTransportRuntimeException(String.format("Can't open url [%s].", urlStr), e);
        } catch (RuntimeException e) {
            JavaCIPUnknownScope.logger.error(String.format("Unknown error. Maybe miss the username and password in url [%s].", urlStr), e);
            throw new UnitTransportRuntimeException(String.format("Unknown error. Maybe miss the username and password in url [%s].", urlStr), e);
        }
        String unitName = urlStr.substring(urlStr.lastIndexOf('/') + 1);
        try {
            if (!StringUtils.isEmpty(unitName))
                tempUnit = new File(CommonUtil.getTempDir(), unitName);
            else
                tempUnit = new File(CommonUtil.createTempFile());
        } catch (DeployToolRuntimeException e) {
            JavaCIPUnknownScope.logger.error(String.format("Can't get temp file [%s].", tempUnit));
            throw new UnitTransportRuntimeException(String.format("Can't get temp file [%s].", tempUnit), e);
        }
        try {
            bos = new BufferedOutputStream(new FileOutputStream(tempUnit));
            JavaCIPUnknownScope.logger.info(String.format("Use [%s] for ftp unit [%s].", tempUnit, urlStr));
        } catch (FileNotFoundRuntimeException e) {
            JavaCIPUnknownScope.logger.error(String.format("File [%s] don't exist.", tempUnit));
            throw new UnitTransportRuntimeException(String.format("File [%s] don't exist.", tempUnit), e);
        }
        try {
            IOUtils.copy(in, bos);
            bos.flush();
        } catch (IORuntimeException e) {
            JavaCIPUnknownScope.logger.error(String.format("Error when download [%s] to [%s].", urlStr, tempUnit), e);
            throw new UnitTransportRuntimeException(String.format("Error when download [%s] to [%s].", urlStr, tempUnit), e);
        } finally {
            IOUtils.closeQuietly(bos);
            IOUtils.closeQuietly(in);
        }
        JavaCIPUnknownScope.logger.info(String.format("Download unit to [%s].", tempUnit.getAbsolutePath()));
        return tempUnit.getAbsolutePath();
    }
}
