package br.mil.eb.decex.ati.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.sun.xml.ws.util.UtilException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;
import net.sf.jasperreports.engine.util.JRLoader;

public class RelatorioUtil {
	
	public static final int RELATORIO_PDF =1;
	public static final int RELATORIO_EXCEL =2;
	public static final int RELATORIO_HTML =3;
	public static final int RELATORIO_PLANILHA_OPEN_OFFICE =4;
	
	public StreamedContent geraRelatorio(HashMap<?, ?> parametrosRelatorio, String nomeRelatorioJasper, String nomeRelatorioSaida, int tipoRelatorio, String pastaRelatorio) throws UtilException {
		
		StreamedContent arquivoRetorno = null;
		Connection conexao = null;
		
		try {
			
			FacesContext context = FacesContext.getCurrentInstance();
			conexao = this.getConexao();
			
			String caminhoRelatorio=context.getExternalContext().getRealPath(pastaRelatorio);
			String caminhoArquivoJasper=caminhoRelatorio + File.separator + nomeRelatorioJasper + ".jasper";
			String caminhoArquivoRelatorio = null;
			
			JasperReport relatorioJasper = (JasperReport) JRLoader.loadObject(caminhoArquivoJasper);
			JasperPrint impressoraJasper = JasperFillManager.fillReport(relatorioJasper, parametrosRelatorio,conexao);
			
			JRExporter tipoArquivoExportado = null;
			String extensaoArquivoExportado = "";
			File arquivoGerado = null;
			
			switch (tipoRelatorio) {
			
			case RelatorioUtil.RELATORIO_PDF:
				
				tipoArquivoExportado = new JRPdfExporter();
				extensaoArquivoExportado="pdf";
				
				break;
				
			case RelatorioUtil.RELATORIO_HTML:
				
				tipoArquivoExportado = new JRHtmlExporter();
				extensaoArquivoExportado="html";
				
				break;
				
			case RelatorioUtil.RELATORIO_EXCEL:
				
				tipoArquivoExportado = new JRXlsExporter();
				extensaoArquivoExportado="xls";
				
				break;
				
			case RelatorioUtil.RELATORIO_PLANILHA_OPEN_OFFICE:
				tipoArquivoExportado = new JROdtExporter();
				extensaoArquivoExportado="ods";
				
				break;
			default:
				
				tipoArquivoExportado = new JRPdfExporter();
				extensaoArquivoExportado="pdf";
				
				break;
			}
			
			caminhoArquivoRelatorio = caminhoRelatorio + File.separator + nomeRelatorioSaida + "." + extensaoArquivoExportado;
			
			arquivoGerado = new java.io.File(caminhoArquivoRelatorio);
			
			tipoArquivoExportado.setParameter(JRExporterParameter.JASPER_PRINT, impressoraJasper);
			tipoArquivoExportado.setParameter(JRExporterParameter.OUTPUT_FILE, arquivoGerado);
			tipoArquivoExportado.exportReport();
			
			arquivoGerado.deleteOnExit();
			InputStream conteudoRelatorio = new FileInputStream(arquivoGerado);
			arquivoRetorno=new DefaultStreamedContent(conteudoRelatorio,"aplication/"+extensaoArquivoExportado,nomeRelatorioSaida+"."+extensaoArquivoExportado);
			
		} catch(FileNotFoundException e){
			
			throw new UtilException("Relatório ainda em desevolvimento.",e);
			
		} catch(JRException e){
			
			throw new UtilException("Nao foi possivel gerar o relatorio.",e);
			
			
		} catch (Exception e){
			throw new UtilException("Problema grave no relatório.",e);
			
		} finally {
			try {
				if(conexao != null && !conexao.isClosed())
					conexao.close();
				
			} catch (SQLException e) {
				throw new UtilException("Erro no fechamento da conexao.",e);				
			}
		}
		
		return arquivoRetorno;
	}
	
	
public StreamedContent geraRelatorio(String SQL, HashMap<?, ?> parametrosRelatorio, String nomeRelatorioJasper, String nomeRelatorioSaida, int tipoRelatorio) throws UtilException {
		
		StreamedContent arquivoRetorno = null;
		Connection conexao = null;
		
		try {
			
			FacesContext context = FacesContext.getCurrentInstance();
			conexao = this.getConexao();
			
			String caminhoRelatorio=context.getExternalContext().getRealPath("relatorio");
			String caminhoArquivoJasper=caminhoRelatorio + File.separator + nomeRelatorioJasper + ".jasper";
			String caminhoArquivoRelatorio = null;
			
			JasperReport relatorioJasper = (JasperReport) JRLoader.loadObject(caminhoArquivoJasper);
			
			
			Statement stm =  this.getConexao().createStatement();
			ResultSet rs = stm.executeQuery(SQL);
			JRResultSetDataSource jrRS = new JRResultSetDataSource(rs); 
			JasperPrint impressoraJasper = JasperFillManager.fillReport(relatorioJasper,parametrosRelatorio,jrRS);
			
			JRExporter tipoArquivoExportado = null;
			String extensaoArquivoExportado = "";
			File arquivoGerado = null;
			
			switch (tipoRelatorio) {
			
			case RelatorioUtil.RELATORIO_PDF:
				
				tipoArquivoExportado = new JRPdfExporter();
				extensaoArquivoExportado="pdf";
				
				break;
			
				
			case RelatorioUtil.RELATORIO_EXCEL:
				
				tipoArquivoExportado = new JRXlsExporter();
				extensaoArquivoExportado="xls";
				
				break;
				
			
			default:
				
				tipoArquivoExportado = new JRPdfExporter();
				extensaoArquivoExportado="pdf";
				
				break;
			}
			
			caminhoArquivoRelatorio = caminhoRelatorio + File.separator + nomeRelatorioSaida + "." + extensaoArquivoExportado;
			
			arquivoGerado = new java.io.File(caminhoArquivoRelatorio);
			
			tipoArquivoExportado.setParameter(JRExporterParameter.JASPER_PRINT, impressoraJasper);
			tipoArquivoExportado.setParameter(JRExporterParameter.OUTPUT_FILE, arquivoGerado);
			tipoArquivoExportado.exportReport();
			
			arquivoGerado.deleteOnExit();
			InputStream conteudoRelatorio = new FileInputStream(arquivoGerado);
			arquivoRetorno=new DefaultStreamedContent(conteudoRelatorio,"aplication/"+extensaoArquivoExportado,nomeRelatorioSaida+"."+extensaoArquivoExportado);
			
		} catch(JRException e){
			
			throw new UtilException("Nao foi possivel gerar o relatorio.",e);
			
		} catch(FileNotFoundException e){
			
			throw new UtilException("Arquivo EnunRelatorio nao encontrado.",e);
			
		} catch (Exception e){
			throw new UtilException("Problema grave no relatório.",e);
		} finally {
			try {
				if(conexao != null && !conexao.isClosed())
					conexao.close();
			} catch (SQLException e) {
				throw new UtilException("Erro no fechamento da conexao.",e);				
			}
		}
		
		return arquivoRetorno;
	}

	
	private Connection getConexao() throws UtilException {

		java.sql.Connection conexao=null;
		
		try{
			
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:jboss/");
			javax.sql.DataSource ds = (javax.sql.DataSource) envContext.lookup("datasources/siceDS");
			conexao = (java.sql.Connection) ds.getConnection();
			
		}catch (NamingException e){
			
			throw new UtilException("Nao foi possivel encontrar o nome da conexao",e);
			
		}catch (SQLException e){
			
			throw new UtilException("Ocorreu um erro de SQL",e);
			
		}
		
		return conexao;
	}	
}