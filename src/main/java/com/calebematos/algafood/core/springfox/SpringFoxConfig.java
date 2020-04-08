package com.calebematos.algafood.core.springfox;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.calebematos.algafood.api.exceptionhandler.Problem;
import com.calebematos.algafood.api.v1.model.CidadeModel;
import com.calebematos.algafood.api.v1.model.CozinhaModel;
import com.calebematos.algafood.api.v1.model.EstadoModel;
import com.calebematos.algafood.api.v1.model.FormaPagamentoModel;
import com.calebematos.algafood.api.v1.model.GrupoModel;
import com.calebematos.algafood.api.v1.model.PedidoResumoModel;
import com.calebematos.algafood.api.v1.model.PermissaoModel;
import com.calebematos.algafood.api.v1.model.ProdutoModel;
import com.calebematos.algafood.api.v1.model.RestauranteBasicoModel;
import com.calebematos.algafood.api.v1.model.UsuarioModel;
import com.calebematos.algafood.api.v1.openapi.model.CidadesModelOpenApi;
import com.calebematos.algafood.api.v1.openapi.model.CozinhasModelOpenApi;
import com.calebematos.algafood.api.v1.openapi.model.EstadosModelOpenApi;
import com.calebematos.algafood.api.v1.openapi.model.FormasPagamentoModelOpenApi;
import com.calebematos.algafood.api.v1.openapi.model.GruposModelOpenApi;
import com.calebematos.algafood.api.v1.openapi.model.LinksModelOpenApi;
import com.calebematos.algafood.api.v1.openapi.model.PageableModelOpenApi;
import com.calebematos.algafood.api.v1.openapi.model.PedidoResumoModelOpenApi;
import com.calebematos.algafood.api.v1.openapi.model.PedidosResumoModelOpenApi;
import com.calebematos.algafood.api.v1.openapi.model.PermissoesModelOpenApi;
import com.calebematos.algafood.api.v1.openapi.model.ProdutosModelOpenApi;
import com.calebematos.algafood.api.v1.openapi.model.RestaurantesBasicoModelOpenApi;
import com.calebematos.algafood.api.v1.openapi.model.UsuariosModelOpenApi;
import com.fasterxml.classmate.TypeResolver;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig implements WebMvcConfigurer{

	@Bean
	public Docket apiDocketV1() {
		TypeResolver typeResolver = new TypeResolver();
		
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("V1")
				.select()
					.apis(RequestHandlerSelectors.basePackage("com.calebematos.algafood.api.controller"))
					.paths(PathSelectors.ant("/v1/**"))
					.build()
				.useDefaultResponseMessages(false)
				.globalResponseMessage(RequestMethod.GET, globalGetResponseMessages())
	            .globalResponseMessage(RequestMethod.POST, globalPostPutResponseMessages())
	            .globalResponseMessage(RequestMethod.PUT, globalPostPutResponseMessages())
	            .globalResponseMessage(RequestMethod.DELETE, globalDeleteResponseMessages())
	            .ignoredParameterTypes(ServletWebRequest.class)
	            .additionalModels(typeResolver.resolve(Problem.class))
	            .directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
	            .directModelSubstitute(Links.class, LinksModelOpenApi.class)
	            .alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(PagedModel.class, CozinhaModel.class),
						CozinhasModelOpenApi.class))
	            .alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(Page.class, PedidoResumoModel.class),
						PedidoResumoModelOpenApi.class))
	            .alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, CidadeModel.class),
						CidadesModelOpenApi.class))
	            .alternateTypeRules(AlternateTypeRules.newRule(
	                    typeResolver.resolve(CollectionModel.class, EstadoModel.class),
	                    EstadosModelOpenApi.class))
	            .alternateTypeRules(AlternateTypeRules.newRule(
	            	    typeResolver.resolve(CollectionModel.class, FormaPagamentoModel.class),
	            	    FormasPagamentoModelOpenApi.class))
	            .alternateTypeRules(AlternateTypeRules.newRule(
	            	    typeResolver.resolve(CollectionModel.class, GrupoModel.class),
	            	    GruposModelOpenApi.class))
            	.alternateTypeRules(AlternateTypeRules.newRule(
	            	        typeResolver.resolve(CollectionModel.class, PermissaoModel.class),
	            	        PermissoesModelOpenApi.class))
            	.alternateTypeRules(AlternateTypeRules.newRule(
            		    typeResolver.resolve(PagedModel.class, PedidoResumoModel.class),
            		    PedidosResumoModelOpenApi.class))
            	.alternateTypeRules(AlternateTypeRules.newRule(
            		    typeResolver.resolve(CollectionModel.class, ProdutoModel.class),
            		    ProdutosModelOpenApi.class))
            	.alternateTypeRules(AlternateTypeRules.newRule(
            		    typeResolver.resolve(CollectionModel.class, RestauranteBasicoModel.class),
            		    RestaurantesBasicoModelOpenApi.class))
           		.alternateTypeRules(AlternateTypeRules.newRule(
            		        typeResolver.resolve(CollectionModel.class, UsuarioModel.class),
            		        UsuariosModelOpenApi.class))
				.apiInfo(apiInfoV1())
				.tags(new Tag("Cidades", "Gerencia as cidades"), 
						new Tag("Cozinhas", "Gerencia as cozinhas"),
						new Tag("Estados", "Gerencia os estados"),
						new Tag("Grupos", "Gerencia os grupos de usuários"),
						new Tag("Formas de pagamento", "Gerencia as formas de pagamento"),
						new Tag("Pedidos", "Gerencia os pedidos"),
						new Tag("Restaurantes", "Gerencia os restaurantes"),
						new Tag("Produtos", "Gerencia os produtos de restaurantes"),
						new Tag("Usuários", "Gerencia os usuários"),
						new Tag("Estatísticas", "Estatísticas da AlgaFood"),
						 new Tag("Permissões", "Gerencia as permissões"));
	}
	
	@Bean
	public Docket apiDocketV2() {
		TypeResolver typeResolver = new TypeResolver();
		
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("V2")
				.select()
					.apis(RequestHandlerSelectors.basePackage("com.calebematos.algafood.api.controller"))
					.paths(PathSelectors.ant("/v2/**"))
					.build()
				.useDefaultResponseMessages(false)
				.globalResponseMessage(RequestMethod.GET, globalGetResponseMessages())
	            .globalResponseMessage(RequestMethod.POST, globalPostPutResponseMessages())
	            .globalResponseMessage(RequestMethod.PUT, globalPostPutResponseMessages())
	            .globalResponseMessage(RequestMethod.DELETE, globalDeleteResponseMessages())
	            .ignoredParameterTypes(ServletWebRequest.class)
	            .additionalModels(typeResolver.resolve(Problem.class))
	            .directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
	            .directModelSubstitute(Links.class, LinksModelOpenApi.class)
	           
				.apiInfo(apiInfoV2());
	}
	
	private List<ResponseMessage> globalGetResponseMessages() {
		return Arrays.asList(
				new ResponseMessageBuilder()
					.code(HttpStatus.INTERNAL_SERVER_ERROR.value())
					.message("Erro interno do servidor")
					.responseModel(new ModelRef("Problema"))
					.build(),
				new ResponseMessageBuilder()
					.code(HttpStatus.NOT_ACCEPTABLE.value())
					.message("Recurso não possui representação que poderia ser aceita pelo consumidor")
					.build()
			);
	}
	
	private List<ResponseMessage> globalPostPutResponseMessages() {
	    return Arrays.asList(
	            new ResponseMessageBuilder()
	                .code(HttpStatus.BAD_REQUEST.value())
	                .message("Requisição inválida (erro do cliente)")
	                .responseModel(new ModelRef("Problema"))
	                .build(),
	            new ResponseMessageBuilder()
	                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
	                .message("Erro interno no servidor")
	                .responseModel(new ModelRef("Problema"))
	                .build(),
	            new ResponseMessageBuilder()
	                .code(HttpStatus.NOT_ACCEPTABLE.value())
	                .message("Recurso não possui representação que poderia ser aceita pelo consumidor")
	                .build(),
	            new ResponseMessageBuilder()
	                .code(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
	                .message("Requisição recusada porque o corpo está em um formato não suportado")
	                .responseModel(new ModelRef("Problema"))
	                .build()
	        );
	}

	private List<ResponseMessage> globalDeleteResponseMessages() {
	    return Arrays.asList(
	            new ResponseMessageBuilder()
	                .code(HttpStatus.BAD_REQUEST.value())
	                .message("Requisição inválida (erro do cliente)")
	                .responseModel(new ModelRef("Problema"))
	                .build(),
	            new ResponseMessageBuilder()
	                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
	                .message("Erro interno no servidor")
	                .responseModel(new ModelRef("Problema"))
	                .build()
	        );
	}
	
	private ApiInfo apiInfoV1() {
		return new ApiInfoBuilder()
				.title("AlgaFood API")
				.description("API aberta para clientes e restaurantes")
				.version("1")
				.contact(new Contact("Lucas Calebe", "https://www.calebematos.com.br", "calebematos@gmail.com"))
				.build();
	}
	
	private ApiInfo apiInfoV2() {
		return new ApiInfoBuilder()
				.title("AlgaFood API")
				.description("API aberta para clientes e restaurantes")
				.version("1")
				.contact(new Contact("Lucas Calebe", "https://www.calebematos.com.br", "calebematos@gmail.com"))
				.build();
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/swagger-ui.html")
			.addResourceLocations("classpath:/META-INF/resources/");

		registry.addResourceHandler("/webjars/**")
			.addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
	
}
