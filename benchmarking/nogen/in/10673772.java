import java.io.*;


class c10673772 {

    private File extractResource(String resourceName, File destDir) throws IOException {
        File file = new File(destDir, resourceName);
        InputStream in = this.getClass().getResourceAsStream(resourceName);
        try {
            FileOutputStream out = FileUtils.openOutputStream(file);
            try {
                IOUtils.copy(in, out);
            } finally {
                if (out != null) {
                    out.close();
                }
            }
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return file;
    }

}
