


class c8735988 {

    public synchronized void readProject(URL url, boolean addMembers) throws IORuntimeException {
        _url = url;
        try {
            readProject(url.openStream(), addMembers);
        } catch (IORuntimeException e) {
            Argo.log.info("Couldn't open InputStream in ArgoParser.load(" + url + ") " + e);
            e.printStackTrace();
            lastLoadMessage = e.toString();
            throw e;
        }
    }

}
