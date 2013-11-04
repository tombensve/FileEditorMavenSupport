/* 
 * 
 * PROJECT
 *     Name
 *         File Editor Maven Support Plugin
 *     
 *     Code Version
 *         1.0
 *     
 *     Description
 *         A plugin that lets you run editing test scripts on files using FileEditor.
 *         
 * COPYRIGHTS
 *     Copyright (C) 2013 by Natusoft AB All rights reserved.
 *     
 * LICENSE
 *     Apache 2.0 (Open Source)
 *     
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *     
 *       http://www.apache.org/licenses/LICENSE-2.0
 *     
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *     
 * AUTHORS
 *     Tommy Svensson (tommy@natusoft.se)
 *         Changes:
 *         2013-10-20: Created!
 *         
 */
package se.natusoft.maven.plugin;

import se.natusoft.tools.codelicmgr.annotations.*;
import se.natusoft.tools.codelicmgr.enums.Source;

/**
 * This represents one property with name and value.
 */
public class Variable {
    //
    // Private Members
    //

    /** The name of the property. */
    private String name;

    /** The value of the property. */
    private String value;

    //
    // Constructors
    //

    /**
     * Default constructor used by maven.
     */
    public Variable() {}

    /**
     * Constructor used for tests.
     *
     * @param name
     * @param value
     */
    public Variable(String name, String value) {
        this.name = name;
        this.value = value;
    }

    //
    // Methods
    //

    /** The name of the property. */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /** The value of the property. */
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
