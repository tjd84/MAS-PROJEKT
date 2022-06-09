<%@ page import="tjd.mas.projectman.model.Projekt" %>
<%@ page import="java.util.Map" %>
<%@ page import="tjd.mas.projectman.model.Osoba" %>
<%@ page import="tjd.mas.projectman.model.ProjektWewnetrzny" %>
<%@ page import="tjd.mas.projectman.model.ProjektDlaKlienta" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    Projekt projekt = (Projekt) request.getAttribute("projekt");
    String projectIdStr = (String) request.getAttribute("projectIdStr");
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

            <h1>Projekt "<%= projekt.getNazwa() %>"</h1>

            <table class="table mt-5">
                <tr>
                    <td>Nazwa projektu</td>
                    <td><%= projekt.getNazwa() %></td>
                </tr>
                <tr>
                    <td>Koordynator projektu</td>
                    <td><%= projekt.getKoordynator().getOsoba().getImie() %> <%= projekt.getKoordynator().getOsoba().getNazwisko() %> (<%= projekt.getKoordynator().getOsoba().getEmail() %>)</td>
                </tr>
                <tr>
                    <td>Data rozpoczęcia:</td>
                    <td><%= projekt.getDataRozpoczecia() %></td>
                </tr>
                <tr>
                    <td>Przewidywana data zakończenia:</td>
                    <td><%= projekt.getPrzewidywanaDataZakonczenia() %></td>
                </tr>
                <tr>
                    <td>Data zakończenia:</td>
                    <td><%= projekt.getDataZakonczenia()==null?"Projekt trwa":projekt.getDataZakonczenia() %></td>
                </tr>
                <%
                    if(projekt instanceof ProjektDlaKlienta){
                        ProjektDlaKlienta tmp = (ProjektDlaKlienta) projekt;%>
                <tr>
                    <td>Rodzaj projektu:</td>
                    <td>Projekt dla klienta</td>
                </tr>
                <tr>
                    <td>Oczekiwany przychod z realizacji projektu:</td>
                    <td><%=tmp.getOczekiwanyPrzychod()%> zł</td>
                </tr>
                <tr>
                    <td>Budżet projektu:</td>
                    <td><%=tmp.getBudzet()%> zł</td>
                </tr>
                <tr>
                    <td>Wykorzystanie budżetu:</td>
                    <td><%=tmp.getTotalCost()%> zł</td>
                </tr>
                <%}%>

                <%
                    if(projekt instanceof ProjektWewnetrzny){
                        ProjektWewnetrzny tmp = (ProjektWewnetrzny) projekt;%>
                <tr>
                    <td>Rodzaj projektu:</td>
                    <td>Projekt wewnętrzny</td>
                </tr>
                <%}%>
            </table>

            <h1 class="mt-5">Zadania w projekcie</h1>

            <div class="tjdGrid mt-5">
                <% for (Map.Entry<Integer, Projekt.Zadanie> tmp : projekt.getZadania().entrySet()){%>
                    <a href="zadanie?projectId=<%= projectIdStr %>&zadanieId=<%= tmp.getKey().toString() %>">
                        <div class="tjdDivContainer">
                            <div class="m-3">
                                <%=tmp.getValue().getStatus()%><br>
                                <b><%=tmp.getValue().getNazwa()%></b><br>
                                dotychczasowy koszt: <%=tmp.getValue().getTotalCost()%> zł
                                wykonać do: <%=tmp.getValue().getPlanowanaDataWykonaia().toString()%>
                            </div>

                        </div>
                    </a>
                <% } %>
            </div>

        </div>
    </div>

</div>

</body>
</html>