


class c17029388 {

    public static InputStream getConfigIs(String path, String name) throws ProgrammerRuntimeException, DesignerRuntimeException, UserRuntimeException {
        InputStream is = null;
        try {
            URL url = getConfigResource(new MonadUri(path).append(name));
            if (url != null) {
                is = url.openStream();
            }
        } catch (IORuntimeException e) {
            throw new ProgrammerRuntimeException(e);
        }
        return is;
    }

}
