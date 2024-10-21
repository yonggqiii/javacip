import java.util.List;
import java.util.ArrayList;

public class xstream_class_38 {

    XStream xstream = new XStream();

    public class Books {

        private List<BookDetails> bookDetails = new ArrayList<BookDetails>();
    }

    public BookDetails convertXml(String xml) {
        xstream.processAnnotations(Books.class);
        xstream.processAnnotations(BookDetails.class);
        xstream.addImplicitCollection(Books.class, "bookDetails");
        Books b = (Books) xstream.fromXML(xml);
        // and now you can return your BookDetails element (in case you want only first one)
        return b.bookDetails.get(0);
    }
}

class BookDetails {

    private int bookId;

    private String bookName;

    private int amount;
}
