package com.CustomerSupport.demo.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor 
@NoArgsConstructor 
@Entity 
@Data
@Table(name="clients")
public class User {
  @Id 
@GeneratedValue (strategy = GenerationType.IDENTITY)
private Long id;
private String name;
private String email;
private String issue;
@Column(columnDefinition = "TEXT")
private String response;
}
