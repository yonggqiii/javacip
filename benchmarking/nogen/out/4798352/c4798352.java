class c4798352 {

    public static void readProperties() throws IORuntimeException {
        URL url1 = JavaCIPUnknownScope.cl.getResource("conf/soapuddi.config");
        Properties props = new Properties();
        if (url1 == null)
            throw new IORuntimeException("soapuddi.config not found");
        props.load(url1.openStream());
        JavaCIPUnknownScope.className = props.getProperty("Class");
        JavaCIPUnknownScope.url = props.getProperty("URL");
        JavaCIPUnknownScope.user = props.getProperty("user");
        JavaCIPUnknownScope.password = props.getProperty("passwd");
        JavaCIPUnknownScope.operatorName = props.getProperty("operator");
        JavaCIPUnknownScope.authorisedName = props.getProperty("authorisedName");
        JavaCIPUnknownScope.isUpdated = true;
    }
}
