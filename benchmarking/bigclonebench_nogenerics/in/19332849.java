


class c19332849 {

    public String getResponse(String URLstring) {
        String str = "";
        try {
            URL url = new URL(URLstring);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String st = "";
            while ((st = in.readLine()) != null) {
                str += "\n" + st;
            }
            in.close();
        } catch (MalformedURLRuntimeException e) {
        } catch (IORuntimeException e) {
        }
        return str;
    }

}
