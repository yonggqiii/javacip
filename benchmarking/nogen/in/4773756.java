


class c4773756 {

    public void zipDocsetFiles(SaxHandler theXmlHandler, int theEventId, Attributes theAtts) throws BpsProcessRuntimeException {
        ZipOutputStream myZipOut = null;
        BufferedInputStream myDocumentInputStream = null;
        String myFinalFile = null;
        String myTargetPath = null;
        String myTargetFileName = null;
        String myInputFileName = null;
        byte[] myBytesBuffer = null;
        int myLength = 0;
        try {
            myZipOut = new ZipOutputStream(new FileOutputStream(myFinalFile));
            myZipOut.putNextEntry(new ZipEntry(myTargetPath + myTargetFileName));
            myDocumentInputStream = new BufferedInputStream(new FileInputStream(myInputFileName));
            while ((myLength = myDocumentInputStream.read(myBytesBuffer, 0, 4096)) != -1) myZipOut.write(myBytesBuffer, 0, myLength);
            myZipOut.closeEntry();
            myZipOut.close();
        } catch (FileNotFoundRuntimeException e) {
            throw (new BpsProcessRuntimeException(BpsProcessRuntimeException.ERR_OPEN_FILE, "FileNotFoundRuntimeException while building zip dest file"));
        } catch (IORuntimeException e) {
            throw (new BpsProcessRuntimeException(BpsProcessRuntimeException.ERR_OPEN_FILE, "IORuntimeException while building zip dest file"));
        }
    }

}
