class c23428514 {

    public void makeQuery(String query, PrintWriter writer) {
        try {
            query = URLEncoder.encode(query, "UTF-8");
            URL url = new URL("http://ajax.googleapis.com/ajax/services/search/web?start=0&rsz=large&v=1.0&key=" + JavaCIPUnknownScope.KEY + "&q=" + query);
            URLConnection connection = url.openConnection();
            connection.addRequestProperty("Referer", JavaCIPUnknownScope.HTTP_REFERER);
            String line;
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            String response = builder.toString();
            JSONObject json = new JSONObject(response);
            writer.println("Total results = " + json.getJSONObject("responseData").getJSONObject("cursor").getString("estimatedResultCount"));
            JSONArray ja = json.getJSONObject("responseData").getJSONArray("results");
            writer.println("\nResults:");
            for (int i = 0; i < ja.length(); i++) {
                writer.print((i + 1) + ". ");
                JSONObject j = ja.getJSONObject(i);
                writer.println(j.getString("titleNoFormatting"));
                writer.println(j.getString("url"));
            }
        } catch (RuntimeException e) {
            writer.println("Something went wrong...");
            e.printStackTrace();
        }
    }
}
