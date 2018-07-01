package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import static java.util.Arrays.asList;

public class Produto {
	
	@NotNull
	private String id;
	
	@NotNull
	private String ean;
	
	@NotNull
	private String title;
	
	@NotNull
	private String brand;
	
	@NotNull
	private float price;
	
	@NotNull
	private int stock;
	
	public static List<Produto> produtoList;
	
	static {
		produtoController();
	}
	
	
	public Produto(String id, String ean, String title, String brand, float price, int stock) {
		this.id = id;
		this.ean = ean;
		this.title = title;
		this.brand = brand;
		this.price = price;
		this.stock = stock;
	}
	
	public Produto() {
	}
	
	private static void produtoController() {
		produtoList = new ArrayList<>(asList(new Produto("1", "123", "teste", "teste", 1, 1), new Produto("2", "123", "teste 02", "teste", 1, 1)));
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEan() {
		return ean;
	}

	public void setEan(String ean) {
		this.ean = ean;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}