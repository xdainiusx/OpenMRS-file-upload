<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<style>
    .error {
        color: #ff0000;
    }

    .errorblock {
        color: #000;
        background-color: #ffEEEE;
        border: 3px solid #ff0000;
        padding: 8px;
        margin: 16px;
    }
    #dss-file-upload {
        display: block;
        clear: both;
        overflow: hidden;
        padding: 0 0 10px 0;
        margin: 0;
        border-bottom: solid 1px #efefef;
    }
    #dss-code-input {
        display: block;
        clear: both;
        overflow: hidden;
        padding: 0 0 10px 0;
        margin: 0;
    }
    textarea {
        width: 600px;
        min-width: 600px;
        max-width: 600px;
        min-height: 300px;
    }
    input, textarea {
        border: solid 1px #eeeeee;
    }
    .btn {
        font-weight: bold;
        padding: 5px 30px;
    }
    .btn {
        -moz-border-bottom-colors: none;
        -moz-border-left-colors: none;
        -moz-border-right-colors: none;
        -moz-border-top-colors: none;
        background-color: #F5F5F5;
        background-image: linear-gradient(to bottom, #FFFFFF, #E6E6E6);
        background-repeat: repeat-x;
        border-color: rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.1) #B3B3B3;
        border-image: none;
        border-radius: 4px 4px 4px 4px;
        border-style: solid;
        border-width: 1px;
        box-shadow: 0 1px 0 rgba(255, 255, 255, 0.2) inset, 0 1px 2px rgba(0, 0, 0, 0.05);
        color: #333333;
        cursor: pointer;
        display: inline-block;
        font-size: 14px;
        line-height: 20px;
        margin-bottom: 0;
        padding: 4px 12px;
        text-align: center;
        text-shadow: 0 1px 1px rgba(255, 255, 255, 0.75);
        vertical-align: middle;
    }
    .bt:hover {
        color: #333333;
        background-color: #e6e6e6;
        *background-color: #d9d9d9;
        text-decoration: none;
        background-position: 0 -15px;
        -webkit-transition: background-position 0.1s linear;
           -moz-transition: background-position 0.1s linear;
             -o-transition: background-position 0.1s linear;
                transition: background-position 0.1s linear;
    }
    .label {
        vertical-align: top;
        width: 180px;
        font-weight: bold;
    }
    .error-row {
        padding: 3px 0px 5px;
        color: #ff0000;
    }
</style>


<h2><spring:message code="DSS Upload Form" /></h2>

<div id="dss-file-upload">
    <form:form commandName="FORM" enctype="multipart/form-data" method="POST">
        <table>
            <tr>
                <td class="label"></td>
                <td  class="error-row"><form:errors path="*" cssStyle="color : red;"/>
                    ${errors}
                    ${dssErrors}
                </td>
            </tr>
            <tr>
                <td class="label">Select File : </td>
                <td>
                    <input type="file" name="file" path="file" />
                </td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td><input class="btn" type="submit" value="Upload File" /></td>
            </tr>
        </table>
    </form:form>
</div>

<div id="dss-code-input">
    <form:form commandName="DSS_CODE" method="POST" action="inputDSS.form">
        <table>
            <tr>
                <td class="label">Enter DSS Program: </td>
                <td>
                    <textarea name="textarea"></textarea> 
                </td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td><input class="btn" type="submit" value="Submit DSS Program" /></td>
            </tr>
        </table>
    </form:form>
</div>


<%@ include file="/WEB-INF/template/footer.jsp"%>