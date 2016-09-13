package org.drools.persistence.processinstance.mapdb;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.marshalling.impl.MarshallerReaderContext;
import org.drools.core.marshalling.impl.MarshallerWriteContext;
import org.drools.core.marshalling.impl.ProtobufInputMarshaller;
import org.drools.core.marshalling.impl.ProtobufOutputMarshaller;
import org.drools.core.process.instance.WorkItem;
import org.drools.persistence.PersistentWorkItem;
import org.kie.api.runtime.Environment;

public class MapDBWorkItem implements PersistentWorkItem {

	private Long id;
	private int state;
	private byte[] data;
	private WorkItem workItem;
	private Environment env;
	
	public MapDBWorkItem() {
	}
	
	public MapDBWorkItem(WorkItem workItem, Environment env) {
		this.workItem = workItem;
		this.env = env;
	}

	@Override
	public void transform() {
		this.state = workItem.getState();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            MarshallerWriteContext context = new MarshallerWriteContext( baos,
            		null, null, null, null, this.env);
            ProtobufOutputMarshaller.writeWorkItem(context, workItem);
            context.close();
            this.data = baos.toByteArray();
        } catch ( IOException e ) {
            throw new IllegalArgumentException( "IOException while storing workItem " + workItem.getId() + ": " + e.getMessage() );
        }
	}

	public void setState(int state) {
		this.state = state;
	}
	
	public int getState() {
		return state;
	}
	
	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public WorkItem getWorkItem(Environment env, InternalKnowledgeBase kieBase) {
		ByteArrayInputStream bais = new ByteArrayInputStream( this.data );
		try {
			MarshallerReaderContext context = new MarshallerReaderContext( bais,
					kieBase, null, null, null, env);
			return ProtobufInputMarshaller.readWorkItem(context);
		} catch (IOException e) {
            throw new RuntimeException("Unable to read work item ", e);
		}
	}

	public void setData(byte[] data) {
		this.data = data;
	}
	
	public byte[] getData() {
		return data;
	}

}
