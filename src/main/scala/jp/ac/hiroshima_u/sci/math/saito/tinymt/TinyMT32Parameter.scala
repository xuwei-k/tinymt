package jp.ac.hiroshima_u.sci.math.saito.tinymt

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL

/**
  * This class is used to keep parameters for TinyMT32, and to get parameters
  * from resource file.
  *
  * @author M. Saito
  *
  */
object TinyMT32Parameter {
  /** hexadecimal format. */
  private val HEX_FORMAT: Int = 16
  /** int to float mask. */
  private final val INT_TO_FLOAT_MASK = 0x3f800000
  /** int to float shift. */
  private final val INT_TO_FLOAT_SHIFT = 9
  /** decimal format. */
  private final val DEC_FORMAT = 10

  /**
    * default parameter.
    */
  private[this] val DEFAULT_PARAMETER = new TinyMT32Parameter("d8524022ed8dff4a8dcc50c798faba43", 0, 0x8f7011ee, 0xfc78ff1f, 0x3793fdff, 63, 0)
  /**
    * parameters for ThreadLocalRandom.
    */
  private val THREAD_LOCAL_PARAMETER: Array[TinyMT32Parameter] = Array(new TinyMT32Parameter("80227acb382d7b47f3714bd1223bedaf", 1, 0xda251b45, 0xfed0ffb5, 0x9b5cf7ff, 67, 0), new TinyMT32Parameter("db46f27d546507bdf3445acd188fa8a3", 1, 0xa55a14aa, 0xfd28ff4b, 0xc2b9efff, 67, 0), new TinyMT32Parameter("e1c47f40863c844be54fc078750562ef", 1, 0xa45b148a, 0xfd20ff49, 0x79aff7ff, 61, 0), new TinyMT32Parameter("d1346cadec1fbc329d1fe2283a577b77", 1, 0x837c106e, 0xfc18ff07, 0x5fffa9bf, 71, 0), new TinyMT32Parameter("c7487f9b2e8f8aaa231ac4b22b14db9b", 1, 0x817e102e, 0xfc08ff03, 0x69f3f77f, 65, 0), new TinyMT32Parameter("b0d7d986ce26326dbb6b0fccda28bdbb", 1, 0x68970d13, 0xfb40fed1, 0xd868edff, 71, 0), new TinyMT32Parameter("c0edefb49baf0424fd235ce48e8d26fb", 1, 0x45ba08b6, 0xfa28fe8b, 0x9c503dff, 69, 0), new TinyMT32Parameter("b24aa41c9eba05c4ffa8fc0e90438c37", 1, 0x2cd3059b, 0xf960fe59, 0xe67d73ff, 61, 0), new TinyMT32Parameter("a6aca2283f3e12ed69818bd95c35d00b", 1, 0x19e6033d, 0xf8c8fe33, 0x207e65ff, 61, 0), new TinyMT32Parameter("a86f5f29166785b075c431b027044287", 1, 0xfa041f41, 0xf7d8fdf7, 0x9c3efdff, 57, 0), new TinyMT32Parameter("e570ac318b37d9caa71ba4b99bad6a3b", 1, 0xf40a1e80, 0xf7a8fdeb, 0xe1cffbfd, 69, 0), new TinyMT32Parameter("d227f7cbfb9408765dd7da7d51790a13", 1, 0xf10f1e20, 0xf780fde1, 0xcac37fff, 71, 0), new TinyMT32Parameter("833689351c2ed91b5f56d10bde4b5197", 1, 0xdb251b65, 0xf6d0fdb5, 0x17bd7bff, 65, 0), new TinyMT32Parameter("dd68340e3a7a7b8d993c6f412b125ca7", 1, 0xc23c1846, 0xf618fd87, 0x899e7dff, 65, 0), new TinyMT32Parameter("9898245c4fccabd1617bb16fff089643", 1, 0xae5015cb, 0xf578fd5f, 0x93fc9ffd, 65, 1), new TinyMT32Parameter("e04a99bcc12b07a661440bef54402207", 1, 0x926c124c, 0xf498fd27, 0x01bbff5f, 53, 0))

  /**
    * returns default parameter of TinyMT32.
    *
    * @return default parameter
    */
  def getDefaultParameter: TinyMT32Parameter = {
    DEFAULT_PARAMETER
  }

  /**
    * returns default parameter for ThreadLocalRandom. This parameter is
    * calculated by TinyMT32DC with ID=1.
    *
    * @param threadId thread ID
    * @return TinyMT32Parameter of ID=1
    */
  def getThreadLocalParameter (threadId: Long): TinyMT32Parameter = {
    val length = THREAD_LOCAL_PARAMETER.length
    var no: Int = (threadId % length).toInt
    if (no < 0) {
      no = -no
    }
    THREAD_LOCAL_PARAMETER(no)
  }

  /**
    * returns specified number of parameters.
    *
    * @param count
    * number of parameters to be returned
    * @return specified number of parameters
    * @throws IOException
    * when fails to read resource file
    */
  @throws(classOf[IOException])
  def getParameters (count: Int): Array[TinyMT32Parameter] = {
    getParameters(0, count)
  }

  /**
    * returns if line is comment or default parameter.
    *
    * @param line
    * line from resource file
    * @return to be skipped or not.
    */
  private def isSkip (line: String): Boolean = {
    line.startsWith("#") || line.startsWith("d8524022ed8dff4a8dcc50c798faba43")
  }

  /**
    * returns specified number of parameters from start line.
    *
    * @param start
    * line no. to start to read. 0 is first parameter except default
    * parameter.
    * @param count
    * number of parameters to be returned
    * @return specified number of parameters
    * @throws IOException
    * when fails to read resource file
    */
  @throws(classOf[IOException])
  def getParameters (start: Int, count: Int): Array[TinyMT32Parameter] = {
    val url = classOf[TinyMT32].getClassLoader.getResource("tinymt32dc.0.65536.txt")
    val params = new java.util.ArrayList[TinyMT32Parameter]
    val br = new BufferedReader(new InputStreamReader(url.openStream))
    var index: Int = 0
    var preIdx: Int = 0
    try {
      var continue = true
      while (start > preIdx && continue) {
        val line = br.readLine
        if (line == null) {
          continue = false
        } else if (isSkip(line)) {

        } else {
          preIdx += 1
        }
      }
      continue = true
      while (index < count && continue) {
        val line = br.readLine
        if (line == null) {
          continue = false
        } else if (isSkip(line)) {
        } else {
          val p = parseString(line)
          params.add(p)
          index += 1
        }
      }
    }
    catch {
      case e: IOException =>
        if (index > 0) {
          params.toArray(new Array[TinyMT32Parameter](params.size))
        }
        else {
          throw e
        }
    } finally {
      br.close()
    }
    params.toArray(new Array[TinyMT32Parameter](params.size))
  }

  /**
    * make TinyMT32Prameter instance from line.
    *
    * @param line
    * line of resource file
    * @return TinyMT32Parameter instance made from line
    * @throws IOException
    * when line does not contain parameters.
    */
  @throws(classOf[IOException])
  private def parseString (line: String): TinyMT32Parameter = {
    val str = line.split(",")
    if (str.length < 8) {
      throw new IOException("line does not contain parameters.")
    }
    val characteristic = str(0)
    val id: Int = str(2).toInt
    val mat1 = java.lang.Long.parseLong(str(3), HEX_FORMAT).toInt
    val mat2 = java.lang.Long.parseLong(str(4), HEX_FORMAT).toInt
    val tmat = java.lang.Long.parseLong(str(5), HEX_FORMAT).toInt
    val weight = Integer.parseInt(str(6), DEC_FORMAT)
    val delta = Integer.parseInt(str(7), DEC_FORMAT)
    new TinyMT32Parameter(characteristic, id, mat1, mat2, tmat, weight, delta)
  }
}

final class TinyMT32Parameter (
  /** characteristic polynomial. */
  characteristic: F2Polynomial,
  /** ID of TinyMT32. */
  id: Int,
  /** parameter mat1 of TinyMT32. */
  mat1: Int,
  /** parameter mat2 of TinyMT32. */
  mat2: Int,
  /** parameter tmat of TinyMT32. */
  tmat: Int,
  /** Hamming weight of characteristic polynomial. */
  weight: Int,
  /** Delta of TinyMT. */
  delta: Int
) {

  /**
    * private constructor.
    *
    * @param pcharacteristic
    * hexadecimal format of characteristic polynomial
    * @param pid
    * parameter ID
    * @param pmat1
    * parameter mat1
    * @param pmat2
    * parameter mat2
    * @param ptmat
    * parameter tmat
    * @param pweight
    * parameter weight
    * @param pdelta
    * parameter delta
    */
  private def this (pcharacteristic: String, pid: Int, pmat1: Int, pmat2: Int, ptmat: Int, pweight: Int, pdelta: Int) {
    this(
      characteristic = new F2Polynomial(pcharacteristic, TinyMT32Parameter.HEX_FORMAT),
      id = pid,
      mat1 = pmat1,
      mat2 = pmat2,
      tmat = ptmat,
      weight = pweight,
      delta = pdelta
    )
  }

  /**
    * return characteristic polynomial.
    *
    * @return characteristic polynomial
    */
  private[tinymt] def getCharacteristic: F2Polynomial = {
    characteristic
  }

  /**
    * return ID.
    *
    * @return ID
    */
  private[tinymt] def getId: Int = {
    id
  }

  /**
    * return mat1.
    *
    * @return mat1
    */
  private[tinymt] def getMat1: Int = {
    mat1
  }

  /**
    * return mat1 when x is odd number.
    *
    * @param x
    * number
    * @return mat1 when x is odd else 0
    */
  private[tinymt] def getMat1 (x: Int): Int = {
    if ((x & 1) == 0) {
      0
    }
    else {
      mat1
    }
  }

  /**
    * return mat2.
    *
    * @return mat2
    */
  private[tinymt] def getMat2: Int = {
    mat2
  }

  /**
    * return mat2 when x is odd number.
    *
    * @param x
    * integer
    * @return mat1 if x is odd else 0
    */
  private[tinymt] def getMat2 (x: Int): Int = {
    if ((x & 1) == 0) {
      0
    }
    else {
      mat2
    }
  }

  /**
    * return tmat parameter.
    *
    * @return tmat
    */
  private[tinymt] def getTmat: Int = {
    tmat
  }

  /**
    * return tmat if x is odd number.
    *
    * @param x
    * integer
    * @return return tmat if x is odd else 0
    */
  private[tinymt] def getTmat (x: Int): Int = {
    if ((x & 1) == 0) {
      0
    }
    else {
      tmat
    }
  }

  /**
    * return bit pattern depends on x is odd or not.
    *
    * @param x
    * integer
    * @return bit pattern depends on x is odd or not.
    */
  private[tinymt] def getTmatFloat (x: Int): Int = {
    if ((x & 1) == 0) {
      TinyMT32Parameter.INT_TO_FLOAT_MASK
    }
    else {
      TinyMT32Parameter.INT_TO_FLOAT_MASK | (tmat >>> TinyMT32Parameter.INT_TO_FLOAT_SHIFT)
    }
  }

  /**
    * return Hamming weight of characteristic polynomial.
    *
    * @return Hamming weight of characteristic polynomial
    */
  private[tinymt] def getWeight: Int = {
    weight
  }

  /**
    * return delta.
    *
    * @return delta
    */
  private[tinymt] def getDelta: Int = {
    delta
  }
}