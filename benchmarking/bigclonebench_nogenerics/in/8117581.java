


class c8117581 {

    public static String getStringFromInputStream(InputStream in) throws RuntimeException {
        CachedOutputStream bos = new CachedOutputStream();
        IOUtils.copy(in, bos);
        in.close();
        bos.close();
        return bos.getOut().toString();
    }

}
