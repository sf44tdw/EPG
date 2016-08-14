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
package libepg.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.impl.NoOpLog;

/**
 * クラス名セット済みロガーの生成。及びロガーごとの出力抑止設定を行う。<br>
 * outLog=falseに設定した状態で取得したロガーは何も出力しない。<br>
 *
 * @author normal
 */
public final class LoggerFactory {

    private final Class<?> myClass;
    private final boolean outLog;
    private final Log THIS_LOG = LogFactory.getLog(LoggerFactory.class);

    public LoggerFactory(Class<?> myClass, boolean outLog) {
        this.myClass = myClass;
        this.outLog = outLog;
    }

    public Log getLOG() {
        final Log LOG;
        if (this.outLog) {
            LOG = LogFactory.getLog(myClass);
        } else {
            LOG = new NoOpLog();
            THIS_LOG.info(myClass + "は、ログ出力抑止中です。");
        }
        return LOG;
    }

    public boolean isOutLog() {
        return outLog;
    }

}
