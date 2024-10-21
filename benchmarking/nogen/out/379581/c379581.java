class c379581 {

    public void login(String UID, String PWD, int CTY) throws RuntimeException {
        JavaCIPUnknownScope.sSideURL = JavaCIPUnknownScope.sSideURLCollection[CTY];
        JavaCIPUnknownScope.sUID = UID;
        JavaCIPUnknownScope.sPWD = PWD;
        JavaCIPUnknownScope.iCTY = CTY;
        JavaCIPUnknownScope.sLoginLabel = JavaCIPUnknownScope.getLoginLabel(JavaCIPUnknownScope.sSideURL);
        String sParams = JavaCIPUnknownScope.getLoginParams();
        CookieHandler.setDefault(new ListCookieHandler());
        URL url = new URL(JavaCIPUnknownScope.sSideURL + JavaCIPUnknownScope.sLoginURL);
        URLConnection conn = url.openConnection();
        JavaCIPUnknownScope.setRequestProperties(conn);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(sParams);
        wr.flush();
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line = rd.readLine();
        while (line != null) {
            sb.append(line + "\n");
            line = rd.readLine();
        }
        wr.close();
        rd.close();
        String sPage = sb.toString();
        Pattern p = Pattern.compile(">Dein Penner<");
        Matcher matcher = p.matcher(sPage);
        JavaCIPUnknownScope.LogedIn = matcher.find();
    }
}
