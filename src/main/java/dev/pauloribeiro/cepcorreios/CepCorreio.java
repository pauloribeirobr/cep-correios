package dev.pauloribeiro.cepcorreios;

public class CepCorreio {

	private String cep;
	private String estado;
	private String cidade;
	private String bairro;
	private String logradouro;
	private String complemento;
	private String nomeUnidade;
	
	/**
	 * @param cep
	 * @param estado
	 * @param cidade
	 * @param bairro
	 * @param logradouro
	 * @param complemento
	 * @param nomeUnidade
	 */
	public CepCorreio(String cep, String estado, String cidade, String bairro, String logradouro, String complemento,
			String nomeUnidade) {
		super();
		this.cep = cep;
		this.estado = estado;
		this.cidade = cidade;
		this.bairro = bairro;
		this.logradouro = logradouro;
		this.complemento = complemento;
		this.nomeUnidade = nomeUnidade;
	}

	/**
	 * @return the cep
	 */
	public String getCep() {
		return cep;
	}

	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @return the cidade
	 */
	public String getCidade() {
		return cidade;
	}

	/**
	 * @return the bairro
	 */
	public String getBairro() {
		return bairro;
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

	/**
	 * @return the nomeUnidade
	 */
	public String getNomeUnidade() {
		return nomeUnidade;
	}
}
