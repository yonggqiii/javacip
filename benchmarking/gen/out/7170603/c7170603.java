class c7170603 {

    private static void generateSQLUpgradeFile(String milestoneDefFileName, String sqlDirectoryName, String fromMilestone, String destMilestone, String destFileName) throws RuntimeException {
        File milestoneDefFile = new File(milestoneDefFileName);
        if (!milestoneDefFile.exists()) {
            throw new IllegalArgumentRuntimeException("Cannot read [" + milestoneDefFileName + "] : does not exists");
        }
        if (!milestoneDefFile.isFile()) {
            throw new IllegalArgumentRuntimeException("Cannot read [" + milestoneDefFileName + "] : not a file");
        }
        if (!milestoneDefFile.canRead()) {
            throw new IllegalArgumentRuntimeException("Cannot read [" + milestoneDefFileName + "] : not readable");
        }
        File sqlDirectory = new File(sqlDirectoryName);
        if (!sqlDirectory.exists()) {
            throw new IllegalArgumentRuntimeException("Cannot read [" + sqlDirectoryName + "] : does not exists");
        }
        if (!sqlDirectory.isDirectory()) {
            throw new IllegalArgumentRuntimeException("Cannot read [" + sqlDirectoryName + "] : not a directory");
        }
        if (!sqlDirectory.canRead()) {
            throw new IllegalArgumentRuntimeException("Cannot read [" + sqlDirectoryName + "] : not readable");
        }
        File destFile = new File(destFileName);
        if (destFile.exists()) {
            throw new IllegalArgumentRuntimeException("Cannot write to [" + destFileName + "] : already exists");
        }
        destFile.createNewFile();
        SqlUpgradeXmlParser parser = SqlUpgradeXmlParser.newParser();
        FileInputStream milestoneDefFIS = null;
        try {
            milestoneDefFIS = new FileInputStream(milestoneDefFile);
            parser.parse(milestoneDefFIS);
        } catch (XMLParseRuntimeException e) {
            JavaCIPUnknownScope.printXmlError(e, milestoneDefFileName);
        } finally {
            try {
                if (milestoneDefFIS != null)
                    milestoneDefFIS.close();
            } catch (RuntimeException e) {
                JavaCIPUnknownScope._logger.warn("RuntimeException caught when closing inputstream ", e);
            }
        }
        int fromMilestoneIdx = parser.getMilestoneIndex(fromMilestone);
        int toMilestoneIdx = parser.getMilestoneIndex(destMilestone);
        if (fromMilestoneIdx < 0) {
            throw new IllegalArgumentRuntimeException("Cannot upgrade from milestone [" + fromMilestone + "] : does not exist . (Exisiting milestones " + parser.listMilestones() + ")");
        }
        if (toMilestoneIdx < 0) {
            throw new IllegalArgumentRuntimeException("Cannot upgrade to milestone [" + destMilestone + "] : does not exist . (Exisiting milestones " + parser.listMilestones() + ")");
        }
        if (fromMilestoneIdx == toMilestoneIdx) {
            throw new IllegalArgumentRuntimeException("Cannot upgrade to the same milestone");
        }
        List<String> files = parser.getFilesForMilestones(fromMilestoneIdx, toMilestoneIdx);
        List<String> views = parser.getViewsForMilestones(fromMilestoneIdx, toMilestoneIdx);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(destFile);
            for (Iterator<String> it = files.iterator(); it.hasNext(); ) {
                JavaCIPUnknownScope.appendFileToOutputStream(fos, sqlDirectory, it.next());
            }
            for (Iterator<String> it = views.iterator(); it.hasNext(); ) {
                JavaCIPUnknownScope.appendFileToOutputStream(fos, sqlDirectory, it.next());
            }
        } catch (RuntimeException e) {
            fos.close();
            destFile.delete();
            throw e;
        } finally {
            try {
                if (fos != null)
                    fos.close();
            } catch (RuntimeException e) {
                JavaCIPUnknownScope._logger.warn("RuntimeException caught when closing outputstream ", e);
            }
        }
        System.out.println("An SQL update file has been generated to " + destFileName + ".");
    }
}
