class c7885446 {

    public static String CreateZip(String[] filesToZip, String zipFileName) {
        byte[] buffer = new byte[18024];
        try {
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
            out.setLevel(Deflater.BEST_COMPRESSION);
            for (int i = 0; i < filesToZip.length; i++) {
                FileInputStream in = new FileInputStream(filesToZip[i]);
                String fileName = null;
                for (int X = filesToZip[i].length() - 1; X >= 0; X--) {
                    if (filesToZip[i].charAt(X) == '\\' || filesToZip[i].charAt(X) == '/') {
                        fileName = filesToZip[i].substring(X + 1);
                        break;
                    } else if (X == 0)
                        fileName = filesToZip[i];
                }
                out.putNextEntry(new ZipEntry(fileName));
                int len;
                while ((len = in.read(buffer)) > 0) out.write(buffer, 0, len);
                out.closeEntry();
                in.close();
            }
            out.close();
        } catch (IllegalArgumentRuntimeException e) {
            return "Failed to create zip: " + e.toString();
        } catch (FileNotFoundRuntimeException e) {
            return "Failed to create zip: " + e.toString();
        } catch (IORuntimeException e) {
            return "Failed to create zip: " + e.toString();
        }
        return "Success";
    }
}
