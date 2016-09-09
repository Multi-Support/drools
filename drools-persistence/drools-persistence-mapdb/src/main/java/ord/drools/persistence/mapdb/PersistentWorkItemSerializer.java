package ord.drools.persistence.mapdb;

import java.io.IOException;

import org.drools.persistence.PersistentWorkItem;
import org.mapdb.DataInput2;
import org.mapdb.DataOutput2;
import org.mapdb.Serializer;

public class PersistentWorkItemSerializer implements Serializer<PersistentWorkItem> {

	public PersistentWorkItemSerializer(PersistentWorkItem workItem) {
		// TODO Auto-generated constructor stub
	}

	public PersistentWorkItemSerializer() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compare(PersistentWorkItem wi1, PersistentWorkItem wi2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PersistentWorkItem deserialize(DataInput2 arg0, int arg1) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void serialize(DataOutput2 arg0, PersistentWorkItem arg1) throws IOException {
		// TODO Auto-generated method stub

	}

}
