package org.drools.persistence.mapdb;

import java.io.IOException;

import org.drools.persistence.PersistentSession;
import org.mapdb.DataInput2;
import org.mapdb.DataOutput2;
import org.mapdb.Serializer;

public class PersistentSessionSerializer implements Serializer<PersistentSession> {

	public PersistentSessionSerializer() {
	}

	@Override
	public int compare(PersistentSession s1, PersistentSession s2) {
		return s1.getId().compareTo(s2.getId());
	}

	@Override
	public PersistentSession deserialize(DataInput2 input, int available) throws IOException {
		MapDBSession session = new MapDBSession();
		long id = input.readLong();
		int size = input.readInt();
		byte[] data = new byte[size];
		input.readFully(data);
		session.setData(data);
		if (id > -1) {
			session.setId(id);
		}
		return session;
	}

	@Override
	public void serialize(DataOutput2 output, PersistentSession session) throws IOException {
		session.transform();
		Long id = session.getId();
		byte[] data = session.getData();
		int size = data.length;
		output.writeLong(id == null ? -1L : id);
		output.writeInt(size);;
		output.write(data);
	}
}
