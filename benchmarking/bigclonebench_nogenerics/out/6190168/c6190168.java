class c6190168 {

    public void xtest12() throws RuntimeException {
        PDFManager manager = new ITextManager();
        InputStream pdf = new FileInputStream("/tmp/090237098008f637.pdf");
        InputStream page1 = manager.cut(pdf, 36, 36);
        OutputStream outputStream = new FileOutputStream("/tmp/090237098008f637-1.pdf");
        IOUtils.copy(page1, outputStream);
        outputStream.close();
        pdf.close();
    }
}
