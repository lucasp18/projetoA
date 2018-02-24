package com.tcc.projeto_a.facade;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.tcc.projeto_a.model.ClassificadorSvm;
import com.tcc.projeto_a.model.Parametro;
import com.tcc.projeto_a.model.ParametroBuscado;
import com.tcc.projeto_a.model.QuartilSalario;
import com.tcc.projeto_a.model.ViewInformacaoEdital;
import com.tcc.projeto_a.repository.AreaRepository;
import com.tcc.projeto_a.repository.CidadeRepository;
import com.tcc.projeto_a.repository.ClassificadorSvmRepository;
import com.tcc.projeto_a.repository.DisciplinaRepository;
import com.tcc.projeto_a.repository.EditalRepository;
import com.tcc.projeto_a.repository.Escolaridade2Repository;
import com.tcc.projeto_a.repository.EsferaRepository;
import com.tcc.projeto_a.repository.ParametroBuscadoRepository;
import com.tcc.projeto_a.repository.ParametroEditalRepository;
import com.tcc.projeto_a.repository.ParametroRepository;
import com.tcc.projeto_a.repository.QuartilSalarioRepository;
import com.tcc.projeto_a.repository.RequisitoCargoRepository;
import com.tcc.projeto_a.repository.SalarioRepository;
import com.tcc.projeto_a.repository.ViewInformacaoEditalRepository;

import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.WekaPackageManager;
import weka.experiment.InstanceQuery;
import weka.filters.Filter;

@Service
@Configurable
public class FClassificacao {
	
	private ParametroRepository parametroR;
	private ClassificadorSvmRepository classificadorSvmR; 
	private ViewInformacaoEditalRepository viewInformacaoRepository;
	private EditalRepository editalR;
	private InstanceQuery queryWeka;  
	private Connection conexaoBd;
	private DisciplinaRepository disciplinaR;
	private AreaRepository areaR;
	private Escolaridade2Repository escolaridadeR;
	private EsferaRepository esferaR;
	private RequisitoCargoRepository requisitoCargoR;
	private ParametroBuscadoRepository parametroBuscadoR;
	private ParametroEditalRepository parametroEditalR;
	private SalarioRepository salarioR;
	private CidadeRepository cidadeR;	
    private QuartilSalarioRepository quartilSalarioR;
	
	public FClassificacao(ParametroRepository parametroR, ClassificadorSvmRepository classificadorSvmR,
			ViewInformacaoEditalRepository viewInformacaoRepository,EditalRepository editalR,
		DisciplinaRepository disciplinaR,AreaRepository areaR, Escolaridade2Repository escolaridadeR,
			EsferaRepository esferaR,RequisitoCargoRepository requisitoCargoR,ParametroBuscadoRepository parametroBuscadoR,
			ParametroEditalRepository parametroEditalR,CidadeRepository cidadeR,SalarioRepository salarioR, QuartilSalarioRepository quartilSalarioR) {
		super();
		this.parametroR = parametroR;
		this.classificadorSvmR = classificadorSvmR;
		this.viewInformacaoRepository = viewInformacaoRepository;
		this.editalR = editalR;
		this.disciplinaR = disciplinaR;
		this.areaR = areaR;
		this.escolaridadeR = escolaridadeR;
		this.esferaR = esferaR;
		this.requisitoCargoR = requisitoCargoR;
		this.parametroBuscadoR = parametroBuscadoR; 
		this.parametroEditalR = parametroEditalR;
		this.cidadeR = cidadeR;
		this.salarioR = salarioR;
		this.quartilSalarioR = quartilSalarioR;
		conexaoWeka();
		conexaoBd();
	}
	
	private void conexaoWeka(){
		try {
			this.queryWeka = new InstanceQuery();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.queryWeka.setUsername("postgres");
		this.queryWeka.setPassword("postgres");
		this.queryWeka.setDatabaseURL("jdbc:postgresql://localhost:5432/tcc");        
	}
	
	private void conexaoBd(){
		this.conexaoBd = Conexao.getConnection(); 
	}
	
	public boolean reclassificar(int v){
		List<Integer> ids = this.parametroR.todosIdsEscolaridade();		
		Instances dados = null,dadosIds = null;
		AbstractClassifier svm;
		Parametro parametro = null;
		System.out.println("ids escolaridade"+ids);
		if(calcularSalarios()){
			return true;
		}
		
		try {
			//dadosIds = parametrosIds();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (Integer id : ids) {
			parametro = new Parametro();
			//System.out.println("idDisciplina = "+id);
			parametro.setEscolaridade(this.escolaridadeR.findOne(new Long(id)));
			
			try {
				dados = parametrosEscolaridade(id,false);			
				dadosIds = parametrosEscolaridade(id,true);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			svm = fabricarSvm();
			//if(dados.numClasses() == 2){
				salvarClassificar(dados, svm, parametro,dadosIds);
			//}
		}
		
		List<Integer> idsDisciplinas = this.parametroR.todosIdsDisciplina();		
		dados = null;
		svm = null;
		parametro = null;
		System.out.println("ids disciplinas"+ idsDisciplinas);
		for (Integer idDisciplina : idsDisciplinas) {
			parametro = new Parametro();
			System.out.println("idDisciplina = "+idDisciplina);
			parametro.setDisciplina(this.disciplinaR.findOne(new Long(idDisciplina)));
			
			try {
				dados = parametrosDisciplinaId(idDisciplina,false);			
				dadosIds = parametrosDisciplinaId(idDisciplina,true);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			svm = fabricarSvm();
			
			//if(dados.numClasses() == 2){
				salvarClassificar(dados, svm, parametro,dadosIds);
			//}
		}
		
		ids = this.parametroR.todosIdsArea();		
		dados = null;
		svm = null;
		parametro = null;
		
		for (Integer id : ids) {
			parametro = new Parametro();
			System.out.println("idArea = "+id);
			parametro.setArea(this.areaR.findOne(new Long(id)));
			
			try {
				dados = parametrosAreaId(id,false);			
				dadosIds = parametrosAreaId(id,true);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			svm = fabricarSvm();
			
			//if(dados.numClasses() == 2){
				salvarClassificar(dados, svm, parametro,dadosIds);
			//}
		}
		
		
		ids = this.parametroR.todosIdsSalario();		
		dados = null;
		svm = null;
		parametro = null;
		
		for (Integer id : ids) {
			parametro = new Parametro();
			System.out.println("SalarioId = "+id);
			parametro.setSalario(this.salarioR.findOne(new Long(id)));
			
			try {
				dados = parametrosSalarioId(id,false);			
				dadosIds = parametrosSalarioId(id,true);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			svm = fabricarSvm();
			//if(dados.numClasses() == 2){
				salvarClassificar(dados, svm, parametro,dadosIds);						
			//}
		}
		
		
		ids = this.parametroR.todosIdsEsfera();		
		dados = null;
		svm = null;
		parametro = null;
		
		for (Integer id : ids) {
			parametro = new Parametro();
			System.out.println("idEsfera = "+id);
			parametro.setEsfera(this.esferaR.findOne(new Long(id)));
			
			try {
				dados = parametrosEsferaId(id,false);			
				dadosIds = parametrosEsferaId(id,true);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			svm = fabricarSvm();
			//if(dados.numClasses() == 2){
				salvarClassificar(dados, svm, parametro,dadosIds);
			//}
		}
		
		ids = this.parametroR.todosIdsRequisitoCargo();		
		dados = null;
		svm = null;
		parametro = null;
		
		for (Integer id : ids) {
			parametro = new Parametro();
			System.out.println("requisito cargo = "+id);
			parametro.setRequisitoCargo(this.requisitoCargoR.findOne(new Long(id)));
			
			try {
				dados = parametrosRequisitoCargoId(id,false);			
				dadosIds = parametrosRequisitoCargoId(id,true);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			svm = fabricarSvm();
			//if(dados.numClasses() == 2){
				salvarClassificar(dados, svm, parametro,dadosIds);
			//}
		}
		
		ids = this.parametroR.todosIdsCidade();		
		dados = null;
		svm = null;
		parametro = null;
		
		for (Integer id : ids) {
			parametro = new Parametro();
			System.out.println("idCidade = "+id);
			parametro.setCidade(this.cidadeR.findOne(new Long(id)));
			
			try {
				dados = parametrosCidadeId(id,false);			
				dadosIds = parametrosCidadeId(id,true);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			svm = fabricarSvm();
			//if(dados.numClasses() == 2){
				salvarClassificar(dados, svm, parametro,dadosIds);						
			//}
		}
		
		calcularSalarios();
		//System.out.println("fimmm "+ p);
		return false;
	}
	
	private boolean calcularSalarios(){
		List<Double> salarios = this.salarioR.valorSalarios();
		Collections.sort(salarios);
		System.out.println(salarios);
		int interacao = (int) Math.round( salarios.size() / 4.0);
		int iteradorInicio = interacao;
		System.out.println("Math.round( salarios.size() / 4)" + Math.round( salarios.size() / 4.0) + " salarios.size() / 4.0 = "+(salarios.size() / 4.0));
		System.out.println("interacao "+interacao);
		System.out.println("total salarios "+salarios.size());
		String inicio = "0";
		String fim = "-1";
		int prox = interacao -1 ;
		String p = "", z = "";
		QuartilSalario quartilSalario;
		this.quartilSalarioR.deleteAll();
		for (int i = 0; i < salarios.size(); i++) {		
			if(prox == i){				
				quartilSalario = new QuartilSalario();
				quartilSalario.setDe(Double.valueOf(inicio));
				quartilSalario.setSeparador("entre");
				//z = inicio + " entre ";
				//p = inicio+" entre "+ fim +"; "+p; 
				if(i+1 == salarios.size()){
					//se for ultimo
					fim = String.valueOf(Math.round(Double.valueOf(String.valueOf(salarios.get(i)))+ 1.0));
				}else{
					fim = String.valueOf(Math.round(Double.valueOf(String.valueOf(salarios.get(i))))+ 1.99);
				}
				
				quartilSalario.setAte(Double.valueOf(fim));
				//z = z + fim;
				System.out.println(quartilSalario);
				System.out.println(this.quartilSalarioR);
				this.quartilSalarioR.saveAndFlush(quartilSalario);
				inicio = String.valueOf( Math.round(Double.valueOf( Double.valueOf(String.valueOf(salarios.get(i)) )) + 2.00));
				prox = i + interacao;
				//p = p+" ; "+z;
				//System.out.println("inicio "+ inicio +"fim "+fim);
			}
		}
		return true;
	}
	
	
	private Instances filtro(Instances instances){
		System.out.println("inicio filtro: " + instances.numInstances());
		
		
		if (instances.classIndex() == -1){
			instances.setClassIndex(instances.numAttributes() - 1);
	    }
		Instances antigasInstances = new Instances(instances);
		
		Instances instancesSim = new Instances(instances), instancesNao = new Instances(instances) , instacesBalanceada = new Instances(instances);
		instancesSim.clear();
		instancesNao.clear();
		instacesBalanceada.clear();
		System.out.println("instances >"+ instances.numInstances());
		System.out.println("instances2 >"+ instances.numInstances());
		for (int i = 0; i < instances.numInstances(); i++) {
			System.out.println("vai");			
			if(instances.instance(i).classValue() == 1.0 ){
				instancesSim.add(instances.instance(i));
			}else{
				instancesNao.add(instances.instance(i));
			}
		}
	
		System.out.println("sim "+ instancesSim.numInstances() + " não "+ instancesNao.numInstances()  );
		if( instancesSim.numInstances() == instancesNao.numInstances() ){
			System.out.println("final de igual filtro =>" + antigasInstances.numInstances());
			return antigasInstances;
		}else if(instancesSim.numInstances() > instancesNao.numInstances() ){
			instacesBalanceada = instancesNao;
			int qtdNao;
			if( instancesNao.numInstances() < (instancesSim.numInstances() - instancesNao.numInstances())){
				qtdNao = instancesNao.numInstances();
			}else{
				qtdNao = instancesSim.numInstances() - (instancesSim.numInstances() - instancesNao.numInstances()) ;
			}
			if(qtdNao < 10){
				qtdNao = 10;
				return antigasInstances;				
			}
			
			int qtdBalanciado = 0 , index;
			Random gerador = new Random();
			while( qtdBalanciado < qtdNao ){
				index = gerador.nextInt(instancesSim.numInstances());
				instacesBalanceada.add(instancesSim.instance(index));
				instancesSim.remove(index);
				qtdBalanciado++;
			}					
		}else{
			instacesBalanceada = instancesSim;
			int qtdSim;
			if( instancesSim.numInstances() < (instancesNao.numInstances() - instancesSim.numInstances())){
				qtdSim = instancesSim.numInstances();
			}else{
				qtdSim = instancesNao.numInstances() - ( instancesNao.numInstances() - instancesSim.numInstances()) ;
			}
			if(qtdSim < 10){
				qtdSim = 10;
				return antigasInstances;
			}
			
			int qtdBalanciado = 0 , index;
			Random gerador = new Random();
			while( qtdBalanciado < qtdSim ){
				index = gerador.nextInt(instancesNao.numInstances());
				instacesBalanceada.add(instancesNao.instance(index));
				instancesNao.remove(index);
				qtdBalanciado++;
			}
		}
		System.out.println("qtd: "+instacesBalanceada.numInstances() );
		return instacesBalanceada;
	}
	
	public void salvarClassificar(Instances dados, AbstractClassifier svm,Parametro parametro,Instances dadosIds){
		Instances dados2;
		Evaluation eval = null;		
		String padraoSim = "informacao_sim_";
		String padraoNao = "informacao_nao_";
		
		if (dados.classIndex() == -1){
			dados.setClassIndex(dados.numAttributes() - 1);
	    }		
		
		if(dados.numClasses() != 2){
			return;
		}
		
		try {
			//weka.filters.supervised.instance.ClassBalancer t = new weka.filters.supervised.instance.ClassBalancer();
			//weka.filters.supervised.instance.StratifiedRemoveFolds t = new weka.filters.supervised.instance.StratifiedRemoveFolds(); 
			//t.setInputFormat(dados);
			dados2 = filtro(dados); //trabalhando aqui
			svm.buildClassifier(dados2);
			try {
				eval = crossValidation(dados2, svm);
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("eval.kappa() ="+eval.kappa());
		System.out.println("eval class 0 = "+eval.fMeasure(0)+" eval class 1=  "+eval.fMeasure(1)+" id_classificador");
		if(eval.kappa() >= 0.82){
			
		
		//parametro = this.parametroR.save(parametro);
		ClassificadorSvm cSvm = new ClassificadorSvm();
		cSvm.setByteClassificador(converterClassificadorParaByte(svm));
		cSvm.setPrecisao(eval.correct()/eval.numInstances() * 100);
		cSvm.setKappa(eval.kappa());
		
		
		cSvm = classificadorSvmR.save(cSvm);
		ParametroBuscado parametroBuscado = new ParametroBuscado();
		//System.out.println("parametro_id="+parametro.getId());
		parametro = this.parametroR.save(parametro);
		parametroBuscado.setParametro(parametro);
		parametroBuscado.setClassificadorSvm(cSvm);
		//if ( cSvm.getKappa() >= 0.82 ){
		parametroBuscado.setBomClassificador(true);
		//}else{
			//parametroBuscado.setBomClassificador(false);
		//}
		
		parametroBuscado = this.parametroBuscadoR.save(parametroBuscado);
		System.out.println("parametroBuscado.get="+parametroBuscado.getParametro().getId());
		System.out.println("depois de salvar");
				
		ViewInformacaoEdital vEdital = new ViewInformacaoEdital();
		vEdital.setClassificadorSvm(cSvm);
		
		boolean EditalTemSim = false;
		boolean EditalNaoTem = false;
		int p = 0;
		List<Long> idsEditalSim = new ArrayList<Long>();
		List<Long> idsEditalNao = new ArrayList<Long>();		
		
		for (int j = 0; j < dados.size(); j++) {						
			try{							
				p++;
				System.out.println("svm.classifyInstance(dados2.instance(j))"+ svm.classifyInstance(dados.instance(j))+"instance>>"+ dados.instance(j) );
				if(svm.classifyInstance(dados.instance(j)) != dados.instance(j).classValue()){
					System.out.println("classificou errado!!");
				}
				if(svm.classifyInstance(dados.instance(j)) == 1.0){
					System.out.println("aqui >>>>"+ dadosIds.instance(j).value(0) + "---" +dadosIds.instance(j) );
					if(!idsEditalSim.contains(this.parametroEditalR.buscarParametroEdital(Long.valueOf( String.valueOf((int)dadosIds.instance(j).value(0)))).getIdEdital().getId())){
						idsEditalSim.add( this.parametroEditalR.buscarParametroEdital(Long.valueOf( String.valueOf((int)dadosIds.instance(j).value(0)))).getIdEdital().getId());
					}
										
					EditalTemSim = true;
				}else if(svm.classifyInstance(dados.instance(j)) == 0.0){
					if(!idsEditalNao.contains(this.parametroEditalR.buscarParametroEdital(Long.valueOf( String.valueOf((int)dadosIds.instance(j).value(0)))).getIdEdital().getId())){
						idsEditalNao.add( this.parametroEditalR.buscarParametroEdital(Long.valueOf( String.valueOf((int)dadosIds.instance(j).value(0)))).getIdEdital().getId());
					}
					
					EditalNaoTem = true;				
				}else{
					throw new Exception("Existe um parametro que fez o classificador não ser capaz de fazer a diferença entre duas classes");
				}							
				if(EditalTemSim && EditalNaoTem ){
					//break; // o edital tem os dois parametros
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		//idsEditalSim.re/
		//System.out.println(idsEditalSim.stream().distinct().toArray().toString());
		System.out.println("string = " + idsEditalSim.toString());
		if(cSvm.getKappa() >= 0.82){
			vEdital = viewInformacaoRepository.save(vEdital);
			System.out.println("quantidade de dados => "+dados.size()+"vinformacao_edital=>>"+vEdital.getId()+" qtd testado=>"+p);
			if(EditalTemSim == true){
				vEdital.setNomeViewSim(padraoSim+vEdital.getId());
			}
			
			if(EditalNaoTem == true){
				vEdital.setNomeViewNao(padraoNao+vEdital.getId());
			}
					
			vEdital = viewInformacaoRepository.save(vEdital);
			System.out.println("id = " + parametroBuscado.getParametro().getId());
			//ParametroEdital parametroEdital = this.parametroEditalR.buscarParametroEdital(parametroBuscado.getParametro().getId());
			List<Long> idsNovosNao = new ArrayList<Long>();
			if(EditalTemSim == true && EditalNaoTem == true ){				
				System.out.println("inicio");
			
				for (Long idEditalNao : idsEditalNao) {
					if(!idsEditalSim.contains(idEditalNao)){
						idsNovosNao.add(idEditalNao);
					}
				}
				System.out.println("o sim >" + idsEditalSim );
				System.out.println("tava assim >"+ idsEditalNao );
				idsEditalNao = idsNovosNao; 
				System.out.println("ficou assim  >"+ idsEditalNao );
				System.out.println("fim");
				if(idsEditalNao.isEmpty()){
					EditalNaoTem = false;
				}
			}
			
			if(EditalTemSim == true){
				criarView("sim",vEdital,idsEditalSim);			
			}
			
			if(EditalNaoTem == true){
				criarView("nao",vEdital,idsEditalNao);			
			}
		}
		}
	}
	
	
	private void criarView(String tipo, ViewInformacaoEdital vEdital,List<Long> idsEdital ){
		PreparedStatement query = null;		
		System.out.println("criou view");
		String nomeView = null;
		try{
			if(tipo.equals("sim")){
				nomeView = vEdital.getNomeViewSim();
			}else if(tipo.equals("nao")){
				nomeView = vEdital.getNomeViewNao();
			}
			
			//query = this.conexaoBd.prepareStatement("SELECT * FROM projeto_a."+nomeView);
		//	System.out.println(parametroEdital);
			String inIdEdital = "";
			for (Long idEdital : idsEdital) {
				inIdEdital+=idEdital+",";
			}
			System.out.println("nome view "+ nomeView + " idsEdital " + idsEdital);
			inIdEdital = inIdEdital.substring(0,inIdEdital.length()-1);
			System.out.println("vaii) "+inIdEdital);
			
			query = this.conexaoBd.prepareStatement("CREATE MATERIALIZED VIEW IF NOT EXISTS projeto_a."+nomeView+" "
					+ "AS select id from projeto_a.edital where id in( "+inIdEdital+" )");
			
			System.out.println("query view=>"+query.toString());
			query.executeUpdate();
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	private AbstractClassifier fabricarSvm(){
		WekaPackageManager.loadPackages( false, true, false );
		AbstractClassifier classificador = null;
		try {
			classificador = ( AbstractClassifier ) Class.forName("weka.classifiers.functions.LibSVM" ).newInstance();
			String options = ( "-S 0 -K 2 -D 3 -G 0.0 -R 0.0 -N 0.5 -M 40.0 -C 1.0 -E 0.001 -P 0.1" );
			String[] optionsArray = options.split(" ");
			try {
				classificador.setOptions( optionsArray );
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return classificador;
	}	
	
	
	private Instances parametrosEscolaridade(long id,boolean addId) throws Exception{                
        String coluna = "";		
		coluna = (addId ? "id" : "id_escolaridade");
		this.queryWeka.setQuery("SELECT "+ coluna
        		+ " , CASE WHEN id_escolaridade = "+id +"THEN 'SIM' "+
        		"ELSE" +
        		 	"'NÃO'" +			
        		 	"END  AS CLASSE FROM projeto_a.parametro"
        		 	+ " WHERE parametro.id_esfera is not null and parametro.id_salario is not null and parametro.id_escolaridade is not null and parametro.id_disciplina is not null and parametro.id_cidade is not null and parametro.id_area is not null and parametro.id_requisito_cargo is not null"
        		 	+ " ORDER BY CLASSE ");        
       // System.out.println("hora do dado1");
        
        Instances data = this.queryWeka.retrieveInstances();
        System.out.println("data2=> "+data);        
        //System.out.println("hora do dado2");                                 
		return data;
	}
	
	
	private Instances parametrosCidadeId(long id,boolean addId) throws Exception{                
		String coluna = "";		
		coluna = (addId ? "id" : "id_cidade");
		this.queryWeka.setQuery("SELECT "+coluna
        		+ " , CASE WHEN id_cidade = "+id+" THEN 'SIM' "+
        		"ELSE" +
        		 	"'NÃO'" +			
        		 	"END  AS CLASSE FROM projeto_a.parametro"
        		 	+ " WHERE parametro.id_esfera is not null and parametro.id_salario is not null and parametro.id_escolaridade is not null and parametro.id_disciplina is not null and parametro.id_cidade is not null and parametro.id_area is not null and parametro.id_requisito_cargo is not null"
        		 	+ " ORDER BY CLASSE ");        
       // System.out.println("hora do dado1");
        
        Instances data = this.queryWeka.retrieveInstances();
        System.out.println("data2=> "+data);
        //System.out.println("hora do dado2");                                 
		return data;
	}
	
	private Instances parametrosAreaId(long id,boolean addId) throws Exception{                
		String coluna = "";		
		coluna = (addId ? "id" : "id_area");
		this.queryWeka.setQuery("SELECT "+ coluna
        		+ " , CASE WHEN id_area = "+id+" THEN 'SIM' "+
        		"ELSE" +
        		 	"'NÃO'" +			
        		 	"END AS CLASSE FROM projeto_a.parametro"
        		 	+ " WHERE parametro.id_esfera is not null and parametro.id_salario is not null and parametro.id_escolaridade is not null and parametro.id_disciplina is not null and parametro.id_cidade is not null and parametro.id_area is not null and parametro.id_requisito_cargo is not null"
        		 	+ " ORDER BY CLASSE ");        
       // System.out.println("hora do dado1");
        
        Instances data = this.queryWeka.retrieveInstances();
        System.out.println("data2=> "+data);
        //System.out.println("hora do dado2");                                 
		return data;
	}
	
	private Instances parametrosDisciplinaId(long id,boolean addId) throws Exception{                
		String coluna = "";		
		coluna = (addId ? "id" : "id_disciplina");
		this.queryWeka.setQuery("SELECT " + coluna
        		+ " , CASE WHEN id_disciplina = "+id+" THEN 'SIM' "+
        		"ELSE" +
        		 	"'NÃO'" +			
        		 	"END  AS CLASSE FROM projeto_a.parametro"
        		 	+ " WHERE parametro.id_esfera is not null and parametro.id_salario is not null and parametro.id_escolaridade is not null and parametro.id_disciplina is not null and parametro.id_cidade is not null and parametro.id_area is not null and parametro.id_requisito_cargo is not null"
        		 	+ " ORDER BY CLASSE");        
       // System.out.println("hora do dado1");
        
        Instances data = this.queryWeka.retrieveInstances();
        System.out.println("data2=> "+data);
        //System.out.println("hora do dado2");                                 
		return data;
	}
	
	private Instances parametrosEsferaId(long id, boolean addId) throws Exception{                
		String coluna = "";		
		coluna = (addId ? "id" : "id_esfera");
		this.queryWeka.setQuery("SELECT " + coluna
        		+ " , CASE WHEN id_esfera = "+id+" THEN 'SIM' "+
        		"ELSE" +
        		 	"'NÃO'" +			
        		 	"END  AS CLASSE FROM projeto_a.parametro"
        		 	+ " WHERE parametro.id_esfera is not null and parametro.id_salario is not null and parametro.id_escolaridade is not null and parametro.id_disciplina is not null and parametro.id_cidade is not null and parametro.id_area is not null and parametro.id_requisito_cargo is not null"
        		 	+ " ORDER BY CLASSE");        
       // System.out.println("hora do dado1");
        
        Instances data = this.queryWeka.retrieveInstances();
        System.out.println("data2=> "+data);
        //System.out.println("hora do dado2");                                 
		return data;
	}
	
	private Instances parametrosRequisitoCargoId(long id,boolean addId) throws Exception{                
		String coluna = "";		
		coluna = (addId ? "id" : "id_requisito_cargo");
		this.queryWeka.setQuery("SELECT " + coluna
        		+ " , CASE WHEN id_requisito_cargo = "+id+" THEN 'SIM' "+
        		"ELSE" +
        		 	"'NÃO'" +			
        		 	"END  AS CLASSE FROM projeto_a.parametro"
        		 	+ " WHERE parametro.id_esfera is not null and parametro.id_salario is not null and parametro.id_escolaridade is not null and parametro.id_disciplina is not null and parametro.id_cidade is not null and parametro.id_area is not null and parametro.id_requisito_cargo is not null"
        		 	+ " ORDER BY CLASSE ");        
       // System.out.println("hora do dado1");
        
        Instances data = this.queryWeka.retrieveInstances();
        System.out.println("data2=> "+data);
        //System.out.println("hora do dado2");                                 
		return data;
	}
	
	private Instances parametrosSalarioId(long id,boolean addId) throws Exception{                
		String coluna = "";		
		coluna = (addId ? "id" : "id_salario");
		this.queryWeka.setQuery("SELECT " + coluna
        		+ " , CASE WHEN id_salario = "+id+" THEN 'SIM' "+
        		"ELSE" +
        		 	"'NÃO'" +			
        		 	"END  AS CLASSE FROM projeto_a.parametro"
        		 	+ " WHERE parametro.id_esfera is not null and parametro.id_salario is not null and parametro.id_escolaridade is not null and parametro.id_disciplina is not null and parametro.id_cidade is not null and parametro.id_area is not null and parametro.id_requisito_cargo is not null"
        		 	+ " ORDER BY CLASSE ");        
       // System.out.println("hora do dado1");
        
        Instances data = this.queryWeka.retrieveInstances();
        System.out.println("data2=> "+data);
        //System.out.println("hora do dado2");                                 
		return data;
	}
	
	private Evaluation crossValidation(Instances data,AbstractClassifier classificador) throws Exception, Throwable, Exception{
				
	    if (data.classIndex() == -1){
	    	data.setClassIndex(data.numAttributes() - 1);
	    }
	    
	    System.out.println(data);
	 	    
	    Evaluation avaliacao = new Evaluation(data);
	    
	    avaliacao.crossValidateModel(classificador, data, 10, new Random(1));
	    
	    return avaliacao;	    
	}
			
	
	
	private static byte[] converterClassificadorParaByte(Classifier classificador) {
        System.out.println("metodo converterClassificadorParaByte");
		try {
               ByteArrayOutputStream bao = new ByteArrayOutputStream();
               ObjectOutputStream ous;
               ous = new ObjectOutputStream(bao);
               ous.writeObject(classificador);
               
               return bao.toByteArray();
        } catch (IOException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
        }

        return null;
	} 
	
	private void salvarModelo(Classifier classificador) throws Exception{
		//Debug.saveToFile("C:\\Users\\Lucas Pereira\\Documents\\modelos\\texto.tr", classificador);
		
		
		
		
		//weka.core.SerializationHelper.write("C:\\Users\\Lucas Pereira\\Documents\\modelos\\texto.tr.model", classificador);

		
		InstanceQuery query = new InstanceQuery();
        query.setUsername("postgres");
        query.setPassword("postgres");
        query.connectToDatabase();
        
        query.setQuery("Insert into modelo(modelo)values("+converterClassificadorParaByte(classificador)+")");
        
        //query.execute("Insert into modelo(modelo)values("+converterClassificadorParaByte(classificador)+")");
        query.retrieveInstances();
        query.close();
		
        
        		
	}
	
			
}
