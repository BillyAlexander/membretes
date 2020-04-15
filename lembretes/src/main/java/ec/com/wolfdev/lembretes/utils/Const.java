package ec.com.wolfdev.lembretes.utils;

public interface Const {
	public static final String SCHEMA = "lembretes";
	public static final String JNDI_DATA_BASE = "java:/PostGreDS";
	public static final String JNDI_MAIL = "java:jboss/mail/Pgp";	
	public static final String PACKAGE_NAMING = "ec.com.wolfdev.lembretes";
	public static final String API_PRIVATE = "/api/";
	public static final String API_PUBLIC = "open/";
	public static final String PASSWORD_SYMBOLS = "1234567890abcdefghijklmnopqrstuvwxyz0987654321";
	public static final String SORT_ASC = "asc";
	public static final String SORT_DESC = "desc";
	public static final Integer PAGE_0_DEFAULT = 0;
	public static final Integer PAGE_SIZE_DEFAULT = 10;
	public static final String SORT_FIELD_DEFAULT = "creationDate";
}
