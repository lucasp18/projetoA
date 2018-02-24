package parserEditais;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import conexaoBD.Conexao;

public class Main {	
	
	
	public static void main(String[] args) throws IOException, Exception{
		File folder = new File("C:\\Users\\Lucas Pereira\\Documents\\testePDF\\certame\\cespe\\Nova pasta\\");
		File[] listOfFiles  = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
		//for (int i = 0; i < 3; i++) {
		      if (listOfFiles[i].isFile() ) {
		        System.out.println("File " + listOfFiles[i].getName());
		        main2(listOfFiles[i].getName());
		      } else if (listOfFiles[i].isDirectory()) {
		        System.out.println("Directory " + listOfFiles[i].getName());
		      }
		}				
	}
	
	public static void main2(String nomeFile) throws IOException, Exception{
		String EDITAL_TITULO = "EDITAL_TITULO";
		int idEditalTitulo = 0;
		//String nomeArquivo = "C:\\Users\\Lucas Pereira\\Documents\\testePDF\\certame\\cespe\\Nova pasta\\parserEdital8.txt"; 
		String nomeArquivo = "C:\\Users\\Lucas Pereira\\Documents\\testePDF\\certame\\cespe\\Nova pasta\\"+nomeFile;
		String DISCIPLINAS_COMUM = "DISCIPLINAS_COMUM";
		//String disciplinaComumConteudo;
		String ESCOLARIDADE_SUPERIOR = "ESCOLARIDADE:SUPERIOR;";
		int idEscolaridadeSuperior = 0;
		String ESCOLARIDADE_MEDIO = "ESCOLARIDADE:MÉDIO;";
		int idEscolaridadeMedio = 0;
		String ESCOLARIDADE_FUNDAMENTAL = "ESCOLARIDADE:FUNDAMENTAL;";
		int idEscolaridadeFundamental = 0;
		String SALARIO = "SALARIO";
		int idSalario = 0;
		String REQUISITO_CARGO_ESCOLARIDADE = "REQUISITO_CARGO_ESCOLARIDADE";
		int idCargoEscolaridade = 0;
		String AREA = "ÁREA";
		int idArea = 0;
		String LOTACAO = "LOTACAO";
		List<Integer> idsLotacao = new ArrayList<Integer>();
		String DISCIPLINAS = "DISCIPLINAS";
		List<Integer> idsDisciplinas = new ArrayList<Integer>();
		List<Integer> idsDisciplinasComuns = new ArrayList<Integer>();
		String ESFERA = "ESFERA";
		int idEsfera = 0;
		String INSERT = "INSERT";
		
		//FileInputStream arquivo = new FileInputStream(new File(nomeArquivo));
		
		
		FileReader arq = new FileReader(nomeArquivo);
	    BufferedReader lerArq = new BufferedReader(arq);
	    Connection con = Conexao.getConnection();
        PreparedStatement ps = null,ps2 = null;
        ResultSet rs;
        String linha = lerArq.readLine();			
	
		while (linha != null) {		        
		   if(linha.startsWith(EDITAL_TITULO)){
				 idEditalTitulo = 0;
				 System.out.println("tem titulo");
				 ps = con.prepareStatement("INSERT INTO projeto_a.edital(titulo) VALUES(?)",Statement.RETURN_GENERATED_KEYS);
				 String[] conteudo = linha.split("\\:");
				 ps.setString(1, conteudo[1].replace(";",""));
				 ps.executeUpdate();
				 rs = ps.getGeneratedKeys();
				 if(rs.next()){
					 idEditalTitulo = rs.getInt("id");
				 }
				 System.out.println("idEditalTitulo = "+String.valueOf(idEditalTitulo));
			}else if(linha.startsWith(DISCIPLINAS_COMUM)){
				String[] conteudo = linha.split("\\:");
				String[] disciplinasComuns = conteudo[1].split(",");
				 
				for (int i = 0; i < disciplinasComuns.length; i++) {
					
					ps = con.prepareStatement("SELECT id FROM projeto_a.disciplina WHERE nome = ?");
					ps.setString(1,disciplinasComuns[i].trim().replace(";",""));
					System.out.println("nome disciplina >>" + disciplinasComuns[i].trim().replace(";",""));
					rs = ps.executeQuery();
					
					if(rs.next()){						
						idsDisciplinas.add(rs.getInt("id"));					
					}else{
						
						ps = con.prepareStatement("INSERT INTO projeto_a.disciplina(nome) VALUES(?) ",Statement.RETURN_GENERATED_KEYS);
						ps.setString(1,disciplinasComuns[i].trim().replace(";",""));
						ps.executeUpdate();
						
						rs = ps.getGeneratedKeys();
						if(rs.next()){
							idsDisciplinasComuns.add(rs.getInt("id"));
						}	
					}
				}
							
				
			}else if(linha.startsWith(ESCOLARIDADE_SUPERIOR)){				
				System.out.println("escolaridade nivel superior");
				String[] conteudo = linha.split("\\:");				
				ps = con.prepareStatement("SELECT id FROM projeto_a.escolaridade WHERE nome = ?");
				ps.setString(1,conteudo[1].trim().replace(";",""));
				rs = ps.executeQuery();
				
				if(rs.next()){
					idEscolaridadeMedio = 0;
					idEscolaridadeFundamental = 0;
					idEscolaridadeSuperior = rs.getInt("id");
				}
											
			}else if(linha.startsWith(SALARIO)){
				String[] conteudo = linha.split("\\:");				
				ps = con.prepareStatement("SELECT id FROM projeto_a.salario WHERE valor = ?");
				ps.setObject(1, Double.valueOf(conteudo[1].replace(";","")),java.sql.Types.NUMERIC);
				//ps.setLong(1,Long.valueOf(conteudo[1].replace(";","")));
				rs = ps.executeQuery();
				
				if(rs.next()){
					idSalario = rs.getInt("id");
				}else{
					ps = con.prepareStatement("INSERT INTO projeto_a.salario(valor) VALUES(?) ",Statement.RETURN_GENERATED_KEYS);
					ps.setObject(1, Double.valueOf(conteudo[1].replace(";","")),java.sql.Types.NUMERIC);
					//ps.setLong(1,Long.valueOf(conteudo[1].replace(";","")));
					ps.executeUpdate();					
					rs = ps.getGeneratedKeys();
					if(rs.next()){
						idSalario = rs.getInt("id");
					}	
				}
				ps.close();
			}else if(linha.startsWith(REQUISITO_CARGO_ESCOLARIDADE)){
				String[] conteudo = linha.split("\\:");				
				ps = con.prepareStatement("SELECT id FROM projeto_a.requisito_cargo WHERE nome = ?");
				System.out.println("conteudo ="+conteudo[1].trim().replace(";",""));
				ps.setString(1,conteudo[1].trim().replace(";",""));
				//ps.setLong(1,Long.valueOf(conteudo[1].replace(";","")));
				rs = ps.executeQuery();
				System.out.println(ps.toString());
				if(rs.next()){
					idCargoEscolaridade = rs.getInt("id");
				}else{
					ps.close();
					ps = con.prepareStatement("INSERT INTO projeto_a.requisito_cargo(nome) VALUES(?);",Statement.RETURN_GENERATED_KEYS);
					ps.setString(1,conteudo[1].trim().replace(";",""));
					//ps.setLong(1,Long.valueOf(conteudo[1].replace(";","")));
					ps.executeUpdate();
					rs = ps.getGeneratedKeys();
					System.out.println(ps.toString());
					
					if(rs.next()){
						idCargoEscolaridade = rs.getInt("id");
						System.out.println("idCargoEscolaridade"+idCargoEscolaridade);
					}	
					//ps.close();
				}	
				ps.close();
			}else if(linha.startsWith(AREA)){
				System.out.println("linha area = "+linha);
				String[] conteudo = linha.split("\\:");				
				ps = con.prepareStatement("SELECT id FROM projeto_a.area WHERE nome = ?");
				ps.setString(1,conteudo[1].trim().replace(";",""));
				rs = ps.executeQuery();
				
				if(rs.next()){
					idArea = rs.getInt("id");
				}else{
					ps = con.prepareStatement("INSERT INTO projeto_a.area(nome) VALUES(?) ",Statement.RETURN_GENERATED_KEYS);
					ps.setString(1,conteudo[1].trim().replace(";",""));
					System.out.println(ps.toString());
					ps.executeUpdate();
					rs = ps.getGeneratedKeys(); 					
					if(rs.next()){
						idArea = rs.getInt("id");
					}	
				}	
			}else if(linha.startsWith(LOTACAO)){
				System.out.println("lotacao");
				String[] conteudo = linha.split("\\:");				
				//List<String> lotacoes = new ArrayList<String>();
				String[] lotacoes = conteudo[1].split(",");
							
				//System.out.println(lotacoes[0]);
				
				for (int i = 0; i < lotacoes.length; i++) {
					String[] cidadeEstado = lotacoes[i].split("\\&");
					
					ps = con.prepareStatement("SELECT cidade.id FROM projeto_a.cidade cidade"
							+ " inner join projeto_a.estado estado on estado.id = cidade.id_estado"
							+ " WHERE cidade.nome = ? and estado.nome = ? ");
					
					String[] estado = cidadeEstado[0].split("\\=");
					String[] cidade  = cidadeEstado[1].split("\\=");
					
					ps.setString(1,cidade[1].trim().replace(";",""));
					ps.setString(2,estado[1].trim().replace(";",""));
					System.out.println(ps.toString());
					
					rs = ps.executeQuery();
					
					if(rs.next()){
						idsLotacao.add(rs.getInt("id"));
					}
				}			
			}else if(linha.startsWith(DISCIPLINAS)){
				System.out.println("insert disciplinas");
				String[] conteudo = linha.split("\\:");				
				System.out.println("todas conteudo"+ conteudo[0]+"  "+conteudo[1]);
				String[] disciplinas = conteudo[1].split(",");
				System.out.println("todas disciplinas"+ disciplinas.length);
				for (int i = 0; i < disciplinas.length; i++) {
					
					ps = con.prepareStatement("SELECT id FROM projeto_a.disciplina WHERE nome = ?");
					ps.setString(1,disciplinas[i].trim().replace(";",""));
					System.out.println("disciplinas mesmo >>"+ disciplinas[i].trim().replace(";",""));
					rs = ps.executeQuery();
					
					if(rs.next()){
						System.out.println("tamanho antes de adicionar"+ idsDisciplinas.size());
						idsDisciplinas.add(rs.getInt("id"));					
					}else{
						ps = con.prepareStatement("INSERT INTO projeto_a.disciplina(nome) VALUES(?) ",Statement.RETURN_GENERATED_KEYS);
						ps.setString(1,disciplinas[i].trim().replace(";",""));
						ps.executeUpdate();
						rs = ps.getGeneratedKeys();
						
						if(rs.next()){
							System.out.println(" else tamanho antes de adicionar"+ idsDisciplinas.size());
							idsDisciplinas.add(rs.getInt("id"));
						}
						
					}
				}
				System.out.println("todas as disciplinas");
				System.out.println("idsDisciplinas "+ idsDisciplinas.toString());	
			}else if(linha.startsWith(ESFERA)){
				System.out.println("esfera");
				String[] conteudo = linha.split("\\:");				
				ps = con.prepareStatement("SELECT id FROM projeto_a.esfera WHERE nome = ?");
				ps.setString(1,conteudo[1].trim().replace(";",""));
				rs = ps.executeQuery();
				
				if(rs.next()){
					idEsfera = rs.getInt("id");
				}
								
			}else if(linha.startsWith(ESCOLARIDADE_MEDIO)){
				System.out.println("escolaridade medio aqui");
				String[] conteudo = linha.split("\\:");				
				ps = con.prepareStatement("SELECT id FROM projeto_a.escolaridade WHERE nome = ?");
				ps.setString(1,conteudo[1].trim().replace(";",""));
				rs = ps.executeQuery();
				System.out.println(conteudo[1].trim().replace(";",""));
				if(rs.next()){
					idEscolaridadeMedio = rs.getInt("id");
					idEscolaridadeFundamental = 0;
					idEscolaridadeSuperior = 0;
				}
			
				System.out.println("idEscolaridadeMedio"+idEscolaridadeMedio);
			}else if(linha.startsWith(ESCOLARIDADE_FUNDAMENTAL)){
				String[] conteudo = linha.split("\\:");				
				ps = con.prepareStatement("SELECT id FROM projeto_a.escolaridade WHERE nome = ?");
				ps.setString(1,conteudo[1].trim().replace(";",""));
				rs = ps.executeQuery();
				
				if(rs.next()){
					idEscolaridadeMedio = 0;
					idEscolaridadeFundamental = rs.getInt("id");
					idEscolaridadeSuperior = 0;
				}
			}else if( linha.startsWith(INSERT)){
				System.out.println("insert");
				System.out.println(idsDisciplinas.size() +" _ "+ idsLotacao.size());
				ps = con.prepareStatement("INSERT INTO projeto_a.parametro(id_esfera,id_salario,id_escolaridade,id_disciplina,id_cidade,id_area,id_requisito_cargo) VALUES(?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1,idEsfera);
				ps.setInt(2,idSalario);
				if(idEscolaridadeFundamental != 0){
					ps.setInt(3,idEscolaridadeFundamental);
				}else if(idEscolaridadeMedio != 0){
					ps.setInt(3,idEscolaridadeMedio);
				}else if(idEscolaridadeSuperior != 0){
					ps.setInt(3,idEscolaridadeSuperior);
				}
				ps.setInt(6,idArea);
				ps.setInt(7,idCargoEscolaridade);
				if(idsDisciplinas.size() <= idsLotacao.size()){									
					for (Integer idDisciplina : idsDisciplinas) {
						ps.setInt(4,idDisciplina);
						for (Integer idLotacao : idsLotacao) {
							ps.setInt(5,idLotacao);
							ps.executeUpdate();
							rs = ps.getGeneratedKeys(); 
							if(rs.next()){								
								int idParametro = rs.getInt("id");	
								ps2 = con.prepareStatement("INSERT INTO projeto_a.parametro_edital(id_parametro,id_edital) VALUES(?,?)",Statement.RETURN_GENERATED_KEYS);
								ps2.setInt(1, idParametro);
								ps2.setInt(2, idEditalTitulo);
								ps2.executeUpdate();
							}
						}					
						
						
					}
					
				}else{
					for (Integer idLotacao : idsLotacao) {
						ps.setInt(5,idLotacao);
						for (Integer idDisciplina : idsDisciplinas) {
							ps.setInt(4,idDisciplina);
							ps.executeUpdate();
							rs = ps.getGeneratedKeys();
							System.out.println(ps.toString());
							if(rs.next()){								
								int idParametro = rs.getInt("id");	
								ps2 = con.prepareStatement("INSERT INTO projeto_a.parametro_edital(id_parametro,id_edital) VALUES(?,?)",Statement.RETURN_GENERATED_KEYS);
								ps2.setInt(1, idParametro);
								ps2.setInt(2, idEditalTitulo);
								System.out.println(ps2.toString());
								ps2.executeUpdate();
								
							}
						}					
					}
				}
				
				if(idsDisciplinasComuns.size() <= idsLotacao.size()){
					for (Integer idDisciplinaComum : idsDisciplinasComuns) {
						ps.setInt(4,idDisciplinaComum);
						for (Integer idLotacao : idsLotacao) {
							ps.setInt(5,idLotacao);
							System.out.println("sql = "+ps.toString());
							rs = ps.getGeneratedKeys();
							if(rs.next()){								
								int idParametro = rs.getInt("id");	
								ps2 = con.prepareStatement("INSERT INTO projeto_a.parametro_edital(id_parametro,id_edital) VALUES(?,?)",Statement.RETURN_GENERATED_KEYS);
								ps2.setInt(1, idParametro);
								ps2.setInt(2, idEditalTitulo);
								ps2.executeQuery();
							}
						}					
						
						
					}
					
					
					
				}else{
					
					
					for (Integer idLotacao : idsLotacao) {
						ps.setInt(5,idLotacao);
						for (Integer idDisciplinasComum : idsDisciplinasComuns) {
							ps.setInt(4,idDisciplinasComum);
							ps.executeUpdate();
							rs = ps.getGeneratedKeys();
							if(rs.next()){								
								int idParametro = rs.getInt("id");	
								ps2 = con.prepareStatement("INSERT INTO projeto_a.parametro_edital(id_parametro,id_edital) VALUES(?,?)",Statement.RETURN_GENERATED_KEYS);
								ps2.setInt(1, idParametro);
								ps2.setInt(2, idEditalTitulo);
								ps2.executeUpdate();
							}
						}					
					}
																			
				}
									
				idsDisciplinas.clear();
				//idsDisciplinasComuns.clear();
				idsLotacao.clear();
			}
											
		    linha = lerArq.readLine(); // lê da segunda até a última linha
		}
		
		
	}


}
