


class c7655020 {

    public String download(String urlStr) {
        StringBuffer sb = new StringBuffer();
        String line = null;
        BufferedReader buffer = null;
        try {
            url = new URL(urlStr);
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            buffer = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            System.out.println(buffer);
            while ((line = buffer.readLine()) != null) {
                sb.append(line);
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            try {
                buffer.close();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}
