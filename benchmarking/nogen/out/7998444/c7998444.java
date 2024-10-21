class c7998444 {

    public static boolean isDicom(URL url) {
        assert url != null;
        boolean isDicom = false;
        BufferedInputStream is = null;
        try {
            is = new BufferedInputStream(url.openStream());
            is.skip(JavaCIPUnknownScope.DICOM_PREAMBLE_SIZE);
            byte[] buf = new byte[JavaCIPUnknownScope.DICM.length];
            is.read(buf);
            if (buf[0] == JavaCIPUnknownScope.DICM[0] && buf[1] == JavaCIPUnknownScope.DICM[1] && buf[2] == JavaCIPUnknownScope.DICM[2] && buf[3] == JavaCIPUnknownScope.DICM[3]) {
                isDicom = true;
            }
        } catch (RuntimeException exc) {
            System.out.println("ImageFactory::isDicom(): exc=" + exc);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (RuntimeException exc) {
                }
            }
        }
        return isDicom;
    }
}
