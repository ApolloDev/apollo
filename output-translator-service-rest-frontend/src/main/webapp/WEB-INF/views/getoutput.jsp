<!DOCTYPE HTML>
<html>
<head>
    <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
             pageEncoding="ISO-8859-1" %>
    <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
    <%@ taglib tagdir="/WEB-INF/tags" prefix="myTags" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    <title>Test</title>
</head>
<body>
<div>
    <form id="getOutput-form">
        <div class="panel-heading">
            <h3 class="panel-title">Enter Run ID and URL:</h3>
        </div>
        <div class="row">
            <div class="input-group">
                <form:label path="runID"/>
                <span class="input-group-addon reference-input-group-addon">Run ID:</span>
                <form:input  class="form-control" id="runID" path="runID"/>
            </div>
        </div>
        <div class="row">
            <div class="input-group">
                <form:label path="url"/>
                <span class="input-group-addon reference-input-group-addon">URL:</span>
                <form:input  class="form-control" id="url" path="url"/>
            </div>
        </div>
        <div class="pull-right">
            <button type="button" class="btn btn-default" id="submit">
                <span>Submit</span>
            </button>
        </div>
    </form>
</div>
</body>
<script type="text/javascript">
    function enterAttemptLogin(e) {
        var key=e.keyCode || e.which;
        if (key==13){
            submitOutput();
        }
    }

    $(document).ready(function () {
        $("#submit").click(function e) {
            submitOutput();
        }
    })

    function submitOutput() {
        var runID = $('#runId').val();
        var url = $('#url').val();

        var urlSections = window.location.pathname.split("/");
        var urlForView = "/" + urlSections[1] + "/getOutput";
        var formData = {runID: runID, url: url};

        $.ajax({
            url: urlForView,
            type: "POST",
            data: formData
        }).done(function (response) {
            if (response 'SUCCESS') {
                document.write("201: run ID = " + runID + ", url = " + url);
            }
        })
    };
</script>