package com.tcc.projeto_a.facade;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.tcc.projeto_a.formulario.FormParametro;
import com.tcc.projeto_a.model.Edital;
import com.tcc.projeto_a.model.Parametro;
import com.tcc.projeto_a.model.ParametroBuscado;
import com.tcc.projeto_a.model.Salario;
import com.tcc.projeto_a.repository.ClassificadorSvmRepository;
import com.tcc.projeto_a.repository.EditalRepository;
import com.tcc.projeto_a.repository.ParametroBuscadoRepository;
import com.tcc.projeto_a.repository.ParametroRepository;
import com.tcc.projeto_a.repository.ViewInformacaoEditalRepository;

public class FSvm {
	
	private List<Salario> salariosPorQuartil;
	private FormParametro formParametro;
	private ParametroRepository parametrosR;
	
	private ClassificadorSvmRepository classificadores;	
	private ViewInformacaoEditalRepository viewsInformacoes;
	private ParametroBuscadoRepository parametrosBuscados;
	private Connection conexao;
	private String nomeView;
	private EditalRepository editalRepository;
	
	public FSvm(List<Salario> salariosPorQuartil, FormParametro formParametro,ParametroRepository parametrosR, ClassificadorSvmRepository classificadores,ViewInformacaoEditalRepository viewsInformacoes,ParametroBuscadoRepository parametrosBuscados, EditalRepository editalRepository) {
		super();
		this.salariosPorQuartil = salariosPorQuartil;
		this.formParametro = formParametro;
		this.parametrosR = parametrosR;
		this.classificadores = classificadores;
		this.viewsInformacoes = viewsInformacoes;
		this.parametrosBuscados = parametrosBuscados;
		this.conexao = Conexao.getConnection();
		this.nomeView = "informacao_sim_";
		this.editalRepository = editalRepository ;
	}
	
	public FSvm() {
		
	}
	
	public List<String> viewParametros(List<Long> idsSalarios ){
		System.out.println(this.formParametro.getIdArea());
		Parametro parametroArea = this.parametrosR.buscarParametroIdArea(this.formParametro);
		System.out.println("fez a consulta por area");
		Parametro parametroCidade  = this.parametrosR.buscarParametroIdCidade(formParametro);
		Parametro parametroDisciplina = this.parametrosR.buscarParametroIdDisciplina(formParametro);
		Parametro parametroEscolaridade = this.parametrosR.buscarParametroIdEscolaridade(formParametro);
		Parametro parametroEsfera = this.parametrosR.buscarParametroIdEsfera(formParametro);
		Parametro parametroRequisitoCargo = this.parametrosR.buscarParametroIdRequisitoCargo(formParametro);
		List<Parametro> parametrosSalarios = this.parametrosR.buscarParametroIdSalarios(idsSalarios);
		List<String> idsEdital = new ArrayList<String>();
		System.out.println("parametroArea => "+ parametroArea + " parametroCidade =>" + parametroCidade + " parametroDisciplina =>"+ parametroDisciplina + " parametroEscolaridade =>" + parametroEscolaridade + " parametroEsfera =>"+ parametroEsfera + " parametroRequisitoCargo =>" + parametroRequisitoCargo + " parametrosSalarios.isEmpty() =>"+ parametrosSalarios.isEmpty());
		if( parametroArea != null && parametroCidade != null && parametroDisciplina != null && 
		    parametroEscolaridade != null && parametroEsfera != null && parametroRequisitoCargo != null &&
		    ! parametrosSalarios.isEmpty()  ){
			
			System.out.println("aqui 4");
			
			/*
			if( viewExiste(parametroArea) && viewExiste(parametroCidade) && viewExiste(parametroDisciplina) && 
			    viewExiste(parametroEscolaridade) && viewExiste(parametroEsfera) && viewExiste(parametroRequisitoCargo) ){	
				System.out.println("aqui 5");
				for (Parametro parametroSalario : parametrosSalarios) {
					if( ! viewExiste(parametroSalario) ){
						System.out.println("retornou null 2");
						return null;
					}
				}
				
				
			}
			*/
			idsEdital = editalIntersecao(parametroArea,parametroCidade,parametroDisciplina,parametroEscolaridade,parametroEsfera,parametroRequisitoCargo,parametrosSalarios);
			System.out.println("talvez nao seja null >>" + idsEdital);
			return idsEdital;
		}
		System.out.println("retornou null 1");
		return null;
	}
	
	private List<String> editalIntersecao(Parametro parametroArea,Parametro parametroCidade,Parametro parametroDisciplina,
			Parametro parametroEscolaridade,Parametro parametroEsfera,Parametro parametroRequisitocargo, List<Parametro> parametrosSalarios){
		List<String> idsEdital = new ArrayList<String>();
		List<String> idsEditalAcumulado = new ArrayList<String>();
		List<String> idsEditalAcumuladoSalvo = new ArrayList<String>();
		List<String> idsEditalAcumuladoNovo = new ArrayList<String>();
		
		idsEditalAcumulado = obterEdital(parametroArea);
		idsEdital = obterEdital(parametroCidade);
		idsEditalAcumulado = intersecao(idsEditalAcumulado,idsEdital);		
		if(idsEditalAcumulado.isEmpty()){
			return null;
		}
		idsEdital = obterEdital(parametroDisciplina);
		idsEditalAcumulado = intersecao(idsEditalAcumulado,idsEdital);		
		if(idsEditalAcumulado.isEmpty()){
			return null;
		}
		idsEdital = obterEdital(parametroEscolaridade);		
		idsEditalAcumulado = intersecao(idsEditalAcumulado,idsEdital);		
		if(idsEditalAcumulado.isEmpty()){
			return null;
		}
		idsEdital = obterEdital(parametroEsfera);
		idsEditalAcumulado = intersecao(idsEditalAcumulado,idsEdital);		
		if(idsEditalAcumulado.isEmpty()){
			return null;
		}
		idsEdital = obterEdital(parametroRequisitocargo);
		idsEditalAcumulado = intersecao(idsEditalAcumulado,idsEdital);
		if(idsEditalAcumulado.isEmpty()){
			return null;
		}
		idsEditalAcumuladoSalvo = idsEditalAcumulado; 
		
		for (Parametro parametro : parametrosSalarios) {
			idsEdital = obterEdital(parametro);
			idsEditalAcumulado = intersecao(idsEditalAcumuladoSalvo,idsEdital);
			if(!idsEditalAcumulado.isEmpty()){
				//idsEdital
				//idsEditalAcumuladoNovo.ad
				idsEditalAcumuladoNovo = intersecao(idsEditalAcumuladoSalvo,idsEditalAcumulado);
			}
		}
				
		
		
		//idsEdital.add(rs.getString("id"));
		//parei aqui, precisa fazer a interseção com o salario
		
		return !idsEditalAcumuladoNovo.isEmpty() ? idsEditalAcumuladoNovo : null;		
	}
	
	
	
	private List<String> intersecao(List<String> idsEditalAcumulado, List<String> idsEdital){
		List<String> intersecaoIdEdital = new ArrayList<String>();
		for (String idEdital : idsEdital) {
			if(idsEditalAcumulado.contains(idEdital)){
				intersecaoIdEdital.add(idEdital);
			}
		}
		return intersecaoIdEdital;
	}
	
	private List<String> obterEdital(Parametro parametro){
		List<String> idsEdital = new ArrayList<String>();
		try {			
			Statement stmt = this.conexao.createStatement();
			System.out.println("testeeee abaixo");
			System.out.println("parametro id>>" + parametro.getId()+ " parametroBuscado>>" + parametro.getParametroBuscado().getId());
			ResultSet rs = stmt.executeQuery("SELECT id FROM projeto_a."+ this.nomeView+String.valueOf(parametro.getParametroBuscado().getId() ) );				
			while(rs.next()){
				idsEdital.add( rs.getString("id") );
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		return idsEdital;
	}
	
	private Boolean viewExiste(Parametro parametro) {
		ResultSet view;
		try {
			view = this.conexao.getMetaData().getTables(null, null, this.nomeView+String.valueOf(parametro.getId()) , null);
			if(view.next()){
				return true;
			}		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return false;
	}
	
	public List<Edital> svm(){
		System.out.println(this.salariosPorQuartil);
		System.out.println(this.formParametro);
		//List<Parametro> parametros = parametrosR.buscarParametro(this.salariosPorQuartil,this.formParametro);		
		//System.out.println(parametros);
		System.out.println("aquii");
		//System.out.println(this.salariosPorQuartil.get(0).getId());
		List<Long> idsSalarios = getIdSalarios(this.salariosPorQuartil);
		//String idsSalarios = "15";
		//System.out.println(idsSalarios);
		System.out.println("la");
		List<String> idsEditaisSelecionados = new ArrayList<String>(); 
		idsEditaisSelecionados =  viewParametros(idsSalarios);
		
		//areas.findAll();
		System.out.println("testar se eh null => " + idsEditaisSelecionados);
		if(idsEditaisSelecionados == null || idsEditaisSelecionados.isEmpty()){
			System.out.println("eh null");			
			return null; 
		}else{
		
			//System.out.println("não eh null");
			//return null;
			List <Long> idsEditaisSelecionadosLong = new ArrayList<Long>();
			for (String id : idsEditaisSelecionados) {
				idsEditaisSelecionadosLong.add(Long.parseLong(id));
			}
			return this.editalRepository.buscarEditais(idsEditaisSelecionadosLong);
			//return selecionarClassificadores(parametros);		
		}
	}

	private List<List<Edital>> selecionarClassificadores(List<Parametro> parametros){
		
		List<ParametroBuscado> parametrosBuscados = this.parametrosBuscados.buscarClassificadores(parametros);
		List<Edital> editalSim = new ArrayList<Edital>();
		List<Edital> editalNao = new ArrayList<Edital>();
		List<List<Edital>> editais = new ArrayList<List<Edital>>();
		
		for (ParametroBuscado parametroBuscado : parametrosBuscados) {
			editalSim.add(parametroBuscado.getClassificadorSvm().getViewEditalSim());
			editalNao.add(parametroBuscado.getClassificadorSvm().getViewEditalNao());
		}		
		
		editais.add(editalSim);
		editais.add(editalNao);
		return editais;
	}
	
	private List<Long> getIdSalarios(List<Salario> salariosPorQuartil){
		List<Long> idsSalarios = new ArrayList<Long>();
		for (Salario salario : salariosPorQuartil) {
			idsSalarios.add(salario.getId()); 			
		}
		return idsSalarios;
	}

}
