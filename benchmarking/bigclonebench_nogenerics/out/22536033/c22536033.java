class c22536033 {

    private boolean setPayload() throws IORuntimeException {
        if (JavaCIPUnknownScope.Index < JavaCIPUnknownScope.Headers.length) {
            FileOutputStream fos = new FileOutputStream(JavaCIPUnknownScope.Headers[JavaCIPUnknownScope.Index], true);
            FileInputStream fis = new FileInputStream(JavaCIPUnknownScope.HeadlessData);
            FileChannel fic = fis.getChannel();
            FileChannel foc = fos.getChannel();
            fic.transferTo(0, fic.size(), foc);
            fic.close();
            foc.close();
            JavaCIPUnknownScope.setDestination(JavaCIPUnknownScope.Destinations[JavaCIPUnknownScope.Index]);
            JavaCIPUnknownScope.setPayload(JavaCIPUnknownScope.Headers[JavaCIPUnknownScope.Index]);
            JavaCIPUnknownScope.Index++;
            return true;
        }
        return false;
    }
}
