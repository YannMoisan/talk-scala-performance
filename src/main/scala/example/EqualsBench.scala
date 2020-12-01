package example

import java.util.concurrent.{ThreadLocalRandom, TimeUnit}

import cats.Eq
import cats.implicits.catsSyntaxEq
import org.openjdk.jmh.annotations._

@State(Scope.Benchmark)
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(value = 1, jvmArgsAppend = Array("-Djmh.stack.lines=3"))
@Threads(1)
@Warmup(iterations = 5, time = 3, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 3, timeUnit = TimeUnit.SECONDS)
class EqualsBench {

  var long1: Long = _
  var long2: Long = _

  @Setup
  def setUp(): Unit = {
    val random = ThreadLocalRandom.current()
    long1 = random.nextInt()
    long2 = random.nextInt()
  }

  @Benchmark
  @SuppressWarnings(Array("org.wartremover.warts.Equals"))
  def `==`(): Boolean = long1 == long2

  @Benchmark
  def `===`(): Boolean = long1 === long2

  @Benchmark
  def `eqv`(): Boolean = Eq[Long].eqv(long1, long2)

}
