class c8620255 {

    private void sendMessages() {
        Configuration conf = Configuration.getInstance();
        for (int i = 0; i < JavaCIPUnknownScope.errors.size(); i++) {
            String msg = null;
            synchronized (this) {
                msg = JavaCIPUnknownScope.errors.get(i);
                if (JavaCIPUnknownScope.DEBUG)
                    System.out.println(msg);
                JavaCIPUnknownScope.errors.remove(i);
            }
            if (!conf.getCustomerFeedback())
                continue;
            if (conf.getApproveCustomerFeedback()) {
                ConfirmCustomerFeedback dialog = new ConfirmCustomerFeedback(JOptionPane.getFrameForComponent(SqlTablet.getInstance()), msg);
                if (dialog.getResult() == ConfirmCustomerFeedback.Result.NO)
                    continue;
            }
            try {
                URL url = new URL("http://www.sqltablet.com/beta/bug.php");
                URLConnection urlc = url.openConnection();
                urlc.setDoOutput(true);
                urlc.setDoOutput(true);
                urlc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                DataOutputStream out = new DataOutputStream(urlc.getOutputStream());
                String[] lines = msg.split("\n");
                for (int l = 0; l < lines.length; l++) {
                    String line = (l > 0 ? "&line" : "line") + l + "=";
                    line += URLEncoder.encode(lines[l], "UTF-8");
                    out.write(line.getBytes());
                }
                out.flush();
                out.close();
                BufferedReader in = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    if (JavaCIPUnknownScope.DEBUG)
                        System.out.println("RemoteLogger : " + line + "\n");
                }
                in.close();
            } catch (MalformedURLRuntimeException e) {
                e.printStackTrace();
            } catch (IORuntimeException e) {
                e.printStackTrace();
            }
        }
    }
}
