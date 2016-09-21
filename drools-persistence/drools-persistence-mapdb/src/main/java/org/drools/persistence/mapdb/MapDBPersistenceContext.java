package org.drools.persistence.mapdb;

import org.drools.persistence.PersistenceContext;
import org.drools.persistence.PersistentSession;
import org.drools.persistence.PersistentWorkItem;
import org.mapdb.BTreeMap;
import org.mapdb.DB;
import org.mapdb.serializer.GroupSerializer;
import org.mapdb.serializer.SerializerLong;

public class MapDBPersistenceContext implements PersistenceContext {

	protected DB db;
	private GroupSerializer<PersistentSession> sessionSerializer = new PersistentSessionSerializer();
	private GroupSerializer<Long> idSerializer = new SerializerLong();
	private GroupSerializer<PersistentWorkItem> workItemSerializer = new PersistentWorkItemSerializer();
	private BTreeMap<Long, PersistentWorkItem> workItemMap;
	private BTreeMap<Long, PersistentSession> sessionMap;

	public MapDBPersistenceContext(DB db) {
		this.db = db;
		this.workItemMap = db.treeMap("workItem", idSerializer, workItemSerializer).createOrOpen();
		this.sessionMap = db.treeMap("session", idSerializer, sessionSerializer).createOrOpen(); 
    }
	
	@Override
	public PersistentSession persist(PersistentSession session) {
		long id;
		if (session.getId() == null || session.getId() == -1) {
			id = sessionMap.size() + 1L;
			session.setId(id);
		} else {
			id = session.getId();
		}
		sessionMap.put(id, session);
		return session;
	}

	@Override
	public PersistentSession findSession(Long id) {
		return sessionMap.getOrDefault(id, null);
	}

	@Override
	public void remove(PersistentSession session) {
		sessionMap.remove(session.getId());
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
		long id;
		if (workItem.getId() == null || workItem.getId() == -1) {
			id = workItemMap.size() + 1L;
			workItem.setId(id);
		} else {
			id = workItem.getId();
		}
		workItemMap.put(id, workItem);
		return workItem;
	}

	@Override
	public PersistentWorkItem findWorkItem(Long id) {
		return workItemMap.getOrDefault(id, null);
	}

	@Override
	public void remove(PersistentWorkItem workItem) {
		workItemMap.remove(workItem.getId());
	}

	@Override
	public void lock(PersistentWorkItem workItem) {
	}

	@Override
	public PersistentWorkItem merge(PersistentWorkItem workItem) {
		workItemMap.replace(workItem.getId(), workItem);
		return workItem;
	}
}
