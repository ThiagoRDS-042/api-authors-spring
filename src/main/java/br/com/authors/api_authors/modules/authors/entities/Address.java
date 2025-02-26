package br.com.authors.api_authors.modules.authors.entities;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotEmpty;

import java.util.Objects;

@Entity(name = "addresses")
public class Address {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @NotEmpty
  @Column(nullable = false)
  private String city;

  @NotEmpty
  @Column(nullable = false)
  private String street;

  @NotEmpty
  @Column(name = "zip_code", nullable = false)
  private String zipCode;

  @NotEmpty
  @Column(name = "state_code", nullable = false)
  private String stateCode;

  @Column(nullable = true)
  private String complement;

  @NotEmpty
  @Column(nullable = false)
  private String neighborhood;

  @NotEmpty
  @Column(name = "author_id", nullable = false)
  private UUID authorId;

  @OneToOne
  @JoinColumn(name = "author_id", insertable = false, updatable = false)
  private Author author;

  public Address() {
  }

  public Address(UUID id, String city, String street, String zipCode, String stateCode, String complement,
      String neighborhood, UUID authorId, Author author) {
    this.id = id;
    this.city = city;
    this.street = street;
    this.zipCode = zipCode;
    this.stateCode = stateCode;
    this.complement = complement;
    this.neighborhood = neighborhood;
    this.authorId = authorId;
    this.author = author;
  }

  public Address(String city, String street, String zipCode, String stateCode, String complement,
      String neighborhood, UUID authorId, Author author) {
    this.city = city;
    this.street = street;
    this.zipCode = zipCode;
    this.stateCode = stateCode;
    this.complement = complement;
    this.neighborhood = neighborhood;
    this.authorId = authorId;
    this.author = author;
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

  public UUID getAuthorId() {
    return this.authorId;
  }

  public void setAuthorId(UUID authorId) {
    this.authorId = authorId;
  }

  public Author getAuthor() {
    return this.author;
  }

  public void setAuthor(Author author) {
    this.author = author;
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
        && Objects.equals(neighborhood, address.neighborhood) && Objects.equals(authorId, address.authorId)
        && Objects.equals(author, address.author);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, city, street, zipCode, stateCode, complement, neighborhood, authorId, author);
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
        ", authorId='" + getAuthorId() + "'" +
        ", author='" + getAuthor() + "'" +
        "}";
  }

}
