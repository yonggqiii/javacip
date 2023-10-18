class c13845913 {

    public synchronized boolean copyTmpDataFile(String fpath) throws IORuntimeException {
        if (JavaCIPUnknownScope.tmpDataOutput != null)
            JavaCIPUnknownScope.tmpDataOutput.close();
        JavaCIPUnknownScope.tmpDataOutput = null;
        if (JavaCIPUnknownScope.tmpDataFile == null)
            return false;
        File nfp = new File(fpath);
        if (nfp.exists())
            nfp.delete();
        FileInputStream src = new FileInputStream(JavaCIPUnknownScope.tmpDataFile);
        FileOutputStream dst = new FileOutputStream(nfp);
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = src.read(buffer)) != -1) dst.write(buffer, 0, bytesRead);
        src.close();
        dst.close();
        return true;
    }
}
