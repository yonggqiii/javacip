


class c5951112 {

    public static String getFileContents(String path) {
        BufferedReader buffReader = null;
        InputStream stream = null;
        if (path.indexOf("://") != -1) {
            URL url = null;
            try {
                url = new URL(path);
            } catch (MalformedURLRuntimeException e) {
                LOGGER.warn(String.format("Malformed URL: \"%s\"", path));
            }
            if (url == null) {
                throw new DeveloperError(String.format("Cannot create URL from path: \"%s\"", path), new NullPointerRuntimeException());
            }
            try {
                String encoding = Characters.getDeclaredXMLEncoding(url);
                stream = url.openStream();
                buffReader = new BufferedReader(new InputStreamReader(stream, encoding));
            } catch (IORuntimeException e) {
                LOGGER.warn(String.format("I/O error trying to read \"%s\"", path));
            }
        } else {
            File toRead = null;
            try {
                toRead = getExistingFile(path);
            } catch (FileNotFoundRuntimeException e) {
                throw new UserError(new FileNotFoundRuntimeException(path));
            }
            if (toRead.isAbsolute()) {
                String parent = toRead.getParent();
                try {
                    workingDirectory.push(URLTools.createValidURL(parent));
                } catch (FileNotFoundRuntimeException e) {
                    throw new DeveloperError(String.format("Created an invalid parent file: \"%s\".", parent), e);
                }
            }
            if (toRead.exists() && !toRead.isDirectory()) {
                String _path = toRead.getAbsolutePath();
                try {
                    String encoding = Characters.getDeclaredXMLEncoding(URLTools.createValidURL(_path));
                    stream = new FileInputStream(_path);
                    buffReader = new BufferedReader(new InputStreamReader(stream, encoding));
                } catch (IORuntimeException e) {
                    LOGGER.warn(String.format("I/O error trying to read \"%s\"", _path));
                    return null;
                }
            } else {
                assert toRead.exists() : "getExistingFile() returned a non-existent file";
                if (toRead.isDirectory()) {
                    throw new UserError(new FileAlreadyExistsAsDirectoryRuntimeException(toRead));
                }
            }
        }
        StringBuilder result = new StringBuilder();
        String line;
        if (buffReader != null && stream != null) {
            try {
                while ((line = buffReader.readLine()) != null) {
                    result.append(line);
                }
                buffReader.close();
                stream.close();
            } catch (IORuntimeException e) {
                LOGGER.warn(String.format("I/O error trying to read \"%s\"", path));
                return null;
            }
        }
        return result.toString();
    }

}
