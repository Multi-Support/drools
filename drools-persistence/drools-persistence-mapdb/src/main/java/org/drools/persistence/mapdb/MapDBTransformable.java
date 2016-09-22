package org.drools.persistence.mapdb;

import org.drools.persistence.Transformable;
import org.kie.api.runtime.Environment;
import org.mapdb.DB;

public interface MapDBTransformable extends Transformable {

	String getMapKey();
	
	boolean updateOnMap(DB db);

	void setEnvironment(Environment environment);
}
