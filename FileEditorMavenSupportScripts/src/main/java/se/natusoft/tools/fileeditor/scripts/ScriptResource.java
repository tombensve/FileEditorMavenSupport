package se.natusoft.tools.fileeditor.scripts;

import java.io.InputStream;

/**
 * Provides input streams to script resources.
 */
public class ScriptResource {

    public static InputStream getScript(String name) {
        return ScriptResource.class.getResourceAsStream(name);
    }
}
