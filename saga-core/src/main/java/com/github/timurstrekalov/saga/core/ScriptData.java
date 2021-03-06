package com.github.timurstrekalov.saga.core;

import com.google.common.collect.Maps;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.Map;
import java.util.Set;

class ScriptData {

    private final String sourceName;
    private final String sourceCode;
    private final boolean separateFile;

    private final Map<Integer, Integer> statementsWithLengths = Maps.newTreeMap();

    private String instrumentedSourceCode;

    ScriptData(final String sourceName, final String sourceCode, final boolean separateFile) {
        this.sourceName = sourceName;
        this.sourceCode = sourceCode;
        this.separateFile = separateFile;
    }

    void addExecutableLine(final Integer lineNr, final Integer length) {
        statementsWithLengths.put(lineNr, length);
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public Set<Integer> getLineNumbersOfAllStatements() {
        return statementsWithLengths.keySet();
    }

    public int getNumberOfStatements() {
        return statementsWithLengths.size();
    }

    public boolean hasStatement(final int lineNr) {
        return statementsWithLengths.containsKey(lineNr);
    }

    public Integer getStatementLength(final int lineNr) {
        return statementsWithLengths.get(lineNr);
    }

    /**
     * For inline scripts, first statement's line number might not be 1 (it will be the actual line number in the
     * HTML)
     */
    public int getLineNumberOfFirstStatement() {
        return getLineNumbersOfAllStatements().iterator().next();
    }

    public void setInstrumentedSourceCode(final String instrumentedSourceCode) {
        this.instrumentedSourceCode = instrumentedSourceCode;
    }

    public String getInstrumentedSourceCode() {
        return instrumentedSourceCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScriptData that = (ScriptData) o;

        if (instrumentedSourceCode != null ? !instrumentedSourceCode.equals(that.instrumentedSourceCode) : that.instrumentedSourceCode != null)
            return false;
        if (sourceCode != null ? !sourceCode.equals(that.sourceCode) : that.sourceCode != null) return false;
        if (sourceName != null ? !sourceName.equals(that.sourceName) : that.sourceName != null) return false;
        if (statementsWithLengths != null ? !statementsWithLengths.equals(that.statementsWithLengths) : that.statementsWithLengths != null)
            return false;

        return true;
    }

    public boolean isSeparateFile() {
        return separateFile;
    }

    @Override
    public int hashCode() {
        int result = sourceName != null ? sourceName.hashCode() : 0;
        result = 31 * result + (sourceCode != null ? sourceCode.hashCode() : 0);
        result = 31 * result + (statementsWithLengths != null ? statementsWithLengths.hashCode() : 0);
        result = 31 * result + (instrumentedSourceCode != null ? instrumentedSourceCode.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).
                append("sourceName", sourceName).
                append("sourceCode", sourceCode).
                append("statementsWithLengths", statementsWithLengths).
                append("instrumentedSourceCode", instrumentedSourceCode).
                toString();
    }
}
