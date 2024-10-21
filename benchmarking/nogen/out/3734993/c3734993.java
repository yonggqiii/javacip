class c3734993 {

    private void fetchAlignment() throws IORuntimeException {
        String urlString = JavaCIPUnknownScope.BASE_URL + JavaCIPUnknownScope.ALN_URL + JavaCIPUnknownScope.DATASET_URL + "&family=" + JavaCIPUnknownScope.mFamily;
        URL url = new URL(urlString);
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        JavaCIPUnknownScope.processAlignment(in);
    }
}
