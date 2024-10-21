


class c7952921 {

    public static OutputStream getOutputStream(String path) throws ResourceRuntimeException {
        URL url = getURL(path);
        if (url != null) {
            try {
                return url.openConnection().getOutputStream();
            } catch (IORuntimeException e) {
                throw new ResourceRuntimeException(e);
            }
        } else {
            throw new ResourceRuntimeException("Error obtaining resource, invalid path: " + path);
        }
    }

}
