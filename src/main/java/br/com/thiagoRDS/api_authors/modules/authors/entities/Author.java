package br.com.thiagoRDS.api_authors.modules.authors.entities;

import java.time.LocalDateTime;
import java.io.Serializable;
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
@NamedEntityGraph(name = "Author")
@NamedEntityGraph(name = "Author.address", attributeNodes = @NamedAttributeNode("address"))
public class Author implements Serializable {
  private static final long serialVersionUID = -273282389L;

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

  @Column(nullable = true)
  private String avatar;

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

  @CreationTimestamp
  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;

  public Author() {
  }

  public Author(UUID id, String name, String email, String tag, String password, String avatar, LocalDate birthdate,
      UUID addressId, Address address, LocalDateTime updatedAt, LocalDateTime createdAt) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.tag = tag;
    this.password = password;
    this.avatar = avatar;
    this.birthdate = birthdate;
    this.addressId = addressId;
    this.address = address;
    this.updatedAt = updatedAt;
    this.createdAt = createdAt;
  }

  public Author(String name, String email, String tag, String password, LocalDate birthdate) {
    this.name = name;
    this.email = email;
    this.tag = tag;
    this.password = password;
    this.birthdate = birthdate;
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

  public String getAvatar() {
    return this.avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
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

  public LocalDateTime getUpdatedAt() {
    return this.updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
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
        && Objects.equals(avatar, author.avatar) && Objects.equals(birthdate, author.birthdate)
        && Objects.equals(addressId, author.addressId) && Objects.equals(address, author.address)
        && Objects.equals(createdAt, author.createdAt) && Objects.equals(updatedAt, author.updatedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, email, tag, password, avatar, birthdate, addressId, address, createdAt, updatedAt);
  }

  @Override
  public String toString() {
    return "{" +
        " id='" + getId() + "'" +
        ", name='" + getName() + "'" +
        ", email='" + getEmail() + "'" +
        ", tag='" + getTag() + "'" +
        ", password='" + getPassword() + "'" +
        ", avatar='" + getAvatar() + "'" +
        ", birthdate='" + getBirthdate() + "'" +
        ", addressId='" + getAddressId() + "'" +
        ", address='" + getAddress() + "'" +
        ", createdAt='" + getCreatedAt() + "'" +
        ", updatedAt='" + getUpdatedAt() + "'" +
        "}";
  }

  @Override
  public Author clone() {
    try {
      return (Author) super.clone();
    } catch (CloneNotSupportedException e) {
      return new Author(this.id, this.name, this.email, this.tag, this.password, this.avatar, this.birthdate,
          this.addressId, this.address, this.updatedAt, this.createdAt);
    }
  }
}
