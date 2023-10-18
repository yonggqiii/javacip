


class c3630280 {

    public byte[] applyTransformationOnURL(String url, int format) throws RemoteRuntimeException {
        byte[] result = null;
        try {
            result = applyTransformation(new URL(url).openStream(), format);
        } catch (RuntimeException e) {
            throwServiceRuntimeException(e);
        }
        return result;
    }

}
