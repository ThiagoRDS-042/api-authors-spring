package br.com.thiagoRDS.api_authors.modules.posts.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import br.com.thiagoRDS.api_authors.modules.authors.entities.Author;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedSubgraph;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "posts")
@NamedEntityGraph(name = "Post")
@NamedEntityGraph(name = "Post.author.address", attributeNodes = @NamedAttributeNode(value = "author", subgraph = "Author.address"), subgraphs = {
    @NamedSubgraph(name = "Author.address", attributeNodes = @NamedAttributeNode("address")) })
public class Post implements Serializable {
  private static final long serialVersionUID = -112237786L;

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false, unique = true)
  private String title;

  @Column(nullable = false)
  private String content;

  @Column(nullable = false)
  private String description;

  @Column(nullable = false)
  private String keywords;

  @Column(nullable = false)
  @ColumnDefault(value = "0")
  private Integer up;

  @Column(nullable = false)
  @ColumnDefault(value = "1")
  private Integer version;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @CreationTimestamp
  @Column(name = "published_at", nullable = true)
  private LocalDateTime publishedAt;

  @CreationTimestamp
  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;

  @Column(nullable = false, name = "author_id")
  private UUID authorId;

  @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH })
  @JoinColumn(name = "author_id", insertable = false, updatable = false)
  private Author author;

  public Post() {
  }

  public Post(UUID id, String title, String content, String description, String keywords, Integer up,
      Integer version, LocalDateTime createdAt, LocalDateTime publishedAt, LocalDateTime updatedAt, UUID authorId,
      Author author) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.description = description;
    this.keywords = keywords;
    this.up = up;
    this.version = version;
    this.createdAt = createdAt;
    this.publishedAt = publishedAt;
    this.updatedAt = updatedAt;
    this.authorId = authorId;
    this.author = author;
  }

  public Post(String title, String content, String description, String keywords, UUID authorId, Author author) {
    this.title = title;
    this.content = content;
    this.description = description;
    this.keywords = keywords;
    this.authorId = authorId;
    this.author = author;
    this.up = 0;
    this.version = 1;
    this.publishedAt = LocalDateTime.now();
  }

  public Post(String title, String content, String description, String keywords, UUID authorId) {
    this.title = title;
    this.content = content;
    this.description = description;
    this.keywords = keywords;
    this.authorId = authorId;
    this.up = 0;
    this.version = 1;
    this.publishedAt = LocalDateTime.now();
  }

  public UUID getId() {
    return this.id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return this.content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getKeywords() {
    return this.keywords;
  }

  public void setKeywords(String keywords) {
    this.keywords = keywords;
  }

  public Integer getUp() {
    return this.up;
  }

  public void setUp(Integer up) {
    this.up = up;
  }

  public Integer getVersion() {
    return this.version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

  public LocalDateTime getCreatedAt() {
    return this.createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getPublishedAt() {
    return this.publishedAt;
  }

  public void setPublishedAt(LocalDateTime publishedAt) {
    this.publishedAt = publishedAt;
  }

  public LocalDateTime getUpdatedAt() {
    return this.updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
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
    if (!(o instanceof Post)) {
      return false;
    }
    Post post = (Post) o;
    return Objects.equals(id, post.id) && Objects.equals(title, post.title) && Objects.equals(content, post.content)
        && Objects.equals(description, post.description) && Objects.equals(keywords, post.keywords)
        && Objects.equals(up, post.up) && Objects.equals(version, post.version)
        && Objects.equals(createdAt, post.createdAt) && Objects.equals(publishedAt, post.publishedAt)
        && Objects.equals(updatedAt, post.updatedAt) && Objects.equals(authorId, post.authorId)
        && Objects.equals(author, post.author);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, content, description, keywords, up, version, createdAt, publishedAt,
        updatedAt, authorId, author);
  }

  @Override
  public String toString() {
    return "{" +
        " id='" + getId() + "'" +
        ", title='" + getTitle() + "'" +
        ", content='" + getContent() + "'" +
        ", description='" + getDescription() + "'" +
        ", keywords='" + getKeywords() + "'" +
        ", up='" + getUp() + "'" +
        ", version='" + getVersion() + "'" +
        ", createdAt='" + getCreatedAt() + "'" +
        ", publishedAt='" + getPublishedAt() + "'" +
        ", updatedAt='" + getUpdatedAt() + "'" +
        ", authorId='" + getAuthorId() + "'" +
        ", author='" + getAuthor() + "'" +
        "}";
  }

  @Override
  public Post clone() {
    try {
      return (Post) super.clone();
    } catch (CloneNotSupportedException e) {
      return new Post(this.id, this.title, this.content, this.description, this.keywords, this.up, this.version,
          this.createdAt, this.publishedAt, this.updatedAt, this.authorId, this.author);
    }
  }
}
