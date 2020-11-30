package example

import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations.{
  Benchmark,
  BenchmarkMode,
  Fork,
  Measurement,
  Mode,
  OutputTimeUnit,
  Scope,
  Setup,
  State,
  Threads,
  Warmup
}

@State(Scope.Benchmark)
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(value = 1, jvmArgsAppend = Array("-Djmh.stack.lines=3"))
@Threads(1)
@Warmup(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
class HashCodeBench {
  var int: Int = _
  var long: Long = _
  var wrappedInt: Wrapped = _
  var valueClass: ValueClass = _
  @Setup
  def setup() = {
    int = 1234
    long = 1234L
    wrappedInt = Wrapped(1234)
    valueClass = ValueClass(1234)
  }

  @Benchmark
  def hashCode_int(): Int =
    int.hashCode()

  @Benchmark
  def `##_int`(): Int =
    int.##

  @Benchmark
  def hashCode_long(): Int =
    long.hashCode()

  @Benchmark
  def `##_long`(): Int =
    long.##

  @Benchmark
  def hashCode_valueclass(): Int =
    valueClass.hashCode()

  @Benchmark
  def `##_valueclass`(): Int =
    valueClass.##

  @Benchmark
  def hashCode_wrapped(): Int =
    wrappedInt.hashCode()

  @Benchmark
  def `##_wrapped`(): Int =
    wrappedInt.##

}

case class ValueClass(i: Int) extends AnyVal

case class Wrapped(i: Int)
