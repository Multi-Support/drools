package org.drools.persistence.mapdb;

import org.drools.persistence.PersistenceContext;
import org.drools.persistence.PersistentSession;
import org.drools.persistence.PersistentWorkItem;
import org.mapdb.DB;
import org.mapdb.DBException;
import org.mapdb.Serializer;

public class MapDBPersistenceContext implements PersistenceContext {

	private DB db;
	private Serializer<PersistentSession> sessionSerializer = new PersistentSessionSerializer();
	private Serializer<PersistentWorkItem> workItemSerializer = new PersistentWorkItemSerializer();

	public MapDBPersistenceContext(DB db) {
		this.db = db;
    }
	
	@Override
	public PersistentSession persist(PersistentSession session) {
		long id;
		if (session.getId() == null || session.getId() == -1) {
			id = db.getStore().preallocate();
			session.setId(id);
		} else {
			id = session.getId();
		}
		db.getStore().update(id, session, sessionSerializer);
		return session;
	}

	@Override
	public PersistentSession findSession(Long id) {
		return db.getStore().get(id, sessionSerializer);
	}

	@Override
	public void remove(PersistentSession session) {
		db.getStore().delete(session.getId(), sessionSerializer);
	}

	@Override
	public boolean isOpen() {
		return !db.getStore().isClosed();
	}

	@Override
	public void joinTransaction() {
	}

	@Override
	public void close() {
		//db.getStore().close();
	}

	@Override
	public PersistentWorkItem persist(PersistentWorkItem workItem) {
		long id = db.getStore().preallocate();
		workItem.setId(id);
		return workItem;
	}

	@Override
	public PersistentWorkItem findWorkItem(Long id) {
		try {
			return db.getStore().get(id, workItemSerializer);
		} catch (DBException e) {
			return null;
		}
	}

	@Override
	public void remove(PersistentWorkItem workItem) {
		db.getStore().delete(workItem.getId(), workItemSerializer);
	}

	@Override
	public void lock(PersistentWorkItem workItem) {
	}

	@Override
	public PersistentWorkItem merge(PersistentWorkItem workItem) {
		db.getStore().update(workItem.getId(), workItem, workItemSerializer);
		return workItem;
	}

}
