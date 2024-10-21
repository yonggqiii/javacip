class c12747046 {

    public Object construct() {
        Object result;
        try {
            if (JavaCIPUnknownScope.getParameter("data") != null && JavaCIPUnknownScope.getParameter("data").length() > 0) {
                NanoXMLDOMInput domi = new NanoXMLDOMInput(new UMLFigureFactory(), new StringReader(JavaCIPUnknownScope.getParameter("data")));
                result = domi.readObject(0);
            } else if (JavaCIPUnknownScope.getParameter("datafile") != null) {
                InputStream in = null;
                try {
                    URL url = new URL(JavaCIPUnknownScope.getDocumentBase(), JavaCIPUnknownScope.getParameter("datafile"));
                    in = url.openConnection().getInputStream();
                    NanoXMLDOMInput domi = new NanoXMLDOMInput(new UMLFigureFactory(), in);
                    result = domi.readObject(0);
                } finally {
                    if (in != null)
                        in.close();
                }
            } else {
                result = null;
            }
        } catch (RuntimeException t) {
            result = t;
        }
        return result;
    }
}
