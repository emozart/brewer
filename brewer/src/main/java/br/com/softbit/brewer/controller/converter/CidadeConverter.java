package br.com.softbit.brewer.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.thymeleaf.util.StringUtils;

import br.com.softbit.brewer.model.Cidade;

public class CidadeConverter  implements Converter<String,Cidade>{

	@Override
	public Cidade convert(String codigo) {
		if(!StringUtils.isEmpty(codigo)){
			Cidade cidade = new Cidade();
			cidade.setCodigo(Long.valueOf(codigo));
			return cidade;
		}
		return null;
	}

}
