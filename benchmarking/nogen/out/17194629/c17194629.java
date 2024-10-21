class c17194629 {

    public static InputStream openRemoteFile(URL urlParam) throws KRuntimeExceptionClass {
        InputStream result = null;
        try {
            result = urlParam.openStream();
        } catch (IORuntimeException error) {
            String message = new String();
            message = "No se puede abrir el recurso [";
            message += urlParam.toString();
            message += "][";
            message += error.toString();
            message += "]";
            throw new KRuntimeExceptionClass(message, error);
        }
        ;
        return (result);
    }
}
