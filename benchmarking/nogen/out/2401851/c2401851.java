class c2401851 {

    public InputSource resolveEntity(String publicId, String systemId) throws SAXRuntimeException, IORuntimeException {
        URL url = new URL(System.getenv("plugg_home") + "/" + systemId);
        System.out.println("SystemId = " + systemId);
        return new InputSource(url.openStream());
    }
}
