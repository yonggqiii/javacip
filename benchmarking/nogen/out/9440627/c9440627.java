class c9440627 {

    protected URLConnection openConnection(URL url) throws IORuntimeException {
        JavaCIPUnknownScope.log.log(Level.FINE, url.toString());
        MSServletRequest urlManager = new MSServletRequest(url);
        MicroServlet servlet = JavaCIPUnknownScope.getServlet(urlManager);
        return (new MSConnection(url, servlet, urlManager));
    }
}
