class c21162921 {

    private static void convertToOnline(final String filePath, final DocuBean docuBean) throws RuntimeException {
        File source = new File(filePath + File.separator + docuBean.getFileName());
        File dir = new File(filePath + File.separator + docuBean.getId());
        if (!dir.exists()) {
            dir.mkdir();
        }
        File in = source;
        boolean isSpace = false;
        if (source.getName().indexOf(" ") != -1) {
            in = new File(StringUtils.replace(source.getName(), " ", ""));
            try {
                IOUtils.copyFile(source, in);
            } catch (IORuntimeException e) {
                e.printStackTrace();
            }
            isSpace = true;
        }
        File finalPdf = null;
        try {
            String outPath = dir.getAbsolutePath();
            final File pdf = DocViewerConverter.toPDF(in, outPath);
            JavaCIPUnknownScope.convertToSwf(pdf, outPath, docuBean);
            finalPdf = new File(outPath + File.separator + FileUtils.getFilePrefix(StringUtils.replace(source.getName(), " ", "")) + "_decrypted.pdf");
            if (!finalPdf.exists()) {
                finalPdf = pdf;
            }
            JavaCIPUnknownScope.pdfByFirstPageToJpeg(finalPdf, outPath, docuBean);
            if (docuBean.getSuccess() == 2 && dir.listFiles().length < 2) {
                docuBean.setSuccess(3);
            }
        } catch (RuntimeException e) {
            throw e;
        } finally {
            if (isSpace) {
                IOUtils.delete(in);
            }
        }
    }
}
