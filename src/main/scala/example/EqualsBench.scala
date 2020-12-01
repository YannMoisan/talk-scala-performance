package example

import java.util.concurrent.{ThreadLocalRandom, TimeUnit}

import cats.Eq
import cats.implicits.catsSyntaxEq
import org.openjdk.jmh.annotations._

import scala.collection.BitSet

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

  var id1: Id = _
  var id2: Id = _

  @Setup
  def setUp(): Unit = {
    val random = ThreadLocalRandom.current()
    long1 = random.nextInt()
    long2 = random.nextInt()

    id1 = Id(random.nextInt())
    id2 = Id(random.nextInt())
    val s = BitSet()
  }

  @Benchmark
  @SuppressWarnings(Array("org.wartremover.warts.Equals"))
  def `==`(): Boolean = long1 == long2

  @Benchmark
  def `===`(): Boolean = long1 === long2

  @Benchmark
  def `eqv`(): Boolean = Eq[Long].eqv(long1, long2)

  @Benchmark
  @SuppressWarnings(Array("org.wartremover.warts.Equals"))
  def `id_==`(): Boolean = id1 == id2

  @Benchmark
  def `id_===`(): Boolean = id1 === id2

  @Benchmark
  def `id_eqv`(): Boolean = Eq.eqv(id1, id2)

}

case class Id(i: Long) extends AnyVal

object Id {
  implicit val eqInstance: Eq[Id] = Eq.fromUniversalEquals
}
