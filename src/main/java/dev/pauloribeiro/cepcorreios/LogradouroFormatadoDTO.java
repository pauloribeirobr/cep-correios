package dev.pauloribeiro.cepcorreios;

public class LogradouroFormatadoDTO {

	private String logradouro;
	private String complemento;
	
	/**
	 * @param logradouro
	 * @param complemento
	 */
	public LogradouroFormatadoDTO(String logradouro, String complemento) {
		super();
		this.logradouro = logradouro;
		this.complemento = complemento;
	}

	/**
	 * @return the logradouro
	 */
	public String getLogradouro() {
		return logradouro;
	}

	/**
	 * @return the complemento
	 */
	public String getComplemento() {
		return complemento;
	}

}
