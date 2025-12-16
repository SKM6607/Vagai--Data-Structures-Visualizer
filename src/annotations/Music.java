package annotations;
import java.lang.annotation.*;
/**
 *
 * The Music Annotation is an annotation used to hold the details of music used.
 * @see sound.BackgroundMusic
 * @author Sri Koushik JK
 * @version v0.0.4
 * */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Music {
    String name() default "";
    String path() default "C:\\";
    boolean isBackground() default false;
}
