package cx.it.hyperbadger.spacezombies;

/*
 * Copyright (c) 2002-2008 LWJGL Project
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'LWJGL' nor the names of
 *   its contributors may be used to endorse or promote products derived
 *   from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

/**
 *
 * Holds a 2-tuple vector.
 *
 * @author cix_foo <cix_foo@users.sourceforge.net>
 * @version $Revision$
 * $Id$
 */

public class Vector2BD implements Serializable {
	public static int scale = 16;
	public static MathContext mathContext = new MathContext(scale);
	private static final long serialVersionUID = 1L;
	public BigDecimal x=null,y=null;

	/**
	 * Constructor for Vector3f.
	 */
	public Vector2BD() {
		super();
	}

	/**
	 * Constructor
	 */
	public Vector2BD(Vector2BD src) {
		set(src);
	}

	/**
	 * Constructor
	 */
	public Vector2BD(BigDecimal x, BigDecimal y) {
		set(x, y);
	}
	/**
	 * Constructor
	 */
	public Vector2BD(String x, String y) {
		set(new BigDecimal(x), new BigDecimal(y));
	}

	/* (non-Javadoc)
	 * @see org.lwjgl.util.vector.WritableVector2d#set(double, double)
	 */
	public void set(BigDecimal x, BigDecimal y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Load from another Vector2d
	 * @param src The source vector
	 * @return this
	 */
	public Vector2BD set(Vector2BD src) {
		x = src.getX();
		y = src.getY();
		return this;
	}

	/**
	 * @return the length squared of the vector
	 */
	public BigDecimal lengthSquared() {
		return x.pow(2).add(y.pow(2));
	}

	/**
	 * Translate a vector
	 * @param x The translation in x
	 * @param y the translation in y
	 * @return this
	 */
	public Vector2BD translate(BigDecimal x, BigDecimal y) {
		this.x = this.x.add(x);
		this.x = this.y.add(y);
		return this;
	}

	/**
	 * Negate a vector
	 * @return this
	 */
	public Vector2BD negate() {
		this.x = this.x.negate();
		this.y = this.y.negate();
		return this;
	}

	/**
	 * Negate a vector and place the result in a destination vector.
	 * @param dest The destination vector or null if a new vector is to be created
	 * @return the negated vector
	 */
	public Vector2BD negate(Vector2BD dest) {
		if (dest == null)
			dest = new Vector2BD();
		dest.x = this.x.negate();
		dest.y = this.y.negate();
		return dest;
	}
	
	/**
	 * @return the length of the vector
	 */
	public final BigDecimal length() {
		return Vector2BD.sqrt(lengthSquared(),scale);
	}

	/**
	 * Normalise this vector and place the result in another vector.
	 * @param dest The destination vector, or null if a new vector is to be created
	 * @return the normalised vector
	 */
	public Vector2BD normalise(Vector2BD dest) {
		BigDecimal l = length();

		if (dest == null)
			dest = new Vector2BD(x.divide(l), y.divide(l));
		else
			dest.set(x.divide(l), y.divide(l));

		return dest;
	}

	/**
	 * The dot product of two vectors is calculated as
	 * v1.x * v2.x + v1.y * v2.y + v1.z * v2.z
	 * @param left The LHS vector
	 * @param right The RHS vector
	 * @return left dot right
	 */
	public static BigDecimal dot(Vector2BD left, Vector2BD right) {
		return left.x.multiply(right.x,mathContext).add(left.y.multiply(right.y,mathContext));
	}



	/**
	 * Calculate the angle between two vectors, in radians
	 * @param a A vector
	 * @param b The other vector
	 * @return the angle between the two vectors, in radians
	 */
	public static BigDecimal angle(Vector2BD a, Vector2BD b) {
		BigDecimal lM = a.length().multiply(b.length(),mathContext);
		BigDecimal dls = dot(a, b).divide(lM,mathContext);
		BigDecimal one = new BigDecimal("1");
		if(dls.compareTo(one.negate())<0){
			dls = one.negate();
		}else if (dls.compareTo(one)>0){
			dls = one;
		}
		return new BigDecimal(Math.acos(dls.doubleValue()));
	}
	/**
	 * Get the angle this vector is at
	 * 
	 * @return The angle this vector is at (in degrees) as double
	 */
	public double getDegrees() {
		double theta = StrictMath.toDegrees(StrictMath.atan2(y.doubleValue(), x.doubleValue()));
		if ((theta < -360) || (theta > 360)) {
			theta = theta % 360;
		}
		if (theta < 0) {
			theta = 360 + theta;
		}

		return theta;
	} 
	/**
	 * @return the unit vector
	 */
	public Vector2BD unitVector(){
		BigDecimal one = new BigDecimal("1");
		BigDecimal a = null;
		if(this.length().compareTo(new BigDecimal("0"))==0){
			a = new BigDecimal("1");
		}else{
			a = one.divide(this.length(),mathContext);
		}
		Vector2BD b = new Vector2BD(this.getX(),this.getY());
		b.scale(a);
		return b;
	}
	/**
	 * Add a vector to another vector and place the result in a destination
	 * vector.
	 * @param left The LHS vector
	 * @param right The RHS vector
	 * @param dest The destination vector, or null if a new vector is to be created
	 * @return the sum of left and right in dest
	 */
	public static Vector2BD add(Vector2BD left, Vector2BD right, Vector2BD dest) {
		if (dest == null)
			return new Vector2BD(left.x.add(right.x), left.y.add(right.y));
		else {
			dest.set(left.x.add(right.x), left.y.add(right.y));
			return dest;
		}
	}

	/**
	 * Subtract a vector from another vector and place the result in a destination
	 * vector.
	 * @param left The LHS vector
	 * @param right The RHS vector
	 * @param dest The destination vector, or null if a new vector is to be created
	 * @return left minus right in dest
	 */
	public static Vector2BD sub(Vector2BD left, Vector2BD right, Vector2BD dest) {
		if (dest == null)
			return new Vector2BD(left.x.subtract(right.x), left.y.subtract(right.y));
		else {
			dest.set(left.x.subtract(right.x), left.y.subtract(right.y));
			return dest;
		}
	}

	/* (non-Javadoc)
	 * @see org.lwjgl.vector.Vector#scale(double)
	 */
	public Vector2BD scale(BigDecimal scale) {

		x = x.multiply(scale,mathContext);
		y = y.multiply(scale,mathContext);

		return this;
	}
	
	
	/**
	   * Get the distance from this point to another
	   * 
	   * @param other The other point we're measuring to
	   * @return The distance to the other point
	   */
	  public BigDecimal distance(Vector2BD other) {
	    BigDecimal dx = other.getX().subtract(getX());
	    BigDecimal dy = other.getY().subtract(getY());
	    
	    return Vector2BD.sqrt((dx.pow(2)).add(dy.pow(2)),Vector2BD.scale);
	  }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(64);

		sb.append("Vector2BD[");
		sb.append(x);
		sb.append(", ");
		sb.append(y);
		sb.append(']');
		return sb.toString();
	}

	/**
	 * @return x
	 */
	public final BigDecimal getX() {
		return x;
	}

	/**
	 * @return y
	 */
	public final BigDecimal getY() {
		return y;
	}

	/**
	 * Set X
	 * @param x
	 */
	public final void setX(BigDecimal x) {
		this.x = x;
	}

	/**
	 * Set Y
	 * @param y
	 */
	public final void setY(BigDecimal y) {
		this.y = y;
	}
	public static BigDecimal sqrt(BigDecimal x, int scale)
    {
        // Check that x >= 0.
        if (x.signum() < 0) {
            throw new IllegalArgumentException("x < 0");
        }
 
        // n = x*(10^(2*scale))
        BigInteger n = x.movePointRight(scale << 1).toBigInteger();
 
        // The first approximation is the upper half of n.
        int bits = (n.bitLength() + 1) >> 1;
        BigInteger ix = n.shiftRight(bits);
        BigInteger ixPrev;
 
        // Loop until the approximations converge
        // (two successive approximations are equal after rounding).
        do {
            ixPrev = ix;
 
            // x = (x + n/x)/2
            if(ix.compareTo(new BigInteger("0"))==0){
            	break;
            }
            ix = ix.add(n.divide(ix)).shiftRight(1);
 
            Thread.yield();
        } while (ix.compareTo(ixPrev) != 0);
 
        return new BigDecimal(ix, scale);
    }

}