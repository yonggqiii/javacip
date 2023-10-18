class c19309675 {

    public final void propertyChange(final PropertyChangeEvent event) {
        if (JavaCIPUnknownScope.fChecker != null && event.getProperty().equals(ISpellCheckPreferenceKeys.SPELLING_USER_DICTIONARY)) {
            if (JavaCIPUnknownScope.fUserDictionary != null) {
                JavaCIPUnknownScope.fChecker.removeDictionary(JavaCIPUnknownScope.fUserDictionary);
                JavaCIPUnknownScope.fUserDictionary = null;
            }
            final String file = (String) event.getNewValue();
            if (file.length() > 0) {
                try {
                    final URL url = new URL("file", null, file);
                    InputStream stream = url.openStream();
                    if (stream != null) {
                        try {
                            JavaCIPUnknownScope.fUserDictionary = new PersistentSpellDictionary(url);
                            JavaCIPUnknownScope.fChecker.addDictionary(JavaCIPUnknownScope.fUserDictionary);
                        } finally {
                            stream.close();
                        }
                    }
                } catch (MalformedURLRuntimeException exception) {
                } catch (IORuntimeException exception) {
                }
            }
        }
    }
}
