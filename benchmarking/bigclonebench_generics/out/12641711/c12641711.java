class c12641711 {

    private void scanURL(String packagePath, Collection<String> componentClassNames, URL url) throws IOException {
        URLConnection connection = url.openConnection();
        JarFile jarFile;
        if (connection instanceof JarURLConnection) {
            jarFile = ((JarURLConnection) connection).getJarFile();
        } else {
            jarFile = JavaCIPUnknownScope.getAlternativeJarFile(url);
        }
        if (jarFile != null) {
            JavaCIPUnknownScope.scanJarFile(packagePath, componentClassNames, jarFile);
        } else if (JavaCIPUnknownScope.supportsDirStream(url)) {
            Stack<Queued> queue = new Stack<Queued>();
            queue.push(new Queued(url, packagePath));
            while (!queue.isEmpty()) {
                Queued queued = queue.pop();
                JavaCIPUnknownScope.scanDirStream(queued.packagePath, queued.packageURL, componentClassNames, queue);
            }
        } else {
            String packageName = packagePath.replace("/", ".");
            if (packageName.endsWith(".")) {
                packageName = packageName.substring(0, packageName.length() - 1);
            }
            JavaCIPUnknownScope.scanDir(packageName, new File(url.getFile()), componentClassNames);
        }
    }
}
