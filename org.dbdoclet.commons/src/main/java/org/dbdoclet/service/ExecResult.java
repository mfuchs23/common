package org.dbdoclet.service;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExecResult {

    private Process process;
    private String command;
    private StringBuffer output;
    private Throwable oops;
    private int exitCode = -1;

    public ExecResult() {

        output = new StringBuffer();
        oops = null;
    }

    public void setCommand(String command) {
        this.command = command;
    }
    
    public String getCommand() {
        return command;
    }
    
    public void setOutput(StringBuffer output) {
        this.output = output;
    }

    public void appendOutput(StringBuffer buffer) {
        this.output.append(buffer);
    }

    public void appendOutput(String buffer) {
        this.output.append(buffer);
    }

    public String getOutput() {
        return output.toString();
    }

    public void setProcess(Process process) {
        this.process = process;
    }
    
    public Process getProcess() {
        return process;
    }
    
    public void setThrowable(Throwable oops) {
        this.oops = oops;
    }

    public Throwable getThrowable() {
        return oops;
    }

    public String getStackTrace() {

        if (oops == null) {
            return "";
        }

        StringWriter buffer = new StringWriter();
        oops.printStackTrace(new PrintWriter(buffer));
        return buffer.toString();
    }

    public void setExitCode(int exitCode) {

        this.exitCode = exitCode;
        
    }

    public int getExitCode() {

        return exitCode;
    }

    public boolean failed() {

        if (exitCode == 0) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {

        StringBuffer buffer = new StringBuffer();

        buffer.append("Command '" + command + "' failed!\n");
        buffer.append("Exit Code: ");
        buffer.append(exitCode);
        buffer.append('\n');
        buffer.append("Console Output: ");
        buffer.append(output);
        buffer.append('\n');
        
        if (oops != null) {
            buffer.append("Stacktrace: ");
            buffer.append(getStackTrace());
            buffer.append('\n');
        }

        return buffer.toString();
    }
}
