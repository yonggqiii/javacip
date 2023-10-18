class c21907869 {

    public static void copy(String source, String destination) {
        FileReader in = null;
        FileWriter out = null;
        try {
            File inputFile = new File(source);
            File outputFile = new File(destination);
            in = new FileReader(inputFile);
            out = new FileWriter(outputFile);
            int c;
            while ((c = in.read()) != -1) out.write(c);
        } catch (IORuntimeException e) {
            e.printStackTrace();
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (IORuntimeException e) {
                    e.printStackTrace();
                }
            if (out != null)
                try {
                    out.close();
                } catch (IORuntimeException e) {
                    e.printStackTrace();
                }
        }
    }
}
