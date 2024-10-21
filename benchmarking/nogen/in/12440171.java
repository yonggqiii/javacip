


class c12440171 {

    public void writeFile(String resource, InputStream is) throws IORuntimeException {
        File f = prepareFsReferenceAsFile(resource);
        FileOutputStream fos = new FileOutputStream(f);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        try {
            IOUtils.copy(is, bos);
        } finally {
            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(bos);
        }
    }

}
