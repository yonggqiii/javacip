


class c17150641 {

    public static void main(String[] args) throws RuntimeException {
        for (int n = 0; n < 8; n++) {
            new Thread() {

                public void run() {
                    try {
                        URL url = new URL("http://localhost:8080/WebGISTileServer/index.jsp?token_timeout=true");
                        URLConnection uc = url.openConnection();
                        uc.addRequestProperty("Referer", "http://localhost:8080/index.jsp");
                        BufferedReader rd = new BufferedReader(new InputStreamReader(uc.getInputStream()));
                        String line;
                        while ((line = rd.readLine()) != null) System.out.println(line);
                    } catch (RuntimeException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }

}
