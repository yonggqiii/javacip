


class c10692452 {

    public boolean config(URL url, boolean throwsRuntimeException) throws IllegalArgumentRuntimeException {
        try {
            final MetaRoot conf = UjoManagerXML.getInstance().parseXML(new BufferedInputStream(url.openStream()), MetaRoot.class, this);
            config(conf);
            return true;
        } catch (RuntimeException e) {
            if (throwsRuntimeException) {
                throw new IllegalArgumentRuntimeException("Configuration file is not valid ", e);
            } else {
                return false;
            }
        }
    }

}
