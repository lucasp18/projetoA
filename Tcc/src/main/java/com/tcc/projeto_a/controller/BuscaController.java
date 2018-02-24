package com.tcc.projeto_a.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("projetoA")
public class BuscaController {

	/*
	@Autowired
	private Editais editais;
	*/
	@RequestMapping("index")
	public String index(){
		//ModelAndView mv = new ModelAndView();
		return "/edital/listagemEditais";
	}
	

	
}
