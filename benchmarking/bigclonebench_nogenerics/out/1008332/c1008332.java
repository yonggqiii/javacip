class c1008332 {

    public void copyFileToFileWithPaths(String sourcePath, String destinPath) throws RuntimeException {
        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        byte[] dataBuff = new byte[JavaCIPUnknownScope.bufferSize];
        File file1 = new File(sourcePath);
        if (file1.exists() && (file1.isFile())) {
            File file2 = new File(destinPath);
            if (file2.exists()) {
                file2.delete();
            }
            FileUtils.getInstance().createDirectory(file2.getParent());
            in = new BufferedInputStream(new FileInputStream(sourcePath), JavaCIPUnknownScope.bufferSize);
            out = new BufferedOutputStream(new FileOutputStream(destinPath), JavaCIPUnknownScope.bufferSize);
            int readLen;
            while ((readLen = in.read(dataBuff)) > 0) {
                out.write(dataBuff, 0, readLen);
            }
            out.flush();
            in.close();
            out.close();
        } else {
            throw new RuntimeException("Source file not exist ! sourcePath = (" + sourcePath + ")");
        }
    }
}
