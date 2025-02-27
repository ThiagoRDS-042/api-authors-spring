package br.com.authors.api_authors.modules.authors.entities;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "authors")
@NamedEntityGraph(name = "Author.address", attributeNodes = @NamedAttributeNode("address"))
public class Author {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false)
  private String name;

  @Column(unique = true)
  private String email;

  @Column(unique = true)
  private String tag;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private LocalDate birthdate;

  @Column(name = "address_id", nullable = true)
  private UUID addressId;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "address_id", insertable = false, updatable = false)
  private Address address;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @Column(name = "updtaed_at", nullable = false)
  private LocalDateTime updtaedAt;

  public Author() {
  }

  public Author(UUID id, String name, String email, String tag, String password, LocalDate birthdate, UUID addressId,
      Address address,
      LocalDateTime updtaedAt, LocalDateTime createdAt) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.tag = tag;
    this.password = password;
    this.birthdate = birthdate;
    this.addressId = addressId;
    this.address = address;
    this.updtaedAt = updtaedAt;
    this.createdAt = createdAt;
  }

  public Author(String name, String email, String tag, String password, LocalDate birthdate) {
    var today = LocalDateTime.now();

    this.name = name;
    this.email = email;
    this.tag = tag;
    this.password = password;
    this.birthdate = birthdate;
    this.updtaedAt = today;
  }

  public UUID getId() {
    return this.id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getTag() {
    return this.tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public LocalDate getBirthdate() {
    return this.birthdate;
  }

  public void setBirthdate(LocalDate birthdate) {
    this.birthdate = birthdate;
  }

  public Address getAddress() {
    return this.address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public UUID getAddressId() {
    return this.addressId;
  }

  public void setAddressId(UUID addressId) {
    this.addressId = addressId;
  }

  public LocalDateTime getCreatedAt() {
    return this.createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getUpdtaedAt() {
    return this.updtaedAt;
  }

  public void setUpdtaedAt(LocalDateTime updtaedAt) {
    this.updtaedAt = updtaedAt;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof Author)) {
      return false;
    }
    Author author = (Author) o;
    return Objects.equals(id, author.id) && Objects.equals(name, author.name) && Objects.equals(email, author.email)
        && Objects.equals(tag, author.tag) && Objects.equals(password, author.password)
        && Objects.equals(birthdate, author.birthdate) && Objects.equals(addressId, author.addressId) &&
        Objects.equals(address, author.address) && Objects.equals(createdAt, author.createdAt)
        && Objects.equals(updtaedAt, author.updtaedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, email, tag, password, birthdate, addressId, address, createdAt, updtaedAt);
  }

  @Override
  public String toString() {
    return "{" +
        " id='" + getId() + "'" +
        ", name='" + getName() + "'" +
        ", email='" + getEmail() + "'" +
        ", tag='" + getTag() + "'" +
        ", password='" + getPassword() + "'" +
        ", birthdate='" + getBirthdate() + "'" +
        ", addressId='" + getAddressId() + "'" +
        ", address='" + getAddress() + "'" +
        ", createdAt='" + getCreatedAt() + "'" +
        ", updtaedAt='" + getUpdtaedAt() + "'" +
        "}";
  }

}
