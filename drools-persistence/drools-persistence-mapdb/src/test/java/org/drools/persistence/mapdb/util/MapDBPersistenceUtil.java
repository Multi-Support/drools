package org.drools.persistence.mapdb.util;

import static org.drools.persistence.mapdb.MapDBEnvironmentName.DB_OBJECT;
import static org.kie.api.runtime.EnvironmentName.GLOBALS;
import static org.kie.api.runtime.EnvironmentName.TRANSACTION;
import static org.kie.api.runtime.EnvironmentName.TRANSACTION_MANAGER;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.UserTransaction;

import org.drools.core.base.MapGlobalResolver;
import org.drools.core.impl.EnvironmentFactory;
import org.drools.persistence.mapdb.KnowledgeStoreServiceImpl;
import org.kie.api.runtime.Environment;
import org.mapdb.DB;
import org.mapdb.DBMaker;

import bitronix.tm.TransactionManagerServices;

public class MapDBPersistenceUtil {

	public static void cleanUp(Map<String, Object> context) {
		DB db = (DB) context.get(DB_OBJECT);
		db.close();
	}

	public static Map<String, Object> setupMapDB() {
		new KnowledgeStoreServiceImpl(); //TODO this reference is to make sure it registers the store service
		HashMap<String, Object> context = new HashMap<>();
		context.put(DB_OBJECT, DBMaker.memoryDB().transactionEnable().make());
		return context;
	}

	public static Environment createEnvironment(Map<String, Object> context) {
		// TODO Auto-generated method stub
		Environment env = EnvironmentFactory.newEnvironment();
		UserTransaction ut = (UserTransaction) context.get(TRANSACTION);
        if (ut != null) {
            env.set(TRANSACTION, ut);
        }

        env.set(DB_OBJECT, context.get(DB_OBJECT));
        env.set(TRANSACTION_MANAGER, TransactionManagerServices.getTransactionManager());
        env.set(GLOBALS, new MapGlobalResolver());

		return env;
	}

}

