/*
 * This file is part of the L2J Mobius project.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.l2j.gameserver.scripting.java;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author HorridoJoho
 */
public final class ScriptingClassLoader extends ClassLoader {
    public static final Logger LOGGER = LoggerFactory.getLogger(ScriptingClassLoader.class.getName());

    private Iterable<ScriptingFileInfo> _compiledClasses;

    ScriptingClassLoader(ClassLoader parent, Iterable<ScriptingFileInfo> compiledClasses) {
        super(parent);
        _compiledClasses = compiledClasses;
    }

    void removeCompiledClasses() {
        _compiledClasses = null;
    }

}
