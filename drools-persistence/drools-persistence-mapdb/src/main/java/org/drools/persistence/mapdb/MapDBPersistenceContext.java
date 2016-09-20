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

	public MapDBPersistenceContext(DB db) {
		this.db = db;
    }
	
	@Override
	public PersistentSession persist(PersistentSession session) {
		long id;
		if (session.getId() == null || session.getId() == -1) {
			Long lk = getSessionMap().lastKey2();
			id = (lk == null ? 0 : lk) + 1L;
			session.setId(id);
		} else {
			id = session.getId();
		}
		getSessionMap().put(id, session);
		db.commit();
		return session;
	}

	@Override
	public PersistentSession findSession(Long id) {
		return getSessionMap().getOrDefault(id, null);
	}

	@Override
	public void remove(PersistentSession session) {
		getSessionMap().remove(session.getId());
		db.commit();
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
			Long lk = getWorkItemMap().lastKey2();
			id = (lk == null ? 0 : lk) + 1L;
			workItem.setId(id);
		} else {
			id = workItem.getId();
		}
		getWorkItemMap().put(id, workItem);
		db.commit();
		return workItem;
	}

	@Override
	public PersistentWorkItem findWorkItem(Long id) {
		return getWorkItemMap().getOrDefault(id, null);
	}

	protected BTreeMap<Long, PersistentWorkItem> getWorkItemMap() {
//		db.atomicVar("workItem", workItemSerializer).createOrOpen();
//		db.treeSet("workItemByState", workItemSerializer).createOrOpen();
//		db.indexTreeList("workItemByState", workItemSerializer).createOrOpen();
//		db.hashMap("workItemByState", new SerializerInteger(), workItemSerializer).createOrOpen();
//		Triple<A, B, C> tuple3;
//		db.treeMap("workItem", idSerializer, workItemSerializer).create().prefixSubMap(tuple3);
		return db.treeMap("workItem", idSerializer, workItemSerializer).createOrOpen();
	}
	
	protected BTreeMap<Long, PersistentSession> getSessionMap() {
		return db.treeMap("session", idSerializer, sessionSerializer).createOrOpen();
	}

	@Override
	public void remove(PersistentWorkItem workItem) {
		getWorkItemMap().remove(workItem.getId());
		db.commit();
	}

	@Override
	public void lock(PersistentWorkItem workItem) {
	}

	@Override
	public PersistentWorkItem merge(PersistentWorkItem workItem) {
		getWorkItemMap().replace(workItem.getId(), workItem);
		db.commit();
		return workItem;
	}
}
