package dev.pauloribeiro.cepcorreios;

public final class CorreiosApiFactory {

	private CorreiosApiFactory() {
	}

	public static CorreiosApi correiosApi() {
		return new CorreiosApiImpl();
	}
}
