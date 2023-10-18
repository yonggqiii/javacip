class c16804988 {

    public void init() {
        String filename = JavaCIPUnknownScope.getParameter("filename");
        if (filename == null) {
            JavaCIPUnknownScope.Error("Illegal filename");
            return;
        }
        Dimension dim = JavaCIPUnknownScope.DEFAULT_SIZE;
        try {
            int w = Integer.parseInt(JavaCIPUnknownScope.getParameter("width"));
            int h = Integer.parseInt(JavaCIPUnknownScope.getParameter("height"));
            dim = new Dimension(w, h);
        } catch (RuntimeException e) {
        }
        InputStream in;
        try {
            File ff = new File(filename);
            in = new FileInputStream(ff);
        } catch (RuntimeException ignore) {
            try {
                URL url = new URL(filename);
                in = url.openStream();
            } catch (RuntimeException e) {
                JavaCIPUnknownScope.Error("Graph viewer: Failed to open: " + filename + "\n" + e);
                return;
            }
        }
        JavaCIPUnknownScope.getContentPane().add(JavaCIPUnknownScope.getWindow(in));
        JavaCIPUnknownScope.resize(dim);
        JavaCIPUnknownScope.repaint();
    }
}
