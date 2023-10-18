class c3978284 {

    private String getJsonString(String url) throws IORuntimeException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(url).openStream()), 8192);
        String line = reader.readLine();
        String jsonString = "";
        while (line != null) {
            jsonString += line;
            line = reader.readLine();
        }
        jsonString = jsonString.substring(jsonString.indexOf(":") + 1, jsonString.length() - 1);
        return jsonString;
    }
}