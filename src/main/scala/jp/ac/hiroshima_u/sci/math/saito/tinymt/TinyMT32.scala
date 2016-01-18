package jp.ac.hiroshima_u.sci.math.saito.tinymt

import java.io.IOException
import java.math.BigInteger

/**
  * TinyMT is a pseudo random number generator.
  * <p>
  * To get an instance, call {@link TinyMT32#getDefault()},
  * {@link TinyMT32#getDefault(long)}, {@link TinyMT32#getDefault(String)}
  * or {@link TinyMT32#getDefault(int[])}.
  * </p>
  * <p>
  * This class is <strong>not synchronized</strong>, One way to use TinyMT in
  * concurrent environment is to use an instance per a thread.
  * </p>
  * <p>
  * This class supports jump function. User can get an array of pseudo random
  * number generators by calling
  * {@link TinyMT32#getDefaultArray(int, long, long)}, or
  * {@link TinyMT32#getDefaultArray(int, String, long)}.
  * </p>
  * <p>
  * This class supports discrete characteristic polynomial generators. User can
  * get an array of pseudo random number generators whose characteristic
  * polynomials are discrete by calling
  * {@link TinyMT32#getTinyMTArray(int, long)}.
  * </p>
  *
  * @author M. Saito
  * @see <a href=
  *      "http://www.math.sci.hiroshima-u.ac.jp/~m-mat/MT/TINYMT/index.html">
  *      TinyMT web page</a>
  */
object TinyMT32 {
  /** bit size of int. */
  private val INT_SIZE: Int = 32
  /** least long over int. */
  private val LONG_LIMIT: Long = 0x100000000L
  /** initialize shift. */
  private val INITIALIZE_SHIFT: Int = 27
  /** initialize shift. */
  private val INITIALIZE_SHIFT2: Int = 30
  /** magic number. */
  private val MAGIC_NUMBER1: Int = 1664525
  /** magic number. */
  private val MAGIC_NUMBER2: Int = 1566083941
  /** magic number. */
  private val MAGIC_NUMBER3: Int = 1812433253
  /** int to float shift. */
  private val INT_TO_FLOAT_SHIFT: Int = 9
  /** long to double shift. */
  private val LONG_TO_DOUBLE_SHIFT: Int = 12
  /** hexadecimal base. */
  private val HEXA_DECIMAL_BASE: Int = 16
  /** int to unsigned long mask. */
  private val INT_TO_LONG_MASK: Long = 0xffffffffL
  /** long to double mask. */
  private val LONG_TO_DOUBLE_MASK: Long = 0x3ff0000000000000L
  /** basic jump step.
    * every jump step is a multiple of this step.
    */
  private val BASIC_JUMP_STEP: BigInteger = new BigInteger("2").pow(64)
  /** mask pattern to limit internal size. */
  private val MASK: Int = 0x7fffffff
  /** fixed shift 0. */
  private val SH0: Int = 1
  /** fixed shift 1. */
  private val SH1: Int = 10
  /** fixed 8 bit shift. */
  private val SH8: Int = 8
  /** pre loop before generation. */
  private val MIN_LOOP: Int = 8

  /**
    * Factory method which returns the TinyMT with the first generated
    * parameter of TinyMTDC.
    *
    * @param seed
    * seed of pseudo random numbers.
    * @return TinyMT with the first parameter.
    */
  def getDefault (seed: String): TinyMT32 = {
    val defaultParameter = TinyMT32Parameter.getDefaultParameter
    val tiny = new TinyMT32(defaultParameter)
    tiny.setSeed(seed)
    tiny
  }

  /**
    * Factory method which returns the TinyMT with the first generated
    * parameter of TinyMTDC.
    *
    * @param seed
    * seed of pseudo random numbers.
    * @return TinyMT with the first parameter.
    */
  def getDefault (seed: Long): TinyMT32 = {
    val defaultParameter = TinyMT32Parameter.getDefaultParameter
    val tiny = new TinyMT32(defaultParameter)
    tiny.setSeed(seed)
    tiny
  }

  /**
    * get default TinyMT32 with seeding by array.
    *
    * @param seeds seeds for initialization.
    * @return random number generator TinyMT32
    */
  def getDefault (seeds: Array[Int]): TinyMT32 = {
    val defaultParameter: TinyMT32Parameter = TinyMT32Parameter.getDefaultParameter
    val tiny: TinyMT32 = new TinyMT32(defaultParameter)
    tiny.setSeed(seeds)
    tiny
  }

  /**
    * Factory method which returns the TinyMT with the first generated
    * parameter of TinyMTDC. {@link System#nanoTime()} and
    * {@link Thread#getId()} are used for seed.
    *
    * @return TinyMT with the first parameter.
    */
  def getDefault: TinyMT32 = {
    val seed: Array[Int] = new Array[Int](4)
    val time: Long = System.nanoTime
    val threadId: Long = Thread.currentThread.getId
    seed(0) = (time >>> INT_SIZE).toInt
    seed(1) = time.toInt
    seed(2) = (threadId >>> INT_SIZE).toInt
    seed(3) = threadId.toInt
    val defaultParameter: TinyMT32Parameter = TinyMT32Parameter.getDefaultParameter
    val tiny: TinyMT32 = new TinyMT32(defaultParameter)
    tiny.setSeed(seed)
    tiny
  }

  /**
    * make and return an array of TinyMT. Each element has the same
    * characteristic polynomial with TinyMT gotten by getDefaultMT. Especially,
    * the first element is just same as default TinyMT. The second element has
    * the state of <b>jump</b> * 2<sup>64</sup> steps after the first element.
    * In other word, the first element will generate the same sequence with the
    * second element, after <b>jump</b> * 2<sup>64</sup> pseudo random number
    * generation.
    *
    * @param count
    * number of TinyMT to be created.
    * @param seed
    * seed of first element
    * @param jump
    * step is jump * 2<sup>64</sup>
    * @return array of TinyMT
    */
  def getDefaultArray (count: Int, seed: Long, jump: Long): Array[TinyMT32] = {
    val tiny: TinyMT32 = getDefault(seed)
    tiny.getJumpedArray(count, jump)
  }

  /**
    * Make and return an array of TinyMT. Each element has the same
    * characteristic polynomial with TinyMT gotten by getDefaultMT. Especially,
    * the first element is just same as default TinyMT. The second element has
    * the state of <b>jump</b> * 2<sup>64</sup> steps after the first element.
    * In other word, the first element will generate the same sequence with the
    * second element, after <b>jump</b> * 2<sup>64</sup> pseudo random number
    * generation.
    *
    * This is equals to TinyMT32.getDefault(seed).getJumpedArray(count, jump);
    *
    * @param count
    * number of TinyMT to be created.
    * @param seed
    * seed of first element
    * @param jump
    * step is jump * 2<sup>64</sup>
    * @return array of TinyMT
    */
  def getDefaultArray (count: Int, seed: String, jump: Long): Array[TinyMT32] = {
    val tiny = getDefault(seed)
    tiny.getJumpedArray(count, jump)
  }

  /**
    * Make and return an array of TinyMT. Each element of the array has
    * discrete characteristic polynomial. This function does not return
    * defaultTinyMT. User can call {@link TinyMT32#getJumpedArray(int, long)}
    * using each element and can get more TinyMTs.
    * <p>
    * Calling setSeed for each element is O.K and is a nice idea
    * to assure independency.
    * </p>
    *
    * @param count
    * number of TinyMT you want.
    * @param seed
    * seed of each element
    * @return array of TinyMT, length may be smaller than { @code count}
    * @throws IOException when can't read resource file.
    */
  @throws(classOf[IOException])
  def getTinyMTArray (count: Int, seed: Long): Array[TinyMT32] = {
    val params: Array[TinyMT32Parameter] = TinyMT32Parameter.getParameters(count)
    val tiny = new Array[TinyMT32](params.length)

    var i: Int = 0
    while (i < params.length) {
      tiny(i) = new TinyMT32(params(i))
      tiny(i).setSeed(seed)
      i += 1
    }
    tiny
  }

  /**
    * return TinyMT32 instance whose parameter has ID = 1.
    *
    * @param threadId thread ID
    * @return TinyMT32 instance
    */
  def getThreadLlocal (threadId: Long): TinyMT32 = {
    new TinyMT32(TinyMT32Parameter.getThreadLocalParameter(threadId))
  }
}

final class TinyMT32(
  /** internal state 0. */
  private var st0: Int,
  /** internal state 1. */
  private var st1: Int,
  /** internal state 2. */
  private var st2: Int,
  /** internal state 3. */
  private var st3: Int,
  /** parameters for this generator. */
  private val parameter: TinyMT32Parameter
) {

  /**
    * Constructor from a parameter.
    *
    * @param param
    * a parameter generated by TinyMTDC
    */
  private def this (param: TinyMT32Parameter) {
    this(
      st0 = 0,
      st1 = 0,
      st2 = 0,
      st3 = 0,
      parameter = param
    )
  }

  /**
    * Copy constructor.
    *
    * @param that
    * source
    */
  private def this(that: TinyMT32) {
    this(
      parameter = that.parameter,
      st0 = that.st0,
      st1 = that.st1,
      st2 = that.st2,
      st3 = that.st3
    )
  }

  /**
    * returns 32-bit integer.
    *
    * @return next int
    */
  def nextInt: Int = {
    nextState()
    output
  }

  /**
    * returns 64-bit integer.
    *
    * @return next long
    */
  def nextLong: Long = {
    var x: Long = nextInt
    x = x << TinyMT32.INT_SIZE
    x |= nextInt & TinyMT32.INT_TO_LONG_MASK
    x
  }

  /**
    * initialize internal state by seed.
    *
    * @param seed seed of randomness
    */
  def setSeed (seed: Long) {
    if ((seed >= 0) && (seed < TinyMT32.LONG_LIMIT)) {
      setSeed(seed.toInt)
    }
    else {
      val tmp: Array[Int] = new Array[Int](2)
      tmp(0) = (seed & 0xffffffff).toInt
      tmp(1) = (seed >>> TinyMT32.INT_SIZE).toInt
      setSeed(tmp)
    }
  }

  /**
    * seeding by string, This will be convenient. This function does not
    * compatible from other language implementation because coding of
    * characters are different.
    *
    * @param seed
    * seed of pseudo random numbers
    */
  def setSeed (seed: String) {
    val intSeeds = new Array[Int](seed.length)
    var i = 0
    while (i < intSeeds.length) {
      intSeeds(i) = seed.charAt(i)
      i += 1
    }
    setSeed(intSeeds)
  }

  /**
    * seeding by array of integers. This seeding is compatible with other
    * language implementation.
    *
    * @param seeds
    * seeds of pseudo random numbers.
    */
  def setSeed (seeds: Array[Int]) {
    val lag: Int = 1
    val mid: Int = 1
    val size: Int = 4
    var i: Int = 0
    var j: Int = 0
    var count: Int = 0
    var r: Int = 0
    val keyLength: Int = seeds.length
    val status = Array[Int](0, parameter.getMat1, parameter.getMat2, parameter.getTmat)
    if (keyLength + 1 > TinyMT32.MIN_LOOP) {
      count = keyLength + 1
    }
    else {
      count = TinyMT32.MIN_LOOP
    }
    r = iniFunc1(status(0) ^ status(mid % size) ^ status((size - 1) % size))
    status(mid % size) += r
    r += keyLength
    status((mid + lag) % size) += r
    status(0) = r
    count -= 1

    {
      i = 1
      j = 0
      while ((j < count) && (j < keyLength)) {
        r = iniFunc1(status(i % size) ^ status((i + mid) % size) ^ status((i + size - 1) % size))
        status((i + mid) % size) += r
        r += seeds(j) + i
        status((i + mid + lag) % size) += r
        status(i % size) = r
        i = (i + 1) % size
        j += 1
      }
    }

    while (j < count) {
      r = iniFunc1(status(i % size) ^ status((i + mid) % size) ^ status((i + size - 1) % size))
      status((i + mid) % size) += r
      r += i
      status((i + mid + lag) % size) += r
      status(i % size) = r
      i = (i + 1) % size
      j += 1
    }

    {
      j = 0
      while (j < size) {
        r = iniFunc2(status(i % size) + status((i + mid) % size) + status((i + size - 1) % size))
        status((i + mid) % size) ^= r
        r -= i
        status((i + mid + lag) % size) ^= r
        status(i % size) = r
        i = (i + 1) % size
        j += 1
      }
    }
    st0 = status(0)
    st1 = status(1)
    st2 = status(2)
    st3 = status(3)
    periodCertification()

    {
      i = 0
      while (i < TinyMT32.MIN_LOOP) {
        nextState()
        i += 1
      }
    }
  }

  /**
    * sub function of initialization.
    *
    * @param x
    * input number
    * @return scrambled integer
    */
  private def iniFunc1 (x: Int): Int = {
    (x ^ (x >>> TinyMT32.INITIALIZE_SHIFT)) * TinyMT32.MAGIC_NUMBER1
  }

  /**
    * sub function of initialization.
    *
    * @param x
    * input number
    * @return scrambled integer
    */
  private def iniFunc2 (x: Int): Int = {
    (x ^ (x >>> TinyMT32.INITIALIZE_SHIFT)) * TinyMT32.MAGIC_NUMBER2
  }

  /**
    * internal set seed function This seeding is compatible with C language
    * implementation.
    *
    * @param seed
    * seed of pseudo random numbers
    */
  def setSeed (seed: Int) {
    val counterMask: Int = 3
    val status: Array[Int] = new Array[Int](4)
    status(0) = seed
    status(1) = parameter.getMat1
    status(2) = parameter.getMat2
    status(3) = parameter.getTmat

    {
      var i: Int = 1
      while (i < TinyMT32.MIN_LOOP) {
        status(i & counterMask) ^= i + TinyMT32.MAGIC_NUMBER3 * (status((i - 1) & counterMask) ^ (status((i - 1) & counterMask) >>> TinyMT32.INITIALIZE_SHIFT2))
        i += 1
      }
    }
    st0 = status(0)
    st1 = status(1)
    st2 = status(2)
    st3 = status(3)
    periodCertification()

    {
      var i: Int = 0
      while (i < TinyMT32.MIN_LOOP) {
        nextState()
        i += 1
      }
    }
  }

  /**
    * Avoiding all zero status is sufficient for certificating the period of
    * 2<sup>127</sup> - 1 for TinyMT.
    */
  private def periodCertification(): Unit = {
    if (((st0 & TinyMT32.MASK) == 0) && (st1 == 0) && (st2 == 0) && (st3 == 0)) {
      st0 = 'T'
      st1 = 'I'
      st2 = 'N'
      st3 = 'Y'
    }
  }

  /**
    * The state transition function. This function is F<sub>2</sub>-linear.
    */
  private def nextState(): Unit = {
    var x: Int = 0
    var y: Int = 0
    y = st3
    x = (st0 & TinyMT32.MASK) ^ st1 ^ st2
    x ^= (x << TinyMT32.SH0)
    y ^= (y >>> TinyMT32.SH0) ^ x
    st0 = st1
    st1 = st2
    st2 = x ^ (y << TinyMT32.SH1)
    st3 = y
    st1 ^= parameter.getMat1(y)
    st2 ^= parameter.getMat2(y)
  }

  /**
    * The output function.
    *
    * @return pseudo random number
    */
  private def output: Int = {
    var t0: Int = 0
    var t1: Int = 0
    t0 = st3
    t1 = st0 + (st2 >>> TinyMT32.SH8)
    t0 ^= t1
    t0 ^= parameter.getTmat(t1)
    t0
  }

  /**
    * make float random.
    *
    * @return float output.
    */
  private def outputFloat: Float = {
    var t0: Int = 0
    var t1: Int = 0
    t0 = st3
    t1 = st0 + (st2 >>> TinyMT32.SH8)
    t0 ^= t1
    t0 = (t0 >>> TinyMT32.INT_TO_FLOAT_SHIFT) ^ parameter.getTmatFloat(t1)
    java.lang.Float.intBitsToFloat(t0) - 1.0f
  }

  /**
    * Addition as F<sub>2</sub> vector.
    *
    * @param that
    * vector which added to this vector
    */
  private def add (that: TinyMT32) {
    this.st0 ^= that.st0
    this.st1 ^= that.st1
    this.st2 ^= that.st2
    this.st3 ^= that.st3
  }

  /**
    * jump function.
    *
    * @param pol
    * jump polynomial
    * @return jumped new TinyMT
    */
  private def jump (pol: F2Polynomial): TinyMT32 = {
    val src = new TinyMT32(this)
    val that = getZero
    val degree = pol.degree
    var i = 0
    while (i <= degree) {
      if (pol.getCoefficient(i) == 1) {
        that.add(src)
      }
      src.nextState()
      i += 1
    }
    that
  }

  /**
    * Make and return all zero status.
    *
    * @return all zero status
    */
  private def getZero: TinyMT32 = {
    val that: TinyMT32 = new TinyMT32(this)
    that.st0 = 0
    that.st1 = 0
    that.st2 = 0
    that.st3 = 0
    that
  }

  /**
    * Make and return an array of TinyMT. Each element of the array has the
    * same characteristic polynomial with this. Especially, the first element
    * is just same as this. The second element has the state of <b>jump</b> *
    * 2<sup>64</sup> steps after the first element. In other word, the first
    * element will generate the same sequence with the second element, after
    * <b>jump</b> * 2<sup>64</sup> pseudo random number generation.
    *
    * <p>
    * Note: Do not call any setSeed methods after jump. Seeding will cancel
    * the effect of jump.
    * </p>
    *
    * @param count number of arrays
    * @param jump  jump step
    * @return jumped array of TinyMT32.
    */
  def getJumpedArray (count: Int, jump: Long): Array[TinyMT32] = {
    val tiny: Array[TinyMT32] = new Array[TinyMT32](count)
    tiny(0) = this
    val poly = tiny(0).parameter.getCharacteristic
    val pow = TinyMT32.BASIC_JUMP_STEP.multiply(BigInteger.valueOf(jump))
    val jumpPoly = F2Polynomial.X.powerMod(pow, poly)
    var i: Int = 1
    while (i < count) {
      tiny(i) = tiny(i - 1).jump(jumpPoly)
      i += 1
    }
    tiny
  }

  /**
    * returns double r, 0 <= r < 1.0.
    *
    * @return next double
    */
  def nextDouble: Double = {
    val x: Long = (nextLong >>> TinyMT32.LONG_TO_DOUBLE_SHIFT) | TinyMT32.LONG_TO_DOUBLE_MASK
    java.lang.Double.longBitsToDouble(x) - 1.0
  }

  /**
    * returns float r, 0 <= r < 1.0.
    *
    * @return next float
    */
  def nextFloat: Float = {
    nextState()
    outputFloat
  }

  /**
    * return characteristic polynomial in hexadecimal format.
    *
    * @return characteristic polynomial
    */
  def getCharacteristic: String = {
    parameter.getCharacteristic.toString(TinyMT32.HEXA_DECIMAL_BASE)
  }

  /**
    * return ID of TinyMT. ID is not unique in TinyMT.
    *
    * @return ID
    */
  def getId: Int = {
    parameter.getId
  }

  /**
    * return Delta of TinyMT.
    *
    * @return Delta
    */
  def getDelta: Int = {
    parameter.getDelta
  }

  /**
    * return Hamming weight of characteristic polynomial of TinyMT.
    *
    * @return Hamming weight
    */
  def getWeight: Int = {
    parameter.getWeight
  }
}