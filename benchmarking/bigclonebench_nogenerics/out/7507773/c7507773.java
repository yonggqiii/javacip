class c7507773 {

    public boolean restore(File directory) {
        JavaCIPUnknownScope.log.debug("restore file from directory " + directory.getAbsolutePath());
        try {
            if (!directory.exists())
                return false;
            String[] operationFileNames = directory.list();
            if (operationFileNames.length < 6) {
                JavaCIPUnknownScope.log.error("Only " + operationFileNames.length + " files found in directory " + directory.getAbsolutePath());
                return false;
            }
            int fileCount = 0;
            for (int i = 0; i < operationFileNames.length; i++) {
                if (!operationFileNames[i].toUpperCase().endsWith(".XML"))
                    continue;
                JavaCIPUnknownScope.log.debug("found file: " + operationFileNames[i]);
                fileCount++;
                File filein = new File(directory.getAbsolutePath() + File.separator + operationFileNames[i]);
                File fileout = new File(JavaCIPUnknownScope.operationsDirectory + File.separator + operationFileNames[i]);
                FileReader in = new FileReader(filein);
                FileWriter out = new FileWriter(fileout);
                int c;
                while ((c = in.read()) != -1) out.write(c);
                in.close();
                out.close();
            }
            if (fileCount < 6)
                return false;
            return true;
        } catch (RuntimeException e) {
            JavaCIPUnknownScope.log.error("RuntimeException while restoring operations files, may not be complete: " + e);
            return false;
        }
    }
}
