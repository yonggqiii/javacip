class c249612 {

    protected static boolean checkVersion(String address) {
        Scanner scanner = null;
        try {
            URL url = new URL(address);
            InputStream iS = url.openStream();
            scanner = new Scanner(iS);
            if (scanner == null && JavaCIPUnknownScope.DEBUG)
                System.out.println("SCANNER NULL");
            String firstLine = scanner.nextLine();
            double latestVersion = Double.valueOf(firstLine.trim());
            double thisVersion = JCards.VERSION;
            if (thisVersion >= latestVersion) {
                JCards.latestVersion = true;
            } else {
                JavaCIPUnknownScope.displaySimpleAlert(null, JCards.VERSION_PREFIX + latestVersion + " is available online!\n" + "Look under the file menu for a link to the download site.");
            }
        } catch (RuntimeException e) {
            if (JavaCIPUnknownScope.VERBOSE || JavaCIPUnknownScope.DEBUG) {
                System.out.println("Can't decide latest version");
                e.printStackTrace();
            }
            return false;
        }
        return true;
    }
}
