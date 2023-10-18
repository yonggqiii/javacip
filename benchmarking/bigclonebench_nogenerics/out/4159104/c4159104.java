class c4159104 {

    public String GetUserPage(String User, int pagetocrawl) {
        int page = pagetocrawl;
        URL url;
        String line, finalstring;
        StringBuffer buffer = new StringBuffer();
        JavaCIPUnknownScope.setStatus("Start moling....");
        JavaCIPUnknownScope.startTimer();
        try {
            url = new URL(JavaCIPUnknownScope.HTMLuserpage + User + "?setcount=100&page=" + page);
            HttpURLConnection connect = (HttpURLConnection) url.openConnection();
            connect.addRequestProperty("User-Agent", JavaCIPUnknownScope.userAgent);
            System.out.println("moling: page " + page + " of " + User);
            BufferedReader input = new BufferedReader(new InputStreamReader(connect.getInputStream()));
            while ((line = input.readLine()) != null) {
                buffer.append(line);
                buffer.append("\n");
            }
            input.close();
            connect.disconnect();
            JavaCIPUnknownScope.stopTimer();
            JavaCIPUnknownScope.setStatus("Dauer : " + JavaCIPUnknownScope.dauerMs() + " ms");
            finalstring = buffer.toString();
            return finalstring;
        } catch (MalformedURLRuntimeException e) {
            System.err.println("Bad URL: " + e);
            return null;
        } catch (IORuntimeException io) {
            System.err.println("IORuntimeException: " + io);
            return null;
        }
    }
}
