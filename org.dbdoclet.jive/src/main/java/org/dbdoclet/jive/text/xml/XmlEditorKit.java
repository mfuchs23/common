package org.dbdoclet.jive.text.xml;

import javax.swing.text.Document;
import javax.swing.text.StyledEditorKit;

public class XmlEditorKit extends StyledEditorKit {

    private static final long serialVersionUID = 1L;

	@Override
	public String getContentType() {
	return "text/xml";
    }

    @Override
    public Document createDefaultDocument() {
	return new XmlDocument();
    }

}
