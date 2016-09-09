package ord.drools.persistence.mapdb;

import java.io.IOException;

import org.drools.persistence.PersistentSession;
import org.mapdb.DataInput2;
import org.mapdb.DataOutput2;
import org.mapdb.Serializer;

public class PersistentSessionSerializer implements Serializer<PersistentSession> {

	public PersistentSessionSerializer(PersistentSession session) {
		// TODO Auto-generated constructor stub
	}

	public PersistentSessionSerializer() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compare(PersistentSession s1, PersistentSession s2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PersistentSession deserialize(DataInput2 arg0, int arg1) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void serialize(DataOutput2 arg0, PersistentSession arg1) throws IOException {
		// TODO Auto-generated method stub

	}

}
