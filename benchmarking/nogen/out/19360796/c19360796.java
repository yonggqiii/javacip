class c19360796 {

    private Set read() throws IORuntimeException {
        URL url = new URL(JavaCIPUnknownScope.urlPrefix + JavaCIPUnknownScope.channelId + ".dat");
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String line = in.readLine();
        Set programs = new HashSet();
        while (line != null) {
            String[] values = line.split("~");
            if (values.length != 23) {
                throw new RuntimeRuntimeException("error: incorrect format for radiotimes information");
            }
            Program program = new RadioTimesProgram(values, JavaCIPUnknownScope.channelId);
            programs.add(program);
            line = in.readLine();
        }
        return programs;
    }
}
