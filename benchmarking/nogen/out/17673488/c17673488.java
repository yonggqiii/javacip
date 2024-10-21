class c17673488 {

    public String get(String question) {
        try {
            System.out.println(JavaCIPUnknownScope.url + question);
            URL urlonlineserver = new URL(JavaCIPUnknownScope.url + question);
            BufferedReader in = new BufferedReader(new InputStreamReader(urlonlineserver.openStream()));
            String inputLine;
            String returnstring = "";
            while ((inputLine = in.readLine()) != null) returnstring += inputLine;
            in.close();
            return returnstring;
        } catch (IORuntimeException e) {
            return "";
        }
    }
}
