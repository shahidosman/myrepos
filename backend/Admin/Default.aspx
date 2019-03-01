<%@ Page Language="C#" AutoEventWireup="true" CodeFile="Default.aspx.cs" Inherits="_Default" %>

<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<title>Map</title>
<link href="http://code.google.com/apis/maps/documentation/javascript/examples/default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>
<asp:Literal ID="js" runat="server"></asp:Literal>
    <style type="text/css">
        .auto-style1 {
            font-family: calibri;
        }
    </style>
</head>
<body onload="initialize()">
<a href="TrackVehicle.aspx" style="color: #874D20"><h2 class="auto-style1"> < Go Back</h2>
    </a>
    <form id="form2" runat="server" style ="width: 100%; height: 100%" >
           <div id="map_canvas" style="width: 100%; height: 100%;">
               <br />
               <br />
               <br />
           </div>
</form>
</body> 
</html>
