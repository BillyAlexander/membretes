package ec.com.wolfdev.lembretes.core.base.context;

import org.springframework.stereotype.Service;

@Service
public class WolfDevBase {
	private WolfDevBaseConfig config;

	public WolfDevBase(WolfDevBaseConfig config) {
		super();
		this.config = config;
	}

	public WolfDevBaseConfig getConfig() {
		return config;
	}

	public void setConfig(WolfDevBaseConfig config) {
		this.config = config;
	}
}
