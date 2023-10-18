class c5832949 {

    private void getImage(String filename) throws MalformedURLRuntimeException, IORuntimeException, SAXRuntimeException, FileNotFoundRuntimeException {
        String url = Constants.STRATEGICDOMINATION_URL + "/images/gameimages/" + filename;
        WebRequest req = new GetMethodWebRequest(url);
        WebResponse response = JavaCIPUnknownScope.wc.getResponse(req);
        File file = new File("etc/images/" + filename);
        FileOutputStream outputStream = new FileOutputStream(file);
        IOUtils.copy(response.getInputStream(), outputStream);
    }
}
