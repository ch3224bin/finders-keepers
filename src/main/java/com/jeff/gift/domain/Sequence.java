package com.jeff.gift.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;

@Entity
@Table(name = "SEQUENCE")
@Getter
public class Sequence {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
}
