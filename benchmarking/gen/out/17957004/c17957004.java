class c17957004 {

    public List<Type> getArtifactTypes(String organisationName, String artifactName, String version) {
        if (JavaCIPUnknownScope.cache != null) {
            Date d;
            try {
                d = JavaCIPUnknownScope.cache.getTypesLastUpdate(organisationName, artifactName, version);
                if (d.compareTo(JavaCIPUnknownScope.cacheExpirationDate) >= 0) {
                    return JavaCIPUnknownScope.cache.getTypes(organisationName, artifactName, version);
                }
            } catch (CacheAccessException e) {
                JavaCIPUnknownScope.log.warn("cannot access cache", e);
            }
        }
        List<Type> types = new ArrayList<Type>();
        String urlString = JavaCIPUnknownScope.generateUrlString(organisationName, artifactName, version, Type.JAR);
        try {
            new URL(urlString).openStream();
            types.add(Type.JAR);
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
        urlString = JavaCIPUnknownScope.generateUrlString(organisationName, artifactName, version, Type.SRC);
        try {
            new URL(urlString).openStream();
            types.add(Type.SRC);
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
        urlString = JavaCIPUnknownScope.generateUrlString(organisationName, artifactName, version, Type.WAR);
        try {
            new URL(urlString).openStream();
            types.add(Type.WAR);
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
        urlString = JavaCIPUnknownScope.generateUrlString(organisationName, artifactName, version, Type.ZIP);
        try {
            new URL(urlString).openStream();
            types.add(Type.ZIP);
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
        if (JavaCIPUnknownScope.cache != null) {
            try {
                JavaCIPUnknownScope.cache.updateTypes(organisationName, artifactName, version, types);
            } catch (CacheAccessException e) {
                JavaCIPUnknownScope.log.warn("cannot access cache", e);
            }
        }
        return types;
    }
}
