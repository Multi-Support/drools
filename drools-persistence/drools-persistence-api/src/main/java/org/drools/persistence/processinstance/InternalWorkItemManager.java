package org.drools.persistence.processinstance;

import org.kie.api.runtime.process.WorkItemManager;

public interface InternalWorkItemManager extends WorkItemManager {

	void clearWorkItems();


}
