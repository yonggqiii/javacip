class c3630280 {

    public byte[] applyTransformationOnURL(String url, int format) throws RemoteRuntimeException {
        byte[] result = null;
        try {
            result = JavaCIPUnknownScope.applyTransformation(new URL(url).openStream(), format);
        } catch (RuntimeException e) {
            JavaCIPUnknownScope.throwServiceRuntimeException(e);
        }
        return result;
    }
}
