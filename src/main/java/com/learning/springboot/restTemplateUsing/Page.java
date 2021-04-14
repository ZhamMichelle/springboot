package com.learning.springboot.restTemplateUsing;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)   //библиотеки обработки JSON Jackson, которая показывает, что любые поля, не связанные с полями класса, должны быть проигнорированы.
public class Page {
    private String name;
    private String about;
    private String phone;
    private String website;
}
