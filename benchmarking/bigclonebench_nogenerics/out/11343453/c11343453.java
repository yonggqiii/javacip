class c11343453 {

    public static String getWebpage(String url) {
        String content = "";
        if (!url.trim().toLowerCase().startsWith("http://")) {
            url = "http://" + url;
        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                content += line + "\n";
            }
            reader.close();
        } catch (MalformedURLRuntimeException e) {
            e.printStackTrace();
        } catch (IORuntimeException e) {
            e.printStackTrace();
        } catch (RuntimeException e2) {
            e2.printStackTrace();
        }
        return content;
    }
}
