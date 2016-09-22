package org.drools.persistence.mapdb;

import org.drools.persistence.PersistentSession;
import org.drools.persistence.SessionMarshallingHelper;
import org.kie.api.runtime.Environment;
import org.mapdb.BTreeMap;
import org.mapdb.DB;
import org.mapdb.Serializer;

public class MapDBSession implements PersistentSession, MapDBTransformable {
	
	private SessionMarshallingHelper marshallingHelper;
	private byte[] data;
	private Long id;

	@Override
	public void transform() {
		this.data = marshallingHelper.getSnapshot();
	}

	@Override
	public void setEnvironment(Environment env) {
		//do nothing
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public void setMarshallingHelper(SessionMarshallingHelper marshallingHelper) {
		this.marshallingHelper = marshallingHelper;
	}

	@Override
	public byte[] getData() {
		return this.data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	@Override
	public String getMapKey() {
		return "session";
	}

	@Override
	public boolean updateOnMap(DB db) {
		BTreeMap<Long, PersistentSession> map = db.treeMap(
				getMapKey(), 
				Serializer.LONG, 
				new PersistentSessionSerializer()).open();
		map.put(id, this);
		return true;
	}
}
