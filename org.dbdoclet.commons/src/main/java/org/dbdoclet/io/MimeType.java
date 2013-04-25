package org.dbdoclet.io;

import java.util.HashMap;

import org.dbdoclet.service.StringServices;

public class MimeType {

    public static final MimeType COMP = new MimeType("COMP", "Cocs Stock File (.comp)", "text/comp", ".comp", "\\.comp$", false);
    public static final MimeType CSV = new MimeType("CSV", "CSV File (.csv)", "text/csv", ".csv", "\\.csv$", true);
    public static final MimeType DOC = new MimeType("DOC", "Microsoft Word (.doc)", "application/msword", ".doc", "\\.doc$", true);
    public static final MimeType DOCX = new MimeType("DOCX", "Microsoft Word (.docx)", "application/msword (xml)", ".docx", "\\.docx$", true);
    public static final MimeType GIF = new MimeType("GIF", "GIF Image (.gif)", "image/gif", ".gif", "\\.gif$", true);
    public static final MimeType PNG = new MimeType("PNG", "PNG Image (.png)", "image/png", ".png", "\\.png$", true);
    public static final MimeType JPEG = new MimeType("JPEG", "JPEG Image (.jpg)", "image/jpeg", ".jpg", "\\.jpg$", true);
    public static final MimeType HTML = new MimeType("HTML", "HTML (.html)", "text/html", ".html", "\\.htm(l)?$", false);
    public static final MimeType MIDI = new MimeType("MIDI", "Audio MIDI (.midi)", "audio/midi", ".midi", "\\.mid(i)?$", true);
    public static final MimeType MP3 = new MimeType("MP3", "Audio MP3 (.mp3)", "audio/mpeg", ".mp3", "\\.mp3$", true);
    public static final MimeType MPEG = new MimeType("MPEG", "Video MPEG (.mpeg)", "video/mpeg", ".mpeg", "\\.mp(e)?g$", true);
    public static final MimeType ODP = new MimeType("ODP", "OpenOffice Impress (.odp)", "application/vnd.oasis.opendocument.presentation", ".odp", "\\.odp$", true);
    public static final MimeType ODS = new MimeType("ODS", "OpenOffice Calc (.ods)", "application/vnd.oasis.opendocument.spreadsheet", ".ods", "\\.ods$", true);
    public static final MimeType ODT = new MimeType("ODT", "OpenOffice Writer (.odt)", "application/vnd.oasis.opendocument.text", ".odt", "\\.odt$", true);
    public static final MimeType PDF = new MimeType("PDF", "PDF - Portable Document Format (.pdf)", "application/pdf", ".pdf", "\\.pdf$", true);
    public static final MimeType POD = new MimeType("POD", "OpenProj Format (.pod)", "application/pod", ".pod", "\\.pod$", true);
    public static final MimeType PPT = new MimeType("PPT", "Microsoft Powerpoint (.ppt)", "application/mspowerpoint", ".ppt", "\\.ppt$", true);
    public static final MimeType PPTX = new MimeType("PPTX", "Microsoft Powerpoint (.pptx)", "application/mspowerpoint (xml)", ".pptx", "\\.pptx$", true);
    public static final MimeType PPTM = new MimeType("PPTM", "Microsoft Powerpoint with Macros (.pptm)", "application/mspowerpoint with macros (xml)", ".pptm", "\\.pptm$", true);
    public static final MimeType POTX = new MimeType("POTX", "Microsoft Powerpoint (.potx)", "application/mspowerpoint template (xml)", ".potx", "\\.potx$", true);
    public static final MimeType WAV = new MimeType("WAV", "Audio WAV (.wav)", "audio/wav", ".wav", "\\.wav$", true);
    public static final MimeType WMV = new MimeType("WMV", "Video WMV (.wmv)", "video/x-ms-wmv", ".wmv", "\\.wmv$",  true);
    public static final MimeType XLS = new MimeType("XLS", "Microsoft Excel (.xls)", "application/msexcel", ".xls", "\\.xls", true);
    public static final MimeType XLSX = new MimeType("XLSX", "Microsoft Excel (.xlsx)", "application/msexcel (xml)", ".xlsx", "\\.xlsx", true);
    public static final MimeType XML = new MimeType("XML", "XML (.xml)", "application/xml", ".xml", "\\.xml$", false);
    
    private static HashMap<String, MimeType> mimeTypeMap = initMimeTypeMap();
    private static HashMap<String, MimeType> extensionMap = initExtensionMap();
    
    private String extension;
    private String id;
    private String label;
    private String mimeType;
    private boolean binary;
	private final String regexp;
    
    public MimeType(String id, String label, String mimeType, String extension, String regexp, boolean binary) {

        if (id == null) {
            throw new IllegalArgumentException("The argument id must not be null!");
        }

        if (label == null) {
            throw new IllegalArgumentException("The argument label must not be null!");
        }

        
        if (mimeType == null) {
            throw new IllegalArgumentException("The argument mimeType must not be null!");
        }

        if (extension == null) {
            throw new IllegalArgumentException("The argument extension must not be null!");
        }

        if (regexp == null) {
			throw new IllegalArgumentException("The parameter regexp must not be null!");
		}
        
        this.id = id;
        this.label = label;
        this.mimeType = mimeType;
        this.extension = extension;
        this.regexp = regexp;
		this.binary = binary;
    }

    public static MimeType valueOf(String mime) {
        
        if (mime == null) {
            throw new IllegalArgumentException("The argument mime must not be null!");
        }

        MimeType mimeType = mimeTypeMap.get(mime);
        
        if (mimeType == null) {
            throw new IllegalArgumentException("'" + mime + "' is not a valid MimeType!");
        }

        return mimeType;
    }
    
    public static MimeType findByExtension(String extension) {
        
        if (extension == null) {
            throw new IllegalArgumentException("The argument extension must not be null!");
        }

        extension = StringServices.cutPrefix(extension, ".");
        extension = extension.toLowerCase();
        
        MimeType mimeType = extensionMap.get(extension );
        return mimeType;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return label;
    }

    protected void setLabel(String label) {
        this.label = label;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
    
    public String getExtension() {

        if (extension.startsWith(".") == false) {
            return "." + extension;
        }

        return extension;
    }

    public boolean isBinary() {
		return binary;
	}

	public void setBinary(boolean binary) {
		this.binary = binary;
	}

	public String getRegexp() {
		return regexp;
	}

	public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
    
    public String getMimeType() {
        return mimeType;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        }

        if (obj instanceof String) {

            String mimeType = (String) obj;

            if (mimeType.equals(this.mimeType)) {
                return true;
            } else {
                return false;
            }
        }

        if (obj instanceof MimeType) {

            MimeType item = (MimeType) obj;
            String mimeType = item.getMimeType();

            if (mimeType.equals(this.mimeType)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int hashCode() {

        int hashCode = mimeType.hashCode();
        return hashCode;
    }

    @Override
    public String toString() {
        return label;
    }

    private static HashMap<String, MimeType> initExtensionMap() {

        HashMap<String, MimeType> map = new HashMap<String, MimeType>();
        
        map.put("comp", COMP);
        map.put("csv", CSV);
        map.put("doc", DOC);
        map.put("docx", DOCX);
        map.put("gif", GIF);
        map.put("html", HTML);
        map.put("jpg", JPEG);
        map.put("midi", MIDI);
        map.put("mp3", MP3);
        map.put("mpeg", MPEG);
        map.put("odp", ODP);
        map.put("ods", ODS);
        map.put("odt", ODT);
        map.put("pdf", PDF);
        map.put("pod", POD);
        map.put("png", PNG);
        map.put("ppt", PPT);
        map.put("pptx", PPTX);
        map.put("pptm", PPTM);
        map.put("potx", POTX);
        map.put("wav", WAV);
        map.put("wmv", WMV);
        map.put("xml", XML);
        map.put("xsl", XLS);
        map.put("xslx", XLSX);
        
        
        return map;
    }

    private static HashMap<String, MimeType> initMimeTypeMap() {

        HashMap<String, MimeType> map = new HashMap<String, MimeType>();
        
        map.put("application/msexcel", XLS);
        map.put("application/mspowerpoint", PPT);
        map.put("application/msword", DOC);
        map.put("application/msexcel (xml)", XLSX);
        map.put("application/mspowerpoint (xml)", PPTX);
        map.put("application/mspowerpoint with macros (xml)", PPTM);
        map.put("application/mspowerpoint template (xml)", POTX);
        map.put("application/msword (xml)", DOCX);
        map.put("application/pdf", PDF);
        map.put("application/pod", POD);
        map.put("application/vnd.oasis.opendocument.presentation", ODP);
        map.put("application/vnd.oasis.opendocument.spreadsheet", ODS);
        map.put("application/vnd.oasis.opendocument.text", ODT);
        map.put("application/xml", XML);
        map.put("audio/midi", MIDI);
        map.put("audio/mpeg", MP3);
        map.put("audio/wav", WAV);
        map.put("image/gif", GIF);
        map.put("image/jpeg", JPEG);
        map.put("image/png", PNG);
        map.put("text/comp", COMP);
        map.put("text/csv", CSV);
        map.put("text/html", HTML);
        map.put("video/mpeg", MPEG);
        map.put("video/x-ms-wmv", WMV);
        
        
        return map;
    }
    
}
