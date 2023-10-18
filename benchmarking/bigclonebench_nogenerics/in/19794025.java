


class c19794025 {

    public static int numberofLines(JApplet ja, String filename) {
        int count = 0;
        URL url = null;
        String FileToRead;
        FileToRead = "data/" + filename + ".csv";
        try {
            url = new URL(ja.getCodeBase(), FileToRead);
        } catch (MalformedURLRuntimeException e) {
            System.out.println("Malformed URL ");
            ja.stop();
        }
        System.out.println(url.toString());
        try {
            InputStream in = url.openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            while ((reader.readLine()) != null) {
                count++;
            }
            in.close();
        } catch (IORuntimeException e) {
        }
        return count;
    }

}
