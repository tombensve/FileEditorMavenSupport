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
 *         2013-10-19: Created!
 *         
 */
package se.natusoft.tools.fileeditor.plugin;

/**
 * This allows providing small test.scripts directly in the configuration and when script
 * should be run or not is determined by file extension.
 */
public class Script {

    //
    // code
    //

    private String code;

    /**
     * Sets the code to run.
     *
     * @param code The code to run.
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Returns the code to run.
     */
    public String getCode() {
        return this.code;
    }

    //
    // sourcePath
    //

    private String sourcePath;

    /**
     * Sets the source path specifying the file(s) to edit.
     *
     * @param sourcePath The source path to set.
     */
    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    /**
     * Returns the source path specifying the file(s) to edit.
     */
    public String getSourcePath() {
        return this.sourcePath;
    }

    //
    // scriptFile
    //

    private java.lang.String scriptFile;

    /**
     * Sets the path to a script file to execute.
     *
     * @param scriptFile The script file path to set.
     */
    public void setScriptFile(String scriptFile) {
        this.scriptFile = scriptFile;
    }

    /**
     * Returns the path to the script file to execute.
     */
    public String getScriptFile() {
        return this.scriptFile;
    }

    //
    // Variables
    //

    private Variables variables;

    /**
     * Sets the variables to provide to the script.
     *
     * @param variables The variables to provide.
     */
    public void setVariables(Variables variables) {
        this.variables = variables;
    }

    /**
     * Returns the variables to return to the script.
     */
    public Variables getVariables() {
        return this.variables;
    }

    //
    // artifactId
    //

    private String forArtifactId = "";

    /**
     * Sets the artifact id this script applies for. If not set it will apply for all.
     *
     * @param forArtifactId
     */
    public void setForArtifactId(String forArtifactId) {
        this.forArtifactId = forArtifactId;
    }

    /**
     * Returns the artifact id this script applies for.
     */
    public String getForArtifactId() {
        return this.forArtifactId;
    }

}
