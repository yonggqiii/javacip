class c3841509 {

    public StringBuilder get(String q) {
        StringBuilder builder = new StringBuilder();
        try {
            URL url = new URL(q);
            URLConnection urlc = url.openConnection();
            BufferedInputStream buffer = new BufferedInputStream(urlc.getInputStream());
            int byteRead;
            while ((byteRead = buffer.read()) != -1) builder.append((char) byteRead);
            buffer.close();
        } catch (MalformedURLRuntimeException ex) {
            JOptionPane.showMessageDialog(null, "Error " + ex.toString());
        } catch (IORuntimeException ex) {
            JOptionPane.showMessageDialog(null, "Error " + ex.toString());
        }
        return builder;
    }
}