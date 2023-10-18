class c20991673 {

    public NodeId generateTopicId(String topicName) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA");
        } catch (NoSuchAlgorithmRuntimeException e) {
            System.err.println("No SHA support!");
        }
        if (JavaCIPUnknownScope.m_ready)
            System.out.println("Scribe is ready at" + JavaCIPUnknownScope.getNodeId() + " , topic is " + topicName);
        md.update(topicName.getBytes());
        byte[] digest = md.digest();
        NodeId newId = new NodeId(digest);
        return newId;
    }
}
