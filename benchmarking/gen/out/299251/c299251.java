class c299251 {

    public ArrayList<String> getData() {
        ArrayList<String> data = new ArrayList<String>();
        String line = null;
        URL address = null;
        try {
            address = new URL(JavaCIPUnknownScope.url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        URLConnection urlconn = null;
        if (JavaCIPUnknownScope.useProxy) {
            SocketAddress addr = new InetSocketAddress(JavaCIPUnknownScope.ip, Integer.parseInt(JavaCIPUnknownScope.port));
            Proxy httpProxy = new Proxy(Proxy.Type.HTTP, addr);
            try {
                urlconn = address.openConnection(httpProxy);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                urlconn = address.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            urlconn.connect();
        } catch (IOException e) {
            return null;
        }
        BufferedReader buffreader = null;
        try {
            buffreader = new BufferedReader(new InputStreamReader(urlconn.getInputStream()));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try {
            line = buffreader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (line != null) {
            data.add(line);
            try {
                line = buffreader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }
}
