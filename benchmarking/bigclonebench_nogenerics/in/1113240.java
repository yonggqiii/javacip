


class c1113240 {

        public RemotePolicyMigrator createRemotePolicyMigrator() {
            return new RemotePolicyMigrator() {

                public String migratePolicy(InputStream stream, String url) throws ResourceMigrationRuntimeException, IORuntimeException {
                    ByteArrayOutputCreator oc = new ByteArrayOutputCreator();
                    IOUtils.copyAndClose(stream, oc.getOutputStream());
                    return oc.getOutputStream().toString();
                }
            };
        }

}
