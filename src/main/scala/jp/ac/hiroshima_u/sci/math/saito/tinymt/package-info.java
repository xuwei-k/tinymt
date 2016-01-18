/**
 * <p>Tiny Mersenne Twister in Java.</p>
 * 
 * <p>
 * This package provides Tiny Mersenne Twister (TinyMT) random number generator.
 * Here are two kinds of TinyMT,
 * {@link jp.ac.hiroshima_u.sci.math.saito.tinymt.TinyMT32}
 * and {@link jp.ac.hiroshima_u.sci.math.saito.tinymt.TinyMT64}.
 * TinyMT32 is based on 32-bit calculation, and TinyMT64 is based on 64-bit one.
 * TinyMTs are parametric generators, which generate distinct sequence for each
 *  parameter. 
 * TinyMTs are defined as subclass of
 *  org.apache.commons.math.random.AbstractRandomGenerator.
 * </p>
 * <p>
 * Caution: TinyMTs are <strong>not synchronized</strong>,
 * not safe when used across threads.
 * Instead, using one TinyMT object for one thread is recommended.
 * Actually, {@link jp.ac.hiroshima_u.sci.math.saito.tinymt.ThreadLocalRandom}
 * class is provided for this purpose.
 * </p>
 */
package jp.ac.hiroshima_u.sci.math.saito.tinymt;