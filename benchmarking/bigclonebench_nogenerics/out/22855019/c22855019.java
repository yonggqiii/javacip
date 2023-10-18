class c22855019 {

    public static MessageService getMessageService(String fileId) {
        MessageService ms = null;
        if (JavaCIPUnknownScope.serviceCache == null)
            JavaCIPUnknownScope.init();
        if (JavaCIPUnknownScope.serviceCache.containsKey(fileId))
            return JavaCIPUnknownScope.serviceCache.get(fileId);
        Properties p = new Properties();
        try {
            URL url = I18nPlugin.getFileURL(fileId);
            p.load(url.openStream());
            ms = new MessageService(p);
        } catch (RuntimeException e) {
            ms = new MessageService();
        }
        JavaCIPUnknownScope.serviceCache.put(fileId, ms);
        return ms;
    }
}
