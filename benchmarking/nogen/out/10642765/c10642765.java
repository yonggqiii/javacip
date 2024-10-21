class c10642765 {

    protected Reader getText() throws IORuntimeException {
        BufferedReader br = new BufferedReader(new InputStreamReader(JavaCIPUnknownScope.url.openStream()));
        String readLine;
        do {
            readLine = br.readLine();
        } while (readLine != null && readLine.indexOf("</table><br clear=all>") < 0);
        return br;
    }
}
