


class c6190163 {

    public void xtest7() throws RuntimeException {
        System.out.println("Lowagie");
        FileInputStream inputStream = new FileInputStream("C:/Temp/arquivo.pdf");
        PDFBoxManager manager = new PDFBoxManager();
        InputStream[] images = manager.toImage(inputStream, "jpeg");
        int count = 0;
        for (InputStream image : images) {
            FileOutputStream outputStream = new FileOutputStream("C:/Temp/arquivo_" + count + ".jpg");
            IOUtils.copy(image, outputStream);
            count++;
            outputStream.close();
        }
        inputStream.close();
    }

}
