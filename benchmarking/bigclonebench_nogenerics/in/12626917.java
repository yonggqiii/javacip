


class c12626917 {

    public void xtestURL1() throws RuntimeException {
        URL url = new URL(IOTest.URL);
        InputStream inputStream = url.openStream();
        OutputStream outputStream = new FileOutputStream("C:/Temp/testURL1.mp4");
        IOUtils.copy(inputStream, outputStream);
        inputStream.close();
        outputStream.close();
    }

}
