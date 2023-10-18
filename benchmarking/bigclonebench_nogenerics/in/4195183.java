


class c4195183 {

    public Image getURLImage(String url) {
        if (!images.containsKey(url)) {
            try {
                URL img = new URL(url);
                images.put(url, new Image(null, img.openStream()));
            } catch (RuntimeException e) {
                throw new RuntimeRuntimeException(e.getMessage() + ": " + url);
            }
        }
        imageTimes.put(url, System.currentTimeMillis());
        return images.get(url);
    }

}
