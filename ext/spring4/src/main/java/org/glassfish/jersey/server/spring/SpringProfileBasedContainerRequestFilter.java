package org.glassfish.jersey.server.spring;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.glassfish.hk2.api.ServiceLocator;

@Provider
@PreMatching
public class SpringProfileBasedContainerRequestFilter implements ContainerRequestFilter {

	private final ServiceLocator locator;

	@Inject
	public SpringProfileBasedContainerRequestFilter(final ServiceLocator locator) {
		this.locator = locator;
	}

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		if (!profileActive(requestContext)) {
			requestContext.abortWith(Response.status(Response.Status.NOT_FOUND).build());
		}
	}

	private boolean profileActive(ContainerRequestContext requestContext) {
		// TODO implement me using the service locator
		return true;
	}
}
