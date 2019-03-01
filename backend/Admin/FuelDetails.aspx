<%@ Page Title="" Language="C#" MasterPageFile="~/MasterPage.master" AutoEventWireup="true" CodeFile="FuelDetails.aspx.cs" Inherits="FuelDetails" %>

<%@ Register assembly="System.Web.DataVisualization, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35" namespace="System.Web.UI.DataVisualization.Charting" tagprefix="asp" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head" Runat="Server">
     <style type="text/css">
        .auto-style2 {
            font-family: calibri;
            font-size: large;
        }
        .auto-style3 {
            width: 60%;
        }
        .auto-style4 {
            font-family: "Calibri Light";
            font-size: large;
            font-weight: bold;
        }
        .auto-style5 {
            font-family: calibri;
            font-size: x-large;
        }
        .auto-style6 {
            font-family: calibri;
            font-size: xx-large;
        }
        .auto-style8 {
            font-family: calibri;
            font-size: small;
        }
        .auto-style9 {
        font-family: "Calibri Light";
        font-size: xx-large;
    }
        </style>
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" Runat="Server">
    <br />
            <strong>
            <asp:Label ID="Label1" CssClass="auto-style9" runat="server" Text="Fuel Details"></asp:Label>
        </strong>
    <table class="auto-style3" style="border-top-style: double; border-top-width: 4px; border-top-color: #C0C0C0">
        <tr>
            <td align="center" width="30%">
                &nbsp;</td>
            <td align="left" width="70%">
    <asp:Label ID="Label8" runat="server" Text="Label" Visible="False"></asp:Label>
            </td>
        </tr>
        <tr>
            <td align="center" width="30%">
                <asp:Label ID="Label3" runat="server" CssClass="auto-style5" Text="Name :"></asp:Label>
            </td>
            <td align="left" width="70%"><strong>
                <asp:DropDownList ID="DropDownList1" runat="server" AutoPostBack="True" CssClass="auto-style4" Height="35px" OnSelectedIndexChanged="DropDownList1_SelectedIndexChanged" Width="60%">
                </asp:DropDownList>
                </strong></td>
        </tr>
    </table>
    <asp:Panel ID="Panel1" runat="server">
    <table width="60%">
        <tr>
            <td align="right" width="80%">
    <strong>
    <asp:Label ID="Label5" runat="server" CssClass="auto-style2" Text="Search By :"></asp:Label>
                </strong>
            </td>
            <td align="left">
                <asp:DropDownList ID="DropDownList2" runat="server" CssClass="auto-style2" Width="80%" AutoPostBack="True" OnSelectedIndexChanged="DropDownList2_SelectedIndexChanged">
                    <asp:ListItem>--Select--</asp:ListItem>
                    <asp:ListItem>Date</asp:ListItem>
                </asp:DropDownList>
            </td>
        </tr>
    </table>
        </asp:Panel>
    <asp:Panel ID="Panel2" runat="server">
    <table width="60%">
        <tr>
            <td align="center" width="50%"><strong>
                <asp:Label ID="Label6" runat="server" CssClass="auto-style1" Text="From Date :"></asp:Label>
                </strong></td>
            <td align="left" width="50%"><strong>
                <asp:Label ID="Label7" runat="server" CssClass="auto-style1" Text="To Date :"></asp:Label>
                </strong></td>
        </tr>
        <tr>
            <td align="center" width="50%">
                <asp:Calendar ID="Calendar1" runat="server" CssClass="auto-style8"></asp:Calendar>
            </td>
            <td align="left" width="50%">
                <asp:Calendar ID="Calendar2" runat="server" CssClass="auto-style8"></asp:Calendar>
            </td>
        </tr>
        <tr>
            <td align="center" width="50%">&nbsp;</td>
            <td align="left">&nbsp;</td>
        </tr>
        <tr>
            <td align="center" colspan="2" width="50%">
                <asp:Button ID="Button1" runat="server" OnClick="Button1_Click1" Text="Proceed" />
            </td>
        </tr>
    </table>
        </asp:Panel>
    <br />
    <asp:Panel ID="Panel3" runat="server">
    <asp:GridView ID="GridView1" runat="server" AutoGenerateColumns="false" BackColor="White" BorderColor="#DEDFDE" BorderStyle="None" BorderWidth="1px" CellPadding="4" CssClass="auto-style2" ForeColor="Black" GridLines="Vertical" Width="60%">
        <AlternatingRowStyle BackColor="White" />
        <FooterStyle BackColor="#CCCC99" />
        <HeaderStyle BackColor="#6B696B" Font-Bold="True" ForeColor="White" />
        <PagerStyle BackColor="#F7F7DE" ForeColor="Black" HorizontalAlign="Right" />
        <RowStyle BackColor="#F7F7DE" />
        <SelectedRowStyle BackColor="#CE5D5A" Font-Bold="True" ForeColor="White" />
        <SortedAscendingCellStyle BackColor="#FBFBF2" />
        <SortedAscendingHeaderStyle BackColor="#848384" />
        <SortedDescendingCellStyle BackColor="#EAEAD3" />
        <SortedDescendingHeaderStyle BackColor="#575357" />
       <Columns>
                <asp:BoundField HeaderText="Driver Name" DataField="DriverName"/>
                <asp:BoundField HeaderText="Vehicle No." DataField="VehicleNo"/>
                <asp:BoundField HeaderText="Amount" DataField="Amt" />
                <asp:BoundField HeaderText="Date" DataField="Date" />
                </Columns>

    </asp:GridView>
        
    <table width="60%" style="border-top-style: solid; border-top-width: 2px; border-top-color: #808080; border-bottom-style: solid; border-bottom-width: 2px; border-bottom-color: #808080;">
        <tr>
            <td align="center" width="50%">
    <strong>
    <asp:Label ID="Label4" runat="server" CssClass="auto-style5" Text="Total :" Visible="False"></asp:Label>
                </strong>
            </td>
            <td align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <asp:Label ID="LabelTotal" runat="server" CssClass="auto-style6"></asp:Label>
            </td>
        </tr>
    </table>
        </asp:Panel>
    <strong>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </strong>
    <br />
    <asp:Chart ID="Chart1" runat="server" Width="600px">
    <series>
        <asp:Series Name="Series1" YValuesPerPoint="2">
        </asp:Series>
    </series>
    <chartareas>
        <asp:ChartArea Name="ChartArea1">
        </asp:ChartArea>
    </chartareas>
</asp:Chart>
    <br />
    <br />
</asp:Content>
