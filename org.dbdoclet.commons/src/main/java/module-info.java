module org.dbdoclet.commons {
	
	exports org.dbdoclet;
	exports org.dbdoclet.comparator;
	exports org.dbdoclet.io;
	exports org.dbdoclet.lock;
	exports org.dbdoclet.manager;
	exports org.dbdoclet.net;
	exports org.dbdoclet.progress;
	exports org.dbdoclet.service;
	exports org.dbdoclet.service.file;
	exports org.dbdoclet.template;
	exports org.dbdoclet.transaction;
	exports org.dbdoclet.unit;
	
	requires jdk.xml.dom;
	requires java.desktop;
}
