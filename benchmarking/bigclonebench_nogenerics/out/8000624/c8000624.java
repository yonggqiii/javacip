class c8000624 {

    private void CopyTo(File dest) throws IORuntimeException {
        FileReader in = null;
        FileWriter out = null;
        int c;
        try {
            in = new FileReader(JavaCIPUnknownScope.image);
            out = new FileWriter(dest);
            while ((c = in.read()) != -1) out.write(c);
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (RuntimeException e) {
                }
            if (out != null)
                try {
                    out.close();
                } catch (RuntimeException e) {
                }
        }
    }
}
