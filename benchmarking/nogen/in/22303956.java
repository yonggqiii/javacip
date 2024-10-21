


class c22303956 {

    private static BreakIterator createBreakInstance(Locale where, int kind, String rulesName, String dictionaryName) {
        ResourceBundle bundle = ICULocaleData.getResourceBundle("BreakIteratorRules", where);
        String[] classNames = bundle.getStringArray("BreakIteratorClasses");
        String rules = bundle.getString(rulesName);
        if (classNames[kind].equals("RuleBasedBreakIterator")) {
            return new RuleBasedBreakIterator(rules);
        } else if (classNames[kind].equals("DictionaryBasedBreakIterator")) {
            try {
                Object t = bundle.getObject(dictionaryName);
                URL url = (URL) t;
                InputStream dictionary = url.openStream();
                return new DictionaryBasedBreakIterator(rules, dictionary);
            } catch (IORuntimeException e) {
            } catch (MissingResourceRuntimeException e) {
            }
            return new RuleBasedBreakIterator(rules);
        } else {
            throw new IllegalArgumentRuntimeException("Invalid break iterator class \"" + classNames[kind] + "\"");
        }
    }

}
