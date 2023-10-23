package dev.pauloribeiro.cepcorreios;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CorreiosApiImpl implements CorreiosApi {

	@Override
	public List<CepCorreio> getCep(String cepPesquisa) throws InterruptedException {
		List<CepCorreio> ceps = new ArrayList<>();
		int inicio = 1;
		int fim = 50;
		boolean continuar = true;
		
		while(continuar) {
			CorreiosApiDTO response = callCorreiosApi(cepPesquisa, String.valueOf(inicio), String.valueOf(fim));
			
			for (CorreiosApiBaseDadoDTO dados : response.getDados()) {
				
				if(dados.getCep().startsWith(cepPesquisa)) {
					LogradouroFormatadoDTO logradouro = logradouroSplit(dados.getLogradouroDNEC());
					String cidade = "";
					String bairro = "";
					//tipoCep = 1 - Comum (mas municipios sem CEP)
					//tipoCep = 5 - Agência Click e Retire
					//tipoCep = 6 - Grande Usuário - Agência dos Correios
					
					//tipoCep = 1
					if(dados.getTipoCep().equals("1")) {
						if(dados.getBairro().trim().isEmpty() && dados.getLocalidadeSubordinada().trim().isEmpty()) {
							cidade = dados.getLocalidade();
							bairro = "";
						}else {
							cidade = dados.getLocalidadeSubordinada();
							if(dados.getBairro().trim().isEmpty()) {
								bairro = dados.getLocalidade();
							}else {
								bairro = dados.getBairro();
							}							
						}
					}else {
						if(dados.getLocalidadeSubordinada().trim().isEmpty()) {
							cidade = dados.getLocalidade();
							bairro = dados.getBairro();
						}else {
							cidade = dados.getLocalidadeSubordinada();
							bairro = dados.getLocalidade();
						}
					}

					//if(dados.getTipoCep().equals("5") || dados.getTipoCep().equals("6")) {

					//}
					
					
					
					CepCorreio cep = new CepCorreio(
							dados.getCep(), 
							dados.getUf(), 
							cidade, 
							bairro, 
							logradouro.getLogradouro(),
							logradouro.getComplemento(),
							dados.getNomeUnidade(),
							dados.getTipoCep());
					System.out.println(cep.toString());
					ceps.add(cep);					
				}
			}
			
			if(response.getTotal() > fim) {
				inicio = inicio + 50;
				fim = fim + 50;
				Thread.sleep(5000);
			}else {
				continuar = false;
			}
		}
		
		return ceps;
	}
	
	private CorreiosApiDTO callCorreiosApi(String cepPesquisa, String pagInicio, String pagFinal) {

		CorreiosApiDTO response = new CorreiosApiDTO();
		
		try {

			URL url = new URL("https://buscacepinter.correios.com.br/app/endereco/carrega-cep-endereco.php");
			URLConnection con = url.openConnection();
			HttpURLConnection http = (HttpURLConnection) con;
			http.setRequestMethod("POST");
			http.setDoOutput(true);
			
			Map<String,String> map = new HashMap<>();
			map.put("pagina", "/app/endereco/index.php");
	        map.put("cepaux", "");
	        map.put("mensagem_alerta", "");
	        map.put("endereco", cepPesquisa);
	        map.put("tipoCEP", "ALL");
	        map.put("inicio", pagInicio);
	        map.put("final", pagFinal);
			StringJoiner stringJoiner = new StringJoiner("&");
			for(Map.Entry<String,String> entry : map.entrySet())
			    stringJoiner.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "=" 
			         + URLEncoder.encode(entry.getValue(), "UTF-8"));
			byte[] output = stringJoiner.toString().getBytes(StandardCharsets.UTF_8);
			
			int length = output.length;
			
			http.setFixedLengthStreamingMode(length);
			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			http.setRequestProperty("authority", "buscacepinter.correios.com.br");
			http.setRequestProperty("origin", "https://buscacepinter.correios.com.br");
			http.setRequestProperty("referer", "https://buscacepinter.correios.com.br/app/endereco/index.php");
			http.connect();
			try(OutputStream outputStream = http.getOutputStream()) {
			    outputStream.write(output);
			}
			
			StringBuilder stringBuilder = new StringBuilder();
			for (int ch; (ch = http.getInputStream().read()) != -1; ) {
			    stringBuilder.append((char) ch);
			}
			
			ObjectMapper mapper = new ObjectMapper();
			
			response = mapper.readValue(stringBuilder.toString(), CorreiosApiDTO.class);
			
		} catch (Exception e) {
			System.out.println("Problema ao pesquisar o cep " + cepPesquisa + " . " + e.getMessage() );
			return response;
		}
		
		return response;
	}
	
	/**
	 * Separa o logradouro do complemento
	 * @param logradouro
	 * @return
	 */
	private static LogradouroFormatadoDTO logradouroSplit(String logradouro) {
		
		Pattern pattern;
		Matcher matcher;

		//Rua 1
		//CLN 102
		pattern = Pattern.compile("^[a-zA-ZçÇ]+\\s+\\d+$");
		matcher = pattern.matcher(logradouro);
		if (matcher.find()) {
			return new LogradouroFormatadoDTO(matcher.group(0).trim(), matcher.replaceFirst("").trim());
		}
		
		//CLN 102 Bloco A
		pattern = Pattern.compile("^([a-zA-ZçÇ]+\\s+\\d+)(\\s+\\w+\\s+\\w+)$");
		matcher = pattern.matcher(logradouro);
		if (matcher.find()) {
			return new LogradouroFormatadoDTO(matcher.group(1).trim(), matcher.group(2).trim());
		}
		
		
		// Praça do Correio s/n
		pattern = Pattern.compile("s/n$");
		matcher = pattern.matcher(logradouro);
		if (matcher.find()) {
			return new LogradouroFormatadoDTO(matcher.replaceFirst("").trim(), matcher.group(0).trim());
		}
		
		// 	Rua Tijuca do Sul, s/n
		pattern = Pattern.compile(", s/n$");
		matcher = pattern.matcher(logradouro);
		if (matcher.find()) {
			return new LogradouroFormatadoDTO(matcher.replaceFirst("").trim(), matcher.group(0).trim());
		}
		
		// "Rua Santo Antônio - até 799/800"
		pattern = Pattern.compile("- até \\d+./.\\d+$");
		matcher = pattern.matcher(logradouro);
		if (matcher.find()) {
			return new LogradouroFormatadoDTO(matcher.replaceFirst("").trim(), matcher.group(0).trim());
		}
		
		// Rua Santo Antônio - de 801/802 ao fim
		pattern = Pattern.compile("- de \\d+./.\\d+. ao fim$");
		matcher = pattern.matcher(logradouro);
		if (matcher.find()) {
			return new LogradouroFormatadoDTO(matcher.replaceFirst("").trim(), matcher.group(0).trim());
		}
		
		// Avenida Guilherme - de 541/542 a 789/790
		// Rua Santo Antônio - de 512/513 a 1160/1161
		pattern = Pattern.compile("(- de \\d+./.\\d+.a.\\d+./.\\d+)$");
		matcher = pattern.matcher(logradouro);
		if (matcher.find()) {
			return new LogradouroFormatadoDTO(matcher.replaceFirst("").trim(), matcher.group(0).trim());
		}
		
        //Rua Quinze de Novembro - lado par
        //Parque Dom Pedro II - de 502 ao fim - lado par
        //Rua São Bento - até 318 - lado par
		pattern = Pattern.compile("-.*par$");
		matcher = pattern.matcher(logradouro);
		if (matcher.find()) {
			return new LogradouroFormatadoDTO(matcher.replaceFirst("").trim(), matcher.group(0).trim());
		}

        //Rua Quinze de Novembro - lado ímpar
        //Rua São Bento - até 319 - lado ímpar
        //Rua São Bento - de 321 ao fim - lado ímpar
		pattern = Pattern.compile("-.*ímpar$");
		matcher = pattern.matcher(logradouro);
		if (matcher.find()) {
			return new LogradouroFormatadoDTO(matcher.replaceFirst("").trim(), matcher.group(0).trim());
		}
		
		// Esse aqui é raro mas eu peguei 2
		// Rua Bento Freitas 176 5º andar Conjunto 53 a 56
		// Avenida Paulista 37 3º Andar Conjunto 31 e 32
		pattern = Pattern
				.compile("([\\s\\S]+)\\s([\\d]+)\\s([\\d]+)([\\s\\S]+)(Conjunto [\\d]+ [ae] [\\d]+)");
		matcher = pattern.matcher(logradouro);
		if (matcher.find()) {
			
			return new LogradouroFormatadoDTO(matcher.group(1).trim(),
					(matcher.group(2).trim() + " " + matcher.group(3).trim() + " " + matcher.group(4).trim() + " " + matcher.group(5).trim()));
		}

		//Rua Dona Vitalina. Erro terminando com ponto
		pattern = Pattern.compile("\\.$");
		matcher = pattern.matcher(logradouro);
		if (matcher.find()) {
			return new LogradouroFormatadoDTO(matcher.replaceFirst("").trim(), "");
		}

		// Avenida Paulista ,200 (COM a virgula)
		pattern = Pattern.compile(",.*$");
		matcher = pattern.matcher(logradouro);
		if (matcher.find()) {
			return new LogradouroFormatadoDTO(matcher.replaceFirst("").trim(), matcher.group(0).trim());
		}

		// Avenida Angélica 2491 20º Andar Conjunto 203/204
		// Avenida Brigadeiro Luís Antônio 2375 Loja 11
		pattern = Pattern.compile("^([\\s\\S][^,]+)(\\s([0-9])+\\s)([\\s\\S][^,]+)$");
		matcher = pattern.matcher(logradouro);
		if (matcher.find()) {
			return new LogradouroFormatadoDTO(matcher.group(1).trim(), matcher.group(4).trim());
		}

		// Avenida Paulista 200 (Sem a virgula)
		pattern = Pattern.compile("([\\s\\S][^,]+)(\\s+([0-9])+)$");
		matcher = pattern.matcher(logradouro);
		if (matcher.find()) {
			return new LogradouroFormatadoDTO(matcher.group(1).trim(), matcher.group(2).trim());
		}

		// 3 numeros
		// Esse mais raro ainda. Peguei só 1
		// Alameda Barão de Limeira 401/425/454
		pattern = Pattern.compile("\\s(\\d+)/(\\d+)/(\\d+)$");
		matcher = pattern.matcher(logradouro);
		if (matcher.find()) {
			return new LogradouroFormatadoDTO(matcher.replaceFirst("").trim(), matcher.group(0).trim());
		}

		// Ambos
		// Rua Brigadeiro Tobias 110/118
		// foi colocado um espaço antes "/s" para evitar problema com logradouros que
		// terminam com mais de 2 números
		pattern = Pattern.compile("\\s(\\d+.)/(\\d+)$");
		matcher = pattern.matcher(logradouro);
		if (matcher.find()) {
			return new LogradouroFormatadoDTO(matcher.replaceFirst("").trim(), matcher.group(0).trim());
		}
		
		return new LogradouroFormatadoDTO(logradouro, "");
	}
}
