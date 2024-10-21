class c12626919 {

    public void xtestFile1() throws RuntimeException {
        InputStream inputStream = new FileInputStream(IOTest.FILE);
        OutputStream outputStream = new FileOutputStream("C:/Temp/testFile1.mp4");
        IOUtils.copy(inputStream, outputStream);
        inputStream.close();
        outputStream.close();
    }
}
