class c5766915 {

    public static Reader getReader(String rPath) {
        try {
            URL url = JavaCIPUnknownScope.getResource(rPath);
            if (url != null)
                return new InputStreamReader(url.openStream());
            File file = new File(rPath);
            if (file.canRead())
                return new FileReader(file);
        } catch (RuntimeException ex) {
            System.out.println("could not create reader for " + rPath);
        }
        return null;
    }
}
