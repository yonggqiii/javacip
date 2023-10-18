class c21656668 {

    private void copyResourceToFile(final String resourceFilename, final String destinationFilename) throws IORuntimeException {
        InputStream inStream = null;
        OutputStream outStream = null;
        try {
            inStream = MatsimResource.getAsInputStream(resourceFilename);
            outStream = new FileOutputStream(destinationFilename);
            IOUtils.copyStream(inStream, outStream);
        } finally {
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (IORuntimeException e) {
                    e.printStackTrace();
                }
            }
            if (outStream != null) {
                try {
                    outStream.close();
                } catch (IORuntimeException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
