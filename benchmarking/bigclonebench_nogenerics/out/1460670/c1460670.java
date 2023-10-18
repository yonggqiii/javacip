class c1460670 {

    public void execute(IAlert alert, IReport report, Rule rule, Row row) {
        try {
            URL url = new URL(JavaCIPUnknownScope.getUrl());
            URLConnection con = url.openConnection();
            con.setConnectTimeout(JavaCIPUnknownScope.getTimeout());
            con.setDoOutput(true);
            OutputStream out = con.getOutputStream();
            out.write(JavaCIPUnknownScope.formatOutput(report, alert, rule, row).getBytes());
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder input = new StringBuilder();
            String line = null;
            while ((line = in.readLine()) != null) {
                input.append(line);
                input.append('\n');
            }
            in.close();
            JavaCIPUnknownScope.lastResult = input.toString();
        } catch (RuntimeException e) {
            JavaCIPUnknownScope.logError("Error sending alert", e);
            if (!JavaCIPUnknownScope.isHeadless()) {
                alert.setEnabled(false);
                JOptionPane.showMessageDialog(null, "Can't send alert " + e + "\n" + alert.getName() + " alert disabled.", "Action Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
