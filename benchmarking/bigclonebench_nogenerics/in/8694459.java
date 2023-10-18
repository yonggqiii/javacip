


class c8694459 {

    public OutputStream getOutputStream() throws IORuntimeException {
        try {
            URL url = getURL();
            URLConnection urlc = url.openConnection();
            return urlc.getOutputStream();
        } catch (MalformedURLRuntimeException e) {
            e.printStackTrace();
        } catch (IORuntimeException e) {
            e.printStackTrace();
        }
        return null;
    }

}
