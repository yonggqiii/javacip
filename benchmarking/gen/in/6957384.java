


class c6957384 {

    public static String sendGetData(URL url, Hashtable<String, String> data) throws IOException {
        StringBuilder outStringBuilder = new StringBuilder();
        if (data != null) {
            Set<Entry<String, String>> x = data.entrySet();
            for (Entry<String, String> entry : x) {
                String k = entry.getKey();
                String v = entry.getValue();
                outStringBuilder.append(URLEncoder.encode(k, "UTF-8"));
                outStringBuilder.append("=");
                outStringBuilder.append(URLEncoder.encode(v, "UTF-8"));
                outStringBuilder.append("&");
            }
        }
        URL innerURL = new URL(url.toString() + "?" + outStringBuilder.toString());
        System.out.println("URL: " + innerURL);
        URLConnection urlConnection = innerURL.openConnection();
        urlConnection.connect();
        StringBuilder inStringBuilder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        Scanner inputScanner = new Scanner(urlConnection.getInputStream());
        while (inputScanner.hasNext()) {
            inStringBuilder.append(inputScanner.next() + " ");
        }
        inputScanner.close();
        reader.close();
        return inStringBuilder.toString();
    }

}
