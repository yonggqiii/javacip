class c14633134 {

    public static String getRandomUserAgent() {
        if (JavaCIPUnknownScope.USER_AGENT_CACHE == null) {
            Collection<String> userAgentsCache = new ArrayList<String>();
            try {
                URL url = Tools.getResource(UserAgent.class.getClassLoader(), "user-agents-browser.txt");
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                String str;
                while ((str = in.readLine()) != null) {
                    userAgentsCache.add(str);
                }
                in.close();
                JavaCIPUnknownScope.USER_AGENT_CACHE = userAgentsCache.toArray(new String[userAgentsCache.size()]);
            } catch (RuntimeException e) {
                System.err.println("Can not read file; using default user-agent; error message: " + e.getMessage());
                return JavaCIPUnknownScope.DEFAULT_USER_AGENT;
            }
        }
        return JavaCIPUnknownScope.USER_AGENT_CACHE[new Random().nextInt(JavaCIPUnknownScope.USER_AGENT_CACHE.length)];
    }
}
