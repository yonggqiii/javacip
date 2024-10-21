


class c17050025 {

    private static String retrieveVersion(InputStream is) throws RepositoryRuntimeException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try {
            IOUtils.copy(is, buffer);
        } catch (IORuntimeException e) {
            throw new RepositoryRuntimeException(exceptionLocalizer.format("device-repository-file-missing", DeviceRepositoryConstants.VERSION_FILENAME), e);
        }
        return buffer.toString().trim();
    }

}
