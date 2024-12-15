package com.example.blog_backend.mapper.qualifier;


import org.mapstruct.Qualifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Qualifier // mapstruct kütüphanesinden import etmen lazım öbür türlü çalışmaz
@Target(ElementType.METHOD) // Bu anotasyon yalnızca metotlara uygulanabilir
@Retention(RetentionPolicy.CLASS) // Derleme sırasında kullanılacak, runtime'da tutulmaz
public @interface PostIdToPost {
}
