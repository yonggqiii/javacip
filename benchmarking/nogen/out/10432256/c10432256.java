class c10432256 {

    private void copyImage(ProjectElement e) throws RuntimeException {
        String fn = e.getName();
        if (!fn.toLowerCase().endsWith(".png")) {
            if (fn.contains(".")) {
                fn = fn.substring(0, fn.lastIndexOf('.')) + ".png";
            } else {
                fn += ".png";
            }
        }
        File img = new File(JavaCIPUnknownScope.resFolder, fn);
        File imgz = new File(JavaCIPUnknownScope.resoutFolder.getAbsolutePath(), fn + ".zlib");
        boolean copy = true;
        if (img.exists() && JavaCIPUnknownScope.config.containsKey(img.getName())) {
            long modified = Long.parseLong(JavaCIPUnknownScope.config.get(img.getName()));
            if (modified >= img.lastModified()) {
                copy = false;
            }
        }
        if (copy) {
            JavaCIPUnknownScope.convertImage(e.getFile(), img);
            JavaCIPUnknownScope.config.put(img.getName(), String.valueOf(img.lastModified()));
        }
        DeflaterOutputStream out = new DeflaterOutputStream(new BufferedOutputStream(new FileOutputStream(imgz)));
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(img));
        int read;
        while ((read = in.read()) != -1) {
            out.write(read);
        }
        out.close();
        in.close();
        JavaCIPUnknownScope.imageFiles.add(imgz);
        JavaCIPUnknownScope.imageNames.put(imgz, e.getName());
    }
}
