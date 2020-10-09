package com.xknower.web.jx.nine;

/**
 * JxBrowser provides functionality that allows you to execute commands such as "Cut", "Copy", "Paste", "Undo", "Redo", "Insert Text", etc.
 * on the loaded web page. JxBrowser supports two types of commands: simple commands (e.g. "Copy", "Cut", "Paste")
 * and parametrized commands ("InsertText", "FindString", etc.).
 * <p>
 * Most of commands can be executed only
 * in a WYSIWYG editor (e.g. "FontSize", "ForegroundColor", "Bold", etc.), HTML text filed or text area ("InsertText", "Cut", "Delete", etc.).
 * <p>
 * To execute command you need to use the Browser.executeCommand(EditorCommand command) method to execute simple commands
 * or the Browser.executeCommand(EditorCommand command, String value) method to execute parameterised command.
 */
public class EditorCommands {
    public static void main(String[] args) {
        // The list of all supported commands you can find in the EditorCommand enum.
//        browser.executeCommand(EditorCommand.SELECT_ALL);
//        browser.executeCommand(EditorCommand.COPY);
//        browser.executeCommand(EditorCommand.CUT);
//        browser.executeCommand(EditorCommand.PASTE);
//        browser.executeCommand(EditorCommand.INSERT_TEXT, "Text");
    }
}
