package dev.pauloribeiro.cepcorreios;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.assertj.core.api.AbstractStringAssert;
import org.junit.jupiter.api.Test;

class CorreiosApiImplTest {
	
	private final CorreiosApiImpl correiosApi = new CorreiosApiImpl();
	
	//Rua 1
	//Praça 2
	@Test
	void testCep74013010() throws InterruptedException {
		
		String LOGRADOURO = "Rua 1"; 
		String COMPLEMENTO = "";
		String CEP = "74013010";
		
		List<CepCorreio> response = correiosApi.getCep(CEP);

		assertThat(response.get(0).getLogradouro()).isEqualTo(LOGRADOURO);
		assertThat(response.get(0).getComplemento()).isEqualTo(COMPLEMENTO);		
	}
	
	//Cln 102
	@Test
	void testCep70722500() throws InterruptedException {
		
		String LOGRADOURO = "CLN 102"; 
		String COMPLEMENTO = "";
		String CEP = "70722500";
		
		List<CepCorreio> response = correiosApi.getCep(CEP);
		
		assertThat(response.get(0).getLogradouro()).isEqualTo(LOGRADOURO);
		assertThat(response.get(0).getComplemento()).isEqualTo(COMPLEMENTO);		

	}

	//Cln 102 Bloco A
	@Test
	void testCep70722510() throws InterruptedException {
		
		String LOGRADOURO = "CLN 102"; 
		String COMPLEMENTO = "Bloco A";
		String CEP = "70722510";
		
		List<CepCorreio> response = correiosApi.getCep(CEP);
		
		assertThat(response.get(0).getLogradouro()).isEqualTo(LOGRADOURO);
		assertThat(response.get(0).getComplemento()).isEqualTo(COMPLEMENTO);		

	}
	
	//Avenida Paulista, 407
	@Test
	void testCep01311906() throws InterruptedException {

		String LOGRADOURO = "Avenida Paulista"; 
		String COMPLEMENTO = ", 407";
		String CEP = "01311906";
		
		
		List<CepCorreio> response = correiosApi.getCep(CEP);
		
		assertThat(response.get(0).getLogradouro()).isEqualTo(LOGRADOURO);
		assertThat(response.get(0).getComplemento()).isEqualTo(COMPLEMENTO);
	}
	
	
	@Test
	void testCep01046010() throws InterruptedException {
		
		String LOGRADOURO = "Avenida Ipiranga"; 
		String COMPLEMENTO = "- até 399/400";
		String CEP = "01046010";
		
		
		List<CepCorreio> response = correiosApi.getCep(CEP);
		
		assertThat(response.get(0).getLogradouro()).isEqualTo(LOGRADOURO);
		assertThat(response.get(0).getComplemento()).isEqualTo(COMPLEMENTO);
		
	}
}