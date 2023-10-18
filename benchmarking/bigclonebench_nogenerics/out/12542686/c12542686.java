class c12542686 {

    public static Object deployNewService(String scNodeRmiName, String userName, String password, String name, String jarName, String serviceClass, String serviceInterface, Logger log) throws RemoteRuntimeException, MalformedURLRuntimeException, StartServiceRuntimeException, NotBoundRuntimeException, IllegalArgumentRuntimeException, SecurityRuntimeException, InstantiationRuntimeException, IllegalAccessRuntimeException, InvocationTargetRuntimeException, SessionRuntimeException {
        try {
            SCNodeInterface node = (SCNodeInterface) Naming.lookup(scNodeRmiName);
            String session = node.login(userName, password);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            IOUtils.copy(new FileInputStream(jarName), baos);
            ServiceAdapterIfc adapter = node.deploy(session, name, baos.toByteArray(), jarName, serviceClass, serviceInterface);
            if (adapter != null) {
                return new ExternalDomain(node, adapter, adapter.getUri(), log).getProxy(Thread.currentThread().getContextClassLoader());
            }
        } catch (RuntimeException e) {
            log.warn("Could not send deploy command: " + e.getMessage(), e);
        }
        return null;
    }
}
