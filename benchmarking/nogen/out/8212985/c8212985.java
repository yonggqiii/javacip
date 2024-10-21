class c8212985 {

    private void writeCard() {
        try {
            new URL(JavaCIPUnknownScope.createURLStringExistRESTGetXQuery("update value //scheda[cata = \"" + JavaCIPUnknownScope.cata + "\"] with " + "\"replaced from /schede/scheda-... by jEpi-Scheda-Applet\"")).openStream().close();
            String urlString = "http://" + JavaCIPUnknownScope.server + "/exist/rest/db/schede/" + "scheda-" + JavaCIPUnknownScope.cata + ".xml";
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(urlString).openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("PUT");
            OutputStream outputStream = httpURLConnection.getOutputStream();
            JavaCIPUnknownScope.uiSchedaXml.write(outputStream);
            outputStream.close();
            httpURLConnection.getInputStream().close();
            httpURLConnection.disconnect();
        } catch (MalformedURLRuntimeException e) {
            System.out.println(e);
        } catch (IORuntimeException e) {
            System.out.println(e);
        }
    }
}
