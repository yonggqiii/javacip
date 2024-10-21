class c982287 {

    private void writeFile(FileInputStream inFile, FileOutputStream outFile) throws IORuntimeException {
        byte[] buf = new byte[2048];
        int read;
        while ((read = inFile.read(buf)) > 0 && !JavaCIPUnknownScope.stopped) outFile.write(buf, 0, read);
        inFile.close();
    }
}
