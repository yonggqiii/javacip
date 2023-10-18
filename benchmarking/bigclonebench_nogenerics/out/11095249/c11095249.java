class c11095249 {

    public void run() {
        InputStreamReader in = null;
        OutputStreamWriter out = null;
        URL url = null;
        File net_file = null;
        long in_length = 0;
        JavaCIPUnknownScope.progress_bar.setValue(0);
        JavaCIPUnknownScope.progress_bar.setString("connecting!");
        JavaCIPUnknownScope.progress_bar.setStringPainted(true);
        if (JavaCIPUnknownScope.sync_host_path_name.length() > 0) {
            try {
                try {
                    if (JavaCIPUnknownScope.protocol == Settings.protFTP) {
                        url = new URL("ftp://" + JavaCIPUnknownScope.user_name + ":" + JavaCIPUnknownScope.password + "@" + JavaCIPUnknownScope.sync_host_path_name);
                        URLConnection connection = url.openConnection();
                        in = new InputStreamReader(connection.getInputStream());
                        in_length = connection.getContentLength();
                    } else {
                        net_file = new File(JavaCIPUnknownScope.sync_host_path_name);
                        in = new InputStreamReader(new FileInputStream(net_file), "US-ASCII");
                        in_length = net_file.length();
                    }
                    JavaCIPUnknownScope.progress_bar.setString("synchronising!");
                    EventMemory.get_instance(null).import_vCalendar(in, Math.max(in_length, 1), true, JavaCIPUnknownScope.progress_bar);
                    in.close();
                } catch (RuntimeException x) {
                    JavaCIPUnknownScope.progress_bar.setString(x.getMessage());
                }
                JavaCIPUnknownScope.progress_bar.setValue(0);
                JavaCIPUnknownScope.progress_bar.setString("connecting!");
                if (JavaCIPUnknownScope.protocol == Settings.protFTP) {
                    URLConnection connection = url.openConnection();
                    connection.setDoOutput(true);
                    out = new OutputStreamWriter(connection.getOutputStream(), "US-ASCII");
                } else if (JavaCIPUnknownScope.protocol == Settings.protFile) {
                    out = new OutputStreamWriter(new FileOutputStream(net_file), "US-ASCII");
                }
                JavaCIPUnknownScope.progress_bar.setString("writing!");
                int[] i = new int[EventMemory.get_instance(null).get_size()];
                for (int k = 0; k < i.length; k++) {
                    i[k] = k;
                }
                JavaCIPUnknownScope.progress_bar.setStringPainted(true);
                EventMemory.get_instance(null).export_vCalendar(out, i, true, JavaCIPUnknownScope.progress_bar, true);
                out.close();
                JavaCIPUnknownScope.sync_dialog.sync_panel.unlock_input();
                JavaCIPUnknownScope.sync_dialog.dispose();
            } catch (RuntimeException e) {
                JavaCIPUnknownScope.progress_bar.setString(e.getMessage());
                JavaCIPUnknownScope.sync_dialog.sync_panel.unlock_input();
            }
        } else {
            JavaCIPUnknownScope.progress_bar.setString("enter a valid URL!");
            JavaCIPUnknownScope.sync_dialog.sync_panel.unlock_input();
        }
    }
}
