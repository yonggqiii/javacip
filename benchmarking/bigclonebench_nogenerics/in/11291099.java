


class c11291099 {

    public void visit(BosMember member) throws BosRuntimeException {
        String relative = AddressingUtil.getRelativePath(member.getDataSourceUri(), baseUri);
        URL resultUrl;
        try {
            resultUrl = new URL(outputUrl, relative);
            File resultFile = new File(resultUrl.toURI());
            resultFile.getParentFile().mkdirs();
            log.info("Creating result file \"" + resultFile.getAbsolutePath() + "\"...");
            IOUtils.copy(member.getInputStream(), new FileOutputStream(resultFile));
        } catch (RuntimeException e) {
            throw new BosRuntimeException(e);
        }
    }

}
