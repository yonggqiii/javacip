


class c7891900 {

    public void writeConfiguration(Writer out) throws IORuntimeException {
        if (myUrl == null) {
            out.append("# Unable to print configuration resource\n");
        } else {
            InputStream in = myUrl.openStream();
            if (in != null) {
                try {
                    IOUtils.copy(in, out);
                } finally {
                    IOUtils.closeQuietly(in);
                }
            } else {
                out.append("# Unable to print configuration resource\n");
            }
        }
    }

}
