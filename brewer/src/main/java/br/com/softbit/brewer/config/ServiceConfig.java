package br.com.softbit.brewer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import br.com.softbit.brewer.service.CadastroCervejaService;
import br.com.softbit.brewer.storage.FotoStorage;
import br.com.softbit.brewer.storage.local.FotoStorageLocal;

@Configuration
@ComponentScan(basePackageClasses=CadastroCervejaService.class)
public class ServiceConfig {
	
	@Bean
	public FotoStorage fotoStorage(){
		return new FotoStorageLocal();
	}
	
}
