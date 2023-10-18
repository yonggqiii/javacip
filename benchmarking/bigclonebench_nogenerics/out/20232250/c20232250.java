class c20232250 {

    private InputStream sendRequest(SequenceI seq) throws UnsupportedEncodingRuntimeException, IORuntimeException {
        StringBuilder putBuf = new StringBuilder();
        JavaCIPUnknownScope.processOptions(putBuf);
        putBuf.append("INPUT_SEQUENCE=");
        putBuf.append(URLEncoder.encode(">" + seq.getName() + "\n", JavaCIPUnknownScope.ENCODING));
        putBuf.append(URLEncoder.encode(seq.getResidues(), JavaCIPUnknownScope.ENCODING));
        URL url = new URL(JavaCIPUnknownScope.PRIMER_BLAST_URL);
        URLConnection conn = url.openConnection();
        conn.setDoOutput(true);
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(putBuf.toString());
        wr.flush();
        wr.close();
        JavaCIPUnknownScope.apollo.util.IOUtil.informationDialog("Primer-BLAST request sent");
        return conn.getInputStream();
    }
}
