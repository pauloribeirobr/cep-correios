package dev.pauloribeiro.cepcorreios;

import java.util.List;

public class CorreiosApiDTO {

	private boolean erro;
	private String mensagem;
	private int total;
	private List<CorreiosApiBaseDadoDTO> dados;
	/**
	 * @return the erro
	 */
	public boolean isErro() {
		return erro;
	}
	/**
	 * @param erro the erro to set
	 */
	public void setErro(boolean erro) {
		this.erro = erro;
	}
	/**
	 * @return the mensagem
	 */
	public String getMensagem() {
		return mensagem;
	}
	/**
	 * @param mensagem the mensagem to set
	 */
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	/**
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(int total) {
		this.total = total;
	}
	/**
	 * @return the dados
	 */
	public List<CorreiosApiBaseDadoDTO> getDados() {
		return dados;
	}
	/**
	 * @param dados the dados to set
	 */
	public void setDados(List<CorreiosApiBaseDadoDTO> dados) {
		this.dados = dados;
	}
}
