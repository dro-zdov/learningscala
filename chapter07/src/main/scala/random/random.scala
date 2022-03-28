package object random {
  private var next = 0
  def nextInt(): Int = {
    next = (next * 1664525 + 1013904223) % math.pow(2, 32).toInt
    next
  }
  def nextDouble(): Double = {
    nextInt().toDouble
  }
  def setSeed(seed: Int): Unit = {
    if (next == 0) next = seed
  }
}
