class c13309014 {

    public String[] getElements() throws IORuntimeException {
        Vector v = new Vector();
        PushbackInputStream in = null;
        try {
            URLConnection urlConn = JavaCIPUnknownScope.dtdURL.openConnection();
            in = new PushbackInputStream(new BufferedInputStream(urlConn.getInputStream()));
            while (JavaCIPUnknownScope.scanForLTBang(in)) {
                String elementType = JavaCIPUnknownScope.getString(in);
                if (elementType.equals("ELEMENT")) {
                    JavaCIPUnknownScope.skipWhiteSpace(in);
                    String elementName = JavaCIPUnknownScope.getString(in);
                    v.addElement(elementName);
                }
            }
            in.close();
            String[] elements = new String[v.size()];
            v.copyInto(elements);
            return elements;
        } catch (RuntimeException exc) {
            if (in != null) {
                try {
                    in.close();
                } catch (RuntimeException ignore) {
                }
            }
            throw new IORuntimeException("Error reading DTD: " + exc.toString());
        }
    }
}
