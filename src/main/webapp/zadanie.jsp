<%@ page import="java.util.Map" %>
<%@ page import="tjd.mas.projectman.model.*" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
  Projekt projekt = (Projekt) request.getAttribute("projekt");
  String projectIdStr = (String) request.getAttribute("projectIdStr");
  Projekt.Zadanie zadanie = (Projekt.Zadanie) request.getAttribute("zadanie");
  String zadanieIdStr = (String) request.getAttribute("zadanieIdStr");
  String dataStored = (String) request.getAttribute("dataStored");
%>

<!DOCTYPE html>
<html>
<head>
  <link rel="stylesheet" href="css/tjd.css">
  <!-- CSS only -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
  <!-- JavaScript Bundle with Popper -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
  <title>projectMan</title>
</head>
<body>
<div class="container-fluid" style="background: dodgerblue;">
  <div class="container">
    <div class="row">
      <div class="col-md-7">
        <div class="mt-2 mb-2" style="color: white; font-size: 32px;">
          <a style="color: white !important;" href="projekt?projectId=<%= projectIdStr %>">projectMan</a>
        </div>
      </div>
      <div class="col-md-5">
        <div class="mt-2 mb-2">
          <input type="text" class="form-control" id="search" placeholder="znajdź zadanie">
        </div>
      </div>
    </div>
  </div>
</div>
<div class="container mt-5 border" style="border-radius: 6px;">

  <div class="row">
    <div class="col-12 p-3">

      <h1>Zadanie "<%=zadanie.getNazwa()%>" w projekcie "<%= projekt.getNazwa() %>"</h1>
      <table class="table mt-5">
        <tr>
          <td>Nazwa zadania:</td>
          <td><%=zadanie.getNazwa()%></td>
        </tr>
        <tr>
          <td>Planowana data wykonania:</td>
          <td><%=zadanie.getPlanowanaDataWykonaia()%></td>
        </tr>
        <tr>
          <td>Status:</td>
          <td><%=zadanie.getStatus()%></td>
        </tr>
        <tr>
          <td>Przypisany pracownik (osoba odpowiedzialna za zadanie):</td>
          <td><%=zadanie.getPracownik().getOsoba().getImie()%> <%=zadanie.getPracownik().getOsoba().getNazwisko()%> (<%=zadanie.getPracownik().getOsoba().getEmail()%>)</td>
        </tr>
      </table>

      <h1 class="mt-5">Zarejestrowane prace</h1>

      <% if(dataStored!=null){ %>
      <div class="alert alert-success" role="alert">
        Dodano pracę.
      </div>
      <%}%>

      <table class="table mt-5">
        <thead>
          <tr>
            <th>Opis czynności</th>
            <th>Pracownik</th>
            <th>Data wykoania</th>
            <th>Czas pracy [h]</th>
            <th>Koszt łączny</th>
          </tr>
        </thead>
        <tbody>
        <% for(Map.Entry<Integer, Praca> praca : zadanie.getPrace().entrySet()) { %>
          <tr>
            <td><%=praca.getValue().getOpisCzynnosci()%></td>
            <td><%=praca.getValue().getPracownik().getOsoba().getImie()%> <%=praca.getValue().getPracownik().getOsoba().getNazwisko()%> (<%=praca.getValue().getPracownik().getStawkaGodzinowa()%> zł/h)</td>
            <td><%=praca.getValue().getDataWykonania()%></td>
            <td><%=praca.getValue().getCzasPracy()%></td>
            <td><%=(praca.getValue().getCzasPracy()*praca.getValue().getPracownik().getStawkaGodzinowa())%> zł</td>
          </tr>
        <%}%>
        </tbody>
        <tfoot>
          <tr>
            <td colspan="2">&nbsp;</td>
            <td><b>TOTAL:</b></td>
            <td><b><%=zadanie.getTotalTime()%></b></td>
            <td><b><%=zadanie.getTotalCost()%> zł</b></td>
          </tr>
        </tfoot>
      </table>
    </div>
    <a href="praca?projectId=<%= projectIdStr %>&zadanieId=<%= zadanieIdStr %>">
      <div class="col-2 p-3 offset-10 mb-3" style="border: 1px solid dodgerblue; background: dodgerblue; border-radius: 4px; color: white;">
        rejestruj czas pracy
      </div>
    </a>

  </div>

</div>

</body>
</html>