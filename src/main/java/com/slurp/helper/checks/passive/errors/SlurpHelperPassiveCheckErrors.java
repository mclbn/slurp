package com.slurp.helper.checks.passive.errors;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import com.slurp.SlurpUtils;
import com.slurp.helper.AbstractSlurpHelperCheck;
import com.slurp.helper.AbstractSlurpHelperPassiveCheck;
import com.slurp.helper.SlurpHelperAnalyzedRequestResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SlurpHelperPassiveCheckErrors extends AbstractSlurpHelperPassiveCheck {
    private List<String> knownErrors;
    private ArrayList<String> errorList = null;

    public SlurpHelperPassiveCheckErrors(int id, SlurpHelperAnalyzedRequestResponse analyzed, AbstractSlurpHelperCheck parent) {
        super(id, analyzed, parent);
        this.knownErrors = Arrays.asList(
                "A syntax error has occurred",
                "ADODB.Field error",
                "ASP.NET is configured to show verbose error messages",
                "ASP.NET_SessionId",
                "Active Server Pages error",
                "An illegal character has been found in the statement",
                "An unexpected token \"END-OF-STATEMENT\" was found",
                "CLI Driver",
                "Can't connect to local",
                "Custom Error Message",
                "DB2 Driver",
                "DB2 Error",
                "DB2 ODBC",
                "Died at",
                "Disallowed Parent Path",
                "Error Diagnostic Information",
                "Error Message : Error loading required libraries.",
                "Error Report",
                "Error converting data type varchar to numeric",
                "Fatal error",
                "Incorrect syntax near",
                "Index of",
                "Internal Server Error",
                "Invalid Path Character",
                "Invalid procedure call or argument",
                "Invision Power Board Database Error",
                "JDBC Driver",
                "JDBC Error",
                "JDBC MySQL",
                "JDBC Oracle",
                "JDBC SQL",
                "Microsoft OLE DB Provider for ODBC Drivers",
                "Microsoft VBScript compilation error",
                "Microsoft VBScript error",
                "MySQL Driver",
                "MySQL Error",
                "MySQL ODBC",
                "ODBC DB2",
                "ODBC Driver",
                "ODBC Error",
                "ODBC Microsoft Access",
                "ODBC Oracle",
                "ODBC SQL",
                "ODBC SQL Server",
                "OLE/DB provider returned message",
                "ORA-0",
                "ORA-1",
                "Oracle DB2",
                "Oracle Driver",
                "Oracle Error",
                "Oracle ODBC",
                "PHP Error",
                "PHP Parse error",
                "PHP Warning",
                "Parent Directory",
                "Permission denied: 'GetObject'",
                "PostgreSQL query failed: ERROR: parser: parse error",
                "SQL Server Driver][SQL Server",
                "SQL command not properly ended",
                "SQLException",
                "Supplied argument is not a valid PostgreSQL result",
                "Syntax error in query expression",
                "The error occurred in",
                "The script whose uid is",
                "Type mismatch",
                "Unable to jump to row",
                "Unclosed quotation mark before the character string",
                "Unterminated string constant",
                "Warning: Cannot modify header information - headers already sent",
                "Warning: Supplied argument is not a valid File-Handle resource in",
                "Warning: mysql_query()",
                "Warning: pg_connect(): Unable to connect to PostgreSQL server: FATAL",
                "You have an error in your SQL syntax near",
                "data source=",
                "detected an internal error [IBM][CLI Driver][DB2/6000]",
                "error",
                "include_path",
                "invalid query",
                "is not allowed to access",
                "missing expression",
                "mySQL error with query",
                "mysql error",
                "on MySQL result index",
                "on line",
                "server at",
                "server object error",
                "supplied argument is not a valid MySQL result resource",
                "unexpected end of SQL command"
        );
    }

    @Override
    public void doDataCheck(List<String> data) {

    }

    private void addError(String comment) {
        if (this.errorList == null) {
            this.errorList = new ArrayList<String>();
        }
        this.errorList.add(comment);
    }

    @Override
    public void doCheck() {
        int foundErrors = 0;
        String resAsString = SlurpUtils.getInstance().getHelpers().bytesToString(getOriginalRequestResponse().getResponse());

        for (String error : knownErrors) {
            if (resAsString.contains(error)) {
                foundErrors++;
                addError(error);
            }
        }

        if (foundErrors > 0) {
            this.markAsPositive();
        }
    }

    @Override
    public List<String> getData() {
        return errorList;
    }
}
