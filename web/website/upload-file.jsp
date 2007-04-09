<%@ include file="/taglibs.jsp" %><%@ include file="/theme/header.jsp" %>

<roller:StatusMessage/>

<h1><fmt:message key="uploadFiles.title" /></h1>
<roller:FileUpload />

<h2><fmt:message key="uploadFiles.manageFiles" /></h2>

<html:form action="/editor/uploadFiles" method="post">
    <roller:FileManager />
    <table>
       <tr>
          <td align="left">
             <input type="submit" value='<fmt:message key="uploadFiles.button.delete" />' /></input>
          </td>
       </tr>
    </table>
    <input type="hidden" name="method" value="delete"></input>
</html:form>

<%--
Added by Matt Raible since the focus javascript generated by Struts uses
a name reference and IE seems to only focus on file inputs via elements[0]?
--%>
<script type="text/javascript">
<!--
    document.forms[0].elements[0].focus();
// -->
</script>


<%@ include file="/theme/footer.jsp" %>
