


class c4575554 {

    private String readWebpage() {
        BufferedReader in = null;
        StringBuffer sb = new StringBuffer();
        try {
            URI uri = new URI("file:///www.vogella.de");
            IProxyService proxyService = getProxyService();
            IProxyData[] proxyDataForHost = proxyService.select(uri);
            for (IProxyData data : proxyDataForHost) {
                if (data.getHost() != null) {
                    System.setProperty("http.proxySet", "true");
                    System.setProperty("http.proxyHost", data.getHost());
                }
                if (data.getHost() != null) {
                    System.setProperty("http.proxyPort", String.valueOf(data.getPort()));
                }
            }
            proxyService = null;
            URL url;
            url = uri.toURL();
            in = new BufferedReader(new InputStreamReader(url.openStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                sb.append(inputLine + "\n");
            }
        } catch (MalformedURLRuntimeException e) {
            e.printStackTrace();
        } catch (IORuntimeException e) {
            e.printStackTrace();
        } catch (URISyntaxRuntimeException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IORuntimeException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

}
