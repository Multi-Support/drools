package org.drools.persistence.mapdb;

import org.drools.persistence.PersistentSession;
import org.drools.persistence.SessionMarshallingHelper;

public class MapDBSession implements PersistentSession {

	private SessionMarshallingHelper marshallingHelper;
	private byte[] data;
	private Long id;

	@Override
	public void transform() {
		if (marshallingHelper != null) {
			this.data = marshallingHelper.getSnapshot();
		}
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

}
