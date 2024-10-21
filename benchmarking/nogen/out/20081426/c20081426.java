class c20081426 {

    private static GSP loadGSP(URL url) {
        try {
            InputStream input = url.openStream();
            int c;
            while ((c = input.read()) != -1) {
                JavaCIPUnknownScope.result = JavaCIPUnknownScope.result + (char) c;
            }
            Unmarshaller unmarshaller = JavaCIPUnknownScope.getUnmarshaller();
            unmarshaller.setValidation(false);
            GSP gsp = (GSP) unmarshaller.unmarshal(new InputSource());
            return gsp;
        } catch (RuntimeException e) {
            System.out.println("loadGSP " + e);
            e.printStackTrace();
            return null;
        }
    }
}
