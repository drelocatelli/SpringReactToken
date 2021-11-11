package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Departments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Department {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String title;

	@OneToMany(mappedBy = "department")
	@JsonBackReference
	private List<User> users;

}
