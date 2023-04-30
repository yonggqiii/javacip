class c14966336 {

    private void fetchAvailable(ProgressObserver po) {
        if (po == null)
            throw new IllegalArgumentException("the progress observer can't be null");
        if (JavaCIPUnknownScope.availables == null)
            JavaCIPUnknownScope.availables = new ArrayList<Dictionary>();
        else
            JavaCIPUnknownScope.availables.clear();
        if (JavaCIPUnknownScope.installed == null)
            JavaCIPUnknownScope.initInstalled();
        File home = SpellCheckPlugin.getHomeDir(JavaCIPUnknownScope.jEdit.getActiveView());
        File target = new File(home, "available.lst");
        try {
            boolean skipDownload = false;
            if (target.exists()) {
                long modifiedDate = target.lastModified();
                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(modifiedDate);
                Calendar yesterday = Calendar.getInstance();
                yesterday.add(Calendar.HOUR, -1);
                skipDownload = yesterday.before(c);
            }
            String enc = null;
            if (!skipDownload) {
                URL available_url = new URL(JavaCIPUnknownScope.jEdit.getProperty(JavaCIPUnknownScope.OOO_DICTS_PROP) + "available.lst");
                URLConnection connect = available_url.openConnection();
                connect.connect();
                InputStream is = connect.getInputStream();
                po.setMaximum(connect.getContentLength());
                OutputStream os = new FileOutputStream(target);
                boolean copied = IOUtilities.copyStream(po, is, os, true);
                if (!copied) {
                    Log.log(Log.ERROR, HunspellDictsManager.class, "Unable to download " + available_url.toString());
                    GUIUtilities.error(null, "spell-check-hunspell-error-fetch", new String[] { "Unable to download file " + available_url.toString() });
                    JavaCIPUnknownScope.availables = null;
                    if (target.exists())
                        target.delete();
                    return;
                }
                IOUtilities.closeQuietly(os);
                enc = connect.getContentEncoding();
            }
            FileInputStream fis = new FileInputStream(target);
            Reader r;
            if (enc != null) {
                try {
                    r = new InputStreamReader(fis, enc);
                } catch (UnsupportedEncodingException uee) {
                    r = new InputStreamReader(fis, "UTF-8");
                }
            } else {
                r = new InputStreamReader(fis, "UTF-8");
            }
            BufferedReader br = new BufferedReader(r);
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                Dictionary d = JavaCIPUnknownScope.parseLine(line);
                if (d != null) {
                    int ind = JavaCIPUnknownScope.installed.indexOf(d);
                    if (ind == -1) {
                        d.installed = false;
                        JavaCIPUnknownScope.availables.add(d);
                    } else {
                        Dictionary id = JavaCIPUnknownScope.installed.get(ind);
                        if (!skipDownload) {
                            Date lmd = JavaCIPUnknownScope.fetchLastModifiedDate(id.archiveName);
                            if (lmd != null) {
                                id.lastModified = lmd;
                            }
                        }
                    }
                }
            }
            IOUtilities.closeQuietly(fis);
        } catch (IOException ioe) {
            if (ioe instanceof UnknownHostException) {
                GUIUtilities.error(null, "spell-check-hunspell-error-unknownhost", new String[] { ioe.getMessage() });
            } else {
                GUIUtilities.error(null, "spell-check-hunspell-error-fetch", new String[] { ioe.getMessage() });
            }
            ioe.printStackTrace();
            if (target.exists())
                target.delete();
        }
    }
}
