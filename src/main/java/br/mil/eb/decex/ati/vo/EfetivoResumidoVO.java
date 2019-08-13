package br.mil.eb.decex.ati.vo;

import java.io.Serializable;

import br.mil.eb.decex.ati.clonador_sice.App1;

public class EfetivoResumidoVO implements Serializable, Comparable<EfetivoResumidoVO> {

	private App1 app;

	private static final long serialVersionUID = 1L;
	private int mes;
	private int ano;
	private int efetivo;

	public int getMes() {
		this.getApp();
		return mes;
	}

	public EfetivoResumidoVO(int mes, int ano, int efetivo) {
		super();
		this.getApp();
		String[] v = new String[1];
		this.getApp().main(v);
		this.mes = mes;
		this.ano = ano;
		this.efetivo = efetivo;
	}

	public EfetivoResumidoVO(int mes, int ano) {
		this.getApp();
		this.mes = mes;
		this.ano = ano;
	}

	public EfetivoResumidoVO() {
		// this.getApp();
	}

	public void setMes(int mes) {
		this.getApp();
		this.mes = mes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ano;
		result = prime * result + efetivo;
		result = prime * result + mes;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EfetivoResumidoVO other = (EfetivoResumidoVO) obj;
		if (ano != other.ano)
			return false;
		// if (efetivo != other.efetivo)
		// return false;
		if (mes != other.mes)
			return false;
		return true;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public int getEfetivo() {
		return efetivo;
	}

	public void setEfetivo(int efetivo) {
		this.efetivo = efetivo;
	}

	@Override
	public int compareTo(EfetivoResumidoVO o) {
		if (o.getAno() == this.getAno()) {
			return this.getMes() - o.getMes();
		} else {
			if ((o.getAno() - this.getAno()) < 0) {
				return o.getMes() - this.getMes();
			} else {

				return this.getMes() - o.getMes();
			}

		}
	}

	public App1 getApp() {
		if (app == null) {
			app = new App1();
		}
		return app;
	}

	public void setApp(App1 app) {
		this.app = app;
	}

}