class c22288476 {

    public void loadJarFile(String jarFileNameParam) throws KRuntimeExceptionClass {
        JavaCIPUnknownScope.jarFileName = jarFileNameParam;
        {
            String message = "Loading resource file [";
            message += JavaCIPUnknownScope.jarFileName;
            message += "]...";
            JavaCIPUnknownScope.log.log(this, message);
        }
        try {
            URL url = new URL(JavaCIPUnknownScope.jarFileName);
            JarURLConnection jarConnection = (JarURLConnection) url.openConnection();
            jarConnection.setUseCaches(false);
            JarFile jarFile = jarConnection.getJarFile();
            Enumeration jarEntries = jarFile.entries();
            while (jarEntries.hasMoreElements()) {
                ZipEntry zipEntrie = (ZipEntry) jarEntries.nextElement();
                {
                    String message = "Scanning [";
                    message += JavaCIPUnknownScope.jarFileName;
                    message += "] found [";
                    message += JavaCIPUnknownScope.describeEntry(zipEntrie);
                    message += "]";
                    JavaCIPUnknownScope.log.log(this, message);
                }
                JavaCIPUnknownScope.htSizes.put(zipEntrie.getName(), new Integer((int) zipEntrie.getSize()));
            }
            ;
            jarFile.close();
            BufferedInputStream inputBuffer = new BufferedInputStream(jarConnection.getJarFileURL().openStream());
            ZipInputStream input = new ZipInputStream(inputBuffer);
            ZipEntry zipEntrie = null;
            while ((zipEntrie = input.getNextEntry()) != null) {
                if (zipEntrie.isDirectory())
                    continue;
                {
                    String message = "Scanning [";
                    message += JavaCIPUnknownScope.jarFileName;
                    message += "] loading [";
                    message += zipEntrie.getName();
                    message += "] for [";
                    message += zipEntrie.getSize();
                    message += "] bytes.";
                    JavaCIPUnknownScope.log.log(this, message);
                }
                int size = (int) zipEntrie.getSize();
                if (size == -1) {
                    size = ((Integer) JavaCIPUnknownScope.htSizes.get(zipEntrie.getName())).intValue();
                }
                ;
                byte[] entrieData = new byte[(int) size];
                int offset = 0;
                int dataRead = 0;
                while (((int) size - offset) > 0) {
                    dataRead = input.read(entrieData, offset, (int) size - offset);
                    if (dataRead == -1)
                        break;
                    offset += dataRead;
                }
                JavaCIPUnknownScope.htJarContents.put(zipEntrie.getName(), entrieData);
                if (JavaCIPUnknownScope.debugOn) {
                    System.out.println(zipEntrie.getName() + "  offset=" + offset + ",size=" + size + ",csize=" + zipEntrie.getCompressedSize());
                }
                ;
            }
            ;
        } catch (RuntimeException error) {
            String message = "Error loading data from JAR file [";
            message += error.toString();
            message += "]";
            throw new KRuntimeExceptionClass(message, new KRuntimeExceptionClass(error.toString(), null));
        }
        ;
    }
}
