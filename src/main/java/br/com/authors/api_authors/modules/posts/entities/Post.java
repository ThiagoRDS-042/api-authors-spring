package br.com.authors.api_authors.modules.posts.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import br.com.authors.api_authors.modules.authors.entities.Author;
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
public class Post {
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

  @Column(nullable = false, name = "`like`")
  @ColumnDefault(value = "0")
  private Integer like;

  @Column(name = "des_like", nullable = false)
  @ColumnDefault(value = "0")
  private Integer desLike;

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
  @Column(name = "updtaed_at", nullable = false)
  private LocalDateTime updtaedAt;

  @Column(nullable = false, name = "author_id")
  private UUID authorId;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "author_id", insertable = false, updatable = false)
  private Author author;

  public Post() {
  }

  public Post(UUID id, String title, String content, String description, String keywords, Integer like, Integer desLike,
      Integer version, LocalDateTime createdAt, LocalDateTime publishedAt, LocalDateTime updtaedAt, UUID authorId,
      Author author) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.description = description;
    this.keywords = keywords;
    this.like = like;
    this.desLike = desLike;
    this.version = version;
    this.createdAt = createdAt;
    this.publishedAt = publishedAt;
    this.updtaedAt = updtaedAt;
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
    this.like = 0;
    this.desLike = 0;
    this.version = 1;
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

  public Integer getLike() {
    return this.like;
  }

  public void setLike(Integer like) {
    this.like = like;
  }

  public Integer getDesLike() {
    return this.desLike;
  }

  public void setDesLike(Integer desLike) {
    this.desLike = desLike;
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

  public LocalDateTime getUpdtaedAt() {
    return this.updtaedAt;
  }

  public void setUpdtaedAt(LocalDateTime updtaedAt) {
    this.updtaedAt = updtaedAt;
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
        && Objects.equals(like, post.like) && Objects.equals(desLike, post.desLike)
        && Objects.equals(version, post.version) && Objects.equals(createdAt, post.createdAt)
        && Objects.equals(publishedAt, post.publishedAt) && Objects.equals(updtaedAt, post.updtaedAt)
        && Objects.equals(authorId, post.authorId) && Objects.equals(author, post.author);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, content, description, keywords, like, desLike, version, createdAt, publishedAt,
        updtaedAt, authorId, author);
  }

  @Override
  public String toString() {
    return "{" +
        " id='" + getId() + "'" +
        ", title='" + getTitle() + "'" +
        ", content='" + getContent() + "'" +
        ", description='" + getDescription() + "'" +
        ", keywords='" + getKeywords() + "'" +
        ", like='" + getLike() + "'" +
        ", desLike='" + getDesLike() + "'" +
        ", version='" + getVersion() + "'" +
        ", createdAt='" + getCreatedAt() + "'" +
        ", publishedAt='" + getPublishedAt() + "'" +
        ", updtaedAt='" + getUpdtaedAt() + "'" +
        ", authorId='" + getAuthorId() + "'" +
        ", author='" + getAuthor() + "'" +
        "}";
  }
}
