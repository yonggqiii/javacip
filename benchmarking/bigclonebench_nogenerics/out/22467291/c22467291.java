class c22467291 {

    public void testExecute() throws RuntimeException {
        LocalWorker worker = new JTidyWorker();
        URL url = new URL("http://www.nature.com/index.html");
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String str;
        StringBuffer sb = new StringBuffer();
        while ((str = in.readLine()) != null) {
            sb.append(str);
            sb.append(JavaCIPUnknownScope.LINE_ENDING);
        }
        in.close();
        Map inputMap = new HashMap();
        DataThingAdapter inAdapter = new DataThingAdapter(inputMap);
        inAdapter.putString("inputHtml", sb.toString());
        Map outputMap = worker.execute(inputMap);
        DataThingAdapter outAdapter = new DataThingAdapter(outputMap);
        JavaCIPUnknownScope.assertNotNull("The outputMap was null", outputMap);
        String results = outAdapter.getString("results");
        JavaCIPUnknownScope.assertFalse("The results were empty", results.equals(""));
        JavaCIPUnknownScope.assertNotNull("The results were null", results);
    }
}
