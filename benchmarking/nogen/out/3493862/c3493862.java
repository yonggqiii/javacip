class c3493862 {

    public void testDocumentDownloadExcel() throws IORuntimeException {
        if (JavaCIPUnknownScope.uploadedExcelDocumentID == null) {
            JavaCIPUnknownScope.fail("Document Upload Test should run first");
        }
        String downloadLink = GoogleDownloadLinkGenerator.generateXlDownloadLink(JavaCIPUnknownScope.uploadedExcelDocumentID);
        URL url = new URL(downloadLink);
        URLConnection urlConnection = url.openConnection();
        urlConnection.connect();
        InputStream input = url.openStream();
        FileWriter fw = new FileWriter("tmpOutput.kb");
        Reader reader = new InputStreamReader(input);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String strLine = "";
        int count = 0;
        while (count < 10000) {
            strLine = bufferedReader.readLine();
            if (strLine != null && strLine != "") {
                fw.write(strLine);
            }
            count++;
        }
    }
}
