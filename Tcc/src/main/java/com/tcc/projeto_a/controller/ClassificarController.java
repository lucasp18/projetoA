package com.tcc.projeto_a.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tcc.projeto_a.facade.FClassificacao;
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

@Controller
@RequestMapping("/projetoA/classificar")
public class ClassificarController {

	@Autowired
	private ParametroRepository parametrosR;
	
	@Autowired
	private ClassificadorSvmRepository classificadores;
	
	@Autowired
	private ViewInformacaoEditalRepository viewsInformacoes;
	
	@Autowired
	private EditalRepository editalR;
	
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
	private ParametroBuscadoRepository parametrosBuscados;
	
	@Autowired
	private ParametroEditalRepository parametrosEditais;
	
	@Autowired
	private QuartilSalarioRepository quartilSalarioR;
	
	
	@RequestMapping("/edital")
	public ModelAndView iniciar(){						
		ModelAndView mv = new ModelAndView("/svm/classificador");		
		
		FClassificacao fClassificacao = new FClassificacao(this.parametrosR,this.classificadores,this.viewsInformacoes,this.editalR,this.disciplinas,this.areas,this.escolaridades,this.esferas,this.requisitosCargos,this.parametrosBuscados,this.parametrosEditais,this.cidades,this.salarios,this.quartilSalarioR);
		fClassificacao.reclassificar(0);
		//fClassificacao.reclassificar();
		return mv;		
	}

	
	
	
	
	
	
}
