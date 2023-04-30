class c129856 {

    public String getScenarioData(String urlForSalesData) throws IOException, Exception {
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        DataInputStream input = null;
        StringBuffer sBuf = new StringBuffer();
        Proxy proxy;
        if (JavaCIPUnknownScope.httpWebProxyServer != null && Integer.toString(JavaCIPUnknownScope.httpWebProxyPort) != null) {
            SocketAddress address = new InetSocketAddress(JavaCIPUnknownScope.httpWebProxyServer, JavaCIPUnknownScope.httpWebProxyPort);
            proxy = new Proxy(Proxy.Type.HTTP, address);
        } else {
            proxy = null;
        }
        proxy = null;
        URL url;
        try {
            url = new URL(urlForSalesData);
            HttpURLConnection httpUrlConnection;
            if (proxy != null)
                httpUrlConnection = (HttpURLConnection) url.openConnection(proxy);
            else
                httpUrlConnection = (HttpURLConnection) url.openConnection();
            httpUrlConnection.setDoInput(true);
            httpUrlConnection.setRequestMethod("GET");
            String name = JavaCIPUnknownScope.rb.getString("WRAP_NAME");
            String password = JavaCIPUnknownScope.rb.getString("WRAP_PASSWORD");
            Credentials simpleAuthCrentials = new Credentials(TOKEN_TYPE.SimpleApiAuthToken, name, password);
            ACSTokenProvider tokenProvider = new ACSTokenProvider(JavaCIPUnknownScope.httpWebProxyServer, JavaCIPUnknownScope.httpWebProxyPort, simpleAuthCrentials);
            String requestUriStr1 = "https://" + JavaCIPUnknownScope.solutionName + "." + JavaCIPUnknownScope.acmHostName + "/" + JavaCIPUnknownScope.serviceName;
            String appliesTo1 = JavaCIPUnknownScope.rb.getString("SIMPLEAPI_APPLIES_TO");
            String token = tokenProvider.getACSToken(requestUriStr1, appliesTo1);
            httpUrlConnection.addRequestProperty("token", "WRAPv0.9 " + token);
            httpUrlConnection.addRequestProperty("solutionName", JavaCIPUnknownScope.solutionName);
            httpUrlConnection.connect();
            if (httpUrlConnection.getResponseCode() == HttpServletResponse.SC_UNAUTHORIZED) {
                List<TestSalesOrderService> salesOrderServiceBean = new ArrayList<TestSalesOrderService>();
                TestSalesOrderService response = new TestSalesOrderService();
                response.setResponseCode(HttpServletResponse.SC_UNAUTHORIZED);
                response.setResponseMessage(httpUrlConnection.getResponseMessage());
                salesOrderServiceBean.add(response);
            }
            inputStream = httpUrlConnection.getInputStream();
            input = new DataInputStream(inputStream);
            bufferedReader = new BufferedReader(new InputStreamReader(input));
            String str;
            while (null != ((str = bufferedReader.readLine()))) {
                sBuf.append(str);
            }
            String responseString = sBuf.toString();
            return responseString;
        } catch (MalformedURLException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }
    }
}
