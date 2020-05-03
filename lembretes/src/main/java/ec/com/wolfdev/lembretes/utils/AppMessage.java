package ec.com.wolfdev.lembretes.utils;

public interface AppMessage {
	public static final String MSJ_SIGNUP_EMAIL_EXISTS = "El correo electrónico ya se encuentra registrado: ";
	public static final String MSJ_SIGNUP_OK = "El usuario ha sido registrado ";
	public static final String MSJ_SIGNUP_DOCID_EXISTS = "El documento de identificación ya se encuentra registrado: ";
	public static final String MSJ_SIGNUP_EMAIL_NO_SEND = "El usuario fue guardado pero el correo con la contraseña no pude ser enviado. Inténtalo más tarde desde el botón \"Reestablecer Contraseña\"";
	public static final String MSJ_NOT_FOUND_INFORMATION = "La información que busca no existe";
	public static final String MSJ_UNAUTHORIZED = "No tiene autorización para realizar esta acción";
	public static final String MSJ_FOUND_INFORMATION_DELETED = "La información de %s ha sido desactivada, por favor comuniquese con el Administrador del Sistema";
	public static final String MSJ_NOT_DELETE_MODERATOR_USER = "No se puede eliminar a un usuario con rol: %s ";
	public static final String MSJ_DELETE_INFORMATION = "La información ha sido %s";
	public static final String MSJ_BLOCKED_INFORMATION = "La información ha sido %s";
	public static final String MSJ_CREATE_INFORMATION = "La información ha sido creada";
	public static final String MSJ_UPDATE_INFORMATION = "La información ha sido actualizada";
	public static final String MSJ_UPDATE_WITHOUT_INFORMATION = "No hay información ha actualizar";
	public static final String MSJ_ERR_MAIL = "No se ha podido enviar la informacion al correo, revise los parametros RecipentTO, RecipentCC";
	public static final String MSJ_PASS_RECOVERY = "La nueva contraseña ha sido enviada a %s ";

}
