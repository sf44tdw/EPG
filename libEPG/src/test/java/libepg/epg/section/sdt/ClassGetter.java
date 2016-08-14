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
package libepg.epg.section.sdt;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.apache.commons.logging.Log;
import libepg.epg.section.body.servicedescriptiontable.ServiceDescriptionTableRepeatingPart;
import libepg.util.LoggerFactory;

/**
 *
 * @author normal
 */
public class ClassGetter {

    private static final Log LOG;

    static {
        final Class<?> myClass = MethodHandles.lookup().lookupClass();
        LOG = new LoggerFactory(myClass, ServiceDescriptionTableRepeatingPart.CLASS_LOG_OUTPUT_MODE).getLOG();
    }

    private ClassGetter() {
    }

//強制呼び出し    
    public static ServiceDescriptionTableRepeatingPart init(byte[] data) throws Throwable {
        try {
            Object[] args = {data};
            Class<?>[] params = {byte[].class};
            Constructor<ServiceDescriptionTableRepeatingPart> constructor = ServiceDescriptionTableRepeatingPart.class.getDeclaredConstructor(params);
            constructor.setAccessible(true);
            ServiceDescriptionTableRepeatingPart target = constructor.newInstance(args);
            return target;
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | SecurityException ex) {
            LOG.fatal(ex);
        } catch (InvocationTargetException ex) {
            throw ex.getCause();
        }
        return null;
    }
}
