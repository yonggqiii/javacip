class c20514301 {

    List<String> options(String path) throws TwinException {
        try {
            BasicHttpRequest request = new BasicHttpRequest("OPTIONS", JavaCIPUnknownScope.url + path);
            HttpClient client = JavaCIPUnknownScope.getClient();
            HttpResponse response = client.execute(new HttpHost(JavaCIPUnknownScope.url.getHost(), JavaCIPUnknownScope.url.getPort()), request);
            Header hdr = response.getFirstHeader("Allow");
            if (hdr == null || hdr.getValue().isEmpty())
                return Collections.emptyList();
            return Arrays.asList(hdr.getValue().split("\\s*,\\s*"));
        } catch (IOException e) {
            throw TwinError.UnknownError.create("IOException when accessing RC", e);
        }
    }
}
