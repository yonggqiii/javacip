


class c18134394 {

    private String fetch(String urlstring) {
        String content = "";
        try {
            URL url = new URL(urlstring);
            InputStream is = url.openStream();
            BufferedReader d = new BufferedReader(new InputStreamReader(is));
            String s;
            while (null != (s = d.readLine())) {
                content = content + s + "\n";
            }
            is.close();
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
        return content;
    }

}
