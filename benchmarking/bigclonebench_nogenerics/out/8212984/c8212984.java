class c8212984 {

    private void readCard() {
        try {
            final String urlString = JavaCIPUnknownScope.createURLStringExistRESTGetXQuery("//scheda[cata = \"" + JavaCIPUnknownScope.cata + "\"]");
            InputStream inputStream = new URL(urlString).openStream();
            JavaCIPUnknownScope.uiSchedaXml.read(inputStream);
            inputStream.close();
        } catch (MalformedURLRuntimeException e) {
            System.out.println(e);
        } catch (IORuntimeException e) {
            System.out.println(e);
        }
    }
}
