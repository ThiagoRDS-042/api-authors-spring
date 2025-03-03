package br.com.thiagoRDS.api_authors.modules.authors.entities;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "addresses")
public class Address implements Serializable {
  private static final long serialVersionUID = -8332834589L;

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false)
  private String city;

  @Column(nullable = false)
  private String street;

  @Column(name = "zip_code", nullable = false)
  private String zipCode;

  @Column(name = "state_code", nullable = false)
  private String stateCode;

  @Column(nullable = true)
  private String complement;

  @Column(nullable = false)
  private String neighborhood;

  public Address() {
  }

  public Address(UUID id, String city, String street, String zipCode, String stateCode, String complement,
      String neighborhood) {
    this.id = id;
    this.city = city;
    this.street = street;
    this.zipCode = zipCode;
    this.stateCode = stateCode;
    this.complement = complement;
    this.neighborhood = neighborhood;
  }

  public Address(String city, String street, String zipCode, String stateCode, String complement,
      String neighborhood) {
    this.city = city;
    this.street = street;
    this.zipCode = zipCode;
    this.stateCode = stateCode;
    this.complement = complement;
    this.neighborhood = neighborhood;
  }

  public UUID getId() {
    return this.id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getCity() {
    return this.city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getStreet() {
    return this.street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getZipCode() {
    return this.zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  public String getStateCode() {
    return this.stateCode;
  }

  public void setStateCode(String stateCode) {
    this.stateCode = stateCode;
  }

  public String getComplement() {
    return this.complement;
  }

  public void setComplement(String complement) {
    this.complement = complement;
  }

  public String getNeighborhood() {
    return this.neighborhood;
  }

  public void setNeighborhood(String neighborhood) {
    this.neighborhood = neighborhood;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof Address)) {
      return false;
    }
    Address address = (Address) o;
    return Objects.equals(id, address.id) && Objects.equals(city, address.city)
        && Objects.equals(street, address.street) && Objects.equals(zipCode, address.zipCode)
        && Objects.equals(stateCode, address.stateCode) && Objects.equals(complement, address.complement)
        && Objects.equals(neighborhood, address.neighborhood);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, city, street, zipCode, stateCode, complement, neighborhood);
  }

  @Override
  public String toString() {
    return "{" +
        " id='" + getId() + "'" +
        ", city='" + getCity() + "'" +
        ", street='" + getStreet() + "'" +
        ", zipCode='" + getZipCode() + "'" +
        ", stateCode='" + getStateCode() + "'" +
        ", complement='" + getComplement() + "'" +
        ", neighborhood='" + getNeighborhood() + "'" +
        "}";
  }

}
