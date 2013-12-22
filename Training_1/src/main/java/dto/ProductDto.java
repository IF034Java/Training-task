package dto;

import java.util.List;


public class ProductDto {

    private Integer id;
    private String name;
    private Double price;
    private String expirationDate;
    
    private List<ClientDto> clientDtos;
    
	public List<ClientDto> getClientDtos() {
		return clientDtos;
	}
	public void setClientDtos(List<ClientDto> clientDtos) {
		this.clientDtos = clientDtos;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

    
}
