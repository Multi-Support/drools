package org.drools.persistence.mapdb;

import java.io.IOException;

import org.drools.persistence.PersistentWorkItem;
import org.drools.persistence.processinstance.mapdb.MapDBWorkItem;
import org.mapdb.DataInput2;
import org.mapdb.DataOutput2;
import org.mapdb.Serializer;

public class PersistentWorkItemSerializer implements Serializer<PersistentWorkItem> {

	public PersistentWorkItemSerializer() {
	}

	@Override
	public int compare(PersistentWorkItem wi1, PersistentWorkItem wi2) {
		return wi1.getId().compareTo(wi2.getId());
	}

	@Override
	public PersistentWorkItem deserialize(DataInput2 input, int available) throws IOException {
		MapDBWorkItem workItem = new MapDBWorkItem();
		long id = input.readLong();
		int size = input.readInt();
		byte[] data = new byte[size];
		input.readFully(data);
		int state = input.readInt();
		workItem.setData(data);
		workItem.setState(state);;
		if (id > -1) {
			workItem.setId(id);
		}
		return workItem;
	}

	@Override
	public void serialize(DataOutput2 output, PersistentWorkItem workItem) throws IOException {
		MapDBWorkItem dbWorkItem = (MapDBWorkItem) workItem;
		output.writeLong(workItem.getId() == null ? -1 : workItem.getId());
		int size = dbWorkItem.getData().length;
		output.writeInt(size);;
		output.write(dbWorkItem.getData());
		output.writeInt(dbWorkItem.getState());
	}

}
