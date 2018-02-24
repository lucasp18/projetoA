package com.tcc.projeto_a.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tcc.projeto_a.facade.FSvm;
import com.tcc.projeto_a.formulario.FormParametro;
import com.tcc.projeto_a.model.Edital;
import com.tcc.projeto_a.model.Estado;
import com.tcc.projeto_a.model.ParametroEdital;
import com.tcc.projeto_a.repository.AreaRepository;
import com.tcc.projeto_a.repository.CidadeRepository;
import com.tcc.projeto_a.repository.ClassificadorSvmRepository;
import com.tcc.projeto_a.repository.DisciplinaRepository;
import com.tcc.projeto_a.repository.EditalRepository;
import com.tcc.projeto_a.repository.Escolaridade2Repository;
import com.tcc.projeto_a.repository.EsferaRepository;
import com.tcc.projeto_a.repository.EstadoRepository;
import com.tcc.projeto_a.repository.ParametroBuscadoRepository;
import com.tcc.projeto_a.repository.ParametroEditalRepository;
import com.tcc.projeto_a.repository.ParametroRepository;
import com.tcc.projeto_a.repository.QuartilSalarioRepository;
import com.tcc.projeto_a.repository.RequisitoCargoRepository;
import com.tcc.projeto_a.repository.SalarioRepository;
import com.tcc.projeto_a.repository.ViewInformacaoEditalRepository;
import com.tcc.projeto_a.service.ObjAcumulado;


@Controller
@RequestMapping({"/projetoA/SVM","/"})
public class SvmController {
	
	@Autowired
	private Escolaridade2Repository escolaridades;
	
	@Autowired
	private EsferaRepository esferas;
	
	@Autowired
	private DisciplinaRepository disciplinas;
	
	@Autowired
	private EstadoRepository estados;
	
	@Autowired
	private QuartilSalarioRepository quartisSalarios;
	
	@Autowired
	private CidadeRepository cidades;
	
	@Autowired
	private RequisitoCargoRepository requisitosCargos;
	
	@Autowired
	private AreaRepository areas;
	
	@Autowired
	private SalarioRepository salarios;
	
	@Autowired
	private ParametroRepository parametrosR;
	
	@Autowired
	private ClassificadorSvmRepository classificadores;
	
	@Autowired
	private ViewInformacaoEditalRepository viewsInformacoes;
	
	@Autowired
	private ParametroBuscadoRepository parametrosBuscados;
	
	@Autowired
	private ParametroEditalRepository parametrosEditalR;
	
	@Autowired
	private EditalRepository editalRepository;
		
	@RequestMapping({"/formulario","/"})
	public ModelAndView iniciar(){				
		
		ModelAndView mv = new ModelAndView("/svm/formulario");		
		mv.addObject("escolaridades",escolaridades.findAll(new Sort(Sort.Direction.ASC, "nome")));
		mv.addObject("esferas",esferas.findAll(new Sort(Sort.Direction.ASC, "nome")));		
		mv.addObject("disciplinas",disciplinas.findAll(new Sort(Sort.Direction.ASC, "nome")));
		mv.addObject("estados",estados.estadosParametro());
		mv.addObject("quartisSalarios",quartisSalarios.findAll());		
		mv.addObject("requisitosCargos",requisitosCargos.findAll(new Sort(Sort.Direction.ASC, "nome")));
		mv.addObject("areas",areas.findAll(new Sort(Sort.Direction.ASC, "nome")));		
		mv.addObject("formParametro",new FormParametro());
		
		return mv;		
	}
	
	@ResponseBody
	@RequestMapping(value = "cidades",method = RequestMethod.POST)
	public ModelAndView cidades( @RequestBody Estado estado,Model model){				
		ModelAndView mv = new ModelAndView("fragmento/ajaxCidade :: cidadesFragmento");		
		mv.addObject("cidades",cidades.cidadesParametro(estado));		
		return mv;
	}
	
	@PostMapping("/buscar")
	public ModelAndView consultarSvm(FormParametro formParametro) {		
		//List<Salario> salariosPorQuartil = salarios.porQuartil(quartisSalarios.findOne(formParametro.getIdQuartil()));
		System.out.println("metodo buscar");
		FSvm fSvm = new FSvm(salarios.porQuartil(quartisSalarios.findOne(formParametro.getIdQuartil())),formParametro,parametrosR,classificadores,viewsInformacoes,parametrosBuscados,editalRepository);
		
		System.out.println("antes da instancia");
		ModelAndView mv = new ModelAndView("/svm/resultado");
		System.out.println("antes do null");
		mv.addObject("resultados",fSvm.svm());
		//mv.addObject("vai",null);
		//formParametro.getIdQuartil();		
		System.out.println("acao buscar"+formParametro.getIdCidade());
		System.out.println("antes do return");
		return mv;
	}
				
	@ResponseBody
	@RequestMapping(value = "{id}/editais",method = RequestMethod.GET)
	public ModelAndView	edital(@PathVariable Long id){
				
		Edital edital = this.editalRepository.findOne(id);
		ModelAndView mv = new ModelAndView("/svm/edital");
		mv.addObject("edital",edital);
		List<ParametroEdital> parametrosEdital = this.parametrosEditalR.buscarEdital(edital);
		System.out.println("qtd_parametro>> "+parametrosEdital.size());
		mv.addObject("esfera", parametrosEdital.get(0).getIdParametro().getEsfera().getNome());
		mv.addObject("disciplinas", gerarLista("disciplina",parametrosEdital));
		mv.addObject("areas", gerarLista("area",parametrosEdital));
		mv.addObject("cidades", gerarLista("cidade",parametrosEdital));
		mv.addObject("escolaridades", gerarLista("escolaridade",parametrosEdital));
		mv.addObject("salarios", gerarLista("salario",parametrosEdital)); 
		mv.addObject("requisitosCargos", gerarLista("requisitoCargo",parametrosEdital));
		
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "editais",method = RequestMethod.GET)
	public ModelAndView	editais(){
		System.out.println("editais");		
		List<Edital> editais = this.editalRepository.findAll();
		ModelAndView mv = new ModelAndView("/svm/resultado");
		/*
		List<ObjAcumulado> objs = new ArrayList<ObjAcumulado>();
		for (Edital edital : editais) {
			List<ParametroEdital> parametrosEdital = this.parametrosEditalR.buscarEdital(edital);
			ObjAcumulado objAcumulado = new ObjAcumulado();
			objAcumulado.setEsferas(parametrosEdital.get(0).getIdParametro().getEsfera().getNome());
			objAcumulado.setDisciplinas(gerarLista("disciplina",parametrosEdital));
			objAcumulado.setAreas(gerarLista("disciplina",parametrosEdital));
			objAcumulado.setCidades(gerarLista("cidade",parametrosEdital));			
			objAcumulado.setEscolaridades(gerarLista("escolaridade",parametrosEdital));
			objAcumulado.setSalarios(gerarLista("salario",parametrosEdital));
			objAcumulado.setRequisitosCargos(gerarLista("requisitoCargo",parametrosEdital));
			
			objs.add(objAcumulado);
		}
		*/		
		mv.addObject("resultados", editais);
		return mv;
	}
	
	
	public List<String> gerarLista(String tipo,List<ParametroEdital> parametrosEdital ){
		List<String> lista = new ArrayList<String>();
		String palavra;
		for (ParametroEdital parametroEdital : parametrosEdital) {
			palavra = palavra(tipo,parametroEdital);
			if(!lista.contains(palavra)){
				lista.add(palavra);
			}
		}
		return lista;
	}
	
	public String palavra(String tipo, ParametroEdital parametroEdital){
		if(tipo.equals("requisitoCargo")){
			return parametroEdital.getIdParametro().getRequisitoCargo().getNome();
		}else if(tipo.equals("area")){
			return parametroEdital.getIdParametro().getArea().getNome();
		}else if(tipo.equals("cidade")){
			return parametroEdital.getIdParametro().getCidade().getNome();
		}else if(tipo.equals("escolaridade")){
			return parametroEdital.getIdParametro().getEscolaridade().getNome();
		}else if(tipo.equals("salario")){
			return String.valueOf(parametroEdital.getIdParametro().getSalario().getValor());
		}else if(tipo.equals("disciplina")){
			return parametroEdital.getIdParametro().getDisciplina().getNome();
		}
		return null;
	}
	
}
