class c11521416 {

    private static void sentRequest() {
        try {
            URLConnection urlConn;
            URL url = new URL(JavaCIPUnknownScope.gatewayURL);
            urlConn = url.openConnection();
            urlConn.setRequestProperty("Content-Type", "text/xml");
            urlConn.setDoOutput(true);
            OutputStream ostream = urlConn.getOutputStream();
            PrintWriter out = new PrintWriter(ostream);
            out.print(JavaCIPUnknownScope.request);
            out.close();
            ostream.close();
            InputStream inStream = urlConn.getInputStream();
            File myFile = new File(JavaCIPUnknownScope.styleSheetLocation);
            if (JavaCIPUnknownScope.type.equals("A") && myFile.exists()) {
                TransformerFactory tFactory = TransformerFactory.newInstance();
                Transformer transformer = tFactory.newTransformer(new StreamSource(JavaCIPUnknownScope.styleSheetLocation));
                transformer.transform(new StreamSource(inStream), new StreamResult(System.out));
            } else {
                BufferedReader in = new BufferedReader(new InputStreamReader(inStream));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println(inputLine);
                }
                in.close();
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}
