package gzip.firefox.precompressed.bug.demo;

import org.springframework.core.io.AbstractResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.resource.HttpResource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

public class MyEncodedResource extends AbstractResource implements HttpResource {

    private final Resource original;

    private final String coding;

    private final Resource encoded;

    public MyEncodedResource(Resource original, String coding) {
        this.original = original;
        this.coding = coding;
        this.encoded = original;
    }

    public MyEncodedResource(Resource original, String coding, String extension) throws IOException {
        this.original = original;
        this.coding = coding;
        this.encoded = original.createRelative(original.getFilename() + extension);
    }


    @Override
    public InputStream getInputStream() throws IOException {
        return this.encoded.getInputStream();
    }

    @Override
    public boolean exists() {
        return this.encoded.exists();
    }

    @Override
    public boolean isReadable() {
        return this.encoded.isReadable();
    }

    @Override
    public boolean isOpen() {
        return this.encoded.isOpen();
    }

    @Override
    public boolean isFile() {
        return this.encoded.isFile();
    }

    @Override
    public URL getURL() throws IOException {
        return this.encoded.getURL();
    }

    @Override
    public URI getURI() throws IOException {
        return this.encoded.getURI();
    }

    @Override
    public File getFile() throws IOException {
        return this.encoded.getFile();
    }

    @Override
    public long contentLength() throws IOException {
        return this.encoded.contentLength();
    }

    @Override
    public long lastModified() throws IOException {
        return this.encoded.lastModified();
    }

    @Override
    public Resource createRelative(String relativePath) throws IOException {
        return this.encoded.createRelative(relativePath);
    }

    @Override
    @Nullable
    public String getFilename() {
        return this.original.getFilename();
    }

    @Override
    public String getDescription() {
        return this.encoded.getDescription();
    }

    @Override
    public HttpHeaders getResponseHeaders() {
        HttpHeaders headers;
        if (this.original instanceof HttpResource) {
            headers = ((HttpResource) this.original).getResponseHeaders();
        } else {
            headers = new HttpHeaders();
        }
        headers.add(HttpHeaders.CONTENT_ENCODING, this.coding);
        headers.add(HttpHeaders.VARY, HttpHeaders.ACCEPT_ENCODING);
        return headers;
    }
}
