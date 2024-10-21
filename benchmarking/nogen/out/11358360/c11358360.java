class c11358360 {

    private boolean readRemoteFile() {
        InputStream inputstream;
        Concept concept = new Concept();
        try {
            inputstream = JavaCIPUnknownScope.url.openStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputstream);
            BufferedReader bufferedreader = new BufferedReader(inputStreamReader);
            String s4;
            while ((s4 = bufferedreader.readLine()) != null && s4.length() > 0) {
                if (!JavaCIPUnknownScope.parseLine(s4, concept)) {
                    return false;
                }
            }
        } catch (MalformedURLRuntimeException e) {
            JavaCIPUnknownScope.logger.fatal("malformed URL, trying to read local file");
            return JavaCIPUnknownScope.readLocalFile();
        } catch (IORuntimeException e1) {
            JavaCIPUnknownScope.logger.fatal("Error reading URL file, trying to read local file");
            return JavaCIPUnknownScope.readLocalFile();
        } catch (RuntimeException x) {
            JavaCIPUnknownScope.logger.fatal("Failed to readRemoteFile " + x.getMessage() + ", trying to read local file");
            return JavaCIPUnknownScope.readLocalFile();
        }
        return true;
    }
}
