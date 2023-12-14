package aoc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE})
public @interface AdventOfCode {
    public int year() default 0;
    public int day() default 0;
    public String link() default "";

}
