package br.com.thiagoRDS.api_authors.modules.authors.entities;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

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
@Table(name = "recovery_tokens")
@NamedEntityGraph(name = "RecoveryToken")
@NamedEntityGraph(name = "RecoveryToken.Author", attributeNodes = @NamedAttributeNode("author"))
public class RecoveryToken implements Serializable {
  private static final long serialVersionUID = 1233227153L;

  public static final Integer DURATION_IN_MINUTES = 5;

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false, unique = true)
  private UUID code;

  @Column(nullable = false, name = "author_id", unique = true)
  private UUID authorId;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "author_id", insertable = false, updatable = false)
  private Author author;

  @Column(nullable = false, name = "expires_at")
  private LocalDateTime expiresAt;

  public RecoveryToken() {
  }

  public RecoveryToken(UUID id, UUID code, UUID authorId, Author author, LocalDateTime expiresAt) {
    this.id = id;
    this.code = code;
    this.authorId = authorId;
    this.author = author;
    this.expiresAt = expiresAt;
  }

  public RecoveryToken(UUID id, UUID code, UUID authorId) {
    this.id = id;
    this.code = code;
    this.authorId = authorId;
    this.expiresAt = LocalDateTime.now().plus(Duration.ofMinutes(DURATION_IN_MINUTES));
  }

  public RecoveryToken(UUID code, UUID authorId) {
    this.code = code;
    this.authorId = authorId;
    this.expiresAt = LocalDateTime.now().plus(Duration.ofMinutes(DURATION_IN_MINUTES));
  }

  public UUID getId() {
    return this.id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public UUID getCode() {
    return this.code;
  }

  public void setCode(UUID code) {
    this.code = code;
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

  public LocalDateTime getExpiresAt() {
    return this.expiresAt;
  }

  public void setExpiresAt(LocalDateTime expiresAt) {
    this.expiresAt = expiresAt;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof RecoveryToken)) {
      return false;
    }
    RecoveryToken recoveryToken = (RecoveryToken) o;
    return Objects.equals(id, recoveryToken.id) && Objects.equals(code, recoveryToken.code)
        && Objects.equals(authorId, recoveryToken.authorId) && Objects.equals(author, recoveryToken.author)
        && Objects.equals(expiresAt, recoveryToken.expiresAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, code, authorId, author, expiresAt);
  }

  @Override
  public String toString() {
    return "{" +
        " id='" + getId() + "'" +
        ", code='" + getCode() + "'" +
        ", authorId='" + getAuthorId() + "'" +
        ", author='" + getAuthor() + "'" +
        ", expiresAt='" + getExpiresAt() + "'" +
        "}";
  }

  @Override
  public RecoveryToken clone() {
    try {
      return (RecoveryToken) super.clone();
    } catch (CloneNotSupportedException e) {
      return new RecoveryToken(this.id, this.code, this.authorId, this.author, this.expiresAt);
    }
  }
}
