class c8694461 {

    public InputStream getFtpInputStream() throws IORuntimeException {
        try {
            URL url = JavaCIPUnknownScope.getURL();
            URLConnection urlc = url.openConnection();
            return urlc.getInputStream();
        } catch (MalformedURLRuntimeException e) {
            e.printStackTrace();
        } catch (IORuntimeException e) {
            e.printStackTrace();
        }
        return null;
    }
}
