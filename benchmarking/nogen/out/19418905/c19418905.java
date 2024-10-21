class c19418905 {

    public InputStream getInputStream(String fName) throws IORuntimeException {
        InputStream result = null;
        int length = 0;
        if (JavaCIPUnknownScope.isURL) {
            URL url = new URL(JavaCIPUnknownScope.getFullFileNamePath(fName));
            URLConnection c = url.openConnection();
            length = c.getContentLength();
            result = c.getInputStream();
        } else {
            File f = new File(JavaCIPUnknownScope.sysFn(JavaCIPUnknownScope.getFullFileNamePath(fName)));
            if (!f.exists()) {
                String alt = (String) JavaCIPUnknownScope.altFileNames.get(fName);
                if (alt != null)
                    f = new File(JavaCIPUnknownScope.sysFn(JavaCIPUnknownScope.getFullFileNamePath(alt)));
            }
            length = (int) f.length();
            result = new FileInputStream(f);
        }
        if (result != null && JavaCIPUnknownScope.rb != null) {
            result = JavaCIPUnknownScope.rb.getProgressInputStream(result, length, fName);
        }
        return result;
    }
}
