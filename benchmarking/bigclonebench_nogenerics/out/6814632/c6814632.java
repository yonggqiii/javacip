class c6814632 {

    protected void setOuterIP() {
        try {
            URL url = new URL("http://elm-ve.sf.net/ipCheck/ipCheck.cgi");
            InputStreamReader isr = new InputStreamReader(url.openStream());
            BufferedReader br = new BufferedReader(isr);
            String ip = br.readLine();
            ip = ip.trim();
            JavaCIPUnknownScope.bridgeOutIPTF.setText(ip);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}
