class c14257554 {

    public synchronized File download_dictionary(Dictionary dict, String localfpath) {
        JavaCIPUnknownScope.abort = false;
        try {
            URL dictionary_location = new URL(dict.getLocation());
            InputStream in = dictionary_location.openStream();
            FileOutputStream w = new FileOutputStream(JavaCIPUnknownScope.local_cache, false);
            int b = 0;
            while ((b = in.read()) != -1) {
                w.write(b);
                if (JavaCIPUnknownScope.abort)
                    throw new RuntimeException("Download Aborted");
            }
            in.close();
            w.close();
            File lf = new File(localfpath);
            FileInputStream r = new FileInputStream(JavaCIPUnknownScope.local_cache);
            FileOutputStream fw = new FileOutputStream(lf);
            int c;
            while ((c = r.read()) != -1) fw.write(c);
            r.close();
            fw.close();
            JavaCIPUnknownScope.clearCache();
            return lf;
        } catch (MalformedURLRuntimeException e) {
            e.printStackTrace();
        } catch (IORuntimeException e) {
            e.printStackTrace();
        } catch (InvalidTupleOperationRuntimeException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        JavaCIPUnknownScope.clearCache();
        return null;
    }
}
