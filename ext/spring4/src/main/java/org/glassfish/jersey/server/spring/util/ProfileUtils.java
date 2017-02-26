package org.glassfish.jersey.server.spring.util;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

public class ProfileUtils {
	private ProfileUtils() {}

	public static boolean isComponentAcceptable(Environment environment, Class<?> component) {
		String[] profileValues = aggregateProfileValues(component.getAnnotationsByType(Profile.class));
		return profileValues.length > 0 && environment.acceptsProfiles(profileValues);
	}

	public static boolean isResourceAcceptable(Environment environment, Method method) {
		Profile[] methodAnnotationsByType = method.getAnnotationsByType(Profile.class);
		Profile[] classAnnotationsByType = method.getDeclaringClass().getAnnotationsByType(Profile.class);
		Set<Profile> profiles = new HashSet<>();
		profiles.addAll(Arrays.asList(methodAnnotationsByType));
		profiles.addAll(Arrays.asList(classAnnotationsByType));
		String[] profileValues = aggregateProfileValues(profiles.toArray(new Profile[profiles.size()]));
		return profileValues.length > 0 && environment.acceptsProfiles(profileValues);
	}

	private static String[] aggregateProfileValues(Profile... profiles) {
		Set<String> aggregatedValues = new HashSet<>();
		for (Profile profile : profiles) {
			aggregatedValues.addAll(Arrays.asList(profile.value()));
		}
		return aggregatedValues.toArray(new String[aggregatedValues.size()]);
	}
}
