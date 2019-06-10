package gzip.firefox.precompressed.bug.demo;

import org.springframework.core.io.Resource;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.resource.AbstractResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolverChain;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class MyEncodedResourceResolver extends AbstractResourceResolver {

    @Override
    protected Resource resolveResourceInternal(@Nullable HttpServletRequest request, String requestPath,
                                               List<? extends Resource> locations, ResourceResolverChain chain) {
        requestPath = requestPath + ".gz";

        Resource resource = chain.resolveResource(request, requestPath, locations);

        Resource encoded = new MyEncodedResource(resource, "gzip");
        if (encoded.exists()) {
            return encoded;
        } else {
            throw new RuntimeException("Not found");
        }
    }

    @Override
    protected String resolveUrlPathInternal(String resourceUrlPath,
                                            List<? extends Resource> locations,
                                            ResourceResolverChain chain) {
        return chain.resolveUrlPath(resourceUrlPath, locations);
    }
}