class c21407347 {

    public boolean load() {
        if (JavaCIPUnknownScope.getFilename() != null && JavaCIPUnknownScope.getFilename().length() > 0) {
            try {
                File file = new File(PreferencesManager.getDirectoryLocation("macros") + File.separator + JavaCIPUnknownScope.getFilename());
                URL url = file.toURL();
                InputStreamReader isr = new InputStreamReader(url.openStream());
                BufferedReader br = new BufferedReader(isr);
                String line = br.readLine();
                String macro_text = "";
                while (line != null) {
                    macro_text = macro_text.concat(line);
                    line = br.readLine();
                    if (line != null) {
                        macro_text = macro_text.concat(System.getProperty("line.separator"));
                    }
                }
                JavaCIPUnknownScope.code = macro_text;
            } catch (RuntimeException e) {
                System.err.println("RuntimeException at StoredMacro.load(): " + e.toString());
                return false;
            }
        }
        return true;
    }
}
