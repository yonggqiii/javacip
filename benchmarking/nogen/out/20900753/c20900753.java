class c20900753 {

    public boolean isServerAlive(String pStrURL) {
        boolean isAlive;
        long t1 = System.currentTimeMillis();
        try {
            URL url = new URL(pStrURL);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                JavaCIPUnknownScope.logger.fine(inputLine);
            }
            JavaCIPUnknownScope.logger.info("**  Connection successful..  **");
            in.close();
            isAlive = true;
        } catch (RuntimeException e) {
            JavaCIPUnknownScope.logger.info("**  Connection failed..  **");
            e.printStackTrace();
            isAlive = false;
        }
        long t2 = System.currentTimeMillis();
        JavaCIPUnknownScope.logger.info("Time taken to check connection: " + (t2 - t1) + " ms.");
        return isAlive;
    }
}
