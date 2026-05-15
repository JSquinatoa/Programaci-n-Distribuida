package com.programacion.distribuida.authors.dtos;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter@Setter
@ToString
@Builder
public class AuthorDto {

    private Integer id;
    private String name;


}
