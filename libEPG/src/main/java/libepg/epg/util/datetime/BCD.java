/*
 * Copyright (C) 2016 normal
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package libepg.epg.util.datetime;

import java.text.MessageFormat;
import org.apache.commons.lang3.Range;

/**
 * 0以上99以下の10進整数に相当するBCDコードを保持する。
 * @author normal
 */
public class BCD {
    
    private final Range<Integer> BCD_RANGE = Range.between(0, 9);

    private final int high;
    private final int low;

    public BCD(byte bcd) {
        
        high = bcd >>> 4;
        low = bcd & 0x0f;

        Object[] parameters = null;
        CHECK:
        {
            if (!BCD_RANGE.contains(high)) {
                parameters = new Object[]{Integer.toHexString(bcd),"上", Integer.toHexString(bcd)};
                break CHECK;
            }
            if (!BCD_RANGE.contains(low)) {
                parameters = new Object[]{Integer.toHexString(bcd),"下", Integer.toHexString(bcd)};
                break CHECK;
            }

        }
        if (parameters != null) {
            MessageFormat msg = new MessageFormat("このオブジェクトで取り扱える範囲外の数値です。渡された値={0} 桁={1} その桁の値={2}");
            throw new IllegalArgumentException(msg.format(parameters));
        }

    }

    public int getHigh() {
        return high;
    }

    public int getLow() {
        return low;
    }

    public int getDecimal() {
        return 10 * high + low;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + this.high;
        hash = 59 * hash + this.low;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BCD other = (BCD) obj;
        if (this.high != other.high) {
            return false;
        }
        if (this.low != other.low) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BCD{" + "high=" + Integer.toHexString(high) + ", low=" + Integer.toHexString(low) + ", decimal=" + this.getDecimal() + '}';
    }

}
