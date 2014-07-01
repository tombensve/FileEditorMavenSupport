# FileEditorMavenPlugin User Guide

This is a trivially simple plugin that lets you run editing scripts on files. The _se.natusoft.tools.fileeditor:FileEditor:n.n_ tool is used for the editing. This is a programmable only text editor. An instance of this called [editor](http://apidoc.natusoft.se/FileEditor/) is always provided to each script.

## Changes

### Version 1.1

Bumped versions of dependencies to the latest versions which are also available for download on bintray. I should clean my local maven repo more often to find if I'm using non downloadable dependencies :-). 

Modified the *pom\_add\_snapshot\_to\_version.bsh* and the *pom-change\_version.bsh* scripts to assume that each sub module have the same version as the parent and that for submodules only the parent version is specified. This is the most common case as I see it. Thereby the _modifyParent_ parameter is no longer valid (just ignored). 

## Plugin example

    <plugin>
      <groupId>se.natusoft.maven.plugin</groupId>
      <artifactId>file-editor-maven-plugin</artifactId>
      <version>1.1</version>
 
      <executions>
        <execution>
          <id>some-good-id</id>
          <goals>
            <goal>edit</goal>
          </goals>
          <phase>generate-sources</phase>
          <configuration>
            <scripts>
              <script>
                <code>
                  Bean Shell java code ...
                </code>
                <scriptFile>The path to the bsh script to execute.</scriptFile>
                <sourcePath>Path to file to edit</sourcePath>
                <variables>
                  <variable>
                    <name>name</name>
                    <value>value</value>
                  </variable>
                </variables>
              </script>
            </scripts>
          </configuration>
        </execution>
      </executions>
    </plugin>

The _&lt;code&gt;_ and _&lt;scriptFile&gt;_ tags are mutually exclusive. The first provides the code directly in the pom. The second points to a script file that gets executed. 

Do note that the _&lt;scriptFile&gt;_ tag can begin with _script:_ which really means that it will be looked for within the classpath in package se.natusoft.tools.fileeditor.scripts. Otherwise a _java.io.File_ object will be used to reference the file.


The variables are passed to the script and are only strings. That is, in the Bean Shell script there will be a java.lang.String variable with name _name_ and value _value_.

## Script examples

The following 3 scripts are available in the plugin package. 

Anyone that uses this plugin and makes a generally useful editing script, please consider mailing it to me and I will include it in the next version. 

### script:pom_add_parent.bsh

    /*
     * This script adds a parent to the pom.
     *
     * It requires the following variables:
     *   groupId
     *   artifactId
     *   version
     */
    editor.moveToTopOfFile();
    if (editor.find("<project")) {
    
      if (!editor.findFromCurrent("<parent")) {
        if (editor.find("<modelVersion")) {
          editor.insertLine("    <parent>");
          editor.insertLine("        <groupId>" + groupId + "</groupId>");
          editor.insertLine("        <artifactId>" + artifactId + "</artifactId>");
          editor.insertLine("        <version>" + version + "</version>");
          editor.insertLine("    </parent>");
        }
      }
    }

### script:pom_add_snapshot_to_version.bsh

    /*
     * This script adds "-SNAPSHOT" to version.
     *
     * This script makes the assumption that either the pom is
     * the top parent and does not have a parent itself in which
     * case the main model version is updated, otherwise the parent
     * version is updated. This also assumes that all modules have
     * the same version as the root pom, and just inherits version
     * from parent.
     */
    editor.moveToTopOfFile();
    if (editor.find("<project")) {
    
        if (editor.findFromCurrent("<version>")) {
            String version = editor.getCurrentLineBetween("<version>", "</version>");
            editor.replaceCurrentLineBetween("<version>", "</version>", version + "-SNAPSHOT");
        }
    
    }

### script:pom-change_version.bsh

This script is generally useful when having multi module projects and you keep the same version for modules. This allows you to put the version in only the top pom. Do not use &lt;forActifactId&gt;...&lt;/forArtifactId&gt; when using this, otherwise only one pom will be edited. Also note that if you use this, put a &lt;version&gt;...&lt;/version&gt; tag in each pom instead of just inheriting version from parent so that no later &lt;version&gt;...&lt;/version&gt; gets edited instead by accident!

    /*
     * This script replaces the pom version.
     *
     * This script makes the assumption that either the pom is
     * the top parent and does not have a parent itself in which
     * case the main model version is updated, otherwise the parent
     * version is updated. This also assumes that all modules have
     * the same version as the root pom, and just inherits version
     * from parent.
     *
     * This requires the following variables:
     *   pomVersion - The new version to set to.
     */
    editor.moveToTopOfFile();
    if (editor.find("<project")) {

        if (editor.findFromCurrent("<version>")) {
            editor.replaceCurrentLineBetween("<version>", "</version>", pomVersion);
        }
    }
