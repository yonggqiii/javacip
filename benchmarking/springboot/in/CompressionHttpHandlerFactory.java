import java.util.ArrayList;
import java.util.List;

class CompressionHttpHandlerFactory implements HttpHandlerFactory {

	private final Compression compression;

	CompressionHttpHandlerFactory(Compression compression) {
		this.compression = compression;
	}

	@Override
	public HttpHandler getHandler(HttpHandler next) {
		if (!this.compression.getEnabled()) {
			return next;
		}
		ContentEncodingRepository repository = new ContentEncodingRepository();
		repository.addEncodingHandler("gzip", new GzipEncodingProvider(), 50,
				Predicates.and(getCompressionPredicates(this.compression)));
		return new EncodingHandler(repository).setNext(next);
	}

	private static Predicate[] getCompressionPredicates(Compression compression) {
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(new MaxSizePredicate((int) compression.getMinResponseSize().toBytes()));
		predicates.add(new CompressibleMimeTypePredicate(compression.getMimeTypes()));
		if (compression.getExcludedUserAgents() != null) {
			for (String agent : compression.getExcludedUserAgents()) {
				RequestHeaderAttribute agentHeader = new RequestHeaderAttribute(new HttpString(HttpHeaders.USER_AGENT));
				predicates.add(Predicates.not(Predicates.regex(agentHeader, agent)));
			}
		}
		return predicates.toArray(new Predicate[0]);
	}

	private static class CompressibleMimeTypePredicate implements Predicate {

		private final List<MimeType> mimeTypes;

		CompressibleMimeTypePredicate(String[] mimeTypes) {
			this.mimeTypes = new ArrayList<>(mimeTypes.length);
			for (String mimeTypeString : mimeTypes) {
				this.mimeTypes.add(MimeTypeUtils.parseMimeType(mimeTypeString));
			}
		}

		@Override
		public boolean resolve(HttpServerExchange value) {
			String contentType = value.getResponseHeaders().getFirst(HttpHeaders.CONTENT_TYPE);
			if (contentType != null) {
				try {
					MimeType parsed = MimeTypeUtils.parseMimeType(contentType);
					for (MimeType mimeType : this.mimeTypes) {
						if (mimeType.isCompatibleWith(parsed)) {
							return true;
						}
					}
				}
				catch (InvalidMimeTypeException ex) {
					return false;
				}
			}
			return false;
		}

	}

	private static class MaxSizePredicate implements Predicate {

		private final Predicate maxContentSize;

		MaxSizePredicate(int size) {
			this.maxContentSize = Predicates.requestLargerThan(size);
		}

		@Override
		public boolean resolve(HttpServerExchange value) {
			if (value.getResponseHeaders().contains(Headers.CONTENT_LENGTH)) {
				return this.maxContentSize.resolve(value);
			}
			return true;
		}

	}

}
