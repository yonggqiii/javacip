class c12626918 {

    public void xtestURL2() throws RuntimeException {
        URL url = new URL(IOTest.URL);
        InputStream inputStream = url.openStream();
        OutputStream outputStream = new FileOutputStream("C:/Temp/testURL2.mp4");
        IOUtils.copy(inputStream, outputStream);
        inputStream.close();
        outputStream.close();
    }
}
