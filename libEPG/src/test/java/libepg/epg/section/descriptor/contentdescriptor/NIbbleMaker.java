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
package libepg.epg.section.descriptor.contentdescriptor;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.apache.commons.logging.Log;
import libepg.epg.section.descriptor.contentdescriptor.Nibble;
import libepg.util.LoggerFactory;

/**
 *
 * @author normal
 */
class NIbbleMaker {

     private static final Log LOG;

    static {
        final Class<?> myClass = MethodHandles.lookup().lookupClass();
        LOG = new LoggerFactory(myClass, NibbleTest.CLASS_LOG_OUTPUT_MODE).getLOG();
    }

    public static final Nibble init(byte[] data) throws Throwable {
        try {
            Object[] args = {data};
            Class<?>[] params = {byte[].class};
            Constructor<Nibble> constructor = Nibble.class.getDeclaredConstructor(params);
            constructor.setAccessible(true);
            Nibble target = constructor.newInstance(args);
            return target;
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException ex) {
            LOG.fatal(ex);
        } catch (InvocationTargetException ex) {
            throw ex.getCause();
        }
        return null;
    }

}
