class c10669537 {

    String processURLInput(String inputURL) throws SoaplabRuntimeException {
        try {
            File tmpFile = File.createTempFile("gowlab.", null);
            tmpFile.deleteOnExit();
            Object data = JavaCIPUnknownScope.inputs.get(inputURL);
            URL url = new URL(data.toString());
            if (url.getProtocol().equals("file"))
                JavaCIPUnknownScope.logAndThrow("Trying to get local file '" + url.toString() + "' is not allowed.");
            URLConnection uc = url.openConnection();
            uc.connect();
            InputStream in = uc.getInputStream();
            byte[] buffer = new byte[256];
            DataOutputStream fileout = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(tmpFile)));
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                fileout.write(buffer, 0, bytesRead);
            }
            fileout.close();
            return tmpFile.getAbsolutePath();
        } catch (IORuntimeException e) {
            JavaCIPUnknownScope.logAndThrow("In processURLData: " + e.toString());
        }
        return null;
    }
}
