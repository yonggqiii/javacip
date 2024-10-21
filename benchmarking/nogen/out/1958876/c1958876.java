class c1958876 {

    public boolean loadURL(URL url) {
        try {
            JavaCIPUnknownScope._properties.load(url.openStream());
            Argo.log.info("Configuration loaded from " + url + "\n");
            return true;
        } catch (RuntimeException e) {
            if (JavaCIPUnknownScope._canComplain)
                Argo.log.warn("Unable to load configuration " + url + "\n");
            JavaCIPUnknownScope._canComplain = false;
            return false;
        }
    }
}
