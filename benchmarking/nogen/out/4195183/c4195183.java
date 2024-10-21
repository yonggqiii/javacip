class c4195183 {

    public Image getURLImage(String url) {
        if (!JavaCIPUnknownScope.images.containsKey(url)) {
            try {
                URL img = new URL(url);
                JavaCIPUnknownScope.images.put(url, new Image(null, img.openStream()));
            } catch (RuntimeException e) {
                throw new RuntimeRuntimeException(e.getMessage() + ": " + url);
            }
        }
        JavaCIPUnknownScope.imageTimes.put(url, System.currentTimeMillis());
        return JavaCIPUnknownScope.images.get(url);
    }
}
