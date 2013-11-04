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
 *         2013-01-26: Created!
 *         
 */
package se.natusoft.maven.plugin;

import bsh.EvalError;
import bsh.Interpreter;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import se.natusoft.tools.fileeditor.TextFileEditor;

import java.io.*;
import java.util.List;
import se.natusoft.tools.codelicmgr.annotations.*;
import se.natusoft.tools.codelicmgr.enums.Source;

/**
 * This goal takes the specified vars and append the specified values to them.
 *
 * @goal edit
 * 
 * @phase generate-sources
 */
public class FileEditorMavenPlugin extends AbstractMojo {


    /**
     * A reference to the Maven project being executed
     *
     * @parameter default-value="${project}"
     * @required
     * @readonly
     */
    private MavenProject project;

    /**
     * @parameter
     * @requred
     */
    private Scripts scripts;

    /**
     * @parameter
     * @optional
     */
    private Variables vars;

    // The following 3 methods are just for making testing easier. The code uses these getters rather than
    // referencing the private members directly.

    /**
     * @return The projects basedir.
     */
    protected File getBasedir() {
        return this.project.getBasedir();
    }

    /**
     * @return The editing test.scripts to execute.
     */
    protected Scripts getScripts() {
        return this.scripts;
    }

    /**
     * @return The variables to define.
     */
    protected Variables getVars() {
        return this.vars;
    }

    /**
     * The plugin entry and exit point.
     *
     * @throws MojoExecutionException
     */
    public void execute() throws MojoExecutionException {
        try {
            for (Script script : getScripts().getScripts()) {
                SourcePath sourcePath = null;
                if (script.getSourcePath().startsWith(File.separator)) {
                    sourcePath = new SourcePath(new File(script.getSourcePath()));
                }
                else {
                    sourcePath = new SourcePath(getBasedir(), script.getSourcePath());
                }
                for (File file : sourcePath.getSourceFiles()) {
                    Interpreter bsh = new Interpreter();
                    bsh.eval("import se.natusoft.tools.fileeditor.*;");
                    if (getVars() != null) {
                        for (String name : getVars().getProps().stringPropertyNames()) {
                            String value = getVars().getProps().getProperty(name);
                            bsh.eval("String " + name + "=\"" + value + "\";");
                        }
                    }
                    TextFileEditor editor = new TextFileEditor();
                    editor.load(file);
                    editor.setAllowLoadSave(false);
                    bsh.set("editor", editor);

                    if (script.getCode() != null && !script.getCode().isEmpty()) {
                        getLog().info("Executing inline editing code on " + file + " ...");
                        bsh.eval(script.getCode());
                    }
                    else {
                        getLog().info("Executing script '" + script.getScriptFile() + "' on file " + file + " ...");
                        Reader scriptReader = null;
                        if (script.getScriptFile().startsWith("resource:")) {
                            InputStream resourceInputStream = ClassLoader.getSystemClassLoader().
                                    getResourceAsStream(script.getScriptFile().substring(9));
                            if (resourceInputStream != null) {
                                scriptReader = new InputStreamReader(resourceInputStream);
                            }
                            else {
                                throw new MojoExecutionException("Non existing script file: '" + script.getScriptFile() + "'!");
                            }
                        }
                        else {
                            scriptReader = new FileReader(script.getScriptFile());
                        }

                        bsh.eval(scriptReader);
                        scriptReader.close();
                    }

                    editor.setAllowLoadSave(true);
                    editor.save();

                    getLog().info("Editing done!");
                }
            }
        }
        catch (EvalError ee) {
            throw new MojoExecutionException("Bsh error!", ee);
        }
        catch (IOException ioe) {
            throw new MojoExecutionException("IO Failure when executing plugin!", ioe);
        }
    }
}
