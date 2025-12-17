package annotations;
import java.lang.annotation.*;
/**
 *
 * The Music Annotation is an annotation used to hold the details of music used.
 * @see sound.BackgroundMusic
 * @since v0.0.5
 * @author Sri Koushik JK
 * @version v0.0.5
 * */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Music {
    String name() default "";
    String path();
    boolean isBackground() default false;
}
