package com.example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.jbpm.runtime.manager.impl.jpa.EntityManagerFactoryManager;
import org.jbpm.services.api.RuntimeDataService;
import org.jbpm.services.api.query.QueryService;
import org.kie.server.services.api.KieServerApplicationComponentsService;
import org.kie.server.services.api.KieServerRegistry;
import org.kie.server.services.api.SupportedTransports;
import org.kie.server.services.jbpm.JbpmKieServerExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueryExtensionComponentsService implements KieServerApplicationComponentsService {
	private static final String OWNER_EXTENSION = JbpmKieServerExtension.EXTENSION_NAME;
	private static final String PU_NAME = "org.jbpm.domain";
	Logger logger = LoggerFactory.getLogger(QueryExtensionComponentsService.class);

	@Override
	public Collection<Object> getAppComponents(String extension, SupportedTransports supportedTransports,
			Object... services) {

		if (!OWNER_EXTENSION.equals(extension)) {
			return Collections.emptyList();
		}

		logger.info("Registering QueryExtensionComponentsService");

		KieServerRegistry kieServerRegistry = null;
		QueryService queryService = null;
		RuntimeDataService runtimeDataService = null;

//		for (Object object : services) {
//			if (RuntimeDataService.class.isAssignableFrom(object.getClass())) {
//				runtimeDataService = (RuntimeDataService) object;
//				continue;
//			}
//
//		}
//			} else if (KieServerRegistry.class.isAssignableFrom(object.getClass())) {
//				kieServerRegistry = (KieServerRegistry) object;
//				continue;
//			}
//		}

		List<Object> components = new ArrayList<Object>(1);
		if (SupportedTransports.REST.equals(supportedTransports)) {
			components.add(new VariablesQuery(EntityManagerFactoryManager.get().getOrCreate(PU_NAME)));
		}
		logger.info("QueryExtensionComponentsService registered");

		return components;
	}
}