/* 
 * 
 * PROJECT
 *     Name
 *         File Editor Maven Support Scripts
 *     
 *     Code Version
 *         1.0
 *     
 *     Description
 *         This provides generally useful scripts to run with file-editor-maven-plugin.
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
package se.natusoft.tools.fileeditor.plugin;

import junit.framework.TestCase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

// Do note that we run the test here since we cannot do it in the plugin for some strange reason!
public class FileEditorMavenPluginTest extends TestCase {

    private static Scripts scripts = new Scripts();

    private static FileEditorMavenPlugin femp = new FileEditorMavenPlugin() {

        @Override
        public Scripts getScripts() {
            return scripts;
        }

        @Override
        public File getBasedir() {
            return new File(".");
        }
    };

    private static String testPomRoot =
    "<project xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
    "    xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd\">\n" +
    "    <modelVersion>4.0.0</modelVersion>\n" +
    "    <groupId>se.natusoft.tools.fileeditor.plugin</groupId>\n" +
    "    <artifactId>file-editor-maven-plugin</artifactId>\n" +
    "    <packaging>maven-plugin</packaging>\n" +
    "    <version>1.0</version>\n" +
    "    \n" +
    "    <name>DummyTestPom</name>\n" +
    "    \n" +
    "    <description>\n" +
    "        A dummy pom for testing.\n" +
    "    </description>\n" +
    "    \n" +
    "</project>\n";

    private static String testPomModule =
            "<project xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                    "    xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd\">\n" +
                    "    <parent>\n" +
                    "        <groupId>se.natusoft.tools.fileeditor.plugin</groupId>\n" +
                    "        <artifactId>file-editor-maven-plugin</artifactId>\n" +
                    "        <version>1.0</version>\n" +
                    "    </parent>\n" +
                    "    \n" +
                    "    <modelVersion>4.0.0</modelVersion>\n" +
                    "    \n" +
                    "    <artifactId>test-artifact</artifactId>\n" +
                    "    <packaging>maven-plugin</packaging>\n" +
                    "    \n" +
                    "    <name>DummyTestPom</name>\n" +
                    "    \n" +
                    "    <description>\n" +
                    "        A dummy pom for testing.\n" +
                    "    </description>\n" +
                    "    \n" +
                    "</project>\n";


    private void writeFile(File file, String content) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(content.getBytes());
        fos.close();
    }

    private String loadFile(File file) throws IOException {
        byte[] buffer = new byte[(int)file.length()];
        FileInputStream fis = new FileInputStream(file);
        fis.read(buffer);
        fis.close();
        return new String(buffer);
    }

    public void testChangeRootVersion() throws Exception {
        Script script = new Script();
        script.setScriptFile("script:pom_change_version.bsh");
        File tmpFile = File.createTempFile("fileeditor","test");
        writeFile(tmpFile, testPomRoot);
        script.setSourcePath(tmpFile.getAbsolutePath());
        scripts = new Scripts();
        scripts.addScript(script);

        Variables vars = new Variables();
        vars.setVariable(new Variable("pomVersion", "2.4.8"));
        script.setVariables(vars);

        try {
            femp.execute();

            String modified = loadFile(tmpFile);
            System.out.println(modified);
            assertTrue(modified.contains("<version>2.4.8</version>"));
        }
        finally {
            tmpFile.delete();
        }
    }

    public void testChangeModuleVersion() throws Exception {
        Script script = new Script();
        script.setScriptFile("script:pom_change_version.bsh");
        File tmpFile = File.createTempFile("fileeditor","test");
        writeFile(tmpFile, testPomModule);
        script.setSourcePath(tmpFile.getAbsolutePath());
        scripts = new Scripts();
        scripts.addScript(script);

        Variables vars = new Variables();
        vars.setVariable(new Variable("pomVersion", "2.4.8"));
        script.setVariables(vars);

        try {
            femp.execute();

            String modified = loadFile(tmpFile);
            System.out.println(modified);
            assertTrue(modified.contains("<version>2.4.8</version>"));
        }
        finally {
            tmpFile.delete();
        }
    }

    public void testAddParent() throws Exception {
        Script script = new Script();
        script.setScriptFile("script:pom_add_parent.bsh");
        File tmpFile = File.createTempFile("fileeditor","test");
        writeFile(tmpFile, testPomRoot);
        script.setSourcePath(tmpFile.getAbsolutePath());
        scripts = new Scripts();
        scripts.addScript(script);

        Variables vars = new Variables();
        vars.setVariable(new Variable("groupId", "se.natusoft.test"));
        vars.setVariable(new Variable("artifactId", "test-parent"));
        vars.setVariable(new Variable("version", "1.2.3"));
        script.setVariables(vars);

        try {
            femp.execute();

            String modified = loadFile(tmpFile);
            assertTrue(modified.contains("<parent>"));
            assertTrue(modified.contains("</parent>"));
            assertTrue(modified.contains("<groupId>se.natusoft.test</groupId>"));
            assertTrue(modified.contains("<artifactId>test-parent</artifactId>"));
            assertTrue(modified.contains("<version>1.2.3</version>"));
            assertTrue(modified.contains("<version>1.0</version>"));
            assertTrue(modified.indexOf("<version>1.0</version>") > modified.indexOf("<version>1.2.3</version>"));
        }
        finally {
            tmpFile.delete();
        }
    }

    public void testAddRootSnapshot() throws Exception {
        Script script = new Script();
        script.setScriptFile("script:pom_add_snapshot_to_version.bsh");
        File tmpFile = File.createTempFile("fileeditor","test");
        writeFile(tmpFile, testPomRoot);
        script.setSourcePath(tmpFile.getAbsolutePath());
        scripts = new Scripts();
        scripts.addScript(script);

        Variables vars = new Variables();
        script.setVariables(vars);

        try {
            femp.execute();

            String modified = loadFile(tmpFile);
            System.out.println(modified);
            assertTrue(modified.contains("<version>1.0-SNAPSHOT</version>"));
            assertFalse(modified.contains("<version>1.0</version>"));
        }
        finally {
            tmpFile.delete();
        }
    }

    public void testAddModuleSnapshot() throws Exception {
        Script script = new Script();
        script.setScriptFile("script:pom_add_snapshot_to_version.bsh");
        File tmpFile = File.createTempFile("fileeditor","test");
        writeFile(tmpFile, testPomModule);
        script.setSourcePath(tmpFile.getAbsolutePath());
        scripts = new Scripts();
        scripts.addScript(script);

        Variables vars = new Variables();
        script.setVariables(vars);

        try {
            femp.execute();

            String modified = loadFile(tmpFile);
            System.out.println(modified);
            assertTrue(modified.contains("<version>1.0-SNAPSHOT</version>"));
            assertFalse(modified.contains("<version>1.0</version>"));
        }
        finally {
            tmpFile.delete();
        }
    }
}
