class c12626920 {

    public void xtestFile2() throws RuntimeException {
        InputStream inputStream = new FileInputStream(IOTest.FILE);
        OutputStream outputStream = new FileOutputStream("C:/Temp/testFile2.mp4");
        IOUtils.copy(inputStream, outputStream);
        inputStream.close();
        outputStream.close();
    }
}
