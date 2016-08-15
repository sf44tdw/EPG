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
public class Main {

    private static void dump(int tsid) {
        System.out.println("getTSID2TP =  " + Target.getTSID2TP(tsid));
        System.out.println("getTSID2SLOT = " + Target.getTSID2SLOT(tsid));
        System.out.println(Target.getTP(tsid));
    }

    public static void main(String[] args) {
        //トランスポートストリーム識別 = 16,400 チャンネルID = BS_151 放送局名 = ＢＳ朝日１ 
        dump(16400);

        //トランスポートストリーム識別 = 18,289 チャンネルID = BS_255 放送局名 = ＢＳ日本映画専門ｃｈ
        dump(18289);

        //トランスポートストリーム識別 = 16,432 チャンネルID = BS_191 放送局名 = ＷＯＷＯＷプライム
        dump(16432);

        //トランスポートストリーム識別 = 18,033 チャンネルID = BS_236 放送局名 = ＢＳアニマックス
        dump(18033);

        //トランスポートストリーム識別 = 16,433 チャンネルID = BS_171 放送局名 = ＢＳジャパン
        dump(16433);
    }

}
