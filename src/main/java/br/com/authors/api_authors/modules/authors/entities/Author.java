package br.com.authors.api_authors.modules.authors.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

import java.util.Objects;

@Entity
@Table(name = "authors")
public class Author {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @NotEmpty
  @Column(nullable = false)
  private String name;

  @NotEmpty
  @Column(unique = true)
  private String email;

  @NotEmpty
  @Column(unique = true)
  private String tag;

  @NotEmpty
  @Column(nullable = false)
  private LocalDateTime birthdate;

  @NotEmpty
  @CreationTimestamp
  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @NotEmpty
  @Column(name = "updtaed_at", nullable = false)
  private LocalDateTime updtaedAt;

  public Author() {
  }

  public Author(String name, String email, String tag, LocalDateTime birthdate,
      LocalDateTime updtaedAt) {
    this.name = name;
    this.email = email;
    this.tag = tag;
    this.birthdate = birthdate;
    this.updtaedAt = updtaedAt;
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

  public LocalDateTime getBirthdate() {
    return this.birthdate;
  }

  public void setBirthdate(LocalDateTime birthdate) {
    this.birthdate = birthdate;
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
        && Objects.equals(tag, author.tag) && Objects.equals(birthdate, author.birthdate)
        && Objects.equals(createdAt, author.createdAt) && Objects.equals(updtaedAt, author.updtaedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, email, tag, birthdate, createdAt, updtaedAt);
  }

  @Override
  public String toString() {
    return "{" +
        " id='" + getId() + "'" +
        ", name='" + getName() + "'" +
        ", email='" + getEmail() + "'" +
        ", tag='" + getTag() + "'" +
        ", birthdate='" + getBirthdate() + "'" +
        ", createdAt='" + getCreatedAt() + "'" +
        ", updtaedAt='" + getUpdtaedAt() + "'" +
        "}";
  }

}
