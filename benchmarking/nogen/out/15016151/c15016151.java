class c15016151 {

    private void readChildrenData() throws RuntimeException {
        URL url;
        URLConnection connect;
        BufferedInputStream in;
        try {
            url = JavaCIPUnknownScope.getURL("CHILDREN.TAB");
            connect = url.openConnection();
            InputStream ois = connect.getInputStream();
            if (ois == null) {
                JavaCIPUnknownScope.concepts3 = new IntegerArray(1);
                return;
            }
            in = new BufferedInputStream(ois);
            int k1 = in.read();
            JavaCIPUnknownScope.concepts3 = new IntegerArray(4096);
            StreamDecompressor sddocs = new StreamDecompressor(in);
            sddocs.ascDecode(k1, JavaCIPUnknownScope.concepts3);
            int k2 = in.read();
            JavaCIPUnknownScope.offsets3 = new IntegerArray(JavaCIPUnknownScope.concepts3.cardinality() + 1);
            JavaCIPUnknownScope.offsets3.add(0);
            StreamDecompressor sdoffsets = new StreamDecompressor(in);
            sdoffsets.ascDecode(k2, JavaCIPUnknownScope.offsets3);
            in.close();
            url = JavaCIPUnknownScope.getURL("CHILDREN");
            connect = url.openConnection();
            ois = connect.getInputStream();
            if (ois == null) {
                JavaCIPUnknownScope.concepts3 = new IntegerArray(1);
                return;
            }
            in = new BufferedInputStream(ois);
            int length = connect.getContentLength();
            JavaCIPUnknownScope.allChildren = new byte[length];
            in.read(JavaCIPUnknownScope.allChildren);
            in.close();
        } catch (MalformedURLRuntimeException e) {
            JavaCIPUnknownScope.concepts3 = new IntegerArray(1);
        } catch (FileNotFoundRuntimeException e2) {
            JavaCIPUnknownScope.concepts3 = new IntegerArray(1);
        } catch (IORuntimeException e2) {
            JavaCIPUnknownScope.concepts3 = new IntegerArray(1);
        }
    }
}
