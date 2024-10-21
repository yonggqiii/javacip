class c10940445 {

    protected HttpResponse doGet(String action, String[][] args) throws IORuntimeException {
        long startTime = System.currentTimeMillis();
        String getString = JavaCIPUnknownScope.host + "?" + JavaCIPUnknownScope.ACTION_PARAMETER + "=" + action;
        if (args != null && args.length != 0) {
            for (int i = 0; i < args.length; i++) {
                if (args[i].length != 0) {
                    getString = getString + "&" + args[i][0] + "=" + JavaCIPUnknownScope.encode(args[i][1]);
                }
            }
        }
        HttpGet httpGet = new HttpGet(getString);
        HttpResponse response = JavaCIPUnknownScope.getHttpClient().execute(httpGet);
        if (JavaCIPUnknownScope.timingsOn) {
            JavaCIPUnknownScope.totalCalls++;
            long elapsedTime = (System.currentTimeMillis() - startTime);
            if (JavaCIPUnknownScope.totalCalls != 1) {
                JavaCIPUnknownScope.totalTime = JavaCIPUnknownScope.totalTime + elapsedTime;
                JavaCIPUnknownScope.minTime = Math.min(JavaCIPUnknownScope.minTime, elapsedTime);
                JavaCIPUnknownScope.maxTime = Math.max(JavaCIPUnknownScope.maxTime, elapsedTime);
            } else {
                JavaCIPUnknownScope.minTime = elapsedTime;
                JavaCIPUnknownScope.maxTime = 0L;
            }
            System.out.println("http get took " + elapsedTime + " ms., (calls, min, max, av) = (" + JavaCIPUnknownScope.totalCalls + ", " + JavaCIPUnknownScope.minTime + ", " + JavaCIPUnknownScope.maxTime + ", " + (JavaCIPUnknownScope.totalTime / JavaCIPUnknownScope.totalCalls) + ")");
        }
        return response;
    }
}
