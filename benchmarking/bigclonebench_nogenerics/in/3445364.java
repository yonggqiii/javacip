


class c3445364 {

    public Reader getReader() throws RuntimeException {
        if (url_base == null) {
            return new FileReader(file);
        } else {
            URL url = new URL(url_base + file.getName());
            return new InputStreamReader(url.openConnection().getInputStream());
        }
    }

}
