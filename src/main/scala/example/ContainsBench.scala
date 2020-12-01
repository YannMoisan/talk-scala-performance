package example

import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations._

import scala.collection.immutable.BitSet
import scala.util.Random

@State(Scope.Benchmark)
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(value = 1, jvmArgsAppend = Array("-Djmh.stack.lines=3"))
@Threads(1)
@Warmup(iterations = 5, time = 3, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 3, timeUnit = TimeUnit.SECONDS)
class ContainsBench {
  var set: Set[Int] = _
  var bitSet: BitSet = _
  var javaBitSet: java.util.BitSet = _

  var elem: Int = _

  @Setup
  def setUp(): Unit = {
    val random = new Random()
    set = List.fill(100)(random.nextInt(65535)).toSet
    bitSet = BitSet(set.toSeq: _*)
    javaBitSet = new java.util.BitSet
    set.foreach(i => javaBitSet.set(i))

    elem = 512
  }

  @Benchmark
  def contains_set(): Boolean =
    set.contains(elem)

  @Benchmark
  def contains_bitSet(): Boolean =
    bitSet.contains(elem)

  @Benchmark
  def contains_javaBitSet(): Boolean =
    javaBitSet.get(elem)
}
