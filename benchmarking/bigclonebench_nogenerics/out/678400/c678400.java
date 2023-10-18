class c678400 {

    public void go() {
        DataOutputStream outStream = null;
        try {
            JavaCIPUnknownScope.connection = (HttpURLConnection) JavaCIPUnknownScope.url.openConnection();
            JavaCIPUnknownScope.connection.setDoOutput(true);
            JavaCIPUnknownScope.connection.setDoInput(true);
            JavaCIPUnknownScope.connection.setRequestMethod("POST");
            JavaCIPUnknownScope.connection.setRequestProperty("Content-Length", new Integer(JavaCIPUnknownScope.sendData.length()).toString());
            JavaCIPUnknownScope.connection.setRequestProperty("Content-type", "text/html");
            JavaCIPUnknownScope.connection.setRequestProperty("User-Agent", "Pago HTTP cartridge");
            outStream = new DataOutputStream(JavaCIPUnknownScope.connection.getOutputStream());
            outStream.writeBytes(JavaCIPUnknownScope.sendData);
            System.out.println(1);
            InputStream is = JavaCIPUnknownScope.connection.getInputStream();
            System.out.println(2);
            JavaCIPUnknownScope.inReader = new BufferedReader(new InputStreamReader(is));
            String result;
            System.out.println(3);
            if ((result = JavaCIPUnknownScope.inReader.readLine()) != null) {
                System.out.println(result);
            }
        } catch (IORuntimeException ioe) {
            ioe.printStackTrace();
            System.exit(0);
        } finally {
            try {
                if (outStream != null)
                    outStream.close();
                if (JavaCIPUnknownScope.inReader != null)
                    JavaCIPUnknownScope.inReader.close();
            } catch (IORuntimeException ioe) {
                System.err.println("Error closing Streams!");
                ioe.printStackTrace();
            }
            JavaCIPUnknownScope.connection.disconnect();
        }
    }
}
