package ec.com.wolfdev.lembretes.core.base.service;

import java.lang.reflect.Method;

import javax.servlet.ServletException;

public abstract class BaseService<ENTITY> {
	private Class<ENTITY> type;

	public BaseService(Class<ENTITY> type) {
		this.type = type;
	}

	public ENTITY changeStatus(ENTITY obj) throws ServletException {
		try {
			Method getStatus = this.type.getMethod("getStatus");
			Method setStatus = this.type.getMethod("setStatus", Boolean.class);
			Boolean status = (Boolean) getStatus.invoke(obj);
			setStatus.invoke(obj, !status);
			return obj;
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	public ENTITY changeStatus(ENTITY obj, Boolean status) throws ServletException {
		try {
			Method setStatus = this.type.getMethod("setStatus", Boolean.class);
			setStatus.invoke(obj, status);
			return obj;
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	public Class<ENTITY> getType() {
		return type;
	}

	public void setType(Class<ENTITY> type) {
		this.type = type;
	}
}
