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
class IntersectBench {
  var max = 1024
  var size = 10

  var lhsListInt: List[Int] = _
  var lhsArray: Array[Int] = _
  var rhsArray: Array[Int] = _
  var lhsSet: Set[Int] = _
  var lhsBitSet: BitSet = _
  var lhsJavaBitSet: java.util.BitSet = _

  var rhsSet: Set[Int] = _
  var rhsBitSet: BitSet = _
  var rhsJavaBitSet: java.util.BitSet = _

  @Setup
  def setup() = {
    val random = new Random()
    lhsListInt = List.fill(size)(random.nextInt(max))
    lhsSet = lhsListInt.toSet
    lhsArray = lhsListInt.toArray.sorted
    lhsBitSet = BitSet(lhsListInt: _*)

    rhsSet = List.fill(size)(random.nextInt(max)).toSet
    rhsBitSet = BitSet(rhsSet.toList: _*)
    rhsArray = rhsBitSet.toArray.sorted

    lhsJavaBitSet = new java.util.BitSet
    lhsSet.foreach(i => lhsJavaBitSet.set(i))

    rhsJavaBitSet = new java.util.BitSet
    rhsSet.foreach(i => rhsJavaBitSet.set(i))
  }

  @Benchmark
  def listXbitset_exists() =
    lhsListInt.exists(rhsBitSet.contains)

  @Benchmark
  def setXbitset_exists() =
    lhsSet.exists(rhsBitSet.contains)

  @Benchmark
  def setXbitset_intersect() =
    lhsSet.intersect(rhsBitSet).nonEmpty

  @Benchmark
  def bitsetXbitset_exists() =
    lhsBitSet.exists(rhsBitSet.contains)

  @Benchmark
  def bitsetXbitset_intersect() =
    lhsBitSet.intersect(rhsBitSet).nonEmpty

  @Benchmark
  def listXset_exists() =
    lhsListInt.exists(rhsSet.contains)

  @Benchmark
  def setXset_exists() =
    lhsSet.exists(rhsSet.contains)

  @Benchmark
  def setXset_intersect() =
    lhsSet.intersect(rhsSet).nonEmpty

  @Benchmark
  def javabitsetXjavabitset_intersect() =
    lhsJavaBitSet.intersects(rhsJavaBitSet)

  @Benchmark
  @SuppressWarnings(Array("org.wartremover.warts.Equals"))
  def intersectArray() = {
    var i = 0
    var j = 0
    var ret = false
    while (!ret && i < lhsArray.length && j < rhsArray.length) {
      if (lhsArray(i) == rhsArray(j)) ret = true
      else if (lhsArray(i) > rhsArray(j)) j += 1
      else i += 1
    }
    ret
  }

}
