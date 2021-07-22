package com.luxoft.trainings.java.rabbit.jms;

import io.vavr.CheckedConsumer;
import io.vavr.CheckedFunction1;
import io.vavr.CheckedFunction2;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public class ScopedFunctions {

  public <T> void with(@NotNull T self,
                       @NotNull CheckedConsumer<T> c) {
    c.unchecked().accept(self);
  }

  public <T> @NotNull T applyChecked(@NotNull T self,
                                     @NotNull CheckedConsumer<T> c) {
    with(self, c);
    return self;
  }

  public <T, U> @NotNull Tuple2<T, U> zip(@NotNull T self,
                                          @NotNull CheckedFunction1<T, U> f) {
    return Tuple.of(self, f.unchecked().apply(self));
  }

  public <T, U, R> R applyChecked(@NotNull Tuple2<T, U> self,
                                  @NotNull CheckedFunction2<T, U, R> f) {
    return f.unchecked().apply(self._1, self._2);
  }
}
