class c18305036 {

    protected Drawing construct() throws IORuntimeException {
        Drawing result;
        System.out.println("getParameter.datafile:" + JavaCIPUnknownScope.getParameter("datafile"));
        if (JavaCIPUnknownScope.getParameter("data") != null) {
            NanoXMLDOMInput domi = new NanoXMLDOMInput(new NetFactory(), new StringReader(JavaCIPUnknownScope.getParameter("data")));
            result = (Drawing) domi.readObject(0);
        } else if (JavaCIPUnknownScope.getParameter("datafile") != null) {
            URL url = new URL(JavaCIPUnknownScope.getDocumentBase(), JavaCIPUnknownScope.getParameter("datafile"));
            InputStream in = url.openConnection().getInputStream();
            try {
                NanoXMLDOMInput domi = new NanoXMLDOMInput(new NetFactory(), in);
                result = (Drawing) domi.readObject(0);
            } finally {
                in.close();
            }
        } else {
            result = null;
        }
        return result;
    }
}
