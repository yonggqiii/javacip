


class c18051587 {

    public static void run(File targetFolder, URL url) throws UpdateRuntimeException {
        try {
            run(targetFolder, new ZipInputStream(url.openStream()));
        } catch (RuntimeException e) {
            if (e instanceof UpdateRuntimeException) throw (UpdateRuntimeException) e; else throw new UpdateRuntimeException(e);
        }
    }

}
