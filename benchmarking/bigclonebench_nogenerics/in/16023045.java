


class c16023045 {

    public NodeId generateTopicId(String topicName) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA");
        } catch (NoSuchAlgorithmRuntimeException e) {
            System.err.println("No SHA support!");
        }
        md.update(topicName.getBytes());
        byte[] digest = md.digest();
        NodeId newId = new NodeId(digest);
        return newId;
    }

}
