package br.mil.eb.decex.ati.vo;
 
import java.io.Serializable;
import java.time.Month;
import java.util.Formatter;
import java.util.List;

import javax.swing.plaf.synth.SynthToggleButtonUI;

import org.primefaces.model.chart.ChartSeries;

import br.mil.eb.decex.ati.enumerado.TelaEnum;
 

public class SerieVO implements Serializable {
    
	private static final long serialVersionUID = 1L;
	private TelaEnum tela;
	private List<EfetivoResumidoVO> listaEfetivo;

	public SerieVO(TelaEnum tela, List<EfetivoResumidoVO> listaEfetivo) {
		super();
		this.tela = tela;
		this.listaEfetivo = listaEfetivo;
	}

	public ChartSeries getSerie(){
        ChartSeries serie = new ChartSeries();
        serie.setLabel(getTela().getValue());
        for (EfetivoResumidoVO er : getListaEfetivo()) {
        	//serie.set(Month.of(er.getMes())+" "+er.getAno(), er.getEfetivo());
        	//numero = formatter.format("%d", er.getMes()).toString();
        	//System.out.println(er.getAno()+"-01-"+ ( er.getMes()<10?("0"+er.getMes()):er.getMes()));
        	//System.out.println( ( er.getMes()<10?("0"+er.getMes()):er.getMes()) + " - " + Month.of(er.getMes())+" "+er.getAno() );
        	serie.set(er.getAno()+ "-"+( (er.getMes()<10)?("0"+er.getMes()):er.getMes())+"-01" , er.getEfetivo());
		}
        return serie;
	}

	public TelaEnum getTela() {
		return tela;
	}

	public void setTela(TelaEnum tela) {
		this.tela = tela;
	}

	public List<EfetivoResumidoVO> getListaEfetivo() {
		return listaEfetivo;
	}

	public void setListaEfetivo(List<EfetivoResumidoVO> listaEfetivo) {
		this.listaEfetivo = listaEfetivo;
	}
     
   
 
}