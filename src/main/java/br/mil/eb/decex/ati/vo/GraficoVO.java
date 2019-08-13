package br.mil.eb.decex.ati.vo;
 
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;

import br.mil.eb.decex.ati.enumerado.TelaEnum;
 

public class GraficoVO implements Serializable {
    
	private static final long serialVersionUID = 1L;
	
	private LineChartModel grafico;
	private int alturaMaxima = -1;
	
	@PostConstruct
	public void iniciarGrafico(){
		

		this.getGrafico().clear();
		alturaMaxima = -1;
		
		this.getGrafico().setTitle("Visão Anual do Efetivo");
		
		DateAxis axis = new DateAxis("Meses");
        axis.setTickAngle(-50);
        //axis.setMax("2014-02-01");
        axis.setTickFormat("%b %y");
        this.getGrafico().setZoom(false);
        this.getGrafico().getAxes().put(AxisType.X, axis);
		//this.getGrafico().getAxes().put(AxisType.X, new CategoryAxis("Meses"));
		this.getGrafico().setLegendPosition("nw");
		this.getGrafico().setShowPointLabels(false);
		this.getGrafico().setResetAxesOnResize(true);
		this.getGrafico().setShowDatatip(true);
		Axis yAxis = this.getGrafico().getAxis(AxisType.Y);
	        yAxis.setLabel("Efetivo");
	        yAxis.setMin(0);
	        //yAxis.setMax(1000);
	        
	}
	
	public void ajustarAlturaMaxima(){
		Axis yAxis = this.getGrafico().getAxis(AxisType.Y);
		Double teste01 = new Double(1.1);
		teste01= teste01  * alturaMaxima;
		alturaMaxima = teste01.intValue();
		yAxis.setMax(alturaMaxima);
	}
	
	public void preencherGrafico(TelaEnum tela, List<EfetivoResumidoVO> listaEfetivo){
		int ultimoMes = listaEfetivo.get(listaEfetivo.size()-1).getMes();
		int ultimoAno = listaEfetivo.get(listaEfetivo.size()-1).getAno();
		for (EfetivoResumidoVO e : listaEfetivo) {
			if (e.getEfetivo()>alturaMaxima){
				alturaMaxima =e.getEfetivo();
			}
			
		}
		
		listaEfetivo.add(new EfetivoResumidoVO(++ultimoMes, ultimoAno));
		this.getGrafico().addSeries(new SerieVO(tela, listaEfetivo).getSerie());
	}


	public LineChartModel getGrafico() {
		if (grafico ==null){
			grafico = new LineChartModel();
		}
		return grafico;
	}




	public void setGrafico(LineChartModel grafico) {
		this.grafico = grafico;
	}

	public int getAlturaMaxima() {
		return alturaMaxima;
	}

	public void setAlturaMaxima(int alturaMaxima) {
		this.alturaMaxima = alturaMaxima;
	}
 
}