class c15016152 {

    private void readFromDB() throws RuntimeException {
        URL url;
        URLConnection connect;
        BufferedInputStream in = null;
        Schema schema = new Schema(JavaCIPUnknownScope.base, JavaCIPUnknownScope.indexDir, false);
        BtreeDictParameters params = new BtreeDictParameters(schema, "TMAP");
        params.readState();
        JavaCIPUnknownScope.tmap = new BtreeDict(params);
        JavaCIPUnknownScope.readChildrenData();
        url = JavaCIPUnknownScope.getURL("DOCS.TAB");
        connect = url.openConnection();
        in = new BufferedInputStream(connect.getInputStream());
        int k1 = in.read();
        JavaCIPUnknownScope.concepts = new IntegerArray(4096);
        StreamDecompressor sddocs = new StreamDecompressor(in);
        sddocs.ascDecode(k1, JavaCIPUnknownScope.concepts);
        int k2 = in.read();
        JavaCIPUnknownScope.offsets = new IntegerArray(JavaCIPUnknownScope.concepts.cardinality() + 1);
        JavaCIPUnknownScope.offsets.add(0);
        StreamDecompressor sdoffsets = new StreamDecompressor(in);
        sdoffsets.ascDecode(k2, JavaCIPUnknownScope.offsets);
        in.close();
        url = JavaCIPUnknownScope.getURL("DOCS");
        connect = url.openConnection();
        in = new BufferedInputStream(connect.getInputStream());
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        byte[] buff = new byte[512];
        int i = 0;
        while ((i = in.read(buff)) != -1) {
            data.write(buff, 0, i);
        }
        JavaCIPUnknownScope.allLists = data.toByteArray();
        in.close();
        url = JavaCIPUnknownScope.getURL("OFFSETS");
        connect = url.openConnection();
        in = new BufferedInputStream(connect.getInputStream());
        k1 = in.read();
        JavaCIPUnknownScope.documents = new IntegerArray(4096);
        sddocs = new StreamDecompressor(in);
        sddocs.ascDecode(k1, JavaCIPUnknownScope.documents);
        k2 = in.read();
        JavaCIPUnknownScope.offsets2 = new IntegerArray(JavaCIPUnknownScope.documents.cardinality() + 1);
        sdoffsets = new StreamDecompressor(in);
        sdoffsets.ascDecode(k2, JavaCIPUnknownScope.offsets2);
        int k3 = in.read();
        JavaCIPUnknownScope.titles = new IntegerArray(JavaCIPUnknownScope.documents.cardinality());
        StreamDecompressor sdtitles = new StreamDecompressor(in);
        sdtitles.decode(k3, JavaCIPUnknownScope.titles);
        in.close();
        RAFFileFactory factory = RAFFileFactory.create();
        url = JavaCIPUnknownScope.getURL("POSITIONS");
        JavaCIPUnknownScope.positionsFile = factory.get(url, false);
    }
}
