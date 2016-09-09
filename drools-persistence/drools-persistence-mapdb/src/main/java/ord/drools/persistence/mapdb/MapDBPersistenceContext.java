package ord.drools.persistence.mapdb;

import org.drools.persistence.PersistenceContext;
import org.drools.persistence.PersistentSession;
import org.drools.persistence.PersistentWorkItem;
import org.mapdb.DB;
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
		db.getStore().put(session, sessionSerializer);
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
		db.getStore().close();
	}

	@Override
	public PersistentWorkItem persist(PersistentWorkItem workItem) {
		db.getStore().put(workItem, workItemSerializer);
		return null;
	}

	@Override
	public PersistentWorkItem findWorkItem(Long id) {
		return db.getStore().get(id, workItemSerializer);
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
