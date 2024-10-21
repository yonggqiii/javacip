import java.io.File;
import java.util.Collection;

public interface ConfigurableUndertowWebServerFactory extends ConfigurableWebServerFactory {

    void setBuilderCustomizers(Collection<? extends UndertowBuilderCustomizer> customizers);

    void addBuilderCustomizers(UndertowBuilderCustomizer... customizers);

    void setBufferSize(Integer bufferSize);

    void setIoThreads(Integer ioThreads);

    void setWorkerThreads(Integer workerThreads);

    void setUseDirectBuffers(Boolean useDirectBuffers);

    void setAccessLogDirectory(File accessLogDirectory);

    void setAccessLogPattern(String accessLogPattern);

    void setAccessLogPrefix(String accessLogPrefix);

    void setAccessLogSuffix(String accessLogSuffix);

    void setAccessLogEnabled(boolean accessLogEnabled);

    void setAccessLogRotate(boolean accessLogRotate);

    void setUseForwardHeaders(boolean useForwardHeaders);
}
