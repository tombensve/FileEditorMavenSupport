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

import java.util.Properties;

/**
 * This represents a set of properties.
 */
public class Variables {
    //
    // Private Members
    //

    private Properties properties = new Properties();

    //
    // Methods
    //

    public void setProperty(Variable variable) {
        this.properties.setProperty(variable.getName(), variable.getValue());
    }

    public Properties getProps() {
        return this.properties;
    }
}
