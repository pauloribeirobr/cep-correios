package dev.pauloribeiro.cepcorreios;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CorreiosApiBaseDadoDTO {

	private String uf;
	private String localidade;
	private String logradouroDNEC;
	private String bairro;
	private String cep;
	private String nomeUnidade;
	private String localidadeSubordinada;
	private String tipoCep;	//2 - Comum | 5 - Grande Usuário
	
	/**
	 * @return the uf
	 */
	public String getUf() {
		return uf;
	}
	/**
	 * @param uf the uf to set
	 */
	public void setUf(String uf) {
		this.uf = uf;
	}
	/**
	 * @return the localidade
	 */
	public String getLocalidade() {
		return localidade;
	}
	/**
	 * @param localidade the localidade to set
	 */
	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}
	/**
	 * @return the logradouroDNEC
	 */
	public String getLogradouroDNEC() {
		return logradouroDNEC;
	}
	/**
	 * @param logradouroDNEC the logradouroDNEC to set
	 */
	public void setLogradouroDNEC(String logradouroDNEC) {
		this.logradouroDNEC = logradouroDNEC;
	}
	/**
	 * @return the bairro
	 */
	public String getBairro() {
		return bairro;
	}
	/**
	 * @param bairro the bairro to set
	 */
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	/**
	 * @return the cep
	 */
	public String getCep() {
		return cep;
	}
	/**
	 * @param cep the cep to set
	 */
	public void setCep(String cep) {
		this.cep = cep;
	}
	/**
	 * @return the nomeUnidade
	 */
	public String getNomeUnidade() {
		return nomeUnidade;
	}
	/**
	 * @param nomeUnidade the nomeUnidade to set
	 */
	public void setNomeUnidade(String nomeUnidade) {
		this.nomeUnidade = nomeUnidade;
	}
	public String getLocalidadeSubordinada() {
		return localidadeSubordinada;
	}
	public void setLocalidadeSubordinada(String localidadeSubordinada) {
		this.localidadeSubordinada = localidadeSubordinada;
	}
	public String getTipoCep() {
		return tipoCep;
	}
	public void setTipoCep(String tipoCep) {
		this.tipoCep = tipoCep;
	}
}