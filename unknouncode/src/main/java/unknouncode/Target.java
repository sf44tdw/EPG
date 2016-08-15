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
package unknouncode;

/**
 *
 * @author normal
 */
public class Target {

    private Target() {
    }

    public static int getTSID2TP(int tsid) {
        return (tsid & 0x01f0) >> 4;
    }

    public static int getTSID2SLOT(int tsid) {
        /* for NHK BS */
        if (tsid == 16625 || tsid == 16626) {
            tsid = tsid - 1;
        }
        return tsid & 0x0007;
    }

    public static String getTP(int tsid) {
        final StringBuilder sb = new StringBuilder();
        switch ((tsid & 0xf000) >> 12) {
            case 4:
                sb.append("BS_").append(getTSID2TP(tsid)).append("_").append(getTSID2SLOT(tsid));
                break;
            case 6:
            case 7:
                sb.append("CS_").append(getTSID2TP(tsid));
                break;
        }
        return sb.toString();
    }
}
