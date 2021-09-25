package dev.pauloribeiro.cepcorreios;

import java.util.List;

public interface CorreiosApi {
	
	List<CepCorreio> getCep(String cepPesquisa) throws InterruptedException;

}
